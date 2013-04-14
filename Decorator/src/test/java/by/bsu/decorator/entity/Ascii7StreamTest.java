package by.bsu.decorator.entity;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Elena Vizgalova
 */
public class Ascii7StreamTest {
    
    private Ascii7Stream stream;
    
    @Test
    public void handleBuffeFullConstructor() {
        stream = new Ascii7Stream(new MemoryStream());
        String expectedResult = "converting to ASCII7 writing into Memory";
        
        Assert.assertEquals("The result must be " + expectedResult, 
                expectedResult, stream.handleBufferFull());
    }
    
    public void handleBuffeFullSet() {
        stream = new Ascii7Stream();
        AbstractStream networkStream = new NetworkStream();
        stream.setStream(networkStream);
        
        String expectedResult = "converting to ASCII7 writing into Network";
        
        Assert.assertEquals("The result must be " + expectedResult, 
                expectedResult, stream.handleBufferFull());
    }
}
