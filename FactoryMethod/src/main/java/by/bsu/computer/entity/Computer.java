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

    public Motherboard getMotherboard() {
        return motherboard;
    }

    public void setMotherboard(Motherboard motherboard) {
        this.motherboard = motherboard;
    }

    public Cpu getCpu() {
        return cpu;
    }

    public void setCpu(Cpu cpu) {
        this.cpu = cpu;
    }

    public Rom getRom() {
        return rom;
    }

    public void setRom(Rom rom) {
        this.rom = rom;
    }
    
    
    
}
