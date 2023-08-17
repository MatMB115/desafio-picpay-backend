package com.simplifiedpicpay.domain.services;

import com.simplifiedpicpay.domain.dtos.UserDTO;
import com.simplifiedpicpay.domain.repositories.UserRepository;
import com.simplifiedpicpay.domain.user.User;
import com.simplifiedpicpay.domain.user.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public void validateTransaction(User sender, BigDecimal amount) throws Exception{
        if(sender.getUserType() == UserType.MERCHANT){
            throw new Exception("Lojistas não pode efetuar transações");
        }

        if(sender.getBalance().compareTo(amount) < 0){
            throw new Exception("Saldo insuficiente");
        }
    }

    public User findUserById(BigInteger id) throws Exception {
        return this.repository.findUserById(id).orElseThrow(()-> new Exception("Usuário não encontrado"));
    }

    public User createUser(UserDTO data) {
        User newUser = new User(data);
        this.saveUser(newUser);
        return newUser;
    }

    public void saveUser(User user){
        this.repository.save(user);
    }


    public List<User> getAllUsers() {
        return this.repository.findAll();
    }
}
