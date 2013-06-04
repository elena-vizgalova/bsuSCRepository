package by.epam.yummybook.command;

import by.epam.yummybook.command.logger.CommandLogger;
import by.epam.yummybook.command.paramname.IParameterName;
import by.epam.yummybook.controller.ParameterWrapper;
import by.epam.yummybook.entity.Book;
import by.epam.yummybook.entity.Category;
import by.epam.yummybook.controller.ParameterWrapperException;
import by.epam.yummybook.logic.CategoryLogic;
import by.epam.yummybook.logic.LogicException;
import by.epam.yummybook.manager.MessageManager;
import by.epam.yummybook.manager.PageAddressManager;
import java.util.List;

/**
 * It's singleton class that process functionality loading categories from DB.
 * @see Commandable
 * @author Elena Vizgalova
 */
class CategoryCommand implements Commandable {

    private static CategoryCommand instance;
    
    private CategoryCommand() {
    }

    public static synchronized CategoryCommand getInstance() {
        if ( null == instance ) {
            instance = new CategoryCommand();
        }
        return instance;
    }
    
    /**
     * Uses {@link CategoryLogic } class for loading categories.
     * Gets number of category from {@link ParameterWrapper} object. And if it's <code>null</code>, 
     * it puts error message into session attribute and forwards request to error page.
     * If the number of category is correct, it loads from DB category with given number (it's ID),
     * loads books from DB, that belongs given category, writes <tt>Category</tt> and 
     * {@code List<Book> } objects into session.
     * Forwards request into category page.
     * @param parameterWrapper
     * @return
     * @throws CommandException 
     * @see Commandable
     */
    @Override
    public String execute(ParameterWrapper parameterWrapper) throws CommandException {
        try {
            String selectedCategoryIDStr = parameterWrapper.getQueryStringFromRequest();

            if ( null == selectedCategoryIDStr ) {
                MessageManager messageManager = MessageManager.getInstance();
                PageAddressManager configurationManager = PageAddressManager.getInstance();
                String errorMessage = messageManager.getProperty( MessageManager.ERROR_UNKNOWN_CATEGORY );

                parameterWrapper.setSessionAttribute(IParameterName.ERROR, errorMessage);

                return configurationManager.getProperty( PageAddressManager.ERROR_PAGE );
            }
            
            int categoryID = Integer.parseInt( selectedCategoryIDStr );
            CategoryLogic categoryLogic = CategoryLogic.getInstance();

            Category selectedCategory = categoryLogic.getCategoryByID( categoryID );

            List<Book> categoryBooks = categoryLogic.getBookListByCategoryID(categoryID);
     
            parameterWrapper.setSessionAttribute( IParameterName.SELECTED_CATEGORY, selectedCategory );
            parameterWrapper.setSessionAttribute( IParameterName.CATEGORY_BOOKS, categoryBooks );
            
            PageAddressManager configurationManager = PageAddressManager.getInstance();
            return configurationManager.getProperty( PageAddressManager.CATEGORY_PAGE );
        } catch (LogicException | ParameterWrapperException ex) {
            CommandLogger logger = CommandLogger.getInstance(CategoryCommand.class);
            logger.error( ex );
            throw new CommandException( ex );
        }
    }
}
