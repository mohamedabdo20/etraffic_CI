package isoft.etraffic.vhl.sddipages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.SeleniumWraper;

public class TourismReturnBackPages extends SeleniumWraper {

	By cbTncVehicleRenewal = By.id("cbTncVehicleRenewal");
	By startProcessBtn = By.id("btnStartProcess");
	By cmlAuthorityLst = By.id("cmlAuthorityId");
	By agreeConfirmationBtn = By.id("agreeConfirmationID");
	By vehicleTestInfoBtn = By.id("vehicleTestInfo");
	By confrimAndProceedBtn = By.partialLinkText("Confirm & Proceed");
	By dateOfDeliveryRB = By.id("dateOfDelivery_<%=deliveryDay%>");
	By chooseDeliveryBtn = By.xpath("//*[@id='button-direction']/div[1]/div/div/button");
	By confirmBtn = By.id("btnGoToStep35");
	
	public void proceedTrs() throws InterruptedException
	{
		waitForElement(By.id("cmlAuthorityId"));
		selectFromListByVisibleText(cmlAuthorityLst, "Al Meydan Free Zone");
		Thread.sleep(1000);

		scrolldown();
		clickElementJS(chooseDeliveryBtn);

		waitForElement(confirmBtn);
		clickElementJS(confirmBtn);

	}
	
	public TourismReturnBackPages(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}