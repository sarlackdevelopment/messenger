package ru.top.programmer.messenger.player;

import java.util.concurrent.BlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.top.programmer.messenger.exception.MessagerException;

public abstract class BasePlayer implements Player {

  private static final Logger log = LoggerFactory.getLogger(BasePlayer.class);

  protected String name;
  protected int outgoingMessageCount;
  protected int incomingMessageCount;
  protected BlockingQueue<String> incomingQueue;
  protected BlockingQueue<String> outgoingQueue;

  public BasePlayer(String name, BlockingQueue<String> incomingQueue,
      BlockingQueue<String> outgoingQueue) {
    this.name = name;
    this.incomingQueue = incomingQueue;
    this.outgoingQueue = outgoingQueue;
  }

  @Override
  public int getOutgoingMessageCount() {
    return outgoingMessageCount;
  }

  @Override
  public int getIncomingMessageCount() {
    return incomingMessageCount;
  }

  protected String receiveMessage() {
    log.info("Player ({}) receiving message from consumer", name);
    String incomingMessage = takeMessage();
    log.info("Player ({}) received message = {}", name, incomingMessage);
    return incomingMessage;
  }

  private String takeMessage() {
    try {
      return incomingQueue.take();
    } catch (InterruptedException e) {
      String error = String
          .format("Couldn't receive message from consumer. Exception message : %s", e.getMessage());
      log.error(error);
      throw new MessagerException(error);
    }
  }
}
