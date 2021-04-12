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
