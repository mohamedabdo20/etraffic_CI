package isoft.etraffic.cta.ftfpages;

import java.awt.AWTException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.SeleniumWraper;

public class ReviewContractRequestPage extends SeleniumWraper {
	
	String TrafficFile = "50182076";
 
	By MainMenu = By.id("menu-trigger");
	By ContractfromMenu = By.id("listCategoryLinksId2_13");
	By ReviewContractRequests = By.xpath("//*[contains(@data-name,'متابعة طلبات العقود')]");// j_idt167:j_idt170 review req
	By TrafficFileNo = By.id("select2-vho8-container");
	By TypeTrafficNo = By.xpath("//*[contains(@class,'select2-search__field')]");
	By SelectsearchResult = By.xpath("//*[contains(@class,\"select2-results__option select2-results__option--highlighted\")]");
	//By SelectsearchResult = By.id("resultsTable:3:c9");
	//By SelectsearchResult = By.xpath("//*[contains(@class,'af_table_data-table af_table_data-table-VH-lines')]");
	By SelectRequestStatus = By.id("contractStatus:contractStatusselectOneDomainPanelSelectOneMenu");// value 6 , 12 ,14
	By selectRequestType = By.id("updateType:updateTypeselectOneDomainPanelSelectOneMenu"); // value 1/2 // 14
	By SearchBtn = By.id("j_idt175:searchButtonId");
	//By SelectRow = By.id("resultsTable:3:c11");
	By SelectRow = By.xpath("//*[contains(@class,'af_table_data-row')]");
	By OpenView = By.id("actions1:viewButtonId");
	By AddNotes = By.id("octKpiForm:remarks:remarks");
	By FileUploaded = By.id("fileUploaded");
	By ViewAttach = By.xpath("//*[@class='form-group col-sm-3'][1]//*[@id='viewAttachment']");
	By ViewAttach2 = By.xpath("//*[@class='form-group col-sm-3'][2]//*[@id='viewAttachment']");
	By ViewAttach3 = By.xpath("//*[@class='form-group col-sm-3'][2]//*[@id='viewAttachment']");
	By ApprovalBtn = By.id("octKpiForm:auditApproval");
	By BackBtn = By.xpath("//*[contains(@id,'octKpiForm:j_idt')]");
	By BackFromContractBtn = By.xpath("//*[contains(@id,'octKpiForm:j_idt')]");
	By EID = By.id("octKpiForm:emirateId:emirateId");
	By Passport = By.id("octKpiForm:passportId:passportId");
	By ArName = By.id("octKpiForm:signatureNameAr:signatureNameAr");
	By EnName = By.id("octKpiForm:signatureNameEn:signatureNameEn");
	By NewContractNO = By.id("octKpiForm:signedContractNumber:signedContractNumber");
	
	

	public void requestToAddVehicleForPlanDep() throws InterruptedException, AWTException {
		clickElement(MainMenu);
		scrollToelement(ContractfromMenu);
		clickElement(ContractfromMenu);
		clickElement(ReviewContractRequests);
		writeToElement(TypeTrafficNo,TrafficFile);
		waitForElement(SelectsearchResult);
		clickElement(SelectsearchResult);
		selectFromListByValue(SelectRequestStatus, "6");
		selectFromListByValue(selectRequestType, "1");
		clickElement(SearchBtn);
		scrollToelement(SelectRow);
		clickElement(SelectRow);
		clickElement(OpenView);
		waitForElement(AddNotes);
		writeToElement(AddNotes, "ملاحظات");
		Thread.sleep(5000);
		clickElement(ViewAttach);
		Thread.sleep(5000);
		
		String firstwindow = switchToSecondWindow();
		driver.get("javascript:document.getElementById('overridelink').click();");
		Thread.sleep(5000);
		driver.close();
		Thread.sleep(5000);
		switchToWindow(firstwindow);
		Thread.sleep(5000);
		
		clickElement(FileUploaded);
		uploadFile("Lighthouse.jpg");
		Thread.sleep(5000);
		clickElement(ApprovalBtn);
		Thread.sleep(5000);
		clickElement(BackBtn);
		
	}
		public void requestToAddVehicleForContractDep() throws InterruptedException, AWTException {
			//clickElement(MainMenu);
			//scrollToelement(ContractfromMenu);
		//	clickElement(ContractfromMenu);
			//clickElement(ReviewContractRequests);
			writeToElement(TypeTrafficNo, TrafficFile);
			waitForElement(SelectsearchResult);
			clickElement(SelectsearchResult);
			selectFromListByValue(SelectRequestStatus, "12");
			selectFromListByValue(selectRequestType, "1");
			clickElement(SearchBtn);
			scrollToelement(SelectRow);
			clickElement(SelectRow);
			clickElement(OpenView);
			waitForElement(AddNotes);
			writeToElement(AddNotes, "ملاحظات");
			
			Thread.sleep(5000);
			clickElement(ViewAttach);
			Thread.sleep(5000);
			String firstwindow = switchToSecondWindow();
			Thread.sleep(5000);
			driver.close();
			Thread.sleep(5000);
			switchToWindow(firstwindow);
			Thread.sleep(5000);
			
			clickElement(FileUploaded);
			uploadFile("Lighthouse.jpg");
			Thread.sleep(5000);
			
			
			clickElement(ViewAttach2);
			Thread.sleep(5000);
			String SecondWindow = switchToSecondWindow();
			Thread.sleep(5000);
			driver.close();
			Thread.sleep(5000);
			switchToWindow(SecondWindow);
			clickElement(ApprovalBtn);
			Thread.sleep(5000);
			clickElement(BackFromContractBtn);
		

	}
		public void requestToAddVehicleForFinancialDep() throws InterruptedException, AWTException {
			//clickElement(MainMenu);
			//scrollToelement(ContractfromMenu);
			//clickElement(ContractfromMenu);
			//clickElement(ReviewContractRequests);
			writeToElement(TypeTrafficNo, TrafficFile);
			waitForElement(SelectsearchResult);
			clickElement(SelectsearchResult);
			selectFromListByValue(SelectRequestStatus, "14");
			selectFromListByValue(selectRequestType, "1");
			clickElement(SearchBtn);
			scrollToelement(SelectRow);
			clickElement(SelectRow);
			clickElement(OpenView);
			waitForElement(AddNotes);
			writeToElement(AddNotes, "ملاحظات");
			
			Thread.sleep(5000);
			clickElement(ViewAttach);
			Thread.sleep(5000);
			String firstwindow = switchToSecondWindow();
			Thread.sleep(5000);
			driver.close();
			Thread.sleep(5000);
			switchToWindow(firstwindow);
			
			Thread.sleep(5000);
			clickElement(ViewAttach2);
			Thread.sleep(5000);
			firstwindow = switchToSecondWindow();
			Thread.sleep(5000);
			driver.close();
			Thread.sleep(5000);
			switchToWindow(firstwindow);
			

			Thread.sleep(5000);
			clickElement(ViewAttach3);
			Thread.sleep(5000);
			String ThirdWindow = switchToSecondWindow();
			Thread.sleep(5000);
			driver.close();
			Thread.sleep(5000);
			switchToWindow(ThirdWindow);
			
			writeToElement(EID, "123456");
			writeToElement(Passport, "123456");
			writeToElement(ArName, "كريم عماد");
			writeToElement(EnName, "كريم عماد");
			writeToElement(NewContractNO, TrafficFile);
			
			clickElement(FileUploaded);
			uploadFile("Lighthouse.jpg");
			Thread.sleep(5000);
			clickElement(ApprovalBtn);
			Thread.sleep(5000);
			clickElement(BackBtn);
		


	}
		
		
		public void requestToReduceVehicleForPlanDep() throws InterruptedException, AWTException {
			clickElement(MainMenu);
			scrollToelement(ContractfromMenu);
			clickElement(ContractfromMenu);
			clickElement(ReviewContractRequests);
			writeToElement(TypeTrafficNo,TrafficFile);
			waitForElement(SelectsearchResult);
			clickElement(SelectsearchResult);
			selectFromListByValue(SelectRequestStatus, "6");
			selectFromListByValue(selectRequestType, "2");
			clickElement(SearchBtn);
			scrollToelement(SelectRow);
			clickElement(SelectRow);
			clickElement(OpenView);
			waitForElement(AddNotes);
			writeToElement(AddNotes, "ملاحظات");
			Thread.sleep(5000);
			clickElement(ViewAttach);
			Thread.sleep(5000);
			String firstwindow = switchToSecondWindow();
			Thread.sleep(5000);
			driver.close();
			Thread.sleep(5000);
			switchToWindow(firstwindow);
			clickElement(FileUploaded);
			uploadFile("Lighthouse.jpg");
			Thread.sleep(5000);
			clickElement(ApprovalBtn);
			Thread.sleep(5000);
			clickElement(BackBtn);
			
		}
			public void requestTReduceVehicleForContractDep() throws InterruptedException, AWTException {
				clickElement(MainMenu);
				scrollToelement(ContractfromMenu);
				clickElement(ContractfromMenu);
				clickElement(ReviewContractRequests);
				writeToElement(TypeTrafficNo, TrafficFile);
				waitForElement(SelectsearchResult);
				clickElement(SelectsearchResult);
				selectFromListByValue(SelectRequestStatus, "12");
				selectFromListByValue(selectRequestType, "2");
				clickElement(SearchBtn);
				scrollToelement(SelectRow);
				clickElement(SelectRow);
				clickElement(OpenView);
				waitForElement(AddNotes);
				writeToElement(AddNotes, "ملاحظات");
				
				Thread.sleep(5000);
				clickElement(ViewAttach);
				Thread.sleep(5000);
				String firstwindow = switchToSecondWindow();
				Thread.sleep(5000);
				driver.close();
				Thread.sleep(5000);
				switchToWindow(firstwindow);
				Thread.sleep(5000);
				
				clickElement(FileUploaded);
				uploadFile("Lighthouse.jpg");
				Thread.sleep(5000);
				
				
				clickElement(ViewAttach2);
				Thread.sleep(5000);
				String SecondWindow = switchToSecondWindow();
				Thread.sleep(5000);
				System.out.println("kokokoko");
				driver.close();
				Thread.sleep(5000);
				switchToWindow(SecondWindow);
				clickElement(ApprovalBtn);
				Thread.sleep(5000);
				clickElement(BackFromContractBtn);
			
			

		}
			public void requestToReduceVehicleForFinancialDep() throws InterruptedException, AWTException {
				clickElement(MainMenu);
				scrollToelement(ContractfromMenu);
				clickElement(ContractfromMenu);
				clickElement(ReviewContractRequests);
				writeToElement(TypeTrafficNo, TrafficFile);
				waitForElement(SelectsearchResult);
				clickElement(SelectsearchResult);
				selectFromListByValue(SelectRequestStatus, "14");
				selectFromListByValue(selectRequestType, "2");
				clickElement(SearchBtn);
				scrollToelement(SelectRow);
				clickElement(SelectRow);
				clickElement(OpenView);
				waitForElement(AddNotes);
				writeToElement(AddNotes, "ملاحظات");
				
				Thread.sleep(5000);
				clickElement(ViewAttach);
				Thread.sleep(5000);
				String firstwindow = switchToSecondWindow();
				Thread.sleep(5000);
				driver.close();
				Thread.sleep(5000);
				switchToWindow(firstwindow);
				
				Thread.sleep(5000);
				clickElement(ViewAttach2);
				Thread.sleep(5000);
				firstwindow = switchToSecondWindow();
				Thread.sleep(5000);
				driver.close();
				Thread.sleep(5000);
				switchToWindow(firstwindow);
				

				Thread.sleep(5000);
				clickElement(ViewAttach3);
				Thread.sleep(5000);
				String ThirdWindow = switchToSecondWindow();
				Thread.sleep(5000);
				driver.close();
				Thread.sleep(5000);
				switchToWindow(ThirdWindow);
				
				writeToElement(EID, "123456");
				writeToElement(Passport, "123456");
				writeToElement(ArName, "Kareem");
				writeToElement(EnName, "Kareem");
				writeToElement(NewContractNO, "123456");
				
				clickElement(FileUploaded);
				uploadFile("Lighthouse.jpg");
				Thread.sleep(5000);
				clickElement(ApprovalBtn);
				Thread.sleep(5000);
				clickElement(BackBtn);
			
			

		}

	public void requestToReduceVehicle() {

	}

	public ReviewContractRequestPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

}
