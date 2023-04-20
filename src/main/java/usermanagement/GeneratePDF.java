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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class GeneratePDF {

    public  void openPDF(String filename) {


        Document doc = new Document();
      //  Scanner sc=new Scanner(System.in);
        String fileName=null;
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

            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File("/PDF_Java/" + fileName +".pdf");
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                // no application registered for PDFs
            }
        }

    }
}
