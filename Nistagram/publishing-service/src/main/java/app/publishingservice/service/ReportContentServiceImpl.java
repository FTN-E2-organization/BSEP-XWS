package app.publishingservice.service;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.publishingservice.dto.ReportContentRequestDTO;
import app.publishingservice.enums.ContentType;
import app.publishingservice.mapper.ReportContentMapper;
import app.publishingservice.model.Profile;
import app.publishingservice.model.ReportContentRequest;
import app.publishingservice.repository.ProfileRepository;
import app.publishingservice.repository.ReportContentRepository;


@Service
public class ReportContentServiceImpl implements ReportContentService {
	
	private ReportContentRepository reportContentRepository;
	private ProfileRepository profileRepository;
	
	@Autowired
	public ReportContentServiceImpl(ReportContentRepository reportContentRepository,ProfileRepository profileRepository) {
		this.reportContentRepository = reportContentRepository;
		this.profileRepository = profileRepository;
	}

	@Override
	public void create(ReportContentRequestDTO contentRequestDTO) {
		ReportContentRequest reportContentRequest = new ReportContentRequest();
		Profile profile = profileRepository.findByUsername(contentRequestDTO.initiatorUsername);
		
		reportContentRequest.setReason(contentRequestDTO.reason);
		reportContentRequest.setContentId(contentRequestDTO.contentId);
		reportContentRequest.setContentType(contentRequestDTO.type);
		reportContentRequest.setInitiator(profile);
		reportContentRequest.setApproved(false);
		reportContentRequest.setOwnerUsername(contentRequestDTO.ownerUsername);
		
		reportContentRepository.save(reportContentRequest);
	}

	@Override
	public Collection<ReportContentRequestDTO> getAll() {
		Collection<ReportContentRequest> reportContentRequests = reportContentRepository.findAllDisapproved();
		Collection<ReportContentRequest> result = new ArrayList<>();
		
		for(ReportContentRequest request:reportContentRequests) {
			if(!profileRepository.findByUsername(request.getOwnerUsername()).isBlocked())
				result.add(request);
		}
		
		return ReportContentMapper.toContentRequestDTOs(result);
	}

	@Override
	public void removeContent(Long contentId, ContentType type) {
		Collection<ReportContentRequest> reportContentRequests = reportContentRepository.findAllByContentIdAndType(contentId, type.toString());
		
		for(ReportContentRequest request:reportContentRequests) {
			request.setApproved(true);
		}
		
		reportContentRepository.saveAll(reportContentRequests);
	}

}
