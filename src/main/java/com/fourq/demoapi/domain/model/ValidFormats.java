package com.fourq.demoapi.domain.model;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public enum ValidFormats {
	
	 PDF("pdf","PDF"),
	 XML("xml","XML"), 
	 TXT("txt","TXT"); 
	
	 private final String ext1;
	 private final String ext2;
	 private String ext;
	 
	public String toString() {
		return ext;
	} 

	ValidFormats(String ext1, String ext2) {
		this.ext1 = ext1;
		this.ext2 = ext2;
	}
	
	public static Optional<ValidFormats> isValid(String fileName) {
		String ext = fileName.split("\\.")[1];
		return Stream.of(ValidFormats.values()).
				filter(d -> d.ext1.equals(ext) ||  d.ext2.equals(ext) ? true : false)
				.map(d -> {
						d.ext=ext;
						return d;
					}).findFirst();
	}

}
