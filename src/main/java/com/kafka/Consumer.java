package com.kafka;

import com.model.AcquirerTransaction;
import com.model.TransactionDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;


import java.sql.*;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class Consumer {

    public static void main(String[] args) {

        // Topics
        String[] topics = {"transaction"};

        //  set consumer properties
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, TransactionDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "consumers");

        // create kafka consumer
        KafkaConsumer<String, AcquirerTransaction> kafkaConsumer = new KafkaConsumer<String, AcquirerTransaction>(properties);
        kafkaConsumer.subscribe(Arrays.asList(topics));

        // Connection to MySQL Database
        String url = "jdbc:mysql://localhost:3306/PaymentService";
        String user = "root";
        String password = "Khushi";
        Connection connection;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url,user,password);
            System.out.println("Connection is successful to the database "+ url);

            while(true) {
                // create consumer record
                ConsumerRecords<String, AcquirerTransaction> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(1000));
                for (ConsumerRecord<String, AcquirerTransaction> consumerRecord : consumerRecords) {
                    System.out.println(consumerRecord.value());
                    AcquirerTransaction acquirerTransaction = consumerRecord.value();

                    String query = " insert into transaction (id, customerName, customerCardNumber, merchantName, merchantAccountId, amount, timestamp, status)"
                            + " values (?, ?, ?, ?, ?, ?, ?, ?)";

                    // create the mysql insert prepared statement
                    PreparedStatement preparedStmt = connection.prepareStatement(query);
                    preparedStmt.setString (1, acquirerTransaction.getId());
                    preparedStmt.setString (2, acquirerTransaction.getCustomerName());
                    preparedStmt.setString   (3, acquirerTransaction.getCustomerCardNumber());
                    preparedStmt.setString(4, acquirerTransaction.getMerchantName());
                    preparedStmt.setString    (5, acquirerTransaction.getMerchantAccountId());
                    preparedStmt.setLong(6,acquirerTransaction.getAmount());
                    preparedStmt.setLong(7, acquirerTransaction.getTimestamp());
                    preparedStmt.setString(8, acquirerTransaction.getStatus());

                    // execute the prepared statement
                    preparedStmt.execute();

                    System.out.println("Entry added to the database");
                }
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();

        }


    }
}
