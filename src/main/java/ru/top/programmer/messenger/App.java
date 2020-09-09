package ru.top.programmer.messenger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import ru.top.programmer.messenger.player.Consumer;
import ru.top.programmer.messenger.player.Player;
import ru.top.programmer.messenger.player.Producer;

public class App {


  public static void main(String[] args) {
    // I think we should use BlockingQueue. That's because we have known about small amount of players
    BlockingQueue<String> incomingQueue = new LinkedBlockingQueue<>(10);
    BlockingQueue<String> outgoingQueue = new LinkedBlockingQueue<>(10);
    Player producer = new Producer("Producer", incomingQueue, outgoingQueue);
    Player consumer = new Consumer("Consumer", outgoingQueue, incomingQueue);
    new MessageExchanger(producer, consumer).exchangeMessage();
  }
}
