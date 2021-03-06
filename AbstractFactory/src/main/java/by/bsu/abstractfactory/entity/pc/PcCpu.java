package by.bsu.abstractfactory.entity.pc;

import by.bsu.abstractfactory.entity.ComputerType;
import by.bsu.abstractfactory.entity.Cpu;

/**
 *
 * @author Elena Vizgalova
 */
public class PcCpu extends Cpu{

    @Override
    public ComputerType getSupportedComputerType() {
        return ComputerType.PC;
    }
    
}
