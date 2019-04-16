package isoft.etraffic.drl.ftfpages;

import java.awt.AWTException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import isoft.etraffic.wrapper.SeleniumWraper;

public class UpdateSponsorInfoPage extends SeleniumWraper {

	By manualBtn = By.id("Manual_Data");
	By manualImg = By.xpath("//*[@src='/traffic/smart_resources/images/Manual-Entry.png']");
	By proceedBtn = By.id("proceedTrsId");
	By sponsorTypeLst = By.id("sponsorTypeManual");
	By verifyChBox = By.id("isVerified");
	By attachmentBtn = By.id("attachmentBtnHidden");
	By submitBtn = By.id("submitBtn");
	By closeAttachmentBtn = By.id("attachmentClose");

	public void updateInfo(String sponsorProfession, String sponsorTrfFile) throws InterruptedException, AWTException {
		
		Thread.sleep(1000);
		clickElementJS(manualImg);
		Thread.sleep(1000);
		
		List<WebElement> inputs = driver.findElements(By.xpath("//*[@class='select2-search__field']"));
		selectFromFTFList(inputs.get(0), sponsorProfession);
		Thread.sleep(1000);
		
		selectFromListByVisibleText(sponsorTypeLst, "مواطن");
		
		List<WebElement> combobox = driver.findElements(By.xpath("//*[@role='combobox']"));
		Actions a = new Actions(driver);
		a.moveToElement(combobox.get(1)).click().build().perform();
		Thread.sleep(500);
		inputs = driver.findElements(By.xpath("//*[@class='select2-search__field']"));
		selectFromFTFList(inputs.get(0), sponsorTrfFile);
		
		clickElementJS(verifyChBox);
		Thread.sleep(500);

		Thread.sleep(1000);
		clickElementJS(proceedBtn);
		Thread.sleep(1000);
		
		String mainWindow = driver.getWindowHandle();
		waitForElement(By.id("attachmentIframeId"));
		switchToFrame("attachmentIframeId");
		waitForElement(By.id("dataFileId"));
		a = new Actions(driver);
		a.moveToElement(driver.findElement(By.id("dataFileId"))).click().build().perform();
		
		Thread.sleep(1000);
		uploadFile("TrailerReg.jpg");
		Thread.sleep(500);
		clickElementJS(submitBtn);
		Thread.sleep(500);
		
		switchToWindow(mainWindow);
		clickElementJS(closeAttachmentBtn);
		
		waitForElement(By.id("nextPageBtnId"));
		clickElementJS(By.id("nextPageBtnId"));
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public UpdateSponsorInfoPage(WebDriver driver) {
		super(driver);
	}
}
