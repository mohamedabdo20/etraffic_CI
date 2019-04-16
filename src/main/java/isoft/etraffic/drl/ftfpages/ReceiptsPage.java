package isoft.etraffic.drl.ftfpages;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import isoft.etraffic.wrapper.SeleniumWraper;

public class ReceiptsPage extends SeleniumWraper {

	By fieldsframe = By.id("myIframe");
	By receiptNoTxt = By.xpath("//*[@name='receiptNo']");
	
	By firstRow = By.xpath("//*[@class='TabularRowOver']");
	By cancelationReasonTxt = By.xpath("//*[@name='cancelationReason']");
	

	public void cancel(String recieptNo) throws InterruptedException {
		waitForElement(fieldsframe);
		String mainWindow = driver.getWindowHandle();
		System.out.println("mainWindow1: "+ mainWindow);
		switchToFrame(fieldsframe);
		writeToElement(receiptNoTxt, recieptNo);
		hitEnterToElement(receiptNoTxt);

		switchToWindow(mainWindow);
		Thread.sleep(1000);
		System.out.println("mainWindow2: "+ driver.getWindowHandle());
		switchToWindow(mainWindow);
		waitForElement(fieldsframe);
		switchToFrame(fieldsframe);
		Actions a = new Actions(driver);
		a.moveToElement(driver.findElement(By.xpath("//*[contains(@id, 'TrsId1ms__id')]")), 0,30).build().perform();
		clickElementJS(firstRow);
		Thread.sleep(3000);
		

		switchToWindow(mainWindow);
		switchToFrame(fieldsframe);
		List<WebElement> buttons = driver.findElements(By.xpath("//*[@class='Button']"));
		{ buttons = driver.findElements(By.xpath("//*[@class='Button']"));
		}
		for(int i=0;i<buttons.size(); i++)
		{
			System.out.println(getElementAttributeValue(buttons.get(i), "userDefinedClick"));
		}
		clickElementJS(buttons.get(6));
		Thread.sleep(1000);
		mainWindow = switchToSecondWindow();
		waitForElement(cancelationReasonTxt);
		writeToElement(cancelationReasonTxt, "cancelationReasonTxt cancelationReasonTxt cancelationReasonTxt");
		clickByLinkTxt("موافق");
		System.out.println("Doooooooooooooooooooooooooone");
	}

	public String getTodayDate() {
		SimpleDateFormat todayDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		return todayDateFormat.format(cal.getTime());
	}

	public ReceiptsPage(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}
