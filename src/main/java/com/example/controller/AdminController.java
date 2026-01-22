package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entites.Contact;
import com.example.entites.User;
import com.example.repository.ContactRepository;
import com.example.repository.UserRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ContactRepository contactRepo;

	@GetMapping("/adminDashboard")
	public String showUsers(Model model) {

		List<User> allUser = userRepo.findAll();

		model.addAttribute("users", allUser);

		return "admin/adminDashboard";

	}

	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable int id, Model model) {
		userRepo.deleteById(id);
		return "redirect:/admin/adminDashboard";

	}

	@GetMapping("/profile/{id}")
	public String userProfile(@PathVariable int id, Model model) {

		User user = userRepo.findById(id).get();
		List<Contact> contacts = contactRepo.findByUserId(id);

		model.addAttribute("user", user);
		model.addAttribute("contacts", contacts);

		return "admin/userProfile";
	}

}
