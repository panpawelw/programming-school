package pl.pjm77.misc;

import javax.servlet.*;
import java.io.IOException;

public class RequestEncodeFilter implements Filter {
    //FilterConfig object
    private FilterConfig filterConfig = null;

    //Default constructor
    public RequestEncodeFilter() {
        System.out.println("Request response encoder Filter object has been created");
    }

    //Intitialization method
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //Setting the character set for the request
        request.setCharacterEncoding("UTF-8");

        // pass the request on
        chain.doFilter(request, response);

        //Setting the character set for the response
        response.setContentType("text/html; charset=UTF-8");
    }

    public void destroy() {
        this.filterConfig = null;
    }
}