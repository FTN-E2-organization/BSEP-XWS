package app.campaignservice.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import app.campaignservice.model.InfluenceRequest;
import app.campaignservice.model.Profile;

public interface InfluenceRequestRepository extends JpaRepository<InfluenceRequest, Long> {
	Collection<InfluenceRequest> findByProfileUsername(String username);
	Collection<InfluenceRequest> findByProfileUsernameAndIsApproved(String username,Boolean isApproved);
}
