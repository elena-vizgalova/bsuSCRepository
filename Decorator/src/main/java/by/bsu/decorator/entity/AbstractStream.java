package by.bsu.decorator.entity;

/**
 *
 * @author Elena Vizgalova
 */
public abstract class AbstractStream {

    private String streamContainer;

    public String getStreamContainer() {
        return streamContainer;
    }

    public void setStreamContainer(String streamContainer) {
        this.streamContainer = streamContainer;
    }
    
    public int putByte( byte[] bytes ) {
        return bytes.length;
    }
    
    public int putInt( int number ) {
        return number;
    }
    
    public abstract String handleBufferFull();
}
