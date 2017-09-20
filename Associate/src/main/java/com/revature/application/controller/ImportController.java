package com.revature.application.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.revature.application.services.FileService;

@RequestMapping("import")
@RestController
public class ImportController {

	@Autowired
	FileService fs;

	@PostMapping("excel")
	public ResponseEntity<String> importXlsx(@RequestParam("file") MultipartFile file,HttpServletRequest req) {		
		boolean fileAvailable = false;
		int office = 1; //for now but need managers officeId
	
			if (!file.isEmpty()) {
				try {
					File xlsx = new File(file.getOriginalFilename());
					xlsx.createNewFile();
					FileOutputStream fos = new FileOutputStream(xlsx); 
					fos.write(file.getBytes());
					fos.close(); 
					System.out.println(fs.importExcel(xlsx, office));

					return ResponseEntity.ok("Imported successfully.");
				} catch (IOException e) {
					return ResponseEntity.ok("Failed to upload file for Import.");
				}
			} else {
				return ResponseEntity.ok("No file to Import.");
			}

	}
	@PostMapping("excelViewAll")
	public ResponseEntity<String> importAndViewXlsx(@RequestParam("file") MultipartFile file,HttpServletRequest req) {		

		if (!file.isEmpty()) {
			try {
				File xlsx = new File(file.getOriginalFilename());
				xlsx.createNewFile();
				FileOutputStream fos = new FileOutputStream(xlsx); 
				fos.write(file.getBytes());
				fos.close(); 
				System.out.println(fs.sysoutExcelInfo(xlsx));

				return ResponseEntity.ok("Imported successfully.");
			} catch (IOException e) {
				return ResponseEntity.ok("Failed to upload file for Import.");
			}
		} else {
			return ResponseEntity.ok("No file to Import.");
		}

	}

}
