package br.com.santander.example.saga.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Transaction {
    public static final String VALIDATION_BALANCE = "VALIDATION_BALANCE";
    public static final String SUCCEEDED = "SUCCEEDED";
    public static final String ABORTED = "ABORTED";
    public static final String COMPENSATE = "COMPENSATE";
    public static final String COMPENSATED = "COMPENSATED";

    public String id;
    public String currentStep;
    public Card payload;
    public String sagaStatus;
    public String stepStatus;
    public String type;
    public String version;

}
