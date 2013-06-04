package by.epam.yummybook.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Checks encoding and sets UTF-8 encoding.
 * @author Elena Vizgalova
 */
public class EncodingFilter implements Filter {

    public static final String ENCODING_UTF8 = "UTF-8";

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

    /**
     * Checks request encoding and if it's not UTF-8, sets a UTF-8 encoding.
     *
     * @param request a ServletRequest
     * @param response a ServletResponse
     * @param chain a FilterChain
     * @throws IOException a IOException
     * @throws ServletException a ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String encoding = request.getCharacterEncoding();

        if (!ENCODING_UTF8.equals(encoding)) {
            request.setCharacterEncoding(ENCODING_UTF8);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
