package by.bsu.patterncommand.command;

import by.bsu.patterncommand.paramwrapper.ParameterWrapper;

/**
 * The <tt>Command</tt> interface must implemented by any class that needs to
 * be a command.
 * 
 * @author Elena Vizgalova
 */
public interface Command {
    
    public ParameterWrapper execute( ParameterWrapper parameterWrapper );

}