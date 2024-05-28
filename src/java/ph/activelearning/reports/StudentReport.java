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
 * @author giann
 */
public class StudentReport extends PdfReports {
    private String sqlString = "SELECT id.COURSE_ID, COURSE.COURSE_NAME, COURSE.DATE, COURSE.TIME, id.STATUS, id.USER_ID\n" +
"                            FROM COURSE\n" +
"                            INNER JOIN (SELECT info.USER_ID, takes.COURSE_ID, takes.STATUS\n" +
"                                FROM USER_INFO AS info\n" +
"                                INNER JOIN TAKES AS takes\n" +
"                                ON info.USER_ID = takes.STUDENT_ID\n" +
"                                WHERE info.USERNAME = ?) AS id\n" +
"                            ON id.COURSE_ID = COURSE.COURSE_ID;";
    
    public StudentReport(Database database, String path, String username) {
        this.path = path;
        id = 2;
        ptg = new PdfTableGenerator(id);
        title = new Paragraph("STUDENT REPORT", new Font(Font.FontFamily.TIMES_ROMAN, 24, Font.BOLDITALIC));
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
                resultSet.first();
                pg1 = new Paragraph("ID: " + resultSet.getString("id.USER_ID"));
                pg2 = new Paragraph("Name: " + username + "\nCourse Enrolled");
            
                tables = new PdfPTable[5];
                
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