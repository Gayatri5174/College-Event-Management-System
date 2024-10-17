package com.code.teacher;

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
 * Servlet implementation class TeacherLogin
 */
@WebServlet("/TeacherLogin")
public class TeacherLogin extends HttpServlet {
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
	
		String erp_number= request.getParameter("erp_number");
		String password = request.getParameter("password");
		
		System.out.println("ERP Number"+erp_number);
		System.out.println("Password "+password);

		
		try 
		{
			HttpSession session=request.getSession();
			PreparedStatement ps1 = con.prepareStatement("SELECT * FROM `teacher_details` where t_erp_no='"+ erp_number + "' AND password='" + password + "'");
			ResultSet rs = ps1.executeQuery();
			if (rs.next()) 
			{	
				session.setAttribute("u_type", "Teacher");
				session.setAttribute("t_id", rs.getString("t_id"));
				session.setAttribute("t_erp_no", rs.getString("t_erp_no"));
				session.setAttribute("name", rs.getString("tchr_name"));
				response.sendRedirect("teacherHome.jsp?login=done");
			}
			else 
			{
				System.out.println("Login fail");
				response.sendRedirect("teacherLogin.jsp?flogin=login");
			}
		}
		catch (Exception e) 
		{
			System.out.println("Exc "+e);
		}
	}
}