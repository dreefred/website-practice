package ph.activelearning.reports;

import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Element;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import java.sql.*;
/**
 * @author jonathanseanestaya
 */
public class PdfTableGenerator {
    public static final int ADMIN = 0;
    public static final int INSTRUCTOR = 1;
    public static final int STUDENT = 2;
    public static final int GUEST = 3;
    public static final int ADMINOLD = 4;
    private final int control;
    private PdfPTable table;
    private PdfPCell cell;
    private final Font titleTR = new Font(Font.FontFamily.TIMES_ROMAN, 15);
    private final Font normalTR = new Font(Font.FontFamily.TIMES_ROMAN, 12);
    
    public PdfTableGenerator(int control) {
        this.control = control;
    }
    
    public void setTable() {
        switch(control) {
            case ADMIN: setAdminTable();
                break;
            case INSTRUCTOR: setInstructorTable();
                break;
            case STUDENT: setStudentTable();
                break;
            case GUEST: //setGuestTable();
                break;
            case ADMINOLD: setOldAdminTable();
                break;
            default:
        }
    }
    
    private void setAdminTable() {
        table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setHeaderRows(1);
    }
    
    private void setOldAdminTable() {
        table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setHeaderRows(1);
        
    }
    
    private void setInstructorTable() {
        table = new PdfPTable(2);
        table.setWidthPercentage(100);
    }
    
    private void setStudentTable() {
        
    }
    
    public void createHeaderCell() {
        cell = new PdfPCell();
        cell.setBackgroundColor(new BaseColor(250, 230, 205, 80));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(Rectangle.BOTTOM);
        cell.setPhrase(new Phrase("User ID", new Font(Font.FontFamily.TIMES_ROMAN, 15)));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Username", new Font(Font.FontFamily.TIMES_ROMAN, 15)));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Role", new Font(Font.FontFamily.TIMES_ROMAN, 15)));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Date Created", new Font(Font.FontFamily.TIMES_ROMAN, 15)));
        table.addCell(cell);
    }
    
    public void createOldHeaderCell() {
        cell = new PdfPCell();
        cell.setBackgroundColor(new BaseColor(250, 230, 205, 80));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(Rectangle.BOTTOM);
        
        cell.setPhrase(new Phrase("Username", new Font(Font.FontFamily.TIMES_ROMAN, 15)));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Role", new Font(Font.FontFamily.TIMES_ROMAN, 15)));
        table.addCell(cell);
        
    }
    public void print(ResultSet result) throws SQLException {
        int count = 0;
        
        if(control == 0) {
            while(result.next()) {
                Phrase id = new Phrase(result.getString("USER_ID"),new Font(Font.FontFamily.TIMES_ROMAN,12));
                Phrase un = new Phrase(result.getString("USERNAME"),new Font(Font.FontFamily.TIMES_ROMAN,12));
                Phrase r = new Phrase(result.getString("ROLE"),new Font(Font.FontFamily.TIMES_ROMAN,12));
                Phrase dc = new Phrase(result.getString("DATE_CREATED"),new Font(Font.FontFamily.TIMES_ROMAN,12));
                cell = new PdfPCell();

                if(count % 2 == 1)
                    cell.setBackgroundColor(new BaseColor(250, 230, 205, 80));

                cell.setBorder(Rectangle.NO_BORDER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPaddingBottom(10);
                cell.setPhrase(id);
                table.addCell(cell);
                cell.setPhrase(un);
                table.addCell(cell);
                cell.setPhrase(r);
                table.addCell(cell);
                cell.setPhrase(dc);
                table.addCell(cell);

                count++;
            }
        }
        else if(control == 1) {
            cell = new PdfPCell();
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPhrase(new Phrase("Course ID", titleTR));
            table.addCell(cell);
            cell.setPhrase(new Phrase(result.getString("COURSE_ID"), normalTR));
            table.addCell(cell);
            cell.setPhrase(new Phrase("Course Name", titleTR));
            table.addCell(cell);
            cell.setPhrase(new Phrase(result.getString("COURSE_NAME"), normalTR));
            table.addCell(cell);
            cell.setPhrase(new Phrase("Date", titleTR));
            table.addCell(cell);
            cell.setPhrase(new Phrase(result.getString("DATE"), normalTR));
            table.addCell(cell);
            cell.setPhrase(new Phrase("Time", titleTR));
            table.addCell(cell);
            cell.setPhrase(new Phrase(result.getString("TIME"), normalTR));
            table.addCell(cell);
        }
        else if(control == 2) {
            //PUT TABLE GENERATION FOR STUDENTS HERE
        }
        else if(control == 4) {
            while(result.next()) {
                
                Phrase un = new Phrase(result.getString("USERNAME"),new Font(Font.FontFamily.TIMES_ROMAN,12));
                
                Phrase r = new Phrase(result.getString("ROLE"),new Font(Font.FontFamily.TIMES_ROMAN,12));
                
                cell = new PdfPCell();
                
                if(count % 2 == 1)
                    cell.setBackgroundColor(new BaseColor(250, 230, 205, 80));

                cell.setBorder(Rectangle.NO_BORDER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPaddingBottom(10);
                
                cell.setPhrase(un);
                table.addCell(cell);
                cell.setPhrase(r);
                table.addCell(cell);
                
                count++;
            }
        }
    }
    
    public PdfPTable getTable() {
        return table;
    }
}