package com.supplier;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.qrcode.Create_QR;
import com.user.DBconn;

/**
 * Servlet implementation class supplier_info
 */
@WebServlet("/supplier_info")
public class supplier_info extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public supplier_info() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		System.out.println("ID=>" + id);
		
		PrintWriter pw = response.getWriter();
		try {
			Connection con = (Connection) DBconn.conn();
			int flag = 0;
			String MID="";
			String Order_Qty = "", Manufacturer_Email_ID = "";
			String msg = "Accept";
			Statement stRegister12 = (Statement) con.createStatement();
			ResultSet rsLogin12 = stRegister12
					.executeQuery("select * from manufacturer_info_order where OID='"
							+ id + "'");
			if (rsLogin12.next()) {
				Manufacturer_Email_ID = rsLogin12
						.getString("Manufacturer_Email_ID");
				Order_Qty = rsLogin12.getString("New_Qty");
				MID=rsLogin12.getString("T_ID");
			}
			int oldqty = 0;
			String MedicineName = "", CompanyName = "", DescriptionName = "", MFGDate = "", Price = "", ExpiryDate = "", Medicine_No = "";
			Statement stRegister1 = (Statement) con.createStatement();
			ResultSet rsLogin1 = stRegister1
					.executeQuery("select * from supplier_info where MID='"
							+ MID + "'");
			if (rsLogin1.next()) {
				MedicineName = rsLogin1.getString("SupplierName");
				CompanyName = rsLogin1.getString("CompanyName");
				DescriptionName = rsLogin1.getString("Raw_Materials_Data");
				MFGDate = rsLogin1.getString("MFGDate");
				Price = rsLogin1.getString("Price");
				ExpiryDate = rsLogin1.getString("ExpiryDate");
				Medicine_No = rsLogin1.getString("Supplier_No");
				oldqty = rsLogin1.getInt("Qty");
			}

			//
			Statement st101 = con.createStatement();
			int oqty = Integer.parseInt(Order_Qty);
			System.out.println("O_QTY" + oqty + "\t Old Qty" + oldqty);
			if (oqty <= oldqty) {

				int totalqty = oldqty - oqty;
				st101.executeUpdate("update supplier_info set Qty='" + totalqty
						+ "' where MId='" + MID + "'");
				Statement stRegister101 = con.createStatement();
				String acc = "1";
				stRegister101
						.executeUpdate("update manufacturer_info_order set Manufacturer_Status='"
								+ msg
								+ "' where OID='"
								+ id
								+ "' and T_ID='" + MID + "' and Manufacturer_Email_ID='"+Manufacturer_Email_ID+"'");

//				String Username = Manufacturer_Email_ID + "_" + id + "_"
//						+ MID;
//				File finalpath = new File(DBconn.filepath, Username);
//				finalpath.mkdir();
//				String path = DBconn.filepath + "\\" + Username + "\\"
//						+ Username + ".png";
//				//System.out.println(path);
//				// creating QR code
//				String datainfo = MedicineName + "@0@" + CompanyName + "@0@"
//						+ DescriptionName + "@0@" + MFGDate + "@0@" + Price
//						+ "@0@" + ExpiryDate + "@0@" + Medicine_No+"@0@"+id;
//				
//				Create_QR.CreateQR(datainfo, path);
				
				pw.println("<html><script>alert('Status Update Successfully');</script><body>");
				pw.println("");
				pw.println("</body></html>");
				response.sendRedirect("Supplier_Order_ManufacturerMedicine.jsp?Update");
			} else {
				pw.println("<html><script>alert('Out of stock');</script><body>");
				pw.println("");
				pw.println("</body></html>");
				response.sendRedirect("Supplier_Order_ManufacturerMedicine.jsp?stock");
			}

		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public static String substring(String SupplierName)
	{
		String msg;
		String mystring = new String(SupplierName);
		 Random rand = new Random(); 
		 int rand_int1 = rand.nextInt(100000);
		msg=mystring.substring(0,1)+rand_int1;
		return msg;
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		HttpSession session = request.getSession(false);
		String SupplierName = request.getParameter("MedicineName");
		String companyname = request.getParameter("companyname");
		String Descriptionname = request.getParameter("Descriptionname");
		String mfgdate = request.getParameter("mfgdate");
		String price = request.getParameter("price");
		String Expirydate = request.getParameter("Expirydate");
		String qty = request.getParameter("qty");
		
		String Supplier_No=substring(SupplierName);
		try {
			Connection con = DBconn.conn();

			Statement st = (Statement) con.createStatement();
			st.executeUpdate("insert into supplier_info(SupplierName, CompanyName, Raw_Materials_Data, MFGDate,Price,ExpiryDate,Qty,Supplier_No) values ('"
							+ SupplierName
							+ "','"
							+ companyname
							+ "','"
							+ Descriptionname
							+ "','" + mfgdate + "','"+price+"','"+Expirydate+"','"+qty+"','"+Supplier_No+"')");

			

		} catch (Exception e) {
			System.out.println(e);
		}
		pw.println("<html><script>alert('Add Raw Materials Save Successfully');</script><body>");
		pw.println("");
		pw.println("</body></html>");
		response.sendRedirect("Supplier_Raw_Materials_Medicine.jsp?succ");
		

	}
	

}
