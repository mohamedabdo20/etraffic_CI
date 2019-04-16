package isoft.etraffic.drl.ftfpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.SeleniumWraper;

public class NOCTransferTrfPage_TC_0055_0056 extends SeleniumWraper {

	By proceedTrsBtn = By.id("proceedTrsId");
	By emiratesLst = By.id("emirateId:emirateIdSelectOneMenu-selectized");

	public void proceedTrs() throws InterruptedException {
		waitForElement(emiratesLst);
		selectFromFTFList(emiratesLst, "الشارقة");

		Thread.sleep(1000);
		waitForElement(proceedTrsBtn);
		clickElementJS(proceedTrsBtn);
	}

	public NOCTransferTrfPage_TC_0055_0056(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

}
