package fr.eni.projet.bll.filestorage;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
	
	String saveImageToStorage(String uploadDirectory, MultipartFile imageFile) throws IOException;

}
