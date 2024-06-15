package com.nexia.nexus.api.exception;

public class NexusPluginException extends RuntimeException {
    public NexusPluginException(String message) {
        super(message);
    }

    public NexusPluginException(String message, Throwable cause) {
        super(message, cause);
    }
}
