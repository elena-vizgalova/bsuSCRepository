package by.bsu.web.command;

/**
 * Exception for command classes, that implement {@link Commandable} interface.
 * @see ControllerExeption
 * @author Elena Vizgalova
 */
public class CommandException extends Exception {
    
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
