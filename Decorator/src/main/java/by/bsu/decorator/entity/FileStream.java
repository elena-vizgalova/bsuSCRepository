package by.bsu.decorator.entity;

/**
 *
 * @author Elena Vizgalova
 */
public class FileStream extends AbstractStream {

    @Override
    public String handleBufferFull() {
        //TODO: writing data into File
        return "writing into File";
    }
    
}
