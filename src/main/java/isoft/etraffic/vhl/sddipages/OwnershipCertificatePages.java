package isoft.etraffic.vhl.sddipages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.SeleniumWraper;

public class OwnershipCertificatePages extends SeleniumWraper {

	By authorityLst = By.xpath("//*[@name='authorityId']");
	By vehicleRenewalCb = By.id("cbTncVehicleRenewal");
	By confrimAndProceedBtn = By.id("idConfrimAndProceedButton");
	By methodOfDelivery = By.xpath("//*[@name='methodOfDelivery']");
	
	public void proceedTrs() throws InterruptedException
	{
		waitForElement(authorityLst);
		selectFromListByVisibleText(authorityLst, "Department of Economic Development");
		
		Thread.sleep(2000);
		clickElementJS(confrimAndProceedBtn);

		waitForElement(methodOfDelivery);
		clickElementJS(methodOfDelivery);
		
		waitForElement(confrimAndProceedBtn);
		clickElementJS(confrimAndProceedBtn);
	}
	
	public OwnershipCertificatePages(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}
