package com.thbs.servlets;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
@SuppressWarnings("serial")
public class AddTrainFwd extends HttpServlet{
	protected void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException
	{
		res.setContentType("text/html");
		PrintWriter pw = res.getWriter();
		Cookie ck[] = req.getCookies();
		if(ck!=null) {
			String uName = ck[0].getValue();
			if(!uName.equals("")||uName!=null) {
				RequestDispatcher rd = req.getRequestDispatcher("AddTrains.html");
				rd.forward(req, res);
			}
		}
		else {
			RequestDispatcher rd = req.getRequestDispatcher("AdminLogin.html");
			rd.include(req, res);
			pw.println("<div style='text-align:center; margin-top:3%; font-size:15px; font-weight:700;color:#00008B;' class='tab'><p1 class='menu'>Please Login first !</p1></div>");
		}
	}

}
