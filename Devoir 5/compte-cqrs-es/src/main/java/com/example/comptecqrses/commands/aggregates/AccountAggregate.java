package com.example.comptecqrses.commands.aggregates;

import com.example.comptecqrses.commonapi.commands.CreateAccountCommand;
import com.example.comptecqrses.commonapi.enums.AccountStatus;
import com.example.comptecqrses.commonapi.events.AccountActivatedEvent;
import com.example.comptecqrses.commonapi.events.AccountCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

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
}
