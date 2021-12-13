package br.com.santander.example.saga.events;

import br.com.santander.example.saga.model.Transaction;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.UUID;

@ApplicationScoped
public class TransactionConsumer2 {

    private final Logger logger = Logger.getLogger(TransactionConsumer.class);
    public static final String STEP_STATUS_STARTED = "STARTED";
    public static final String CURRENT_STEP_VALIDATION = "VALIDATION_PAYMENT";

    @Incoming("preview-authorization2")
    public void process(String msg) {
        logger.info("Got a transaction: " + msg);
    }

    @Inject
    @Channel("preview-authorization-producer")
    Emitter<Transaction> emitter;
    public void sendTransactionToKafka(Transaction transaction) {
        UUID uuid = UUID.randomUUID();
        transaction.id = uuid.toString();
        //transaction.payload = Long.toString(new Date().getTime());
        transaction.stepStatus = STEP_STATUS_STARTED;
        transaction.currentStep = CURRENT_STEP_VALIDATION;
        logger.info("Producer of transacion=\t"+transaction);
        emitter.send(transaction);
    }

}
