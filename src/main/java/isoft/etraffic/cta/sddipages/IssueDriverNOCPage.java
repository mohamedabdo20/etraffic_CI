package isoft.etraffic.cta.sddipages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.SeleniumWraper;

public class IssueDriverNOCPage extends SeleniumWraper {


	private By issuenocdriver = By.cssSelector("a[href*='serviceCode=816']");
	/*
	 * WebElement e3 = driver.findElement(By.xpath("//input[@type='checkbox']"));
	 * JavascriptExecutor exec3 = (JavascriptExecutor) driver;
	 * exec3.executeScript("arguments[0].click();", e3);
	 */
	private By CheckBox = By.xpath("//input[@type='checkbox']");
	private By StartProcess = By.xpath("//button[@id='btnStartProcess']");
	private By Traffic = By.xpath("//input[@id='trafficFileNoId']");
	private By LicenseNO = By.xpath("//input[@id='tradeLicenceId']");
	private By Search = By.xpath("//button[@id='btnSearchCompanyDetails']");
	private By Proceed = By.id("btnProceed");

	public void issuedrivernoc(String TrafficFile ,String TradeLicense ) throws InterruptedException {
		clickElement(issuenocdriver);
		clickElement(CheckBox);
		clickElement(StartProcess);
		writeToElement(Traffic, TrafficFile);
		writeToElement(LicenseNO, TradeLicense);
		clickElement(Search);
		clickElement(Proceed);
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

	public IssueDriverNOCPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

}
