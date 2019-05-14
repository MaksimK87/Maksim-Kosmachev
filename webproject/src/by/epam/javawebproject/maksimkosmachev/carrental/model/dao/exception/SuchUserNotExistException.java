package by.epam.javawebproject.maksimkosmachev.carrental.model.dao.exception;

public class SuchUserNotExistException extends Exception {
    public SuchUserNotExistException() {
    }

    public SuchUserNotExistException(String message) {
        super(message);
    }

    public SuchUserNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
