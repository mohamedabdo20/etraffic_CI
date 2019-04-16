package isoft.etraffic.vhl.sddipages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.SeleniumWraper;

public class PossessionCertificatePages extends SeleniumWraper {

	By vehicleRenewalBtn = By.linkText("Vehicle Renewal");
	By vehicleRenewalCb = By.id("cbTncVehicleRenewal");
	By startProcessBtn = By.id("btnStartProcess");
	
	public void proceedTrs() throws InterruptedException
	{
		waitForElement(By.id("confirmAndProceedButton"));
		try{selectFromListByVisibleText(By.id("plateStatusId"), "Returned to RTA");}
		catch(Exception e) {}
		Thread.sleep(3000);
		clickElementJS(By.id("confirmAndProceedButton"));	
	}
	
	public PossessionCertificatePages(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}
