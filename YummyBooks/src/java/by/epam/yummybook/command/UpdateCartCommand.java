package by.epam.yummybook.command;

import by.epam.yummybook.command.logger.CommandLogger;
import by.epam.yummybook.command.paramname.IParameterName;
import by.epam.yummybook.controller.ParameterWrapper;
import by.epam.yummybook.controller.ParameterWrapperException;
import by.epam.yummybook.entity.Cart;
import by.epam.yummybook.entity.CustomerOrder;
import by.epam.yummybook.entity.EntityException;
import by.epam.yummybook.entity.LoginInfo;
import by.epam.yummybook.logic.LogicException;
import by.epam.yummybook.logic.UpdateCartLogic;
import by.epam.yummybook.manager.MessageManager;
import by.epam.yummybook.manager.PageAddressManager;

/**
 * Singleton class for updating cart.
 * @author Elena Vizgalova
 */
class UpdateCartCommand implements Commandable {

    private static UpdateCartCommand instance;

    private UpdateCartCommand() {
    }
    
    public static synchronized UpdateCartCommand getInstance() {
        if ( null == instance ) {
            instance = new UpdateCartCommand();
        }
        return instance;
    }

    /**
     * Uses {@link UpdateCartLogic}.
     * Gets book id and it's quantity from <tt>parameterWrapper</tt>.
     * If quantity is invalid, sets error message and returns error page.
     * If parameters are correct, gets <code>CustomerOrder</code> and <code>Cart</code>
     * objects, modifies them according to book id and it's quantity and 
     * sets new order and cart into <tt>parameterWrapper</tt>
     * @param parameterWrapper
     * @return {@link PageAddressManager#CART_PAGE}
     * @throws CommandException 
     */
    @Override
    public String execute(ParameterWrapper parameterWrapper) throws CommandException {
        try {
            String bookID = (String) parameterWrapper.getRequestParam(
                    IParameterName.BOOK_ID);
            String quantityStr = (String) parameterWrapper.getRequestParam(
                    IParameterName.BOOK_QUANTITY);

            UpdateCartLogic updateCartLogic = UpdateCartLogic.getInstance();
 
            if ( !updateCartLogic.isValidQuantity(quantityStr) ) {
                MessageManager messageManager = MessageManager.getInstance();
                String error = messageManager.getProperty(MessageManager.ERROR_IN_QUANTITY);
                parameterWrapper.setRequestParam( IParameterName.ERROR, error);

                PageAddressManager configurationManager = PageAddressManager.getInstance();
                return configurationManager.getProperty(PageAddressManager.CART_PAGE);
            }

            int quantity = Integer.parseInt(quantityStr);

            if ( quantity < 0 ) {

                MessageManager messageManager = MessageManager.getInstance();
                String error = messageManager.getProperty( MessageManager.ERROR_IN_QUANTITY );
                parameterWrapper.setRequestParam(IParameterName.ERROR, error);

                PageAddressManager configurationManager = PageAddressManager.getInstance();
                return configurationManager.getProperty(PageAddressManager.CART_PAGE);

            } else {
                CustomerOrder order = (CustomerOrder) parameterWrapper.getSessionAttribute(
                        IParameterName.CUSTOMER_ORDER );

                if ( null == order ) {
                    LoginInfo loginInfo = (LoginInfo) parameterWrapper.getSessionAttribute( IParameterName.LOGIN_INFO );
                    order = updateCartLogic.getCustomerOrder(loginInfo);
                }
                
                MessageManager messageManager = MessageManager.getInstance();

                String errorMessage;
                if ( !updateCartLogic.isExistsInWarehouse(bookID) ) {
                    errorMessage = messageManager.getProperty(MessageManager.ERROR_NO_SUCH_BOOK);
                    parameterWrapper.setRequestParam( IParameterName.ERROR, errorMessage);

                    PageAddressManager configurationManager = PageAddressManager.getInstance();
                    return configurationManager.getProperty(PageAddressManager.CART_PAGE);

                } else if (0 > updateCartLogic.getBookAmoutFromWarehouse(bookID) - quantity) {
                    int amountInWarehouse = updateCartLogic.getBookAmoutFromWarehouse(bookID);
                    if (0 == amountInWarehouse) {
                        errorMessage = messageManager.getProperty(MessageManager.ERROR_NO_SUCH_BOOK);
                    } else if (1 == amountInWarehouse) {
                        errorMessage = messageManager.getProperty(MessageManager.ERROR_ONE_BOOK);
                    } else {
                        errorMessage = messageManager.getProperty(MessageManager.ERROR_BOOK_AMOUNT);
                        errorMessage = errorMessage.replace(MessageManager.AMOUNT_TO_CHANGE,
                                Integer.toString(amountInWarehouse));
                    }

                    parameterWrapper.setRequestParam( IParameterName.ERROR, errorMessage);

                    PageAddressManager configurationManager = PageAddressManager.getInstance();
                    return configurationManager.getProperty(PageAddressManager.CART_PAGE);
                }

                order = updateCartLogic.updateOrder(order, bookID, quantity);

                Cart cart = new Cart(order);
                
                if ( order.getBookQuantity().isEmpty() ) {
                    order = new CustomerOrder();
                    cart = new Cart();
                }

                parameterWrapper.setSessionAttribute( IParameterName.CART, cart );
                parameterWrapper.setSessionAttribute( IParameterName.CUSTOMER_ORDER, order );

                PageAddressManager configurationManager = PageAddressManager.getInstance();
                return configurationManager.getProperty(PageAddressManager.CART_PAGE);

            }
        } catch (LogicException | ParameterWrapperException | EntityException ex) {
            CommandLogger logger = CommandLogger.getInstance( UpdateCartCommand.class );
            logger.error(ex);
            throw new CommandException(ex);
        }
    }
}
