package isoft.etraffic.vhl.ftfpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import isoft.etraffic.enums.ReservationPeriod;
import isoft.etraffic.wrapper.SeleniumWraper;

public class RenewReservedNumberPage extends SeleniumWraper {

	public RenewReservedNumberPage(WebDriver driver) {
		super(driver);
	}

	// Select Plate
	By threeMonthBtn = By.id("3MLinkId");
	By sixMonthBtn = By.id("6MLinkId");
	By oneYearBtn = By.id("12MLinkId");
	By popUp = By.id("closeModalId");
	By proceedTrsBtn = By.id("proceedTrsId");

	public void proceedTrs(ReservationPeriod period) throws InterruptedException {

		waitForElement(proceedTrsBtn);
		switch(period)
		{
		case ThreeMonths:
			clickButton(threeMonthBtn);
		case SixMonths:
			clickButton(sixMonthBtn);
		case OneYear:
			clickButton(oneYearBtn);
		default:
			clickButton(threeMonthBtn);
		}
		
		while(isElementDisplayed(popUp))
			Thread.sleep(1000);
		
		clickButton(proceedTrsBtn);
		
	}

	public void proceedTrs() throws InterruptedException {
		waitForElement(proceedTrsBtn);
		clickElementJS(proceedTrsBtn);
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}

