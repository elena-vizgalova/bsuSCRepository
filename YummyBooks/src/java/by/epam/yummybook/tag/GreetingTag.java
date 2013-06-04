package by.epam.yummybook.tag;

import by.epam.yummybook.entity.LoginInfo;
import by.epam.yummybook.manager.MessageManager;
import by.epam.yummybook.tag.logger.TagLogger;
import java.util.Locale;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * Writes specific greeting message on page.
 * @author Elena Vizgalova
 */
public class GreetingTag extends SimpleTagSupport {
    
    private final String LANG_PARAM = "language";
    private final String LOGIN_INFO_PARAM = "login_info";
    
    /**
     * Writes specific greeting message on page.
     * If there is no {@link GreetingTag#LOGIN_INFO_PARAM} in JspContext, 
     * it writes {@link MessageManager#GREETING_GUEST_TITLE}, otherwise it 
     * writes {@link MessageManager#GREETING_USER_TITLE}, replacing 
     * {@link MessageManager#USERNAME_TO_CHANGE} on username from loginInfo.
     */
    @Override
    public void doTag() throws JspException {
        JspWriter out = getJspContext().getOut();
        try {
            String language = (String) getJspContext().findAttribute( LANG_PARAM );
            Locale locale = new Locale(language);
            
            MessageManager manager = MessageManager.getInstance();
            manager.setLocale(locale);
            
            LoginInfo loginInfo = (LoginInfo) getJspContext().findAttribute( LOGIN_INFO_PARAM );
            String greeting;
            
            if ( null == loginInfo ) {
                
                greeting = manager.getProperty( MessageManager.GREETING_GUEST_TITLE );
                
            } else {
                
                greeting = manager.getProperty( MessageManager.GREETING_USER_TITLE );
                greeting = greeting.replace( MessageManager.USERNAME_TO_CHANGE, loginInfo.getUsername() );
                
            }
            
            out.write( greeting );

        } catch (java.io.IOException ex) {
            TagLogger logger = TagLogger.getInstance( GreetingTag.class );
            logger.error("Error in GreetingTag tag", ex);
            throw new JspException("Error in GreetingTag tag", ex);
        }
    }
}
