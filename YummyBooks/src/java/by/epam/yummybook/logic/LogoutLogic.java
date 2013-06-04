package by.epam.yummybook.logic;

import by.epam.yummybook.controller.ParameterWrapper;

/**
 * Provides operations for logout.
 * @author Elena Vizgalova
 */
public class LogoutLogic {
    
    private static LogoutLogic instance;

    private LogoutLogic() {
    }
    
    public static synchronized LogoutLogic getInstance() {
        if ( null == instance ) {
            instance = new LogoutLogic();
        }
        return instance;
    }
    
    /**
     * Invalidates session and creates new.
     * @param parameterWrapper 
     */
    public void logout( ParameterWrapper parameterWrapper ) {
        parameterWrapper.invalidateSession();
        parameterWrapper.createNewSession();
    }
    
}
