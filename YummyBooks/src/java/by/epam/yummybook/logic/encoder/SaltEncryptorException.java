package by.epam.yummybook.logic.encoder;

import by.epam.yummybook.logic.LogicException;

/**
 * Exception class for SaltEncryptor classes.
 * @author Elena Vizgalova
 */
public class SaltEncryptorException extends LogicException {
    public SaltEncryptorException() {
    }
    
    public SaltEncryptorException(Throwable cause) {
        super(cause);
    }

    public SaltEncryptorException(String message, Throwable cause) {
        super(message, cause);
    }

    public SaltEncryptorException(String message) {
        super(message);
    }
}
