package by.bsu.decorator.entity;

/**
 *
 * @author Elena Vizgalova
 */
public abstract class StreamDecorator extends AbstractStream {
    
    public abstract String handleBufferFull();

}
