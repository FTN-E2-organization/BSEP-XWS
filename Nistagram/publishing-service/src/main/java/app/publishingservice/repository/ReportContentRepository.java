package app.publishingservice.repository;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import app.publishingservice.model.ReportContentRequest;

public interface ReportContentRepository extends JpaRepository<ReportContentRequest, Long> {

	@Query(value = "select * from report_content_request r where r.is_approved = false order by r.content_id asc", nativeQuery = true)
	Collection<ReportContentRequest> findAllDisapproved();
	
	@Query(value = "select * from report_content_request r where r.content_id = ?1 and r.content_type = ?2", nativeQuery = true)
	Collection<ReportContentRequest> findAllByContentIdAndType(Long contentId, String type);
}
