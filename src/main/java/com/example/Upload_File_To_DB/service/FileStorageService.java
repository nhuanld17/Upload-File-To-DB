package com.example.Upload_File_To_DB.service;

import com.example.Upload_File_To_DB.model.FileDB;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

public interface FileStorageService {
	
	public FileDB store(MultipartFile file) throws IOException;
	
	public FileDB getFile(String id);
	
	public Stream<FileDB> getAllFiles();
	
	public void deleteFileById(String id);
}
