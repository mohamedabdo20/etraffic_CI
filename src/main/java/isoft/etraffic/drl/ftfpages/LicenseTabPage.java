package isoft.etraffic.drl.ftfpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.SeleniumWraper;

public class LicenseTabPage extends SeleniumWraper {

	By licenseFrame = By.xpath("//*[starts-with(@src,'/traffic/faces/jsf/secure/dtt/driving_license_search.jsf?trafficNo=')]");
	By changeToManualBtn = By.xpath("//*[contains(@id,'convertAutoToManualBtnId')]");
	By testDateLbl = By.xpath("//*[@class='modal-title']");
	By testDateFrame = By.xpath("//*[starts-with(@src,'/traffic/faces/jsf/secure/stp/trs/appointment_slots.jsf?')]");
	By firstAvlDate=By.xpath("//*[@title='إحجز']");
	By confirmBtn=By.xpath("//*[contains(@class,'confirmButton')]");
	By proceedTrsBtn = By.id("recertifyBtnId");
	
	public void changeToManual() throws InterruptedException {
		waitForElement(licenseFrame);
		Thread.sleep(2000);
		switchToFrame(licenseFrame);
		
		//waitForElement(changeToManualBtn);
		clickElementJS(changeToManualBtn);
		
		waitForElement(testDateLbl);
		String mainWindow = driver.getWindowHandle();
		waitForElement(testDateFrame);
		switchToFrame(testDateFrame);
		clickElementJS(firstAvlDate);
		
		waitForElement(confirmBtn);
		clickElementJS(confirmBtn);
		
		Thread.sleep(1000);
		switchToWindow(mainWindow);
		waitForElement(proceedTrsBtn);
		clickElementJS(proceedTrsBtn);
	} 
	
	public LicenseTabPage(WebDriver driver) {
		super(driver);
	}
	
	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}