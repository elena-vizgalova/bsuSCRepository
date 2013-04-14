package by.bsu.decorator.entity;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Elena Vizgalova
 */
public class CompressingStreamTest {
    private CompressingStream stream;
    
    @Test
    public void compressingStreamHandleBufferFull() {
        stream = new CompressingStream(new FileStream());
        String expectedResult = "compressing writing into File";
        
        Assert.assertEquals("The result must be " + expectedResult, 
                expectedResult, stream.handleBufferFull());
    }
    
    public void handleBuffeFullSet() {
        stream = new CompressingStream();
        AbstractStream networkStream = new NetworkStream();
        stream.setStream(networkStream);
        
        String expectedResult = "compressing writing into Network";
        
        Assert.assertEquals("The result must be " + expectedResult, 
                expectedResult, stream.handleBufferFull());
    }
}
