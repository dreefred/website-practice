package ph.activelearning;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SignupServlet extends HttpServlet {

    private Database db1, db2;
    private Connection conn1, conn2;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        String driver1 = config.getInitParameter("jdbcDriver");
        String dbUsername1 = config.getInitParameter("username");
        String dbPassword1 = config.getInitParameter("password");
        String dbUrl1 = config.getInitParameter("dbDriver");

        db1 = new Database(driver1, dbUrl1, dbUsername1, dbPassword1);
        conn1 = db1.getConnection();

        String driver2 = config.getInitParameter("jdbcDriver2");
        String dbUsername2 = config.getInitParameter("username2");
        String dbPassword2 = config.getInitParameter("password2");
        String dbUrl2 = config.getInitParameter("dbDriver2");

        db2 = new Database(driver2, dbUrl2, dbUsername2, dbPassword2);
        conn2 = db2.getConnection();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("uname");
        String password = request.getParameter("upass");
        String cpassword = request.getParameter("confirm-upass");
        String role = request.getParameter("role");
        HttpSession session = request.getSession();

        try {
            String certRedirect = certify(session, username, password, cpassword, role);
            response.sendRedirect(certRedirect);
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    public String certify(HttpSession session, String username, String password, String cpassword, String role) throws SQLException {
        ServletConfig config = getServletConfig();
        boolean userExists = false;
        PreparedStatement getUserStatement = conn1.prepareStatement("SELECT USERNAME FROM USER_INFO");
        ResultSet getUserResult = getUserStatement.executeQuery();

        while (getUserResult.next()) {
            if (getUserResult.getString("USERNAME").equals(username)) {
                userExists = true;
                break;
            }
        }
        if (!userExists) {
            if (password.equals(cpassword)) {
                String encryptedPassword = SecurityHandling.encrypt(password, config.getInitParameter("Cipher"), config.getInitParameter("Key"));
                PreparedStatement insertUserStatement = conn1.prepareStatement("INSERT INTO USER_INFO (username, password, role) VALUES(?, ?, ?)");
                insertUserStatement.setString(1, username);
                insertUserStatement.setString(2, encryptedPassword);
                insertUserStatement.setString(3, role);
                insertUserStatement.executeUpdate();
                session.setAttribute("auth", "auth");
                session.setAttribute("username", username);
                session.setAttribute("role", role);

                String userId = generateUserId(role, conn2);
                java.sql.Date dateCreated = new java.sql.Date(System.currentTimeMillis());
                PreparedStatement insertUserStatement2 = conn2.prepareStatement("INSERT INTO USER_INFO (user_id, username, role, date_created) VALUES(?, ?, ?, ?)");
                insertUserStatement2.setString(1, userId);
                insertUserStatement2.setString(2, username);
                insertUserStatement2.setString(3, role);
                insertUserStatement2.setDate(4, dateCreated);
                insertUserStatement2.executeUpdate();

                return "index.jsp";
            } else {
                String  s1 = "UH...INTRODUCE YOUR PASSWORDS AND SEE IF THEY MATCH!";
                String  s2 = "Your password did not match<br>Try again.";
                session.setAttribute("s1", s1);
                session.setAttribute("s2", s2);
                return "errorSignup.jsp";
            }
        } else if(userExists&password.equals(cpassword)) {
            String s1 = "OOPS!THE USERNAME IS ALREADY TAKEN.";
            String  s2 = "Try again?";
            session.setAttribute("s1", s1);
            session.setAttribute("s2", s2);
            return "errorSignup.jsp";
        }
        else{
            String s1 = "OOPS!THE USERNAME IS ALREADY TAKEN.YOUR PASSWORD DID NOT ALSO MATCH";
            String  s2 = "Try again?";
            session.setAttribute("s1", s1);
            session.setAttribute("s2", s2);
        return "errorSignup.jsp";
        }
    }

    private String generateUserId(String role, Connection conn) throws SQLException {
        String prefix = role.equals("student") ? "STU" : "INS";
        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        PreparedStatement getLastIdStatement = conn.prepareStatement("SELECT user_id FROM USER_INFO WHERE user_id LIKE ? ORDER BY user_id DESC LIMIT 1");
        getLastIdStatement.setString(1, prefix + year + "%");
        ResultSet getLastIdResult = getLastIdStatement.executeQuery();
        if (getLastIdResult.next()) {
            String lastId = getLastIdResult.getString("user_id");
            int lastNumber = Integer.parseInt(lastId.substring(7));
            return prefix + year + String.format("%03d", lastNumber + 1);
        } else {
            return prefix + year + "001";
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
