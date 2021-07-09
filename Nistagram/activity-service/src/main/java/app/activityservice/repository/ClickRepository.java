package app.activityservice.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.activityservice.enums.OwnerType;
import app.activityservice.model.Click;

public interface ClickRepository extends JpaRepository<Click, Long>{

	Collection<Click> findAllByCampaignId(long campaignId);

	@Query("select count(*) from Click c where c.owner.username = ?1 and c.ownerType = ?2")
	int getNumberOfClicksByUsernameAndOwnerType(String username, OwnerType ownerType);

}
