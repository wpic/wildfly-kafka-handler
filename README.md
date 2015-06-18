# How to use it?

1. First setup Kafka module from here:  https://github.com/wpic/wildfly-kafka-module.
2. Then setup log handler module from this repo (copy to module folder of your Wildfly/JBoss).
3. Add handler to ```standalone/configuration/standalone.xml``` (or another configuration you use)
```xml
<profile>
    <subsystem xmlns="urn:jboss:domain:logging:2.0">
        ...
        <!-- Change pattern, server and topic -->
        <custom-handler name="Kafka" class="com.github.wpic.kafka.KafkaHandler" module="com.github.wpic.kafka">
            <formatter>
                <named-formatter name="PATTERN"/>
            </formatter>
            <properties>
                <property name="servers" value="127.0.0.1:9092"/>
                <property name="topic" value="test"/>
            </properties>
        </custom-handler>
        <!-- You have to add this because the logger can not append to log itself -->
        <logger category="org.apache.kafka.clients" use-parent-handlers="true">
            <level name="OFF"/>
        </logger>
        <!-- Add it to the root handler also if you want -->
        <root-logger>
            <level name="INFO"/>
            <handlers>
                ...
                <handler name="Kafka"/>
            </handlers>
        </root-logger>
        ...
```

You can now see/store all (of part of) the Wildfly/JBoss logs on you Kafka.