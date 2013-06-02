package by.bsu.abstractfactory.entity.pc;

import by.bsu.abstractfactory.entity.ComputerType;
import by.bsu.abstractfactory.entity.Rom;

/**
 *
 * @author Elena Vizgalova
 */
public class PcRom extends Rom{

    @Override
    public ComputerType getSupportedComputerType() {
        return ComputerType.PC;
    }
    
}
