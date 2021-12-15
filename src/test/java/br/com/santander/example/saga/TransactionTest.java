package br.com.santander.example.saga;

import br.com.santander.example.saga.model.Transaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class TransactionTest {
    @Test
    public void testTransaction() {
        Transaction transaction = new Transaction();
        transaction.setId("1");
        Assertions.assertEquals("2", transaction.id);
    }

}
