package com.subhajit.sbactivemq.listeners;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class PsQueueListener implements MessageListener {

    private String consumerName;
    public PsQueueListener(String consumerName){
        this.consumerName = consumerName;
    }
    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                System.out.println(consumerName + " -----> message received :: " +  textMessage.getText());
            }
        } catch (Exception exp) {
        }

    }
}
