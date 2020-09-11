package ru.top.programmer.messenger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import ru.top.programmer.messenger.player.Player;

/**
 * Messages broker. This functionality allow to do exchange messages between players
 */
public class Broker {

  private Player producer;
  private Player consumer;

  public Broker(Player producer, Player consumer) {
    this.producer = producer;
    this.consumer = consumer;
  }

  public void exchangeMessage() {
    ExecutorService producerExecutor = Executors.newSingleThreadExecutor();
    ExecutorService consumerExecutor = Executors.newSingleThreadExecutor();

    producerExecutor.submit(() -> {
      while (true) {
        if (producer.IsInterruptConditionMet()) break;
        producer.startMessaging();
        try {
          Thread.sleep(500);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });
    consumerExecutor.submit(() -> {
      while (true) {
        if (consumer.IsInterruptConditionMet()) break;
        consumer.startMessaging();
        try {
          Thread.sleep(500);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });

    producerExecutor.shutdown();
    consumerExecutor.shutdown();
  }
}
