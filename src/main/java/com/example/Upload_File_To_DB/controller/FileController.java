package com.example.Upload_File_To_DB.controller;

import com.example.Upload_File_To_DB.message.ResponeFile;
import com.example.Upload_File_To_DB.message.ResponeMessage;
import com.example.Upload_File_To_DB.model.FileDB;
import com.example.Upload_File_To_DB.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@CrossOrigin("http://localhost:8081")
public class FileController {
	
	private final FileStorageService storageService;
	
	@Autowired
	public FileController(FileStorageService storageService) {
		this.storageService = storageService;
	}
	
	@GetMapping("/")
	public String homepage(){
		return "redirect:/files";
	}
	
	/* Trả về danh sách tất cả các file đã được lưu trữ, kèm theo thông tin file và URL để tải xuống. */
	@GetMapping("/files")
	public String getFiles(Model model) {
		List<ResponeFile> listFiles = storageService.getAllFiles().map(fileDB -> {
			String fileDownLoadUri = ServletUriComponentsBuilder
					.fromCurrentContextPath()
					.path("/files/")
					.path(fileDB.getId())
					.toUriString();
			/* OKE thì cái này nó tạo link download dựa trên path /files/{fileDB.getId()}
			*  là thằng path kia nó sẽ giống với endpoint của thằng getFile, cũng có
			*  endpoint là @GetMapping("/files/{filename:.+}") ->  tải theo id*/
			
			return new ResponeFile(
				fileDB.getId(),
				fileDB.getName(),
				fileDownLoadUri,
				fileDB.getType(),
				fileDB.getData().length
			);
		}).toList();
		
		model.addAttribute("files", listFiles);
		return "list-files";
	}
	
	/* Mục đích: Cho phép tải xuống một file cụ thể dựa trên ID. */
	@GetMapping("/files/{id}")
	public ResponseEntity<byte[]> getFile(@PathVariable String id) {
		FileDB fileDB =storageService.getFile(id);
		
		return ResponseEntity.status(HttpStatus.OK)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = \""
				+ URLEncoder.encode(fileDB.getName(), StandardCharsets.UTF_8) + "\"")
				.body(fileDB.getData());
	}
	
	@GetMapping("/files/delete/{id}")
	public String deleteFile(@PathVariable String id) {
		storageService.deleteFileById(id);
		return "redirect:/files";
	}
	
	@GetMapping("/files/new")
	public String newFile(Model model) {
		return "upload_form";
	}
	
	/* Xử lí việc upload file từ người dùng */
	@PostMapping("/files/upload")
	public String uploadFile(@RequestParam("file") MultipartFile file,
	                         Model model) {
		String message = "";
		
		try {
			storageService.store(file);
			
			message = "Upload file successfully: " + file.getOriginalFilename();
			model.addAttribute("message", message);
		} catch (IOException e) {
			message = "Upload file failed: " + file.getOriginalFilename();
		}
		return "upload_form";
	}
}
