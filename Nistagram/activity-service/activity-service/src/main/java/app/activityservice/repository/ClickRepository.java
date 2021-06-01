package app.activityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.activityservice.model.Click;

public interface ClickRepository extends JpaRepository<Click, Long>{

}
