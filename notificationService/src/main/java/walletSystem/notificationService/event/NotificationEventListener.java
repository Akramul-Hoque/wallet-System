package walletSystem.notificationService.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificationEventListener {

    @KafkaListener(topics = "user.created", groupId = "notification-group")
    public void handleUserCreated(String message) {
        log.info("Notification: User created - {}", message);
    }

    @KafkaListener(topics = "transaction.initiated", groupId = "notification-group")
    public void handleTransactionInitiated(String message) {
        log.info("Notification: Transaction initiated - {}", message);
    }

    @KafkaListener(topics = "wallet.debited", groupId = "notification-group")
    public void handleWalletDebited(String message) {
        log.info("Notification: Wallet debited - {}", message);
    }

    @KafkaListener(topics = "wallet.credited", groupId = "notification-group")
    public void handleWalletCredited(String message) {
        log.info("Notification: Wallet credited - {}", message);
    }

    @KafkaListener(topics = "transaction.completed", groupId = "notification-group")
    public void handleTransactionCompleted(String message) {
        log.info("Notification: Transaction completed - {}", message);
    }

    @KafkaListener(topics = "transaction.failed", groupId = "notification-group")
    public void handleTransactionFailed(String message) {
        log.info("Notification: Transaction failed - {}", message);
    }
}
