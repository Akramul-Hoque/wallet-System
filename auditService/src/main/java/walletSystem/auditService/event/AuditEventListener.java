package walletSystem.auditService.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import walletSystem.auditService.entity.AuditLog;
import walletSystem.auditService.repository.AuditLogRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuditEventListener {

    private final AuditLogRepository auditLogRepository;

    @KafkaListener(topics = {"user.created", "transaction.initiated", "wallet.debited", "wallet.credited", "transaction.completed", "transaction.failed", "wallet.debit.failed"}, groupId = "audit-group")
    public void handleEvent(String message) {
        try {
            AuditLog logEntry = new AuditLog();
            logEntry.setEventType("UNKNOWN"); // Ideally parse topic from header or use specific listeners
            logEntry.setEventData(message);
            auditLogRepository.save(logEntry);
            log.info("Audit log saved: {}", message);
        } catch (Exception e) {
            log.error("Error saving audit log", e);
        }
    }
    
    // Note: For better granularity, we could have separate methods for each topic to set eventType correctly.
    // For now, capturing the payload is sufficient for the requirement.
}
