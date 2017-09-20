package com.revature.application.services;

import java.io.File;
import java.io.InputStream;

public interface FileService {

	public boolean importExcel(File file, int officeId);
	public boolean sysoutExcelInfo(File file);
	public boolean importCSV();
	
}
