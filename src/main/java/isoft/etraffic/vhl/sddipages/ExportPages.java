package isoft.etraffic.vhl.sddipages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import isoft.etraffic.wrapper.SeleniumWraper;

public class ExportPages extends SeleniumWraper {

	By vehicleRenewalBtn = By.linkText("Vehicle Renewal");
	By vehicleRenewalCb = By.id("cbTncVehicleRenewal");
	By startProcessBtn = By.id("btnStartProcess");
	
	public void proceedTrs() throws InterruptedException
	{
		scrolldown();
		selectFromListByVisibleText(By.id("countryId"), "Jordan");

		Thread.sleep(2000);
		if (driver.findElement(By.id("reservationPeriodId")).isEnabled()) {
			selectFromListByVisibleText(By.id("oldPlateStatusId"), "Returned to RTA");
		}

		waitForElement(By.id("stId"));
		selectFromListByVisibleText(By.id("stId"), "No");
		if (driver.findElement(By.id("soldvehicle")).isDisplayed()) {
			selectFromListByVisibleText(By.id("soldvehicle"), "No");
		}
		
		clickElementJS(By.id("agreeConfirmation"));
		
		try { clickElementJS(By.id("vehicleTestInfo"));}
		catch(Exception e){System.out.println(" No Need for Vehicle Test");}

		Thread.sleep(2000);
		clickElementJS(By.id("confirmAndProceedButton"));

		// Thread.sleep(3000);
		// driver.switchTo().alert().accept();

//		scrolldown();// choose your delivery date and delivery method
//		driver.findElement(By.id("dateOfDelivery_<%=deliveryDay%>")).click();
//		Thread.sleep(6000);
//		clickElementJS(By.id("idConfrimAndProceedButton"));
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
	
	public void startRenewalProcess() throws InterruptedException {
		waitForElement(vehicleRenewalCb);
		clickElementJS(vehicleRenewalCb);
		clickElementJS(startProcessBtn);
	}
	
	public void changePlate() throws InterruptedException
	{
		waitForElement(By.id("btnChangePlateNumber"));
		clickElementJS(By.id("btnChangePlateNumber"));
		waitForElement(By.id("oldPlateStatusId"));

		Actions a = new Actions(driver);
		a.moveToElement(driver.findElement(By.id("btnAddBrandPlate"))).perform();
		Thread.sleep(1000);
		selectFromListByVisibleText(By.id("oldPlateStatusId"), "Returned to RTA");
		clickElementJS(By.id("dailyPlateRadioId"));
		Thread.sleep(1000);
		clickElementJS(By.id("btnSaveTransaction"));
		Thread.sleep(1000);
		waitForElement(By.id("confirm-btn"));
		clickElementJS(By.id("confirm-btn"));

		Thread.sleep(1000);
	}
	public ExportPages(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}
