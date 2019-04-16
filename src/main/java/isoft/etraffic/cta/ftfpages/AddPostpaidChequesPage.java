package isoft.etraffic.cta.ftfpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import io.qameta.allure.Step;
import isoft.etraffic.wrapper.SeleniumWraper;

public class AddPostpaidChequesPage extends SeleniumWraper {
	
	
	private By Cheques = By.id("postpaid_cheques-btn-id");
	private By AddCheques = By.id("actions1:addButtonId");
	private By DepositChecques = By.id("actionType:actionTypeSelectOneMenu");
	private By NoOfVehicles = By.id("numOfVehicles:numOfVehicles");
	private By NoOfCheques = By.id("chequesNumbers:chequesNumbers");
	private By MonthSelect = By.id("coveredMonth:coveredMonthselectManyListbox");
	private By YearSelect = By.id("coveredYear:coveredYearSelectOneMenu");
	private By Save = By.id("j_idt280:saveButtonId");
	
	//private By StartDate = By.id("contractStartDate:contractStartDate");
	//private By EndDate = By.id("contractEndDate:contractEndDate");
	
/*	public String StartDate() {

		String StartDate;

		RemoteWebElement getstart = (RemoteWebElement) 
				driver.findElement(By.id("contractStartDate:contractStartDate"));
		StartDate = getstart.getText();
		System.out.println(StartDate);
		return StartDate;
	}

	
	public String EndDate() {

		String EndDate;
		RemoteWebElement getdate = (RemoteWebElement) driver
				.findElement(By.id("contractEndDate:contractEndDate"));
		EndDate = getdate.getText();
		System.out.println(EndDate);
		return EndDate;
	}*/
	
	@Step("Add PostPaid Cheaques on Contract")
	public void addpostpaidchequespage() throws InterruptedException {
		Thread.sleep(3000);
		clickElement(Cheques);
		clickElement(AddCheques);
		selectFromListByVisibleText(DepositChecques, "إيداع");
		writeToElement(NoOfVehicles, "100");
		writeToElement(NoOfCheques, "123456");
		selectFromListByValue(MonthSelect, "12");
		selectFromListByValue(YearSelect, "2018");
		clickElement(Save);
		Thread.sleep(3000);
		
	}


	
	public AddPostpaidChequesPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}
