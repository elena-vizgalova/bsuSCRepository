package by.bsu.web.command;

import by.bsu.web.controller.ParameterWrapper;
import by.bsu.web.logic.ShowCategoryLogic;
import by.bsu.web.manager.PageAddressManager;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author Elena Vizgalova
 */
public class ShowCategoriesCommand implements Commandable {

    @Override
    public String execute(ParameterWrapper parameterWrapper) throws CommandException {
            ShowCategoryLogic logic = new ShowCategoryLogic();
            String page = logic.generatePage();
            return page;

    }
    
}
