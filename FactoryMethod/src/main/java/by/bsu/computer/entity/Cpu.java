package by.bsu.computer.entity;

/**
 *
 * @author Elena Vizgalova
 */
public class Cpu {
    private int cacheSize;
    private int kernelNumber;
    private int firmName;

    /**
     * @return the cacheSize
     */
    public int getCacheSize() {
        return cacheSize;
    }

    /**
     * @return the kernelNumber
     */
    public int getKernelNumber() {
        return kernelNumber;
    }

    /**
     * @return the firmName
     */
    public int getFirmName() {
        return firmName;
    }

    /**
     * @param cacheSize the cacheSize to set
     */
    public void setCacheSize(int cacheSize) {
        this.cacheSize = cacheSize;
    }

    /**
     * @param kernelNumber the kernelNumber to set
     */
    public void setKernelNumber(int kernelNumber) {
        this.kernelNumber = kernelNumber;
    }

    /**
     * @param firmName the firmName to set
     */
    public void setFirmName(int firmName) {
        this.firmName = firmName;
    }
    
}
