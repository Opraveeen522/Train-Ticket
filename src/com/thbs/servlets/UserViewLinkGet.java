package com.thbs.servlets;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.thbs.jdbc_connections.DBConnection;

import java.sql.*;
@SuppressWarnings("serial")
public class UserViewLinkGet extends HttpServlet{
	protected void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException
	{
		res.setContentType("text/html");
		PrintWriter pw = res.getWriter();
		Cookie ck[] = req.getCookies();
		if(ck!=null) {
			String uName = ck[0].getValue();
			String pWord = ck[1].getValue();
			if(!uName.equals("")||uName!=null) {
				try {
					Connection con = DBConnection.getCon();
					PreparedStatement ps = con.prepareStatement("Select * from trains where tr_no=? and from_stn=? and to_stn=?");
					ps.setLong(1,Long.parseLong(req.getParameter("trainNo")));
					ps.setString(2, req.getParameter("fromStn"));
					ps.setString(3, req.getParameter("toStn"));
					ResultSet rs = ps.executeQuery();
					if(rs.next()) {
						RequestDispatcher rd = req.getRequestDispatcher("UserHome.html");
						rd.include(req, res);
						pw.println("<div style='text-align:center; margin-top:3%; font-size:25px; font-weight:700;color:white;' class='tab'>" + 
								"		<p1 class='menu'>" + 
								"	Hello "+uName+" ! Welcome to THBS " + 
								"		</p1>" + 
								"	</div>");
						pw.println("<div style='text-align:center; margin-top:3%; font-size:15px; font-weight:700;color:white;'class='main'><p1 class='menu'>Selected Train Detail</p1></div>");
						pw.println("<div style='text-align:center; margin-top:3%; font-size:20px; font-weight:700;color:#ffdb58;' class='tab'>"
								+ "<table border=1 align=center cellpadding=1>"
								+ "<tr><td class='blue'>Train Name :</td><td>"+rs.getString("tr_name")+"</td></tr>"
								+ "<tr><td class='blue'>Train Number :</td><td>"+rs.getLong("tr_no")+"</td></tr>"
								+ "<tr><td class='blue'>From Station :</td><td>"+rs.getString("from_Stn")+"</td></tr>"
								+ "<tr><td class='blue'>To Station :</td><td>"+rs.getString("to_Stn")+"</td></tr>"
								+ "<tr><td class='blue'>Available Seats:</td><td>"+rs.getLong("available")+"</td></tr>"
								+ "<tr><td class='blue'>Fare (INR) :</td><td>"+rs.getLong("fare")+" RS</td></tr>"
								+ "</table>"
								+ "</div>");
					}
					else {
						RequestDispatcher rd = req.getRequestDispatcher("SearchTrains.html");
						rd.include(req, res);
						pw.println("<div style='text-align:center; margin-top:3%; font-size:15px; font-weight:700;color:#00008B;' class='tab'><p1 class='menu'>Train No."+req.getParameter("trainnumber")+" is Not Available !</p1></div>");
					}
				}
				catch(Exception e) {}
				
			}
		}
		else {
			RequestDispatcher rd = req.getRequestDispatcher("UserLogin.html");
			rd.include(req, res);
			pw.println("<div style='text-align:center; margin-top:3%; font-size:15px; font-weight:700;color:#00008B;'class='tab'><p1 class='menu'>Please Login first !</p1></div>");
		}
	}

}
