package de.iv.manager.exceptions;

public class UnknownTextChannelException extends Exception {

    @Override
    public String getMessage() {
        return "Given text channel does not exist.";
    }
}
