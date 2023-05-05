package com.distribute;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.qrcode.Read_QR;
import com.qrcode.product_detection;
import com.user.DBconn;

/**
 * Servlet implementation class QRCodeupload
 */
@WebServlet("/QRCodeupload")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
maxFileSize = 1024 * 1024 * 10, // 10MB
maxRequestSize = 1024 * 1024 * 50)
public class QRCodeupload extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static String tenfilepath = null;
	private static final int BUFFER_SIZE = 4096;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QRCodeupload() {
		super();
		// TODO Auto-generated constructor stub
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		Part tenfile = request.getPart("qrcodefile");
		InputStream teninputStream = null;
		String tenmsg = "qr.png";
		try {

			teninputStream = tenfile.getInputStream();
			String relativeWebPath = "/output/" + tenmsg;
			String absoluteDiskPath = getServletContext().getRealPath(
					relativeWebPath);
			File f = new File(absoluteDiskPath);
			tenfilepath = f.getAbsolutePath().toString();
			OutputStream outStream = new FileOutputStream(f);
			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;
			while ((bytesRead = teninputStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}
			teninputStream.close();
			outStream.close();
			String dncg = Read_QR.readQR(tenfilepath);

			f.delete();
			System.out.println("Data read from QR Code:" + dncg);
			if (dncg.equalsIgnoreCase("Unable to scan")) {
				response.sendRedirect("QRCode_details.jsp?Unable");
			} else {
				String[] medicinedata = dncg.split("@0@");

				System.out.println(medicinedata.length);
				if (medicinedata.length < 8) {
					response.sendRedirect("QRCode_details.jsp?Unable");
				} else {
					String productno = medicinedata[6];
					int ch = DBconn.productcheck(productno);
					if (ch == 1) {
						response.sendRedirect("QRCode_details.jsp?Fake");
					} else {
						String medicinno = medicinedata[6];
						
						String ExpiryDate = medicinedata[5];
						int exdate = product_detection.ProductExpiryDate(
								medicinno, ExpiryDate);
						if (exdate == 1) {
							System.out.println(dncg + "\tMedicine No:"
									+ medicinno);
							response.sendRedirect("Dist_ShowMedicine.jsp?medicinno="
									+ medicinno);
						} else {
							response.sendRedirect("QRCode_details.jsp?Fake");
						}

					}

				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
