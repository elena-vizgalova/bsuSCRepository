package by.bsu.computer.factorymethod;

import by.bsu.computer.entity.Computer;
import by.bsu.computer.entity.PcComputer;

/**
 *
 * @author Elena Vizgalova
 */
public class PcCreator implements ComputerCreator {

    private static  volatile PcCreator instance;

    private PcCreator() {
    }
    
    public static PcCreator getInstance() {
        if (null == instance) {
            
            synchronized(PcCreator.class) {
                if ( null == instance ) {
                    instance = new PcCreator();
                }
            }
            
        }
        return instance;
    }
    
    @Override
    public Computer factoryMethod() {
        return new PcComputer();
    }
    
}
