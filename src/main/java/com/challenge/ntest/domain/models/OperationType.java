package com.challenge.ntest.domain.models;

public enum OperationType {
    ACCOUNT(Account.class),
    TRANSACTION(Transaction.class);

    private final Class type;

    OperationType(Class clazz){
        this.type = clazz;
    }

    public Class getType() {
        return type;
    }

}
