package ph.activelearning.reports;

import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import ph.activelearning.Database;
import ph.activelearning.SecurityHandling;
/**
 * @author jonathanseanestaya
 */
public class GuestReport extends PdfReports {
    private Phrase un;
    private Phrase pw;
    
    public GuestReport(Database database, String path, String user, String cipher, String key) {
        this.path = path;
        id = 3;
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
        
        title = new Paragraph("ADMIN REPORT", new Font(Font.FontFamily.TIMES_ROMAN, 24, Font.BOLDITALIC));
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
}
