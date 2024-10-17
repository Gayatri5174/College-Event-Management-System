package com.code.admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.code.conn.DbConnection;
import com.code.entity.ParticipantDetails;
import com.code.utils.DownloadParticipateList;

/**
 * Servlet implementation class AdminLogin
 */
@WebServlet("/AdminLogin")
public class AdminLogin extends HttpServlet {
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
	
		String username= request.getParameter("username");
		String password = request.getParameter("password");
		
		System.out.println("Username "+username);
		System.out.println("Password "+password);

		
		try 
		{
			HttpSession session=request.getSession();
			PreparedStatement ps1 = con.prepareStatement("SELECT * FROM `admin_details` where username='"+ username + "' AND password='" + password + "'");
			ResultSet rs = ps1.executeQuery();
			if (rs.next()) 
			{	
				session.setAttribute("u_type", "Admin");
				
				System.out.println("Login Done");
				response.sendRedirect("adminHome.jsp?login=done");

			}
			else 
			{
				System.out.println("Login fail");
				response.sendRedirect("adminLogin.jsp?flogin=login");
			}
		}
		catch (Exception e) 
		{
			System.out.println("Exc "+e);
		}

	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		DownloadParticipateList dPd=new DownloadParticipateList();
		dPd.getParticipateLs(request.getParameter("eid"));
		response.sendRedirect("tViewAllEventDetails.jsp?dwnload=done");
	
	}
}