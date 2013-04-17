package by.bsu.patterncommand.command;

import by.bsu.patterncommand.paramwrapper.ParameterWrapper;

/**
 * Singleton class that indexes that there are no command.
 * @author Elena Vizgalova
 */
class NoCommand implements Command {

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
    public ParameterWrapper execute(ParameterWrapper param) {
        return param;
    }
    
}
