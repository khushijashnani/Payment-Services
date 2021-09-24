package com.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class TransactionSerializer implements Serializer<AcquirerTransaction> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String s, AcquirerTransaction acquirerTransaction) {
        try {
            if (acquirerTransaction == null){
                System.out.println("Null received at serializing");
                return null;
            }
            System.out.println("Serializing...");
            return objectMapper.writeValueAsBytes(acquirerTransaction);
        }
        catch(Exception e) {
            System.out.println(e);
        }
        return new byte[0];
    }

    @Override
    public void close() {

    }
}
