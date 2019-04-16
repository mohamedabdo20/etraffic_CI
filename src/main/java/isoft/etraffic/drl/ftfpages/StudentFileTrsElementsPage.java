package isoft.etraffic.drl.ftfpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import isoft.etraffic.wrapper.SeleniumWraper;

public class StudentFileTrsElementsPage extends SeleniumWraper {

	public StudentFileTrsElementsPage(WebDriver driver) {
		super(driver);
	}

	// Search by Plate
	By updateLicenseBtn = By.id("nextPageBtnId");
	By emiratesLst = By.id("emirateId:emirateIdSelectOneMenu-selectized");
	By newCenterLst = By.id("centersField:centersFieldSelectOneMenu-selectized");
	
	By proceedTrsBtn = By.id("proceedTrsId");
	
	public void clickUpdateLicenceBtn() throws InterruptedException {
		waitForElement(updateLicenseBtn);
		clickElementJS(updateLicenseBtn);

	}
	
	public void selectEmirate(String emirate) throws InterruptedException {
		waitForElement(emiratesLst);
		Thread.sleep(1000);
		selectFromFTFList(emiratesLst, emirate);
	}
	
	public void selectNewCenter(String centerName) throws InterruptedException {
		waitForElement(newCenterLst);
		Thread.sleep(1000);
		selectFromFTFList(newCenterLst, centerName);
	}

	public void clickProceedBtn() throws InterruptedException {
		waitForElement(proceedTrsBtn);
		Thread.sleep(1000);
		clickElementJS(proceedTrsBtn);

	}
	
	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}
