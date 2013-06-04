package by.bsu.web.dao.impl;

import by.bsu.web.dao.BookDao;
import by.bsu.web.entity.Book;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 *
 * @author Elena Vizgalova
 */
@Repository
@Transactional
public class BookDaoImpl implements BookDao {
    
    private final String ID_FIELDNAME = "id";
    private final String CATEGORY_FIELDNAME = "category";
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Book findById( String bookId ) {
        
        if ( !StringUtils.hasText( bookId ) ) {
            return null;
        }
        
        Criteria criteria = getSessionFactory().getCurrentSession().
                createCriteria( Book.class );
        criteria.add( Restrictions.eq( ID_FIELDNAME, bookId ) );
        criteria.setCacheable( true );
        
        return ( Book ) criteria.uniqueResult();
    }

    public List<Book> searchByCategory( String categoryName ) {
        
        Criteria hibernateCriteria = getSessionFactory().getCurrentSession().
                createCriteria( Book.class );
        
        hibernateCriteria.add( Restrictions.eq(
                    CATEGORY_FIELDNAME, categoryName ) );
        
        return hibernateCriteria.list();
    }

    public List<Book> retrieveAllBooks() {
        
        Criteria criteria = getSessionFactory().getCurrentSession().
                createCriteria( Book.class );
        
        criteria.addOrder( Order.asc( ID_FIELDNAME ) );
        
        return criteria.list();
    }

    public void save( Book book ) {
        
        if ( findById( book.getId() ) == null ) {
            getSessionFactory().getCurrentSession().save( book );
        } else {
            getSessionFactory().getCurrentSession().update( book );
        }
        
    }

    public Book update( Book book ) {
        
        getSessionFactory().getCurrentSession().update( book );
        
        return book;
    }

    public void delete(Book book) {
        getSessionFactory().getCurrentSession().delete( book );
    }

    public void deleteById(String id) {
        
        Book book = findById( id );
        
        if ( null != book )
        {
            delete( book );
        }
    }
    
}
