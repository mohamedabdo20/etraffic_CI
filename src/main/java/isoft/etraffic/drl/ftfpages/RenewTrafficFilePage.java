package isoft.etraffic.drl.ftfpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.SeleniumWraper;

public class RenewTrafficFilePage extends SeleniumWraper {

	By renewBtn = By.xpath("//*[@data-original-title='تجديد ملف']");
	By proceedTrsBtn = By.id("proceedTrsId");

	public void proceedTrs() throws InterruptedException {
		waitForElement(By.id("studentFileFrame"));
		switchToFrame("studentFileFrame");
		clickElementJS(renewBtn);
		
		waitForElement(proceedTrsBtn);
		Thread.sleep(1000);
		clickElementJS(proceedTrsBtn);

	} 
	
	public RenewTrafficFilePage(WebDriver driver) {
		super(driver);
	}
	
	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

}
