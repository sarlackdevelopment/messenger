package ru.top.programmer.messenger.player;

/**
 * Interface represents participants of message exchange
 */
public interface Player {
  /**
   *
   * @return boolean - condition interrupt messaging thread
   */
  boolean IsInterruptConditionMet();

  /**
   * Executing messaging process
   */
  void startMessaging();
}
