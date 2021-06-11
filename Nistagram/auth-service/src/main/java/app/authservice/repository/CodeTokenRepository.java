package app.authservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.authservice.model.CodeToken;
import app.authservice.model.User;

@Repository("codeTokenRepository")
public interface CodeTokenRepository extends CrudRepository<CodeToken, String>{

	CodeToken findByUser(User user);
}