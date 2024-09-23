package com.example.Upload_File_To_DB.service;

import com.example.Upload_File_To_DB.model.FileDB;
import com.example.Upload_File_To_DB.repository.FileDBRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@Service
public class FileStorageServiceImpl implements FileStorageService {
	private final FileDBRepository fileDBRepository;
	
	@Autowired
	public FileStorageServiceImpl(FileDBRepository fileDBRepository) {
		this.fileDBRepository = fileDBRepository;
	}
	
	
	/*  Mục đích: Lưu file được tải lên vào cơ sở dữ liệu. */
	@Override
	@Transactional
	public FileDB store(MultipartFile file) throws IOException {
		/* Lấy tên file và làm sạch tên file, loại bỏ các kí tự không
		*  hợp lệ có thể gây ra vấn đề bảo mật */
		String fileName = file.getOriginalFilename();
		
		/* Tạo 1 đối tượng FileDB với tên file, loại MIME(contentType)
		*  và nội dung file dưới dạng byte (file.getByte()) */
		FileDB fileDB = new FileDB(fileName, file.getContentType(), file.getBytes());
		
		return fileDBRepository.save(fileDB);
	}
	
	/* Mục đích: Lấy một file từ cơ sở dữ liệu dựa trên ID. */
	@Override
	public FileDB getFile(String id) {
		return fileDBRepository.findById(id).get();
	}
	
	@Override
	public Stream<FileDB> getAllFiles() {
		/* fileDBRepository.findAll(): Lấy tất cả các bản ghi FileDB từ cơ sở dữ liệu. */
		/* .stream(): Chuyển đổi danh sách các file thành 1 Stream, dễ dàng xử lí, lọc,
		 *  hoặc biến đổi dữ liệu */
		return fileDBRepository.findAll().stream();
	}
	
	@Override
	public void deleteFileById(String id) {
		fileDBRepository.deleteById(id);
	}
}
