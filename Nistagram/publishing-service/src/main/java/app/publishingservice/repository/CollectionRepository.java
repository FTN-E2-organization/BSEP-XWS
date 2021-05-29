package app.publishingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.publishingservice.model.Collection;

public interface CollectionRepository extends JpaRepository<Collection, Long> {

}
