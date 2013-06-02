package by.bsu.abstractfactory.entity.mac;

import by.bsu.abstractfactory.entity.ComputerType;
import by.bsu.abstractfactory.entity.Rom;

/**
 *
 * @author Elena Vizgalova
 */
public class MacRom extends Rom{

    @Override
    public ComputerType getSupportedComputerType() {
        return ComputerType.MAC;
    }
    
}
