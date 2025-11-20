package walletSystem.auditService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import walletSystem.auditService.entity.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
