package by.epam.yummybook.dao;

import by.epam.yummybook.entity.LoginInfo;

/**
 * Handles basic operations for {@link LoginInfo} entity, working with corresponding
 * DB table.
 * @author Elena Vizgalova
 */
public interface LoginDAO {
    
    /**
     * Checks if there is row with specific <tt>user</tt> and <tt>password</tt> in DB.
     * @param user
     * @param password
     * @return <code>true</code> if exists, <code>false</code> otherwise.
     * @throws LoginDAOException 
     */
    public abstract boolean checkLoginInfo( String user, String password ) throws LoginDAOException;
    
    /**
     * Checks if user with specific <tt>user</tt> is administrator.
     * @param user
     * @return <code>true</code> if it's administrator, <code>false</code> otherwise.
     * @throws LoginDAOException 
     */
    public abstract boolean isAdmin( String user ) throws LoginDAOException;
    
    /**
     * Checks if user with specific <tt>user</tt> is in black list.
     * @param user
     * @return <code>true</code> if is in black list, <code>false</code> otherwise.
     * @throws LoginDAOException 
     */
    public abstract boolean isInBlackList( String user ) throws LoginDAOException;
    
    /**
     * Gets {@link LoginInfo} object with specific <tt>user</tt> is in black list.
     * @param user
     * @return {@link LoginInfo} object
     * @throws LoginDAOException 
     */
    public abstract LoginInfo getLoginInfo( String user ) throws LoginDAOException;
    
    /**
     * Sets black list flag in DB for user with specific <tt>user</tt>.
     * @param user
     * @param flagToSet
     * @throws LoginDAOException 
     */
    public abstract void setIsInBlackListFlag( String user, boolean flagToSet ) throws LoginDAOException;
}
