package br.com.santander.example.saga.events;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TransactionConsumer2 {

    private final Logger logger = Logger.getLogger(TransactionConsumer.class);

    @Incoming("preview-authorization2")
    public void process(String msg) {
        logger.info("Got a transaction: " + msg);
    }

}
