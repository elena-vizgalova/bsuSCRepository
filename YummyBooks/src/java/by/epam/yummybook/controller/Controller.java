package by.epam.yummybook.controller;

import by.epam.yummybook.command.CommandException;
import by.epam.yummybook.command.CommandGetter;
import by.epam.yummybook.command.Commandable;
import by.epam.yummybook.controller.logger.ControllerLogger;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet class that controls requests.
 * @author Elena Vizgalova
 */
public class Controller extends HttpServlet {

   /**
     * Parameter's name for servlet command, that used on jsp page.
     */
    private static final String COMMAND_PARAM = "command";
    
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession( true );
            
            ParameterWrapper parameterWrapper = new ParameterWrapper();
            parameterWrapper.setRequest( request );
            parameterWrapper.setResponse( response );
            parameterWrapper.setSession( session );
            
            CommandGetter commandGetter = CommandGetter.getInstance();
            
            String commandName = parameterWrapper.getRequestParam( COMMAND_PARAM );
            String servletPath = parameterWrapper.getServletPath();
            
            Commandable command = commandGetter.getCommand( commandName, servletPath );
            String page = command.execute( parameterWrapper );
            
            RequestDispatcher disp = request.getRequestDispatcher( page );
            disp.forward( request, response );
        } catch (CommandException ex) {
            ControllerLogger logger = ControllerLogger.getInstance( Controller.class );
            logger.error( ex );
        } catch (ServletException | IOException | ParameterWrapperException ex) {
            ControllerLogger logger = ControllerLogger.getInstance( Controller.class );
            logger.error( ex );
        }
    }

    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest( request, response );
    }
     
}
