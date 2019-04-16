package isoft.etraffic.drl.ftfpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.SeleniumWraper;

public class LinkCodeWithLicPage extends SeleniumWraper {

	By addBtn = By.id("actions1:addButtonId");
	By countryLst = By.id("countryList:countryListSelectOneMenu");
	By codeLst = By.id("otherCountriesLicenseList:otherCountriesLicenseListSelectOneMenu");
	By categoryLst = By.id("selectCenter");
	By saveBtn = By.id("searchButton");
	
	public void add() throws InterruptedException {
		waitForElement(addBtn);
		clickElementJS(addBtn);
		Thread.sleep(1000);
		waitForElement(countryLst);
		selectFromListByVisibleText(countryLst, "Antarctica");
		waitforLoading();
		waitForElement(codeLst);
		selectFromListByVisibleText(codeLst, "11");
		selectFromListByVisibleText(categoryLst, "مركبة خفيفة");
		Thread.sleep(500);
		clickElementJS(saveBtn);
	}

	public LinkCodeWithLicPage(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}
