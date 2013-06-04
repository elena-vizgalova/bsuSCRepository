package by.bsu.web.command;

import by.bsu.web.controller.ParameterWrapper;
import by.bsu.web.manager.PageAddressManager;

/**
 * Singleton class that indexes that there are no command.
 * Class for forwarding request into index page.
 * @author Elena Vizgalova
 */
class NoCommand implements Commandable {

    private static NoCommand instance;

    private NoCommand() {
    }
    
    public static synchronized NoCommand getInstance() {
        if ( null == instance ) {
            instance = new NoCommand();
        }
        return instance;
    }

    /**
     * @param param
     * @return {@link PageAddressManager#INDEX_PAGE}
     * @throws CommandException 
     */
    @Override
    public String execute(ParameterWrapper param) throws CommandException {
        
        String page = PageAddressManager.getInstance().getProperty(
                PageAddressManager.HOME_PAGENAME );
        
        return page;
    }
    
}
