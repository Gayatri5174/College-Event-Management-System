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

import com.code.conn.DbConnection;
import com.code.utils.GlobalFunction;
import com.code.utils.SendSMS;

/**
 * Servlet implementation class AddTeacherAccount
 */
@WebServlet("/AddTeacherAccount")
public class AddTeacherAccount extends HttpServlet {
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
	
		String teacher_name= request.getParameter("fname")+" "+request.getParameter("mname")+" "+request.getParameter("lname");
		
		String gender= request.getParameter("gender");
		String erp_number= request.getParameter("erp_number");
		String mobile= request.getParameter("mobile");
		String email= request.getParameter("email");
		GlobalFunction gf=new GlobalFunction();
		String password = gf.generatePassword();
		//String password= request.getParameter("password");
		
		try 
		{
			PreparedStatement ps = con.prepareStatement("SELECT * FROM `teacher_details` WHERE t_erp_no='"+erp_number+"'");
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				response.sendRedirect("createTeacherAccount.jsp?already=done");
			}
			else
			{
			
				PreparedStatement ps1 = con.prepareStatement("INSERT INTO `teacher_details`(`tchr_name`, `gender`, `mobile`, `email`, `t_erp_no`, `password`) VALUES ('"+teacher_name+"','"+gender+"','"+mobile+"','"+email+"','"+erp_number+"','"+password+"')");
				int i=ps1.executeUpdate();
				if (i>0) 
				{
					String msgC="Dear User  Your Login Credentials Username "+erp_number+" Password "+password;
					SendSMS.callURL(msgC, mobile,"LOGIN");
					
					response.sendRedirect("createTeacherAccount.jsp?reg=done");
				}
				else 
				{
					response.sendRedirect("createTeacherAccount.jsp?freg=done");
				}
			}
		}
		catch (Exception e) 
		{
			System.out.println("Exc "+e);
		}
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String m_id= request.getParameter("mid");
		String sts= request.getParameter("sts");
		String status="DeActive";
		if(sts.equals("a"))
		{
			status="Active";
		}
		
		try 
		{
			PreparedStatement ps1 = con.prepareStatement("UPDATE `mechanicle_details` SET acc_status='"+status+"' where m_id='"+m_id+"'");
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