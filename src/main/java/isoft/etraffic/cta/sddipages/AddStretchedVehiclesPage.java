package isoft.etraffic.cta.sddipages;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import io.qameta.allure.Step;
import isoft.etraffic.wrapper.SeleniumWraper;

public class AddStretchedVehiclesPage extends SeleniumWraper
{
	
	private By StetchedVehicleLink = By.xpath("//*[contains(@href, 'add-used-streched-approval-booklet')]");
	private By chassisNumber = By.id("chassisNumberId");
	private By SelectVehicle =By.id("vsmId");   
	//private By VehicleModel =By.id("select2-vsmId-result-6pm4-310000002058");
	private By SelectModelYear = By.id("myrId");
	//select2-myrId-result-a8av-2019
	private By OpenPlate = By.id("pltLookupId");
	private By ChooseOnePlate = By.xpath("//*[@class=\"srchrsult\"]//tr[2]/td[1]");
	private By CheckBox1 = By.id("dataAccuracyId");
	private By CheckBox2 = By.id("feeAcknowledgementId");
	private By Confirm = By.id("btnProceed");
	
	

	@Step("Initiate and fill data of Streched vehicle request")
	
	public void addstretchedvehiclespage () throws InterruptedException {
		
		clickElement(StetchedVehicleLink);
		
		//to generate Chassis No
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String chassis =  sdf.format(cal.getTime()).replace("-", "").replace(":", "")+"00";
		writeToElement(chassisNumber, chassis);

		//selectFromListByVisibleText(SelectVehicle, "LINCOLN TOWN CAR");
		selectFromListByValue(SelectVehicle, "310000002058");
		selectFromListByVisibleText(SelectModelYear, "2019");
		clickElement(OpenPlate);
		driver.switchTo().frame(driver.findElement(By.xpath("//*[contains(@src,'franchise-plates-lookup')]")));
	                        	//*[@class='bottom-menu-item ']//*[contais(@src,'franchise-contract-details.png')]
	                                            
		clickElement(ChooseOnePlate);
		clickElement(CheckBox1);
		clickElement(CheckBox2);
		clickElement(Confirm);
	}
	

	public AddStretchedVehiclesPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

}
