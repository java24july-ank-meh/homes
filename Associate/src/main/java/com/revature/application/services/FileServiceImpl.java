package com.revature.application.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.application.model.Associate;
import com.revature.application.repository.AssociateRepository;

@Service
public class FileServiceImpl implements FileService {
	@Autowired AssociateRepository associateRepository;

	@Override
	public boolean importExcel(File file, int officeId) {

		long managerOfficeID = officeId;

		FileInputStream excelFile;
		Workbook wb;
		Map<String, Integer> colNames = null;
		Iterator<Row> iteratorR;

		//just for seeing problems/seeing what is placed
		List<Associate> associates = new ArrayList<Associate>();

		try {

			excelFile = new FileInputStream(file);

			wb = new XSSFWorkbook(excelFile);

			Sheet dataSheet = wb.getSheetAt(0);

			iteratorR = dataSheet.iterator();
			Row curRow = iteratorR.next();

			int cellNum = curRow.getLastCellNum();

			colNames = new HashMap<String, Integer>();
			for(int i=0;i<(cellNum);i++) {
				colNames.put(curRow.getCell(i).toString(), i);
				//sets column and name
			}

			System.out.println("Map: "+colNames.toString()+"\n\n");

			while(iteratorR.hasNext()) {

				curRow = iteratorR.next();

				boolean agreed = false;

				Associate tempA = new Associate();

				int haCol = colNames.get("Housing Agreement");
				Cell haCell = curRow.getCell(haCol);

				int row = 0;
				if(haCell != null && (haCell.toString().equals("signed")||haCell.toString().equals("(checkmarked)"))) {
					agreed = true;
					tempA.setHousingAgreed(LocalDateTime.now());
					row = haCell.getRowIndex();
					tempA.setRole("associate");
				}

				if(agreed) {

					System.out.println("row: "+row);
					tempA.setOfficeId(managerOfficeID);


					int fnCol = colNames.get("First Name");
					int lnCol = colNames.get("Last Name");
					int pCol = colNames.get("Mobile");//ask palmer format
					int eCol = colNames.get("Email");
					//					int unitCol = colNames.get("Housing Unit");//ask palmer format
					int carCol = colNames.get("Has Car");//ask palmer values
					int mInCol = colNames.get("Move-In Date");//ask palmer date format

					tempA.setFirstName(curRow.getCell(fnCol).toString());

					tempA.setLastName(curRow.getCell(lnCol).toString());
					String dPhone = curRow.getCell(pCol).toString();
					System.out.println(dPhone);
					tempA.setPhone(dPhone);

					tempA.setEmail(curRow.getCell(eCol).toString());

					//					double hUnit = curRow.getCell(unitCol).getNumericCellValue();
					//					int unit = (int) hUnit;
					//					tempA.setUnitId((long) unit);

					int hasCar = 0;
					if(curRow.getCell(carCol).toString().equalsIgnoreCase("yes")) {//ask palmer
						hasCar = 1;
					}

					tempA.setHasCar(hasCar);

					/*
					 * below is stuff for date but have to wait
					 */
					//								Date curDate = curCell.getDateCellValue();
					//								LocalDateTime curLDT = LocalDateTime.ofInstant(curDate.toInstant(), ZoneId.systemDefault());

					//^ not good at all, cant get numeric value from string field...need to see an exmaple of date

					//another option
					//					Double dDate = curRow.getCell(mInCol).getNumericCellValue();
					//					System.out.println("date as string: "+dDate);

					String sDate = curRow.getCell(mInCol).toString();
					//					System.out.println("date as string: "+sDate);

					String delim = "-";
					StringTokenizer st = new StringTokenizer(sDate,delim);

					String day = st.nextToken(delim);
					int nDay = Integer.parseInt(day);

					String month = st.nextToken(delim);
					int nMonth = monthStringToInt(month);
					String year = st.nextToken(delim);
					int nYear = Integer.parseInt(year);

					LocalDate ld = LocalDate.of(nYear, nMonth, nDay);
					LocalDateTime ldt = LocalDateTime.of(ld, LocalTime.NOON);

					tempA.setMoveInDate(ldt);
					
					//associates.add(tempA);
					if(associateRepository.findByEmail(tempA.getEmail()) == null)
						associateRepository.saveAndFlush(tempA);
				}
			}

			wb.close();
			System.out.println("\nI am done reading the excel.\nHere are the values.\n");

			for(Associate a: associates) {
				System.out.println(a);
			}

			//add associates to db with service

			return true;

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public int monthStringToInt(String month) {
		int nMonth = 0;

		switch (month) {
		case "Jan" :
			nMonth = 1;
			break;
		case "Feb" :
			nMonth = 2;
			break;
		case "Mar" :
			nMonth = 3;
			break;
		case "Apr" :
			nMonth = 4;
			break;
		case "May" :
			nMonth = 5;
			break;
		case "Jun" :
			nMonth = 6;
			break;
		case "Jul" :
			nMonth = 7;
			break;
		case "Aug" :
			nMonth = 8;
			break;
		case "Sep" :
			nMonth = 9;
			break;
		case "Oct" :
			nMonth = 10;
			break;
		case "Nov" :
			nMonth = 11;
			break;
		case "Dec" :
			nMonth = 12;
			break;
		}

		return nMonth;
	}

	public boolean sysoutExcelInfo(File file) {

		FileInputStream excelFile;
		Workbook wb;
		Map<String, Integer> colNames = null;
		Iterator<Row> iteratorR;

		//just for seeing problems/seeing what is placed
		List<Associate> associates = new ArrayList<Associate>();

		try {

			excelFile = new FileInputStream(file);

			wb = new XSSFWorkbook(excelFile);

			Sheet dataSheet = wb.getSheetAt(0);

			iteratorR = dataSheet.iterator();
			Row curRow = iteratorR.next();

			while(iteratorR.hasNext()) {

				curRow = iteratorR.next();

				Iterator<Cell> iteratorC = curRow.iterator();

				while(iteratorC.hasNext()) {
					Cell curCell = iteratorC.next();

					CellAddress ad = curCell.getAddress();

					System.out.println(curCell.toString());
					System.out.println("address: "+ad);

					//					String str = curCell.getStringCellValue();
					//					double dob = curCell.getNumericCellValue();


					//					System.out.println("column: "+col);
					//					System.out.println("row: "+row);
					//					System.out.println("string: "+str);
					//					System.out.println("double: "+dob);

				}

			}

			wb.close();
			return true;

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;		
	}

	/*
	 * excel doesnt have
	 *  
	 * about
	 * gender
	 * slackId
	 * 
	 * ************************** *
	 * user doesnt have
	 * 
	 * move out data
	 * training batch
	 * training status
	 * seed status
	 * canidate marketing status
	 * canidate status
	 * marketing start date
	 * project start date
	 * 
	 */

	@Override
	public boolean importCSV() {
		// TODO Auto-generated method stub
		return false;
	}

}
