package walletSystem.transactionService.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import walletSystem.transactionService.service.TransactionService;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class TransactionEventListener {

    private final TransactionService transactionService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "wallet.debited", groupId = "transaction-group")
    public void handleWalletDebited(String message) {
        try {
            Map<String, Object> event = objectMapper.readValue(message, Map.class);
            Long transactionId = Long.valueOf(event.get("transactionId").toString());
            log.info("Received wallet.debited for transaction: {}", transactionId);
            transactionService.handleWalletDebited(transactionId);
        } catch (Exception e) {
            log.error("Error processing wallet.debited", e);
        }
    }

    @KafkaListener(topics = "wallet.credited", groupId = "transaction-group")
    public void handleWalletCredited(String message) {
        try {
            Map<String, Object> event = objectMapper.readValue(message, Map.class);
            Long transactionId = Long.valueOf(event.get("transactionId").toString());
            log.info("Received wallet.credited for transaction: {}", transactionId);
            transactionService.handleWalletCredited(transactionId);
        } catch (Exception e) {
            log.error("Error processing wallet.credited", e);
        }
    }

    @KafkaListener(topics = "wallet.debit.failed", groupId = "transaction-group")
    public void handleWalletDebitFailed(String message) {
        try {
            Map<String, Object> event = objectMapper.readValue(message, Map.class);
            Long transactionId = Long.valueOf(event.get("transactionId").toString());
            log.info("Received wallet.debit.failed for transaction: {}", transactionId);
            transactionService.handleWalletDebitFailed(transactionId);
        } catch (Exception e) {
            log.error("Error processing wallet.debit.failed", e);
        }
    }
}
