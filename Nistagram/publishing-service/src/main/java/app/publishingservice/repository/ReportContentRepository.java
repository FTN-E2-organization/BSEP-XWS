package app.publishingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import app.publishingservice.model.ReportContentRequest;

public interface ReportContentRepository extends JpaRepository<ReportContentRequest, Long> {

}
