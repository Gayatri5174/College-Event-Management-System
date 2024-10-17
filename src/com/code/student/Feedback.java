package com.code.student;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.code.conn.DbConnection;

/**
 * Servlet implementation class Feedback
 */
@WebServlet("/Feedback")
public class Feedback extends HttpServlet {
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
		HashMap<Integer, String> questAnswer=new HashMap<>();
		for(int i=1;i<15;i++)
		{
			String q=request.getParameter("q"+i);
			questAnswer.put(i, q);
		}
		
		System.out.println("Question Answer "+questAnswer);
		
		String eid=request.getParameter("eid");
		String pid=request.getParameter("pid");
		HttpSession session=request.getSession();
		String st_id = session.getAttribute("st_id").toString();
		
		for(Integer key:questAnswer.keySet())
		{
			String value = questAnswer.get(key);
			try 
			{
				PreparedStatement ps=con.prepareStatement("INSERT INTO `feedback_details`(`question_id`, `answer_value`, `event_id`, `st_id`) VALUES ('"+key+"','"+value+"','"+eid+"','"+st_id+"')");
				int i=ps.executeUpdate();
				if(i>0)
				{
					System.out.println("Question "+key+"Stored ");
				}
			} catch (Exception e) 
			{
				System.out.println("Exc "+e);
			}
		}
		
		try{
			PreparedStatement ps=con.prepareStatement("UPDATE `participant_details` SET feedback_status='Provide' where p_id='"+pid+"'");
			int i=ps.executeUpdate();
			if(i>0)
			{
				System.out.println("Feedback status is updated in Participant details table");
			}
			
		}catch(Exception e )
		{
			System.out.println("Exc "+e);
		}
		
		response.sendRedirect("enrollEventDetails.jsp?feedback=send");
		
		
		
		

	}

}
