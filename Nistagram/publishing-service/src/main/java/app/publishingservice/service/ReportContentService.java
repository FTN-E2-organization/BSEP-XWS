package app.publishingservice.service;

import java.util.Collection;
import app.publishingservice.dto.ReportContentRequestDTO;
import app.publishingservice.enums.ContentType;

public interface ReportContentService {

	void create(ReportContentRequestDTO contentRequestDTO);
	Collection<ReportContentRequestDTO> getAll();
	void removeContent(Long contentId, ContentType type);
}
