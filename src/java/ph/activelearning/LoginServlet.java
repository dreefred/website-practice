package ph.activelearning;

import java.sql.*;
import java.io.IOException;
import java.util.Random;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author jonathanseanestaya
 */
public class LoginServlet extends HttpServlet {
    private Database db;
    private Connection conn;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        
        String driver = config.getInitParameter("jdbcDriver");
        String dbUsername = config.getInitParameter("username");
        String dbPassword = config.getInitParameter("password");
        String dbUrl = config.getInitParameter("dbDriver");
        
        db = new Database(driver, dbUrl, dbUsername, dbPassword);
        conn = db.getConnection();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("uname");
        String password = request.getParameter("upass");
        HttpSession session = request.getSession();
        
        Cookie cookieSession = new Cookie("JSESSIONID", session.getId());
        cookieSession.setMaxAge(60 * 5);
        response.addCookie(cookieSession);
        
        try {
            String certRedirect = certify(session, username, password);
            
            if(certRedirect.equals("CaptchaServlet")) { //og: success.jsp
                session.setAttribute("auth", "auth");
                session.setAttribute("username", username);
                session.setAttribute("CaptchaString", "");
            }
            
            response.sendRedirect(certRedirect);
        }
        catch(SQLException e ) {
            System.err.println(e);
        }
    }
    
    public String certify(HttpSession session, String username, String password) 
            throws SQLException {
        ServletConfig config = getServletConfig(); 
        boolean correctUser = false;
        boolean correctPass = false;
        PreparedStatement getUserStatement = conn.prepareStatement("SELECT USERNAME FROM USER_INFO");
        ResultSet getUserResult = getUserStatement.executeQuery();
        PreparedStatement getPassStatement = conn.prepareStatement("SELECT PASSWORD FROM USER_INFO WHERE USERNAME = ?");
        getPassStatement.setString(1, username);
        ResultSet getPassResult = getPassStatement.executeQuery();
        PreparedStatement getRoleStatement = conn.prepareStatement("SELECT ROLE FROM USER_INFO WHERE USERNAME = ?");
        getRoleStatement.setString(1, username);
        ResultSet getRoleResult = getRoleStatement.executeQuery();
        
        while(getUserResult.next()) {
            if(getUserResult.getString("USERNAME").equals(username)) {
                correctUser = true;
                break;
            }
        }
        
        while(getPassResult.next()) {
            String p = getPassResult.getString("PASSWORD");
            String s = SecurityHandling.decrypt(p, config.getInitParameter("Cipher"), config.getInitParameter("Key"));
            if(s.equals(password)) {
                System.out.println("Encrypted: " + p);
                System.out.println("Decrypted: "+ s);
                correctPass = true;
                break;
            }
        }
        
        if(correctUser && correctPass) {
            getRoleResult.next();
            session.setAttribute("auth", "auth");
            session.setAttribute("username", username);
            session.setAttribute("role", getRoleResult.getString("ROLE"));
            session.setAttribute("Cipher",config.getInitParameter("Cipher"));
            session.setAttribute("Key", config.getInitParameter("Key"));
            getRoleResult.close();
            return "success2.jsp"; //note og: CaptchaServlet (disabled captcha for faster debug)
        }
        else if(correctUser == false && password.isEmpty())
            return "error_1.jsp";
        else if(correctUser == true && correctPass == false)
            return "error_2.jsp";
        else
            return "error_3.jsp";
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}