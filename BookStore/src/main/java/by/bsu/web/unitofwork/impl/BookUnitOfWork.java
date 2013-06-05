package by.bsu.web.unitofwork.impl;

import by.bsu.web.dao.BookDao;
import by.bsu.web.entity.Book;
import by.bsu.web.unitofwork.UnitOfWork;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Elena Vizgalova
 */
public class BookUnitOfWork implements UnitOfWork {

    @Autowired
    private BookDao bookDao;
    
    private Set<Book> newSet;
    private Set<Book> dirtySet;
    private Set<Book> removeSet;
    
    public BookUnitOfWork() {
        
        newSet = new HashSet<Book>();
        dirtySet = new HashSet<Book>();
        removeSet = new HashSet<Book>();
        
    }

    public BookDao getBookDao() {
        return bookDao;
    }

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public void registerToRemove( Object object ) {
        Book book = (Book) object;
        
        if ( isInNew( book ) ) {
            newSet.remove( book );
            return;
        }
        
        if ( isInDirty( book ) ) {
            dirtySet.remove( book );
        }
        
        removeSet.add( book );
        
    }

    public void registerNew( Object object ) {
        
        Book book = (Book) object;
        
        if ( isInNew( book ) ) {
            replaceBook( newSet, book );
            return;
        }
        
        if ( isInDirty( book ) ) {
            replaceBook( dirtySet , book );
            return;
        }
        
        if ( isInRemove( book ) ) {
            removeSet.remove( book );
        }
        
        newSet.add( book );
        
    }

    public void registerDirty( Object object ) {
        
        Book book = (Book) object;
        
        if ( isInNew( book ) ) {
            replaceBook( newSet, book );
            return;
        }
        
        if ( isInDirty( book ) ) {
            replaceBook( dirtySet , book );
            return;
        }
        
        if ( isInRemove( book ) ) {
            removeSet.remove( book );
            dirtySet.add( book );
            return;
        }
        
        newSet.add( book );
    }

    public boolean isInNew( Object object ) {
        
        Book book = (Book) object;
        
        if ( null == newSet || newSet.isEmpty() ) {
            return false;
        }
        
        for ( Book bookFromList: newSet ) {
            if ( bookFromList.getId().equals( book.getId() ) ) {
                return true;
            }
        }
        
        return false;
    }

    public boolean isInDirty( Object object ) {

        Book book = (Book) object;
        
        if ( null == dirtySet || dirtySet.isEmpty() ) {
            return false;
        }
        
        for ( Book bookFromList: dirtySet ) {
            if ( bookFromList.getId().equals( book.getId() ) ) {
                return true;
            }
        }
        
        return false;
        
    }

    public boolean isInRemove( Object object ) {

        Book book = (Book) object;
        
        if ( null == removeSet || removeSet.isEmpty() ) {
            return false;
        }
        
        for ( Book bookFromList: removeSet ) {
            if ( bookFromList.getId().equals( book.getId() ) ) {
                return true;
            }
        }
        
        return false;
        
    }
    
    public void commit() {
        
        for ( Book book: removeSet ) {
            bookDao.delete( book);
        }
        removeSet.clear();
        
        for ( Book book: dirtySet ) {
            bookDao.update( book );
        }
        dirtySet.clear();
        
        for ( Book book: newSet ) {
            bookDao.save( book );
        }
        newSet.clear();
        
    }
    
    private void replaceBook ( Set<Book> set, Book book ) {

        Iterator iter = set.iterator();
        
        while ( iter.hasNext() ) {
            Book bookFromSet = (Book) iter.next();
            if ( bookFromSet.getId().equals( book.getId() ) ) {
                iter.remove();
                set.add( book );
            }
        }
        
    }

}
