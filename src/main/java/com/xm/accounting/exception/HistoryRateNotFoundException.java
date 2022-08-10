package com.xm.accounting.exception;

public class HistoryRateNotFoundException extends RuntimeException {
    public HistoryRateNotFoundException() {
        super();
    }

    public HistoryRateNotFoundException(String message) {
        super(message);
    }

    public HistoryRateNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public HistoryRateNotFoundException(Throwable cause) {
        super(cause);
    }

    protected HistoryRateNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
