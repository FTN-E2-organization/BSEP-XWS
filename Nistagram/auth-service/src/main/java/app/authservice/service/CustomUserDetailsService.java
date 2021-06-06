package app.authservice.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import app.authservice.model.Admin;
import app.authservice.model.Profile;
import app.authservice.repository.AdminRepository;
import app.authservice.repository.ProfileRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	protected final Log LOGGER = LogFactory.getLog(getClass());

	private ProfileRepository profileRepository;
	private AdminRepository adminRepository;

	@Autowired
	public CustomUserDetailsService(ProfileRepository profileRepository, AdminRepository adminRepository) {
		this.profileRepository = profileRepository;
		this.adminRepository = adminRepository;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Profile profile = profileRepository.findByUsername(username);
		
		if(profile == null) {
			Admin admin = adminRepository.findByUsername(username);
			if(admin == null)
				throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
			else
				return admin;
		}
		
		return profile;
	}

}
