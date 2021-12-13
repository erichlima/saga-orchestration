package br.com.santander.example.saga.rest;



import br.com.santander.example.saga.events.TransactionConsumer;
import br.com.santander.example.saga.events.TransactionConsumer2;
import br.com.santander.example.saga.model.Transaction;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TransactionResource {

    @Inject
    TransactionConsumer2 producer;

    @POST
    public Response send(Transaction transaction) {
        producer.sendTransactionToKafka(transaction);
        // Return an 202 - Accepted response.
        return Response.accepted().entity(transaction).build();
    }


}