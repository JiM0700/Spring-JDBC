package com.jdbc.demo.service;

import org.springframework.stereotype.Service;

import com.jdbc.demo.model.Response;
import com.jdbc.demo.model.SignUpModel;

@Service
public interface SignUpService {

	public Response createUser(SignUpModel values);
	
	public Response getUser();
	
	public Response updateEmail(String emailId,String sNo);
	
	public Response getOneUser(SignUpModel values);
	
	public Response deleteUser(String sNo);
	
	public Response userLogin(String emailId,String password);
	
	public Response userInactive(String sNo, boolean isActive);
}
