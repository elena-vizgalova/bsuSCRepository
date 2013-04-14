package by.bsu.decorator.entity;

/**
 *
 * @author Elena Vizgalova
 */
public class CompressingStream extends StreamDecorator {

    private AbstractStream stream;

    public CompressingStream() {
        stream = null;
    }

    public CompressingStream(AbstractStream stream) {
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
        return "compressing " + stream.handleBufferFull();
    }
    
}
