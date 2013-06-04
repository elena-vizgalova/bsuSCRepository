package by.epam.yummybook.dao;

/**
 * Exception class for CategoryDAO.
 * @author Elena Vizgalova
 */
public class CategoryDAOException extends DAOException {
    
    public CategoryDAOException() {
    }
    
    public CategoryDAOException( Throwable cause ) {
        super( cause );
    }

    public CategoryDAOException( String message, Throwable cause ) {
        super( message, cause );
    }

    public CategoryDAOException( String message ) {
        super( message );
    }
    
}
