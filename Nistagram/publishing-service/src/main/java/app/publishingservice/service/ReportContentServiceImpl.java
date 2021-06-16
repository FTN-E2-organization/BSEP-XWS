package app.publishingservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.publishingservice.dto.ReportContentRequestDTO;
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
		reportContentRequest.addProfile(profile);
		reportContentRequest.setApproved(false);
		
		reportContentRepository.save(reportContentRequest);
	}

}
