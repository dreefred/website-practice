package ph.activelearning.reports;

import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import ph.activelearning.Database;
import ph.activelearning.SecurityHandling;
/**
 * @author jonathanseanestaya
 */
public class AdminReport extends PdfReports {
    private Phrase un;
    private Phrase pw;
    //old default
     public AdminReport(Database database, String path, Boolean curUse) {
        this.path = path;
        id = 4;
        ptg = new PdfTableGenerator(id);
        title = new Paragraph("ADMIN REPORT", new Font(Font.FontFamily.TIMES_ROMAN, 24, Font.BOLDITALIC));
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(36);
        ptg.setTable();
        ptg.createOldHeaderCell();
        document.setPageSize(new Rectangle(PageSize.A4));
        
        System.out.println("boolean value received");
        try(Connection conn = database.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT USERNAME, ROLE FROM APP.USER_INFO");) {
            
            
            try(ResultSet resultSet = statement.executeQuery();) {
                ptg.print(resultSet);
            }
        }
        catch(SQLException sqle) {
            System.err.println(sqle);
        }
        
        database.closeConnection();
    }
     public AdminReport(Database database, String path, String user, String cipher, String key) {
        this.path = path;
        id = 1;
        document.setPageSize(new Rectangle(260, 216));
        
        try(Connection conn = database.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM USER_INFO");
            ResultSet resultSet = statement.executeQuery();) {
            while(resultSet.next()) {
                if(resultSet.getString("USERNAME").equals(user)) {
                    un = new Phrase(resultSet.getString("USERNAME"),new Font(Font.FontFamily.TIMES_ROMAN,12));
                    pw = new Phrase((SecurityHandling.decrypt(resultSet.getString("PASSWORD"), cipher, key)),new Font(Font.FontFamily.TIMES_ROMAN,12));
                    break;
                }
            }
        }
        catch(SQLException sqle) {
            System.err.println(sqle);
        }
        
        database.closeConnection();
        
        title = new Paragraph("GUEST REPORT", new Font(Font.FontFamily.TIMES_ROMAN, 24, Font.BOLDITALIC));
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(36);
        
        Phrase uname = new Phrase("Username: ", new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.BOLD));
        pg1 = new Paragraph();
        pg1.add(uname);
        pg1.add(un);
        
        Phrase pass = new Phrase("Password: ", new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.BOLD));
        pg2 = new Paragraph();
        pg2.add(pass);
        pg2.add(pw);
    }
    //default
    public AdminReport(Database database, String path) {
        this.path = path;
        id = 0;
        ptg = new PdfTableGenerator(id);
        title = new Paragraph("ADMIN REPORT", new Font(Font.FontFamily.TIMES_ROMAN, 24, Font.BOLDITALIC));
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(36);
        ptg.setTable();
        ptg.createHeaderCell();
        document.setPageSize(new Rectangle(PageSize.A4));
        
        try(Connection conn = database.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM USER_INFO");) {
            
            
            try(ResultSet resultSet = statement.executeQuery();) {
                ptg.print(resultSet);
            }
        }
        catch(SQLException sqle) {
            System.err.println(sqle);
        }
        
        database.closeConnection();
    }
    //Date Enabled
    public AdminReport(Database database, String path, LocalDate beginDate, LocalDate endDate) {
        this.path = path;
        id = 0;
        ptg = new PdfTableGenerator(id);
        title = new Paragraph("ADMIN REPORT", new Font(Font.FontFamily.TIMES_ROMAN, 24, Font.BOLDITALIC));
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(36);
        ptg.setTable();
        ptg.createHeaderCell();
        document.setPageSize(new Rectangle(PageSize.A4));
        
        try(Connection conn = database.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM USER_INFO WHERE DATE_CREATED BETWEEN ? AND ?");) {
            statement.setDate(1, Date.valueOf(beginDate));
            statement.setDate(2, Date.valueOf(endDate));
            
            try(ResultSet resultSet = statement.executeQuery();) {
                ptg.print(resultSet);
            }
        }
        catch(SQLException sqle) {
            System.err.println(sqle);
        }
        
        database.closeConnection();
    }
    
    //Role enabled
    public AdminReport(Database database, String path, List<String> role) {
        this.path = path;
        id = 0;
        ptg = new PdfTableGenerator(id);
        title = new Paragraph("ADMIN REPORT", new Font(Font.FontFamily.TIMES_ROMAN, 24, Font.BOLDITALIC));
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(36);
        ptg.setTable();
        ptg.createHeaderCell();
        document.setPageSize(new Rectangle(PageSize.A4));
        
        // Create a string builder to dynamically generate the prepared statement
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM USER_INFO WHERE ");

        // Check if courses are specified
        if (!role.isEmpty()) {
            // Append placeholders for each course in the list
            queryBuilder.append("ROLE IN (");
            for (int i = 0; i < role.size(); i++) {
                queryBuilder.append("?");
                if (i < role.size() - 1) {
                    queryBuilder.append(",");
                }
            }
            queryBuilder.append(")");
        }

        try (Connection conn = database.getConnection(); PreparedStatement statement = conn.prepareStatement(queryBuilder.toString());) {

         

            // Set course parameters if courses are specified
            int parameterIndex = 1; // Start index for course parameters
            for (String roles : role) {
                statement.setString(parameterIndex++, roles);
            }

            try (ResultSet resultSet = statement.executeQuery();) {
                ptg.print(resultSet);
            }
        } catch (SQLException sqle) {
            System.err.println(sqle);
        }

        database.closeConnection();
    }
    
    //Role and Date Enabled
    public AdminReport(Database database, String path, List<String> role, LocalDate beginDate, LocalDate endDate) {
        this.path = path;
        id = 0;
        ptg = new PdfTableGenerator(id);
        title = new Paragraph("ADMIN REPORT", new Font(Font.FontFamily.TIMES_ROMAN, 24, Font.BOLDITALIC));
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(36);
        ptg.setTable();
        ptg.createHeaderCell();
        document.setPageSize(new Rectangle(PageSize.A4));

        // Create a string builder to dynamically generate the prepared statement
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM USER_INFO WHERE DATE_CREATED BETWEEN ? AND ?");

        // Check if courses are specified
        if (!role.isEmpty()) {
            // Append placeholders for each course in the list
            queryBuilder.append(" AND ROLE IN (");
            for (int i = 0; i < role.size(); i++) {
                queryBuilder.append("?");
                if (i < role.size() - 1) {
                    queryBuilder.append(",");
                }
            }
            queryBuilder.append(")");
        }

        try (Connection conn = database.getConnection(); PreparedStatement statement = conn.prepareStatement(queryBuilder.toString());) {

            // Set date parameters
            statement.setDate(1, Date.valueOf(beginDate));
            statement.setDate(2, Date.valueOf(endDate));

            // Set course parameters if courses are specified
            int parameterIndex = 3; // Start index for course parameters
            for (String roles : role) {
                statement.setString(parameterIndex++, roles);
            }

            try (ResultSet resultSet = statement.executeQuery();) {
                ptg.print(resultSet);
            }
        } catch (SQLException sqle) {
            System.err.println(sqle);
        }

        database.closeConnection();
    }

}