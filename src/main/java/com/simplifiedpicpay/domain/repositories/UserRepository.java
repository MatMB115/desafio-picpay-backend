package com.simplifiedpicpay.domain.repositories;

import com.simplifiedpicpay.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, BigInteger> {
    Optional<User> findUserByDocument(String document);

    Optional<User> findUserById(BigInteger id);
}
