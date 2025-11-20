package walletSystem.walletService.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import walletSystem.walletService.entity.Wallet;
import walletSystem.walletService.repository.WalletRepository;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class WalletService {

    private final WalletRepository walletRepository;
    private final org.springframework.kafka.core.KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional
    public void createWallet(Long userId) {
        if (walletRepository.findByUserId(userId).isPresent()) {
            log.warn("Wallet already exists for user {}", userId);
            return;
        }
        Wallet wallet = new Wallet();
        wallet.setUserId(userId);
        wallet.setBalance(BigDecimal.ZERO);
        wallet.setCurrency("USD");
        walletRepository.save(wallet);
        log.info("Wallet created for user {}", userId);
    }

    public Wallet getWallet(Long userId) {
        return walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
    }

    @Transactional
    public void debitWallet(Long userId, BigDecimal amount, Long transactionId) {
        Wallet wallet = getWallet(userId);
        if (wallet.getBalance().compareTo(amount) < 0) {
            log.warn("Insufficient funds for user {}", userId);
            kafkaTemplate.send("wallet.debit.failed", java.util.Map.of("transactionId", transactionId, "reason", "Insufficient funds"));
            return;
        }
        wallet.setBalance(wallet.getBalance().subtract(amount));
        walletRepository.save(wallet);
        log.info("Wallet debited for user {}", userId);
        kafkaTemplate.send("wallet.debited", java.util.Map.of("transactionId", transactionId, "userId", userId));
    }

    @Transactional
    public void creditWallet(Long userId, BigDecimal amount, Long transactionId) {
        Wallet wallet = getWallet(userId);
        wallet.setBalance(wallet.getBalance().add(amount));
        walletRepository.save(wallet);
        log.info("Wallet credited for user {}", userId);
        kafkaTemplate.send("wallet.credited", java.util.Map.of("transactionId", transactionId, "userId", userId));
    }
}
