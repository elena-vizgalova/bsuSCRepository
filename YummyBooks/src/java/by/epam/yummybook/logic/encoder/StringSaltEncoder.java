package by.epam.yummybook.logic.encoder;


/**
 * Abstract class that provides method for encryption with salt.
 * @author Elena Vizgalova
 */
public abstract class StringSaltEncoder {

    /**
     * Encrypts given <tt>bytes</tt> with given <tt>salt</tt>.
     * @param bytes
     * @param salt
     * @return encrypted string
     * @throws SaltEncryptorException 
     */
    public abstract String encryptWithSalt( byte[] bytes, byte[] salt ) 
            throws SaltEncryptorException;
    
}
