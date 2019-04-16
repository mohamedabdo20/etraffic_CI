package isoft.etraffic.drl.ftfpages;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import isoft.etraffic.wrapper.SeleniumWraper;

public class EmployeesYearPlanPage_TC_0065 extends SeleniumWraper {

	By searchBtn = By.id("searchButton");
	By calendarStartDate = By.id("startDateField:startDateFieldInputTextCalender");
	By calendarEndDate = By.id("endDateField:endDateFieldInputTextCalender");
	By firstEmployee = By.id("resultsTable:0:j_idt209");
	By editBtn = By.id("actions1:editButtonId");
	By confirmBtn = By.xpath("//*[@class='confirm']");
	By buildCalendarBtn = By.id("buildCalendarButtonId");
	
	int currentCount;
	List<String> vacationBtniIndex;

	public void setStartEndDate(String startDate, String endDate) throws InterruptedException {
		waitForElement(calendarStartDate);
		writeToElement(calendarStartDate, startDate);
		hitTabToElement(calendarStartDate);
		waitForElement(calendarEndDate);
		writeToElement(calendarEndDate, endDate);
		hitTabToElement(calendarStartDate);
	}

	public void selectFirstEmployee() throws InterruptedException {
		waitForElement(firstEmployee);
		clickElementJS(firstEmployee);
	}

	public void selectVacation(String vacation, String date) throws InterruptedException {
		Actions a = new Actions(driver);
		waitForElement(By.id("workigDayButton"));

		getVactionIndex(vacation);
		currentCount = getButtonCount();// getElementText(By.xpath("//*[@id='eventsControlClass']/table/tbody/tr[" +
										// vacationBtniIndex.get(0) + "]/td["+ vacationBtniIndex.get(1) + "]/button"));
		a.moveToElement(driver.findElement(By.xpath("//*[@id='eventsControlClass']/table/tbody/tr["
				+ vacationBtniIndex.get(0) + "]/td[" + vacationBtniIndex.get(1) + "]/button"))).click().build()
				.perform();

		Thread.sleep(3000);
		waitForElement(By.xpath("//*[@data-date='" + date + "']"));
		a.moveToElement(driver.findElements(By.xpath("//*[@data-date='" + date + "']")).get(1)).click().build()
				.perform();
	}

	public boolean isCountIncremented() throws InterruptedException {
		waitforLoading();
		System.out.println("previousCount = "+ currentCount +"----- currentCount: " + getButtonCount() );
		if (getButtonCount() == (currentCount + 1))
			return true;
		else {
			System.out.println(
					"Vacation count wasn't inceremnted, Date may be was selected before or driver couldn't click.");
			return false;
		}
	}

	private int getButtonCount() throws InterruptedException {
		waitForElement(By.xpath("//*[@id='eventsControlClass']/table/tbody/tr[" + vacationBtniIndex.get(0) + "]/td["
				+ vacationBtniIndex.get(1) + "]/button"));
		return Integer.parseInt(getElementText(By.xpath("//*[@id='eventsControlClass']/table/tbody/tr["
				+ vacationBtniIndex.get(0) + "]/td[" + vacationBtniIndex.get(1) + "]/button")));
	}

	private void getVactionIndex(String vacation) {
		vacationBtniIndex = new ArrayList<String>();

		for (int i = 2; i < 10; i++) {
			if (vacation.contains(
					getElementText(By.xpath("//*[@id='eventsControlClass']/table/tbody/tr[" + i + "]/td[1]/label")))) {
				vacationBtniIndex.add(Integer.toString(i));
				vacationBtniIndex.add(Integer.toString(2));
				System.out.println("Vacancy Found: " + getElementText(
						By.xpath("//*[@id='eventsControlClass']/table/tbody/tr[" + i + "]/td[1]/label")));
				break;
			}
			if (vacation.contains(
					getElementText(By.xpath("//*[@id='eventsControlClass']/table/tbody/tr[" + i + "]/td[3]/label")))) {
				vacationBtniIndex.add(Integer.toString(i));
				vacationBtniIndex.add(Integer.toString(4));
				System.out.println("Vacancy Found: " + getElementText(
						By.xpath("//*[@id='eventsControlClass']/table/tbody/tr[" + i + "]/td[3]/label")));
				break;
			}

		}
	}

	public void clickSearchBtn() throws InterruptedException {
		clickElementJS(searchBtn);
		System.out.println("Wait for Loading --> Click search");
		waitforLoading();
	}

	public void clickEditBtn() throws InterruptedException {
		try {
			clickElementJS(editBtn);
		} catch (Exception e) {
			Thread.sleep(1000);
			clickEditBtn();
		}
	}

	public EmployeesYearPlanPage_TC_0065(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

	public void changeCenter(String date) throws InterruptedException {
		Actions a = new Actions(driver);
		waitForElement(By.id("13Button"));
		a.moveToElement(driver.findElement(By.id("13Button"))).build().perform();;
		
		a.moveToElement(driver.findElements(By.xpath("//*[@data-date='" + date + "']")).get(1)).contextClick().build().perform();
		waitForElement(By.xpath("//*[@id='contextMenu']/li/a"));
		System.out.println("Selected Center: " + getElementText(By.xpath("//*[@id='contextMenu']/li/a")));
		clickElementJS(By.xpath("//*[@id='contextMenu']/li/a"));
		
		waitForElement(confirmBtn);
		clickElementJS(confirmBtn);
		waitforLoading();
		
		waitForElement(buildCalendarBtn);
		clickElementJS(By.xpath("//*[@class='fc-widget-content']"));
		clickElementJS(buildCalendarBtn);
		waitForElement(confirmBtn);
		clickElementJS(confirmBtn);
	}
}
