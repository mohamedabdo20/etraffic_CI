package isoft.etraffic.drl.sddipages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.SeleniumWraper;

public class ReissueLearningPermitPage extends SeleniumWraper {
	
	By proceedBtn = By.id("btnGoToStep2");
	By confirmBtn = By.id("btnGoToStep35");
	By collectAllDocumentChBox = By.xpath("//*[@name='confirmCollectAllDocument']");
	
	public void clickProceedBtn() throws InterruptedException {
		waitForElement(proceedBtn);
		clickElementJS(proceedBtn);
	}
	
	public void clickConfirmBtn() throws InterruptedException {
		waitForElement(confirmBtn);
		clickElementJS(confirmBtn);
	}

	public void clickCollectAllDocumentChbox() throws InterruptedException
	{
		waitForElement(collectAllDocumentChBox);
		clickElementJS(collectAllDocumentChBox);
	}
	
	public ReissueLearningPermitPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}