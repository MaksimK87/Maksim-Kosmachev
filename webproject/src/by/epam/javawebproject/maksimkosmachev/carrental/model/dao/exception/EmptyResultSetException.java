package by.epam.javawebproject.maksimkosmachev.carrental.model.dao.exception;

public class EmptyResultSetException extends Exception {
    public EmptyResultSetException() {
    }

    public EmptyResultSetException(String message) {
        super(message);
    }
}
