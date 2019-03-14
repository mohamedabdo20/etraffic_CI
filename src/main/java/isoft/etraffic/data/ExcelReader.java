package isoft.etraffic.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

	static FileInputStream fis = null;
	public static String ExcelfileName = "testdata";
	public static String sheetname = null;
	public static int TotalNumberOfCols;

	public FileInputStream getFileInputStream() {
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

	public Object[][] getExcelData() throws IOException {

		fis = getFileInputStream();
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		// XSSFSheet sheet = wb.getSheetAt(0);
		XSSFSheet sheet = wb.getSheet(sheetname);
		int TotalNumberOfRows = (sheet.getLastRowNum());

		String[][] arrayExcelData = new String[TotalNumberOfRows][TotalNumberOfCols];
		String[][] arrayExcelDataAcrual = null;

		boolean stop = false;
		for (int i = 0; i < TotalNumberOfRows; i++) {
			for (int j = 0; j < TotalNumberOfCols; j++) {
				XSSFRow row = sheet.getRow(i + 1);
				if (!row.getCell(j).toString().equals("")) {
					arrayExcelData[i][j] = row.getCell(j).toString();

				} else {
					stop = true;
					break;

				}
			}
			if (stop) {
				arrayExcelDataAcrual = new String[i][TotalNumberOfCols];
				for (int x = 0; x < i; x++) {
					for (int y = 0; y < TotalNumberOfCols; y++) {
						arrayExcelDataAcrual[x][y] = arrayExcelData[x][y];
					}
				}
				break;
			}
		}

		wb.close();
		return arrayExcelDataAcrual;

	}
}
