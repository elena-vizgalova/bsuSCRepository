package by.bsu.web.dao;

import by.bsu.web.entity.Book;
import java.util.List;

/**
 *
 * @author Elena Vizgalova
 */
public interface BookDao {
    
    /**
     * Gets {@link Book} object with specific id from DB.
     * @param bookId
     * @return Book or null
     */
    public Book findById( String bookId );
    
    public List<Book> searchByCategory( String categoryName );
    
    /**
     * Retrieves all existing books from DB.
     */
    public List<Book> retrieveAllBooks();
    
    /**
     * Saves <code>book</code> in DB.
     * @param book 
     */
    public void save( Book book );
    
    /**
     * Updates fields of <code>studentInfo</code> in DB.
     * @param studentInfo
     * @return 
     */
    public Book update( Book book );
    
    /**
     * Deletes given <code>book</code> from DB table.
     * @param book 
     */
    public void delete(Book book);
    
    /**
     * Deletes {@link Book} entity from DB table by given <code>id</code>.
     * @param studentId 
     */
    public void deleteById( String id );
}
