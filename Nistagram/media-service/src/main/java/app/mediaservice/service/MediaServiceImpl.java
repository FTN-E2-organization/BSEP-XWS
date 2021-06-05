package app.mediaservice.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import app.mediaservice.dto.MediaDTO;
import app.mediaservice.enums.ContentType;
import app.mediaservice.model.Media;
import app.mediaservice.repository.MediaRepository;

@Service
public class MediaServiceImpl implements MediaService {

	private MediaRepository mediaRepository;

	@Autowired
	public MediaServiceImpl(MediaRepository mediaRepository) {
		this.mediaRepository = mediaRepository;
	}
	@Override
	public List<MediaDTO> getMediaByIdContentAndType(Long idContent, ContentType type) {
		List<Media> media =  mediaRepository.getMediaByIdContentAndContentType(idContent, type);
		List<MediaDTO> mediaDTOs = new ArrayList<>();
		for(Media m : media) {
			mediaDTOs.add(new MediaDTO(m.getIdContent(), m.getContentType(), m.getPath()));
		}
		return mediaDTOs;
	}

	private final Path root = Paths.get("uploads");

	@Override
	public void init() {
		try {
			Files.createDirectory(root);
		} catch (IOException e) {
			// throw new RuntimeException("Could not initialize folder for upload!");
		}
	}

	@Override
	public void save(MultipartFile file, Long idContent, ContentType type) {
		try {
			UUID uuid = UUID.randomUUID();
			Path path = this.root.resolve(uuid.toString() + "_" + file.getOriginalFilename());
			Files.copy(file.getInputStream(), path);
			Media media = new Media();
			media.setContentType(type);
			media.setIdContent(idContent);
			media.setPath(path.getFileName().toString());
			mediaRepository.save(media);
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}
	}
	
	@Override
	public Resource load(String filename) {
		try {
			Path file = root.resolve(filename);
			Resource resource = new UrlResource(file.toUri());

			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("Could not read the file!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("Error: " + e.getMessage());
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(root.toFile());

	}

	@Override
	public void deleteOneByIdContentAndType(Long idContent, ContentType type) {
		List<Media> medias = mediaRepository.getMediaByIdContentAndContentType(idContent,type);
		for (Media media : medias) {
			File myObj = new File(root.resolve(media.getPath()).toString());
			if (myObj.delete()) {
				System.out.println("Deleted the file: " + myObj.getName());
			} else {
				System.out.println("Failed to delete the file.");
			}

			mediaRepository.delete(media);
		}

	}

}
