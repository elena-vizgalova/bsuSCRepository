package by.epam.yummybook.logic;

import by.epam.yummybook.logic.logger.LogicLogger;
import by.epam.yummybook.manager.MessageManager;
import by.epam.yummybook.manager.PageAddressManager;
import java.util.Locale;

/**
 * Provides methods for setting language.
 * @author Elena Vizgalova
 */
public class SetLanguageLogic {
    
    private static SetLanguageLogic instance;
    
    private SetLanguageLogic(){
    }
    
    public static synchronized SetLanguageLogic getInstance() {
        if ( null == instance ) {
            instance = new SetLanguageLogic();
        }
        return instance;
    }
    
    /**
     * Sets locale for {@link PageAddressManager} and {@link MessageManager}.
     * @param language
     * @throws LogicException when <tt>language</tt> is <code>null</code> or 
     * empty.
     */
    public void setManagersLanguage( String language ) throws LogicException {
        
        if ( null == language ) {
            LogicLogger logger = LogicLogger.getInstance(SetLanguageLogic.class);
            logger.error("Null language error!");
            throw new LogicException( "Language can't be null!" );
        }
        if ( language.isEmpty() ) {
            LogicLogger logger = LogicLogger.getInstance(SetLanguageLogic.class);
            logger.error("Empty language error!");
            throw new LogicException( "Language can't be empty!" );
        }
        
        Locale locale = new Locale( language );
            
        PageAddressManager manager = PageAddressManager.getInstance();
        manager.setLocale( locale );

        MessageManager messageManager = MessageManager.getInstance();
        messageManager.setLocale( locale );
        
    }
  
}
