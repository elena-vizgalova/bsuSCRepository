package by.bsu.decorator.entity;

/**
 *
 * @author Elena Vizgalova
 */
public class NetworkStream extends AbstractStream {

    @Override
    public String handleBufferFull() {
        //TODO: writing data into Network stream 
        return "writing into Network Stream";
    }
    
}
