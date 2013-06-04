package by.epam.yummybook.dao;

/**
 * Handles basic operations for warehouse DB table.
 * @author Elena Vizgalova
 */
public interface WarehouseDAO {
    
    /**
     * Adds given number of books with given <tt>bookID</tt> in warehouse.
     * @param bookID
     * @param quantity
     * @throws WarehouseDAOException 
     */
    public abstract void addBookWithIDToWarehouse( String bookID, int quantity ) throws WarehouseDAOException;
    
    /**
     * Deletes <tt>n</tt> books with given <tt>bookID</tt> from warehouse.
     * @param bookID
     * @param n number of book to delete
     * @throws WarehouseDAOException 
     */
    public abstract void deleteNBooksFromWarehouse( String bookID, int n ) throws WarehouseDAOException;
    
    /**
     * Gets number of book with given <tt>bookID</tt> in warehouse.
     * @param bookID
     * @return number of books
     * @throws WarehouseDAOException 
     */
    public abstract int getBookAmount( String bookID ) throws WarehouseDAOException;
    
    /**
     * Checks if book with given <tt>bookID</tt> exists in warehouse.
     * @param bookID
     * @return <code>true</code> if exists, <code>else</code> otherwise
     * @throws WarehouseDAOException 
     */
    public abstract boolean isExistBookInWarehouse( String bookID )throws WarehouseDAOException;
}
