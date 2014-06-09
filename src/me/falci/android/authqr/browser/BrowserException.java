package me.falci.android.authqr.browser;

public class BrowserException extends Exception {
    private static final long serialVersionUID = 1L;

    public BrowserException() {
            super();
    }

    public BrowserException(String detailMessage, Throwable throwable) {
            super(detailMessage, throwable);
    }

    public BrowserException(String detailMessage) {
            super(detailMessage);
    }

    public BrowserException(Throwable throwable) {
            super(throwable);
    }

}
