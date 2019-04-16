package isoft.etraffic.drl.ftfpages;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.SeleniumWraper;

public class DistributeExaminersPage_TC_0012 extends SeleniumWraper {

	By examinersManagementBtn = By.id("examiners_management-btn-id");
	By namesLst = By.id("servicesSelectedVoId:servicesSelectedVoIdSelectOneMenu");

	By centersIndicatorsBtn = By.id("centersIndicators-btn-id");
	By statrtDateTxt = By.id("startDateField:startDateFieldInputTextCalender");
	By endDateTxt = By.id("endDateField:endDateFieldInputTextCalender");
	By searchBtn = By.id("searchButton");
	By editBtn = By.id("actions1:editButtonId");

	By examinerDefinitionBtn = By.id("examinerDefinition-btn-id");
	By examinerNamesLst = By.id("examinerNameField:examinerNameFieldSelectOneMenu");
	By firstResultRow = By.id("resultsTable:0:j_idt211");

	public void gotoExaminersManagement() throws InterruptedException {
		waitForElement(examinersManagementBtn);
		clickElementJS(examinersManagementBtn);
	}

	public void gotoCentersIndicators() throws InterruptedException {
		waitForElement(centersIndicatorsBtn);
		clickElementJS(centersIndicatorsBtn);
	}

	public void gotoExaminerDefinition() throws InterruptedException {
		waitForElement(examinerDefinitionBtn);
		clickElementJS(examinerDefinitionBtn);
	}

	public void selectEmployeeName(String name) throws InterruptedException {
		waitForElement(namesLst);
		selectFromListByVisibleText(namesLst, name);
		waitForElement(By.xpath("//*[@id='resultsTable11:0:c3']/span"));
	}

	public void selectExaminerName(String name) throws InterruptedException {
		waitForElement(examinerNamesLst);
		selectFromListByVisibleText(examinerNamesLst, name);
	}

	public void setStartAndEndDate() throws InterruptedException {
		waitForElement(statrtDateTxt);
		writeToElement(statrtDateTxt, getTodayDate());
		hitTabToElement(statrtDateTxt);

		writeToElement(endDateTxt, getTodayDate());
		hitTabToElement(endDateTxt);
	}

	public void clickSearchBtn() throws InterruptedException {
		clickElementJS(searchBtn);
	}

	public void clickEditBtn() throws InterruptedException {
		clickElementJS(editBtn);
	}

	public void selectFirstResultRow() throws InterruptedException {
		waitForElement(firstResultRow);
		clickElementJS(firstResultRow);
	}

	public boolean isFirstRowLightVehicle() throws InterruptedException {
		waitForElement(By.id("resultsTable11:0:c3"));
		if (getElementText(By.xpath("//*[@id='resultsTable11:0:c3']/span")).contains("مركبة خفيفة"))
			return true;
		else
			return false;
	}

	public boolean isFirstCenterShown() throws InterruptedException {
		waitForElement(By.id("centersTable:0:j_idt168"));
		try {
			driver.findElement(By.id("centersTable:0:j_idt168"));
			return true;
		} catch (Exception e) {return false;}
	}

	public void selectTestType(String type, String time) throws InterruptedException {
		Thread.sleep(1000);
		waitForElement(By.xpath("//*[@title='" + type + "']"));
		clickElementJS(By.xpath("//*[@title='" + type + "']/a"));
		waitforLoading();

		waitforLoading();
		clickElementJS(By.xpath("//*[@data-time='" + time + "']"));
	}

	private String getTodayDate() {
		SimpleDateFormat todayDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		return todayDateFormat.format(cal.getTime());
	}

	public DistributeExaminersPage_TC_0012(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}
