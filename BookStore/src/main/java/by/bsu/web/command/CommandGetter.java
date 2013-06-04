package by.bsu.web.command;

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
    private Map<String, Commandable> commands;
    
    private static final String SHOW_CATEGORIES_COMMAND_NAME = "/showCategories";
    
    /**
     * Fills <tt>commands</tt> map.
     */
    private CommandGetter() {
       commands = new HashMap<String, Commandable>();
       
       commands.put( SHOW_CATEGORIES_COMMAND_NAME, new ShowCategoriesCommand());
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
    public Commandable getCommand( String servletPath ) {
        
        return commands.get( servletPath );
    }
    
}
