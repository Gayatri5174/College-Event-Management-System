package com.code.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.code.entity.Participate;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

public class DownloadParticipateList {


	public void getParticipateLs(String e_id) 
	{
		System.out.println("Event ID "+e_id);
    	try {
        	
    		GlobalFunction gf=new GlobalFunction();
    		ArrayList<Participate> participateList = gf.getParticipateList(e_id);
            // Create a Document instance
            Document document = new Document(PageSize.A4.rotate());

            // Specify the file path where the PDF will be generated
            
            HashMap<String, String> eventDetails = gf.getEventDetails(e_id);
            String event_name = eventDetails.get("event_name");
          
            String file_name = event_name;
            
            PdfWriter.getInstance(document, new FileOutputStream(System.getProperty("user.home") + "/Downloads/"+file_name+".pdf"));

            // Open the document
            document.open();

            // Add title to the document
           // Paragraph title = new Paragraph("Vehicle Servicing Report");
            //title.setAlignment(Element.ALIGN_CENTER);
            
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD, BaseColor.RED);
            Paragraph title = new Paragraph();
            Chunk titleChunk = new Chunk("Festagram Event Participate Student List", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.add(titleChunk);

            
            
            document.add(title);
            document.add(new Paragraph("\n")); // Add empty line
            document.add(new LineSeparator());

            document.add(new Paragraph("\n")); // Add empty line
           // HashMap<String, String> customerDetails = reportData.getCustomerDetails();

                       
            // Vehicle Servicing Details
            PdfPTable servicingTable = new PdfPTable(9);
            
            servicingTable.setWidthPercentage(100);
            
            //for aarange column Width
            servicingTable.setWidths(new float[] {1,2, 1, 1,2,3,2,2,2});

            servicingTable.addCell(getHeaderCell("No."));
            servicingTable.addCell(getHeaderCell("Student Name"));
            servicingTable.addCell(getHeaderCell("ERP.No."));
            servicingTable.addCell(getHeaderCell("Gender"));
            servicingTable.addCell(getHeaderCell("Mobile"));
            servicingTable.addCell(getHeaderCell("College Name"));
          
            servicingTable.addCell(getHeaderCell("Reg Date"));
            servicingTable.addCell(getHeaderCell("Reg Status"));
            servicingTable.addCell(getHeaderCell("Payment Status"));

            int i = 1;
            for (Participate pl : participateList) {
                servicingTable.addCell(getHeaderCell(String.valueOf(i)));
                servicingTable.addCell(getHeaderCell(pl.getStName()));
                servicingTable.addCell(getHeaderCell(pl.getEnrollNumber()));
                servicingTable.addCell(getHeaderCell(pl.getGender()));
                servicingTable.addCell(getHeaderCell(pl.getMobile()));
                servicingTable.addCell(getHeaderCell(pl.getClgName()));
                
                servicingTable.addCell(getHeaderCell(pl.getRegDate()));
                servicingTable.addCell(getHeaderCell(pl.getRegSts()));
                servicingTable.addCell(getHeaderCell(pl.getPaymentSts()));
                
                
                i++;
            }
            document.add(servicingTable);
            document.add(new Paragraph("\n")); // Add empty line
            document.add(new Paragraph("\n")); // Add empty line
            document.add(new LineSeparator());
            
            document.add(new Paragraph("\n")); // Add empty line
            Font titleFont1 = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
            Paragraph title1 = new Paragraph();
            Chunk titleChunk1 = new Chunk("Thanks You,\n\n Festagram Team Members", titleFont1);
            title1.setAlignment(Element.ALIGN_RIGHT);
            title1.add(titleChunk1);

            
            
            document.add(title1);
            
            
            
            // Close the document
            document.close();

            System.out.println("PDF generated successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

	}
	
    public static void main(String[] args) 
    {

    	DownloadParticipateList pll=new DownloadParticipateList();
    	pll.getParticipateLs("4");
   }

    // Helper method to create PdfPCell for table cell with content
    private static PdfPCell getCell(String content, String string) {
        PdfPCell cell = new PdfPCell(new Paragraph(content+" "+string));
        cell.setBorderWidth(0);
        return cell;
    }

    // Helper method to create PdfPCell for table header cell with content
    private static PdfPCell getHeaderCell(String content) {
        PdfPCell cell = new PdfPCell(new Paragraph(content));
        cell.setBorderWidth(0);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY); // Set background color for header cell
        return cell;
    }
}
