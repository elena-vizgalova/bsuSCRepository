package by.bsu.decorator.entity;

/**
 *
 * @author Elena Vizgalova
 */
public class MemoryStream extends AbstractStream {

    @Override
    public String handleBufferFull() {
        //TODO: writing data into Memory
        return "writing into Memory";
    }
    
}
