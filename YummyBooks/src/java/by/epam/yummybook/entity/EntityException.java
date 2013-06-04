package by.epam.yummybook.entity;

/**
 *
 * @author Elena Vizgalova
 */
public class EntityException extends Exception {
    
    public EntityException() {
    }
    
    public EntityException(Throwable cause) {
        super(cause);
    }

    public EntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityException(String message) {
        super(message);
    }
    
}
