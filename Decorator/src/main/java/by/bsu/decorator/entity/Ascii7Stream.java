package by.bsu.decorator.entity;

/**
 *
 * @author Elena Vizgalova
 */
public class Ascii7Stream extends StreamDecorator {

    private AbstractStream stream;

    public Ascii7Stream() {
        stream = null;
    }

    public Ascii7Stream(AbstractStream stream) {
        this.stream = stream;
    }

    public AbstractStream getStream() {
        return stream;
    }

    public void setStream(AbstractStream stream) {
        this.stream = stream;
    }
    
    @Override
    public String handleBufferFull() {
        return "converting to ASCII7 " + stream.handleBufferFull();
    }
    
}
