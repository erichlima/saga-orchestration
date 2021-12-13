package br.com.santander.example.saga.service;

import br.com.santander.example.saga.events.TransactionConsumer;
import br.com.santander.example.saga.model.Card;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import static com.mongodb.client.model.Filters.eq;

@ApplicationScoped
public class TransactionService {

    private final Logger logger = Logger.getLogger(TransactionService.class);

    @Inject
    MongoClient mongoClient;

    private MongoCollection<Document> getCollection(){
        logger.info("getCollection from mongoDb");
        return mongoClient.getDatabase("transaction").getCollection("card");
    }

    public String validateAndUpdateBalance(Card card) throws Exception {
        Document document = getCollection().find(eq("idCard", card.idCard)).first();
        if (document != null) {
            if (document.getDouble("balance") >= card.getBalance()){
                getCollection().updateOne(eq("idCard", card.idCard),
                        new Document("$set", new Document("balance",document.getDouble("balance")-card.getBalance())));
            } else return Card.BALANCE_INSUFFICIENT;
        } else return Card.CARD_NOT_FOUND;
        logger.info("Error connecting to database");
        return Card.BALANCE_UPDATED;
    }

/*    public String validateAndUpdateBalance(Card card) throws Exception {
        Card.validateAndUpdateBalance(card);
        return Card.BALANCE_UPDATED;
    }*/

    public String compensateAndUpdateBalance(Card card){
        Document document = getCollection().find(eq("idCard", card.idCard)).first();
        if (document != null) {
            getCollection().updateOne(eq("idCard", card.idCard),
                    new Document("$set", new Document("balance",document.getDouble("balance")+card.getBalance())));
        } else return Card.CARD_NOT_FOUND;
        return Card.BALANCE_RESTORED;
    }

}
