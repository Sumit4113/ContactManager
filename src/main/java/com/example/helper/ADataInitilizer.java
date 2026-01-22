package com.example.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.entites.User;
import com.example.repository.UserRepository;

@Component
public class ADataInitilizer implements CommandLineRunner {
    
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	@Override
	public void run(String... args) throws Exception {
		
		if(!userRepo.existsByEmail("admin01@gmail.com")) {
			
			User admin = new User();
			
			admin.setName("Admin");
			admin.setEmail("admin01@gmail.com");
			admin.setPassword(passwordEncoder.encode("admin1234"));
			admin.setRole("ROLE_ADMIN");
            admin.setEnabled(true);
            
            userRepo.save(admin);
            
            System.out.println("Defalut admin id :");
			
		}
	
		
	}

}
