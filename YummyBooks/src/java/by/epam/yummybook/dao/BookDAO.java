package by.epam.yummybook.dao;

import by.epam.yummybook.entity.Book;
import java.util.List;

/**
 * Handles basic operations for {@link Book} entity, working with corresponding
 * DB table.
 * @author Elena Vizgalova
 */
public interface BookDAO {
    /**
     * Gets book with specific id from DB.
     * @param id book id
     * @return {@link Book } object
     * @throws BookDAOException when SQL Errors accrues.
     */
    public abstract Book getBookInfo( String id ) throws BookDAOException;
    
    /**
     * Checks if book with given <tt>id</tt> exists in DB.
     * @param id
     * @return <code>true</code> if exists, <code>false</code> - otherwise.
     * @throws BookDAOException when SQL Errors accrues.
     */
    public abstract boolean isAlreadyExists( String id ) throws BookDAOException;
    
    /**
     * Adds <tt>book</tt> to DB.
     * @param book
     * @throws BookDAOException when SQL Errors accrues.
     */
    public abstract void addBook( Book book ) throws BookDAOException;
    
    /**
     * Gets books list that belongs to the category with given <tt>categoryID</tt>
     * @param categoryID
     * @return books list
     * @throws BookDAOException when SQL Errors accrues.
     */
    public abstract List<Book> getBookListByCategoryID( int categoryID ) throws BookDAOException;
    
}
