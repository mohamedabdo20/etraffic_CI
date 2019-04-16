package isoft.etraffic.drl.ftfpages;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.SeleniumWraper;

public class PreviousLicensePage extends SeleniumWraper {

	By addBtn = By.xpath("//*[@title='إضافة']");
	By editBtn = By.xpath("//*[@title='تعديل']");
	By deleteBtn = By.xpath("//*[@data-original-title='حذف']");
	By issueCountry = By.id("issueCountry");
	By countriesLst = By.id("issueCountry-selectized");
	By stateLst = By.id("issueState-selectized");
	By issueState = By.id("issueState");
	By proceedTrsBtn = By.id("proceedTrsId");
	By prevLicenseFrame = By.id("prevLicenseFrame");
	By issueDateTxt = By.id("licenseIssueDateForm");
	By expiryDateTxt = By.id("licenseExpDateForm");
	By issuedByTxt = By.id("issuedBy");
	By licenseNoTxt = By.id("licenseNo");
	By licenseCategoryLst = By.id("licenseCategoryCodeSelectId-selectized");
	String issueDate, expiryDate;
	By licenseDetailsBtn = By.id("licenseDetailsBtnId");
	By confirmBtn = By.xpath("//*[contains(@class,'confirmCancelHeldButton')]");
	By addNewLicenseClassBtn = By.id("addNewLicenseClassServiceBtnId");
	By englishLangBtn = By.id("englishLang");
	By swtichTolicenseCardRearFaceBtn = By.id("swtichTolicenseCardRearFaceId");
	By vehicleClassChkBox = By.xpath("//*[@id='formCats']/div[3]/div/label/input");

	public void addNewLicense(String country, String state) throws InterruptedException {
		waitForElement(prevLicenseFrame);
		switchToFrame(prevLicenseFrame);

		waitForElement(addBtn);
		clickElementJS(addBtn);

		waitForElement(issueCountry);
		Thread.sleep(1000);
		clickElementJS(issueCountry);
		waitForElement(countriesLst);
		tryWriteElement(countriesLst, country);
		hitEnterToElement(countriesLst);

		if (!state.equals("")) {
			waitForElement(issueState);
			Thread.sleep(1000);
			clickElementJS(issueState);
			waitForElement(stateLst);
			selectFromFTFList(stateLst, state);
		}
		setIssueAndExpiryDates();
		waitForElement(issueDateTxt);
		Thread.sleep(1000);
		tryWriteElement(issueDateTxt, issueDate);
		// writeToElementWithoutClear(expiryDateTxt, expiryDate);
		writeToElement(issuedByTxt, country);
		writeToElement(licenseNoTxt, "123456789");
		clickElementJS(licenseCategoryLst);
		hitEnterToElement(licenseCategoryLst);
	}

	public void editLicense() throws InterruptedException {
		waitForElement(prevLicenseFrame);
		switchToFrame(prevLicenseFrame);

		String country = getElementText(By.xpath("//*[@id='data-table-prevLicense']/tbody/tr/td[2]"));
		waitForElement(editBtn);
		clickElementJS(editBtn);

		if (country.equals("الولايات المتحدة")) {
			waitForElement(licenseDetailsBtn);
			clickElementJS(licenseDetailsBtn);
			Thread.sleep(1000);
		}

		setIssueAndExpiryDates();
		waitForElement(issueDateTxt);
		tryClickElement(issueDateTxt);
		Thread.sleep(1000);
		writeToElement(issueDateTxt, issueDate);
		writeToElement(expiryDateTxt, expiryDate);

		selectFirstOption(licenseCategoryLst);
		// try {selectFirstValue(licenseCategoryLst);} catch(Exception e) {}
		waitForElement(proceedTrsBtn);
		clickElementJS(proceedTrsBtn);
	}

	public void deleteLicense() throws InterruptedException {
		waitForElement(prevLicenseFrame);
		String mainWindow = driver.getWindowHandle();
		switchToFrame(prevLicenseFrame);

		waitForElement(deleteBtn);
		clickElementJS(deleteBtn);

		switchToWindow(mainWindow);
		waitForElement(confirmBtn);
		clickElementJS(confirmBtn);
	}

	private void setIssueAndExpiryDates() {
		SimpleDateFormat sorceDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -24);
		issueDate = sorceDateFormat.format(cal.getTime());
		cal.add(Calendar.MONTH, 30);
		expiryDate = sorceDateFormat.format(cal.getTime());
		System.out.println("issueDate: " + issueDate + "   expiryDate: " + expiryDate);
	}

	public void selectVehicleClass() throws InterruptedException {
		clickElementJS(swtichTolicenseCardRearFaceBtn);
		//waitForElement(vehicleClassChkBox);
		Thread.sleep(1000);
		tryClickElement(vehicleClassChkBox);
	}

	public void issueNewLicense() throws InterruptedException {
		waitForElement(addNewLicenseClassBtn);
		tryClickElement(addNewLicenseClassBtn);
		waitForElement(englishLangBtn);
		clickElementJS(englishLangBtn);
		Thread.sleep(500);
		clickElementJS(proceedTrsBtn);
	}

	public void clickProceedBtn() throws InterruptedException 
	{
		waitForElement(proceedTrsBtn);
		Thread.sleep(1000);
		clickElementJS(proceedTrsBtn);
	}

	public PreviousLicensePage(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}