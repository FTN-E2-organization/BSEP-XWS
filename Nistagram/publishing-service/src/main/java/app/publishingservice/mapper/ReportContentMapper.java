package app.publishingservice.mapper;

import java.util.ArrayList;
import java.util.Collection;
import app.publishingservice.dto.ReportContentRequestDTO;
import app.publishingservice.model.ReportContentRequest;

public class ReportContentMapper {

	public static Collection<ReportContentRequestDTO> toContentRequestDTOs(Collection<ReportContentRequest> reportContentRequests){
		Collection<ReportContentRequestDTO> contentRequestDTOs = new ArrayList<>();
		
		for(ReportContentRequest request:reportContentRequests) {			
			ReportContentRequestDTO requestDTO = new ReportContentRequestDTO();

			requestDTO.contentId = request.getContentId();
			requestDTO.type = request.getContentType();
			requestDTO.reason = request.getReason();
			requestDTO.initiatorUsername = request.getProfile().getUsername();
			contentRequestDTOs.add(requestDTO);
		}
		
		return contentRequestDTOs;
	}
}
