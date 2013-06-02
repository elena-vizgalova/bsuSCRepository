package by.bsu.abstractfactory.entity.mac;

import by.bsu.abstractfactory.entity.Computer;
import by.bsu.abstractfactory.entity.ComputerType;


/**
 *
 * @author Elena Vizgalova
 */
public class MacComputer extends Computer{

    @Override
    public ComputerType getType() {
        return ComputerType.MAC;
    }
    
}
