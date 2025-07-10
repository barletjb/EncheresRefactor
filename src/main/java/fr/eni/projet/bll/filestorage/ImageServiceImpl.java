package fr.eni.projet.bll.filestorage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageServiceImpl implements ImageService {
	
	public ImageServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String saveImageToStorage(String uploadDirectory, MultipartFile imageFile) throws IOException {
		String uniqueFileName = UUID.randomUUID().toString() + ".jpg";

		Path uploadPath = Path.of(uploadDirectory);
		Path filePath = uploadPath.resolve(uniqueFileName);

		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}

		Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

		return uniqueFileName;
	}

}
