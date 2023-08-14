package com.jdbc.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jdbc.demo.dao.SignUpDao;
import com.jdbc.demo.model.Response;
import com.jdbc.demo.model.SignUpModel;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class SignUpController {
	
	@Autowired
	SignUpDao dao;
	
	@PostMapping("/create")
	public ResponseEntity<Response> createUser(@RequestBody SignUpModel values){
		return ResponseEntity.ok(dao.createUser(values));
	}
	
	@GetMapping("/select")
	public ResponseEntity<Response> getUser(){
		return ResponseEntity.ok(dao.getUser());
	}
	
	@PostMapping("/update")
	public ResponseEntity<Response> updateEmail(@RequestParam String emailId, String sNo){
		return ResponseEntity.ok(dao.updateEmail(emailId,sNo));
	}
	
	@GetMapping("/selectone")
	public ResponseEntity<Response> getOneUser(@RequestBody SignUpModel values){
		return ResponseEntity.ok(dao.getOneUser(values));
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<Response> deleteUser(@RequestParam String sNo){
		return ResponseEntity.ok(dao.deleteUser(sNo));
	}
	
	@PostMapping("/login")
	public ResponseEntity<Response> userLogin(@RequestParam String emailId, String password){
		return ResponseEntity.ok(dao.userLogin(emailId,password));
	}
	
	@PostMapping("/inactive")
	public ResponseEntity<Response> userInactive(@RequestParam String sNo, boolean isActive){
		return ResponseEntity.ok(dao.userInactive(sNo,isActive));
	}
	
	
}