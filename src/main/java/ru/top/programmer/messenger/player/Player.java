package ru.top.programmer.messenger.player;

/**
 * Interface represents participants of message exchange
 */
public interface Player {

  /**
   * Outgoing messages counter
   * @return int - outgoing messages quantity
   */
  int getOutgoingMessageCount();

  /**
   * Incoming messages counter
   * @return int - incoming messages quantity
   */
  int getIncomingMessageCount();

  /**
   * Executing messaging process
   */
  void startMessaging();
}
