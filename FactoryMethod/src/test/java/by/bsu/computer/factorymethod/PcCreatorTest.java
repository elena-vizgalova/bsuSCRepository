package by.bsu.computer.factorymethod;

import by.bsu.computer.entity.Computer;
import by.bsu.computer.entity.ComputerType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Elena Vizgalova
 */
public class PcCreatorTest {
    
    ComputerCreator creator;
    
    @Before
    public void setUp() {
        creator = PcCreator.getInstance();
    }
    
    @Test
    public void factoryMethod() {
        Computer computer = creator.factoryMethod();
        
        Assert.assertEquals("Computer type must be 'PC'!", ComputerType.PC, 
                computer.TYPE);
    }
    
}
