package app.campaignservice.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import app.campaignservice.model.Campaign;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {

	Collection<Campaign> findByAgentUsername(String username);

}
