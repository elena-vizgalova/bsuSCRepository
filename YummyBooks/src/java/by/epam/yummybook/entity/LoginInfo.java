package by.epam.yummybook.entity;

import by.epam.yummybook.entity.logger.EntityLogger;
import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Abstract class for all types of users. Sets fields using rules described in 
 * {@link ValidationConstant} interface.
 * @author Elena Vizgalova
 */
public abstract class LoginInfo implements Serializable {
    
    private String username;
    private String password;
    private boolean isAdmin;
    private boolean isInBlackList;

    public LoginInfo() {
        username = "";
        password = "";
        isAdmin = false;
        isInBlackList = false;
    }
    
    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public boolean isIsInBlackList() {
        return isInBlackList;
    }

    /**
     * @param username the username to set
     * @throws EntityException when <tt>username</tt> is <tt>null</tt>, empty string 
     * or doesn't match {@link ValidationConstant#USERNAME_REGEX} pattern
     */
    public void setUsername( String username ) throws EntityException {
        
        if ( null == username ) {
            EntityLogger logger = EntityLogger.getInstance( LoginInfo.class );
            logger.error("Null username error");
            throw new EntityException( "Username can't be null!" );
        }
        
        if ( username.isEmpty() ) {
            EntityLogger logger = EntityLogger.getInstance( LoginInfo.class );
            logger.error("Empty username error");
            throw new EntityException( "Username can't be empty!" );
        }
        
        Pattern pattern = Pattern.compile( ValidationConstant.USERNAME_REGEX );
        Matcher matcher = pattern.matcher( username );
        
        if ( !matcher.matches() ) {
            EntityLogger logger = EntityLogger.getInstance( LoginInfo.class );
            logger.error("Invalid username error! username: " + username);
            throw new EntityException( "Username must include only latish letters, "
                    + "numbers. It can consist '.', '_' and ' ', '-' but cant't be "
                    + "ended with them." );
        }
        
        this.username = username;
    }

    /**
     * @param password the password to set
     * @throws EntityException when <tt>password</tt> is <tt>null</tt> or empty string.
     */
    public void setPassword( String password ) throws EntityException {
        
        if ( null == password ) {
            EntityLogger logger = EntityLogger.getInstance( LoginInfo.class );
            logger.error("Null password error!");
            throw new EntityException( "Password can't be null!" );
        }
        
        if ( password.isEmpty() ) {
            EntityLogger logger = EntityLogger.getInstance( LoginInfo.class );
            logger.error("Empty password error!");
            throw new EntityException( "Password can't be empty!" );
        }
        
        this.password = password;
    }

    public void setIsAdmin( boolean isAdmin ) {
        this.isAdmin = isAdmin;
    }

    public void setIsInBlackList( boolean isInBlackList ) {
        this.isInBlackList = isInBlackList;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + this.username.hashCode();
        hash = 7 * hash + this.password.hashCode();
        hash = 31 * hash + (this.isAdmin ? 1 : 0);
        hash = 13 * hash + (this.isInBlackList ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LoginInfo other = (LoginInfo) obj;
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (this.isAdmin != other.isAdmin) {
            return false;
        }
        if (this.isInBlackList != other.isInBlackList) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getClass().getName() + "@username: " + username + 
                "\npassword: " + password + "\nisAdmin: " + isAdmin + 
                "\nisInBlackList: " + isInBlackList;
    }
    
}