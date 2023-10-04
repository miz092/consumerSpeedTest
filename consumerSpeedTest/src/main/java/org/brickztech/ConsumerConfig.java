package org.brickztech;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;

import java.util.Map;

public class ConsumerConfig extends AbstractConfig {


    public static final String TEST_BOOTSTRAP_SERVER = "test.kafka.bootstrap";
    public static final String TEST_BOOTSTRAP_SERVER_DEFAULT_VALUE = "ezdf02.hpe4d.local:9092";
    public static final String TEST_BOOTSTRAP_SERVER_DOC = "Define sink bootstrap";


    public static ConfigDef CONFIG = new ConfigDef()
            .define(TEST_BOOTSTRAP_SERVER, ConfigDef.Type.STRING, TEST_BOOTSTRAP_SERVER_DEFAULT_VALUE, ConfigDef.Importance.HIGH, TEST_BOOTSTRAP_SERVER_DOC);

    public ConsumerConfig(Map<String, String> props) {
        super(CONFIG, props);
    }

    public String getTestBootstrapServer(){
        return getString(TEST_BOOTSTRAP_SERVER);
    }
}
