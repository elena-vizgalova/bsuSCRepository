package by.epam.yummybook.dao;

import by.epam.yummybook.entity.Category;
import java.util.List;

/**
 * Handles basic operations for {@link Category} entity, working with corresponding
 * DB table.
 * @author Elena Vizgalova
 */
public interface CategoryDAO {
    
    /**
     * Gets category name by <tt>id</tt>.
     * @param id
     * @return category name
     * @throws CategoryDAOException when SQL Errors accrues.
     */
    public abstract String getCategoryName( int id ) throws CategoryDAOException;
    
    /**
     * Gets {@link Category} object by <tt>id</tt>
     * @param id
     * @return {@link Category} object
     * @throws CategoryDAOException when SQL Errors accrues.
     */
    public abstract Category getCategoryById( int id ) throws CategoryDAOException;
    
    /**
     * Gets categories list from DB.
     * @return list of categories.
     * @throws CategoryDAOException when SQL Errors accrues.
     */
    public abstract List<Category> getAllCategories() throws CategoryDAOException;
    
    /**
     * Checks if the category with specific <tt>id</tt> exists in DB.
     * @param id
     * @return <code>true</code> if category with given <tt>id</tt> exists in DB, 
     * <code>else</code> - otherwise.
     * @throws CategoryDAOException when SQL Errors accrues.
     */
    public abstract boolean isCategoryExists( int id ) throws CategoryDAOException;
    
}
