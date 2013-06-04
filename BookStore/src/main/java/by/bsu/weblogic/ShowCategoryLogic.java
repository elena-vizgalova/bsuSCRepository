package by.bsu.web.logic;

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
public class ShowCategoryLogic {
    
    public String generatePage() {
        try {
            PageAddressManager addressManager = PageAddressManager.getInstance();
            final String XSL_FILENAME = addressManager.getProperty( PageAddressManager.XSL_CATEGORIES );
            final String XML_FILENAME = addressManager.getProperty( PageAddressManager.XML_CATEGORIES );

            //Открыть входной файл и таблицу стилей
            final File xmlFile = new File( XML_FILENAME );
            final File xsltFile = new File( XSL_FILENAME );

            //Чтение данных в JAXP через Source 
            final Source xmlSource = new StreamSource(xmlFile);
            final Source xsltSource = new StreamSource(xsltFile);

            //Открыть процессор XSLT 
            final TransformerFactory transFact = TransformerFactory.newInstance();
            final Transformer trans = transFact.newTransformer(xsltSource);


            StreamResult result = new StreamResult( new File( addressManager.getProperty( PageAddressManager.GENERATE_CATEGORIES ) ) );
            trans.transform(xmlSource, result);
            return addressManager.getProperty( PageAddressManager.CATEGORIES_PAGENAME );
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(ShowCategoryLogic.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(ShowCategoryLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
