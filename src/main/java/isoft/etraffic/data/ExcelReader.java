package isoft.etraffic.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

// All static attributes removed for thread running
	
	//Modified 
	public FileInputStream getFileInputStream(String ExcelfileName) {
		FileInputStream fis = null;
		String filePath = System.getProperty("user.dir") + "/Exceldata/" + ExcelfileName + ".xlsx";
		File srcFile = new File(filePath);

		try {
			fis = new FileInputStream(srcFile);
		} catch (FileNotFoundException e) {
			System.out.println("Test Data file not found. treminating Process !! : Check file path of TestData");
			System.exit(0);
		}
		return fis;
	}

	// Modified to start reading from Row#1 & Stop reading on finding the empty row
	public Object[][] getExcelData(String ExcelfileName, String sheetname, int TotalNumberOfCols) throws IOException {

		FileInputStream fis = getFileInputStream(ExcelfileName);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		// XSSFSheet sheet = wb.getSheetAt(0);
		XSSFSheet sheet = wb.getSheet(sheetname);
		int TotalNumberOfRows = (sheet.getLastRowNum() + 1);

		String[][] arrayExcelData = new String[TotalNumberOfRows][TotalNumberOfCols];
		String[][] arrayExcelDataActual = null;

		boolean stop = false;
		for (int i = 0; i < TotalNumberOfRows; i++) {
			for (int j = 0; j < TotalNumberOfCols; j++) {
				XSSFRow row = sheet.getRow(i + 1); // to ignore the header row
				try {
					if (!row.getCell(j).toString().equals("")) {
						arrayExcelData[i][j] = row.getCell(j).toString();
						//System.out.println("arrayExcelData[i][j]: " + arrayExcelData[i][j]);
					} else {
						// If There is an empty row inserted, set 'stop' to true then break the column loop
						stop = true;
						break;
					}
				} catch (NullPointerException e) {
					// Exception is thrown in case there is no empty row inserted
					stop = true;
				}
			}
			if (stop) {
				// Initialize arrayExcelDataActual with actual no of rows to be tested
				arrayExcelDataActual = new String[i][TotalNumberOfCols];
				break;
			}
		}

		wb.close();
		// Fill arrayExcelDataActual
		System.out.println("arrayExcelDataActual length of sheet ("+ sheetname + ") : " + arrayExcelDataActual.length);
		for (int i = 0; i < arrayExcelDataActual.length; i++) {
			for (int j = 0; j < TotalNumberOfCols; j++) {
				arrayExcelDataActual[i][j] = arrayExcelData[i][j];
			}
			System.out.println("------------");
		}
		return arrayExcelDataActual;
	}
}