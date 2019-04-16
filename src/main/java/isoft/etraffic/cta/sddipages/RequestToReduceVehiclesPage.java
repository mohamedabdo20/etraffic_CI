package isoft.etraffic.cta.sddipages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import io.qameta.allure.Step;
import isoft.etraffic.wrapper.SeleniumWraper;

public class RequestToReduceVehiclesPage extends SeleniumWraper{


	private By ReduceVehiclesLink = By.xpath("//a[contains(@href,'reduce-vehicles-to-contract.do')]");
	private By NoVehicleToAdd = By.id("noOfVehiclesId");
	private By AttchedvehicleSpecfications = By.id("vehicleSpecficationsAttachmentCopyId");
	private By DataAccuracy = By.id("dataAccuracyId");
	private By TermsAndCondition = By.id("termsAndConditionOfAddReducevehiclesId");
	private By Confirm = By.id("btnProceed");

	
	@Step("Initiate Reduce vehicle request")
	public void requesttoreducevehicle () throws InterruptedException {
	
		clickElement(ReduceVehiclesLink);
		writeToElement(NoVehicleToAdd, "5");
		FileInputElement(AttchedvehicleSpecfications, System.getProperty("user.dir")+"\\attachments\\Lighthouse.jpg");
		clickElement(DataAccuracy);
		clickElement(TermsAndCondition);
		clickElement(Confirm);
	}



	public RequestToReduceVehiclesPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

}
