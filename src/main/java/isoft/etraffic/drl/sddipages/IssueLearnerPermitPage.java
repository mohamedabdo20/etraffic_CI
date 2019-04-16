package isoft.etraffic.drl.sddipages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.SeleniumWraper;

public class IssueLearnerPermitPage extends SeleniumWraper{

	By languageLst = By.id("handbookLanguageId");
	By deliveryMethodLst = By.id("deliveryMethodId");
	
	public void proceedTrns() throws InterruptedException
	{
		waitForElement(languageLst);
		selectFromListByVisibleText(languageLst, "English");
		selectFromListByVisibleText(deliveryMethodLst, "Email");
	}
	
	public IssueLearnerPermitPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

}

	

