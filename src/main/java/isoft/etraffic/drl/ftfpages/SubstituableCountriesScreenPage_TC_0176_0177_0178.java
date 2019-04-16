package isoft.etraffic.drl.ftfpages;

import java.awt.AWTException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import isoft.etraffic.wrapper.SeleniumWraper;

public class SubstituableCountriesScreenPage_TC_0176_0177_0178 extends SeleniumWraper {

	By addBtn = By.id("actions1:addButtonId");
	By saveBtn = By.id("j_idt159:saveButtonId");
	By countriesLst = By.id("countryList:countryListSelectOneMenu");
	By citiesLst = By.id("cityList:cityListSelectOneMenu");
	By statusLst = By.id("status:statusselectOneDomainPanelSelectOneMenu");
	By frontTemplateUploadBtn = By.id("frontTemplateUploadFileBtn");
	By backTemplateUploadBtn = By.id("backTemplateUploadFileBtn");
	By deleteBtn = By.id("actions1:deleteButtonIdConfirm");
	By firstCountryNameLbl = By.id("resultsTable:0:c1");

	By searchBtn = By.id("searchButton");
	By calendarStartDate = By.id("startDateField:startDateFieldInputTextCalender");
	By calendarEndDate = By.id("endDateField:endDateFieldInputTextCalender");
	By firstEmployee = By.id("resultsTable:0:j_idt209");
	By editBtn = By.id("actions1:editButtonId");
	By confirmBtn = By.xpath("//*[@class='confirm']");
	By buildCalendarBtn = By.id("buildCalendarButtonId");

	int currentCount;
	List<String> vacationBtniIndex;

	public void addNew(String country, String city) throws InterruptedException, AWTException {

		waitForElement(saveBtn);
		selectFromListByVisibleText(countriesLst, country);
		// waitforLoading();
		waitForElement(citiesLst);
		selectFromListByVisibleText(citiesLst, city);
		selectFromListByVisibleText(statusLst, "فعال");

		clickElementJS(frontTemplateUploadBtn);
		String firstWindow = switchToSecondWindow();
		Thread.sleep(1000);
		uploadImage("TrailerReg.jpg");
		switchToWindow(firstWindow);

		clickElementJS(backTemplateUploadBtn);
		firstWindow = switchToSecondWindow();
		Thread.sleep(1000);
		uploadImage("TrailerReg.jpg");
		switchToWindow(firstWindow);
		
		Thread.sleep(1000);
		clickElementJS(saveBtn);
	}

	public boolean isAddedSuccessfully() throws InterruptedException {
		return checkMessageBody("تم إضافه السجل بنجاح");
	}

	public boolean addedSameSuccessfully() throws InterruptedException {
		waitForElement(By.xpath("//*[@id='resultsTable:0:c3']/span"));
		if(getElementText(By.xpath("//*[@id='resultsTable:0:c3']/span")).contains("1"))
			return false;
		else return true;
	}
	
	public boolean deleteAddedRow() throws InterruptedException {
		clickElementJS(firstCountryNameLbl);
		clickElementJS(deleteBtn);
		waitForElement(confirmBtn);
		Thread.sleep(1000);
		clickElementJS(confirmBtn);
		Thread.sleep(1000);
		return checkMessageBody("تم حذف السجل بنجاح");
	}

	private boolean checkMessageBody(String message) throws InterruptedException {
		waitForElement(By.xpath("//*[@data-growl='message']"));
		if (getElementText(By.xpath("//*[@data-growl='message']")).contains(message)) {
			System.out.println(message);
			return true;
		} else {
			System.err.println("Message: " + message + "  isnot shown as expected");
			return false;
		}
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
		System.out.println("previousCount = " + currentCount + "----- currentCount: " + getButtonCount());
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

	public void clickAddNewBtn() throws InterruptedException {
		waitForElement(addBtn);
		clickElementJS(addBtn);
	}

	public SubstituableCountriesScreenPage_TC_0176_0177_0178(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}
