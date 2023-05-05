package com.distribute;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.user.DBconn;

/**
 * Servlet implementation class savemedicine
 */
@WebServlet("/savemedicine")
public class savemedicine extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public savemedicine() {
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
		String emailid = (String) session.getAttribute("Cuserid");
		String MedicineName = request.getParameter("MedicineName");
		String companyname = request.getParameter("companyname");
		String Descriptionname = request.getParameter("Descriptionname");
		String mfgdate = request.getParameter("mfgdate");
		String price = request.getParameter("price");
		String Expirydate = request.getParameter("Expirydate");
		String qty = request.getParameter("qty");
		String totalprice = request.getParameter("totalprice");
		String Distribute_Mobile_No = (String) session.getAttribute("Cmobile");
		String Medicine_No = request.getParameter("meid");
		String Order_Qty = "";
		try {
			Connection con = DBconn.conn();

			Statement stRegister12 = (Statement) con.createStatement();
			ResultSet rsLogin12 = stRegister12
					.executeQuery("select * from distribute_medicine_info where Medicine_No='"
							+ Medicine_No + "'");
			if (rsLogin12.next()) {

				Order_Qty = rsLogin12.getString("Qty");
			} else {
				Order_Qty = "0";
			}
			Statement st = (Statement) con.createStatement();
			Statement st3 = (Statement) con.createStatement();
			ResultSet rs1;
			String str1 = "select * from distribute_medicine_info where Medicine_No='"
					+ Medicine_No + "'";
			rs1 = ((java.sql.Statement) st3).executeQuery(str1);
			if (rs1.next()) {
				int oqty = Integer.parseInt(Order_Qty);
				int newqty = Integer.parseInt(qty);
				int newtotalqty = oqty + newqty;
				System.out.println("All Ready Exsit");
				Statement ss = con.createStatement();
				ss.executeUpdate("update distribute_medicine_info set MedicineName='"
						+ MedicineName
						+ "', CompanyName='"
						+ companyname
						+ "', DescriptionName='"
						+ Descriptionname
						+ "', MFGDate='"
						+ mfgdate
						+ "',Price='"
						+ price
						+ "',ExpiryDate='"
						+ Expirydate
						+ "',Qty='"
						+ newtotalqty
						+ "' where Medicine_No='"
						+ Medicine_No
						+ "'");
				Statement stt = (Statement) con.createStatement();
				ResultSet rs11;
				String str = "select * from tblamount where Email_ID='"
						+ emailid + "'";

				rs11 = ((java.sql.Statement) stt).executeQuery(str);
				if (rs11.next()) {
					int dbprice = rs11.getInt("Amount");
					int newbill = Integer.valueOf(totalprice);
					int newtotalprice = dbprice - newbill;
					st.executeUpdate("insert into distribute_medicine_info_bill(MedicineName, CompanyName, DescriptionName, MFGDate,Price,ExpiryDate,Qty,Medicine_No,Paybill,Email_ID,Distribute_Mobile_No) values ('"
							+ MedicineName
							+ "','"
							+ companyname
							+ "','"
							+ Descriptionname
							+ "','"
							+ mfgdate
							+ "','"
							+ price
							+ "','"
							+ Expirydate
							+ "','"
							+ qty
							+ "','"
							+ Medicine_No
							+ "','"
							+ totalprice
							+ "','"
							+ emailid + "','" + Distribute_Mobile_No + "')");

					Statement sts = con.createStatement();
					sts.executeUpdate("update tblamount set Amount='"
							+ newtotalprice + "' where Email_ID='" + emailid
							+ "'");
				}

			} else {
				int oqty = Integer.parseInt(Order_Qty);
				int newqty = Integer.parseInt(qty);
				int newtotalqty = oqty + newqty;
				st.executeUpdate("insert into distribute_medicine_info(MedicineName, CompanyName, DescriptionName, MFGDate,Price,ExpiryDate,Qty,Medicine_No) values ('"
						+ MedicineName
						+ "','"
						+ companyname
						+ "','"
						+ Descriptionname
						+ "','"
						+ mfgdate
						+ "','"
						+ price
						+ "','"
						+ Expirydate
						+ "','"
						+ qty
						+ "','"
						+ Medicine_No + "')");

				Statement stt = (Statement) con.createStatement();
				ResultSet rs11;
				String str = "select * from tblamount where Email_ID='"
						+ emailid + "'";

				rs11 = ((java.sql.Statement) stt).executeQuery(str);
				if (rs11.next()) {
					int dbprice = rs11.getInt("Amount");
					int newbill = Integer.valueOf(totalprice);
					int newtotalprice = dbprice - newbill;
					st.executeUpdate("insert into distribute_medicine_info_bill(MedicineName, CompanyName, DescriptionName, MFGDate,Price,ExpiryDate,Qty,Medicine_No,Paybill,Email_ID,Distribute_Mobile_No) values ('"
							+ MedicineName
							+ "','"
							+ companyname
							+ "','"
							+ Descriptionname
							+ "','"
							+ mfgdate
							+ "','"
							+ price
							+ "','"
							+ Expirydate
							+ "','"
							+ newtotalqty
							+ "','"
							+ Medicine_No
							+ "','"
							+ totalprice
							+ "','"
							+ emailid + "','" + Distribute_Mobile_No + "')");

					Statement sts = con.createStatement();
					sts.executeUpdate("update tblamount set Amount='"
							+ newtotalprice + "' where Email_ID='" + emailid
							+ "'");
				}
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		pw.println("<html><script>alert('Medicine Save Successfully');</script><body>");
		pw.println("");
		pw.println("</body></html>");
		response.sendRedirect("QRCode_details.jsp?Done");
	}

}
