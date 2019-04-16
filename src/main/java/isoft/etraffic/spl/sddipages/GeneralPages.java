package isoft.etraffic.spl.sddipages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import isoft.etraffic.wrapper.SeleniumWraper;

public class GeneralPages extends isoft.etraffic.wrapper.SeleniumWraper {

	private By Iagree = By.id("cbTncVehicleRenewal");
	private By StartProcessbtn = By.id("btnStartProcess");
	private By closeHappiniesbtn = By.xpath("//*[@class='fancybox-item fancybox-close']");
	private By Mycontract = By.xpath("//*[contains(@src,'franchise-contract-details.png')]");
	private By MyTradeLicense = By.xpath("//*[contains(@src,'trade-license-info.png')]");
	private By MyVehiclesInformation = By.xpath("//*[contains(@src,'vehicles-info.png')]");
	private By MyDriversPermit = By.xpath("//*[contains(@src,'franchise-contract-details-icon.png')]");
	private By MyEmployeeDetails = By.xpath("//*[contains(@src,'employee-details.png')]");
	private By MyRtaNotification = By.xpath("//*[contains(@src,'notification.png')]");
	private By MymoreServices = By.id("returnToDashboardId");

	public void openmycontract() throws InterruptedException {

		clickElement(Mycontract);
	}

	public void tradeLicensePage() throws InterruptedException {

		clickElement(MyTradeLicense);
	}

	public void vehiclesInformationPage() throws InterruptedException {

		clickElement(MyVehiclesInformation);
	}

	public void driversPermitPage() throws InterruptedException {

		clickElement(MyDriversPermit);
	}

	public void employeeDetailsPage() throws InterruptedException {

		clickElement(MyEmployeeDetails);
	}

	public void rtaNotificationPage() throws InterruptedException {

		clickElement(MyRtaNotification);
	}

	public void moreServicesPage() throws InterruptedException {

		clickElement(MymoreServices);
	}

	public void applyForService() throws InterruptedException {
		Actions a = new Actions(driver);
		WebElement Iagreeelement = driver.findElement(Iagree);
		a.moveToElement(Iagreeelement).perform();
		
		clickElement(Iagree);
		clickElement(StartProcessbtn);
	}

	public void closehappinies() throws InterruptedException {
		clickElement(closeHappiniesbtn);
	}

	public GeneralPages(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

}