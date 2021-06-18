package app.mediaservice.controller;

import java.util.List;
import javax.ws.rs.FormParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import app.mediaservice.dto.MediaDTO;
import app.mediaservice.dto.UploadInfoDTO;
import app.mediaservice.enums.ContentType;
import app.mediaservice.service.MediaService;

@RestController
@RequestMapping(value = "api/media")
public class MediaController {

	private MediaService mediaService;

	@Autowired
	public MediaController(MediaService mediaService) {
		this.mediaService = mediaService;
	}

	@GetMapping("/one/{idContent}/{type}")
	public ResponseEntity<?> findMediaByIdContent(@PathVariable Long idContent,@PathVariable ContentType type) {

		try {
			List<MediaDTO> mediaDTOs = mediaService.getMediaByIdContentAndType(idContent,type);
			return new ResponseEntity<List<MediaDTO>>(mediaDTOs, HttpStatus.OK);
		} catch (Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/upload")
	public ResponseEntity<String> uploadFile(@FormParam("file") MultipartFile file, @FormParam("uploadInfo") String uploadInfo) {		
		try {
			UploadInfoDTO uploadInfoDTO = new ObjectMapper().readValue(uploadInfo, UploadInfoDTO.class);
			mediaService.save(file,uploadInfoDTO.contentId, uploadInfoDTO.type);

			return ResponseEntity.status(HttpStatus.OK).body(new String("Uploaded the files successfully."));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new String( "Could not upload the files."));
		}
	}
	
	
	//da se dobije slika/video na osnovu path-a
	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> getFile(@PathVariable String filename) {
		Resource file = mediaService.load(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@PreAuthorize("hasAuthority('deleteFile')")
	@DeleteMapping("/delete/{idContent}/{type}")
	public ResponseEntity<?> deleteMediaByIdContent(@PathVariable Long idContent,@PathVariable ContentType type) {

		try {
			mediaService.deleteOneByIdContentAndType(idContent,type);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}