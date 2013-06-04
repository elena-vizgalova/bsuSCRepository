package by.epam.yummybook.entity;

/**
 * <tt>AdminLoginInfo</tt> extends all properties of {@link LoginInfo} class,
 * has one constructor, that sets <tt>isAdmin</tt> flag on <tt>true</tt>.
 * @author Elena Vizgalova
 */
public class AdminLoginInfo extends LoginInfo {

    /**
     * Sets <tt>isAdmin</tt> flag on <tt>true</tt>
     * @see LoginInfo
     */
    public AdminLoginInfo() {
        super();
        setIsAdmin( true );
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash + super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
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
        
        return super.equals(obj);
    }
    
}
