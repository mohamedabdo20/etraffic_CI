package isoft.etraffic.drl.sddipages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.SeleniumWraper;

public class TransferTrafficFilePage extends SeleniumWraper {
	
	By proceedBtn = By.id("btnGoToStep2");
	By confirmBtn = By.id("btnGoToStep35");
	By centersLst = By.id("centerId");
	By centerTxt = By.xpath("//*[@class='select2-search__field'");
	By collectAllDocumentChBox = By.xpath("//*[@name='confirmCollectAllDocument']");
	
	public void clickProceedBtn() throws InterruptedException {
		waitForElement(proceedBtn);
		clickElementJS(proceedBtn);
	}
	
	public void selectCenter() throws InterruptedException {
//		waitForElement(centersLst);
//		clickElementJS(centersLst);
		Thread.sleep(10000);
		waitForElement(centerTxt);
		writeToElement(centerTxt, "al ahli");
		hitEnterToElement(centerTxt);
	}

	public void clickCollectAllDocumentChbox() throws InterruptedException
	{
		waitForElement(collectAllDocumentChBox);
		clickElementJS(collectAllDocumentChBox);
	}
	
	public TransferTrafficFilePage(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}