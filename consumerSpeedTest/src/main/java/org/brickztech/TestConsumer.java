package org.brickztech;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.Properties;

public class TestConsumer {

    private final ConfigReader reader = new ConfigReader();
    KafkaConsumer<String, String> consumer;
    private String topicName;
    private String pollFrequency;

    public void init(String filePath) throws IOException {

        Properties properties = reader.getData(filePath);

        topicName = properties.getProperty("topic");

        consumer = new KafkaConsumer<>(properties);
    }

    void run() {
        Instant start = Instant.now();
        consumer.subscribe(Collections.singleton(topicName));
        int counter = 0;
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(Long.parseLong(pollFrequency)));
                for (ConsumerRecord<String, String> record : records) {
                    counter++;
                    if (counter % 10_000 == 0) {
                        Instant finish = Instant.now();
                        long timeElapsedSec = Duration.between(start, finish).toSeconds();
                        System.out.println("RecordCount: " + counter + " in " + timeElapsedSec + "seconds");
                    }
                }
            }
        } finally {
            consumer.close();
        }
    }

    private void printConsumerConfiguration(Properties properties) {
        System.out.println("Kafka Consumer Configuration:");
        System.out.println("Bootstrap Server: " + properties.getProperty("bootstrapserver"));
        System.out.println("Group ID: " + properties.getProperty("groupid"));
        System.out.println("Offset Reset: " + properties.getProperty("offsetreset"));
        System.out.println("Min Bytes: " + properties.getProperty("minbytes"));
        System.out.println("Max Bytes: " + properties.getProperty("maxbytes"));
        System.out.println("Max Poll Records: " + properties.getProperty("maxpoll"));
        System.out.println("Max Poll Interval (ms): " + properties.getProperty("maxpollintervalms"));
        System.out.println("Max Wait Time (ms): " + properties.getProperty("maxwaitms"));
        System.out.println("Topic Name: " + properties.getProperty("topic"));
        System.out.println("Poll Frequency (ms): " + properties.getProperty("pollFrequency"));
    }

}
