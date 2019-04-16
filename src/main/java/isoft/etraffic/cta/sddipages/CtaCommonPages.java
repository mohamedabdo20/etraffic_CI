package isoft.etraffic.cta.sddipages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import isoft.etraffic.wrapper.SeleniumWraper;

public class CtaCommonPages extends SeleniumWraper {

	private By Iagree = By.id("cbTncVehicleRenewal");
	private By StartProcessbtn = By.id("btnStartProcess");
	private By closeHappiniesbtn = By.xpath("//*[@class='fancybox-item fancybox-close']");
	private By Mycontract = By.xpath("//*[contains(@src,'franchise-contract-details.png')]");

	private By MainMenu = By.id("menu-trigger");
	private By ContractfromMenu = By.id("listCategoryLinksId2_13");
	private By ReviewContract = By.xpath("//*[contains(@data-name,'متابعة العقود')]");

	private By ContractNo = By.id("contractNoId");
	private By SelectContractTypye = By.id("allowedTypeId");
	private By SelectStatus = By.id("contractStatusId");
	private By Search = By.id("searchBttnId");
	// private By SelectOneContract = By.xpath("//*[class='x-grid3-row-table']");
	private By SelectOneContract = By.className("x-grid3-col x-grid3-cell x-grid3-td-0 x-grid3-cell-first");
	private By Financial = By.id("financesBtnId");
	private By ReviewContractRequests = By.xpath("//*[contains(@data-name,'متابعة طلبات العقود')]");
	

	private By MyTradeLicense = By.xpath("//*[contains(@src,'trade-license-info.png')]");
	private By MyVehiclesInformation = By.xpath("//*[contains(@src,'vehicles-info.png')]");
	private By MyDriversPermit = By.xpath("//*[contains(@src,'franchise-contract-details-icon.png')]");
	private By MyEmployeeDetails = By.xpath("//*[contains(@src,'employee-details.png')]");
	private By MyRtaNotification = By.xpath("//*[contains(@src,'notification.png')]");
	private By MymoreServices = By.id("returnToDashboardId");

	public void openmycontract() throws InterruptedException {
		clickElement(Mycontract);
	}

	public void applyForService() throws InterruptedException {
		waitForElement(Iagree);
		Actions actions = new Actions(driver);
		actions.moveToElement(driver.findElement(Iagree));
		clickElement(Iagree);
		clickElement(StartProcessbtn);
	}

	public void closehappinies() throws InterruptedException {
		clickElement(closeHappiniesbtn);
	}

	public void goToContractsMainMenu() throws InterruptedException {
		goToMainMenu();
		scrollToelement(ContractfromMenu);
		clickElement(ContractfromMenu);
	}

	public void goToMainMenu() throws InterruptedException {
		waitForElement(MainMenu);
		clickElement(MainMenu);
		Thread.sleep(3000);
	}

	public void reviewContract() throws InterruptedException {

		goToContractsMainMenu();
		clickElement(ReviewContract);
	}

	public void openContractRequests() throws InterruptedException {

		goToContractsMainMenu();
		clickElement(ReviewContractRequests);
	}

	public void searchForContract() throws InterruptedException {
		driver.switchTo().frame(driver.findElement(By.id("myIframe")));
		writeToElement(ContractNo, "123/123");
		selectFromListByValue(SelectContractTypye, "2");
		selectFromListByValue(SelectStatus, "2");
		clickElement(Search);
		clickElement(SelectOneContract);
		clickElement(Financial);
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
	public CtaCommonPages(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

}