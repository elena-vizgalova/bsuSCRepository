package by.epam.yummybook.filter;

import by.epam.yummybook.entity.LoginInfo;
import by.epam.yummybook.manager.MessageManager;
import by.epam.yummybook.manager.PageAddressManager;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Checks user permissions for administrator's pages.
 * @author Elena Vizgalova
 */
public class SecurityFilter implements Filter {
    
    private final String ERROR_VARIABLE = "errorMessage";
    
    public SecurityFilter() {
    }    

    /**
     * Checks <tt>login_info</tt> session parameter and if the user is not
     * administrator or he hasn't log in in system, redirect his request 
     * to error page with specific message.
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession( false );
        
            LoginInfo loginInfo = (LoginInfo) session.getAttribute( "login_info" );
            
            PageAddressManager configurationManager = PageAddressManager.getInstance();
            if ( null == loginInfo ) {
                String redirectPage = configurationManager.getProperty( 
                        PageAddressManager.ERROR_PAGE );
                MessageManager messageManager = MessageManager.getInstance();
                session.setAttribute( ERROR_VARIABLE, messageManager.getProperty( 
                        MessageManager.ERROR_NO_PERMISSIONS ) );
                redirectPage = redirectPage.replaceFirst("/", "/YummyBooks/");
                httpResponse.sendRedirect( redirectPage );

            } else if ( !loginInfo.isIsAdmin() ) {
                String redirectPage = configurationManager.getProperty( 
                        PageAddressManager.ERROR_PAGE );
                MessageManager messageManager = MessageManager.getInstance();
                session.setAttribute( ERROR_VARIABLE, messageManager.getProperty( 
                        MessageManager.ERROR_NO_PERMISSIONS ) );
                redirectPage = redirectPage.replaceFirst("/", "/YummyBooks/");
                httpResponse.sendRedirect( redirectPage );
            }
        
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void destroy() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

}
