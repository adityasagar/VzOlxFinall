package com;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

import javax.mail.Message; 
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



public class ProductUtility {
	public static ProductVO getProduct( long value) throws Exception{
		//ArrayList<ProductVO> results= new ArrayList<ProductVO>();
		ProductVO p = new ProductVO();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			con=ConnectionUtility.getConnection();
			String query="Select * from products where productid=? ";
			ps= con.prepareStatement(query);
			ps.setLong(1,value);
			rs= ps.executeQuery();
			while(rs!=null && rs.next()){
				p.setProductId(rs.getLong("productid"));
				p.setName(rs.getString("productname"));
				p.setCategory(rs.getString("category"));
				p.setHits(rs.getLong("hits"));
				p.setPrice(rs.getFloat("price"));
				p.setOwnerId(rs.getLong("ownerid"));
				p.setSoldTo(rs.getLong("soldto"));
				p.setDescription(rs.getString("description"));
				p.setReason(rs.getString("reason"));
				p.setBuyDate(rs.getDate("buydate"));
				p.setSellDate(rs.getDate("selldate"));
				p.setImageLink(rs.getString("imagelink"));
			}
			
			
		}
catch(Exception e){
	e.printStackTrace();
		}finally{
			rs.close();
			ps.close();
			con.close();
}
		return p;
	}
	public static void sendEmail(ProductVO product,UserVO buyer,UserVO seller,String msg){
		try {
			new EmailSend().sendMail( product, buyer, seller, msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		/*
		String to=seller.getEmail();
		String from=buyer.getEmail();
		String host="smtp.verizon.com";
		System.out.println(to+" ,"+from);
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		Session session = Session.getDefaultInstance(properties);
		MimeMessage message= new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress("Verizon_Employee_Classifieds"));
		
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		message.setSubject("VZ Classified");
		message.setText(msg);
		Transport.send(message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}*/
	public static boolean productEntry(ProductVO p) throws Exception{
		int rows=0;
		boolean check=false;
		Connection con = null;
		PreparedStatement psmt= null;
		try{
			java.sql.Date sqlDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			con = ConnectionUtility.getConnection();
			String query= "insert into products(productname, category,hits, price,ownerid,description, reason, buydate, imagelink ) values(?,?,?,?,?,?,?,?,?)";
			psmt= con.prepareStatement(query);
			psmt.setString(1,p.getName() );
			psmt.setString(2, p.getCategory());
			psmt.setLong(3, 0);
			psmt.setFloat(4, p.getPrice());
			psmt.setLong(5,p.getOwnerId() );
			//psmt.setLong(6,p.getSoldTo() );
			psmt.setString(6,p.getDescription() );
			psmt.setString(7,p.getReason() );
			psmt.setDate(8,sqlDate );
			psmt.setString(9,getCategoryImage(p.getCategory()) );

			//psmt.setDate(10,p.getSellDate() );
			//psmt.setString(11,p.getImageLink() );
			rows =psmt.executeUpdate();
			if(rows>0)
				check=true;
		}
		catch(Exception e){
			e.printStackTrace();
		}finally{
			psmt.close();
			con.close();
		}
			return check;
		}
	
	public static String getCategoryImage( String category){
		String imageLink= "";
		if (category.equalsIgnoreCase("Electronics")){
			imageLink ="img/Electronics.png";
		}else if(category.equalsIgnoreCase("Vehicles")){
			imageLink ="img/vehicles.jpg";
		}else if(category.equalsIgnoreCase("Home & Furniture")){
			imageLink ="img/furnishing.jpg";
		}else if(category.equalsIgnoreCase("Animals")){
			imageLink ="img/animals.jpg";
		}else if(category.equalsIgnoreCase("Books,Sports & Hobbies")){
			imageLink ="img/sports.jpg";
		}else if(category.equalsIgnoreCase("Fashion & Beauty")){
			imageLink ="img/fashion.jpg";
		}else if(category.equalsIgnoreCase("Kids & Baby Products")){
			imageLink ="img/kids.jpg";
		}else if(category.equalsIgnoreCase("Services")){
			imageLink ="img/service.png";
		}else if(category.equalsIgnoreCase("Real Estate")){
			imageLink ="img/realEstate.gif";
		}else if(category.equalsIgnoreCase("Others")){
			imageLink ="img/other.png";
		}else{
			imageLink ="img/blank2.jpg";
		}
		return imageLink;
	}
	public static ArrayList<ProductVO> getCategoryResults( String value) throws SQLException{
		ArrayList<ProductVO> results= new ArrayList<ProductVO>();
		ProductVO p = new ProductVO();
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs= null;
		try{
			con=ConnectionUtility.getConnection();
			String query="Select * from products where category= ? limit 30";
			ps= con.prepareStatement(query);
			ps.setString(1,value);
			rs= ps.executeQuery();
			while(rs!=null && rs.next()){
				p.setProductId(rs.getLong("productid"));
				p.setName(rs.getString("productname"));
				p.setCategory(rs.getString("category"));
				p.setHits(rs.getLong("hits"));
				p.setPrice(rs.getFloat("price"));
				p.setOwnerId(rs.getLong("ownerid"));
				p.setSoldTo(rs.getLong("soldto"));
				p.setDescription(rs.getString("description"));
				p.setReason(rs.getString("reason"));
				p.setBuyDate(rs.getDate("buydate"));
				p.setSellDate(rs.getDate("selldate"));
				p.setImageLink(rs.getString("imagelink"));
				results.add(p);
			}
			
			
		}
catch(Exception e){
	e.printStackTrace();
		}finally{
			rs.close();
			ps.close();
			con.close();
}
		return results;
	}
	
	public static ArrayList<ProductVO> getSearchResults( String value) throws Exception{
		ArrayList<ProductVO> results= new ArrayList<ProductVO>();

		value="%"+value+"%";
		Connection con=null;
		PreparedStatement ps= null;
		ResultSet rs= null;
		try{
			con=ConnectionUtility.getConnection();
			String query="Select * from products where productname like ? limit 30";
			ps= con.prepareStatement(query);
			ps.setString(1,value);
			rs= ps.executeQuery();
			while(rs!=null && rs.next()){
				ProductVO p = new ProductVO();
				p.setProductId(rs.getLong("productid"));
				p.setName(rs.getString("productname"));
				p.setCategory(rs.getString("category"));
				p.setHits(rs.getLong("hits"));
				p.setPrice(rs.getFloat("price"));
				p.setOwnerId(rs.getLong("ownerid"));
				p.setSoldTo(rs.getLong("soldto"));
				p.setDescription(rs.getString("description"));
				p.setReason(rs.getString("reason"));
				p.setBuyDate(rs.getDate("buydate"));
				p.setSellDate(rs.getDate("selldate"));
				p.setImageLink(rs.getString("imagelink"));
				results.add(p);
			}
			
			
		}
catch(Exception e){
	e.printStackTrace();
		}finally{
			rs.close();
			ps.close();
			con.close();
}
		return results;
	}
	public static ArrayList<ProductVO> getMostViewedResults( String value) throws Exception{
		ArrayList<ProductVO> results= new ArrayList<ProductVO>();
		Connection con=null;
		PreparedStatement ps= null;
		ResultSet rs=null;
		//value="%"+value+"%";
		try{
			con=ConnectionUtility.getConnection();
			String query="Select * from products order by productid desc limit ? ";
			ps= con.prepareStatement(query);
			ps.setInt(1,Integer.parseInt(value));
			rs= ps.executeQuery();
			while(rs!=null && rs.next()){
				ProductVO p = new ProductVO();
				p.setProductId(rs.getLong("productid"));
				p.setName(rs.getString("productname"));
				p.setCategory(rs.getString("category"));
				p.setHits(rs.getLong("hits"));
				p.setPrice(rs.getFloat("price"));
				p.setOwnerId(rs.getLong("ownerid"));
				p.setSoldTo(rs.getLong("soldto"));
				p.setDescription(rs.getString("description"));
				p.setReason(rs.getString("reason"));
				p.setBuyDate(rs.getDate("buydate"));
				p.setSellDate(rs.getDate("selldate"));
				p.setImageLink(rs.getString("imagelink"));
				results.add(p);
			}
			
			
		}
catch(Exception e){
	e.printStackTrace();
		}finally{
			rs.close();
			ps.close();
			con.close();
}
		return results;
	}
	public static ArrayList<ProductVO> getRecentResults( String value) throws Exception{
		ArrayList<ProductVO> results= new ArrayList<ProductVO>();
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs= null;
		//value="%"+value+"%";
		try{
			con=ConnectionUtility.getConnection();
			String query="Select * from products order by productid desc limit ? ";
			ps= con.prepareStatement(query);
			ps.setInt(1,Integer.parseInt(value));
			rs= ps.executeQuery();
			while(rs!=null && rs.next()){
				ProductVO p = new ProductVO();
				p.setProductId(rs.getLong("productid"));
				p.setName(rs.getString("productname"));
				p.setCategory(rs.getString("category"));
				p.setHits(rs.getLong("hits"));
				p.setPrice(rs.getFloat("price"));
				p.setOwnerId(rs.getLong("ownerid"));
				p.setSoldTo(rs.getLong("soldto"));
				p.setDescription(rs.getString("description"));
				p.setReason(rs.getString("reason"));
				p.setBuyDate(rs.getDate("buydate"));
				p.setSellDate(rs.getDate("selldate"));
				p.setImageLink(rs.getString("imagelink"));
				results.add(p);
			}
			
			
		}
catch(Exception e){
	e.printStackTrace();
		}finally{
			rs.close();
			ps.close();
			con.close();
}
		return results;
	}
}
