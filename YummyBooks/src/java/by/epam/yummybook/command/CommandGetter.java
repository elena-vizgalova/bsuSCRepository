package by.epam.yummybook.command;

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
    
    private static final String LOAD_CATEGORIES_COMMAND = "/home";
    private static final String CHOOSE_LANGUAGE_COMMAND_NAME = "/chooseLanguage";
    private static final String PAGES_CHOOSE_LANGUAGE_COMMAND_NAME = "/pages/chooseLanguage";
    private static final String ADMIN_CHOOSE_LANGUAGE_COMMAND_NAME = "/pages/admin/chooseLanguage";
    private static final String CATEGORY_COMMAND_NAME = "/pages/category";
    private static final String CLEAR_CART_COMMAND_NAME = "/pages/clearCart";
    private static final String LOGINATION_COMMAND_NAME = "logination";
    private static final String LOGOUT_COMMAND_NAME = "logout";
    private static final String ADD_TO_CART_COMMAND_NAME = "addToCart";
    private static final String UDATE_CART_COMMAND_NAME = "updateCart";
    private static final String CHECKOUT_COMMAND_NAME = "checkout";
    private static final String BLACK_LIST_CHANGE_COMMAND_NAME = "blackListChange";
    private static final String IS_PAYED_CHANGE_COMMAND_NAME = "isPayedChange";
    private static final String VIEW_CUSTOMERS_COMMAND_NAME = "viewCustomers";
    private static final String VIEW_ORDERS_COMMAND_NAME = "viewOrders";
    
    /**
     * Fills <tt>commands</tt> map.
     */
    private CommandGetter() {
       commands = new HashMap<>();
       
       commands.put( CHOOSE_LANGUAGE_COMMAND_NAME, SetLanguageCommand.getInstance() );
       commands.put( PAGES_CHOOSE_LANGUAGE_COMMAND_NAME, SetLanguageCommand.getInstance() );
       commands.put( ADMIN_CHOOSE_LANGUAGE_COMMAND_NAME, SetLanguageCommand.getInstance() );
       commands.put( CATEGORY_COMMAND_NAME, CategoryCommand.getInstance() );
       commands.put( CLEAR_CART_COMMAND_NAME, ClearCartCommand.getInstance() );
       commands.put( LOGINATION_COMMAND_NAME, LoginCommand.getInstance() );
       commands.put( ADD_TO_CART_COMMAND_NAME, AddToCartCommand.getInstance() );
       commands.put( UDATE_CART_COMMAND_NAME, UpdateCartCommand.getInstance() );
       commands.put( CHECKOUT_COMMAND_NAME, CheckoutCommand.getInstance() );
       commands.put( BLACK_LIST_CHANGE_COMMAND_NAME, ChangeBlackListFlagCommand.getInstance() );
       commands.put( IS_PAYED_CHANGE_COMMAND_NAME, ChangeIsPayedOrderFlagCommand.getInstance() );
       commands.put( LOAD_CATEGORIES_COMMAND, HomepageCommand.getInstance() );
       commands.put(LOGOUT_COMMAND_NAME, LogoutCommand.getInstance() );
       commands.put( VIEW_CUSTOMERS_COMMAND_NAME, ViewCustomersCommand.getInstance() );
       commands.put( VIEW_ORDERS_COMMAND_NAME, ViewOrdersCommand.getInstance() );
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
    public Commandable getCommand( String command, String servletPath ) {
        
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
