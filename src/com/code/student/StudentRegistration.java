package com.code.student;

import java.io.File; 
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.code.conn.DbConnection;
import com.code.utils.SendSMS;

/**
 * Servlet implementation class StudentRegistration
 */
@WebServlet("/StudentRegistration")
public class StudentRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;
final static String ImgPath="C:/project/workspace/CollegeApplicationR1/WebContent/student_photos/";
	
	static Connection con;
	public void init(ServletConfig config) throws ServletException 
	{
		try {
			con=DbConnection.getConnection();
		} catch (Exception e) {
			System.out.println("Exc init block "+e);
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
				
				String fname="",mname="",lname="",gender="",clg_name="",address="",email="",password="";
				String mobile="";
				for (FileItem item : multiparts) 
				{
					if ((item.getFieldName()).equals("fname")) {
						fname = (String) item.getString();
					}
				} 
				for (FileItem item : multiparts) 
				{
					if ((item.getFieldName()).equals("mname")) {
						mname = (String) item.getString();
					}
				} 
				for (FileItem item : multiparts) 
				{
					if ((item.getFieldName()).equals("lname")) {
						lname = (String) item.getString();
					}
				} 
				String st_name=fname+" "+mname+" "+lname;
				
				for (FileItem item : multiparts) 
				{
					if ((item.getFieldName()).equals("gender")) {
						gender = (String) item.getString();
					}
				} 
				
				for (FileItem item : multiparts) 
				{
					if ((item.getFieldName()).equals("clg_name")) {
						clg_name= (String) item.getString();
					}
				} 
				for (FileItem item : multiparts) 
				{
					if ((item.getFieldName()).equals("address")) {
						address = (String) item.getString();
					}
				} 
				for (FileItem item : multiparts) 
				{
					if ((item.getFieldName()).equals("email")) {
						email= (String) item.getString();
					}
				} 
				for (FileItem item : multiparts) 
				{
					if ((item.getFieldName()).equals("mobile")) {
						mobile= (String) item.getString();
					}
				} 
				String erp_no="";
				for (FileItem item : multiparts) 
				{
					if ((item.getFieldName()).equals("erp_no")) {
						erp_no = (String) item.getString();
					}
				} 
				
				for (FileItem item : multiparts) 
				{
					if ((item.getFieldName()).equals("password")) {
						password = (String) item.getString();
					}
				} 
				
				
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				Date dateobj = new Date();
				String c_date = df.format(dateobj);
				System.out.println("Current Date "+c_date);

				
				PreparedStatement ps=con.prepareStatement("INSERT INTO `student_details`(`st_name`, `gender`, `mobile`, `email`, `address`, `erp_no`, `password`, `college_name`,`profile_pic_name`) VALUES ('"+st_name+"','"+gender+"','"+mobile+"','"+email+"','"+address+"','"+erp_no+"','"+password+"','"+clg_name+"','"+FileName+"')");
				int i=ps.executeUpdate();
				if(i>0)
				{

					String msgC="Dear User  Your Login Credentials Username "+erp_no+" Password "+password;
					SendSMS.callURL(msgC, mobile,"LOGIN");
				
					System.out.println("Done");
					response.sendRedirect("studentLogin.jsp?reg=done");
				}
				else
				{
					System.out.println("Fail");
					response.sendRedirect("studentRegistration.jsp?freg=fail");
				}
			} catch (Exception e) {
				// TODO: handle exception
	  			System.out.println("Exception "+e);
			}
		}
  		else 
		{
				System.out.println("Condition False");
				response.sendRedirect("userRegistration.jsp?freg=fail");
		}
	}

}