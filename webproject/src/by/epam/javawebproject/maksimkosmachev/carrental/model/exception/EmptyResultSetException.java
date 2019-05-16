package by.epam.javawebproject.maksimkosmachev.carrental.model.exception;

public class EmptyResultSetException extends Exception {
    public EmptyResultSetException() {
    }

    public EmptyResultSetException(String message) {
        super(message);
    }
}
