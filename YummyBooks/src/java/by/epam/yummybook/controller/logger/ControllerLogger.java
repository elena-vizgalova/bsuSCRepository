package by.epam.yummybook.controller.logger;

import by.epam.yummybook.logger.LoggerLevel;
import by.epam.yummybook.manager.LoggerPropertyManager;
import java.io.File;
import java.io.FileOutputStream;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.RootLogger;

/**
 * Logger class for Controller class.
 * @author Elena Vizgalova
 */
public class ControllerLogger {
    
    private static volatile ControllerLogger instance;
    private static Logger logger;
    
   /**
     * Initializing <code>ControllerLogger</code>.
     * Sets it's filename, level, <code>PatternLayout</code> with 
     * {@link LoggerPropertyManager#CONVERSION_PATTERN}
     * @param msgSender 
     */
    private ControllerLogger( Class msgSender ) {
        LoggerPropertyManager loggerPropertyManager = LoggerPropertyManager.getInstance();
        logger = Logger.getLogger( msgSender );

        try {
            String fileName = loggerPropertyManager.getProperty( 
                    LoggerPropertyManager.CONTROLLER_LOG_FILENAME );
            File f = new File(fileName);
            
            String pattern = loggerPropertyManager.getProperty(
                    LoggerPropertyManager.CONVERSION_PATTERN);
            FileAppender appender = new FileAppender ( new PatternLayout(pattern), fileName );

            logger.addAppender(appender);

            String loggerLevelStr = loggerPropertyManager.getProperty( 
                    LoggerPropertyManager.CONTROLLER_LOG_LEVEL );
            
            Level loggerLevel = getLevel(LoggerLevel.valueOf(loggerLevelStr));
            logger.setLevel(loggerLevel);
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(
                    RootLogger.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, e);
        }
    }

    public static synchronized ControllerLogger getInstance(Class msgSender) {
        if ( instance == null ) {
            instance = new ControllerLogger( msgSender );
        }
        return instance;
    }

    private static Level getLevel(LoggerLevel loggerLevel) {
        Level level = null;

        switch (loggerLevel) {
            case DEBUG:
                level = org.apache.log4j.Level.DEBUG;
                break;
            case ERROR:
                level = org.apache.log4j.Level.ERROR;
                break;
            case FATAL:
                level = org.apache.log4j.Level.FATAL;
                break;
            case INFO:
                level = org.apache.log4j.Level.INFO;
                break;
            case TRACE:
                level = org.apache.log4j.Level.TRACE;
                break;
            case WARN:
                level = org.apache.log4j.Level.WARN;
                break;
        }

        return level;
    }

    public void error(Exception e) {
        logger.error( e.toString(), e );
    }

    public void warn(Exception e) {
        logger.warn(e);
    }

    public void fatal(Exception e) {
        logger.fatal(e);
    }

    public void debug(Exception e) {
        logger.debug(e);
    }

    public void trace(Exception e) {
        logger.trace(e);
    }

    public void info(Exception e) {
        logger.info(e);
    }
    
    public void error(String message) {
        logger.error( message );
    }

    public void warn(String message) {
        logger.warn( message );
    }

    public void fatal(String message) {
        logger.fatal( message );
    }

    public void debug(String message) {
        logger.debug( message );
    }

    public void trace(String message) {
        logger.trace(message);
    }

    public void info(String message) {
        logger.info( message );
    }
    
    public void error(String message, Exception e) {
        logger.error( message, e );
    }

    public void warn(String message, Exception e) {
        logger.warn(message, e);
    }

    public void fatal(String message, Exception e) {
        logger.fatal(message, e);
    }

    public void debug(String message, Exception e) {
        logger.debug(message, e);
    }

    public void trace(String message, Exception e) {
        logger.trace(message, e);
    }

    public void info(String message, Exception e) {
        logger.info(message, e);
    }
    
}
