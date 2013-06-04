package by.epam.yummybook.command;

import by.epam.yummybook.command.logger.CommandLogger;
import by.epam.yummybook.command.paramname.IParameterName;
import by.epam.yummybook.controller.ParameterWrapper;
import by.epam.yummybook.controller.ParameterWrapperException;
import by.epam.yummybook.entity.Cart;
import by.epam.yummybook.entity.Category;
import by.epam.yummybook.entity.LoginInfo;
import by.epam.yummybook.logic.HomepageLogic;
import by.epam.yummybook.logic.LogicException;
import by.epam.yummybook.manager.PageAddressManager;
import java.util.List;

/**
 * It's singleton class, that do actions for <code>index.jsp</code> page.
 * @author Elena Vizgalova
 */
public class HomepageCommand implements Commandable {
    
    private static HomepageCommand instance;
    
    private HomepageCommand(){
    }
    
    public static synchronized HomepageCommand getInstance() {
        if ( null == instance ) {
            instance = new HomepageCommand();
        }
        return instance;
    }

    /**
     * Uses {@link HomepageLogic}.
     * Gets <tt>LoginInfo</tt> object from session. If it's not <code>null</code>, 
     * loads <tt>cart</tt> and sets it into session.
     * Loads categories.
     * @param parameterWrapper
     * @return index.jsp page
     * @throws CommandException 
     */
    @Override
    public String execute(ParameterWrapper parameterWrapper) throws CommandException {
        try {
            HomepageLogic loadCategoriesLogic = HomepageLogic.getInstance();
            
            List<Category> categories = loadCategoriesLogic.getAllCategories();
            
            parameterWrapper.setSessionAttribute( IParameterName.CATEGORIES_LIST, categories);
            //parameterWrapper.setSessionAttribute( IParameterName.CATEGORIES_LIST, null);
            Cart cart = (Cart) parameterWrapper.getSessionAttribute( IParameterName.CART );
            LoginInfo loginInfo = (LoginInfo) parameterWrapper.getSessionAttribute(
                    IParameterName.LOGIN_INFO );
            
            if ( null == cart && null != loginInfo ) {
                cart = loadCategoriesLogic.loadCart( loginInfo );
                parameterWrapper.setSessionAttribute( IParameterName.CART , cart );
            }
            
            PageAddressManager addressManager = PageAddressManager.getInstance();
            return addressManager.getProperty( PageAddressManager.INDEX_PAGE );
            
        } catch (ParameterWrapperException | LogicException ex) {
            CommandLogger logger = CommandLogger.getInstance(HomepageCommand.class);
            logger.error( ex );
            throw new CommandException(ex);
        }
    }
    
}
