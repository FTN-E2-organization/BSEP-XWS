package app.java.agentapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import app.java.agentapp.dto.CustomerDTO;
import app.java.agentapp.exception.BadRequest;
import app.java.agentapp.model.Customer;
import app.java.agentapp.repository.AuthorityRepository;
import app.java.agentapp.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService{

	private CustomerRepository customerRepository;
	private AuthorityRepository authorityRepository;
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public CustomerServiceImpl(CustomerRepository customerRepository, AuthorityRepository authorityRepository,PasswordEncoder passwordEncoder) {
		this.customerRepository = customerRepository;
		this.authorityRepository = authorityRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public void createCustomer(CustomerDTO customerDTO) throws Exception {
		
		if(customerRepository.existsByUsername(customerDTO.username)) {
			throw new BadRequest("Username is busy.");
		}
		Customer customer = new Customer();
		
		customer.setUsername(customerDTO.username);
		customer.setEmail(customerDTO.email);
		customer.setPassword(passwordEncoder.encode(customerDTO.password));
		customer.setAuthority(authorityRepository.findByName("ROLE_CUSTOMER"));
		
		customerRepository.save(customer);	
	}

}
