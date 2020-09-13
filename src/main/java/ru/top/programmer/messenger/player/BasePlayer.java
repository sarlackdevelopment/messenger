package ru.top.programmer.messenger.player;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.top.programmer.messenger.Constants;
import ru.top.programmer.messenger.exception.MessagerException;

/**
 * Abstract implementation of interface Player. Somebody who will be receive or send messages
 */
public class BasePlayer implements Player {

  private static final Logger log = LoggerFactory.getLogger(BasePlayer.class);

  private final String name;
  private final LinkedBlockingDeque<String> messagesQueue;
  private final AtomicInteger messageCounter = new AtomicInteger(0);

  public BasePlayer(String name, LinkedBlockingDeque<String> messagesQueue) {
    this.name = name;
    this.messagesQueue = messagesQueue;
  }

  @Override
  public boolean IsInterruptConditionMet() {
    return this.messageCounter.get() == Constants.MAX_COUNT_MESSAGES;
  }

  @Override
  public void startMessaging() {

    int sizeOfQueue = this.messagesQueue.size();

    boolean canProducerSendMessage = (this.name.equals(Constants.PRODUCER_NAME) && (sizeOfQueue % 2 == 0));
    boolean canConsumerSendMessage = (this.name.equals(Constants.CONSUMER_NAME) && (sizeOfQueue % 2 != 0));

    try {

      if (canProducerSendMessage || canConsumerSendMessage) {

        String currentMessage = this.messagesQueue.peekLast();
        String newMessage = String.format("%s %s",
                (currentMessage == null ? Constants.RANDOM_MESSAGE : currentMessage),
                ((Integer) sizeOfQueue).toString());

        this.messagesQueue.put(newMessage);

        this.messageCounter.getAndIncrement();

        String action = canProducerSendMessage ? "sent" : "received";
        log.info("Message: \"{}\" was {} by player \"{}\"", newMessage, action, this.name);
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
