package isoft.etraffic.drl.ftfpages;

import java.awt.AWTException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
import isoft.etraffic.wrapper.SeleniumWraper;

public class RequiredTestModifyLastSuccessfulPage_TC_0025  extends SeleniumWraper {


	By damageBtn = By.id("damage");
	By proceedTrsBtn = By.id("proceedTrsId");
	By reqTestsFrame = By.id("reqTestsFrame");
	By modifyAppBtn = By.xpath("//*[@data-original-title='تعديل اخر موعد فحص']");
	By modifyTrialFrame = By.id("modifyTrialFrame");
	By notesTxt = By.id("allForm:remarksId:remarksId");
	By uploadBtn = By.id("allForm:attachementUploadFileBtn");
	By saveBtn = By.id("saveTrialResultButtonId");

	public void proceedTrs() throws InterruptedException, AWTException {
		Thread.sleep(1000);
		
		waitForElement(reqTestsFrame);
		switchToFrame(reqTestsFrame);
		waitForElement(modifyAppBtn);
		clickElementJS(modifyAppBtn);
		
		switchToWindow(driver.getWindowHandle());
		waitForElement(modifyTrialFrame);
		switchToFrame(modifyTrialFrame);//modifyTrialFrame");
		waitForElement(notesTxt);
		writeToElement(notesTxt, "تعديل اخر موعد فحص");
		
		clickElementJS(uploadBtn);
		String firstWindow = switchToSecondWindow();
		Thread.sleep(1000);
		uploadImage("Lighthouse.jpg");
		switchToWindow(firstWindow);
		
		waitForElement(saveBtn);
		clickElement(saveBtn);
	} 
	
	public RequiredTestModifyLastSuccessfulPage_TC_0025(WebDriver driver) {
		super(driver);
	}
	
	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}
