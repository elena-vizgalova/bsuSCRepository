package by.epam.yummybook.dao.mysql;

import by.epam.yummybook.dao.BookDAO;
import by.epam.yummybook.dao.BookDAOException;
import by.epam.yummybook.dao.CategoryDAO;
import by.epam.yummybook.dao.ConnectionPool;
import by.epam.yummybook.dao.DAOException;
import by.epam.yummybook.dao.logger.DAOLogger;
import by.epam.yummybook.entity.Book;
import by.epam.yummybook.entity.Category;
import by.epam.yummybook.entity.EntityException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Works with MySQL book table.
 * @see BookDAO
 * @author Elena Vizgalova
 */
public class MysqlBookDAO implements BookDAO {

    private static MysqlBookDAO instance;
    private static CategoryDAO categoryDAO;
 
    private final String SELECT_BOOK_BY_ID = "SELECT * FROM book WHERE id = ?";
    private final String SELECT_BOOKS_BY_CATEGORY_ID = "SELECT * FROM book WHERE category_id = ?";
    
    private final String INSERT_BOOK = "INSERT INTO book (id, name, language, price, authors, description,"
            + "category_id) VALUES ( ?, ?, ?, ?, ?, ?, ? )";
    private final int ID_INSERT_INDEX = 1;
    private final int NAME_INSERT_INDEX = 2;
    private final int LANGUAGE_INSERT_INDEX = 3;
    private final int PRICE_INSERT_INDEX = 4;
    private final int AUTHORS_INSERT_INDEX = 5;
    private final int DESCRIPTION_INSERT_INDEX = 6;
    private final int CATEGORY_ID_INSERT_INDEX = 7;
    
    private final int ID_COLUMN_INDEX = 1;
    private final int NAME_COLUMN_INDEX = 2;
    private final int LANGUAGE_COLUMN_INDEX = 3;
    private final int PRICE_COLUMN_INDEX = 4;
    private final int AUTHORS_COLUMN_INDEX = 5;
    private final int DESCRIPTION_COLUMN_INDEX = 6;
    private final int LAST_UPDATE_COLUMN_INDEX = 7;
    private final int CATEGORY_ID_INDEX = 8;
    

    private MysqlBookDAO() throws BookDAOException {
        try {
            
            MysqlDAOFactory mysqlDAOFactory = MysqlDAOFactory.getInstance();
            categoryDAO = mysqlDAOFactory.getCategoryDAO();
        } catch ( DAOException ex ) {
            DAOLogger logger = DAOLogger.getInstance( MysqlBookDAO.class );
            logger.error( "Error during init" , ex );
            throw new BookDAOException( ex );
        }
    }
    
    private static void init() throws BookDAOException {
        instance = new MysqlBookDAO();
    }
    
    public static synchronized MysqlBookDAO getInstance() throws BookDAOException {
        try {
            
            if (null == instance) {
                init();
            }
            
        } catch ( DAOException ex ) {
            throw new BookDAOException( ex );
        }
        return instance;
    }
    
    /**
     * @see BookDAO#getBookInfo(java.lang.String)
     */
    @Override
    public Book getBookInfo( String id ) throws BookDAOException {
        Book book = new Book();
        
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            preparedStatement = (PreparedStatement) connection.
                    prepareStatement( SELECT_BOOK_BY_ID );
            preparedStatement.setString( 1, id );

            resultSet = preparedStatement.executeQuery();
            
            resultSet.next();
            
            book.setId( id );
            book.setName( resultSet.getString( NAME_COLUMN_INDEX ) );
            book.setPrice( resultSet.getDouble( PRICE_COLUMN_INDEX ) );
            book.setAuthors( resultSet.getString( AUTHORS_COLUMN_INDEX ) );
            book.setDescription( resultSet.getString( DESCRIPTION_COLUMN_INDEX ) );
            book.setLanguage( resultSet.getString( LANGUAGE_COLUMN_INDEX ) );
            
            Date lastUpdate = resultSet.getDate( LAST_UPDATE_COLUMN_INDEX );
            java.util.Date newDate = lastUpdate;
            book.setLastUpdate( newDate );
            
            int categoryID = resultSet.getInt( CATEGORY_ID_INDEX );
            
            book.setCategory( categoryDAO.getCategoryById( categoryID ) );
            
            connectionPool.releaseConnection( connection );
        } catch (EntityException ex) {
            DAOLogger logger = DAOLogger.getInstance( MysqlBookDAO.class );
            logger.error( "Error during adding data into book from DB", ex );
            throw new BookDAOException( ex );
        } catch (DAOException | SQLException ex) {
            DAOLogger logger = DAOLogger.getInstance( MysqlBookDAO.class );
            logger.error( ex );
            throw new BookDAOException( ex );
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance( MysqlBookDAO.class );
                    logger.error("The result set cannot be closed.", e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance( MysqlBookDAO.class );
                    logger.error("The statement cannot be closed.", e);
                }
            }
        }
        return book;
    }

    /**
     * @see BookDAO#isAlreadyExists(java.lang.String) 
     */
    @Override
    public boolean isAlreadyExists( String id ) throws BookDAOException {
        boolean isExists = false;
        
        ResultSet resultSet = null;
        Connection connection;
        PreparedStatement preparedStatement = null;
        
        try {
            
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            preparedStatement = (PreparedStatement) connection.
                    prepareStatement( SELECT_BOOK_BY_ID );
            preparedStatement.setString( 1, id );

            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next() ) {
                isExists = true;
            }
            
            connectionPool.releaseConnection( connection );
        } catch (DAOException | SQLException ex) {
            DAOLogger logger = DAOLogger.getInstance( MysqlBookDAO.class );
            logger.error( ex );
            throw new BookDAOException( ex );
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance( MysqlBookDAO.class );
                    logger.error("The result set cannot be closed.", e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance( MysqlBookDAO.class );
                    logger.error("The statement cannot be closed.", e);
                }
            }
        }
        return isExists;
    }

    /**
     * @see BookDAO#addBook(by.epam.yummybook.entity.Book) 
     */
    @Override
    public void addBook( Book book ) throws BookDAOException {
        ResultSet resultSet = null;
        Connection connection;
        PreparedStatement preparedStatement = null;
        
        try {
            
            if ( !categoryDAO.isCategoryExists( book.getCategory().getId() ) ) {
                throw new BookDAOException("No such category!");
            }
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
  
            preparedStatement = (PreparedStatement) connection.
                    prepareStatement( INSERT_BOOK );
            
            preparedStatement.setString( ID_INSERT_INDEX , book.getId() );
            preparedStatement.setString( NAME_INSERT_INDEX , book.getName());
            preparedStatement.setString( AUTHORS_INSERT_INDEX , book.getAuthors() );
            preparedStatement.setString( DESCRIPTION_INSERT_INDEX, book.getDescription() );
            preparedStatement.setString( LANGUAGE_INSERT_INDEX, book.getLanguage() );
            preparedStatement.setDouble( PRICE_INSERT_INDEX, book.getPrice() );
            preparedStatement.setInt( CATEGORY_ID_INSERT_INDEX , book.getCategory().getId() );
            
            preparedStatement.executeUpdate();
            
            connectionPool.releaseConnection( connection );
        } catch (DAOException | SQLException ex) {
            DAOLogger logger = DAOLogger.getInstance( MysqlBookDAO.class );
            logger.error( ex );
            throw new BookDAOException( ex );
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance( MysqlBookDAO.class );
                    logger.error("The result set cannot be closed.", e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance( MysqlBookDAO.class );
                    logger.error("The statement cannot be closed.", e);
                }
            }
        }
    }

    /**
     * @see BookDAO#getBookListByCategoryID(int)
     */
    @Override
    public List<Book> getBookListByCategoryID( int categoryID ) throws BookDAOException {
        List<Book> books = new ArrayList<>();
        
        ResultSet resultSet = null;
        Connection connection;
        PreparedStatement preparedStatement = null;
        
        try {

            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            
            preparedStatement = (PreparedStatement) connection.
                    prepareStatement( SELECT_BOOKS_BY_CATEGORY_ID );
            preparedStatement.setInt( 1, categoryID );
            
            resultSet = preparedStatement.executeQuery();
            
            while( resultSet.next() ) {
                Book book = new Book();
                
                book.setId( resultSet.getString( ID_COLUMN_INDEX ) );
                book.setName( resultSet.getString( NAME_COLUMN_INDEX ) );
                book.setPrice( resultSet.getDouble( PRICE_COLUMN_INDEX ) );
                book.setAuthors( resultSet.getString( AUTHORS_COLUMN_INDEX ) );
                book.setDescription( resultSet.getString( DESCRIPTION_COLUMN_INDEX ) );
                book.setLanguage( resultSet.getString( LANGUAGE_COLUMN_INDEX ) );
                
                Date lastUpdate = resultSet.getDate(LAST_UPDATE_COLUMN_INDEX);
                java.util.Date newDate = lastUpdate;
                book.setLastUpdate( newDate );
                
                Category category = categoryDAO.getCategoryById( resultSet.getInt( CATEGORY_ID_INDEX ) );
                book.setCategory( category );
                
                books.add( book );
            }
            
            connectionPool.releaseConnection( connection );
        } catch (SQLException ex) {
            DAOLogger logger = DAOLogger.getInstance( MysqlBookDAO.class );
            logger.error("SQL error", ex);
            throw new BookDAOException( ex );
        } catch (DAOException ex) {
            DAOLogger logger = DAOLogger.getInstance( MysqlBookDAO.class );
            logger.error( ex );
            throw new BookDAOException( ex );
        } catch (EntityException ex) {
            DAOLogger logger = DAOLogger.getInstance( MysqlBookDAO.class );
            logger.error( "Error during writing data into Book", ex );
            throw new BookDAOException(ex);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance( MysqlBookDAO.class );
                    logger.error("The result set cannot be closed.", e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance( MysqlBookDAO.class );
                    logger.error("The statement cannot be closed.", e);
                }
            }
        }
        return books;
    }
    
}