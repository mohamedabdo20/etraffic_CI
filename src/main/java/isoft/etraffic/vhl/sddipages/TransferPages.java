package isoft.etraffic.vhl.sddipages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.SeleniumWraper;

public class TransferPages extends SeleniumWraper {

	By emirateIdLst = By.id("emirateId");
	By oldPlateStatusLst = By.id("oldPlateStatusId");
	By soldVehicleLst = By.id("soldVehicle");
	By agreeConfirmationBtn = By.id("agreeConfirmationID");
	By vehicleTestInfoBtn = By.id("vehicleTestInfo");
	By confrimAndProceedBtn = By.partialLinkText("Confirm & Proceed");
	By dateOfDeliveryRB = By.id("dateOfDelivery_<%=deliveryDay%>");
	
	public void proceedTrs() throws InterruptedException
	{
		scrolldown();
		selectFromListByVisibleText(emirateIdLst, "Sharjah");
		Thread.sleep(2000);
		
		try {selectFromListByVisibleText(oldPlateStatusLst, "Returned to RTA");}
		catch (Exception e) {}
		
		selectFromListByVisibleText(soldVehicleLst , "No");
		Thread.sleep(2000);
		clickElementJS(agreeConfirmationBtn);
		clickElementJS(vehicleTestInfoBtn);
		Thread.sleep(1000);
		clickElementJS(confrimAndProceedBtn);
		
		waitForElement(dateOfDeliveryRB);
		clickElementJS(dateOfDeliveryRB);
		clickElementJS(confrimAndProceedBtn);
	}
	
	public TransferPages(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}