package com.challenge.ntest.domain.commons;

import com.challenge.ntest.domain.models.OperationType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.List;

public class OperationMapper {

    final ObjectMapper mapper;

    public OperationMapper() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }


    @SuppressWarnings("unchecked")
    public <T> T convertValue(String line)
            throws IllegalArgumentException {
        var operationNode = getOperationNode(mapper, line);
        final OperationType operationType = getOperationType(operationNode);

        final JsonNode root = operationNode.get(operationType.name().toLowerCase());
        final JavaType javaType = mapper.getTypeFactory().constructType(operationType.getType());

        return mapper.convertValue(root, javaType);
    }

    public <T> String writeListAsString(List<T> ob) {
        final StringBuilder builer = new StringBuilder();
        ob.forEach(line -> {
            try {
                builer.append(mapper.writeValueAsString(line))
                        .append("\n");
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });

        return builer.toString();
    }

    private JsonNode getOperationNode(ObjectMapper mapper, String line) {
        try {
            return mapper.readTree(line);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unespected Error");
        }
    }

    private OperationType getOperationType(JsonNode operationNode) {
        return OperationType.valueOf(
                operationNode.fieldNames().next().toUpperCase()
        );
    }

}
