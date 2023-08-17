package com.simplifiedpicpay.domain.repositories;

import com.simplifiedpicpay.domain.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface TransactionRepository extends JpaRepository<Transaction, BigDecimal> {
}
