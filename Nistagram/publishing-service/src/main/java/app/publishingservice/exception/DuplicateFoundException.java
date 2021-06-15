package app.publishingservice.exception;

public class DuplicateFoundException extends RuntimeException {
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
