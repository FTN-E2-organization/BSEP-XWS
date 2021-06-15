package app.authservice.service;

import app.authservice.model.Authority;

public interface AuthorityService {

	Authority findByName(String name);
}
