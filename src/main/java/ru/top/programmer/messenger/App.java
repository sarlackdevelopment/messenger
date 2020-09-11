package ru.top.programmer.messenger;

import ru.top.programmer.messenger.player.BasePlayer;
import ru.top.programmer.messenger.player.Player;

import java.util.concurrent.LinkedBlockingDeque;

public class App {

  public static void main(String[] args) {
    // I think we should use BlockingQueue. That's because we have known about small amount of players
    LinkedBlockingDeque<String> messagesQueue = new LinkedBlockingDeque<>(Constants.MAX_COUNT_MESSAGES);

    Player producer = new BasePlayer(Constants.PRODUCER_NAME, messagesQueue);
    Player consumer = new BasePlayer(Constants.CONSUMER_NAME, messagesQueue);

    new Broker(producer, consumer).exchangeMessage();
  }
}
