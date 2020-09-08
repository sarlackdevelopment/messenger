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
    // Fork/joinPool - Колво ядер процессора. Если много потоков 16 с 8 ядрами, проблемы с перфомансом.
    // ExecutorService - для решения этой проблемы (чтобы не юзать форкджойн). Он выделяет как-то иначе. Создает
    // эти треды минуя Fork/joinPool. Это некий пулл потоков
    ExecutorService producerExecutor = Executors.newSingleThreadExecutor();
    ExecutorService consumerExecutor = Executors.newSingleThreadExecutor();
    producerExecutor.submit(() -> {
      // Все что здесь выполняется в отдельном потоке.
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
