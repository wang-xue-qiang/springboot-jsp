package com.banner.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banner.bean.User;
import com.banner.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public void saveU(User user){
		userRepository.save(user);
	}
	
	
	public List<User> getAll(){
		return userRepository.findAll();
	}
}
