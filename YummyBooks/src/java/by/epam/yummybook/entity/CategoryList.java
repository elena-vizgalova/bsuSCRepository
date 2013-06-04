package by.epam.yummybook.entity;

import by.epam.yummybook.entity.logger.EntityLogger;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Bean class for <tt>List</tt> of categories.
 * @see Category
 * @author Elena Vizgalova
 */
public class CategoryList implements Serializable {
    
    private List<Category> categoryList;

    /**
     * @return the categoryList
     */
    public List<Category> getCategoryList() {
        return categoryList;
    }

    /**
     * @param categoryList the categoryList to set
     * @throws EntityException when <tt>categoryList</tt> is null
     */
    public void setCategoryList( List<Category> categoryList ) throws EntityException {
        if ( null == categoryList ) {
            EntityLogger logger = EntityLogger.getInstance( CategoryList.class );
            logger.error("Null categoryList error!");
            throw new EntityException( "category list to set can't be null" );
        }
        this.categoryList = categoryList;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CategoryList other = (CategoryList) obj;
        if (!Objects.equals(this.categoryList, other.categoryList)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.categoryList);
        return hash;
    }

    @Override
    public String toString() {
        String toStrList = "";
        for ( Category category: categoryList ) {
            toStrList += category.toString();
        }
        return getClass().getName() + "@categoryList: " + toStrList;
    }
    
}
