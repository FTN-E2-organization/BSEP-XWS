package app.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import app.userservice.model.FollowRequest;

public interface FollowRequestRepository extends JpaRepository<FollowRequest, Long> {

}
