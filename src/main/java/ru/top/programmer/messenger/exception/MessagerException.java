package ru.top.programmer.messenger.exception;

/**
 * Custom exception for exchange messages
 */
public class MessagerException extends RuntimeException {
  public MessagerException(String message) {
    super(message);
  }
}
