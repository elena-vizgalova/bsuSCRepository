package by.bsu.web.command;

import by.bsu.web.controller.ParameterWrapper;

/**
 * The <tt>Commandable</tt> interface must implemented by any class that needs to
 * be a command. A Command is a class, that works with {@link ParameterWrapper } 
 * object, causes logic and returns result on jsp page.
 * 
 * @author Elena Vizgalova
 */
public interface Commandable {
    
    /**
     * Causes business logic for concrete command, works with {@link ParameterWrapper }
     * object, can get and set parameters in it.
     * 
     * @param parameterWrapper
     * @return a page, in what {@link Controller } needs to go.
     * @throws CommandException if <tt>parameterWrapper</tt> is null or 
     * when there were thrown logic exceptions.
     * 
     * @see ParameterWrapper
     */
    public String execute( ParameterWrapper parameterWrapper ) throws CommandException;

}