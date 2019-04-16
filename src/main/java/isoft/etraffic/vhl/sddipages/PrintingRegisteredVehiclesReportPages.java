package isoft.etraffic.vhl.sddipages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.SeleniumWraper;

public class PrintingRegisteredVehiclesReportPages  extends SeleniumWraper {

	
	By printLangLst = By.xpath("//*[@name='printLang']");
	By confirmBtn = By.id("confirmBtn");
	By radioBtn = By.xpath("//*[@name='methodOfDelivery']");
	By confrimAndProceedBtn = By.id("idConfrimAndProceedButton");
	
	public void proceedTrs() throws InterruptedException
	{
		selectFromListByVisibleText(printLangLst, "English");
		Thread.sleep(1000);
		clickElementJS(confirmBtn);

		Thread.sleep(5000);
		clickElementJS(radioBtn);
		waitForElement(confrimAndProceedBtn);
		clickElementJS(confrimAndProceedBtn);
	}
	
	public PrintingRegisteredVehiclesReportPages(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}
