package isoft.etraffic.vhl.sddipages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.SeleniumWraper;

public class ClearenceCertificatePage extends SeleniumWraper {

	By confrimAndProceedBtn = By.id("idConfrimAndProceedButton");
	
	public void proceedTrs() throws InterruptedException
	{
		waitForElement(By.xpath("//*[@name='methodOfDelivery']"));
		clickElementJS(By.xpath("//*[@name='methodOfDelivery']"));
		waitForElement(confrimAndProceedBtn);
		clickElementJS(confrimAndProceedBtn);
	}
	
	public ClearenceCertificatePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}
