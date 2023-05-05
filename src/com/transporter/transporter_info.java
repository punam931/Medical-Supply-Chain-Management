package com.transporter;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.user.DBconn;

/**
 * Servlet implementation class transporter_info
 */
@WebServlet("/transporter_info")
public class transporter_info extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public transporter_info() {
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
		String id = request.getParameter("id");
		String Temailid=(String)session.getAttribute("Tuserid");
		System.out.println("ID=>" + id);

		PrintWriter pw = response.getWriter();
		try {
			Connection con = (Connection) DBconn.conn();
			String MID = "";
			String Manufacturer_Email_ID = "";
			String msg = "Accept";
			String msg1 = "Deliver";
			Statement stRegister12 = (Statement) con.createStatement();
			ResultSet rsLogin12 = stRegister12
					.executeQuery("select * from manufacturer_info_order where OID='"
							+ id + "'");
			if (rsLogin12.next()) {
				Manufacturer_Email_ID = rsLogin12
						.getString("Manufacturer_Email_ID");
				MID = rsLogin12.getString("T_ID");
			}

			Statement stRegister101 = con.createStatement();

			stRegister101
					.executeUpdate("update manufacturer_info_order set Manufacturer_Status='"
							+ msg1
							+ "',Transporter_Status='"+msg+"', Temail='"+Temailid+"' where OID='"
							+ id
							+ "' and T_ID='"
							+ MID
							+ "' and Manufacturer_Email_ID='"
							+ Manufacturer_Email_ID + "'");

			//

			pw.println("<html><script>alert('Status Update Successfully');</script><body>");
			pw.println("");
			pw.println("</body></html>");
			response.sendRedirect("Transporter_Order_ManufacturerMedicine.jsp?Update");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
