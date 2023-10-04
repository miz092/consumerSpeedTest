package org.brickztech;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.exit(1);
        }
        String externalFilePath = args[0];
        System.out.println(externalFilePath);
        TestConsumer testConsumer = new TestConsumer();
        testConsumer.init(externalFilePath);
        testConsumer.run();

    }
}