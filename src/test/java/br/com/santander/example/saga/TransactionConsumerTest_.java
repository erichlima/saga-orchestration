package br.com.santander.example.saga;

import br.com.santander.example.saga.model.Card;
import br.com.santander.example.saga.model.Transaction;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.reactive.messaging.connectors.InMemoryConnector;
import io.smallrye.reactive.messaging.connectors.InMemorySink;
import io.smallrye.reactive.messaging.connectors.InMemorySource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.enterprise.inject.Any;
import javax.inject.Inject;


@QuarkusTest
@QuarkusTestResource(KafkaTestResourceLifecycleManager_.class)
public class TransactionConsumerTest_ {

    @Inject @Any
    InMemoryConnector connector;

    @Test
    public void testTransactionConsumerTest() {
        InMemorySource<Transaction> transactionIn = connector.source("preview-authorization");
        InMemorySink<Transaction> transactionOut = connector.sink("confirmation-authorization");

        Transaction transactionFake = new Transaction();
        transactionFake.setId("73707ad2-0732-4592-b7e2-79b07c745e45");
        transactionFake.setCurrentStep("STARTED");
        Card card = new Card();
        card.setIdCard("10");
        card.setBalance(20);
        card.setPin("1234");
        transactionFake.setPayload(card);
        transactionFake.setSagaStatus("STARTED");
        transactionFake.setStepStatus("STARTED");
        transactionFake.setType("VALIDATION_PAYMENT");
        transactionFake.setVersion("1");
        transactionIn.send(transactionFake);

        Assertions.assertEquals(1, transactionOut.received().size());
        Transaction transactionReceived = transactionOut.received().get(0).getPayload();
        Assertions.assertEquals("73707ad2-0732-4592-b7e2-79b07c745e45", transactionReceived.getId());
    }

    @Test
    public void testTransaction() {
        Transaction transaction = new Transaction();
        transaction.setId("1");
        Assertions.assertEquals("1", transaction.id);
    }



}