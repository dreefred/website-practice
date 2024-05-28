package ph.activelearning.reports;

import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import ph.activelearning.Database;
/**
 * @author jonathanseanestaya
 */
public class InstructorReport extends PdfReports {
    private String sqlString = "SELECT id.USER_ID, id.COURSE_ID, COURSE.COURSE_NAME, COURSE.\"DATE\", COURSE.\"TIME\" " +
                            "FROM COURSE " +
                            "INNER JOIN (SELECT info.USER_ID, teach.COURSE_ID " +
                                "FROM USER_INFO AS info " +
                                "INNER JOIN TEACHES AS teach " +
                                "ON info.USER_ID = teach.INSTRUCTOR_ID " +
                                "WHERE info.USERNAME = ?) AS id " +
                            "ON id.COURSE_ID = COURSE.COURSE_ID;";
    
    public InstructorReport(Database database, String path, String username) {
        this.path = path;
        id = 1;
        ptg = new PdfTableGenerator(id);
        title = new Paragraph("INSTRUCTOR REPORT", new Font(Font.FontFamily.TIMES_ROMAN, 24, Font.BOLDITALIC));
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(36);
        ptg.setTable();
        document.setPageSize(PageSize.A4);
        
        try(Connection conn = database.getConnection();
            PreparedStatement statement = conn.prepareStatement(sqlString, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);) {
            statement.setString(1, username);
            
            try(ResultSet resultSet = statement.executeQuery();) {
                int count = 0;
                resultSet.last();
                int num = resultSet.getRow();
                resultSet.first();
                pg1 = new Paragraph("ID: " + resultSet.getString("USER_ID"));
                pg2 = new Paragraph("Name: " + username);
            
                tables = new PdfPTable[num];
                
                do{
                    ptg.print(resultSet);
                    tables[count] = ptg.getTable();
                    count++;
                } while(resultSet.next());
            }
        }
        catch(SQLException sqle) {
            System.err.println(sqle);
        }
    }
}