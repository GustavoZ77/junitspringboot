package com.fourq.demoapi.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fourq.demoapi.domain.exception.MyFileException;
import com.fourq.demoapi.domain.model.ValidFormats;

@Service
public class StorageService {
	
	    private final String MY_PATH = "/home/gustavo/Documents/uploads/";
	
	    public String store(MultipartFile file, String idClient) throws MyFileException {
	        String filename = StringUtils.cleanPath(file.getOriginalFilename());
	        Optional<ValidFormats> ext = ValidFormats.isValid(filename);
	        if(ext.isPresent()) {
		        try {
		        	if(!file.isEmpty()) {
		        		try (InputStream inputStream = file.getInputStream()) {
			                Files.copy(inputStream, verifyAndCreatDirectory().resolve(idClient+"."+ext.get().toString()),
			                        StandardCopyOption.REPLACE_EXISTING);
			            }
		        	}else {
		        		throw new MyFileException("File is empty");
		        	}
		            
		        }
		        catch (IOException e) {
	        		throw new RuntimeException("Somthing wrong, transaction failed "+idClient);
		        }
	        }else {
        		throw new MyFileException("Invalid format");
	        }
	        return idClient;
	    }
	    
	    public Path verifyAndCreatDirectory() {
	    	try {
				return Files.createDirectories(Paths.get(MY_PATH));
			} catch (IOException e1) {
				e1.printStackTrace();
        		throw new RuntimeException("Error in I/O system");
			}
	    }
	    

}
