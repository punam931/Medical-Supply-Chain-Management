package User_Order_Info;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.user.DBconn;

/**
 * Servlet implementation class User_doctor_prescription
 */
@WebServlet("/User_doctor_prescription")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
maxFileSize = 1024 * 1024 * 10, // 10MB
maxRequestSize = 1024 * 1024 * 50)
public class User_doctor_prescription extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public User_doctor_prescription() {
		super();
		//
	}

	String getFileName(Part filePart) {

		for (String cd : filePart.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				String fileName = cd.substring(cd.indexOf('=') + 1).trim()
						.replace("\"", "");
				return fileName.substring(fileName.lastIndexOf('/') + 1)
						.substring(fileName.lastIndexOf('\\') + 1);
				// return cd.substring(cd.indexOf('=') + 1).trim().replace("\"",
				// "") ;
			}

		}

		return null;

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		Part filePart = request.getPart("txt_search");
		InputStream inputStream = null;
		String file_name = getFileName(filePart);
		String emailid = request.getParameter("emailid");
		try {
			inputStream = filePart.getInputStream();
			Connection conn = null; // connection to the database
			
			conn = DBconn.conn();
			String Distributor_Email_Id = "0";
			String status = "0";
			// constructs SQL statement
			String sql = "insert into tbl_doctor_prescription(User_Email_ID,Distributor_Email_Id,File_Name,File_Data,Status,Manufacturer_Email_ID) values (?,?,?,?,?,?)";
			PreparedStatement statement = conn.prepareStatement(sql);

			if (inputStream != null) {
				statement.setString(1, emailid);
				statement.setString(2, Distributor_Email_Id);
				statement.setString(3, file_name);
				statement.setBlob(4, inputStream);

				statement.setString(5, status);
				statement.setString(6, Distributor_Email_Id);
				statement.executeUpdate();

				pw.println("<script> alert('Your Doctor Prescription Uploaded Successfully');</script>");
				RequestDispatcher rd = request
						.getRequestDispatcher("U_doctor_prescription_details.jsp");
				rd.include(request, response);

			} 
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
