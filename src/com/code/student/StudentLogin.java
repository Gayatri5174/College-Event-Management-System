package com.code.student;

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

/**
 * Servlet implementation class StudentLogin
 */
@WebServlet("/StudentLogin")
public class StudentLogin extends HttpServlet 
{
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
	
		String erp_no= request.getParameter("erp_no");
		String password = request.getParameter("password");
		HttpSession session = request.getSession();
		
		System.out.println("erp_no "+erp_no);
		System.out.println("Password "+password);

		
		try 
		{
			
			PreparedStatement ps1 = con.prepareStatement("SELECT * FROM `student_details` where erp_no='"+ erp_no+ "' AND password='" + password + "'");
			ResultSet rs = ps1.executeQuery();
			if (rs.next()) 
			{
				session.setAttribute("st_id", rs.getString("id"));
				session.setAttribute("st_name", rs.getString("st_name"));
				session.setAttribute("erp_no", rs.getString("erp_no"));
				session.setAttribute("u_type", "Student");
				
				System.out.println("Login Done");
				response.sendRedirect("studentHome.jsp?login=done");

			}
			else 
			{
				System.out.println("Login fail");
				response.sendRedirect("studentLogin.jsp?flogin=fail");
			}
		}
		catch (Exception e) 
		{
			System.out.println("Exc "+e);
		}

	}
}