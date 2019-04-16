package isoft.etraffic.drl.sddipages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import isoft.etraffic.wrapper.SeleniumWraper;

public class LicenseFramePage extends SeleniumWraper{

	By frame = By.id("cboxIframe");
	By plateNoTxt = By.xpath("//*[@name='plateNo']");
	By plateCodeLst = By.xpath("//*[@name='selectedPlateCode']");
	By plateCategoryLst = By.xpath("//*[@name='selectedPlateCategory']");
	By trafficFileTxt = By.xpath("//*[@name='trafficNoPlt']");
	By birthDateTxt = By.xpath("//*[@name='birthDatePlt']");
	By saveBtn = By.xpath("//*[@name='saveButton']");
	
	By licenseNoTxt = By.xpath("//*[@name='driverLicenseNo']");
	By issueDateTxt = By.xpath("//*[@name='driverLicenseIssueDate']");
	By licensetrafficNoTxt = By.xpath("//*[@name='trafficNoDrl']");
	By birthYearTxt = By.xpath("//*[@name='birthDateDrl']");
	
	public void byPlate(String plateNumber, String plateCode, String plateCategory, String trafficFile, String birthDate) throws InterruptedException
	{
		waitForElement(frame);
		switchToFrame(frame);
		waitForLoading();
		
		waitForElement(By.xpath("//*[@value='plate']"));
		clickElementJS(By.xpath("//*[@value='plate']"));
		
		waitForElement(plateNoTxt);
		writeToElement(plateNoTxt, plateNumber);
		selectFromListByVisibleText(plateCodeLst, plateCode);
		selectFromListByVisibleText(plateCategoryLst, plateCategory);
		writeToElement(trafficFileTxt, trafficFile);
		writeToElement(birthDateTxt, birthDate);
		
		Thread.sleep(500);
		clickElementJS(saveBtn);
	}
	
	public void byDrivingLicense(String trafficFile, String licenseNumber, String birthYear, String issueDate) throws InterruptedException
	{
		waitForElement(frame);
		switchToFrame(frame);
		waitForLoading();
		
		waitForElement(By.xpath("//*[@value='license']"));
		clickElementJS(By.xpath("//*[@value='license']"));
		
		waitForElement(licenseNoTxt);
		writeToElement(licenseNoTxt, licenseNumber);
		writeToElement(issueDateTxt, issueDate);
		writeToElement(licensetrafficNoTxt, trafficFile);
		writeToElement(birthYearTxt, birthYear);
		
		Thread.sleep(500);
		//clickElementJS(saveBtn);
	}
	private void waitForLoading() throws InterruptedException {
		Thread.sleep(500);
		while(!getElementAttributeValue(By.id("overlay2"), "style").contains("none"))
		{
			System.out.println("wait to be shown.");
			Thread.sleep(500);
		}
	}
	public LicenseFramePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

}
