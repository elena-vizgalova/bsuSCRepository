package by.epam.yummybook.dao;

import by.epam.yummybook.exception.YummyBooksException;

/**
 * Basic class for Exception's classes in DAO.
 * @author Alena_Vizgalava
 */
public class DAOException extends YummyBooksException {
    
    public DAOException() {
    }
    
    public DAOException(Throwable cause) {
        super(cause);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOException(String message) {
        super(message);
    }
    
}
