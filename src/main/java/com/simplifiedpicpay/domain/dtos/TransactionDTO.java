package com.simplifiedpicpay.domain.dtos;

import java.math.BigDecimal;
import java.math.BigInteger;

public record TransactionDTO(BigDecimal value, BigInteger sender, BigInteger receiver) {
}
