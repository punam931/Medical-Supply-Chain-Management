package com.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.user.DBconn;

/**
 * Servlet implementation class Addmedicine
 */
@WebServlet("/Addmedicine")
public class Addmedicine extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Addmedicine() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	public static String substring(String MedicineName)
	{
		String msg;
		String mystring = new String(MedicineName);
		 Random rand = new Random(); 
		 int rand_int1 = rand.nextInt(100000);
		msg=mystring.substring(0,3)+rand_int1;
		return msg;
	}
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		HttpSession session = request.getSession(false);
		String MedicineName = request.getParameter("MedicineName");
		String companyname = request.getParameter("companyname");
		String Descriptionname = request.getParameter("Descriptionname");
		String mfgdate = request.getParameter("mfgdate");
		String price = request.getParameter("price");
		String Expirydate = request.getParameter("Expirydate");
		String qty = request.getParameter("qty");
		
		String Medicine_No=substring(MedicineName);
		try {
			Connection con = DBconn.conn();

			Statement st = (Statement) con.createStatement();
			st.executeUpdate("insert into medicine_info(MedicineName, CompanyName, DescriptionName, MFGDate,Price,ExpiryDate,Qty,Medicine_No) values ('"
							+ MedicineName
							+ "','"
							+ companyname
							+ "','"
							+ Descriptionname
							+ "','" + mfgdate + "','"+price+"','"+Expirydate+"','"+qty+"','"+Medicine_No+"')");

			

		} catch (Exception e) {
			System.out.println(e);
		}
		pw.println("<html><script>alert('Medicine Save Successfully');</script><body>");
		pw.println("");
		pw.println("</body></html>");
		response.sendRedirect("UpdateMedicine.jsp?succ");
		

	}

}
