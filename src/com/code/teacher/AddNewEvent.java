package com.code.teacher;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.code.conn.DbConnection;
import com.code.utils.GlobalFunction;

/**
 * Servlet implementation class AddNewEvent
 */
@WebServlet("/AddNewEvent")
public class AddNewEvent extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final static String ImgPath="C:/project/workspace/FestagramR2/WebContent/event_photos/";
	
	static Connection con;
	public void init(ServletConfig config) throws ServletException 
	{
		try 
		{
			con=DbConnection.getConnection();
		} 
		catch (Exception e) 
		{
			System.out.println("Exception "+e);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	
		if (ServletFileUpload.isMultipartContent(request)) 
  		{
			try 
			{
				@SuppressWarnings("unchecked")
				List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
				 
				String FileName = ""; 

				for (FileItem item1 : multiparts) 
				{
					if (!item1.isFormField()) 
					{

						String name = new File(item1.getName()).getName();
						item1.write(new File(ImgPath + File.separator + name));
						FileName = item1.getName(); 
					}
				}
				
			
				String eventName="", eventPlace= "",aboutEvent="",eventFees="";
				
				
				for (FileItem item : multiparts) 
				{
					if ((item.getFieldName()).equals("eventName")) {
						eventName = (String) item.getString();
					}
				} 
				for (FileItem item : multiparts) 
				{
					if ((item.getFieldName()).equals("eventPlace")) {
						eventPlace = (String) item.getString();
					}
				} 
				for (FileItem item : multiparts) 
				{
					if ((item.getFieldName()).equals("aboutEvent")) {
						aboutEvent = (String) item.getString();
					}
				} 
				
				for (FileItem item : multiparts) 
				{
					if ((item.getFieldName()).equals("eventFees")) {
						eventFees = (String) item.getString();
					}
				} 
				String  regStartDate="", regEndDate="",eventDate="";
				for (FileItem item : multiparts) 
				{
					if ((item.getFieldName()).equals("regStartDate")) {
						regStartDate= (String) item.getString();
					}
				} 
				for (FileItem item : multiparts) 
				{
					if ((item.getFieldName()).equals("regEndDate")) {
						regEndDate = (String) item.getString();
					}
				} 
				for (FileItem item : multiparts) 
				{
					if ((item.getFieldName()).equals("eventDate")) {
						eventDate= (String) item.getString();
					}
				} 
				String event_fees= "";
				
				for (FileItem item : multiparts) 
				{
					if ((item.getFieldName()).equals("event_fees")) {
						event_fees= (String) item.getString();
					}
				} 

				System.out.println("S Date "+regStartDate);
				System.out.println("L Date "+regEndDate);
				System.out.println("E Date "+eventDate);
				
		       // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy, MM, dd");
		        LocalDate regSdate = LocalDate.parse(regStartDate);
		        LocalDate regEdate = LocalDate.parse(regEndDate);
		        LocalDate eventdate = LocalDate.parse(eventDate);

			        // Validate event date
			        boolean isValidEventDate = isEventDateValid(eventdate, regSdate, regEdate);
			        System.out.println("Is event date valid? " + isValidEventDate);
				
				if(isValidEventDate)
				{
					HttpSession session=request.getSession();
					String t_id= session.getAttribute("t_id").toString();
		try 
			{
				PreparedStatement ps = con.prepareStatement("SELECT * FROM `event_details` WHERE event_name='"+eventName+"'");
				ResultSet rs = ps.executeQuery();
				if(rs.next())
				{
					response.sendRedirect("addNewEvents.jsp?ealready=done");
				}
				else
				{
				//update
					PreparedStatement pstmt = con.prepareStatement("INSERT INTO event_details (t_id, event_name, event_place, about_event, event_fees, reg_sdate, reg_ldate, event_date, event_banner, status, guest_status, winner_status) " +
			                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)");
					pstmt.setString(1, t_id);
		            pstmt.setString(2, eventName);
		            pstmt.setString(3, eventPlace);
		            pstmt.setString(4, aboutEvent);
		            pstmt.setString(5, event_fees);
		            pstmt.setString(6, regStartDate);
		            pstmt.setString(7, regEndDate);
		            pstmt.setString(8, eventDate);
		            pstmt.setString(9, FileName);
		            pstmt.setString(10, "Status");
		            pstmt.setString(11, "NA");
		            pstmt.setString(12, "NA");
		            
					int i=pstmt.executeUpdate();
					if (i>0) 
					{
						response.sendRedirect("addNewEvents.jsp?add=event");
					}
					else 
					{
						response.sendRedirect("addNewEvents.jsp?fadd=fail");
					}
				}
			}
			catch (Exception e) 
			{
				System.out.println("Exc "+e);
			}
				}
				else
				{
					System.out.println("Date Not Correct Please correct it..!");
					response.sendRedirect("addNewEvents.jsp?wdate=fail");
				}
	
			}
			catch (Exception e) 
			{
				System.out.println("Exc "+e);
			}
  		}
	}
	  // Method to validate event date
    public static boolean isEventDateValid(LocalDate eventDate, LocalDate registrationStartDate, LocalDate registrationEndDate) {
        return eventDate.isAfter(registrationStartDate) && eventDate.isAfter(registrationEndDate) && registrationStartDate.isBefore(registrationEndDate);
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String e_id= request.getParameter("eid");
		try 
		{
			PreparedStatement ps1 = con.prepareStatement("UPDATE `event_details` SET delete_status='Yes' where e_id='"+e_id+"'");
			int i=ps1.executeUpdate();
			if (i>0) 
			{
				response.sendRedirect("viewMechanics.jsp?update=done");
			}
			else 
			{
				response.sendRedirect("viewMechanics.jsp?fupdate=done");
			}
		}
		catch (Exception e) 
		{
			System.out.println("Exc "+e);
		}		
	}
		
}