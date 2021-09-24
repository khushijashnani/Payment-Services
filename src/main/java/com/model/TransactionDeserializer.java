package com.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

public class TransactionDeserializer implements Deserializer<AcquirerTransaction> {

    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public AcquirerTransaction deserialize(String s, byte[] bytes) {
        if(bytes == null){
            System.out.println("Null received");
            return null;
        }
        else{
            try {
                return objectMapper.readValue(bytes, AcquirerTransaction.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @Override
    public void close() {

    }
}
