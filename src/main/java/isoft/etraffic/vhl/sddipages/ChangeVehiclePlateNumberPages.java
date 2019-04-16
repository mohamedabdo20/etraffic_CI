package isoft.etraffic.vhl.sddipages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.SeleniumWraper;

public class ChangeVehiclePlateNumberPages extends SeleniumWraper {

	//By oldPlateStatu = By.linkText("Vehicle Renewal");
	By oldPlateStatusLst = By.id("oldPlateStatusId");
	By goId = By.id("goId");
	By confirmAndProceedBtn = By.partialLinkText("Confirm & Proceed to Delivery");
	By notAgreeBtn = By.id("notAgree");
	
	public void selectNewPlateSource() throws InterruptedException
	{
		try {selectFromListByVisibleText(By.id("oldPlateStatusId"), "Returned to RTA");}
		catch(Exception e) {}
		
		waitForElement(By.id("selectMyVehiclesId"));
		clickElementJS(By.id("selectMyVehiclesId")); // Step 2 - New Plate Details
		
		Thread.sleep(5000);
		switchToFrame(("cboxIframe"));
		selectFromListByVisibleText(By.id("plateSourceId"), "Daily Distribution");
		Thread.sleep(2000);
		switchToFrame("cboxIframe");
		clickElementJS(goId);
		
//		Thread.sleep(2000);
//		driver.switchTo().alert().dismiss();
		
		tryClickElement(By.partialLinkText("Change plate design"));
		
//		Thread.sleep(5000); 
//		clickElementJS(confirmAndProceedBtn);
//		Thread.sleep(9000);
//		switchToFrame("cboxIframe");//add a Dubai brand logo
//		Thread.sleep(1000);
//		clickButton(notAgreeBtn);
//		
//		Thread.sleep(5000);
	}
	
	public void selectNewPlates() throws InterruptedException
	{
		waitForElement(By.id("addRemoveDubaiLogo"));
		selectFromListByVisibleText(driver.findElements(By.xpath("//*[@name='frontPlateSize']")).get(1), "Short");
		selectFromListByVisibleText(driver.findElements(By.xpath("//*[@name='backPlateSize']")).get(1), "Short");
		Thread.sleep(500);
		clickElementJS(confirmAndProceedBtn);
	}
	public void setDeliveryDetails() throws InterruptedException
	{
		waitForElement(By.id("dateOfDelivery_<%=deliveryDay%>"));
		clickElementJS(By.id("dateOfDelivery_<%=deliveryDay%>"));
		selectFromListByVisibleText(By.xpath("//*[@name='phoneOperatorCode']"), "4");
		driver.findElement(By.xpath("//*[@name='email']")).clear();
		driver.findElement(By.xpath("//*[@name='email']")).sendKeys("aaa@bbb.com");
		driver.findElement(By.xpath("//*[@name='emailConfirm']")).clear();
		driver.findElement(By.xpath("//*[@name='emailConfirm']")).sendKeys("aaa@bbb.com");
		Thread.sleep(1000);
		clickElementJS(By.id("idConfrimAndProceedButton"));
		Thread.sleep(1000);
	}
	
	public ChangeVehiclePlateNumberPages(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}
