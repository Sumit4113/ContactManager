package com.example.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cloudinary.Cloudinary;
import com.example.entites.Contact;
import com.example.entites.User;
import com.example.helper.Message;
import com.example.repository.ContactRepository;
import com.example.repository.UserRepository;
import com.example.services.CloudinaryService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ContactRepository contactRepo;

	@Autowired
	private BCryptPasswordEncoder passwordencoder;

	
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

	@GetMapping("/adminSetting")
	public String adminSetting(Principal principal, Model model) {
		
		User admin = userRepo.findByUserName(principal.getName());
        
		model.addAttribute("admin", admin);
		
		return "admin/adminSetting";
	}

	@PostMapping("/changepass")
	public String changePassword(@RequestParam("oldpass") String oldPass, @RequestParam("newpass") String newPass,
			RedirectAttributes redirectAttributes, Principal principal) {
		User user = userRepo.findByUserName(principal.getName());

		if (passwordencoder.matches(oldPass, user.getPassword())) {
			user.setPassword(passwordencoder.encode(newPass));
			userRepo.save(user);
			redirectAttributes.addFlashAttribute("message", new Message("Password changed successfully", "success"));
		} else {
			redirectAttributes.addFlashAttribute("message", new Message("Incorrect old password", "danger"));
			return "admin/adminSetting";
		}

		return "redirect:/admin/adminSetting";
	}

	@PostMapping("/adminUpdate")
	public String updateUserProfile(@RequestParam("name") String name, @RequestParam("email") String email,
			Principal principal, RedirectAttributes redirectAttributes) {
		try {
			User user = userRepo.findByUserName(principal.getName());

			user.setName(name);
			user.setEmail(email);

			userRepo.save(user);
			redirectAttributes.addFlashAttribute("message", new Message("Profile updated successfully", "success"));
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("message", new Message("Failed to update profile", "danger"));
		}

		return "redirect:/admin/adminSetting";
	}

}
