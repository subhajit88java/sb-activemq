package com.subhajit.sbactivemq.config;

import com.subhajit.sbactivemq.listeners.PsQueueListener;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.util.ArrayList;
import java.util.List;

import static javax.jms.Session.AUTO_ACKNOWLEDGE;

@Component
public class CreateTopicPS {
    private static final String CLIENTID = "subhajit-4";
    private String queueName;
    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private Destination destination;
    private MessageProducer producer;
    private List<MessageConsumer> consumerList = new ArrayList<>();

    public void createTopicPS(String queueName) throws Exception {
        // The name of the queue.
        this.queueName = queueName;
        // URL of the JMS server is required to create connection factory.
        // DEFAULT_BROKER_URL is : tcp://localhost:61616 and is indicates that JMS
        // server is running on localhost
        connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_BROKER_BIND_URL);
        // Getting JMS connection from the server and starting it
        connection = connectionFactory.createConnection();
        connection.setClientID(CLIENTID);
        connection.start();
        // Creating a non-transactional session to send/receive JMS message.
        session = connection.createSession(false, AUTO_ACKNOWLEDGE);
        // Destination represents here our queue ’MyFirstActiveMQ’ on the JMS
        // server.
        // The queue will be created automatically on the JSM server if its not already
        // created.
        destination = session.createTopic(this.queueName);
        // MessageProducer is used for sending (producing) messages to the queue.
        producer = session.createProducer(destination);
        // MessageConsumer is used for receiving (consuming) messages from the queue.
        MessageConsumer consumer1 = session.createConsumer(destination);
        consumer1.setMessageListener(new PsQueueListener("CONSUMER1"));
        MessageConsumer consumer2 = session.createConsumer(destination);
        consumer2.setMessageListener(new PsQueueListener("CONSUMER2"));
        MessageConsumer consumer3 = session.createConsumer(destination);
        consumer3.setMessageListener(new PsQueueListener("CONSUMER3"));
        consumerList.add(consumer1);
        consumerList.add(consumer2);
        consumerList.add(consumer3);
    }
}
