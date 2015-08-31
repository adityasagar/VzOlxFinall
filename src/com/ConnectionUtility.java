package com;

import java.sql.*;

public class ConnectionUtility {
	public static Connection getConnection() {
		Connection conn = null;
		try{
		Class.forName("com.mysql.jdbc.Driver");

conn = DriverManager.getConnection("jdbc:mysql://us-cdbr-iron-east-02.cleardb.net/ad_4456c5904edd24a?user=bd27cc9f0f5478&password=c52e805d");}
		catch(Exception e){
			e.printStackTrace();
		}
return conn;
	}
	public static Connection getConnection1() throws Exception{
		Class jdbcDriver = Class.forName("com.mysql.jdbc.Driver");
		Driver driver = (Driver) jdbcDriver.newInstance();
		DriverManager.registerDriver(driver);
		Connection con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/XE","system","root");
		return con;
	}
	
	
	/*public static void main(String args[]){
		Connection conn= ConnectionUtility.getConnection();
		System.out.println("hello");
	}*/
}
