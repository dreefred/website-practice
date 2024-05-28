package ph.activelearning.reports;

import com.itextpdf.text.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import ph.activelearning.Database;

public class UserReport extends PdfReports {
    private Phrase un;
    private Phrase r;
    private Phrase ca;
    private Phrase dj;
    private Phrase cs;
    
    public UserReport(Database database, Database database2, String path, String username) {
        this.path = path;
        id = 2;
        document.setPageSize(new Rectangle(260, 300));
        
        
        try(Connection conn = database.getConnection();
            PreparedStatement statement1 = conn.prepareStatement("SELECT * FROM USER_INFO");
            ResultSet resultSet1 = statement1.executeQuery();) {
            
            while(resultSet1.next()) {
                if(resultSet1.getString("USERNAME").equals(username)) {
                    try(Connection conn2 = database2.getConnection();
                        PreparedStatement statement2 = conn2.prepareStatement("SELECT * FROM USER_REPORT WHERE USERNAME = ?");
                        ) {
                        statement2.setString(1, username);
                        ResultSet resultSet2 = statement2.executeQuery();
                        if(resultSet2.next()) {
                            un = new Phrase(resultSet2.getString("USERNAME"),new Font(Font.FontFamily.TIMES_ROMAN,12));
                            r = new Phrase(resultSet2.getString("ROLE"),new Font(Font.FontFamily.TIMES_ROMAN,12));
                            ca = new Phrase(resultSet2.getString("COURSE_ASSOCIATED"),new Font(Font.FontFamily.TIMES_ROMAN,12));
                            dj = new Phrase(resultSet2.getString("DATE_JOINED"),new Font(Font.FontFamily.TIMES_ROMAN,12));
                            cs = new Phrase(resultSet2.getString("COURSE_STATUS"),new Font(Font.FontFamily.TIMES_ROMAN,12));
                            break;
                        }
                    }
                }
            }
        }
        catch(SQLException sqle) {
            System.err.println(sqle);
        }
        
        database.closeConnection();
        database2.closeConnection();
        
        title = new Paragraph("USER REPORT", new Font(Font.FontFamily.TIMES_ROMAN, 24, Font.BOLD));
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(36);
        
        Phrase uname = new Phrase("Username: ", new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.BOLD));
        pg1 = new Paragraph();
        pg1.add(uname);
        pg1.add(un);
        
        Phrase role = new Phrase("Role: ", new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.BOLD));
        pg2 = new Paragraph();
        pg2.add(role);
        pg2.add(r);
        
        Phrase cassociated = new Phrase("Course Associated: ", new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.BOLD));
        pg3 = new Paragraph();
        pg3.add(cassociated);
        pg3.add(ca);
        
        Phrase djoined = new Phrase("Date Joined: ", new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.BOLD));
        pg4 = new Paragraph();
        pg4.add(djoined);
        pg4.add(dj);
        
        Phrase cstats = new Phrase("Course Status: ", new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.BOLD));
        pg5 = new Paragraph();
        pg5.add(cstats);
        pg5.add(cs);

    }
}