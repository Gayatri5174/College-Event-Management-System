package com.code.admin;

import java.io.IOException; 
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.code.conn.DbConnection;
 
/**
 * Servlet implementation class AddGeustDetails
 */
@WebServlet("/AddGeustDetails")
public class AddGeustDetails extends HttpServlet {
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
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	
		String geust_name= request.getParameter("fname")+" "+request.getParameter("mname")+" "+request.getParameter("lname");
		
		String gender= request.getParameter("gender");
		String about_geust= request.getParameter("about_geust");
		String mobile= request.getParameter("mobile");
		String email= request.getParameter("email");
		
		
		try 
		{
			PreparedStatement ps1 = con.prepareStatement("INSERT INTO `geust_details`(`g_name`, `gender`, `about_geust`, `g_email`, `g_mobile`) VALUES ('"+geust_name+"','"+gender+"','"+about_geust+"','"+email+"','"+mobile+"')");
			int i=ps1.executeUpdate();
			if (i>0) 
			{				
				
				response.sendRedirect("createGeustAccount.jsp?reg=done");
			}
			else 
			{
				response.sendRedirect("createGeustAccount.jsp?freg=done");
			}
		}
		catch (Exception e) 
		{
			System.out.println("Exc "+e);
		}
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String g_id= request.getParameter("mid");
		String sts= request.getParameter("sts");
		String status="NotAvailable";
		if(sts.equals("a"))
		{
			status="Available";
		}
		
		try 
		{
			PreparedStatement ps1 = con.prepareStatement("UPDATE `geust_details` SET status='"+status+"' where g_id='"+g_id+"'");
			int i=ps1.executeUpdate();
			if (i>0) 
			{
				response.sendRedirect("viewGeustAccount.jsp?update=done");
			}
			else 
			{
				response.sendRedirect("viewGeustAccount.jsp?fupdate=done");
			}
		}
		catch (Exception e) 
		{
			System.out.println("Exc "+e);
		}		
	}
		
}