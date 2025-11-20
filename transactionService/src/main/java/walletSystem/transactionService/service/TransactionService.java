package walletSystem.transactionService.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import walletSystem.transactionService.entity.Transaction;
import walletSystem.transactionService.repository.TransactionRepository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional
    public Transaction initiateTransaction(Long senderId, Long receiverId, BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setSenderId(senderId);
        transaction.setReceiverId(receiverId);
        transaction.setAmount(amount);
        transaction.setStatus("PENDING");
        Transaction saved = transactionRepository.save(transaction);

        // Start Saga: Request Debit
        Map<String, Object> event = new HashMap<>();
        event.put("transactionId", saved.getId());
        event.put("senderId", senderId);
        event.put("amount", amount);
        kafkaTemplate.send("wallet.debit.requested", event);
        
        log.info("Transaction initiated: {}", saved.getId());
        return saved;
    }

    public void handleWalletDebited(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow();
        transaction.setStatus("DEBITED");
        transactionRepository.save(transaction);

        // Continue Saga: Request Credit
        Map<String, Object> event = new HashMap<>();
        event.put("transactionId", transactionId);
        event.put("receiverId", transaction.getReceiverId());
        event.put("amount", transaction.getAmount());
        kafkaTemplate.send("wallet.credit.requested", event);
        
        log.info("Wallet debited for transaction: {}, requesting credit", transactionId);
    }

    public void handleWalletCredited(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow();
        transaction.setStatus("COMPLETED");
        transactionRepository.save(transaction);
        
        kafkaTemplate.send("transaction.completed", Map.of("transactionId", transactionId));
        log.info("Transaction completed: {}", transactionId);
    }

    public void handleWalletDebitFailed(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow();
        transaction.setStatus("FAILED");
        transactionRepository.save(transaction);
        
        kafkaTemplate.send("transaction.failed", Map.of("transactionId", transactionId));
        log.error("Transaction failed: {}", transactionId);
    }
}
