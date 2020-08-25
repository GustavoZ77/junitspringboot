package com.fourq.demoapi.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fourq.demoapi.domain.exception.MyFileException;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {StorageService.class})
public class StorageServiceTest {

	private static final String MY_TEST_PATH = "/home/gustavo/Documents/uploads/";
	private static final String TEST_FILE_NAME = "pom.xml";
	private static final String BAD_TEST_FILE_NAME = "pom.jpeg";

	@Autowired
	public StorageService storageService;

	private static MockMultipartFile file;
	private static MockMultipartFile badfile;

	@BeforeAll
	public static void prepareMuktiPartFiles() {
		try {
			file = new MockMultipartFile(TEST_FILE_NAME, TEST_FILE_NAME, "application/xml",
					new FileInputStream(MY_TEST_PATH + TEST_FILE_NAME));

			badfile = new MockMultipartFile(BAD_TEST_FILE_NAME, BAD_TEST_FILE_NAME, "application/jpeg",
					new FileInputStream(MY_TEST_PATH + BAD_TEST_FILE_NAME));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	@Test
	@DisplayName("Should create a file on a file system")
	public void TestStoreFileSuccess() {
		try {
			storageService.store(file, "123");
		} catch (MyFileException e) {
			e.printStackTrace();
		}
		assertFalse(Files.exists(Paths.get(MY_TEST_PATH).resolve(TEST_FILE_NAME)));

	}

	@Test
	@DisplayName("Should throws empty file exception")
	public void TestStoreFileThrowsExceptionWhenEmpty() {
		Assertions.assertThrows(MyFileException.class,()-> storageService.store(badfile,"123"));

	}

	@Test
	@DisplayName("Should throws an invalid format exception")
	public void TestStoreFileThrowsExceptionWhenIsInvalidFormat() {
		MyFileException a = Assertions.assertThrows(MyFileException.class,()->storageService.store(badfile, "123"));
		assertTrue(a.getMessage().equals("Invalid format"));
	}

}
