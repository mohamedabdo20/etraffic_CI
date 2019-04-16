package isoft.etraffic.drl.ftfpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.SeleniumWraper;

public class ExaminationCenterCapacityPage_TC_0188_0189 extends SeleniumWraper {

	By screenBtn = By.id("examination_center_search-btn-id");
	By centersLst = By.id("centersField:centersFieldSelectOneMenu");
	By searchBtn = By.xpath("//a[contains(@id,'searchButton')]");
	By firstCenterCodeLbl = By.id("resultsTable:0:c2");
	By editBtn = By.id("actions1:editButtonId");
	By searchVehicleBtn = By.id("searchButton");
	By firstStatusLst = By.id("resultsTable:0:status:statusSelectOneMenu");

	public void gotoScreen() throws InterruptedException {
		waitForElement(screenBtn);
		clickElementJS(screenBtn);
	}

	public void searchByCenter(String center) throws InterruptedException {
		waitForElement(centersLst);
		selectFromListByVisibleText(centersLst, center);

		clickElementJS(searchBtn);
		Thread.sleep(1000);
		waitForElement(firstCenterCodeLbl);
		clickElementJS(firstCenterCodeLbl);
		Thread.sleep(1000);
		clickElementJS(editBtn);
	}

	public void clickLightVehicle() throws InterruptedException {
		waitForElement(By.xpath("//*[@class='col-sm-3']"));
		// List<WebElement> list =
		// driver.findElements(By.xpath("//*[@class='col-sm-3']"));

		clickElementJS(By.xpath("//*[@class='row']/div[2]/label/input"));
		clickElementJS(By.xpath("//*[@name='examTypeCheckBox']"));
		Thread.sleep(1000);
		clickElementJS(searchVehicleBtn);
	}

	public boolean firstVehicle(String vehicle) throws InterruptedException {
		Thread.sleep(1000);
		waitForElement(By.id("resultsTable:0:c1"));
		if (getElementText(By.id("resultsTable:0:c1")).contains(vehicle)) {
			System.out.println("First Vehicle Found: " + getElementText(By.id("resultsTable:0:c1")));
			return true;
		} else
			return false;
	}

	public void enableFirstResultStatus() throws InterruptedException {
		String updatedRecord = "updatedRecord: ";
		for (int i = 1; i < 5; i++) {
			updatedRecord += getElementText(By.id("resultsTable:0:c" + i)) + "     ";
		}
		System.out.println(updatedRecord);
		//clickElementJS(By.id("select2-resultsTable:0:status:statusSelectOneMenu-container"));
		selectFromListByVisibleText(firstStatusLst, "فعال");
		clickElementJS(driver.findElements(searchVehicleBtn).get(1));
	}

	public boolean isUpdatedSuccessfully() throws InterruptedException {
		return checkMessageBody("تم تعديل السجل بنجاح");
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

	public ExaminationCenterCapacityPage_TC_0188_0189(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

}
