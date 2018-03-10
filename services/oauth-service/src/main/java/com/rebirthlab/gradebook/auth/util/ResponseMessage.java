package com.rebirthlab.gradebook.auth.util;

import java.time.Instant;

/**
 * Created by Anastasiy
 */
public class ResponseMessage {

    private final Instant timestamp;
    private final int status;
    private final String error;
    private final String message;

    public ResponseMessage(Instant timestamp, int status, String error, String message) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
