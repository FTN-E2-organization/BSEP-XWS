package app.mediaservice.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import app.mediaservice.enums.ContentType;
import app.mediaservice.model.Media;
import app.mediaservice.service.MediaService;

@RestController
@RequestMapping(value = "api/media")
public class MediaController {

	private MediaService mediaService;

	@Autowired
	public MediaController(MediaService mediaService) {
		this.mediaService = mediaService;
	}

	@GetMapping("/one/{idContent}")
	public ResponseEntity<?> findMediaByIdContent(@PathVariable Long idContent) {

		try {
			Media media = mediaService.getMediaByIdContent(idContent);
			return new ResponseEntity<Media>(media, HttpStatus.OK);
		} catch (Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/upload")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam Long idContent,
			@RequestParam ContentType type) {
		String message = "";
		try {
			mediaService.save(file,idContent,type);

			message = "Uploaded the file successfully: " + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.OK).body(new String(message));
		} catch (Exception e) {
			message = "Could not upload the file: " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new String(message));
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

	@DeleteMapping("/delete/{idContent}")
	public ResponseEntity<?> deleteMediaByIdContent(@PathVariable Long idContent) {

		try {
			mediaService.deleteOneByIdContent(idContent);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}