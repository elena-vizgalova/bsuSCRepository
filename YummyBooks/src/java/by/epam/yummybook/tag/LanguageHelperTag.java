package by.epam.yummybook.tag;

import by.epam.yummybook.manager.MessageManager;
import java.util.Locale;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * Sets language on jsp page.
 * @author Elena Vizgalova
 */
public class LanguageHelperTag extends SimpleTagSupport {
    private String variableName;
    
    private final String LANGUAGE_PARAM = "language";
    private final String USERS_BROWSER_LANGAUGE = "userRequestLanguage";

    /**
     * Sets {@link MessageManager} locale and language in <tt>variableName</tt>.
     * If {@link LanguageHelperTag#LANGUAGE_PARAM} is empty, it sets user browser language,
     * otherwise it sets {@link LanguageHelperTag#LANGUAGE_PARAM}.
     */
    @Override
    public void doTag() throws JspException {
        String language = ( String ) getJspContext().findAttribute(LANGUAGE_PARAM);

        if ( null != language ) {
            MessageManager manager = MessageManager.getInstance();
            
            Locale locale = new Locale(language);
            manager.setLocale( locale );
        } else {
            language = (String) getJspContext().findAttribute( USERS_BROWSER_LANGAUGE );
            getJspContext().setAttribute( variableName, language );
            MessageManager manager = MessageManager.getInstance();
            
            Locale locale = new Locale(language);
            manager.setLocale( locale );
        }
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }
}
