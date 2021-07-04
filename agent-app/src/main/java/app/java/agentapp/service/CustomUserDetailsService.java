package app.java.agentapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import app.java.agentapp.model.Agent;
import app.java.agentapp.model.Customer;
import app.java.agentapp.repository.AgentRepository;
import app.java.agentapp.repository.CustomerRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private AgentRepository agentRepository;
	private CustomerRepository customerRepository;

	@Autowired
	public CustomUserDetailsService(AgentRepository agentRepository, CustomerRepository customerRepository) {
		this.agentRepository = agentRepository;
		this.customerRepository = customerRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Agent agent = agentRepository.findByUsername(username);
		
		if(agent == null) {
			Customer customer = customerRepository.findByUsername(username);
			if(customer == null)
				throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
			else
				return customer;
		}
		
		return agent;
	}

}
