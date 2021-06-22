package app.campaignservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.campaignservice.model.AdCategory;

public interface AdCategoryRepository extends JpaRepository<AdCategory, Long> {

}
