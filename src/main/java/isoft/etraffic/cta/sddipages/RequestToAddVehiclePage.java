package isoft.etraffic.cta.sddipages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import io.qameta.allure.Step;
import isoft.etraffic.wrapper.SeleniumWraper;


public class RequestToAddVehiclePage extends SeleniumWraper {


	private By addvehcilelink = By.xpath("//a[contains(@href,'add-vehicles-to-contract.do')]");
	private By NoVehicleToAdd = By.id("noOfVehiclesId");
	private By AttchedvehicleSpecfications = By.id("vehicleSpecficationsAttachmentCopyId");
	private By DataAccuracy = By.id("dataAccuracyId");
	private By TermsAndCondition = By.id("termsAndConditionOfAddReducevehiclesId");
	private By Confirm = By.id("btnProceed");

	
	@Step("Initiate Add vehicle request")
	public void requesttoaddvehicle () throws InterruptedException {
		
		clickElement(addvehcilelink);
		writeToElement(NoVehicleToAdd, "5");
		FileInputElement(AttchedvehicleSpecfications, System.getProperty("user.dir")+"\\attachments\\Lighthouse.jpg");
		clickElement(DataAccuracy);
		clickElement(TermsAndCondition);
		clickElement(Confirm);
	}



	
	public RequestToAddVehiclePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}


}
