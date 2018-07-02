package ib.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import ib.project.model.User;
import ib.project.repository.UserRepository;

@Service
public class UserService implements UserServiceInterface{
	
	@Autowired
    private UserRepository userRepository;

	@Override
	public User findById(Integer id) throws AccessDeniedException {
		return userRepository.findOne(id);
	}

	@Override
	public User findByEmail(String email) throws UsernameNotFoundException{
		return userRepository.findByEmail(email);
	}

	@Override
	public List<User> findAll() throws AccessDeniedException{
		return userRepository.findAll();
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	
}
