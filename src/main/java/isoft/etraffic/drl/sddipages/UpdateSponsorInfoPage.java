package isoft.etraffic.drl.sddipages;

import java.awt.AWTException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import isoft.etraffic.wrapper.SeleniumWraper;

public class UpdateSponsorInfoPage extends SeleniumWraper {

	By manualRdBtn = By.id("featured-2");
	By occupationNameBtn = By.id("lookupOccBtnId");
	By occupationEnTxt = By.id("DescriptionEnId");
	By firstOccupationRow = By.xpath("//*[@class='row border-bottom box']");
	By sponsorNameTxt = By.id("sponsorTrafficFileNameId");
	By chooseFileBtn = By.id("visaCopyFileId");
	By confirmChkBox = By.id("confirmYourDataId");
	By sponsorTypeLst = By.id("sponsorTypeId");
	
	By issuePlaceTxt = By.id("issuePlaceId");
	By licenseNOTxt = By.id("licenseNOId");
	By catCodeTxt = By.id("catCodes-selectized");
	
	By proceedBtn = By.id("idConfrimAndProceedButton");
	
	String issueDate, expiryDate;
	
	public void updateInfo(String occupationName, String sponsorName, String sponsorType) throws InterruptedException, AWTException {
		waitForElement(By.xpath("//*[@class='bootstrap-dialog-footer-buttons']/button"));
		clickElementJS(By.xpath("//*[@class='bootstrap-dialog-footer-buttons']/button"));
		
		waitForElement(manualRdBtn);
		clickElementJS(manualRdBtn);
		Thread.sleep(1000);
		
		waitForElement(sponsorNameTxt);
		clickElementJS(sponsorNameTxt);
		writeToElement(sponsorNameTxt, sponsorName);
		selectFromListByVisibleText(sponsorTypeLst, sponsorType);
		Thread.sleep(1000);
		
		clickElementJS(occupationNameBtn);
		Thread.sleep(1000);
		waitForElement(occupationEnTxt);
		writeToElement(occupationEnTxt, occupationName);
		hitEnterToElement(occupationEnTxt);
		Thread.sleep(500);
		waitForElement(firstOccupationRow);
		clickElementJS(firstOccupationRow);
		
		Thread.sleep(500);
		Actions a = new Actions(driver);
		a.moveToElement(driver.findElement(chooseFileBtn)).click().build().perform();
		clickElementJS(chooseFileBtn);
		Thread.sleep(1000);
		uploadFile("TrailerReg.jpg");
		Thread.sleep(1000);
		
		clickElementJS(confirmChkBox);
		Thread.sleep(1000);
		clickElementJS(proceedBtn);
		
	}

	public UpdateSponsorInfoPage(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean isPageLoaded() {
		return false;
	}
}
