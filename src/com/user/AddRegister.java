package com.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.user.DBconn;

/**
 * Servlet implementation class AddRegister
 */
@WebServlet("/AddRegister")
public class AddRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddRegister() {
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		HttpSession session = request.getSession(false);
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String MbNo = request.getParameter("MbNo");
		String role=request.getParameter("roll");
String Address=request.getParameter("address");
		try {
			Connection con = DBconn.conn();
			
			if(role.equalsIgnoreCase("Supplier"))
			{
				Statement st1 = con.createStatement();
				String str = "select * from supplierregistertbl where Semail='" + email + "'";
				ResultSet rs1 = ((java.sql.Statement) st1).executeQuery(str);
				if (rs1.next()) {
					pw.println("<script> alert('AllReady Email-ID Existing');</script>");
				} else {
				
				Statement st = (Statement) con.createStatement();
				((java.sql.Statement) st)
						.executeUpdate("insert into supplierregistertbl(Susername, Semail, Spassword, SMbNo,Address) values ('"
								+ username
								+ "','"
								+ email
								+ "','"
								+ password
								+ "','" + MbNo + "','"+Address+"')");
				
				pw.println("<script> alert(' Register Manufacture Successfuly');</script>");
				}
				
			}
					
			else if(role.equalsIgnoreCase("Transporter"))
			{
				Statement st1 = con.createStatement();
				String str = "select * from transporterregistertbl where Temail='" + email + "'";
				ResultSet rs1 = ((java.sql.Statement) st1).executeQuery(str);
				if (rs1.next()) {
					pw.println("<script> alert('AllReady Email-ID Existing');</script>");
				} else {
				
				Statement st = (Statement) con.createStatement();
				((java.sql.Statement) st)
						.executeUpdate("insert into transporterregistertbl(Tusername, Temail, Tpassword, TMbNo,Address) values ('"
								+ username
								+ "','"
								+ email
								+ "','"
								+ password
								+ "','" + MbNo + "','"+Address+"')");
				
				
				pw.println("<script> alert(' Register Distribute Successfuly');</script>");
				}
				
			}
			else if(role.equalsIgnoreCase("Manufacture"))
			{
				Statement st1 = con.createStatement();
				String str = "select * from manufactureregistertbl where Memail='" + email + "'";
				ResultSet rs1 = ((java.sql.Statement) st1).executeQuery(str);
				if (rs1.next()) {
					pw.println("<script> alert('AllReady Email-ID Existing');</script>");
				} else {
				
				Statement st = (Statement) con.createStatement();
				((java.sql.Statement) st)
						.executeUpdate("insert into manufactureregistertbl(Musername, Memail, Mpassword, MMbNo,Address) values ('"
								+ username
								+ "','"
								+ email
								+ "','"
								+ password
								+ "','" + MbNo + "','"+Address+"')");
				
				pw.println("<script> alert(' Register Manufacture Successfuly');</script>");
				}
				
			}
					
			else if(role.equalsIgnoreCase("Distribute"))
			{
				Statement st1 = con.createStatement();
				String str = "select * from companyregistertbl where Cemail='" + email + "'";
				ResultSet rs1 = ((java.sql.Statement) st1).executeQuery(str);
				if (rs1.next()) {
					pw.println("<script> alert('AllReady Email-ID Existing');</script>");
				} else {
				
				Statement st = (Statement) con.createStatement();
				((java.sql.Statement) st)
						.executeUpdate("insert into companyregistertbl(Cusername, Cemail, Cpassword, CMbNo,Address) values ('"
								+ username
								+ "','"
								+ email
								+ "','"
								+ password
								+ "','" + MbNo + "','"+Address+"')");
				Statement stt1 = (Statement) con.createStatement();
				((java.sql.Statement) stt1)
						.executeUpdate("insert into tblamount(Email_ID,Amount) values ('"
								+ email
								+ "','100000000')");
				
				pw.println("<script> alert(' Register Distribute Successfuly');</script>");
				}
				
			}
			else{
			
			Statement st1 = con.createStatement();
			String str = "select * from registertbl where Uemail='" + email + "'";
			ResultSet rs1 = ((java.sql.Statement) st1).executeQuery(str);
			if (rs1.next()) {
				pw.println("<script> alert('AllReady Email-ID Existing');</script>");
			} else {
			
			Statement st = (Statement) con.createStatement();
			((java.sql.Statement) st)
					.executeUpdate("insert into registertbl(Uusername, Uemail, Upassword, UMbNo,Address) values ('"
							+ username
							+ "','"
							+ email
							+ "','"
							+ password
							+ "','" + MbNo + "','"+Address+"')");
			
			Statement stt1 = (Statement) con.createStatement();
			((java.sql.Statement) stt1)
					.executeUpdate("insert into tbluser_amount(Email_ID,Amount) values ('"
							+ email
							+ "','100000000')");
			pw.println("<script> alert(' Register USer Successfuly');</script>");
			}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		response.sendRedirect("LoginPage.jsp?succ");
//		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
//		rd.include(request, response);
	}

}
