package by.bsu.abstractfactory.entity.mac;

import by.bsu.abstractfactory.entity.ComputerType;
import by.bsu.abstractfactory.entity.Motheboard;

/**
 *
 * @author Elena Vizgalova
 */
public class MacMotherboard extends Motheboard{

    @Override
    public ComputerType getSupportedComputerType() {
        return ComputerType.MAC;
    }
    
}
