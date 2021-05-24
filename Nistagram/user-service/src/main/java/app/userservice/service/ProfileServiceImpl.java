package app.userservice.service;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.userservice.dto.*;
import app.userservice.model.*;
import app.userservice.repository.*;

@Service
public class ProfileServiceImpl implements ProfileService {

	private ProfileRepository profileRepository;
	private RoleRepository roleRepository;
	
	@Autowired
	public ProfileServiceImpl(ProfileRepository profileRepository, RoleRepository roleRepository) {
		this.profileRepository = profileRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	public void createRegularUser(ProfileDTO profileDTO) {
		Profile profile = new Profile();
		Set<Role> roles = new HashSet<Role>();
		roles.add(roleRepository.findByName("ROLE_REGULAR"));
		
		profile.setUsername(profileDTO.username);
		profile.setEmail(profileDTO.email);
		profile.setPassword(profileDTO.password);
		profile.setName(profileDTO.name);
		profile.setDateOfBirth(profileDTO.dateOfBirth);
		profile.setGender(profileDTO.gender);
		profile.setBiography(profileDTO.biography);
		profile.setPhone(profileDTO.phone);
		profile.setWebsite(profileDTO.website);
		profile.setPublic(profileDTO.isPublic);
		profile.setRoles(roles);
		
		profileRepository.save(profile);
	}

	@Override
	public void updateRegularUser(ProfileDTO profileDTO) {
		/*U kontroleru je potrebno izvuci id od trenutno ulogovanog korisnika*/
		Profile profile = profileRepository.findById((long) 2).get();
		
		profile.setUsername(profileDTO.username);
		profile.setEmail(profileDTO.email);
		profile.setName(profileDTO.name);
		profile.setDateOfBirth(profileDTO.dateOfBirth);
		profile.setGender(profileDTO.gender);
		profile.setBiography(profileDTO.biography);
		profile.setPhone(profileDTO.phone);
		profile.setWebsite(profileDTO.website);
		
		profileRepository.save(profile);
	}
}
