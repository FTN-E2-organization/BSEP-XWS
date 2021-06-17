package app.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.authservice.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	Category findOneByName(String category);

}