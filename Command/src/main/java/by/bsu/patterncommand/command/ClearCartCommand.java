package by.bsu.patterncommand.command;

import by.bsu.patterncommand.entity.Order;
import by.bsu.patterncommand.logic.ClearCartLogic;
import by.bsu.patterncommand.paramwrapper.ParameterWrapper;

/**
 *
 * @author Elena Vizgalova
 */
public class ClearCartCommand implements Command {
    
    private static volatile ClearCartCommand instance;

    private ClearCartCommand() {
    }
    
    public static ClearCartCommand getInstance() {
        
        if ( null == instance ) {
            synchronized( ClearCartLogic.class ) {
                if ( null == instance ) {
                    instance = new ClearCartCommand();
                }
            }
        }
        
        return instance;
    }
    
    @Override
    public ParameterWrapper execute(ParameterWrapper parameterWrapper) {
        ClearCartLogic clearCartLogic = ClearCartLogic.getInstance();
        
        Order order = parameterWrapper.getOrder();
        order = clearCartLogic.clearCart( order );
        parameterWrapper.setOrder( order );
        
        return parameterWrapper;
    }
    
}
