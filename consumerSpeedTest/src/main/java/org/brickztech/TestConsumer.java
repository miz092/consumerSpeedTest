package org.brickztech;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class TestConsumer {

    private final ConfigReader reader = new ConfigReader();
    KafkaConsumer<String, String> consumer;
    private String topicName;
    private String pollFrequency;

    public void init(String filePath) throws IOException {

        Properties properties = reader.getData(filePath);

        topicName = properties.getProperty("topic.name");
        pollFrequency = properties.getProperty("poll.frequency");
        printConsumerConfig(properties);

        consumer = new KafkaConsumer<>(properties);
    }

    void run() {
        Instant start = Instant.now();
        consumer.subscribe(Collections.singleton(topicName));
        Map<String, Integer> appNameCounts = new HashMap<>();
        int counter = 0;
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(Long.parseLong(pollFrequency)));
                for (ConsumerRecord<String, String> record : records) {
                    counter++;
                    String jsonValue = record.value();

                    JsonNode rootNode = new ObjectMapper().readTree(jsonValue);
                    String appName = rootNode.path("APP_NAME").asText();

                    appNameCounts.put(appName, appNameCounts.getOrDefault(appName, 0) + 1);
                    System.out.println(appNameCounts);

                    if (counter % 10_000 == 0) {
                        Instant finish = Instant.now();
                        long timeElapsedSec = Duration.between(start, finish).toSeconds();
                        System.out.println("RecordCount: " + counter + " in " + timeElapsedSec + " seconds");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            consumer.close();
        }
    }


    public void printConsumerConfig(Properties properties) {
        System.out.println("Kafka Consumer Configuration:");
        System.out.println("bootstrap.servers: " + properties.getProperty("bootstrap.servers"));
        System.out.println("key.deserializer: " + properties.getProperty("key.deserializer"));
        System.out.println("value.deserializer: " + properties.getProperty("value.deserializer"));
        System.out.println("group.id: " + properties.getProperty("group.id"));
        System.out.println("auto.offset.reset: " + properties.getProperty("auto.offset.reset"));
        System.out.println("fetch.min.bytes: " + properties.getProperty("fetch.min.bytes"));
        System.out.println("fetch.max.bytes: " + properties.getProperty("fetch.max.bytes"));
        System.out.println("max.poll.records: " + properties.getProperty("max.poll.records"));
        System.out.println("max.poll.interval.ms: " + properties.getProperty("max.poll.interval.ms"));
        System.out.println("fetch.max.wait.ms: " + properties.getProperty("fetch.max.wait.ms"));
        System.out.println("**************************************************************************");
        System.out.println("Kafka Topic Configuration:");
        System.out.println("topic.name: " + properties.getProperty("topic.name"));
        System.out.println("poll.frequency: " + properties.getProperty("poll.frequency"));
    }
}





