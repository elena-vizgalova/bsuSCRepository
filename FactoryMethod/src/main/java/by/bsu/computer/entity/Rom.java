package by.bsu.computer.entity;

/**
 *
 * @author Elena Vizgalova
 */
public class Rom {
    private double readingSpeed;
    private String typeName;

    /**
     * @return the readingSpeed
     */
    public double getReadingSpeed() {
        return readingSpeed;
    }

    /**
     * @return the typeName
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * @param readingSpeed the readingSpeed to set
     */
    public void setReadingSpeed(double readingSpeed) {
        this.readingSpeed = readingSpeed;
    }

    /**
     * @param typeName the typeName to set
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    
}
