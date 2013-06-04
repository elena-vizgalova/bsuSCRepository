package by.epam.yummybook.command;

import by.epam.yummybook.exception.YummyBooksException;

/**
 * Exception for command classes, that implement {@link Commandable} interface.
 * @see ControllerExeption
 * @author Elena Vizgalova
 */
public class CommandException extends YummyBooksException {
    
    public CommandException() {
    }
    
    public CommandException( Throwable cause ) {
        super( cause );
    }

    public CommandException( String message, Throwable cause ) {
        super( message, cause );
    }

    public CommandException( String message ) {
        super( message );
    }
    
}
