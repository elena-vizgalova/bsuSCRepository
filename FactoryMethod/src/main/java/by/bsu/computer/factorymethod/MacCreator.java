package by.bsu.computer.factorymethod;

import by.bsu.computer.entity.Computer;
import by.bsu.computer.entity.MacComputer;

/**
 *
 * @author Elena Vizgalova
 */
public class MacCreator implements ComputerCreator {

    private static  volatile MacCreator instance;

    private MacCreator() {
    }
    
    public static MacCreator getInstance() {
        if (null == instance) {
            
            synchronized(MacCreator.class) {
                if ( null == instance ) {
                    instance = new MacCreator();
                }
            }
            
        }
        return instance;
    }
    
    @Override
    public Computer factoryMethod() {
        return new MacComputer();
    }
    
}
