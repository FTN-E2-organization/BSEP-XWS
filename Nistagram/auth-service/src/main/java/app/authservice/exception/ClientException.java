package app.authservice.exception;

//klasa da se ne ispisuje u logging fajlove exception-i
public class ClientException extends RuntimeException {
    public ClientException() {
        super();
    }

    public ClientException(String message)  {
        super(message);
    }

    public ClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientException(Throwable cause) {
        super(cause);
    }
}
