package by.epam.yummybook.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * TO that handles request, response and session.
 * @author Elena Vizgalova
 */
public class ParameterWrapper {
    
    /**
     * Request value.
     */
    private HttpServletRequest request;
    
    /**
     * Response value.
     */
    private HttpServletResponse response;
    
    /**
     * Session value.
     */
    private HttpSession session;
    
    /**
     * Sets request.
     * 
     * @param request to set
     * @throws ParameterWrapperException, if {@code null == request }
     */
    public void setRequest( HttpServletRequest request ) throws ParameterWrapperException {
        if ( null == request ) {
            throw new ParameterWrapperException( "Request to set can't be null!" );
        }
        
        this.request = request;
    }
    
    /**
     * Sets response.
     * @param response to set
     * @throws ParameterWrapperException, if {@code null == response }
     */
    public void setResponse( HttpServletResponse response ) throws ParameterWrapperException {
        if ( null == response ) {
            throw new ParameterWrapperException( "Response to set can't be null!" );
        }
        
        this.response = response;
        
    }
    
    /**
     * Sets session.
     * @param session
     * @throws ParameterWrapperException, if {@code null == session }
     */
    public void setSession( HttpSession session ) throws ParameterWrapperException {
        if ( null == session ) {
            throw new ParameterWrapperException( "Session to set can't be null!" );
        }
        
        this.session = session;
    }
    
    /**
     * Sets parameter to request.
     * @param paramName to set
     * @param paramValue to set
     * @throws ParameterWrapperException, if {@code null == paramName } or
     *         or {@code paramName.isEmpty() } ,or {@code null == paramValue } 
     */
    public void setRequestParam( String paramName, Object paramValue ) throws ParameterWrapperException {
        
        if ( null == paramName ) {
            throw new ParameterWrapperException( "paramName to set can't be null!" );
        }
        
        if ( paramName.isEmpty() ) {
            throw new ParameterWrapperException( "paramName to set can't be empty String!" );
        }
        
        if ( null == paramValue ) {
            throw new ParameterWrapperException( "paramValue to set can't be null!" );
        }
        
        request.setAttribute( paramName, paramValue );
    }
    
    /**
     * Sets attribute to session.
     * @param paramName name for attribute to set
     * @param paramValue attribute value to set
     * @throws ParameterWrapperException, if {@code null == paramName } 
     *         or {@code paramName.isEmpty() }, or {@code null == paramValue }
     */
    public void setSessionAttribute( String paramName, Object paramValue ) throws ParameterWrapperException {
        
        if ( null == paramName ) {
            throw new ParameterWrapperException( "paramName to set can't be null!" );
        }
        
        if ( paramName.isEmpty() ) {
            throw new ParameterWrapperException( "paramName to set can't be empty String!" );
        }
        
        if ( null == paramValue ) {
            throw new ParameterWrapperException( "paramValue to set can't be null!" );
        }
        
        session.setAttribute( paramName, paramValue );
    }

    /**
     * Calls invalidate method on session.
     * 
     * @see javax.​servlet.​http.​HttpSession#invalidate()
     */
    public void invalidateSession() {
        session.invalidate();
    }
    
    public void createNewSession() {
        session = request.getSession( true );
    }
    
    /**
     * Gets parameter's value by given parameter name from request 
     * calling {@code getParameter(String name) } on request.
     * 
     * @param paramName or <tt>null</tt> if parameter doesn't exists
     * @throws ParameterWrapperException, if paramName is null.
     * 
     * @see javax.​servlet.​ServletRequest#getParameter(String name)
     */
    public String getRequestParam( String paramName ) throws ParameterWrapperException {
        
        if ( null == paramName ) {
            throw new ParameterWrapperException( "Parameter's name can't be null!" );
        }
        
        return request.getParameter( paramName );
        
    }
    
    /**
     * Gets attribute value by given name calling {@code getAttribute(String name) }
     * on request.
     * 
     * @param attributeName or <tt>null</tt> if there is no attribute with the given name
     * @throws ParameterWrapperException, if attribute name is null
     * 
     * @see javax.​servlet.​ServletRequest#getAttribute(String name)
     */
    public Object getRequestAttribute( String attributeName ) throws ParameterWrapperException {
        
        if ( null == attributeName ) {
            throw new ParameterWrapperException( "Attribute's name can't be null!" );
        }
        
        if ( attributeName.isEmpty() ) {
            throw new ParameterWrapperException( "Attribute's name can't be empty!" );
        }

        return request.getAttribute( attributeName );
    }
    
    /**
     * Returns the query string that is contained in the request URL after the path 
     * calling getQueryString() on request.
     * 
     * @see javax.​servlet.​http.​HttpServletRequest#getQueryString()
     */
    public String getQueryStringFromRequest() {
        return request.getQueryString();
    }
    
    /**
     * Gets parameter value with given name from session calling getAttribute(String name)
     * on session.
     * 
     * @param attributeName
     * @throws ParameterWrapperException, if attibuteName is null.
     * 
     * @return the object or <tt>null</tt> if there is no attribute with <tt>attributeName</tt>
     * @see javax.​servlet.​http.​HttpSession#getAttribute(String name)
     */
    public Object getSessionAttribute( String attributeName ) throws ParameterWrapperException {
        
        if ( null == attributeName ) {
            throw new ParameterWrapperException( "Attribute name can't be null!" );
        }
        
        return session.getAttribute( attributeName );
    }
    
    /**
     * Returns the part of this request's URL that calls the servlet.
     * 
     * @see javax.​servlet.​http.​HttpServletRequest#getServletPath()
     */
    public String getServletPath() {
        return request.getServletPath();
    }
    
    
    
}
