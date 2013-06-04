package by.epam.yummybook.command;

import by.epam.yummybook.command.logger.CommandLogger;
import by.epam.yummybook.command.paramname.IParameterName;
import by.epam.yummybook.controller.ParameterWrapper;
import by.epam.yummybook.entity.Cart;
import by.epam.yummybook.entity.Customer;
import by.epam.yummybook.entity.CustomerOrder;
import by.epam.yummybook.entity.EntityException;
import by.epam.yummybook.controller.ParameterWrapperException;
import by.epam.yummybook.logic.CheckoutLogic;
import by.epam.yummybook.logic.LogicException;
import by.epam.yummybook.manager.MessageManager;
import by.epam.yummybook.manager.PageAddressManager;

/**
 * It's singleton class, that processes checkout.
 * @author Elena Vizgalova
 */
public class CheckoutCommand implements Commandable {
    
    private static CheckoutCommand instance;

    private CheckoutCommand() {
    }
    
    public static synchronized CheckoutCommand getInstance() {
        if ( null == instance ) {
            instance = new CheckoutCommand();
        }
        return instance;
    }
    
    /**
     * Uses {@link CheckoutLogic }.
     * Gets parameters from <tt>parameterWrapper</tt>, checks them and if they are 
     * not correct sets parameters with error messages. If parameters are correct,
     * writes them in DB.
     * @param parameterWrapper
     * @return page to go
     * @throws CommandException 
     */
    @Override
    public String execute( ParameterWrapper parameterWrapper ) throws CommandException {
        try {
            Customer customer = (Customer) parameterWrapper.getSessionAttribute( IParameterName.CUSTOMER );
            Cart cart = (Cart) parameterWrapper.getSessionAttribute( IParameterName.CART );
            
            String name = (String) parameterWrapper.getRequestParam( IParameterName.CUSTOMER_NAME );
            String email = (String) parameterWrapper.getRequestParam( IParameterName.CUSTOMER_EMAIL );
            String phone = (String) parameterWrapper.getRequestParam( IParameterName.CUSTOMER_PHONE );
            String address = (String) parameterWrapper.getRequestParam( 
                    IParameterName.CUSTOMER_ADDRESS );
            String cityRegion = (String) parameterWrapper.getRequestParam( IParameterName.CUSTOMER_CITYREGION );
            
            boolean hasError = false;
            MessageManager messageManager = MessageManager.getInstance();
            
            CheckoutLogic checkoutLogic = CheckoutLogic.getInstance();
            
            if ( !checkoutLogic.checkName( name ) ) {
                String error = messageManager.getProperty( MessageManager.ERROR_CUSTOMER_NAME );
                parameterWrapper.setRequestParam( IParameterName.ERROR_NAME , error );
                hasError = true;
            }
            if ( !checkoutLogic.checkEmail( email ) ) {
                String error = messageManager.getProperty( MessageManager.ERROR_CUSTOMER_EMAIL );
                parameterWrapper.setRequestParam( IParameterName.ERROR_EMAIL , error );
                hasError = true;
            }
            if ( !checkoutLogic.checkPhone( phone ) ) {
                String error = messageManager.getProperty( MessageManager.ERROR_CUSTOMER_PHONE );
                parameterWrapper.setRequestParam( IParameterName.ERROR_PHONE , error );
                hasError = true;
            }
            if ( !checkoutLogic.checkAddress( address ) ) {
                String error = messageManager.getProperty( MessageManager.ERROR_CUSTOMER_ADDRESS );
                parameterWrapper.setRequestParam( IParameterName.ERROR_ADDRESS , error );
                hasError = true;
            }
            if ( !checkoutLogic.checkCityRegion( cityRegion ) ) {
                String error = messageManager.getProperty( MessageManager.ERROR_CUSTOMER_CITY_REGION );
                parameterWrapper.setRequestParam( IParameterName.ERROR_CITY_REGION , error );
                hasError = true;
            }
            
            if ( hasError ) {
                PageAddressManager configurationManager = PageAddressManager.getInstance();
                return configurationManager.getProperty( PageAddressManager.CHECKOUT_PAGE );
            } else {
                customer.setAddress( address );
                customer.setCityRegion( cityRegion );
                customer.setEmail( email );
                customer.setName( name );
                customer.setPhone( phone );
                
                checkoutLogic.rewriteCustomer( customer, cart );

                parameterWrapper.setSessionAttribute( IParameterName.CUSTOMER_ORDER, new CustomerOrder() );
                parameterWrapper.setSessionAttribute( IParameterName.CART, new Cart() );
                
                PageAddressManager configurationManager = PageAddressManager.getInstance();
                return configurationManager.getProperty( PageAddressManager.SUCCESS_PAGE );
            }
        } catch (LogicException | ParameterWrapperException | EntityException ex) {
            CommandLogger logger = CommandLogger.getInstance(CheckoutCommand.class);
            logger.error( ex );
            throw new CommandException( ex );
        }
    }
    
}
