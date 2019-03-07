package isoft.etraffic.vhl.ftfpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.SeleniumWraper;

public class TransferPossesionCertificatePage extends SeleniumWraper {

	public TransferPossesionCertificatePage(WebDriver driver) {
		super(driver);
	}

	By certificateNoTxt = By.id("certificateNoId");
	By firstChassisCharTxt = By.id("ch1");
	By proceedTrsBtn = By.id("proceedTrsId");
	int seconds = 0;

	public void proceedTrs(String certificateNumber) throws InterruptedException {
		waitForElement(certificateNoTxt);
		writeToElement(certificateNoTxt, certificateNumber);
		hitTabToElement(certificateNoTxt);
		hitEnterToElement(certificateNoTxt);

		while (seconds < 10) {
			if (getElementText(firstChassisCharTxt).equals("")) {
				Thread.sleep(1000);
				seconds++;
			} else
				break;
		}

		clickElementJS(proceedTrsBtn);
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}
