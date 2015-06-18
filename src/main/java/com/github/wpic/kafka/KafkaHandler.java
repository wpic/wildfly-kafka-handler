package com.github.wpic.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

/**
 *
 */
public class KafkaHandler extends Handler {

    private String servers;

    private String topic;

    private Producer<String, String> producer = null;

    private boolean init = false;

    private static final Formatter defaultFormatter = new SimpleFormatter();

    @Override
    public void publish(final LogRecord record) {
        if (ensureReady()) {
            try {
                //insertRecord(record);
                final String msg;
                if (getFormatter() == null) {
                    msg = this.defaultFormatter.format(record);
                } else {
                    msg = getFormatter().format(record);
                }
                this.producer.send(new ProducerRecord<String, String>(this.topic, msg));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    private synchronized boolean ensureReady() {
        if (!init && this.producer == null) {
            try {

                final Properties props = new Properties();
                props.put("bootstrap.servers", this.servers);
                props.put("acks", "all");
                props.put("retries", 0);
                props.put("key.serializer", org.apache.kafka.common.serialization.StringSerializer.class);
                props.put("value.serializer", org.apache.kafka.common.serialization.StringSerializer.class);

                this.producer = new KafkaProducer<String, String>(props);
            }
            catch (Throwable ex) {
                ex.printStackTrace();
            }
            init = true;
        }

        return this.producer != null;
    }

    @Override
    public void flush() {
    }


    @Override
    public void close() {
        if (this.producer != null) {
            try {
                this.producer.close();
                this.producer = null;
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    public void setServers(final String servers) {
        this.servers = servers;
    }

    public void setTopic(final String topic) {
        this.topic = topic;
    }

}
