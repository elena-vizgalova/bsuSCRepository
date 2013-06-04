package by.epam.yummybook.dao;

import by.epam.yummybook.dao.logger.DAOLogger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


/**
 * Connection pool class for handling connections.
 * @author Alena_Vizgalava
 */
public class ConnectionPool {
    public static final String PROPERTIES_FILE = "resource.databaseOptions";
    public static final int DEFAULT_POOL_SIZE = 30;

    private static final String DRIVER_KEY = "db.driver";
    private static final String URL_KEY = "db.url";
    private static final String USER_KEY = "db.user";
    private static final String PASSWORD_KEY = "db.password";
    private static final String POOL_SIZE_KEY = "db.poolsize";
    
    /** Single instance. */
    private static ConnectionPool instance;

    /** Free connections queue. */
    private BlockingQueue<Connection> connectionQueue;

    /**
     * Initializes <code>ConnectionPool</code> instance.
     * @throws DAOException 
     */
    private ConnectionPool() throws DAOException {
        ResourceBundle rb = ResourceBundle.getBundle( PROPERTIES_FILE );

        String driver = rb.getString ( DRIVER_KEY );
        String url = rb.getString ( URL_KEY );
        String user = rb.getString ( USER_KEY );
        String password = rb.getString ( PASSWORD_KEY );
        String poolSizeStr = rb.getString ( POOL_SIZE_KEY );

        int poolSize = ( poolSizeStr != null ) ?
            Integer.parseInt( poolSizeStr ) : DEFAULT_POOL_SIZE;
        
        try {
            
            Class.forName( driver );

            connectionQueue = new ArrayBlockingQueue<>( poolSize );

            for ( int i = 0; i < poolSize; i++ ) {
                Connection connection;
                connection = (Connection) DriverManager.getConnection (url, user, password);
                connectionQueue.offer( connection );
            }
            
        } catch (SQLException | ClassNotFoundException ex) {
            DAOLogger logger = DAOLogger.getInstance(ConnectionPool.class);
            logger.fatal( "Error during initialize connection pool!", ex );
            throw new DAOException( ex );
        }
        
        
    }
    
    /**
     * Init connection pool: reads the configuration file and creates
     * ConnectionPool singleton using configuration params
     * Call on initialized ConnectionPool has no effect
     * @throws SQLException some sql exception at pool creation time
     */
    private static void init () throws DAOException {
        instance = new ConnectionPool();
    }
    
    /**
     * Close connections. Clear connection queue.
     * Call on disposed ConnectionPool has no effect
     * @throws SQLException
     */
    public static void dispose () throws DAOException {
        if ( instance != null ) {
            instance.clearConnectionQueue();
            instance = null;
        }
    }

    /**
     * Returns current ConnectionPool instance
     * @return ConnectionPool singleton
     */
    public static synchronized ConnectionPool getInstance() throws DAOException {
        if ( null == instance ) {
            init();
        }
        return instance;
    }

    /**
     * Returns free connection. Waiting if pool is empty.
     * If waiting interrupts - returns `null`
     * @return free connection
     */
    public Connection takeConnection () throws DAOException, SQLException {
        Connection connection = null;
        try {
            /*
             * Tries to take connection from queue.
             * If all connections are blocked, waits for free connection.
             */
            connection = connectionQueue.take();
        } catch (InterruptedException e) {
            DAOLogger logger = DAOLogger.getInstance(ConnectionPool.class);
            logger.error( "Free connection waiting interrupted. Returned `null` connection", e );
        }
        return connection;
    }

    /**
     * Release connection
     * @param connection connection to release
     */
    public void releaseConnection (Connection connection) throws DAOException {
        try {
            if ( connection.isClosed() ) {
                DAOLogger logger = DAOLogger.getInstance(ConnectionPool.class);
                logger.warn( "Trying to release closed connection. Possible `leakage` of connections");
                throw new DAOException("Trying to release closed connection. Possible `leakage` of connections");
            }
            
            connectionQueue.offer(connection);
            
        } catch (SQLException e) {
            DAOLogger logger = DAOLogger.getInstance(ConnectionPool.class);
            logger.error( "Connection not added. Possible `leakage` of connections" ,e );
          throw new DAOException( "Connection not added. Possible `leakage` of connections" );
        }
    }
    
    /**
     * Clears <tt>connectionQueue</tt>, closes all connections from it.
     * @throws DAOException 
     */
    private void clearConnectionQueue() throws DAOException {
        Connection connection;
        try {
            while ( null != (connection = connectionQueue.poll ()) ) {
                    if (!connection.getAutoCommit ()) {
                        connection.commit ();
                    }
                    connection.close ();
            }
        } catch (SQLException ex) {
            DAOLogger logger = DAOLogger.getInstance(ConnectionPool.class);
            logger.error( "Error clearing connection queue" , ex );
            throw new DAOException( ex );
       }
    }
    
    @Override
    protected void finalize() throws Throwable {
        
        for ( Connection c: connectionQueue ) {
            c.close();
        }
        
        super.finalize();
    }
    
}
