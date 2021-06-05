package app.mediaservice.controller;

import java.util.List;
import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import app.mediaservice.dto.MediaDTO;
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
	public ModelAndView uploadFile(@FormParam("file") MultipartFile file, @QueryParam(value = "idContent") Long idContent,
			@QueryParam(value = "type") ContentType type) {
		String message = "";
		try {
			mediaService.save(file,idContent,type);

			message = "Uploaded the file successfully: " + file.getOriginalFilename();
			//return ResponseEntity.status(HttpStatus.OK).body(new String(message));
		    return new ModelAndView("redirect:" + "http://localhost:8111/html/profile.html");
		} catch (Exception e) {
			message = "Could not upload the file: " + file.getOriginalFilename() + "!";
			//return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new String(message));
			return new ModelAndView("redirect:" + "http://localhost:8111/html/publishPost.html");
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