package isoft.etraffic.vhl.sddipages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.SeleniumWraper;

public class ReplacementLostDamagedPlateNumberPages extends SeleniumWraper {

	By confrimAndProceedBtn = By.id("idConfrimAndProceedButton");
	By notAgreeBtn = By.id("notAgree");
	By reasonLst = By.id("reasonId");
	By changePlateDesignBtn = By.partialLinkText("Change plate design");
	
	public void proceedTrs() throws InterruptedException
	{
		waitForElement(reasonLst);
		if(isElementDisplayed(By.xpath("//*[@class='red']")));
			clickElementJS(changePlateDesignBtn);
//		selectFromListByVisibleText(By.xpath("//*[@id='newPlateNumber']/td[2]/select"), "1");
//		selectFromListByVisibleText(By.xpath("//*[@id=\"readMe\"]/div[2]/div[2]/div[3]/ul/li[2]/table/tbody/tr[3]/td[2]/select"), "Long");
//		clickButton(By.xpath("//*[@id='idRowSelect']/td/input"));
//		clickElementJS(By.id("idConfrimAndProceedButton"));
//
//		Thread.sleep(2000);
//		dismissAlert();
//
//		// Thread.sleep(9000);
//		waitForElement(By.id("cboxIframe"));
//		switchToFrame("cboxIframe");// add a Dubai brand logo
//		Thread.sleep(1000);
//		clickButton(notAgreeBtn);
		
//		waitForElement(By.id("dateOfDelivery_<%=deliveryDay%>"));
//		clickElementJS(By.id("dateOfDelivery_<%=deliveryDay%>"));
//		clickElementJS(By.partialLinkText("Confirm"));
	}
	
	public void selectNewPlates() throws InterruptedException
	{
		waitForElement(By.id("addRemoveDubaiLogo"));
		selectFromListByVisibleText(driver.findElements(By.xpath("//*[@name='frontPlateSize']")).get(1), "Short");
		selectFromListByVisibleText(driver.findElements(By.xpath("//*[@name='backPlateSize']")).get(1), "Short");
		Thread.sleep(500);
		clickElementJS(By.partialLinkText("Confirm"));
	}
	
	public ReplacementLostDamagedPlateNumberPages(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}
