package app.java.agentapp.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import app.java.agentapp.dto.XmlDTO;


@FeignClient(name = "report", url = "http://localhost:8092/api/report/")
public interface ReportClient {
	
	@PostMapping("/xml")
	public String addMonitoring(@RequestBody XmlDTO dto) throws Exception;

}
