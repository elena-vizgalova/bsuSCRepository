package by.epam.yummybook.command;

import by.epam.yummybook.command.logger.CommandLogger;
import by.epam.yummybook.command.paramname.IParameterName;
import by.epam.yummybook.controller.ParameterWrapper;
import by.epam.yummybook.controller.ParameterWrapperException;
import by.epam.yummybook.logic.LogicException;
import by.epam.yummybook.logic.SetLanguageLogic;
import by.epam.yummybook.manager.PageAddressManager;

/**
 * Singleton class, that processes language setting.
 * @see Commandable
 * @author Elena Vizgalova
 */
public class SetLanguageCommand implements Commandable {
    
    private static SetLanguageCommand instance;

    private SetLanguageCommand() {
    }
    
    public static synchronized SetLanguageCommand getInstance() {
        if ( null == instance ) {
            instance = new SetLanguageCommand();
        }
        return instance;
    }
    
    /**
     * Uses {@link SetLanguageLogic}.
     * Gets language and page's name parameters from <tt>parameterWrapper</tt>.
     * Sets language and returns a page.
     * @param parameterWrapper
     * @return a page to go.
     * @throws CommandException 
     */
    @Override
    public String execute( ParameterWrapper parameterWrapper ) throws CommandException {
        try {
            String language = (String) parameterWrapper.getRequestParam( IParameterName.LANGUAGE );
            String pageName = (String) parameterWrapper.getRequestParam( IParameterName.PAGE_NAME );
            
            SetLanguageLogic setLanguageLogic = SetLanguageLogic.getInstance();
            setLanguageLogic.setManagersLanguage( language );

            parameterWrapper.setSessionAttribute( IParameterName.LANGUAGE, language);
            
            PageAddressManager manager = PageAddressManager.getInstance();
            return manager.getProperty( pageName );
            
        } catch (LogicException | ParameterWrapperException ex) {
            CommandLogger logger = CommandLogger.getInstance( SetLanguageCommand.class );
            logger.error(ex);
            throw new CommandException( ex );
        }
    }
    
}
