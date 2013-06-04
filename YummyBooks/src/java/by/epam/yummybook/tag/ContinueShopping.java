package by.epam.yummybook.tag;

import by.epam.yummybook.entity.Category;
import by.epam.yummybook.manager.PageAddressManager;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * Sets page to go for 'continue shopping' button.
 * @author Elena Vizgalova
 */
public class ContinueShopping extends SimpleTagSupport {

    private String variableName;
    private final String SELECTED_CATEGORY_ATTR = "selectedCategory";

    /**
     * If there is {@link ContinueShopping#SELECTED_CATEGORY_ATTR} in session,
     * it sets to <tt>variableName</tt> {@link PageAddressManager#CATEGORY_PAGE},
     * otherwise it sets to <tt>variableName</tt> {@link PageAddressManager#INDEX_PAGE}.
     */
    @Override
    public void doTag() throws JspException {
        Category selectedCategory = (Category) getJspContext().findAttribute(SELECTED_CATEGORY_ATTR);

        PageAddressManager configurationManager = PageAddressManager.getInstance();
        String value;
        if (null == selectedCategory) {
            value = "/" + configurationManager.getProperty(PageAddressManager.INDEX_PAGE);
        } else {
            value = configurationManager.getProperty(PageAddressManager.CATEGORY_PAGE);
        }
        
        getJspContext().setAttribute( variableName, value );
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }
}
