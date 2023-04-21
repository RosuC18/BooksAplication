package usermanagement;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class GeneratePDF {

    public  void openPDF(String fileName) {


        Document doc = new Document();
      //  Scanner sc=new Scanner(System.in);

        PdfWriter writer;

        {
            try {
                writer = PdfWriter.getInstance(doc, new FileOutputStream("C:\\PDF_Java\\" + fileName +".pdf"));
                System.out.println("PDF created.");
                //opens the PDF

                doc.open();
                doc.add(new Paragraph(fileName));
                doc.close();
                writer.close();
                PdfReader reader =new PdfReader("C:\\PDF_Java\\" + fileName +".pdf");
                if (Desktop.isDesktopSupported()) {
                    File myFile = new File("/PDF_Java/" + fileName +".pdf");
                    Desktop.getDesktop().open(myFile);

                }

            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
