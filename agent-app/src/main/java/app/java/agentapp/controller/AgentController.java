package app.java.agentapp.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.DocumentException;

import app.java.agentapp.client.AcivityClient;
import app.java.agentapp.client.CampaignClient;
import app.java.agentapp.client.CategoryClient;
import app.java.agentapp.client.MediaClient;
import app.java.agentapp.client.ReportClient;
import app.java.agentapp.dto.AdDTO;
import app.java.agentapp.dto.AddCampaignMultipleDTO;
import app.java.agentapp.dto.AddCampaignOnceTimeDTO;
import app.java.agentapp.dto.AgentDTO;
import app.java.agentapp.dto.CampaignDTO;
import app.java.agentapp.dto.ContentType;
import app.java.agentapp.dto.MonitoringDTO;
import app.java.agentapp.dto.NumberOfReactionsDTO;
import app.java.agentapp.dto.UploadInfoDTO;
import app.java.agentapp.dto.XmlDTO;
import app.java.agentapp.exception.BadRequest;
import app.java.agentapp.exception.ValidationException;
import app.java.agentapp.report.ReportPDFExporter;
import app.java.agentapp.service.AgentService;
import app.java.agentapp.validator.AgentValidator;

@RestController
@RequestMapping(value = "api/agent")
public class AgentController {
	
	private AgentService agentService;
	private CampaignClient campaignClient;
	private CategoryClient categoryClient;
	private MediaClient mediaClient;
	private ReportClient reportClient;
	private AcivityClient activityClient;
	
	@Autowired
	public AgentController(AgentService agentService, CampaignClient campaignClient, CategoryClient categoryClient, MediaClient mediaClient, ReportClient reportClient, AcivityClient activityClient) {
		this.agentService = agentService;
		this.campaignClient = campaignClient;
		this.categoryClient = categoryClient;
		this.mediaClient = mediaClient;
		this.reportClient = reportClient;
		this.activityClient = activityClient;
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> createAgent(@RequestBody AgentDTO agentDTO) {
		try {
			AgentValidator.createAgentValidation(agentDTO);
			agentService.createAgent(agentDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch (ValidationException ve) {
			return new ResponseEntity<String>(ve.getMessage(), HttpStatus.BAD_REQUEST);
		}catch (BadRequest be) {
			return new ResponseEntity<String>(be.getMessage(), HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while registering agent.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/check-token/{token}")
	public ResponseEntity<?> checkApiTokenFromNistagram(@PathVariable String token) {

		try {
			
			boolean res = categoryClient.checkToken(token);
			return new ResponseEntity<Boolean>(res, HttpStatus.OK);
		} catch (Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/has-token/{username}/{token}")
	public ResponseEntity<?> checkApiTokenFromNistagram(@PathVariable String username, @PathVariable boolean token) {

		try {
			agentService.setHasApiToken(username, token);
			
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	//@PreAuthorize("hasAuthority('campaignManagement')")
	@GetMapping("/all-campaign/{username}")
	public ResponseEntity<?> getAllByUsername(@PathVariable String username){
		try {
			return new ResponseEntity<Collection<CampaignDTO>>(this.campaignClient.getAllByUsername(username), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while getting campaigns. - " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}	
	
	@GetMapping("/api-token/{username}")
	public ResponseEntity<?> getApiTokenByUsername(@PathVariable String username){
		try {
			return new ResponseEntity<Boolean>(agentService.hasToken(username), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while getting API token. - " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}	
	
	@PostMapping(value = "/once-time", consumes = "application/json")
	public ResponseEntity<?> createOnceTimeCampaign(@RequestBody AddCampaignOnceTimeDTO campaignDTO) {
		try {
			this.campaignClient.createOnceTimeCampaign(campaignDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while creating campaign. - " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}	
	
	@PostMapping(value = "/multiple", consumes = "application/json")
	public ResponseEntity<?> createMultipleCampaign(@RequestBody AddCampaignMultipleDTO campaignDTO) {
		try {
			this.campaignClient.createMultipleCampaign(campaignDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while creating campaign. - " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/future/{username}")
	public ResponseEntity<?> getFutureCampaignsByUsername(@PathVariable String username){
		try {
			return new ResponseEntity<Collection<CampaignDTO>>(this.campaignClient.getFutureCampaignsByUsername(username), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while getting future campaigns. - " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}	
	
	@PostMapping(value="/ad", consumes = "application/json")
	public ResponseEntity<?> create(@RequestBody AdDTO dto) {
		try {
			long adId = this.campaignClient.createAd(dto);
			return new ResponseEntity<>(adId, HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while creating ad. - " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}	
	
	@GetMapping("/categories")
	public ResponseEntity<?> getCategories(){
		
		try {
			return new ResponseEntity<>(this.categoryClient.getAllCategories(), HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}	
	
	@PostMapping("/files-upload/ad")
	public ModelAndView uploadFileAd(@FormParam("file") MultipartFile[] file, @QueryParam(value = "idContent") Long idContent, @QueryParam(value = "type") ContentType type) {
		try {
			String uploadInfoJson = new ObjectMapper().writeValueAsString(new UploadInfoDTO(idContent, type));
			
			for(MultipartFile f:file) 
				this.mediaClient.fileUpload(f, uploadInfoJson);
				
			return new ModelAndView("redirect:" + "https://localhost:8091/html/createAd.html");
		}catch (Exception e) {		
			return new ModelAndView("redirect:" + "https://localhost:8091/html/createAd.html");
		}
	}
	
	@GetMapping("/report/pdf/{username}")
	public void exportToPdf(HttpServletResponse response, @PathVariable String username) throws DocumentException, IOException, Exception {
		response.setContentType("aplication/pdf");
		
		XmlDTO dto = new XmlDTO(username);
		String s = this.reportClient.addMonitoring(dto);
		
		Collection<MonitoringDTO> monitoringDTOs = new ArrayList<>();
		Collection<CampaignDTO> campaignDTOs = this.campaignClient.getAll();
		for (CampaignDTO c : campaignDTOs) {	
			if (c.agentUsername.equals(username)) {
				MonitoringDTO monitoringDTO = new MonitoringDTO();
				monitoringDTO.idCampaign = c.id;
				monitoringDTO.contentType = c.contentType;
				monitoringDTO.campaignType = c.campaignType;
				monitoringDTO.categoryName = c.categoryName;
				monitoringDTO.name = c.name;
				for (AdDTO a : c.ads) {
					NumberOfReactionsDTO numberOfReactionsDTO = this.activityClient.getNumberOfReactionsByAdId(a.id);
					monitoringDTO.numberLikes += numberOfReactionsDTO.numberOfLikes;
					monitoringDTO.numberDislikes += numberOfReactionsDTO.numberOfDislikes;
					monitoringDTO.numberComments += numberOfReactionsDTO.numberOfComments;
				}
				monitoringDTO.numberClick = this.activityClient.getAllClicksByCampaignId(c.id).size();				
				monitoringDTO.numberOfClicksDTOs = this.activityClient.getNumberOfClicksByCampaignId(c.id);
				monitoringDTOs.add(monitoringDTO);
			}
		}
		
		DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormater.format(new Date());
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=report_" + currentDateTime + ".pdf";
		
		response.setHeader(headerKey, headerValue);
		
		ReportPDFExporter exporter = new ReportPDFExporter(monitoringDTOs);
		exporter.export(response);
		
	}
	
	
	@GetMapping("/token/{username}")
	public ResponseEntity<?> getApiToken(@PathVariable String username) {

		try {
			String token = agentService.getTokenAgent(username);
			
			return new ResponseEntity<String>(token, HttpStatus.OK);
		} catch (Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/token/{username}/{token}")
	public ResponseEntity<?> setTokenAgent(@PathVariable String username, @PathVariable String token) {

		try {
			agentService.setTokenAgent(username, token);
			
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping(value = "/delete/{campaignId}")
	public ResponseEntity<?> delete(@PathVariable long campaignId) {
		try {
			this.campaignClient.delete(campaignId);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while deleting campaign. - " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}	
	
	@PostMapping(value = "/multiple/update", consumes = "application/json")
	public ResponseEntity<?> updateMultipleCampaign(@RequestBody CampaignDTO campaignDTO) {
		try {
			this.campaignClient.updateMultipleCampaign(campaignDTO);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<String>("An error occurred while updating campaign. - " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
}
