package ph.activelearning;

import ph.activelearning.reports.*;
import java.util.*;
import java.io.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * @author jonathanseanestaya
 */
public class PdfReportsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        ServletConfig config = getServletConfig();
        String driver = config.getInitParameter("jdbcDriver");
        String dbUsername = config.getInitParameter("username");
        String dbPassword = config.getInitParameter("password");
        String dbUrl = config.getInitParameter("dbDriver");
        Database db = new Database(driver, dbUrl, dbUsername, dbPassword);
        String path = getServletContext().getRealPath("/");
        String user = session.getAttribute("username").toString();
        String cipher = session.getAttribute("Cipher").toString();
        String key = session.getAttribute("Key").toString();

        String driver2 = config.getInitParameter("jdbcDriver2");
        String dbUsername2 = config.getInitParameter("username2");
        String dbPassword2 = config.getInitParameter("password2");
        String dbUrl2 = config.getInitParameter("dbDriver2");
        Database db2 = new Database(driver2, dbUrl2, dbUsername2, dbPassword2);

        
        switch(session.getAttribute("role").toString()) {
            case "admin": 
                    PdfReports admin = new AdminReport(db, path);
                    admin.generate();
                break;
            case "guest":
                    PdfReports guest = new GuestReport(db, path, user, cipher, key);
                    guest.generate();
                break;
            case "student":
            case "instructor":  
                UserReport report = new UserReport(db, db2, path, user);
                report.generate();
                break;
            default:
        }
        
        System.out.println(getServletContext().getRealPath("/"));
        response.sendRedirect("display.jsp");
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}