package com.github.wpic.kafka;


import java.util.logging.Logger;

/**
 *
 */
public class HandlerTest {

    private static final Logger logger = Logger.getLogger(HandlerTest.class.getSimpleName());

    public static void main(final String[] args) {
        final KafkaHandler handler = new KafkaHandler();
        handler.setServers("127.0.0.1:9092");
        handler.setTopic("test");
        logger.addHandler(handler);

        logger.info("Hello kafka");

        handler.close();
    }

}
