package com.code.teacher;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import javax.mail.Session;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.code.conn.DbConnection;
import com.code.entity.Guest;
import com.code.utils.GlobalFunction;
import com.code.utils.SendMailSSL;

/**
 * Servlet implementation class UpdateEvent
 */
@WebServlet("/UpdateEvent")
public class UpdateEvent extends HttpServlet {
	private static final long serialVersionUID = 1L;
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
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session=request.getSession();
		
		String staff_name=session.getAttribute("name").toString();
		
		String e_id= request.getParameter("eid");
		String sts = request.getParameter("sts");
		String status="Completed";
		if(sts.equals("cmplt"))
		{
			status="Completed";
		}
		try 
		{
			
			GlobalFunction gf=new GlobalFunction();
			HashMap<String, String> eventDetails = gf.getEventDetails(e_id);
			
			String event_name=eventDetails.get("event_name");
			
			String event_place = eventDetails.get("event_place");
			String about_event = eventDetails.get("about_event");
			String guestName = eventDetails.get("guest_status");
			String event_date = eventDetails.get("event_date");
			
			Guest guestDetails = gf.getGuestDetails(guestName);
			String gName=guestDetails.getgName();
			String gEmail=guestDetails.getgEmail();
			System.out.println("Geust Email "+gEmail);
			
			
			String subject="Thank you for attending "+event_name+" Event";
			
			SendMailSSL mail=new SendMailSSL();

			String msg1="To,<br/>"+gName+"<br/>Dear Sir/Madam,<br/>On behalf of the PES's Modern College of Engg. Pune, Department of Information Technology,"
					+ " I would like to thank you for taking out time from your busy schedule for Inaugural function of "+event_name+" : "+about_event+" as well as to judge the competition."
							+ "  Hope it was a positive experience for you as well as for us. "
							+ "The guidance and suggestions you shared was very enlightening and very useful for the students. Again, my sincere thanks for attending this session."
					+ "<br/><br/>Thanks & Regards,<br/>"+staff_name;			
			
			mail.EmailSending(gEmail, subject, msg1);

			
			PreparedStatement ps1 = con.prepareStatement("UPDATE `event_details` SET status='"+status+"' where e_id='"+e_id+"'");
			int i=ps1.executeUpdate();
			if(i>0)
			{
				response.sendRedirect("tViewAllEventDetails.jsp?update=done");
			}
			else
			{
				response.sendRedirect("tViewAllEventDetails.jsp?fupdate=fail");
			}
		}
		catch (Exception e) 
		{
			System.out.println("Exc "+e);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String s_type = request.getParameter("submit_type");
		String e_id = request.getParameter("e_id");
		HttpSession session=request.getSession();
		String t_id = session.getAttribute("t_id").toString();
		String staff_name=session.getAttribute("name").toString();
	
		
		
		GlobalFunction gf=new GlobalFunction();
		HashMap<String, String> eventDetails = gf.getEventDetails(e_id);
		
		String event_name=eventDetails.get("event_name");
		
		String event_place = eventDetails.get("event_place");
		String about_event = eventDetails.get("about_event");
		String event_fees = eventDetails.get("event_fees");
		String event_date = eventDetails.get("event_date");
		
		
		System.out.println("S Type "+s_type);
		
		if(s_type.equals("AddGeust"))
		{
			System.out.println("in Add Guest Condition");
			
			String guestName = request.getParameter("geust_name");
			
			
			boolean status = addGuest(e_id,guestName);
			Guest guestDetails = gf.getGuestDetails(guestName);
			String gName=guestDetails.getgName();
			String gEmail=guestDetails.getgEmail();
			System.out.println("Geust Email "+gEmail);
			
			System.out.println("Add Geust Statuib "+status);
			if(status)
			{
				String subject="Invitation of "+event_name+" for date "+event_date;
				String msg="To, <br/>"+gName+"<br/><br/><b>Date:</b> "+event_date+"<br/><b>Subject:</b> <b>"+subject+"</b><br/><br/>Dear Sir/ Madam,<br/> I am "+staff_name+" working as an Assistant Professor in the IT department of PES's Modern College of Engg. Pune. IT department cordially invite you for the inaugural function of "+event_name+" as a judge and as an honored guest . The competition cum exhibition will be held at "+event_date+". The theme for this competition is "+about_event+" at "+event_place+"."
						+ " <br/><br/>We kindly request you to accept this as a personal invitation and please join us on "+event_date+ " We look forward to hearing from you soon.<br/><br/>Thank you."
						+ "<br/>- With regards,<br/>"
						+ staff_name
						+ "<br/>PES's Modern College of Engg. Pune";
				
				SendMailSSL mail=new SendMailSSL();
				mail.EmailSending(gEmail, subject, msg);
				
				
				response.sendRedirect("tViewAllEventDetails.jsp?guest=add");
			}
			else
			{
				response.sendRedirect("tViewAllEventDetails.jsp?fguest=add");
			}
		}
		else if(s_type.equals("AddWinners")) 
		{
			System.out.println("in Add Winner Condition");
			
			String firstW = request.getParameter("winner_first");
			String SecondW = request.getParameter("winner_second");
			String ThirdW = request.getParameter("winner_third");
			
			boolean status = addwinners(e_id,firstW,SecondW,ThirdW,t_id);
			if(status)
			{
				response.sendRedirect("tViewAllEventDetails.jsp?wadd=add");
			}
			else
			{
				response.sendRedirect("tViewAllEventDetails.jsp?fwadd=add");
			}
		}
	}
	public static boolean addwinners(String e_id,String firstW,String SecondW,String ThirdW,String t_id) 
	{
		int i=0,i1=0;
		try 
		{
			
			PreparedStatement ps1 = con.prepareStatement("INSERT INTO `winners_details`(`event_id`, `first_winner`, `second_winner`, `third_winner`, `t_id`) VALUES ('"+e_id+"','"+firstW+"','"+SecondW+"','"+ThirdW+"','"+t_id+"')");
			i=ps1.executeUpdate();
			
			PreparedStatement ps2 = con.prepareStatement("Select MAX(w_id) from winners_details");
			ResultSet executeQuery = ps2.executeQuery();
			String max_win_id="0";
			if(executeQuery.next())
			{
				max_win_id= executeQuery.getString(1);
			}
			
			System.out.println("Max ID is "+max_win_id);
			
			PreparedStatement ps3 = con.prepareStatement("UPDATE `event_details` SET winner_status='"+max_win_id+"' where e_id='"+e_id+"'");
			i1=ps3.executeUpdate();
			if(i1>0)
			{
				System.out.println("Update done ");
			}
		}
		catch (Exception e) 
		{
			System.out.println("Exc "+e);
		}
		return i>0?true:false;
	}
	
	public static boolean addGuest(String e_id,String guestName) 
	{
		int i=0;
		try 
		{
			PreparedStatement ps1 = con.prepareStatement("UPDATE `event_details` SET guest_status='"+guestName+"' where e_id='"+e_id+"'");
			i=ps1.executeUpdate();
			
		}
		catch (Exception e) 
		{
			System.out.println("Exc "+e);
		}
		return i>0?true:false;
	}	
	
}
