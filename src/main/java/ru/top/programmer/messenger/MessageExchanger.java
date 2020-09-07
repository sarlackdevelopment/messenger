package ru.top.programmer.messenger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import ru.top.programmer.messenger.player.Player;

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
      while (producer.getOutgoingMessageCount() < 10) {
        producer.startMessaging();
      }
    });
    consumerExecutor.submit(() -> {
      while (consumer.getIncomingMessageCount() < 10) {
        consumer.startMessaging();
      }
    });
    producerExecutor.shutdown();
    consumerExecutor.shutdown();
  }
}
