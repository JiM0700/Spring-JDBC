package com.jdbc.demo.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;
import com.jdbc.demo.model.Response;
import com.jdbc.demo.model.SignUpModel;
import com.jdbc.demo.service.SignUpService;

@Component
public class SignUpDao implements SignUpService {

	Response res = new Response();
	
	String url = "jdbc:mysql://127.0.0.1:3306/kgm";
	String uname = "root";
	String pwd = "Janaki12";
	
	public Response createUser(SignUpModel values) {

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

			try (Connection conn = DriverManager.getConnection(url, uname, pwd);
					Statement st = conn.createStatement();) {
					
					if(String.valueOf(values.getMobileNumber()).length()==10 && values.getMobileNumber()>=6000000000l && values.getMobileNumber()<=9999999999l) {
						
					
					String emailValidation = "^(.+)@gmail.com$";
					Pattern emailPattern = Pattern.compile(emailValidation);
					Matcher emailMatcher = emailPattern.matcher(values.getEmailId());
					
					
					if(emailMatcher.matches()==true) {
						
						
						String insertQuery = "INSERT INTO kgm.user_details(s_no,first_name,last_name,gender,dob,mobile_number,email_id,password,created_by,updated_by,created_date,updated_date,is_active)"
								+ "VALUES('" + values.getsNo() + "','" + values.getFirstName() + "','" + values.getLastName()
								+ "','" + values.getGender() + "','" + values.getDob() + "'," + values.getMobileNumber() + ",'"
								+ values.getEmailId() + "','" + values.getPassword() + "','" + values.getCreatedBy() + "','"
								+ values.getUpdatedBy() + "','" + values.getCreatedDate() + "','" + values.getUpdatedDate()
								+ "','"+ values.isActive() +"');";

						System.out.println(insertQuery);

						st.executeUpdate(insertQuery);

						res.setData("User Created Successfully");
						res.setResponseCode(200);
						res.setResponseMessage("Hurray!");
					}
					else {
						
						res.setData("Invalid Email");
						res.setResponseCode(500);
						res.setResponseMessage("Incorrect Email");
						
						
					}
					
				}
				else {
					
					res.setData("Invalid MobileNumber");
					res.setResponseCode(500);
					res.setResponseMessage("Incorrect Mobile Number");
					
				}
				

			} catch (Exception e) {
				e.printStackTrace();

				res.setData("Cannot Create User");
				res.setResponseCode(500);
				res.setResponseMessage("Oops! That's wrong");

			}

		} catch (Exception e) {
			e.printStackTrace();

			res.setData("Driver Class Error !");
			res.setResponseCode(500);
			res.setResponseMessage("Uh Oh!");
		}

		return res;
	}
	
	@SuppressWarnings("unchecked")
	public Response getUser() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			String selectQuery = "select * from user_details";
			
			
			try (Connection conn = DriverManager.getConnection(url, uname, pwd);
					Statement st = conn.createStatement();
					ResultSet rs = st.executeQuery(selectQuery);) {
				
				JSONArray jsonArray = new JSONArray();
				
				while (rs.next()) {
					JSONObject jsonObject = new JSONObject();
					
					jsonObject.put("sNo",rs.getString("s_no"));
					jsonObject.put("firstName",rs.getString("first_name"));
					jsonObject.put("lastName",rs.getString("last_name"));
					jsonObject.put("emailId",rs.getString("email_id"));
					jsonObject.put("dob",rs.getDate("dob"));
					jsonObject.put("gender",rs.getString("gender"));
					jsonObject.put("mobileNumber",rs.getLong("mobile_number"));
					jsonObject.put("password",rs.getString("password"));
					jsonObject.put("createdBy",rs.getString("created_by"));
					jsonObject.put("createdDate",rs.getDate("updated_date"));
					jsonObject.put("isActive", rs.getString("is_active"));
					
					jsonArray.add(jsonObject);
					
				}
				res.setData("Success");
				res.setjData(jsonArray);
				res.setResponseCode(200);
				res.setResponseMessage("User Fetched Successfully");
				
				
			} catch (Exception e) {
				e.printStackTrace();
				res.setData("Error");
				res.setResponseCode(500);
				res.setResponseMessage("User Fetch UnSuccessful");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			res.setData("Error");
			res.setResponseCode(500);
			res.setResponseMessage("Driver Error");
		}
		return res;	
	}

	public Response updateEmail(String emailId,String sNo) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			
			try(Connection conn = DriverManager.getConnection(url, uname, pwd);
					Statement st = conn.createStatement();) {
				
				String emailValidation = "^(.+)@gmail.com$";
				Pattern emailPattern = Pattern.compile(emailValidation);
				Matcher emailMatcher = emailPattern.matcher(emailId);
				
				
				if(emailMatcher.matches()==true) {
					
					
					String UpdateEmailQuery = " UPDATE kgm.user_details SET email_id = '"+ emailId +"' WHERE s_no = '"+ sNo +"' ; ";
				
					
					st.executeUpdate(UpdateEmailQuery);
					
					res.setData("Success");
					res.setResponseCode(200);
					res.setResponseMessage("Your Email has been updated");
					
				}else {
					
					res.setData("Invalid Email");
					res.setResponseCode(500);
					res.setResponseMessage("Incorrect Email");
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
				res.setData("Error");
				res.setResponseCode(500);
				res.setResponseMessage("Your Email hasn't been updated");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			res.setData("Error");
			res.setResponseCode(500);
			res.setResponseMessage("Driver Error");
		}
		
		
		return res;
	}

	@SuppressWarnings("unchecked")
	public Response getOneUser(SignUpModel values) {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			String selectOneQuery = "SELECT * FROM user_details WHERE s_no = '"+ values.getsNo() +"' ;";
			
			
			try(Connection conn = DriverManager.getConnection(url, uname, pwd);
					Statement st = conn.createStatement();
					ResultSet rs = st.executeQuery(selectOneQuery);) {
				
				JSONObject jsonObject = new JSONObject();
				
				while(rs.next()) {
					jsonObject.put("sNo", rs.getString("s_no"));
					jsonObject.put("firstName", rs.getString("first_name"));
					jsonObject.put("lastName",rs.getString("last_name"));
					jsonObject.put("emailId",rs.getString("email_id"));
					jsonObject.put("dob",rs.getDate("dob"));
					jsonObject.put("gender",rs.getString("gender"));
					jsonObject.put("mobileNumber",rs.getLong("mobile_number"));
					jsonObject.put("password",rs.getString("password"));
					jsonObject.put("createdBy",rs.getString("created_by"));
					jsonObject.put("createdDate",rs.getDate("updated_date"));
					jsonObject.put("isActive", rs.getString("is_active"));
					
				}
				
				res.setData("Success");
				res.setjData(jsonObject);
				res.setResponseCode(200);
				res.setResponseMessage("Requested User detail has been fetched");
				
				
			} catch (Exception e) {
				e.printStackTrace();
				res.setData("Error");
				res.setResponseCode(500);
				res.setResponseMessage("Requested User detail hasn't been fetched");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			res.setData("Error");
			res.setResponseCode(500);
			res.setResponseMessage("Driver Error");

		}
		
		return res;
	}
	
	public Response deleteUser(String sNo) {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			String deleteUser = "DELETE FROM kgm.user_details WHERE s_no='"+ sNo +"';";
			
			try (Connection conn = DriverManager.getConnection(url, uname, pwd);
					Statement st = conn.createStatement();){
								
				st.executeUpdate(deleteUser);
				
				res.setData("Success");
				res.setResponseCode(200);
				res.setResponseMessage("User Deleted Successfully");
				
				
					
			} catch (Exception e) {
				e.printStackTrace();
				
				res.setData("Error");
				res.setResponseCode(200);
				res.setResponseMessage("Unable to delete user");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
			res.setData("Error");
			res.setResponseCode(500);
			res.setResponseMessage("Driver Error");

		}
				
		return res;
	
	}
	
	public Response userLogin(String emailId,String password) {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			String loginStatement = "SELECT * from kgm.user_details WHERE email_id= '"+ emailId +"' AND password= '"+ password +"' ;";
			try(Connection conn = DriverManager.getConnection(url, uname, pwd);
				PreparedStatement prepstmt = conn.prepareStatement(loginStatement);
					ResultSet rs = prepstmt.executeQuery();){
				
				String result;
				
				if(rs.next()) {
					result = "Existing User";
					res.setData(result);
				}else {
					result = "User Not Found";
					res.setData(result);
				}
				
				res.setData(result);
				res.setResponseCode(200);
				res.setResponseMessage("Authentication Successfull");
				
				
			} catch (Exception e) {
				e.printStackTrace();
				res.setData("Error");
				res.setResponseCode(500);
				res.setResponseMessage("Authentication Unsuccessfull");
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			res.setData("Error");
			res.setResponseCode(500);
			res.setResponseMessage("Driver Error");
		}
		
		return res;
	}

	public Response userInactive(String sNo, boolean isActive) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			isActive = false;
			
			String softDelete = "UPDATE kgm.user_details SET is_active = '"+ isActive +"' WHERE s_no = '"+ sNo +"' ; ";
			
			try(Connection conn = DriverManager.getConnection(url, uname, pwd);
					Statement st = conn.createStatement()){
					
				st.executeUpdate(softDelete);
				
				res.setData("Success");
				res.setResponseCode(200);
				res.setResponseMessage("User Set to Inactive");
				
			} catch (Exception e) {
				e.printStackTrace();
				
				res.setData("Error");
				res.setResponseCode(500);
				res.setResponseMessage("Something Went Wrong!");
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			
			res.setData("Error");
			res.setResponseCode(500);
			res.setResponseMessage("Driver Error");
		}
		
		
		return res;
	}
	
}
