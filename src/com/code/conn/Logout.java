package com.code.conn;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Logout
 */
@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try
		{
			HttpSession session=request.getSession();
			System.out.println("2nd session id="+session.getId());
			session.invalidate();
			//session=null;
			HttpSession session1=request.getSession();
			
			session1.setAttribute("acc_type", "Geust");
			response.sendRedirect("index.jsp?logout=done");
			return;
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
