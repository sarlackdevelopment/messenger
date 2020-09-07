package ru.top.programmer.messenger.player;

import java.util.concurrent.BlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.top.programmer.messenger.exception.MessagerException;

public class Consumer extends BasePlayer {

  private static final Logger log = LoggerFactory.getLogger(BasePlayer.class);

  public Consumer(String name, BlockingQueue<String> incomingQueue,
      BlockingQueue<String> outgoingQueue) {
    super(name, incomingQueue, outgoingQueue);
  }

  @Override
  public void startMessaging() {
    receive();
  }

  private void receive() {
    incomingMessageCount++;
    log.info("Player ({}) receiving message from consumer", name);
    String incomingMessage = receiveMessage();
    sendMessage(incomingMessage + " " + incomingMessageCount);
    log.info("Player ({}) received message = {}", name, incomingMessage);
  }

  private void sendMessage(String message) {
    try {
      outgoingQueue.put(message);
    } catch (InterruptedException e) {
      String error = String
          .format("Couldn't sent message № %s. Exception message : %s", outgoingMessageCount,
              e.getMessage());
      log.error("Couldn't sent message № {}", error, e);
      throw new MessagerException(error);
    }
  }
}
