package org.brickztech;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import scala.sys.Prop;

import java.util.Map;
import java.util.Properties;

public class TestConsumer {

    private Properties props = TestConsumerConfig.consumerProperties()
KafkaConsumer<String, String> consumer = new KafkaConsumer<>(TestConsumerConfig.consumerProperties());

}
