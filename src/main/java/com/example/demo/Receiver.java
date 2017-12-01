package com.example.demo;

import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * Created by jerry on 2017/12/1.
 * With any messaging-based application, you need to create a receiver that will respond to published messages.
 */
@Component
public class Receiver {

    /**
     * http://www.importnew.com/15731.html
     * http://www.itzhai.com/the-introduction-and-use-of-a-countdownlatch.html#read-more
     */
    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String message) {
        System.out.println(
            "========================================= \n" +
                "Received: " + message + "\n" +
                "========================================= \n");
    }


    public CountDownLatch getLatch() {
        return latch;
    }
}
