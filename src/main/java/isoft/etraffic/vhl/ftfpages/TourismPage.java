package isoft.etraffic.vhl.ftfpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import isoft.etraffic.wrapper.SeleniumWraper;

public class TourismPage extends SeleniumWraper {

	public TourismPage(WebDriver driver) {
		super(driver);
	}

	By tourismDestinationLst = By.id("tourismDestinationId-selectized");
	By proceedTrsBtn = By.id("proceedTrsId");

	public void proceedTrs(String destination) throws InterruptedException
	{
		waitForElement(tourismDestinationLst);
		selectFromFTFList(tourismDestinationLst, destination);
		clickElement(proceedTrsBtn);
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

}
