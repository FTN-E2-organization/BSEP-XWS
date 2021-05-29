package app.publishingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.publishingservice.model.FavouritePost;

public interface FavouritePostRepository extends JpaRepository<FavouritePost, Long> {

	
}
