package by.epam.yummybook.dao;

import by.epam.yummybook.entity.Customer;
import java.util.List;

/**
 * Handles basic operations for {@link Customer} entity, working with corresponding
 * DB table.
 * @author Elena Vizgalova
 */
public interface CustomerDAO {
    
    /**
     * Gets {@link Customer} with specific <tt>id</tt> from DB.
     * @param id
     * @return {@link Customer} object
     * @throws CustomerDAOException when SQL Errors accrues.
     */
    public abstract Customer getCustomerByID( int id ) throws CustomerDAOException;
    
    /**
     * Gets {@link Customer} with specific <tt>username</tt> from DB.
     * @param username
     * @return {@link Customer} object
     * @throws CustomerDAOException when SQL Errors accrues.
     */
    public abstract Customer getCustomerByUsername( String username ) throws CustomerDAOException;
    
    /**
     * Adds <tt>customer</tt> to DB.
     * @param customer
     * @throws CustomerDAOException when SQL Errors accrues.
     */
    public abstract void addCustomer( Customer customer ) throws CustomerDAOException;
    
    /**
     * Checks if customer with given <tt>username</tt> exists in DB.
     * @param username
     * @return <code>true</code> if exists, <code>false</code> - otherwise.
     * @throws CustomerDAOException when SQL Errors accrues.
     */
    public abstract boolean isCustomerExists( String username ) throws CustomerDAOException;
    
    /**
     * Updates customer info in DB.
     * @param customer
     * @throws CustomerDAOException when SQL Errors accrues.
     */
    public abstract void updateCustomerInfo( Customer customer ) throws CustomerDAOException;
    
    /**
     * Gets all customers from DB.
     * @return customers list
     * @throws CustomerDAOException when SQL Errors accrues.
     */
    public abstract List<Customer> getAllCustomers() throws CustomerDAOException;
    
}
