package isoft.etraffic.vhl.sddipages;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.*;

public class TourismNOCCertificatePage extends SeleniumWraper {

	By refNoTxt = By.id("txtNOCRefNo");
	By typeLst = By.id("txtNOCType");
	By calendar = By.xpath("//*[@id='dp1']//*[@class='glyphicon glyphicon-calendar']");
	By vehicleSourceLst = By.id("txtEmirate");
	By sourceTypeLst = By.id("txtSourceType");
	By plateNoTxt = By.id("txtPlateNo");
	By plateCategoryLst = By.id("txtPlateCategory");
	By plateCodeLst = By.id("txtPlateCode");
	By searchBtn = By.id("btnSearchVehicle");
	By addBtn = By.id("btnAddVehicle");
	By mobileNoTxt = By.id("txtMobileNo");
	By emailTxt = By.id("txtEmail");
	By nocOwnerBtn = By.id("cbIsSameVehiclesOwner");
	By confirmBtn = By.id("btnGoToStep2");
	By proceedBtn = By.id("btnGoToStep4");

	public void proceedTrs(String plateNumber, String plateCategory, String plateCode) throws InterruptedException {

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String reference = sdf.format(cal.getTime()).replace("-", "").replace(":", "");
		waitForElement(refNoTxt);
		writeToElement(refNoTxt, reference);

		selectFromListByVisibleText(typeLst, "Tourism Certficate NOC");
		clickElementJS(calendar);
		Thread.sleep(500); // was 3
		selectFromListByVisibleText(vehicleSourceLst, "Dubai");
		selectFromListByVisibleText(sourceTypeLst, "Registered Vehicle");
		Thread.sleep(500);

		writeToElement(plateNoTxt, plateNumber);
		selectFromListByVisibleText(plateCategoryLst, plateCategory);
		Thread.sleep(1000);
		
		waitForElement(plateCodeLst);
		selectFromListByVisibleText(plateCodeLst, plateCode);
		Thread.sleep(500);
		clickElementJS(searchBtn); // Search
		Thread.sleep(1000);
		
		while(getElementAttributeValue(By.id("overlay"), "style").contains("block"))
		{Thread.sleep(500);}
		clickElementJS(addBtn);

		waitForElement(nocOwnerBtn);
		clickElementJS(nocOwnerBtn);
		writeToElement(mobileNoTxt, "971555145123");
		writeToElement(emailTxt, "test@test.ae");
		Thread.sleep(1000);
		clickElementJS(confirmBtn);

		waitForElement(proceedBtn);
		clickElementJS(proceedBtn);

	}

	public TourismNOCCertificatePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}