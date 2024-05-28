package ph.activelearning;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
/**
 * @author jonathanseanestaya
 */
public class FooterPageEvent extends PdfPageEventHelper {
    private PdfTemplate template;
    
    @Override
    public void onOpenDocument(PdfWriter writer, Document document) {
        template = writer.getDirectContent().createTemplate(30, 16);
    }
    
    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        
    }
    
    @Override
    public void onCloseDocument(PdfWriter writer, Document document) {
        
    }
}