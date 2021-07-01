package app.java.agentapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.java.agentapp.dto.CustomerDTO;
import app.java.agentapp.exception.BadRequest;
import app.java.agentapp.exception.ValidationException;
import app.java.agentapp.service.CustomerService;
import app.java.agentapp.validator.CustomerValidator;

@RestController
@RequestMapping(value = "api/customer")
public class CustomerController {

	private CustomerService customerService;
	
	@Autowired
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> createCustomer(@RequestBody CustomerDTO customerDTO) {
		try {
			CustomerValidator.createCustomerValidation(customerDTO);
			customerService.createCustomer(customerDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch (ValidationException ve) {
			return new ResponseEntity<String>(ve.getMessage(), HttpStatus.BAD_REQUEST);
		}catch (BadRequest be) {
			return new ResponseEntity<String>(be.getMessage(), HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while registering agent.", HttpStatus.BAD_REQUEST);
		}
	}
}
