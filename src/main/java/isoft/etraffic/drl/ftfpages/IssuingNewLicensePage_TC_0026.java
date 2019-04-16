package isoft.etraffic.drl.ftfpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
import isoft.etraffic.wrapper.SeleniumWraper;

public class IssuingNewLicensePage_TC_0026  extends SeleniumWraper {


	By damageBtn = By.id("damage");
	By proceedTrsBtn = By.id("proceedTrsId");
	By studentFileFrame = By.id("studentFileFrame");
	By IssueLicenseBtn = By.xpath("//*[@title='إصدار رخصة جديدة بموجب فحص']");

	public void proceedTrs() throws InterruptedException {
		Thread.sleep(1000);
		waitForElement(studentFileFrame);
		switchToFrame(studentFileFrame);
		waitForElement(IssueLicenseBtn);
		clickElementJS(IssueLicenseBtn);
		Thread.sleep(1000);
		waitForElement(proceedTrsBtn);
//		Thread.sleep(2000);
//		clickElementJS(proceedTrsBtn);
//		refresh();
	} 
	
	public IssuingNewLicensePage_TC_0026(WebDriver driver) {
		super(driver);
	}
	
	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}
