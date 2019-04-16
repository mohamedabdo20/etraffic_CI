package isoft.etraffic.vhl.ftfpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.enums.Replacement;
import isoft.etraffic.wrapper.SeleniumWraper;

public class ReplacementLostDamagedOwnershipPage extends SeleniumWraper {

	public ReplacementLostDamagedOwnershipPage(WebDriver driver) {
		super(driver);
	}

	By damagedBtn = By.id("damagedLinkId");
	By proceedTrsBtn = By.partialLinkText("تدقيق المعاملة");

	public void proceedTrs(Replacement replacement) throws InterruptedException {
		waitForElement(proceedTrsBtn);
		if(replacement.equals(Replacement.Damaged))
			clickElement(damagedBtn);
		clickElement(proceedTrsBtn);
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}
