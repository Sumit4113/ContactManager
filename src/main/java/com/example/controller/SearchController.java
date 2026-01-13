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

	// Corrected URL path
	@GetMapping("/search")
	public String searchContacts(
	        @RequestParam("query") String query,
	        Principal principal,
	        Model model) {

	    User user = userRepo.findByUserName(principal.getName());

	    List<Contact> contacts = contactrepo.findByUsernameContainingOrEmailContainingOrPhoneContainingAndUser(
	            query, query, query, user);

	    model.addAttribute("contacts", contacts);  // this is used in Thymeleaf
	    model.addAttribute("searchQuery", query);   // for input value
	    model.addAttribute("totalPages", 1);        // optional if you want pagination
	    model.addAttribute("currentPage", 0);

	    return "user/showcontact"; // same page
	}

}
