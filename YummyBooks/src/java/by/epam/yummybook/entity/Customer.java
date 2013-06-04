package by.epam.yummybook.entity;

import by.epam.yummybook.entity.logger.EntityLogger;
import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Describes customer. Sets fields using rules described in 
 * {@link ValidationConstant} interface.
 * @author Elena Vizgalova
 */
public class Customer implements Serializable {
    
    private int id;
    
    private String name;
    private String email;
    private String phone;
    private String address;
    private String cityRegion;
    
    private LoginInfo customerLoginInfo;
    
    public Customer() {
        id = 0;
        name = "";
        email = "";
        phone = "";
        address = "";
        cityRegion = "";
        customerLoginInfo = new CustomerLoginInfo();
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @return the cityRegion
     */
    public String getCityRegion() {
        return cityRegion;
    }

    /**
     * @return the customerLoginInfo
     */
    public LoginInfo getCustomerLoginInfo() {
        return customerLoginInfo;
    }

    /**
     * @param id the id to set
     * @throws EntityException when {@code id <= 0 }
     */
    public void setId(int id) throws EntityException {
        if ( id <= 0 ) {
            EntityLogger logger = EntityLogger.getInstance( Customer.class );
            logger.error("Negative or 0 id error! Id: " + id);
            throw new EntityException( "id must be > 0 !" );
        }
        this.id = id;
    }

    /**
     * @param name the name to set
     * @throws EntityException when <tt>name</tt> is <tt>null</tt>, empty string or 
     * doesn't match {@link ValidationConstant#CUSTOMER_NAME_REGEX} pattern.
     */
    public void setName( String name ) throws EntityException {
        
        if ( null == name ) {
            EntityLogger logger = EntityLogger.getInstance( Customer.class );
            logger.error("Null name error!");
            throw new EntityException( "Name can't be null!" );
        }
        
        if ( name.isEmpty() ) {
            EntityLogger logger = EntityLogger.getInstance( Customer.class );
            logger.error("Empty name error!");
            throw new EntityException( "Name can't be empty!" );
        }
        
        Pattern pattern = Pattern.compile( ValidationConstant.CUSTOMER_NAME_REGEX );
        Matcher matcher = pattern.matcher( name );
        
        if ( !matcher.matches() ) {
            EntityLogger logger = EntityLogger.getInstance( Customer.class );
            logger.error("Invvalid name error! Name: " + name);
            throw new EntityException( "Invalid name!" );
        }
        this.name = name;
    }

    /**
     * @param email the email to set
     * @throws EntityException when <tt>email</tt> is <tt>null</tt>, empty string or 
     * doesn't match {@link ValidationConstant#EMAIL_REGEX} pattern.
     */
    public void setEmail(String email) throws EntityException {
        
        if ( null == email ) {
            EntityLogger logger = EntityLogger.getInstance( Customer.class );
            logger.error( "Null email error!" );
            throw new EntityException( "Email can't be null!" );
        }
        
        if ( email.isEmpty() ) {
            EntityLogger logger = EntityLogger.getInstance( Customer.class );
            logger.error( "Empty email error!" );
            throw new EntityException( "Email can't be empty!" );
        }
        
        Pattern pattern = Pattern.compile( ValidationConstant.EMAIL_REGEX );
        Matcher matcher = pattern.matcher( email );
        if ( !matcher.matches() ) {
            EntityLogger logger = EntityLogger.getInstance( Customer.class );
            logger.error("Invalid email error! email: " + email);
            throw new EntityException( "Invalid email!" );
        }
        
        this.email = email;
        
    }

    /**
     * 
     * @param phone the phone to set
     * @throws EntityException when <tt>phone</tt> is <tt>null</tt>, empty string or 
     * doesn't match {@link ValidationConstant#PHONE_REGEX} pattern.
     */
    public void setPhone( String phone ) throws EntityException {
        
        if ( null == phone ) {
            EntityLogger logger = EntityLogger.getInstance( Customer.class );
            logger.error( "Null phone error!" );
            throw new EntityException( "Phone can't be null!" );
        }
        
        if ( phone.isEmpty() ) {
            EntityLogger logger = EntityLogger.getInstance( Customer.class );
            logger.error( "Empty phone error!" );
            throw new EntityException( "Phone can't be epmty!" );
        }
        
        Pattern pattern = Pattern.compile( ValidationConstant.PHONE_REGEX );
        Matcher matcher = pattern.matcher( phone );
        
        if ( !matcher.matches() ) {
            EntityLogger logger = EntityLogger.getInstance( Customer.class );
            logger.error( "Invalid phone error! phone: " + phone );
            throw new EntityException( "Phone must be in format '+XXXXX-XXXXXXX', "
                    + "where first 5 numbers are country's and city's code, "
                    + "the last 7 - phone number." );
        }
        
        this.phone = phone;
    }

    /**
     * @param address the address to set
     * @throws EntityException when <tt>address</tt> is <tt>null</tt> 
     * or empty
     */
    public void setAddress(String address) throws EntityException {
        
        if ( null == address ) {
            EntityLogger logger = EntityLogger.getInstance( Customer.class );
            logger.error( "Null address error!" );
            throw new EntityException( "Address can't be null!" );
        }
        
        if ( address.isEmpty() ) {
            EntityLogger logger = EntityLogger.getInstance( Customer.class );
            logger.error( "Empty address error!" );
            throw new EntityException( "Address can't be empty!" );
        }
        
        this.address = address;
    }

    /**
     * @param cityRegion the cityRegion to set
     * @throws EntityException when <tt>cityRegion</tt> is <tt>null</tt> 
     * or empty.
     */
    public void setCityRegion(String cityRegion) throws EntityException {
        
        if ( null == cityRegion ) {
            EntityLogger logger = EntityLogger.getInstance( Customer.class );
            logger.error( "Null cityRegion error!" );
            throw new EntityException( "cityRegion field can't be null!" );
        }
        
        if ( cityRegion.isEmpty() ) {
            EntityLogger logger = EntityLogger.getInstance( Customer.class );
            logger.error( "Empty cityRegion error!" );
            throw new EntityException( "cityRegion field can't be empty!" );
        }
        
        this.cityRegion = cityRegion;
    }

    /**
     * @param customerLoginInfo the customerLoginInfo to set
     * @throws EntityException when <tt>loginInfo</tt> is empty.
     */
    public void setCustomerLoginInfo( LoginInfo loginInfo) throws EntityException {
        if ( null == loginInfo ) {
            EntityLogger logger = EntityLogger.getInstance( Customer.class );
            logger.error( "Null loginInfo error!" );
            throw new EntityException( "loginInfo can't be null" );
        }
        this.customerLoginInfo = loginInfo;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.id;
        hash = 7 * hash + Objects.hashCode(this.name);
        hash = 64 * hash + Objects.hashCode(this.email);
        hash = 97 * hash + Objects.hashCode(this.phone);
        hash = 32 * hash + Objects.hashCode(this.address);
        hash = 7 * hash + Objects.hashCode(this.cityRegion);
        hash = 7 * hash + Objects.hashCode(this.customerLoginInfo);
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
        final Customer other = (Customer) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.phone, other.phone)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.cityRegion, other.cityRegion)) {
            return false;
        }
        if (!Objects.equals(this.customerLoginInfo, other.customerLoginInfo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getClass().getName() + "@id: " + id + "\nname: " + name 
                + "\nemail: " + email + "\nphone" + phone + "\ncityRegion" 
                + cityRegion + "\naddress: " + address + "\ncustomerLoginInfo" 
                + customerLoginInfo.toString();
    }
    
}