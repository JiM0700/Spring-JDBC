package com.jdbc.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jdbc.demo.dao.UserDao;
import com.jdbc.demo.model.Response;
import com.jdbc.demo.model.UserModel;

@CrossOrigin("*")
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserDao userDao;
	
	@PostMapping("/newpost")
	public ResponseEntity<Response> createPost(@RequestBody UserModel values){
		return ResponseEntity.ok(userDao.createPost(values));
	}
	
	@PutMapping("/editpost")
	public ResponseEntity<Response> modifyPost(@RequestBody UserModel values){
		return ResponseEntity.ok(userDao.modifyPost(values));
	}

}
