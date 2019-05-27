package by.epam.javawebproject.maksimkosmachev.carrental.model.dao.exception;

public class UserIllegalValueException extends Exception {

    public UserIllegalValueException() {
    }

    public UserIllegalValueException(String message) {
        super(message);
    }

    public UserIllegalValueException(String message, Throwable cause) {
        super(message, cause);
    }
}
