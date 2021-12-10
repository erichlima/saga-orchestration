package br.com.santander.example.saga.events;

import br.com.santander.example.saga.events.compensation.TransactionProducerCompensation;
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

import static br.com.santander.example.saga.model.Card.BALANCE_UPDATED;
import static br.com.santander.example.saga.model.Transaction.*;


@ApplicationScoped
public class TransactionConsumer2 {

    private final Logger logger = Logger.getLogger(TransactionConsumer.class);

    @Incoming("preview-authorization2")
    public void process(String msg) {
        logger.info("Got a transaction: " + msg);
    }

}
