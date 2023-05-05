package com.distribute;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import Blockchain.ptop;

import com.user.DBconn;

/**
 * Servlet implementation class distributeorder
 */
@WebServlet("/distributeorder")
public class distributeorder extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static int noofnode=4;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public distributeorder() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		PrintWriter pw = response.getWriter();
		String id = request.getParameter("id");
		String emailid = request.getParameter("emailid");
		String Distributor_Email_Id =(String)session.getAttribute("Cuserid");
		try {
			Connection conn = null; // connection to the database
			
			conn = DBconn.conn();
			
			String status = "Sending";
			Statement st=conn.createStatement();
			
			st.executeUpdate("update tbl_doctor_prescription set Distributor_Email_Id='"+Distributor_Email_Id+"',Status='"+status+"' where  User_Email_ID = '"+emailid+"' and D_Id='"+id+"'");

				pw.println("<script> alert('Prescription Order Successfully');</script>");
				RequestDispatcher rd = request
						.getRequestDispatcher("D_PrescriptionDataShow.jsp");
				rd.include(request, response);

			
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		HttpSession session = request.getSession(false);
		String id = request.getParameter("id");
		String Medicine_No = request.getParameter("Medicine_No");
		String qty = request.getParameter("qty");
String Distribute_Email_ID=(String)session.getAttribute("Cuserid");
String Distribute_Mobile_No=(String)session.getAttribute("Cmobile");
		try {
			Connection con = DBconn.conn();

			Statement st = (Statement) con.createStatement();
			String query = "select * from medicine_info where MID='" + id
					+ "'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				String MedicineName=rs.getString("MedicineName");
				String CompanyName=rs.getString("CompanyName");
				st.executeUpdate("insert into medicine_info_order(Distribute_Email_ID, Medicine_No,MedicineName,CompanyName,Order_Qty,MId,Distribute_Mobile_No,Status_Info) values ('"
						+ Distribute_Email_ID
						+ "','"
						+ Medicine_No
						+ "','"
						+ MedicineName
						+ "','"
						+ CompanyName
						+ "','"
						+ qty
						+ "','"
						+ id
						+ "','"
						+ Distribute_Mobile_No
						+ "','0')");
				String datainfo=Distribute_Email_ID+Medicine_No+MedicineName+CompanyName+qty+id+Distribute_Mobile_No;
				ptop.ptopverify(noofnode,datainfo);

			}
		} catch (Exception e) {
			System.out.println(e);
		}
		pw.println("<html><script>alert('Order Medicine Save Successfully');</script><body>");
		pw.println("");
		pw.println("</body></html>");
		response.sendRedirect("OrderShow.jsp?Order");

	}

}
