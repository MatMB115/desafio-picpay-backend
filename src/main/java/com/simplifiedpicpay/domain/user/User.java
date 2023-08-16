package com.simplifiedpicpay.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.number.money.MonetaryAmountFormatter;

import java.math.BigDecimal;
import java.math.BigInteger;

@Entity(name="tb_users")
@Table(name="tb_users")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String document;

    private String password;

    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    public User() {

    }
}
