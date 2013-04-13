package by.bsu.computer.entity;

/**
 *
 * @author Elena Vizgalova
 */
public abstract class Computer {
    public final ComputerType TYPE;
    
    private Motherboard motherboard;
    private Cpu cpu;
    private Rom rom;

    public Computer(ComputerType TYPE) {
        this.TYPE = TYPE;
    }
    
    
    
}
