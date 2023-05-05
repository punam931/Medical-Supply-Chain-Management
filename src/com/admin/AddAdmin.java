package com.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.user.DBconn;

@WebServlet("/AddAdmin")
public class AddAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddAdmin() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		PrintWriter pw = response.getWriter();
		String id = request.getParameter("id");
		String emailid = request.getParameter("emailid");
		String Distributor_Email_Id = (String) session.getAttribute("Muserid");
		try {
			Connection conn = null; // connection to the database

			conn = DBconn.conn();

			String status = "Accept";
			Statement st = conn.createStatement();

			st.executeUpdate("update tbl_doctor_prescription set Manufacturer_Email_ID='"
					+ Distributor_Email_Id
					+ "',Status='"
					+ status
					+ "' where User_Email_ID = '"
					+ emailid
					+ "' and D_Id='"
					+ id + "'");

			pw.println("<script> alert('Prescription Order Successfully');</script>");
			RequestDispatcher rd = request
					.getRequestDispatcher("Admin_PrescriptionDataShow.jsp");
			rd.include(request, response);

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		HttpSession session = request.getSession(false);
		String SupplierName = request.getParameter("MedicineName");
		String companyname = request.getParameter("companyname");
		String Descriptionname = request.getParameter("Descriptionname");
		String mfgdate = request.getParameter("mfgdate");
		String price = request.getParameter("price");
		String Expirydate = request.getParameter("Expirydate");
		String qty = request.getParameter("qty");
		String New_Qty = request.getParameter("newqty");
		String T_No = request.getParameter("mid");
		String Manufacturer_Email_ID = (String) session.getAttribute("Muserid");
		String Manufacturer_Address = (String) session.getAttribute("Address");
		String Transporter_Status = "0";
		String Manufacturer_Status="0";
		String Temail="0";
		try {
			Connection con = DBconn.conn();

			Statement st = (Statement) con.createStatement();
			st.executeUpdate("insert into manufacturer_info_order(MedicineName, CompanyName, DescriptionName, MFGDate,Price,ExpiryDate,Qty,T_ID,Manufacturer_Email_ID,Manufacturer_Address,New_Qty,Manufacturer_Status,Transporter_Status,Temail) values ('"
					+ SupplierName
					+ "','"
					+ companyname
					+ "','"
					+ Descriptionname
					+ "','"
					+ mfgdate
					+ "','"
					+ price
					+ "','"
					+ Expirydate + "','" + qty + "','" + T_No + "','"+Manufacturer_Email_ID+"','"+Manufacturer_Address+"','"+New_Qty+"','"+Manufacturer_Status+"','"+Transporter_Status+"','"+Temail+"')");

		} catch (Exception e) {
			System.out.println(e);
		}
		pw.println("<html><script>alert('Medicine Save Successfully');</script><body>");
		pw.println("");
		pw.println("</body></html>");
		response.sendRedirect("Admin_Order_ManufacturerMedicine.jsp?succ");

	}
}
