package ru.top.programmer.messenger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import ru.top.programmer.messenger.player.Player;

/**
 * Messages broker. This functionality allow to do exchange messages between players
 */
public class MessageExchanger {

  private Player producer;
  private Player consumer;

  public MessageExchanger(Player producer, Player consumer) {
    this.producer = producer;
    this.consumer = consumer;
  }

  public void exchangeMessage() {
    ExecutorService producerExecutor = Executors.newSingleThreadExecutor();
    ExecutorService consumerExecutor = Executors.newSingleThreadExecutor();
    producerExecutor.submit(() -> {
      while (producer.getOutgoingMessageCount() < Constants.MAX_COUNT_OUTGOING_MESSAGES) {
        producer.startMessaging();
      }
    });
    consumerExecutor.submit(() -> {
      while (consumer.getIncomingMessageCount() < Constants.MAX_COUNT_INCOMING_MESSAGES) {
        consumer.startMessaging();
      }
    });
    producerExecutor.shutdown();
    consumerExecutor.shutdown();
  }
}
