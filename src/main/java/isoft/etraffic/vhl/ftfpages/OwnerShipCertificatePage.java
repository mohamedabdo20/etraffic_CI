package isoft.etraffic.vhl.ftfpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import isoft.etraffic.wrapper.SeleniumWraper;

public class OwnerShipCertificatePage extends SeleniumWraper {
	public OwnerShipCertificatePage(WebDriver driver) {
		super(driver);
	}

	By contactAuthorityLst = By.id("contactAuthority-selectized");
	By proceedTrsBtn = By.id("proceedTrsId");

	public void proceedTrs() throws InterruptedException {

		waitForElement(contactAuthorityLst);
		selectFromFTFList(contactAuthorityLst, "هيئة تنمية المجتمع");

		clickElementJS(proceedTrsBtn);
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

}
