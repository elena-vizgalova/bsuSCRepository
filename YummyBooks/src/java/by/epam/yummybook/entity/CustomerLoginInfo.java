package by.epam.yummybook.entity;

/**
 * Extends all properties of {@link LoginInfo} class.
 * @see LoginInfo
 * @author Elena Vizgalova
 */
public class CustomerLoginInfo extends LoginInfo {

    /**
     * sets <tt>isAdmin</tt> flag to {@code false}, 
     * <tt>isInBlackList</tt> to {@code false}.
     * @see LoginInfo#LoginInfo()
     */
    public CustomerLoginInfo() {
        super();
    }

    @Override
    public int hashCode() {
        int hash = 64;
        return hash + super.hashCode();
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
