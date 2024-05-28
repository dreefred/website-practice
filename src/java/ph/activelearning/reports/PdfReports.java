package ph.activelearning.reports;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.ColumnText;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.sql.*;
import java.io.FileNotFoundException;
import java.io.File;
import ph.activelearning.FooterPageEvent;
import ph.activelearning.DateTimeGenerator;
/**
 * @author jonathanseanestaya
 */
public class PdfReports implements Reports {
    Document document = new Document();
    Paragraph title, pg1, pg2, pg3, pg4, pg5;
    PdfWriter writer;
    Rectangle rect;
    String path;
    PdfTableGenerator ptg;
    PdfPTable[] tables;
    int id;
    String dtg;
    
    public PdfReports() {
    }
    
    @Override
    public void generate() {
        try {
            dtg = DateTimeGenerator.generateDateTime();
            
            writer = PdfWriter.getInstance(document, new FileOutputStream(path + "/" + dtg + ".pdf"));
            FooterPageEvent fpe = new FooterPageEvent();
        
            writer.setPageEvent(fpe);
        
            switch(id) {
                case 0: forAdmin();
                    break;
                case 1: forInstructor();
                    break;
                case 2: //forStudent(); //CREATE A forStudent METHOD
                    break;
                case 3: forGuest();
                    break;
                case 4: forAdmin();
                    break;
                default:
            }
        }
        catch(DocumentException | FileNotFoundException e) {
            System.err.println(e);
        }
    }
    
    private void forAdmin() throws DocumentException {
        document.open();
        document.add(title);
        document.add(ptg.getTable());
        document.close();
        writer.close();
    }
    
    //WILL REMOVE
    private void forGuest() throws DocumentException {
        
        document.open();
        document.add(title);
        document.add(pg1);
        document.add(pg2);
        document.close();
        writer.close();
    }
    
    //WILL REMOVE
    private void forReports() throws DocumentException {
        document.open();
        document.add(title);
        document.add(pg1);
        document.add(pg2);
        document.add(pg3);
        document.add(pg4);
        document.add(pg5);
        document.close();
        writer.close();
    }
    
    private void forInstructor() throws DocumentException {
        document.open();
        document.add(title);
        document.add(pg1);
        pg2.setSpacingAfter(10);
        document.add(pg2);
        
        for(PdfPTable table : tables) {
            document.add(table);
            document.add(new Paragraph("\n\n"));
        }
        
        document.close();
        writer.close();
    }
    
    @Override
    public String getDateTime() {
        return dtg;
    }
}