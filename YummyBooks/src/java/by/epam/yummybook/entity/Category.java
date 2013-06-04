package by.epam.yummybook.entity;

import by.epam.yummybook.entity.logger.EntityLogger;
import java.io.Serializable;
import java.util.Objects;

/**
 * Book's category entity class.
 * @author Elena Vizgalova
 */
public class Category implements Serializable {
    
    /**
     * Category id.
     */
    private int id;
    
    /**
     * Category name.
     */
    private String name;

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
     * @param id the id to set
     * @throws EntityException when {@code id <= 0 }
     */
    public void setId(int id) throws EntityException {
        
        if ( id <= 0 ) {
            EntityLogger logger = EntityLogger.getInstance( Category.class );
            logger.error("Negative or 0 id error! id = " + id);
            throw new EntityException( "id must be > 0 !" );
        }
        
        this.id = id;
    }

    /**
     * @param name the name to set
     * @throws EntityException when <tt>name</tt> to set is <tt>null</tt> 
     * or empty string
     */
    public void setName(String name) throws EntityException {
        
        if ( null == name ) {
            EntityLogger logger = EntityLogger.getInstance( Category.class );
            logger.error("Null name error!");
            throw new EntityException( "Name to set can't be null!" );
        }
        
        if ( name.isEmpty() ) {
            EntityLogger logger = EntityLogger.getInstance( Category.class );
            logger.error("Empty name error!");
            throw new EntityException( "Name can't be empty!" );
        }
        
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Category other = (Category) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.id;
        hash = 97 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public String toString() {
        return getClass().getName() + "@id: " + id + " name: " + name;
    }
    
}
