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
 * Servlet implementation class CancelEnrollment
 */
@WebServlet("/CancelEnrollment")
public class CancelEnrollment extends HttpServlet {
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
		String st_id = session.getAttribute("st_id").toString();
		
		String e_id = request.getParameter("eid");
		try 
		{
			PreparedStatement ps1 = con.prepareStatement("UPDATE `participant_details` SET status='Cancel',payment_status='Refund' where st_id='"+st_id+"' AND e_id='"+e_id+"'");
			int i=ps1.executeUpdate();
			if (i>0) 
			{
				response.sendRedirect("viewAllEventDetails.jsp?ecancel=done");
			}
			else 
			{
				response.sendRedirect("viewAllEventDetails.jsp?fecancel=fail");
			}
		}
		catch (Exception e) 
		{
			System.out.println("Exc "+e);
		}
	}
}