package isoft.etraffic.drl.ftfpages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import isoft.etraffic.wrapper.SeleniumWraper;

public class EmployeeDailyPlanPage_TC_0013_0014 extends SeleniumWraper {

	By centerLst = By.id("select2-centersField:centersFieldSelectOneMenu-container");
	By buildCalendarBtn = By.id("buildCalendarButtonId");
	By confirmBtn = By.xpath("//*[@class='confirm']");

	public void buildCalendarPlan() throws InterruptedException {

	}

	public void selectPeriods() throws InterruptedException {
		waitForElement(centerLst);
//		List<WebElement> list = driver.findElements(By.xpath("//*[@class='fc-bgevent-container']"));
//		System.out.println("Size= " + list.size());
		List<WebElement> list2 = driver.findElements(By.xpath("//*[@class='fc-bgevent']"));
		System.out.println("Size= " + list2.size());
		Actions a = new Actions(driver);
		for (int i = list2.size()-7; i < list2.size(); i++) {
			a.moveToElement(list2.get(i)).click().build().perform();
		}
	}

	public void select1Period() throws InterruptedException {
		waitForElement(centerLst);
		List<WebElement> list2 = driver.findElements(By.xpath("//*[@class='fc-bgevent']"));
		System.out.println("Size= " + list2.size());
		Actions a = new Actions(driver);
		a.moveToElement(list2.get(111)).click().build().perform();
	}
	
	public void select1Period(int index) throws InterruptedException {
		waitForElement(centerLst);
		List<WebElement> list2 = driver.findElements(By.xpath("//*[@class='fc-bgevent']"));
		System.out.println("Size= " + list2.size());
		Actions a = new Actions(driver);
		index*=7;
		a.moveToElement(list2.get(index)).click().build().perform();
	}
	
	public void clickAbsentBtn() throws InterruptedException
	{
		waitForElement(centerLst);
		List<WebElement> attendenceStatusList = driver.findElements(By.xpath("//i[contains(@onclick,'PrepareExaminerConfirmDialog')]"));
		System.out.println("Size= " + attendenceStatusList.size());
		int firstAbsetEmployeeIndex = 0;
		for(int i=0; i<attendenceStatusList.size(); i++)
		{
			if(getElementAttributeValue(attendenceStatusList.get(i), "title").contains("غائب"))
			{
				firstAbsetEmployeeIndex = i;
				break;
			}
		}
		select1Period(firstAbsetEmployeeIndex);
		attendenceStatusList = driver.findElements(By.xpath("//i[contains(@onclick,'PrepareExaminerConfirmDialog')]"));
		System.out.println("Size= " + attendenceStatusList.size() + "  -- firstAbsetEmployeeIndex: " + firstAbsetEmployeeIndex);
		Thread.sleep(1000);
		Actions a = new Actions(driver);
		a.moveToElement(attendenceStatusList.get(firstAbsetEmployeeIndex)).click().build().perform();
		Thread.sleep(500);
		clickConfirmBtn();
	}
	
	public void clickBuildCalendarBtn() throws InterruptedException {
		clickElementJS(buildCalendarBtn);
		clickConfirmBtn();
	}

	private void clickConfirmBtn() throws InterruptedException {
		waitForElement(confirmBtn);
		clickElementJS(confirmBtn);
	}

	public EmployeeDailyPlanPage_TC_0013_0014(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}
