package isoft.etraffic.vhl.ftfpages;

import java.awt.AWTException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import isoft.etraffic.wrapper.SeleniumWraper;

public class ImportCertificatePage extends SeleniumWraper {

	public ImportCertificatePage(WebDriver driver) {
		super(driver);
	}

	By sourceNoTxt = By.id("sourceNoId");
	By sourceDate = By.id("sourceDateId:sourceDateIdInputTextCalender");
	By insuranceInfoBtn = By.id("insuranceInfoBtnId");
	By insuranceRefTxt = By.id("insuranceRefId");
	By insuranceOrgLst = By.id("insuranceOrgId-selectized");
	By insuranceTypeLst = By.id("insuranceTypeId-selectized");
	By insuranceExpiryDateTxt = By.id("insuranceExpiryDateId:insuranceExpiryDateIdInputTextCalender");
	
	//////////////////
	By fetchChassisBtn = By.id("fetchChassisBtnId");
	By globalNumberLst = By.xpath("//*[@name='globalNumber']");
	By manufacturerTxt = By.id("vmkIdZippyComboboxLookupId");
	By modelTxt = By.id("vsmLookupIdId");
	By classLst = By.xpath("//*[@name='vclId']");
	By descriptionTxt = By.id("vdsLookupId");
	By countryTxt = By.id("countryIdZippyComboboxLookupId");
	By modelYearTxt = By.xpath("//*[@id='modelYearIdZippy']/combotextcontainer/input");
	By engineNoTxt = By.xpath("//*[@name='engineNo']");
	By fuelSourceLst = By.xpath("//*[@name='fuelSource']");
	By selectedFuelLst = By.id("selectedFuel");
	By noOfSeatsTxt = By.xpath("//*[@name='noOfSeats']");
	By noOfDoorsTxt = By.xpath("//*[@name='noOfDoors']");
	By color1Lst = By.xpath("//*[@id='colorIdZippy']/combotextcontainer/input"); 
	By weightTxtx = By.xpath("//*[@name='unloadedWeight']");
	
	By saveBtn = By.id("saveButton");
	
	By proceedBtn = By.id("proceedTrsId");
	
	public void proceedTrs(String chassis) throws InterruptedException, AWTException
	{
		waitForElement(sourceNoTxt);
		writeToElement(sourceNoTxt, "123456789");
		hitTabToElement(sourceNoTxt);
		
		setChassis(chassis);
		
		clickElementJS(insuranceInfoBtn);
		Thread.sleep(1000);
		
		scrollup();
		clickButton(fetchChassisBtn);
		Thread.sleep(1000);
		fillVehicleInfo();
		clickElementJS(proceedBtn);

	}
	
	private void setChassis(String chassis) throws InterruptedException {
		String id;
		for (int i = 0; i < chassis.length(); i++) {
			id = "ch" + Integer.toString(i + 1);
			String value = Character.toString(chassis.charAt(i));
			writeToElement(By.id(id), value);
		}
	}
	
	private void fillVehicleInfo() throws InterruptedException, AWTException
	{
		String firstWinHandle = switchToSecondWindow();
		waitForElement(manufacturerTxt);
		writeToElement(manufacturerTxt, "TOYOTA");
		editElementAttributeValue(driver.findElement(manufacturerTxt), "value", "TOYOTA");
		hitTabToElement(manufacturerTxt);
		Thread.sleep(500);
		writeToElement(modelTxt, "PRADO");
		editElementAttributeValue(driver.findElement(modelTxt), "value", "PRADO");
		hitTabToElement(modelTxt);
		Thread.sleep(500);
		
		selectFromListByVisibleText(classLst, "مركبة خفيفة");
		
		writeToElement(descriptionTxt, "مركبة خصوصية");
		hitTabToElement(descriptionTxt);
		writeToElement(countryTxt, "اليابان");
		Thread.sleep(500);
		hitTabToElement(countryTxt);
		selectFromFTFList(modelYearTxt, "2018");
		writeToElement(engineNoTxt, "123456789");
		selectFromListByVisibleText(fuelSourceLst, "مشتقات بترولية");
		hitTabToElement(fuelSourceLst);
		Thread.sleep(500);
		selectFromListByVisibleText(selectedFuelLst, "بنزين");
		hitTabToElement(selectedFuelLst);
		writeToElement(weightTxtx, "2500");
		writeToElement(noOfSeatsTxt, "5");
		writeToElement(noOfDoorsTxt, "4");
		selectFromFTFList(color1Lst, "ابيض");
		selectFromListByVisibleText(globalNumberLst, "نعم");
		
		Thread.sleep(1000);
		clickElementJS(saveBtn);
		Thread.sleep(1000);
		clickEnterRobot();
		
		switchToWindow(firstWinHandle);
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}