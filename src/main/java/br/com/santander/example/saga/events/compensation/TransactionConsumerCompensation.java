package br.com.santander.example.saga.events.compensation;

import br.com.santander.example.saga.model.Card;
import br.com.santander.example.saga.model.Transaction;
import br.com.santander.example.saga.service.TransactionService;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import static br.com.santander.example.saga.model.Card.*;
import static br.com.santander.example.saga.model.Transaction.*;

@ApplicationScoped
public class TransactionConsumerCompensation {

    private final Logger logger = Logger.getLogger(TransactionConsumerCompensation.class);

    private static final Jsonb JSONB = JsonbBuilder.create();

    @Inject
    TransactionService transactionService;

    @Incoming("compensation-authorization")
    @Outgoing("confirmation-authorization-out")
    public Transaction process(Transaction transaction) {
        logger.info("Got a transaction: " + transaction.id);
        transaction.currentStep = VALIDATION_BALANCE;
        String statusReturn = transactionService.compensateAndUpdateBalance(transaction.getPayload());
        if (statusReturn.equals(BALANCE_RESTORED))
            transaction.stepStatus = COMPENSATED;
        else
            transaction.stepStatus = ABORTED;
        logger.info("Compensated a transaction: " + transaction.id + " with status: "
                + transaction.stepStatus);
        return transaction;
    }
}
