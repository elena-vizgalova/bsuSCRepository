/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package by.bsu.computer.factorymethod;

import by.bsu.computer.entity.Computer;
import by.bsu.computer.entity.ComputerType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

/**
 *
 * @author Len
 */
public class MacCreatorTest {
    
    ComputerCreator creator;
    
    @Before
    public void setUp() {
        creator = MacCreator.getInstance();
    }
    
    @Test
    public void factoryMethod() {
        Computer computer = creator.factoryMethod();
        
        Assert.assertEquals("Computer type must be 'PC'!", ComputerType.MAC, 
                computer.TYPE);
    }
}