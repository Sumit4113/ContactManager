package com.example.controller;

import java.security.Principal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.entites.Contact;
import com.example.entites.User;
import com.example.repository.ContactRepository;
import com.example.repository.UserRepository;

@RestController
@RequestMapping("/user")
public class SearchController {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ContactRepository contactrepo;

	@GetMapping("/search/{query}")
	public  ResponseEntity<?> search(@PathVariable("query") String query, Principal principal){
		
		System.out.println(query);
		
		User user = userRepo.findByEmail(principal.getName());
		
	List<Contact> contact =	this.contactrepo.findByUserNameContainingAndUser(query, user);
	
	 return ResponseEntity.ok(contact);
	}

}
