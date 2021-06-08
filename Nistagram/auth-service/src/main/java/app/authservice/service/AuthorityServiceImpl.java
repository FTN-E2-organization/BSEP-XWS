package app.authservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.authservice.model.Authority;
import app.authservice.repository.AuthorityRepository;

@Service
public class AuthorityServiceImpl implements AuthorityService {
	
	private AuthorityRepository authorityRepository;
	
	@Autowired
	public AuthorityServiceImpl(AuthorityRepository authorityRepository) {
		this.authorityRepository = authorityRepository;
	}


	@Override
	public Authority findByName(String name) {
		return authorityRepository.findByName(name);
	}

}
