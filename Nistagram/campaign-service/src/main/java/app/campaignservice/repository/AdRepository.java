package app.campaignservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.campaignservice.model.Ad;

public interface AdRepository extends JpaRepository<Ad, Long> {

}
