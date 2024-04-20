package com.subhajit.sbactivemq.controller;

import com.subhajit.sbactivemq.config.CreateQueuePP;
import com.subhajit.sbactivemq.config.CreateQueuePS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/queue")
public class QueueController {

    @Autowired
    private CreateQueuePP createQueuePP;

    @Autowired
    private CreateQueuePS createQueuePS;

    @GetMapping("/create-pp-queue")
    public String createPPQueue(String queueName){
        try {
            createQueuePP.createQueuePP(queueName);
            return "QUEUE CREATED!!";
        }catch(Exception e){
            e.printStackTrace();
            return "QUEUE CREATION FAILED!!";
        }
    }

    @GetMapping("/create-ps-queue")
    public String createPSQueue(String queueName){
        try {
            createQueuePS.createQueuePS(queueName);
            return "QUEUE CREATED!!";
        }catch(Exception e){
            e.printStackTrace();
            return "QUEUE CREATION FAILED!!";
        }
    }

    @GetMapping("/post-pp-message")
    public String postPpMessage(String message){
        try {
            createQueuePP.send(message);
            return "Message POSTED!!";
        }catch(Exception e){
            e.printStackTrace();
            return "Message POST failed!";
        }
    }

    @GetMapping("/consume-pp-message")
    public String consumePpMessage(){
        try {
            return createQueuePP.receive();
        }catch(Exception e){
            e.printStackTrace();
            return "Message consume failed!";
        }
    }

    @GetMapping("/close-pp-conenction")
    public String closePpConnection(){
        try {
             createQueuePP.close();
            return "Queue closd!";
        }catch(Exception e){
            e.printStackTrace();
            return "Queue close failed!";
        }
    }
}
