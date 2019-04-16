package isoft.etraffic.cta.ftfpages;

import java.awt.AWTException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import io.qameta.allure.Step;
import isoft.etraffic.wrapper.SeleniumWraper;

public class AddBankGuaranteePage extends SeleniumWraper {


	//private By ReviewContract = By.xpath("//*[contains(@data-name,'متابعة العقود')]");
	private By AddGurantee = By.id("actions1:addButtonId");
	private By Deposite = By.id("actionType:actionTypeSelectOneMenu");
	private By NoOFGurantee = By.id("numOfGuarantees:numOfGuarantees");
	private By ReceiptsNo = By.id("receiptNumber:receiptNumber");
	private By Attchment = By.id("attachFile");
	private By SaveBtn = By.id("j_idt208:saveButtonId");      
	
	@Parameters
	@Step("Add Bank Gurantee on Contract")
	public void addbankguaranteepage() throws InterruptedException, AWTException {

	//	clickElement(ReviewContract);
		clickElement(AddGurantee);
		selectFromListByValue(Deposite, "1");
		writeToElement(NoOFGurantee, "100");
		writeToElement(ReceiptsNo, "123456");
		Thread.sleep(3000);
		clickElement(Attchment);
		uploadFile("Lighthouse.jpg");
		clickElement(Attchment);
		uploadFile("Lighthouse.jpg");
		clickElement(SaveBtn);
		
	}

	public AddBankGuaranteePage(WebDriver driver) {
		super(driver);	
	}

	@Override
	public boolean isPageLoaded() {
		return false;
	}
}
