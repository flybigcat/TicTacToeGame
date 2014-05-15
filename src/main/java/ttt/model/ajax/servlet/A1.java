package ttt.model.ajax.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/A1")
public class A1 extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public void doGet( HttpServletRequest request, HttpServletResponse response )
        throws IOException, ServletException
    {
        double d = Math.random();
        response.setContentType( "text/plain" );
        if(d>0.5)
        response.getWriter().println( "Perfect" );
        if(d==0.5)
            response.getWriter().println( "Good" );
        if(d<0.5)
            response.getWriter().println( "Good" );

    }

    public void doPost( HttpServletRequest request, HttpServletResponse response )
        throws IOException, ServletException
    {
        doGet( request, response );
    }

}
