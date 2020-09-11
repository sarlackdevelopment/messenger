package ru.top.programmer.messenger.player;

import java.util.concurrent.LinkedBlockingDeque;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.top.programmer.messenger.Constants;
import ru.top.programmer.messenger.exception.MessagerException;

/**
 * Abstract implementation of interface Player. Somebody who will be receive or send messages
 */
public class BasePlayer implements Player {

  private static final Logger log = LoggerFactory.getLogger(BasePlayer.class);

  private String name;
  private LinkedBlockingDeque<String> messagesQueue;

  public BasePlayer(String name, LinkedBlockingDeque<String> messagesQueue) {
    this.name = name;
    this.messagesQueue = messagesQueue;
  }

  @Override
  public boolean IsInterruptConditionMet() {
    return this.messagesQueue.size() == Constants.MAX_COUNT_MESSAGES;
  }

  @Override
  public void startMessaging() {

    Integer sizeOfQueue = this.messagesQueue.size();

    boolean canProducerSendMessage = (this.name.equals(Constants.PRODUCER_NAME) && (sizeOfQueue % 2 == 0));
    boolean canConsumerSendMessage = (this.name.equals(Constants.CONSUMER_NAME) && (sizeOfQueue % 2 != 0));

    try {

      if (canProducerSendMessage || canConsumerSendMessage) {
        String currentMessage = messagesQueue.peekLast();

        String newMessage = String.format("%s %s", 
                (currentMessage == null ? Constants.RANDOM_MESSAGE : currentMessage), sizeOfQueue.toString());
        messagesQueue.put(newMessage);

        String action = canProducerSendMessage ? "sent" : "received";
        log.info("Message: \"{}\" was \"{}\" by player \"{}\"", newMessage, action, this.name);
      }
    } catch (InterruptedException e) {
      String error = String
              .format("Couldn't sent message № %s. Exception message : %s", this.messagesQueue,
                      e.getMessage());
      log.error("Couldn't sent message № {}", error, e);
      throw new MessagerException(error);
    }
  }

}
