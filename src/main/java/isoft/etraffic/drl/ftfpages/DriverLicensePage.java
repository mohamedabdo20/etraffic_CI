package isoft.etraffic.drl.ftfpages;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import isoft.etraffic.wrapper.SeleniumWraper;

public class DriverLicensePage extends SeleniumWraper {

	By fieldsframe = By.id("myIframe");
	By TrafficNoTxt = By.xpath("//*[@name='TrafficNo']");
	By searchBtn = By.xpath("//*[@userDefinedClick='doSearch();']");
	By changeStatusBtn = By.xpath("//*[@userDefinedClick='changeLicenseStatus();']");
	By firstRow = By.xpath("//*[@class='TabularCellOver']");

	public void changeStatusToSusbended(String trafficFile) throws InterruptedException {
		waitForElement(fieldsframe);
		String mainWindow = driver.getWindowHandle();
		System.out.println("mainWindow1: "+ mainWindow);
		switchToFrame(fieldsframe);
		writeToElement(TrafficNoTxt, trafficFile);
		hitEnterToElement(TrafficNoTxt);

		switchToWindow(mainWindow);
		Thread.sleep(1000);
		System.out.println("mainWindow2: "+ driver.getWindowHandle());
		switchToWindow(mainWindow);
		waitForElement(fieldsframe);
		switchToFrame(fieldsframe);
		Actions a = new Actions(driver);
		a.moveToElement(driver.findElement(By.xpath("//*[contains(@id, 'VclDescriptionAms__id')]")), 0,10).click().build().perform();
		
		List<WebElement> buttons = driver.findElements(By.xpath("//*[@class='Button']"));
		{ buttons = driver.findElements(By.xpath("//*[@class='Button']"));
		}
		for(int i=0;i<buttons.size(); i++)
		{
			System.out.println(getElementAttributeValue(buttons.get(i), "userDefinedClick"));
		}
		clickElementJS(buttons.get(4));
	}

	public String getTodayDate() {
		SimpleDateFormat todayDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		return todayDateFormat.format(cal.getTime());
	}

	public DriverLicensePage(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}
