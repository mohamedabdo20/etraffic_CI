package isoft.etraffic.drl.sddipages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.SeleniumWraper;

public class TourismCertificatePage extends SeleniumWraper {

	By remarksTxt = By.id("remarksId");

	public void proceedTrs() throws InterruptedException {
		waitForElement(remarksTxt);
		writeToElement(remarksTxt, "Remarks Remarks Remarks");
		Thread.sleep(500);
		clickByLinkTxt("Confirm & Proceed");
		
		acceptAlert();
	}

	public TourismCertificatePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

}
