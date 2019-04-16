package isoft.etraffic.drl.sddipages;

import java.awt.AWTException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import isoft.etraffic.wrapper.SeleniumWraper;

public class PreviousLicensePage extends SeleniumWraper {

	By countryLst = By.id("issueCountry-selectized");
	By countriesLst = By.xpath("//*[starts-with(@class, 'selectize-input')]");
	By stateLst = By.id("issueState-selectized");
	By issueDateTxt = By.id("licenseIssueDateId");
	By expiryDateTxt = By.id("licenseExpiredDateId");
	By issuePlaceTxt = By.id("issuePlaceId");
	By licenseNOTxt = By.id("licenseNOId");
	By catCodeTxt = By.id("catCodes-selectized");
	By chooseFileBtn = By.id("licenseFileId");
	By submitBtn = By.id("btnSearchByPlate");
	
	String issueDate, expiryDate;
	
	public void addLicense(String country, String state) throws InterruptedException, AWTException {

		waitForElement(countryLst);
		clickElementJS(countryLst);
		selectFromFTFList(countryLst, country);

		if (country.contains("United State"))

		{
			waitForElement(stateLst);
			tryClickElement(stateLst);
			Thread.sleep(1500);
			selectFromFTFList(stateLst, state);
		}

		setIssueAndExpiryDates();
		waitForElement(issueDateTxt);
		tryClickElement(issueDateTxt);
		Thread.sleep(1000);
		write(issueDate);
		
		clickElementJS(expiryDateTxt);
		writeToElementWithoutClear(expiryDateTxt, expiryDate);
		writeToElement(issuePlaceTxt, country);
		writeToElement(licenseNOTxt, "1234687");
		clickElementJS(catCodeTxt);
		selectFirstValue(catCodeTxt);
		hitTabToElement(catCodeTxt);
		
		
		Thread.sleep(3000);
		Actions a = new Actions(driver);
		a.moveToElement(driver.findElement(chooseFileBtn)).click().build().perform();
		clickElementJS(chooseFileBtn);
		Thread.sleep(1000);
		uploadFile("TrailerReg.jpg");
		Thread.sleep(1000);
		
		Thread.sleep(5000);
		clickElementJS(submitBtn);
		
	}

	private void write(String issueDate)
	{
		try {writeToElementWithoutClear(issueDateTxt, issueDate);}
		catch(Exception e) { write(issueDate);}
	}
	private void setIssueAndExpiryDates() {
		SimpleDateFormat sorceDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -24);
		issueDate = sorceDateFormat.format(cal.getTime());
		cal.add(Calendar.MONTH, 48);
		expiryDate = sorceDateFormat.format(cal.getTime());
		System.out.println("issueDate: " + issueDate + "   expiryDate: " + expiryDate);
	}

	public PreviousLicensePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

}
