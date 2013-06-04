package by.bsu.web.controller;

import by.bsu.web.command.CommandException;
import by.bsu.web.command.CommandGetter;
import by.bsu.web.command.Commandable;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Elena Vizgalova
 */
public class Controller extends HttpServlet {
    
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
            
            String servletPath = parameterWrapper.getServletPath();
            
            Commandable command = commandGetter.getCommand( servletPath );
            String page = command.execute( parameterWrapper );
            
            RequestDispatcher disp = request.getRequestDispatcher( page );
            disp.forward( request, response );
        } catch (CommandException ex) {
        //} catch (ServletException | IOException | ParameterWrapperException ex) {
        } catch (ServletException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParameterWrapperException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
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
