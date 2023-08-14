package com.jdbc.demo.service;

import org.springframework.stereotype.Service;

import com.jdbc.demo.model.Response;
import com.jdbc.demo.model.UserModel;

@Service
public interface UserService {

	public Response createPost(UserModel values);
	
	public Response modifyPost(UserModel values);
	
}
