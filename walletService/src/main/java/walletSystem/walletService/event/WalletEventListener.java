package walletSystem.walletService.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import walletSystem.walletService.service.WalletService;

@Component
@RequiredArgsConstructor
@Slf4j
public class WalletEventListener {

    private final WalletService walletService;
    private final com.fasterxml.jackson.databind.ObjectMapper objectMapper;

    @KafkaListener(topics = "user.created", groupId = "wallet-group")
    public void handleUserCreated(UserCreatedEvent event) {
        log.info("Received user.created event for user: {}", event.getUserId());
        walletService.createWallet(event.getUserId());
    }

    @KafkaListener(topics = "wallet.debit.requested", groupId = "wallet-group")
    public void handleDebitRequested(String message) {
        try {
            java.util.Map<String, Object> event = objectMapper.readValue(message, java.util.Map.class);
            Long userId = Long.valueOf(event.get("senderId").toString());
            java.math.BigDecimal amount = new java.math.BigDecimal(event.get("amount").toString());
            Long transactionId = Long.valueOf(event.get("transactionId").toString());
            
            walletService.debitWallet(userId, amount, transactionId);
        } catch (Exception e) {
            log.error("Error processing wallet.debit.requested", e);
        }
    }

    @KafkaListener(topics = "wallet.credit.requested", groupId = "wallet-group")
    public void handleCreditRequested(String message) {
        try {
            java.util.Map<String, Object> event = objectMapper.readValue(message, java.util.Map.class);
            Long userId = Long.valueOf(event.get("receiverId").toString());
            java.math.BigDecimal amount = new java.math.BigDecimal(event.get("amount").toString());
            Long transactionId = Long.valueOf(event.get("transactionId").toString());
            
            walletService.creditWallet(userId, amount, transactionId);
        } catch (Exception e) {
            log.error("Error processing wallet.credit.requested", e);
        }
    }
}
