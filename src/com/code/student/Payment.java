package com.code.student;

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
 * Servlet implementation class Payment
 */
@WebServlet("/Payment")
public class Payment extends HttpServlet {
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
		HttpSession session=request.getSession();
		String st_id = session.getAttribute("st_id").toString();
		String e_id = session.getAttribute("e_id").toString();
		try 
		{
			PreparedStatement ps1 = con.prepareStatement("INSERT INTO `participant_details`(`st_id`, `e_id`, `payment_status`) VALUES ('"+st_id+"','"+e_id+"','Completed')");
			int i=ps1.executeUpdate();
			if (i>0) 
			{
				response.sendRedirect("enrollEventDetails.jsp?enroll=done");
			}
			else 
			{
				response.sendRedirect("enrollEventDetails.jsp?fenroll=fail");
			}
		}
		catch (Exception e) 
		{
			System.out.println("Exc "+e);
		}
	}
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session=request.getSession();
		String st_id = session.getAttribute("st_id").toString();
		String e_id = session.getAttribute("e_id").toString();
		try 
		{
			PreparedStatement ps1 = con.prepareStatement("INSERT INTO `participant_details`(`st_id`, `e_id`, `payment_status`) VALUES ('"+st_id+"','"+e_id+"','Completed')");
			int i=ps1.executeUpdate();
			if (i>0) 
			{
				response.sendRedirect("enrollEventDetails.jsp?enroll=done");
			}
			else 
			{
				response.sendRedirect("enrollEventDetails.jsp?fenroll=fail");
			}
		}
		catch (Exception e) 
		{
			System.out.println("Exc "+e);
		}
	}
	
}