package com.qrcode;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.user.DBconn;

public class product_detection {

	public static int ProductExpiryDate(String id,String ExpiryDate)
	{
		int flag=0;
		try {
			Connection con = (Connection) DBconn.conn();
			Statement stRegister1 = (Statement) con.createStatement();
			ResultSet rsLogin1 = stRegister1
					.executeQuery("select * from medicine_info where Medicine_No='"+id+"'");
			if (rsLogin1.next()) {
				
				flag=exp_date(ExpiryDate,rsLogin1.getString("ExpiryDate"));
				
			}
		}
		catch(Exception e)
		{}
		
		
		return flag;
	}
	
	public static int exp_date(String Exp_date,String dbExp_date) {
		int dateflag=0;
		try {
			SimpleDateFormat sdfnew = new SimpleDateFormat("yyyy-MM-dd");
			Date date1 = sdfnew.parse(dbExp_date);  
			Date date2 = sdfnew.parse(Exp_date); 
			System.out.println("C date1=>"+date1+"\t E date2=>"+date2);
			if(date1.compareTo(date2) > 0)   
			{  
				dateflag=1;
				System.out.println("Working Valid Expiry Date"); 
			}   
			else if(date1.compareTo(date2) < 0)   
			{  
				dateflag=0;
			System.out.println("Expiry Date");  
			}   
			else if(date1.compareTo(date2) == 0)   
			{  
				dateflag=1;
			System.out.println("Working Valid Both dates are equal");  
			}  
		} catch (Exception e) {
			//
			e.printStackTrace();
		} // Instantiate a Date object
		return dateflag;
	}
	
	public static void main(String[] args) {
		ProductExpiryDate("Cor40134","2025-04-27");
		//exp_date("2023-04-07","2023-04-06");

	}

}
