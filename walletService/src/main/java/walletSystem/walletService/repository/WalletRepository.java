package walletSystem.walletService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import walletSystem.walletService.entity.Wallet;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByUserId(Long userId);
}
