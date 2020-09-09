package ru.top.programmer.messenger.player;

import java.util.concurrent.BlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.top.programmer.messenger.exception.MessagerException;

/**
 * Final implementation of interface Player. Somebody who will be send messages
 */
public class Producer extends BasePlayer {

  private static final Logger log = LoggerFactory.getLogger(Producer.class);

  public Producer(String name, BlockingQueue<String> incomingQueue,
      BlockingQueue<String> outgoingQueue) {
    super(name, incomingQueue, outgoingQueue);
  }

  @Override
  public void startMessaging() {
    sendMessage();
    receiveMessage();
  }

  private void sendMessage() {
    outgoingMessageCount++;
    log.info("Player ({}) sending message № {}", name, outgoingMessageCount);
    putMessage();
    log.info("Player ({}) sent message № {}", name, outgoingMessageCount);
  }

  private void putMessage() {
    try {
      outgoingQueue.put("Hi, bro. I'm not very intrusive?");
    } catch (InterruptedException e) {
      String error = String
          .format("Couldn't sent message № %s. Exception message : %s", outgoingMessageCount,
              e.getMessage());
      log.error("Couldn't sent message № {}", error, e);
      throw new MessagerException(error);
    }
  }
}
