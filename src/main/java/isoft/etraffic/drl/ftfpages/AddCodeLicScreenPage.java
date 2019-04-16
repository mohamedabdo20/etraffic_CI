package isoft.etraffic.drl.ftfpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.SeleniumWraper;

public class AddCodeLicScreenPage extends SeleniumWraper {

	By addBtn = By.id("actions1:addButtonId");
	By countryLst = By.id("countryList:countryListSelectOneMenu");
	By codeTxt = By.id("code:code");
	By statusLst = By.id("statusList:statusListselectOneDomainPanelSelectOneMenu");
	By saveBtn = By.id("j_idt136:saveButtonId");

	public void add(String code) throws InterruptedException {
		waitForElement(addBtn);
		clickElementJS(addBtn);
		Thread.sleep(1000);
		waitForElement(codeTxt);
		editElementAttributeValue(driver.findElement(codeTxt), "onfocus",
				"KeyBoard1.ConvertToEnglish();");
		writeToElement(codeTxt, code);
		selectFromListByVisibleText(statusLst, "فعال");
		clickElementJS(saveBtn);
	}

	public AddCodeLicScreenPage(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}
