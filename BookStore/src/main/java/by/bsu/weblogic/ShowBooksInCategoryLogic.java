package by.bsu.weblogic;

import by.bsu.web.dao.BookDao;
import by.bsu.web.entity.Book;
import by.bsu.web.manager.AppConfigManager;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Elena Vizgalova
 */
public class ShowBooksInCategoryLogic {
    
    private final String FICTION_CATEGORY = "FICTION";
    private final String CHILDREN_BOOK_CATEGORY = "CHILDREN";
    private final String ARTS_CATEGORY = "ARTS";
    private final String POPULER_SCIENCE_CATEGORY = "POPULAR_SCIENCE";
    
    private final String FICTION_LINK_PART = "Fiction";
    private final String CHILDREN_BOOK_LINK_PART = "ChildrenBook";
    private final String ARTS_LINK_PART = "Arts";
    private final String POPULAR_SCIENCE_LINK_PART = "PopularScience";
    
    public List<Book> getBooksByCategory( String booksPage ) {
        
        String categoryName = getCategoryNameFromLink( booksPage );
        
        AppConfigManager appConfigManager = AppConfigManager.getInstance();
        ApplicationContext context = new   
           ClassPathXmlApplicationContext( appConfigManager.getProperty( 
                appConfigManager.APPLICATION_CONTEXT_FILE ) );
        BookDao bookDao = (BookDao) context.getBean( appConfigManager.getProperty( 
               appConfigManager.BOOK_DAO_BEAN ) );
        
        List<Book> books = bookDao.searchByCategory( categoryName );
        
        return books;
    }

    private String getCategoryNameFromLink ( String booksPage ) {
        
        if ( booksPage.contains( FICTION_LINK_PART ) ) {
            return FICTION_CATEGORY;
        }
        
        if ( booksPage.contains( CHILDREN_BOOK_LINK_PART ) ) {
            return CHILDREN_BOOK_CATEGORY;
        }
        
        if ( booksPage.contains( ARTS_LINK_PART ) ) {
            return ARTS_CATEGORY;
        }
        
        if ( booksPage.contains( POPULAR_SCIENCE_LINK_PART ) ) {
            return POPULER_SCIENCE_CATEGORY;
        }
        
        return null;
        
    }
    
}