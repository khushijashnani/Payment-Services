package com.acquirer.rmi;

import com.model.AcquirerTransaction;
import com.model.TransactionSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class AcquirerProcessImpl implements AcquirerProcessInterface, AcquirerTimeInterface {


    private int add(int a, int b) {
        return a + b;
    }


    @Override
    public Map<String, String> processMerchantTransaction(AcquirerTransaction acquirerTransaction) throws Exception {

        System.out.println("Request Received with Body " + acquirerTransaction);
        acquirerTransaction.setTimestamp(this.getSystemTime());
        acquirerTransaction.setStatus("failed");
        Map<String, String> map = new HashMap<>();
        map.put("id", acquirerTransaction.getId());
        map.put("status", "failed");
        map.put("customerName", acquirerTransaction.getCustomerName());
        map.put("merchantName", acquirerTransaction.getMerchantName());
        if(acquirerTransaction.getAmount() <= 1000) {
            map.put("status", "approved");
            acquirerTransaction.setStatus("approved");
        }
        // ProducerRecord
        ProducerRecord<String, AcquirerTransaction> producerRecord = new ProducerRecord<String, AcquirerTransaction>("transaction", acquirerTransaction);

        // Send to Kafka
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, TransactionSerializer.class.getName());

        // create kafka consumer
        KafkaProducer<String, AcquirerTransaction> kafkaProducer = new KafkaProducer<String, AcquirerTransaction>(properties);
        kafkaProducer.send(producerRecord);

        return map; //passed by value
    }

    @Override
    public long getSystemTime(){
        long time = Instant.now().toEpochMilli();
        System.out.println("This is the current time on the server, "+ time);
        return time;
    }
}
