package com;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;

public class RegisterUtility {
	public static UserVO getUserByEmail (String email) throws Exception{
		UserVO u= new UserVO();
		Connection con=null;
		PreparedStatement psmt=null;
		ResultSet rs=null;
		try{
			con=ConnectionUtility.getConnection();
		String query="select * from users where email=?";
			psmt= con.prepareStatement(query);
		psmt.setString(1,u.getEmail());
			rs= psmt.executeQuery();
	if(rs!=null && rs.next()){
		u.setName(rs.getString("username"));
		u.setEmail(rs.getString("email"));
		u.setContact(rs.getString("phone"));
		u.setUserId(rs.getLong("userid"));
	}
		}
		catch(Exception e){
			e.printStackTrace();
		}finally{
			rs.close();
			psmt.close();
			con.close();
		}
		return u;
	}
	
	public static boolean signUp (UserVO u) throws Exception{
	int rows=0;
	Connection con =null;
	PreparedStatement psmt= null;
	try{
		con = ConnectionUtility.getConnection();
		String query= "insert into users(username, email, phone,pwd,joindate) values(?,?,?,?,?)";
	
		java.sql.Date sqlDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		System.out.println("fads--"+query);
		psmt= con.prepareStatement(query);
		psmt.setString(1, u.getName());
		
		psmt.setString(2, u.getEmail());
		psmt.setString(3, u.getContact());
		psmt.setString(4, u.getPwd());
		psmt.setDate(5, sqlDate);
		//psmt.setLong(6, u.getUserId());
		rows=psmt.executeUpdate();
		System.out.println(rows);
	}
	catch(Exception e){
		e.printStackTrace();
		}finally{
			psmt.close();
			con.close();
	}
	
	if(rows>0)
		return true;
	else 
		return false;	
	}
	public static UserVO verify(String  email, String pwd) throws Exception{
		//boolean check= false;
		UserVO u=new UserVO();
		Connection con = null;
		PreparedStatement psmt=null;
		ResultSet rs= null;
		try{
			con = ConnectionUtility.getConnection();
			String query= "select username,pwd,userId,username from users where email= ?";
			
			psmt= con.prepareStatement(query);
			psmt.setString(1,email);
			rs= psmt.executeQuery();
			if(rs.next()){
			if(pwd.equals(rs.getString("pwd")))
					{
				u.setUserId(rs.getLong("userId"));
				u.setName(rs.getString("username"));
				u.setEmail(email);
				u.setPwd(pwd);
				//results.put(rs.getString(3));
				//System.out.println(rs.getString(3));
				//results.put("name",rs.getString(3));
			}
			}
		}
catch(Exception e){
	e.printStackTrace();
		}finally{
			rs.close();
			psmt.close();
			con.close();
}
		
		
		return  u;
	}
	public static UserVO getUser(long userId) throws Exception{
		UserVO u = new UserVO();
		Connection con = null;
		PreparedStatement psmt=null;
		ResultSet rs= null;
		try{
			con = ConnectionUtility.getConnection();
			String query= "select * from users where userid=?";
			
			psmt= con.prepareStatement(query);
			psmt.setLong(1,userId);
			rs= psmt.executeQuery();
			if(rs!=null && rs.next()){
				u.setName(rs.getString("username"));
				u.setEmail(rs.getString("email"));
				u.setContact(rs.getString("phone"));
				u.setUserId(rs.getLong("userid"));
				u.setPwd(rs.getString("pwd"));
				//u.set
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}finally{
			rs.close();
			psmt.close();
			con.close();
		}
		return u;
	}
}
