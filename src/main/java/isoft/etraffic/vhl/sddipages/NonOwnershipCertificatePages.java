package isoft.etraffic.vhl.sddipages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.SeleniumWraper;

public class NonOwnershipCertificatePages extends SeleniumWraper {

	By vehicleRenewalBtn = By.linkText("Vehicle Renewal");
	By vehicleRenewalCb = By.id("cbTncVehicleRenewal");
	By confrimAndProceedBtn = By.id("idConfrimAndProceedButton");
	
	public void proceedTrs() throws InterruptedException
	{
		scrolldown();// Confirm & Proceed to Delivery Method
		selectFromListByVisibleText(By.xpath("//*[@id=\"allPageContentId\"]/div/div[2]/ul/li/table/tbody/tr/td[2]/select"), "Department of Economic Development");
		Thread.sleep(1000);
		clickElementJS(confrimAndProceedBtn);
		
//		scrolldown();// choose your delivery date and delivery method
//		driver.findElement(By.xpath("//*[@id=\"readMe\"]/div[2]/div/div/div[1]/div/ul/li[1]/input")).click();
//		Thread.sleep(6000);
//		clickElementJS(confrimAndProceedBtn);
	}
	
	public NonOwnershipCertificatePages(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}
