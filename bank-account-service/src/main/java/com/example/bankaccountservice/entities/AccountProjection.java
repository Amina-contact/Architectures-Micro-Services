package com.example.bankaccountservice.entities;

import org.springframework.data.rest.core.config.Projection;

@Projection(types = BankAccount.class ,name = "p1")
public interface AccountProjection {
    public String getTd();
    public AccountType getType();
}
