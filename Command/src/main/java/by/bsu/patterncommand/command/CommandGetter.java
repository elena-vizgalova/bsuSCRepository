package by.bsu.patterncommand.command;

import java.util.HashMap;
import java.util.Map;

/**
 * <tt>CommandGetter</tt> is class, that contains all commands in map <tt>commands</tt>
 * and can give concrete command by name.
 * 
 * @author Elena Vizgalova
 */
public class CommandGetter {
    
    private static CommandGetter instance;
    
    /**
     * Map that contains all application's commands.
     * A {@code key } is command's name and a {@code value } is concrete command,
     * that realizes interface {@link Commandable}.
     */
    private Map<String, Command> commands;
    
    private static final String CLEAR_CART_COMMAND_NAME = "/pages/clearCart";
    private static final String ADD_TO_CART_COMMAND_NAME = "addToCart";
    private static final String CHECKOUT_COMMAND_NAME = "checkout";
    
    /**
     * Fills <tt>commands</tt> map.
     */
    private CommandGetter() {
       commands = new HashMap<>();
       
       commands.put( CLEAR_CART_COMMAND_NAME, ClearCartCommand.getInstance() );
       commands.put( ADD_TO_CART_COMMAND_NAME, AddToCartCommand.getInstance() );
       commands.put( CHECKOUT_COMMAND_NAME, CheckoutCommand.getInstance() );
       commands.put( null, NoCommand.getInstance() );
       
    }
    
    private static void init() {
        instance = new CommandGetter();
    }
    
    public static synchronized CommandGetter getInstance() {
        if ( null == instance ) {
            init();
        }
        return instance;
    }
    
   /**
     * 
     * @param parameterWrapper
     * @return concrete command and NoCommand(), if there are no concrete command
     * @throws ControllerExeption if <tt>parameterWrapper</tt> is <tt>null</tt>
     */
    public Command getCommand( String command, String servletPath ) {
        
        String commandName = getCommandName( command, servletPath );
        
        return commands.get( commandName );
    }
    
    /**
     * Checks if there is command with given name in <tt>commands</tt> map.
     * @param commandName
     * @return {@code true} if exits, {@code false} otherwise
     */
    private boolean isInCommandsMap( String commandName ) {
        return commands.containsKey(commandName);
    }
    
    /**
     * Gets command name from <tt>parameterWrapper</tt>.
     * @param parameterWrapper
     * @return the name of concrete command or {@code null}, if there is no
     * such command in <tt>commands</tt>.
     * @throws ControllerExeption
     */
    private String getCommandName( String commandName, String servletPath ) {
        
        if ( null == commandName ) {
            commandName = servletPath;
        }

        if ( !isInCommandsMap( commandName ) ) {
            return null;
        }

        return commandName;
        
    }
    
}
