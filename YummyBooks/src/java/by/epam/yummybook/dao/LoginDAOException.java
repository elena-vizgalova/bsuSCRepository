package by.epam.yummybook.dao;

/**
 * Exception class for LoginDAO classes.
 * @author Elena Vizgalova
 */
public class LoginDAOException extends DAOException {
    
    public LoginDAOException() {
    }
    
    public LoginDAOException(Throwable cause) {
        super(cause);
    }

    public LoginDAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginDAOException(String message) {
        super(message);
    }
    
}
