package app.campaignservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.campaignservice.model.InfluenceRequest;

public interface InfluenceRequestRepository extends JpaRepository<InfluenceRequest, Long> {

}
