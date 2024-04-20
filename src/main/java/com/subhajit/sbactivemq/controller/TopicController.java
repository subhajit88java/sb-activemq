package com.subhajit.sbactivemq.controller;

import com.subhajit.sbactivemq.config.CreateTopicPP;
import com.subhajit.sbactivemq.config.CreateTopicPS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TopicController {

    @Autowired
    private CreateTopicPP createTopicPP;

    @Autowired
    private CreateTopicPS createTopicPS;

    @GetMapping("/create-pp-topic")
    public String createPPTopic(String queueName){
        try {
            createTopicPP.createTopicPP(queueName);
            return "TOPIC CREATED!!";
        }catch(Exception e){
            e.printStackTrace();
            return "TOPIC CREATION FAILED!!";
        }
    }

    @GetMapping("/create-ps-topic")
    public String createPSTopic(String queueName){
        try {
            createTopicPS.createTopicPS(queueName);
            return "TOPIC CREATED!!";
        }catch(Exception e){
            e.printStackTrace();
            return "TOPIC CREATION FAILED!!";
        }
    }

    @GetMapping("/post-pp-message-topic")
    public String postPpMessage(String message){
        try {
            createTopicPP.send(message);
            return "Message POSTED!!";
        }catch(Exception e){
            e.printStackTrace();
            return "Message POST failed!";
        }
    }

    @GetMapping("/consume-pp-message-topic")
    public String consumePpMessage(){
        try {
            return createTopicPP.receive();
        }catch(Exception e){
            e.printStackTrace();
            return "Message consume failed!";
        }
    }

    @GetMapping("/close-pp-conenction-topic")
    public String closePpConnection(){
        try {
            createTopicPP.close();
            return "Topic closed!";
        }catch(Exception e){
            e.printStackTrace();
            return "Topic close failed!";
        }
    }
}
