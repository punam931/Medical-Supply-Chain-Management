package com.admin;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.user.DBconn;

/**
 * Servlet implementation class updatemedicine
 */
@WebServlet("/updatemedicine")
public class updatemedicine extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int BUFFER_SIZE = 4096;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public updatemedicine() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
try {

			
			String id = request.getParameter("id");
			String emailid = request.getParameter("emailid");
			Connection con = DBconn.conn();
			
			String sql1 = "SELECT * FROM tbl_doctor_prescription where User_Email_ID = ? and D_Id=?";
			PreparedStatement stt1 = con.prepareStatement(sql1);
			stt1.setString(1, emailid);
			stt1.setString(2, id);
			ResultSet result1 = stt1.executeQuery();
			if (result1.next()) {
				// gets file name and file blob data
				String fileName = result1.getString("File_Name");
				Blob blob = result1.getBlob("File_Data");
				InputStream inputStream = blob.getBinaryStream();
				int fileLength = inputStream.available();

				System.out.println("fileLength = " + fileLength);

				ServletContext context = getServletContext();

				// sets MIME type for the file download
				String mimeType = context.getMimeType(fileName);
				if (mimeType == null) {
					mimeType = "application/octet-stream";
				}

				// set content properties and header attributes for the response
				response.setContentType(mimeType);
				response.setContentLength(fileLength);
				String headerKey = "Content-Disposition";
				String headerValue = String.format(
						"attachment; filename=\"%s\"", fileName);
				response.setHeader(headerKey, headerValue);

				// writes the file to the client
				OutputStream outStream = response.getOutputStream();

				byte[] buffer = new byte[BUFFER_SIZE];
				int bytesRead = -1;

				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outStream.write(buffer, 0, bytesRead);
				}

				inputStream.close();
				outStream.close();
			} else {
				// no file found
				// response.getWriter().print("File not found for the id: " +
				// fileName);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			response.getWriter().print("SQL Error: " + ex.getMessage());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		HttpSession session = request.getSession(false);
		String MedicineName = request.getParameter("MedicineName");
		String companyname = request.getParameter("companyname");
		String Descriptionname = request.getParameter("Descriptionname");
		String mfgdate = request.getParameter("mfgdate");
		String price = request.getParameter("price");
		String Expirydate = request.getParameter("Expirydate");
		String qty = request.getParameter("qty");
		
		String Medicine_No=request.getParameter("meid");
		String mid=request.getParameter("mid");
		String Order_Qty="";
		try {
			Connection con = DBconn.conn();

			Statement stRegister12 = (Statement) con.createStatement();
			ResultSet rsLogin12 = stRegister12
					.executeQuery("select * from medicine_info where MID='"
							+ mid + "' and Medicine_No='"+Medicine_No+"'");
			if (rsLogin12.next()) {
				
				Order_Qty = rsLogin12.getString("Qty");
			}
			int oqty=Integer.parseInt(Order_Qty);
			int newqty=Integer.parseInt(qty);
			int newtotalqty=oqty+newqty;
			Statement st = (Statement) con.createStatement();
			st.executeUpdate("update medicine_info set MedicineName='"+MedicineName+"', CompanyName='"+companyname+"', DescriptionName='"+Descriptionname+"', MFGDate='"+mfgdate+"',Price='"+price+"',ExpiryDate='"+Expirydate+"',Qty='"+newtotalqty+"' where Medicine_No='"+Medicine_No+"' and MID='"+mid+"'");

			

		} catch (Exception e) {
			System.out.println(e);
		}
		pw.println("<html><script>alert('Medicine Save Successfully');</script><body>");
		pw.println("");
		pw.println("</body></html>");
		response.sendRedirect("Admin_updateMedicine.jsp?succ");
	}

}
