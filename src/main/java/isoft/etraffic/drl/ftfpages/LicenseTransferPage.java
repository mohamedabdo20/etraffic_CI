package isoft.etraffic.drl.ftfpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.SeleniumWraper;

public class LicenseTransferPage  extends SeleniumWraper {


	By AUHBtn = By.id("AUH");
	By proceedTrsBtn = By.id("proceedTrsId");

	public void proceedTrs() throws InterruptedException {
		waitForElement(AUHBtn);
		clickElementJS(AUHBtn);
		
		waitForElement(proceedTrsBtn);
		Thread.sleep(1000);
		clickElementJS(proceedTrsBtn);

	} 
	
	public LicenseTransferPage(WebDriver driver) {
		super(driver);
	}
	
	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

}
