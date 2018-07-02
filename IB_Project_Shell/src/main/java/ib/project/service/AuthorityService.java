package ib.project.service;


import org.springframework.beans.factory.annotation.Autowired;

import ib.project.model.Authority;
import ib.project.repository.AuthorityRepository;

public class AuthorityService implements AuthorityServiceInterface{
	
	@Autowired
	AuthorityRepository authorityRepository;

	@Override
	public Authority findByName(String name) {
		return authorityRepository.findByName(name);
	}

	
}
