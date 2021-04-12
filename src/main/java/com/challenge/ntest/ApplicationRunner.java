package com.challenge.ntest;

import com.challenge.ntest.domain.models.Account;
import com.challenge.ntest.domain.models.OperationType;
import com.challenge.ntest.domain.models.Transaction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class ApplicationRunner {

    public static void main(String[] args) throws JsonProcessingException {
        Scanner input = new Scanner(System.in);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        Account account = null;
        List<Transaction> transactions = new LinkedList<>();

        while (input.hasNext()) {
            String line = input.nextLine();
            var operationNode = mapper.readTree(line);
            OperationType operationType = OperationType.valueOf(
                    operationNode.fieldNames().next().toUpperCase()
            );

            JsonNode root = operationNode.get(operationType.name().toLowerCase());
            JavaType javaType = mapper.getTypeFactory().constructType(operationType.getType());

            if(operationType == OperationType.ACCOUNT){
                if (Objects.isNull(account)) {
                    account = mapper.convertValue(root, javaType);
                } else {
                    throw new RuntimeException("FODA EIN BIXO");
                }
            }else{
                transactions.add(mapper.convertValue(root, javaType));
            }

        }

        System.out.println(account);
        System.out.println(transactions);
    }
}


package com.challenge.ntest.domain.services;

import com.challenge.ntest.domain.models.Account;
import com.challenge.ntest.domain.models.State;
import com.challenge.ntest.domain.models.Transaction;
import com.challenge.ntest.domain.models.ViolationType;
import com.challenge.ntest.domain.models.Violations;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class StateManager {

    LinkedList<State> history;

    public StateManager() {
        this.history = new LinkedList<>();
    }

    public List<State> getHistory() {
        return history;
    }

    public State getCurrent() {
        if (history.isEmpty()) return null;
        return history.getLast();
    }

    public Account getAccount(){
        return Optional.ofNullable(getCurrent())
                .map(State::getAccount)
                .orElse(null);
    }

    public State performOperation(Account account) {
        Violations violations = new Violations();

        if (Objects.nonNull(getAccount())) {
            violations.add(ViolationType.ACCOUNT_ALREADY_INITIALIZED);
            account = getAccount();
        }

        return addState(account, violations);

    }

    public State performOperation(Transaction transaction){
        Violations violations = new Violations();
        Account account = getAccount();

        if(Objects.isNull(account)){
            violations.add(ViolationType.ACCOUNT_NOT_INITIALIZED);
            return addState(null, violations);
        }

        if(!account.isActiveCard()){
            violations.add(ViolationType.CARD_NOT_ACTIVE);
            return addState(account, violations);
        }

        long spare = account.getAvailableLimit() - transaction.getAmount();
        if(spare < 0){
            violations.add(ViolationType.INSUFFICIENT_LIMIT);
            return addState(account, violations);
        }else{
            account = new Account(true, spare);
        }

        return addState(account, violations);
    }

    //TODO: rename this method
    public State addState(Account account, Violations violations) {
        return addState(new State(account, violations));
    }

    public State addState(State state){
        history.add(state);
        return state;
    }


}

package com.challenge.ntest.domain.models;

public enum ViolationType {
    ACCOUNT_ALREADY_INITIALIZED("account-already-initialized"),
    ACCOUNT_NOT_INITIALIZED("account-not-initialized"),
    CARD_NOT_ACTIVE("card-not-active"),
    INSUFFICIENT_LIMIT("insufficient-limit");

    private final String name;

    ViolationType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

package com.challenge.ntest.domain.models;

public class State {

    private Account account;
    private Violations violations;

    public State() {
        this.violations = new Violations();
    }

    public State(Account account) {
        this(account, new Violations());
    }

    public State(Account account, Violations violations) {
        this.violations = violations;
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Violations getViolations() {
        return violations;
    }

    public void setViolations(Violations violations) {
        this.violations = violations;
    }

    public void addViolation(String violation) {
        this.violations.add(violation);
    }
}
