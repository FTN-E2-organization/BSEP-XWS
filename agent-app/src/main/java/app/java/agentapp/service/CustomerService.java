package app.java.agentapp.service;

import app.java.agentapp.dto.CustomerDTO;

public interface CustomerService {

	void createCustomer(CustomerDTO customerDTO) throws Exception;
}
