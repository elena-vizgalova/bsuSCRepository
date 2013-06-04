package by.epam.yummybook.dao;

/**
 * Exception class for WarehouseDAO classes.
 * @author Elena Vizgalova
 */
public class WarehouseDAOException extends DAOException {
    
    public WarehouseDAOException() {
    }
    
    public WarehouseDAOException( Throwable cause ) {
        super( cause );
    }

    public WarehouseDAOException( String message, Throwable cause ) {
        super( message, cause );
    }

    public WarehouseDAOException( String message ) {
        super( message );
    }
    
}
