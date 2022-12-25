<h1> Event Driven Architecture CQRS and Event Sourcing </h1>
Créer une application qui permet de gérer des comptes respectant les patterns CQRS et Event Sourcing avec les Framework AXON et Spring Boot.
<p align="center">
  <img src="https://github.com/Amina-contact/Architectures-Micro-Services/blob/master/Devoir%205/pictures/1.JPG" class="center">
</p>
<h3>Dependencies : </h3>
<pre class="notranslate"><code>
   - Spring web
   - Spring data JPA
   - MySQL Driver
   - Lombok
   - Axon Framwork
</code></pre>
<h3>Application.properties : </h3>
<pre class="notranslate"><code>
spring.application.name=compte-service
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:${MYSQL_PORT:3306}/bank?createDatabaseIfNotExist=true
spring.datasource.username=${MYSQL_USER:root}
spring.datasource.password=${MYSQL_PASSWORD:}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MariaDBDialect
server.port=8082 
</code></pre>
<h3>Commands & Events : </h3>
<li>BaseCommand</strong>:</li>
<pre class="notranslate"><code>
public abstract class BaseCommand<T> {
    @TargetAggregateIdentifier
    @Getter private T id;
    public BaseCommand(T id) {
        this.id = id;
    }
} 
</code></pre>
<li>CreateAccountCommand</strong>:</li>
<pre class="notranslate"><code>
public class CreateAccountCommand extends BaseCommand<String>{
    private double initialBalance;
    private String currency;
    public CreateAccountCommand(String id, double accountBalance, String currency) {
        super(id);
        this.initialBalance = accountBalance;
        this.currency = currency;
    }
    public double getInitialBalance() {
        return initialBalance;
    }
    public String getCurrency() {
        return currency;
    }
}
</code></pre>
<li>DebitAccountCommand</strong>:</li>
<pre class="notranslate"><code>
public class DebitAccountCommand extends BaseCommand<String>{
    @Getter private double debitAmount;
    @Getter private String currency;
    public DebitAccountCommand(String id, double debitAmount, String currency) {
        super(id);
        this.debitAmount = debitAmount;
        this.currency = currency;
    }
} 
</code></pre>
<li>CreditAccountCommand</strong>:</li>
<pre class="notranslate"><code>
public class CreditAccountCommand extends BaseCommand<String>{
    @Getter private double creditAmount;
    @Getter private String currency;
    public CreditAccountCommand(String id, double creditAmount, String currency) {
        super(id);
        this.creditAmount = creditAmount;
        this.currency = currency;
    }
}
</code></pre>
<h3>Commands Controllers : </h3>
<li>AccountCommandController</strong>:</li>
<pre class="notranslate"><code>
public class AccountCommandController {
    private CommandGateway commandGateway;
    private EventStore eventStore;
    @PostMapping("/create")
    public CompletableFuture<String> createAccount(@RequestBody CreateAccountRequestDTO request){
        CompletableFuture<String> commandResponse = commandGateway.send(new CreateAccountCommand(
                UUID.randomUUID().toString(),
                request.getInitialBalance(),
                request.getCurrency()
        ));

        return commandResponse;
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception exception){
        ResponseEntity<String> responseEntity = new ResponseEntity<>(
                exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR
        );

        return responseEntity;
    }
    @GetMapping("/eventStore/{accountId}")
    public Stream eventStore(@PathVariable String accountId){
        return eventStore.readEvents(accountId).asStream();
    }

    @PutMapping("/credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountRequestDTO creditAccountRequestDTO){
        CompletableFuture<String> creditAccountCommandResponse = commandGateway.send(new CreditAccountCommand(
                creditAccountRequestDTO.getAccountId(),
                creditAccountRequestDTO.getAmount(),
                creditAccountRequestDTO.getCurrency()
        ));
        return creditAccountCommandResponse;
    }
    @PutMapping("/debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitAccountRequestDTO debitAccountRequestDTO){
        CompletableFuture<String> debitAccountCommandResponse = commandGateway.send(new DebitAccountCommand(
                debitAccountRequestDTO.getAccountId(),
                debitAccountRequestDTO.getAmount(),
                debitAccountRequestDTO.getCurrency()
        ));

        return debitAccountCommandResponse;
    }
}
</code></pre>
