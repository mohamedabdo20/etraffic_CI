package isoft.etraffic.drl.ftfpages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import isoft.etraffic.wrapper.SeleniumWraper;

public class HoursSchedularPage_TC_0011 extends SeleniumWraper {

	By buildCalendarReadOnlyBtn= By.id("buildCalendarReadOnlyButtonId");
	By proceedTrsBtn = By.id("proceedTrsId");
	By reqTestsFrame = By.id("reqTestsFrame");
	By openHolidayBtn = By.id("openHolidayButtonEditModeId");
	By startDate = By.id("holidayStartDateField:holidayStartDateFieldInputTextCalender");
	By addHolidayBtn = By.id("checkHolidayButtonId");
	
	public void clickBuildCalendarBtn() throws InterruptedException
	{
		waitForElement(buildCalendarReadOnlyBtn);
		Thread.sleep(500);
		clickElementJS(buildCalendarReadOnlyBtn);
		Thread.sleep(3000);
		waitForElement(By.xpath("//*[@class='fc-bgevent']"));
		List<WebElement> l =driver.findElements(By.xpath("//*[@class='fc-business-container']"));
		System.out.println("Size: " + l.size());
		Actions a = new Actions(driver);
		a.moveToElement(l.get(0)).click().build().perform();
	}

	public void selectTestType(String type, String time) throws InterruptedException {
		Thread.sleep(1000);
		waitForElement(By.xpath("//*[@title='"+ type +"']"));
		clickElementJS(By.xpath("//*[@title='"+ type +"']/a"));
		waitforLoading();
		
		clickElementJS(buildCalendarReadOnlyBtn);
		waitforLoading();
		clickElementJS(By.xpath("//*[@data-time='"+ time +"']"));

	}

	public void clickBuildCalendarBtn2() throws InterruptedException
	{
		waitForElement(buildCalendarReadOnlyBtn);
		clickElementJS(buildCalendarReadOnlyBtn);
	}
	
	public void clicOpenHolidayBtn() throws InterruptedException
	{
		waitForElement(openHolidayBtn);
		clickElementJS(openHolidayBtn);
	}
	
	public void addHoliday() throws InterruptedException
	{
		clickBuildCalendarBtn2();
		clicOpenHolidayBtn();
		
		waitForElement(startDate);
		clickElementJS(startDate);
		hitTabToElement(startDate);
		Thread.sleep(1000);
		clickElementJS(addHolidayBtn);
	}
	
	public HoursSchedularPage_TC_0011(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

}
