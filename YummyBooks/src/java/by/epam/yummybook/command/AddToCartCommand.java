package by.epam.yummybook.command;

import by.epam.yummybook.command.logger.CommandLogger;
import by.epam.yummybook.command.paramname.IParameterName;
import by.epam.yummybook.controller.ParameterWrapper;
import by.epam.yummybook.controller.ParameterWrapperException;
import by.epam.yummybook.entity.Cart;
import by.epam.yummybook.entity.Customer;
import by.epam.yummybook.entity.CustomerOrder;
import by.epam.yummybook.entity.EntityException;
import by.epam.yummybook.entity.LoginInfo;
import by.epam.yummybook.logic.AddToCartLogic;
import by.epam.yummybook.logic.LogicException;
import by.epam.yummybook.manager.MessageManager;
import by.epam.yummybook.manager.PageAddressManager;

/**
 * It's singleton class, that processes functionality of adding products to cart.
 * @see Commandable
 * @author Elena Vizgalova
 */
class AddToCartCommand implements Commandable {
    
    private static AddToCartCommand instance;
    
    /**
     * By default it's possible to add one book into a cart.
     */
    private final static int DEFAULT_BOOK_AMOUNT = 1;
 
    private AddToCartCommand() {
    }
    
    public static synchronized AddToCartCommand getInstance() {
        if ( null == instance ) {
            instance = new AddToCartCommand();
        }
        return instance;
    }
    
    /**
     * Uses {@link AddToCartLogic } for adding to cart.
     * If a user doesn't login, it forwards request to login page.
     * If there is an order session parameter, it gets it from DB or creates new
     * and after it adds book in <tt>order</tt> and <tt>cart</tt> bean.
     * If there are not enough books in warehouse, it writes error message on jsp page.
     * @param parameterWrapper
     * @return page to go
     * @throws CommandException
     * 
     * @see Commandable
     * @see AddToCartLogic
     */
    @Override
    public String execute( ParameterWrapper parameterWrapper ) throws CommandException {
        try {
            
            AddToCartLogic addToCartLogic = AddToCartLogic.getInstance();
            
            LoginInfo loginInfo = (LoginInfo) parameterWrapper.
                    getSessionAttribute( IParameterName.LOGIN_INFO );
            
            if ( null == loginInfo ) {
                PageAddressManager configurationManager = PageAddressManager.getInstance();
                return configurationManager.getProperty( PageAddressManager.LOGIN_PAGE );
            }
            
            CustomerOrder order = (CustomerOrder) parameterWrapper.getSessionAttribute( 
                    IParameterName.CUSTOMER_ORDER );
            
            if ( null == order ) {
                order = addToCartLogic.getCustomerOrder( loginInfo );
            }
            
            if ( 0 == order.getId() ) {
                Customer customer = addToCartLogic.getCustomer( loginInfo.getUsername() );
                order = addToCartLogic.createNewOrder( customer.getId() );
            }

            String bookID = (String) parameterWrapper.getRequestParam( IParameterName.BOOK_ID );
            
            MessageManager messageManager = MessageManager.getInstance();
            
            String errorMessage;
            if ( !addToCartLogic.isExistsBookOnWarehouse( bookID ) ) {
                errorMessage = messageManager.getProperty( MessageManager.ERROR_NO_SUCH_BOOK );
                parameterWrapper.setRequestParam( IParameterName.ERROR , errorMessage );
                
                PageAddressManager configurationManager = PageAddressManager.getInstance();
                
                return configurationManager.getProperty( PageAddressManager.CATEGORY_PAGE );
            } else if ( 0 > ( addToCartLogic.getBookAmountInWarehouse(bookID) - DEFAULT_BOOK_AMOUNT) ) {
                int amountInWarehouse = addToCartLogic.getBookAmountInWarehouse(bookID);
                if ( 0 == amountInWarehouse ) {
                    errorMessage = messageManager.getProperty( MessageManager.ERROR_NO_SUCH_BOOK );
                } else if ( 1 == amountInWarehouse ) {
                    errorMessage = messageManager.getProperty( MessageManager.ERROR_ONE_BOOK );
                } else {
                    errorMessage = messageManager.getProperty( MessageManager.ERROR_BOOK_AMOUNT );
                    errorMessage = errorMessage.replace( MessageManager.AMOUNT_TO_CHANGE , 
                            Integer.toString( amountInWarehouse ) );
                }
                
                parameterWrapper.setRequestParam( IParameterName.BOOK_WITH_ERROR, bookID );
                parameterWrapper.setRequestParam( IParameterName.ERROR , errorMessage );

                PageAddressManager configurationManager = PageAddressManager.getInstance();
                return configurationManager.getProperty( PageAddressManager.CATEGORY_PAGE );
            }
            
            order = addToCartLogic.addBookInOrder( order, bookID );
            
            Cart cart = new Cart( order );
            
            parameterWrapper.setSessionAttribute( IParameterName.CART, cart );
            parameterWrapper.setSessionAttribute( IParameterName.CUSTOMER_ORDER, order );
            
            PageAddressManager configurationManager = PageAddressManager.getInstance();
            return configurationManager.getProperty( PageAddressManager.CATEGORY_PAGE );
        } catch (EntityException ex) {
            CommandLogger logger = CommandLogger.getInstance( AddToCartCommand.class );
            logger.error( "Error creating cart from order", ex );
            throw new CommandException( ex );
        } catch (ParameterWrapperException ex) {
            CommandLogger logger = CommandLogger.getInstance( AddToCartCommand.class );
            logger.error( ex );
            throw new CommandException( ex );
        } catch (LogicException ex) {
            CommandLogger logger = CommandLogger.getInstance( AddToCartCommand.class );
            logger.error( ex );
            throw new CommandException( ex );
        }
    }
    
}