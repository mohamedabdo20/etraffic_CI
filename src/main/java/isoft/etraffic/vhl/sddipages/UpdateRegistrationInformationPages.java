package isoft.etraffic.vhl.sddipages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import isoft.etraffic.wrapper.SeleniumWraper;

public class UpdateRegistrationInformationPages extends SeleniumWraper {

	By confrimAndProceedBtn = By.partialLinkText("Confirm & Proceed to Delivery Method");//("confirmAndProceedButton");
	
	public void proceedTrs() throws InterruptedException
	{
		waitForElement(confrimAndProceedBtn);
		clickElementJS(confrimAndProceedBtn);

	}
	
	public UpdateRegistrationInformationPages(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}
