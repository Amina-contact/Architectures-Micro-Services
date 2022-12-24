package com.example.comptecqrses.query.service;

import com.example.comptecqrses.commonapi.enums.OperationType;
import com.example.comptecqrses.commonapi.events.AccountActivatedEvent;
import com.example.comptecqrses.commonapi.events.AccountCreatedEvent;
import com.example.comptecqrses.commonapi.events.AccountCreditedEvent;
import com.example.comptecqrses.commonapi.events.AccountDebitedEvent;
import com.example.comptecqrses.commonapi.queries.GetAccountByIdQuery;
import com.example.comptecqrses.commonapi.queries.GetAllAccountsQuery;
import com.example.comptecqrses.query.entities.Account;
import com.example.comptecqrses.query.entities.Operation;
import com.example.comptecqrses.query.repositories.AccountRepository;
import com.example.comptecqrses.query.repositories.OperationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class AccountServiceHandler {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;
    @EventHandler
    public void on(AccountCreatedEvent accountCreatedEvent){
        log.info("AccountCreatedEvent ");
        Account account =  Account
                .builder()
                .id(accountCreatedEvent.getId())
                .balance(accountCreatedEvent.getInitialBalance())
                .accountStatus(accountCreatedEvent.getAccountStatus())
                .currency(accountCreatedEvent.getCurrency())
                .build();
        accountRepository.save(account);
    }
    @EventHandler
    public void on(AccountActivatedEvent accountActivatedEvent){
        log.info(" AccountActivatedEvent ");
        Account account = accountRepository.findById(accountActivatedEvent.getId()).get();
        account.setAccountStatus(accountActivatedEvent.getAccountStatus());
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountDebitedEvent accountDebitedEvent){
        log.info("AccountDebitedEvent ");
        Account account = accountRepository.findById(accountDebitedEvent.getId()).get();
        Operation operation = Operation.builder()
                .amount(accountDebitedEvent.getAmount())
                .date(new Date())
                .type(OperationType.DEBIT)
                .account(account)
                .build();
        operationRepository.save(operation);
        account.setBalance(account.getBalance() - accountDebitedEvent.getAmount());
        accountRepository.save(account);
    }
    @EventHandler
    public void on(AccountCreditedEvent accountCreditedEvent){
        log.info("AccountCreditedEvent ");
        Account account = accountRepository.findById(accountCreditedEvent.getId()).get();
        Operation operation = Operation.builder()
                .amount(accountCreditedEvent.getAmount())
                .date(new Date())
                .type(OperationType.DEBIT)
                .account(account)
                .build();
        operationRepository.save(operation);
        account.setBalance(account.getBalance() + accountCreditedEvent.getAmount());
        accountRepository.save(account);
    }
    @QueryHandler
    public List<Account> on(GetAllAccountsQuery getAllAccountQuery){
        return accountRepository.findAll();
    }

    @QueryHandler
    public Account on(GetAccountByIdQuery getAccountQuery){
        return accountRepository.findById(getAccountQuery.getId()).get();
    }
}
