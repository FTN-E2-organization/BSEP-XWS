package app.java.agentapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.java.agentapp.model.Authority;
import app.java.agentapp.repository.AuthorityRepository;

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
