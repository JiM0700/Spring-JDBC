package com.jdbc.demo.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Calendar;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.jdbc.demo.model.Response;
import com.jdbc.demo.model.UserModel;
import com.jdbc.demo.service.UserService;

@Component
public class UserDao implements UserService {

	Response res = new Response();
	
	String url = "jdbc:mysql://127.0.0.1:3306/kgm";
	String uname = "root";
	String pwd = "Janaki12";
	
	
	public Response createPost(UserModel values) {
		
		String uuid = UUID.randomUUID().toString();
		values.setsNo(uuid);
		values.setCreatedBy(uuid);
		values.setUpdatedBy(uuid);
		
		Date date = new Date(Calendar.getInstance().getTime().getTime());
		values.setCreatedDate(date);
		values.setUpdatedDate(date);
		
		values.setActive(true);
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			try(Connection conn = DriverManager.getConnection(url, uname, pwd);
					Statement st = conn.createStatement();) {
				String newpostQuery = "INSERT INTO kgm.user_post (post,s_no,created_by,updated_by,created_date,updated_date,is_active) VALUES('"+ values.getPost() +"','"+ values.getsNo() +"','"+ values.getCreatedBy() +"','"+ values.getUpdatedBy() +"','"+ values.getCreatedDate() +"','"+ values.getUpdatedDate() +"','"+ values.isActive() +"' );";
				
				st.executeUpdate(newpostQuery);
				
				res.setData("Post created Successfully");
				res.setResponseCode(200);
				res.setResponseMessage("Success");
				
				
				
			} catch (Exception e) {
				e.printStackTrace();
				
				res.setData("Unable to create post");
				res.setResponseCode(500);
				res.setResponseMessage("Error");
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
			res.setData("Driver Error");
			res.setResponseCode(500);
			res.setResponseMessage("Error");
			
		}
		
		return res;
	}
	
	public Response modifyPost(UserModel values) {
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			try(Connection conn = DriverManager.getConnection(url, uname, pwd);
					Statement st = conn.createStatement();){
					
					String updatePost = "UPDATE kgm.user_post SET post = '"+ values.getPost() +"' WHERE s_no = '"+ values.getsNo() +"';";
					
					st.executeUpdate(updatePost);
					
					res.setData("Post Edited Successfully");
					res.setResponseCode(200);
					res.setResponseMessage("Success");
					
				
			} catch (Exception e) {
				e.printStackTrace();
				res.setData("Unable to Edit Post");
				res.setResponseCode(500);
				res.setResponseMessage("Error");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			res.setData("Driver Error");
			res.setResponseCode(500);
			res.setResponseMessage("Error");
		}
		
		
		return res;
	}

}
