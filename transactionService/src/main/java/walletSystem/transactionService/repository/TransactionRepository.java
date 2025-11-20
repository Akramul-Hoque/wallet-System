package walletSystem.transactionService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import walletSystem.transactionService.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
