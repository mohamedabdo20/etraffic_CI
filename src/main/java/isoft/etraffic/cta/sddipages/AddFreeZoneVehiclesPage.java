package isoft.etraffic.cta.sddipages;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;

import io.qameta.allure.Step;
import isoft.etraffic.wrapper.SeleniumWraper;

public class AddFreeZoneVehiclesPage extends SeleniumWraper {

	private By FreeZoneLink = By.xpath("//*[contains(@href,'add-approval')]");
	private By ChassisNo = By.id("chassisNumberId");	
	private By VehicleClass = By.id("vclId");
	private By VehicleManufucture = By.id("vmkId");
	private By VehicleModel = By.id("vsmId");
	private By SelectPlate = By.xpath("//*[contains(@href,'#')]");
	private By SelectPlateCodeU = By.xpath("//*[@class='srchrsult']//tr[2]/td[1]");
	private By BtnProceed = By.id("btnProceed");
	
			


	@Step("Initiate and fill data of FreeZone vehicle request")
	public void addfreezonevehiclespage() throws InterruptedException {
		
		clickElement(FreeZoneLink);
		
		//to generate Chassis No
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String chassis =  sdf.format(cal.getTime()).replace("-", "").replace(":", "")+"00";
		writeToElement(ChassisNo, chassis);
		
		selectFromListByVisibleText(VehicleClass,"Light Vehicle");
		selectFromListByValue(VehicleManufucture, "10251");
		selectFromListByValue(VehicleModel, "310000002056");
		clickElement(SelectPlate);
		driver.switchTo().frame(driver.findElement(By.xpath("//*[contains(@src,'franchise-plates-lookup.do')]")));
		clickElement(SelectPlateCodeU);
		waitForElement(BtnProceed);
		clickElement(BtnProceed);
		
		
		
	}
	
	
	
	public AddFreeZoneVehiclesPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}



	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

}
