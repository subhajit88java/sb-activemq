package com.subhajit.sbactivemq.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.stereotype.Component;

import javax.jms.*;

import static javax.jms.Session.AUTO_ACKNOWLEDGE;
import static org.apache.activemq.ActiveMQSession.INDIVIDUAL_ACKNOWLEDGE;

@Component
public class CreateTopicPP {

    private static final String CLIENTID = "subhajit-3";
    private String topicName;
    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private Destination destination;
    private MessageProducer producer;
    private MessageConsumer consumer;

    public void createTopicPP(String topicName) throws Exception {
        // The name of the queue.
        this.topicName = topicName;
        // URL of the JMS server is required to create connection factory.
        // DEFAULT_BROKER_URL is : tcp://localhost:61616 and is indicates that JMS
        // server is running on localhost
        connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_BROKER_BIND_URL);
        // Getting JMS connection from the server and starting it
        connection = connectionFactory.createConnection();
        connection.setClientID(CLIENTID);
        connection.start();
        // Creating a non-transactional session to send/receive JMS message.
        session = connection.createSession(false, INDIVIDUAL_ACKNOWLEDGE);
        // Destination represents here our queue ’MyFirstActiveMQ’ on the JMS
        // server.
        // The queue will be created automatically on the JSM server if its not already
        // created.
        destination = session.createTopic(this.topicName);
        // MessageProducer is used for sending (producing) messages to the queue.
        producer = session.createProducer(destination);
        // MessageConsumer is used for receiving (consuming) messages from the queue.
        consumer = session.createConsumer(destination);
    }

    public void send(String textMessage)
            throws Exception {
        // We will send a text message
        TextMessage message = session.createTextMessage(textMessage);
        // push the message into queue
        producer.send(message);
        System.out.println("Message has been pushed to topic!!");
    }

    public String receive() throws Exception {

        String msg = "";
        // receive the message from the queue.
        Message message = consumer.receive();
        message.acknowledge();
        // Since We are using TestMessage in our example. MessageProducer sent us a TextMessage
        // So we need cast to it to get access to its getText() method which will give us the text of the message
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            System.out.println("Message received by consumer :: " + textMessage.getText());
            msg = textMessage.getText();
        }
        return msg;
    }


    public void close() throws JMSException {
        producer.close();
        producer = null;
        consumer.close();
        consumer = null;
        session.close();
        session = null;
        connection.close();
        connection = null;
    }
}
