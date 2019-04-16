package isoft.etraffic.cta.sddipages;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.SeleniumWraper;

public class AddUsedVehiclesPage extends SeleniumWraper{
	
	

	private By Mycontract = By.xpath("//*[contains(@src,'franchise-contract-details.png')]");
	private By Addusedvehcilelink = By.xpath("//a[contains(@href,'add-used-streched-approval-booklet')]");
	private By ChassisNo = By.id("chassisNumberId");
	private By VehicleModel = By.id("select2-vsmId-container");
	private By SelectModel =  By.id("select2-vsmId-result-n03l-2860000000");
	//private By SelectModel = By.id("select2-vsmId-result-82a0-1351690000");select2-vsmId-result-pn0g-1351690000
	private By ModelYearmenu = By.id("select2-myrId-container");
	private By ModelYear = By.xpath("/html/body/span/span/span[2]/ul/li[3]");
	//private By ModelYear = By.id("select2-myrId-result-ybhd-2018");
	private By SelectPlate = By.id("pltLookupId");
	//private By Chooseoneplate = By.xpath("/html/body/form/div[1]/div/table/tbody/tr[2]/td[1]");
	private By Chooseoneplate = By.xpath("//*[@class= \"srchrsult\"]//*[@class=\"plate sm\"]");
	private By DataAccuracy = By.id("dataAccuracyId");
	private By ConfirmFees = By.id("feeAcknowledgementId");
	private By Confirm = By.id("btnProceed");
	
	//to generate Chassis No
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss");
	Calendar cal = Calendar.getInstance();
	String chassis =  sdf.format(cal.getTime()).replace("-", "").replace(":", "")+"00";

	
	
	public void addusedvehicles () throws InterruptedException {
		clickElement(Addusedvehcilelink);
		writeToElement(ChassisNo, chassis);
		clickElement(VehicleModel);
		clickElement(SelectModel);
		clickElement(ModelYearmenu);
		clickElement(ModelYear);
		clickElement(SelectPlate);
		waitForElement(By.xpath("//*[@class='cboxIframe']"));
		driver.switchTo().frame(driver.findElement(By.xpath("//*[@class='cboxIframe']")));
		clickElementJS(Chooseoneplate);
		clickElement(DataAccuracy);
		clickElement(ConfirmFees);
		clickElement(Confirm);
	}



	public AddUsedVehiclesPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
	

}
