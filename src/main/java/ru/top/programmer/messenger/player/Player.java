package ru.top.programmer.messenger.player;

public interface Player {

  int getOutgoingMessageCount();

  int getIncomingMessageCount();

  void startMessaging();
}
