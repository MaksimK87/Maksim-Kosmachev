package by.epam.javawebproject.maksimkosmachev.carrental.model.exception;

public class SuchUserExistsException extends Exception {

    public SuchUserExistsException() {
    }

    public SuchUserExistsException(String message) {
        super(message);
    }

    public SuchUserExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
