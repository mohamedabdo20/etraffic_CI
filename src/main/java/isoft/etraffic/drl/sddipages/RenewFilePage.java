package isoft.etraffic.drl.sddipages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.SeleniumWraper;

public class RenewFilePage extends SeleniumWraper {

	
	By renewFileBtn = By.xpath("//*[@title='Renew File']");

	By requestRefLbl = By.xpath("//*[@class='blue']");

	public void renewTrf(String trafficFile) throws InterruptedException {
		waitForElement(renewFileBtn);
		clickElementJS(renewFileBtn);
	}

	public RenewFilePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

}