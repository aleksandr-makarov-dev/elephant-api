package com.github.elephant.filesystem.exception;

public class InputStreamException extends RuntimeException {
    public InputStreamException(String message) {
        super(message);
    }

    public InputStreamException( Throwable cause) {
      super(cause);
    }
}
