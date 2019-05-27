package by.epam.javawebproject.maksimkosmachev.carrental.model.dao.exception;

public class SuchOrderNotExistsException extends Exception {
    public SuchOrderNotExistsException() {
    }

    public SuchOrderNotExistsException(String message) {
        super(message);
    }

    public SuchOrderNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
