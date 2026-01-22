package com.example.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entites.Contact;
import com.example.entites.User;
import com.example.repository.ContactRepository;
import com.example.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class ContactService {

	@Autowired
	private ContactRepository contactRepo;

	@Autowired
	private UserRepository userRepo;

	@Cacheable(value = "conatcts", key = "#user.id")
	public List<Contact> getContact(Contact contact, User user) {
		System.out.println("Fetch contact with the help of user :");
		return contactRepo.findByUser(user);

	}

	@CacheEvict(value = "contacts", key = "#user.id")
	public Contact saveContact(Contact contact, User user) {
		System.out.println("Saving new Contact and clear cache");
		contact.setUser(user);
		user.getContact().add(contact);
		// userRepo.save(user)
		return contactRepo.save(contact);

	}

	@CacheEvict(value="contacts", allEntries=true)
	@RequestMapping("/delete/{cid}")
	@Transactional
	public void deleteContact(Contact contact, User user) {

		user.getContact().remove(contact);
		userRepo.save(user);
	}

	// ✅ Fetch contacts per user per page with caching
	@Cacheable(value = "contacts", key = "#userId + '-' + #pageable.pageNumber")
	public Page<Contact> getContactsByUserPaginated(int userId, Pageable pageable) {
		System.out.println("Fetching from DB for user " + userId + " page " + pageable.getPageNumber());
		return contactRepo.findContactByUser(userId, pageable);
	}

	@CacheEvict(value="contacts", allEntries=true)
	public void clearUserCache(int userId) {
	}


}
