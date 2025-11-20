package walletSystem.transactionService.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import walletSystem.transactionService.entity.Transaction;
import walletSystem.transactionService.service.TransactionService;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> initiateTransaction(@RequestBody Map<String, Object> request) {
        Long senderId = Long.valueOf(request.get("senderId").toString());
        Long receiverId = Long.valueOf(request.get("receiverId").toString());
        BigDecimal amount = new BigDecimal(request.get("amount").toString());

        Transaction transaction = transactionService.initiateTransaction(senderId, receiverId, amount);
        return ResponseEntity.ok(transaction);
    }
}
