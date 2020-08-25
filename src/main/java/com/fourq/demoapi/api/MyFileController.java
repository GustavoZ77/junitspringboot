package com.fourq.demoapi.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fourq.demoapi.domain.exception.MyFileException;
import com.fourq.demoapi.service.StorageService;

@RestController
public class MyFileController {
	
	@Autowired
	private StorageService storageService;
	
	@PutMapping("/v1/upload/{idClient}")
	public ResponseEntity<String>  saveFile(@PathVariable("idClient") String idClient, MultipartFile file) {	
		try {
			storageService.store(file,idClient);
			return ResponseEntity.status(HttpStatus.OK).body(HttpStatus.OK.toString());
		} catch (MyFileException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("something fail due, <<"+e.getMessage()+">> please try again ");
		}
	}
}
