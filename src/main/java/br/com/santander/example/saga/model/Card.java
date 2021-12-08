package br.com.santander.example.saga.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Card {
    public static final String CARD_NOT_FOUND = "CARD_NOT_FOUND";
    public static final String BALANCE_INSUFFICIENT = "BALANCE_INSUFFICIENT";
    public static final String BALANCE_UPDATED = "BALANCE_UPDATED";
    public static final String BALANCE_RESTORED = "BALANCE_RESTORED";

    public String idCard;
    public double balance;
    public String pin;
}
