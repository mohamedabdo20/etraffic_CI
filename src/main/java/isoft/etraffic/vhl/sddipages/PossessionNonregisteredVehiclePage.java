package isoft.etraffic.vhl.sddipages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import isoft.etraffic.wrapper.SeleniumWraper;

public class PossessionNonregisteredVehiclePage extends SeleniumWraper{

	By emirateLst = By.id("emirateSelect");
	By sourceTypeLst = By.id("sourceTypeSelect");
	By sourceNumberTxt = By.xpath("//*[@name='sourceNumber']");
	By sourceIssueDateTxt = By.id("sourceIssueDate_id");
	By vehicleTestTypeLst = By.id("vehicleTestTypeSelect");
	By vhlTypeLst = By.id("vhlTypeSelect");
	By radioBtn = By.xpath("//*[@name='methodOfDelivery']");
	By confrimAndProceedBtn = By.id("confirmAndProceedButton");
	
	public void proceedTrs(String chassis, String sourceDate) throws InterruptedException
	{
		waitForElement(emirateLst);
		selectFromListByVisibleText(emirateLst, "Dubai");
		selectFromListByVisibleText(sourceTypeLst, "Custom Certificate");
		writeToElement(sourceNumberTxt, "123456789");
		writeToElement(sourceIssueDateTxt, sourceDate);
		selectFromListByVisibleText(vehicleTestTypeLst, "New (Agent)");
		setChassis(chassis, false);
		clickByLinkTxt("Verify Information");
		
		setVehicleDetails(chassis);
		clickElementJS(confrimAndProceedBtn);
	}
	
	private void setChassis(String chassis, boolean detailsPage) throws InterruptedException
	{
		for(int i=1; i<18; i++)
		{
			if(i==9 && detailsPage)
				{writeToElement(By.id("chassisNo"+i), Character.toString(chassis.charAt(i-1)));
				acceptAlert();
				}
			else writeToElement(By.id("chassisNo"+i), Character.toString(chassis.charAt(i-1)));
		}
		Thread.sleep(500);
	}
	
	private void setVehicleDetails(String chassis) throws InterruptedException
	{
		waitForElement(By.id("makerLookupId"));
		setChassis(chassis, true);
		clickElementJS(By.id("makerLookupId"));
		waitForElement(By.id("cboxIframe"));
		switchToFrame("cboxIframe");
		//driver.switchTo().frame(0);
		waitForElement(By.xpath("//*[@id='payopt1']/li/table/tbody/tr/td[1]/p/span/em/input"));
		writeToElement(By.xpath("//*[@id='payopt1']/li/table/tbody/tr/td[1]/p/span/em/input"), "KIA");
		clickElement(By.id("searchByMakerId"));
		Thread.sleep(500);
		clickElement(By.xpath("//*[@class='srchrsult']//*[@value='10144']"));
		clickElement(By.xpath("//*[@id='selectMakerId']/div/button/span/em"));

		Actions actions = new Actions(driver);
		actions.sendKeys(Keys.TAB).build().perform();
		Thread.sleep(2000);
		clickElement(By.id("typeDetailsAnchor"));
		waitForElement(By.id("cboxIframe"));
		try{switchToFrame("cboxIframe");}
		catch(Exception e) {Thread.sleep(1000);switchToFrame("cboxIframe");}
		Thread.sleep(1000);
		clickElement(By.xpath("//*[@type='RADIO']"));
		clickElementJS(By.xpath("//*[@onclick='doSelect();']"));
		Thread.sleep(500);
		
		selectFromListByVisibleText(vhlTypeLst, "Light Vehicle");
		clickElementJS(By.id("serrchMakerTypeDetails2Id"));
		Thread.sleep(1000);
		waitForElement(By.id("cboxIframe"));
		switchToFrame("cboxIframe");
		waitForElement(By.xpath("//*[@name='descriptionEn']"));
		writeToElement(By.xpath("//*[@name='descriptionEn']"), "Privacy");
		hitEnterToElement(By.xpath("//*[@name='descriptionEn']"));
		Thread.sleep(500);
		clickElement(By.xpath("//*[@type='RADIO']"));
		clickElementJS(By.xpath("//*[@onclick='doSelect();']"));
		Thread.sleep(500);
		//clickElement(By.xpath("//*[@id='selectCompanyId']/div/button"));
		
		clickElementJS(By.id("countryLookupId"));
		waitForElement(By.id("cboxIframe"));
		switchToFrame("cboxIframe");
		Thread.sleep(500);
		clickElement(By.xpath("//*[@type='RADIO']"));
		clickElementJS(By.xpath("//*[@onclick='doSelect();']"));
		Thread.sleep(500);
		
		writeToElement(By.xpath("//*[@name='unloadedWeight']"), "2500");
		writeToElement(By.id("engineNumber"), "123456789");
		writeToElement(By.xpath("//*[@name='modelYear']"), "2018");
		selectFromListByVisibleText(By.id("fuelSelect"), "Benzene");
		writeToElement(By.xpath("//*[@name='numOfSeats']"), "5");
		writeToElement(By.xpath("//*[@name='numOfDoors']"), "4");
		selectFromListByVisibleText(By.id("color1Select"), "White");
		Thread.sleep(500);
	}
	
	public PossessionNonregisteredVehiclePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}