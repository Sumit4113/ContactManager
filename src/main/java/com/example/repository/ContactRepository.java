package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entites.Contact;
import com.example.entites.User;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
	// This is user for pegination..
	@Query("from Contact as c where c.user.id =:id")
	//current page-page
	//contact per-page
	public Page<Contact> findContactByUser(@Param("id") int userId,Pageable pagePageable);

	// search contact

	
	
	List<Contact> findByUser(User user);

	public List<Contact> findByUsernameContainingOrEmailContainingOrPhoneContainingAndUser(String query, String query2,
			String query3, User user);


}
