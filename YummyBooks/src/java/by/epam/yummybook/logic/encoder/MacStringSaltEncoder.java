package by.epam.yummybook.logic.encoder;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.CharacterEncoder;

/**
 * Uses {@link Mac} and {@link CharacterEncoder} objects for String encryption.
 * @see StringSaltEncoder
 * @author Elena Vizgalova
 */
public class MacStringSaltEncoder extends StringSaltEncoder {
    
    private String macAlgorithm;
    private Mac mac;
    private CharacterEncoder encoder;

    public MacStringSaltEncoder( String macAlgorithm, CharacterEncoder encoder ) throws NoSuchAlgorithmException {
        mac = Mac.getInstance( macAlgorithm );
        this.macAlgorithm = macAlgorithm;
        this.encoder = encoder;
    
    }
    
    /**
     * @see StringSaltEncoder#encryptWithSalt(byte[], byte[]) 
     */
    @Override
    public String encryptWithSalt( byte[] bytes, byte[] salt ) throws SaltEncryptorException {
        SecretKeySpec keySpec = new SecretKeySpec( salt, macAlgorithm );
        
        try {
            mac.init( keySpec );
        } catch (InvalidKeyException ex) {
            throw new SaltEncryptorException( ex );
        }
        
        byte[] result = mac.doFinal( bytes );

        return encoder.encode( result );
    }
    
}
