package app.activityservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.activityservice.repository.ClickRepository;

@Service
public class ClickServiceImpl implements ClickService {

	private ClickRepository clickRepository;
	
	@Autowired
	public ClickServiceImpl(ClickRepository clickRepository) {
		this.clickRepository = clickRepository;
	}
	
}
