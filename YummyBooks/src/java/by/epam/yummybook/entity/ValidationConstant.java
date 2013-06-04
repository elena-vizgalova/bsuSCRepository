package by.epam.yummybook.entity;

/**
 * <tt>ValidationConstant</tt> interface contains all constants that are
 * used for entity's fields validation.
 * @author Elena Vizgalova
 */
public interface ValidationConstant {
    
    /**
     * 'ISBN' + ISBN number of book, which can be from 9 to 13 digit number.
     */
    public final String BOOK_REGEX = "ISBN[\\d]{9,13}";
    
    /**
     * Must be in format 'XXXXX-XXXXXXX', where the first 5 digits are 
     * the country and city code, the last 7 - the phone number.
     */
    public final String PHONE_REGEX = "[0-9]{5}\\-[0-9]{7}";
    
    /**
     * Customer name must be him or her name or surname, or
     * the name and the surname with middle name or without.
     * Must be started with big letter. Can use only latish (english alphabet) 
     * or cyrillyc (russian alphabet) characters.
     */
    public final String CUSTOMER_NAME_REGEX = "^[A-ZА-Я]{1}[a-zа-я]+(?:[ ][A-ZА-Я]{1}[a-zа-я]+(?:[-][A-ZА-Я]{1}[a-zа-я]+){0,})*";
    
    public final String EMAIL_REGEX = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$";
    
    /**
     * Username must consist latish characters and numbers, that can be separated by
     * '.', '_' or '-'. Must be started from latish letter.
     */
    public final String USERNAME_REGEX = "^[a-zA-Z0-9]+([._-]?[a-zA-Z0-9])*$";
    
}
