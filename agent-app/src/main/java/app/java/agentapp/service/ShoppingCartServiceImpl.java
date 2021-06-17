package app.java.agentapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.java.agentapp.dto.AddShoppingCartDTO;
import app.java.agentapp.model.Customer;
import app.java.agentapp.model.ShoppingCart;
import app.java.agentapp.repository.CustomerRepository;
import app.java.agentapp.repository.ShoppingCartRepository;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService{

	private ShoppingCartRepository shoppingCartRepository;
	private CustomerRepository customerRepository;
	
	@Autowired
	public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, CustomerRepository customerRepository) {
		this.shoppingCartRepository = shoppingCartRepository;
		this.customerRepository = customerRepository;
	}

	@Override
	public void createShoppingCart(AddShoppingCartDTO shoppingCartDTO) {
		ShoppingCart shoppingCart = new ShoppingCart();
		shoppingCart.setCampaignId(shoppingCartDTO.campaignId);
		shoppingCart.setFinished(false);
		shoppingCart.setTotalPrice(0);
		Customer customer = customerRepository.findCustomerById(shoppingCartDTO.customerId);
		shoppingCart.setCustomer(customer);
		
		shoppingCartRepository.save(shoppingCart);
	}
	
}
