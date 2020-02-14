package com.armakuni.event_sourcing_example.errors;

public class InvalidEvent extends RuntimeException {
    public InvalidEvent(String message) {
        super(message);
    }
}
