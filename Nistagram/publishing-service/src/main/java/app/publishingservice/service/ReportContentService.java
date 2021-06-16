package app.publishingservice.service;

import java.util.Collection;
import app.publishingservice.dto.ReportContentRequestDTO;

public interface ReportContentService {

	void create(ReportContentRequestDTO contentRequestDTO);
	Collection<ReportContentRequestDTO> getAll();
}
