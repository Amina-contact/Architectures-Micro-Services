package com.example.comptecqrses.commands.aggregates;

import com.example.comptecqrses.commonapi.commands.DebitAccountCommand;
import com.example.comptecqrses.commonapi.events.AccountDebitedEvent;
import com.example.comptecqrses.commonapi.exceptions.InsufficientBalanceToDebitException;
import com.example.comptecqrses.commonapi.exceptions.InsufficientCreditAmount;
import com.example.comptecqrses.commonapi.commands.CreateAccountCommand;
import com.example.comptecqrses.commonapi.commands.CreditAccountCommand;
import com.example.comptecqrses.commonapi.enums.AccountStatus;
import com.example.comptecqrses.commonapi.events.AccountActivatedEvent;
import com.example.comptecqrses.commonapi.events.AccountCreatedEvent;
import com.example.comptecqrses.commonapi.events.AccountCreditedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.Date;

@Aggregate
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private double balance;
    private String currency;
    private AccountStatus status;
    public AccountAggregate(){
        // Required by Axon
    }
    @CommandHandler // Subscribe to Command Bus, and listen to the CreateAccountCommand events
    public AccountAggregate(CreateAccountCommand createAccountCommand){
        // Business logic Every new account well have a new aggregate
        if(createAccountCommand.getInitialBalance() < 0) throw new RuntimeException(" Balance Negative");
        AggregateLifecycle.apply(new AccountCreatedEvent(
                // Command to event
                createAccountCommand.getId(),
                createAccountCommand.getInitialBalance(),
                createAccountCommand.getCurrency(),
                AccountStatus.ACTIVATED
        ));
    }
    @EventSourcingHandler
    public void on(AccountCreatedEvent accountCreatedEvent){
        this.accountId = accountCreatedEvent.getId();
        this.balance = accountCreatedEvent.getInitialBalance();
        this.currency = accountCreatedEvent.getCurrency();
        this.status = AccountStatus.CREATED;
        AggregateLifecycle.apply(new AccountActivatedEvent(accountCreatedEvent.getId(), AccountStatus.ACTIVATED));
    }
    @EventSourcingHandler
    public void on(AccountActivatedEvent accountActivatedEvent){
        this.status = accountActivatedEvent.getAccountStatus();
    }
    @CommandHandler
    public void handle(CreditAccountCommand creditAccountCommand){

        if(creditAccountCommand.getCreditAmount() <= 100) throw new InsufficientCreditAmount("Credit Amount lower than 100");
        AggregateLifecycle.apply(new AccountCreditedEvent(
                creditAccountCommand.getId(),
                creditAccountCommand.getCreditAmount(),
                creditAccountCommand.getCurrency(),
                new Date()
        ));
    }
    @EventSourcingHandler
    public void on(AccountCreditedEvent accountCreditedEvent){
        this.balance += accountCreditedEvent.getAmount();
    }

    @CommandHandler
    public void handler(DebitAccountCommand debitAccountCommand){
        if(debitAccountCommand.getDebitAmount() < 0) throw new InsufficientCreditAmount("Amount can't be negative");
        if(this.balance < debitAccountCommand.getDebitAmount()) throw new InsufficientBalanceToDebitException("Balance not sufficient");
        AggregateLifecycle.apply(new AccountDebitedEvent(
                debitAccountCommand.getId(),
                debitAccountCommand.getDebitAmount(),
                debitAccountCommand.getCurrency(),
                new Date()
        ));
    }

    @EventSourcingHandler
    public void on(AccountDebitedEvent accountDebitedEvent){
        this.balance -= accountDebitedEvent.getAmount();
    }
}
