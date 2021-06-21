package app.authservice.controller;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.mail.MailException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import app.authservice.dto.*;
import app.authservice.exception.BadRequest;
import app.authservice.exception.ValidationException;
import app.authservice.model.CustomPrincipal;
import app.authservice.service.*;
import app.authservice.validator.ProfileValidator;

@RestController
@RequestMapping(value = "api/auth/profile")
public class ProfileController {

	private ProfileService profileService;
	
	@Autowired
	public ProfileController(ProfileService profileService) {
		this.profileService = profileService;
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> createRegularUser(@RequestBody ProfileDTO profileDTO) {
		try {
			ProfileValidator.createProfileValidation(profileDTO);
			profileService.createRegularUser(profileDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch (ValidationException ve) {
			return new ResponseEntity<String>(ve.getMessage(), HttpStatus.BAD_REQUEST);
		}catch (BadRequest be) {
			return new ResponseEntity<String>(be.getMessage(), HttpStatus.BAD_REQUEST);
		}catch (MailException me) {
			return new ResponseEntity<String>("An error occurred while sending an email.", HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while registering.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('updateProfile')")
	@PutMapping("/personal")
	public ResponseEntity<?> updatePersonalData(@RequestBody ProfileDTO profileDTO) {
		try {
			ProfileValidator.updatePersonalDataValidation(profileDTO);
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        CustomPrincipal principal = (CustomPrincipal) auth.getPrincipal();
			String oldUsername = principal.getUsername();
			
			profileService.updatePersonalData(oldUsername, profileDTO);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch (ValidationException ve) {
			return new ResponseEntity<String>(ve.getMessage(), HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while updating personal data.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('updateProfile')")
	@PutMapping("/privacy")
	public ResponseEntity<?> updateProfilePrivacy(@RequestBody ProfileDTO profileDTO) {
		try {			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        CustomPrincipal principal = (CustomPrincipal) auth.getPrincipal();
			profileDTO.username = principal.getUsername();
			
			profileService.updateProfilePrivacy(profileDTO);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while updating profile privacy.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<?> findProfileByUsername(@PathVariable String username){
		try {
			ProfileDTO profileDTO = profileService.getProfileByUsername(username);
			return new ResponseEntity<ProfileDTO>(profileDTO, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/search/{typeOfSearch}")
	public ResponseEntity<?> getProfiles(@PathVariable String typeOfSearch){
		try {
			Collection<ProfileDTO> profileDTOs = new ArrayList<>();
			if (typeOfSearch.equals("public")) {
				profileDTOs = profileService.getPublicProfiles();
			}
			else if (typeOfSearch.equals("public-and-private")) {
				profileDTOs = profileService.getPublicAndPrivateProfiles();
			}
			else {
				return new ResponseEntity<String>("Path variable is invalid.", HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<Collection<ProfileDTO>>(profileDTOs, HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}	

	@PreAuthorize("hasAuthority('findAllowTaggingProfile')")
	@GetMapping("/allowedTagging")
	public ResponseEntity<?> findAllowedTaggingProfiles(){
		try {
			return new ResponseEntity<>(profileService.findAllowTaggingProfileUsernames(), HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}	
	
	@PostMapping("/password-recovery")
	public boolean recoverPassword(@RequestBody String username) throws MailException, InterruptedException
	{
		try {
			ProfileValidator.checkEmailFormat(username);
		} catch (Exception e) {
			return false;
		}
		return profileService.recoverPassword(username);
	}

	@PostMapping("/password-change")
	public ResponseEntity<?> changePassword(@RequestBody PasswordRequestDTO dto)
	{
		try {		
			return new ResponseEntity<>(profileService.changePassword(dto), HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while changing password.", HttpStatus.BAD_REQUEST);
		}	
	
	}

	/* kad klikne na link iz mejla, aktivira nalog */
	@RequestMapping(value = "/confirm-account", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView confirmUserAccount(@RequestParam("token")String confirmationToken) throws Exception {
		try {
			profileService.confirmProfile(confirmationToken);
			return new ModelAndView("redirect:" + "https://localhost:8111/html/login.html");
		}
		catch (Exception e) {
			return new ModelAndView("redirect:" + "https://localhost:8111/html/login.html");
		}			
	}	
	
	@PostMapping("/new-activation-link")
	public ResponseEntity<?> sendNewActivationLink(@RequestBody String username) {	
		try {		
			profileService.sendNewActivationLink(username);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while sending request for new activation link.", HttpStatus.BAD_REQUEST);
		}		
	}	
	
	@PreAuthorize("hasAuthority('updateProfile')")
	@PostMapping(value = "/edit/new-password", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> setPassword(@RequestBody PasswordDTO dto)
	{		
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        CustomPrincipal principal = (CustomPrincipal) auth.getPrincipal();
			dto.username = principal.getUsername();
			ProfileValidator.checkNullOrEmpty(dto.newPassword, "Password is null or empty!");
			ProfileValidator.checkPasswordFormat(dto.newPassword);
			profileService.setPassword(dto);
			return new ResponseEntity<>(HttpStatus.OK);
		} 
		catch (ValidationException ve) {
			return new ResponseEntity<String>(ve.getMessage(), HttpStatus.BAD_REQUEST);
		}catch (BadRequest be) {
			return new ResponseEntity<String>(be.getMessage(), HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while setting new password.", HttpStatus.BAD_REQUEST);
		}		
	}	
	
	
	@PreAuthorize("hasAuthority('createVerificationRequest')")
	@PostMapping(value = "/verification/request",consumes = "application/json")
	public ResponseEntity<?> createVerificationRequest(@RequestBody VerificationRequestDTO requestDTO) {
		try {
			ProfileValidator.checkNullOrEmpty(requestDTO.name, "Name is null or empty!");
			ProfileValidator.checkNullOrEmpty(requestDTO.surname, "Surame is null or empty!");
			
			return new ResponseEntity<>(profileService.createVerificationRequest(requestDTO),HttpStatus.CREATED);
		}catch (BadRequest be) {
			return new ResponseEntity<String>(be.getMessage(), HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while creating request.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/categories")
	public ResponseEntity<?> getCategories(){
		
		try {
			return new ResponseEntity<>(profileService.getCategories(), HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}	
	@PreAuthorize("hasAuthority('blockProfile')")
	@PutMapping("/block/{username}")
	public ResponseEntity<?> blockProfile(@PathVariable String username){
		try {
			profileService.blockProfile(username);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>("An error occurred while blocking profile.", HttpStatus.BAD_REQUEST);
		}
	}
	@PreAuthorize("hasAuthority('judgeVerificationRequest')")
	@GetMapping("/unverified")
	public ResponseEntity<?> getUnverifiedProfiles(){
		
		try {
			return new ResponseEntity<>(profileService.getUnverifiedProfiles(), HttpStatus.OK);
		}
		catch(Exception exception) {
			exception.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasAuthority('judgeVerificationRequest')")
	@PostMapping(value = "/verification/request/judge",consumes = "application/json")
	public ResponseEntity<?> judgeVerificationRequest(@RequestBody VerificationRequestDTO requestDTO) {
		try {
			profileService.judgeVerificationRequest(requestDTO);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch (Exception be) {
			be.printStackTrace();
			return new ResponseEntity<String>(be.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
