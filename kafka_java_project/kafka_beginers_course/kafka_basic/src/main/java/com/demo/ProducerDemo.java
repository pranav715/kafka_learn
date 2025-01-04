package com.demo;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ProducerDemo {

    private static final Logger log = LoggerFactory.getLogger(ProducerDemo.class.getSimpleName());

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
        //set producer properties
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());

        //create producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        //producer record
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("java_demo", "Hello");

        //send data
        producer.send(producerRecord, new Callback(){

            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                log.info("received metadata for topic = " + recordMetadata.topic());
            }
        });

        //send all data by producer
        producer.flush();

        //flush and close the producer
        producer.close();
    }
}
