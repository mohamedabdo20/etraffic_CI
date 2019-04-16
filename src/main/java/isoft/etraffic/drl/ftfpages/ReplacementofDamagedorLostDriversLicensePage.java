package isoft.etraffic.drl.ftfpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.SeleniumWraper;

public class ReplacementofDamagedorLostDriversLicensePage  extends SeleniumWraper {


	By damageBtn = By.id("damage");
	By proceedTrsBtn = By.id("proceedTrsId");

	public void proceedTrs() throws InterruptedException {
		waitForElement(damageBtn);
		clickElementJS(damageBtn);
		
		waitForElement(proceedTrsBtn);
		Thread.sleep(1000);
		clickElementJS(proceedTrsBtn);

	} 
	
	public ReplacementofDamagedorLostDriversLicensePage(WebDriver driver) {
		super(driver);
	}
	
	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

}
