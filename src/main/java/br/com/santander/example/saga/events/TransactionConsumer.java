package br.com.santander.example.saga.events;

import br.com.santander.example.saga.events.compensation.TransactionProducerCompensation;
import br.com.santander.example.saga.model.Card;
import br.com.santander.example.saga.model.Transaction;
import br.com.santander.example.saga.service.TransactionService;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import java.util.Base64;

import static br.com.santander.example.saga.model.Card.BALANCE_UPDATED;
import static br.com.santander.example.saga.model.Transaction.*;


@ApplicationScoped
public class TransactionConsumer {

    private final Logger logger = Logger.getLogger(TransactionConsumer.class);

    private static final Jsonb JSONB = JsonbBuilder.create();

    @Inject
    TransactionService transactionService;
    @Inject
    TransactionProducerCompensation transactionProducerCompensation;

    @Incoming("preview-authorization")
    @Outgoing("confirmation-authorization")
    public Transaction process(Transaction transaction) {
        logger.info("Got a transaction: " + transaction.id);
        try {
            String mongoRootPassword = ConfigProvider.getConfig().getValue("mongo-root-password", String.class);
            logger.info("mongo-root-password-sem-decode: " + mongoRootPassword);
            logger.info("mongo-root-password-com-decode: " + Base64.getUrlDecoder().decode(mongoRootPassword));
            String statusReturn = transactionService.validateAndUpdateBalance(transaction.getPayload());
            transaction.currentStep = VALIDATION_BALANCE;
            if (statusReturn.equals(BALANCE_UPDATED))
                transaction.stepStatus = SUCCEEDED;
            else
                transaction.stepStatus = ABORTED + "(" + statusReturn + ")";
            logger.info("Validated a transaction: " + transaction.id);
        } catch (Exception ex) {
            logger.error("Error in transaction: " + transaction.id + " - Ex: " + ex.getMessage());
        }
        return transaction;
    }

}
