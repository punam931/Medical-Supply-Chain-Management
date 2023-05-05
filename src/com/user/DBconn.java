package com.user;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBconn {

	public static String filepath="G:\\BE2022-2023\\Vinod Sir\\22C9732-medical-blockchai\\QRCode";
	public static String newfilepath="G:/BE2022-2023/Vinod Sir/22C9732-medical-blockchai/QRCode";
	public static Connection conn() throws ClassNotFoundException, SQLException
	{
		Connection con;
		 Class.forName("com.mysql.jdbc.Driver");
		  con=DriverManager.getConnection("jdbc:mysql://localhost:3306/22C9732_medicine_supply_chain","root","admin");
		   
	return con;
	}
	public static int productcheck(String productno) {
		int msg = 0;
		Connection con;
		try {
			con = DBconn.conn();

			Statement st = (Statement) con.createStatement();
			ResultSet rs;
			String str = "select * from medicine_info where Medicine_No='"
					+ productno + "'";
			rs = ((java.sql.Statement) st).executeQuery(str);
			if (rs.next()) {
				msg=2;
			} else {
				msg=1;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}
	public static void main(String args[]) {
		String mystring = new String("Lets Learn Java");
		/* The index starts with 0, similar to what we see in the arrays
		 * The character at index 0 is s and index 1 is u, since the beginIndex
		 * is inclusive, the substring is starting with char 'u'
		 */
		System.out.println("substring(1):"+mystring.substring(1));
			
		/* When we pass both beginIndex and endIndex, the length of returned
		 * substring is always endIndex - beginIndex which is 3-1 =2 in this example
		 * Point to note is that unlike beginIndex, the endIndex is exclusive, that is 
		 * why char at index 1 is present in substring while the character at index 3 
		 * is not present.
		 */
		System.out.println("substring(1,3):"+mystring.substring(0,3));
	   }
}
