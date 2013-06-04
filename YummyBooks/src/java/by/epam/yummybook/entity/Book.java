package by.epam.yummybook.entity;

import by.epam.yummybook.entity.logger.EntityLogger;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Describes book entity.
 * @author Elena Vizgalova
 */
public class Book implements Serializable {
    
    /**
     * id is ISBN number of book: 'ISBN' + number (from 9 to 13 digits).
     */
    private String id;
    
    /**
     * Book's name.
     */
    private String name;
    
    /**
     * Book's language.
     */
    private String language;
    
    /**
     * Book's price.
     */
    private double price;
    
    /**
     * Authors or author of book.
     */
    private String authors;
    
    /**
     * Short book description.
     */
    private String description;
    
    /**
     * The date, when book was updated on warehouse last time.
     */
    private Date lastUpdate;
    
    /**
     * Book category.
     */
    private Category category;
    
    public Book() {
        id = "0";
        name = "";
        price = 0.00;
        language = "english";
        authors = "Unknown author";
        description = "";
        lastUpdate = new Date();
        
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    public String getLanguage() {
        return language;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @return the authors
     */
    public String getAuthors() {
        return authors;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the lastUpdate
     */
    public Date getLastUpdate() {
        return lastUpdate;
    }

    /**
     * @return the category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Sets id, when it's correct {@see Book#id}, otherwise throws exception. 
     * @param id the id to set
     * @throws EntityException, when <tt>id</tt> doesn't match the pattern.
     */
    public void setId( String id ) throws EntityException {
        
        Pattern pattern = Pattern.compile(ValidationConstant.BOOK_REGEX);
        Matcher matcher = pattern.matcher( id );
        
        if ( !matcher.matches() ) {
            EntityLogger logger = EntityLogger.getInstance( Book.class );
            logger.error("Invalid ID: " + id + "ID must be ISBN number of book: 13-digit number!");
            throw new EntityException( "Invalid ID! "
                    + "ID must be ISBN number of book: 13-digit number!" );
        }
        
        this.id = id;
    }

    /**
     * Sets <tt>name</tt>.
     * @param name the name to set
     * @throws EntityException, when <tt>name</tt> is null or empty.
     */
    public void setName( String name ) throws EntityException {
        
        if ( null == name ) {
            EntityLogger logger = EntityLogger.getInstance( Book.class );
            logger.error("Null name error!");
            throw new EntityException( "Name can't be null!" );
        }
        
        if ( name.isEmpty() ) {
            EntityLogger logger = EntityLogger.getInstance( Book.class );
            logger.error("Empty name error!");
            throw new EntityException( "Name can't be empty!" );
        }
        
        this.name = name;
        
    }

    public void setLanguage(String language) throws EntityException {
        
        if ( null == language ) {
            EntityLogger logger = EntityLogger.getInstance( Book.class );
            logger.error("Null languge error!");
            throw new EntityException( "Language can't be null!" );
        }
        
        if ( language.isEmpty() ) {
            EntityLogger logger = EntityLogger.getInstance( Book.class );
            logger.error("Empty languge string error!");
            throw new EntityException( "Language can't be empty!" );
        }
        
        this.language = language;
        
    }

    /**
     * @param price the price to set
     */
    public void setPrice( double price ) throws EntityException {
        
        if ( price <= 0.0 ) {
            EntityLogger logger = EntityLogger.getInstance( Book.class );
            logger.error( "Price <= 0 error. Price: " + price );
            throw new EntityException( "Price must be > 0 !" );
        }
        
        this.price = price;
    }

    /**
     * @param authors the authors to set
     */
    public void setAuthors( String authors ) throws EntityException {
        
        if ( null == authors ) {
            EntityLogger logger = EntityLogger.getInstance( Book.class );
            logger.error( "Null authors string error!" );
            throw new EntityException( "Authors string can't be null!" );
        }
        
        if ( authors.isEmpty() ) {
            EntityLogger logger = EntityLogger.getInstance( Book.class );
            logger.error( "Empty authors string error!" );
            throw new EntityException( "Authors string can't be empty!" );
        }
        
        this.authors = authors;
    }

    /**
     * @param description the description to set
     */
    public void setDescription( String description ) throws EntityException {
        
        if ( null == description ) {
            EntityLogger logger = EntityLogger.getInstance( Book.class );
            logger.error( "Null description string error!" );
            throw new EntityException( "description string can't be null!" );
        }
        
        if ( description.isEmpty() ) {
            EntityLogger logger = EntityLogger.getInstance( Book.class );
            logger.error( "Empty description string error!" );
            throw new EntityException( "description string can't be empty!" );
        }
        
        this.description = description;
    }

    /**
     * @param lastUpdate the lastUpdate to set
     */
    public void setLastUpdate( Date lastUpdate ) throws EntityException {
        if ( null == description ) {
            EntityLogger logger = EntityLogger.getInstance( Book.class );
            logger.error( "Null lastUpdate error!" );
            throw new EntityException( "lastUpdate can't be null!" );
        }
        
        this.lastUpdate = lastUpdate;
    }

    /**
     * @param category the category to set
     */
    public void setCategory( Category category ) throws EntityException {
        if ( null == description ) {
            EntityLogger logger = EntityLogger.getInstance( Book.class );
            logger.error( "Null category error!" );
            throw new EntityException( "category can't be null!" );
        }
        this.category = category;
    }
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.name);
        hash = 29 * hash + Objects.hashCode(this.language);
        hash = 64 * hash + (int) (Double.doubleToLongBits(this.price) ^ (Double.doubleToLongBits(this.price) >>> 32));
        hash = 29 * hash + Objects.hashCode(this.authors);
        hash = 32 * hash + Objects.hashCode(this.description);
        hash = 29 * hash + Objects.hashCode(this.lastUpdate);
        hash = 29 * hash + Objects.hashCode(this.category);
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
        final Book other = (Book) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.language, other.language)) {
            return false;
        }
        if (Double.doubleToLongBits(this.price) != Double.doubleToLongBits(other.price)) {
            return false;
        }
        if (!Objects.equals(this.authors, other.authors)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.lastUpdate, other.lastUpdate)) {
            return false;
        }
        if (!Objects.equals(this.category, other.category)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getClass().getName() + "@id: " + id + "\nname: " + this.name
                + "\nauthors: " + authors + "\ndescription: " 
                + description + "\nlanguage: " + language 
                + "\nlastUpdate: " + lastUpdate + "\nprice: " + price 
                + "\ncategory: " + category.toString();
    }
    
}
