package isoft.etraffic.vhl.ftfpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.SeleniumWraper;

public class ClearanceCertificatePage extends SeleniumWraper {

	By proceedBtn = By.xpath("//*[@id='renewalRegistrationDisabledId']/button");

	public void proceedTrs() throws InterruptedException {
		waitForElement(proceedBtn);
		clickElementJS(proceedBtn);
 
	}

	public ClearanceCertificatePage(WebDriver driver) {
		super(driver);
	}
	
	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}
