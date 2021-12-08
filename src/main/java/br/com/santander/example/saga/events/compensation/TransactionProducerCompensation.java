package br.com.santander.example.saga.events.compensation;

import br.com.santander.example.saga.model.Transaction;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class TransactionProducerCompensation {

    private final Logger logger = Logger.getLogger(TransactionProducerCompensation.class);

    @Inject
    @Channel("producer_compensate_transaction")
    Emitter<Transaction> emitter;

    public void sendCompensateTransaction(Transaction transaction) {
        logger.info("Send a compensate transaction: " + transaction.id);
        emitter.send(transaction);
    }

}
