package app.activityservice.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import app.activityservice.model.Click;

public interface ClickRepository extends JpaRepository<Click, Long>{

	Collection<Click> findAllByCampaignId(long campaignId);

}
