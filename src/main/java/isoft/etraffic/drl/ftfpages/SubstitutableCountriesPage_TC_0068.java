package isoft.etraffic.drl.ftfpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.SeleniumWraper;

public class SubstitutableCountriesPage_TC_0068 extends SeleniumWraper {

	// Search by Plate
	By firstCountryNamelbl = By.xpath("//*[@id='myTable']/tbody/tr[1]/td[1]");
	By firstCountryProfessionsCountlbl = By.xpath("//*[@id='myTable']/tbody/tr[1]/td[3]");
	By firstCountryDetailsBtn = By.xpath("//*[@data-original-title='التفاصيل']");
	By professionBtn = By.xpath("//*[@data-id='2']");
	By firstProfessionChkbx = By.xpath("//*[@id='myTable']/tbody/tr[7]/td/input");
	By selectBtn = By.xpath("//*[@class='modal-footer']/button");
	By saveBtn = By.id("btnSave");
	By backBtn = By.id("btnBack");
	By successMessagelbl = By.xpath("//*[@class='success-message']");
	By showOtherCountriesLicensesBtn = By.xpath("//*[contains(@href, 'held_license_templates_search.jsf')]");
	By showReportBtn = By.partialLinkText("عرض التقرير");
	String countAfter, countBefore;

	public void changeProfessionsCount() throws InterruptedException {
		waitForElement(firstCountryNamelbl);
		System.out.println("Country to be modified: " + getFirstCountryName());
		System.out.println("Professions Count Brfore: " + getFirstCountryProfessionsCount());
		countBefore = getFirstCountryProfessionsCount();

		clickElementJS(firstCountryDetailsBtn);
		waitForElement(professionBtn);
		clickElementJS(professionBtn);

		waitForElement(firstProfessionChkbx);
		clickElementJS(firstProfessionChkbx);
		clickElementJS(selectBtn);
		Thread.sleep(1000);
		clickElementJS(saveBtn);
		Thread.sleep(1000);

		waitForElement(successMessagelbl);
		while (true) {
			try {
				System.out.println(getElementText(successMessagelbl));
				if (getElementText(successMessagelbl).contains("تمت العملية بنجاح")) {
					break;
				}
			} catch (Exception e) {
				Thread.sleep(1000);
			}

		}
		clickElementJS(backBtn);
		waitForElement(firstCountryProfessionsCountlbl);
		System.out.println("Professions Count After: " + getFirstCountryProfessionsCount());
		countAfter = getFirstCountryProfessionsCount();
	}

	public void openFirstCountryScreen() throws InterruptedException {
		waitForElement(firstCountryNamelbl);
		clickElementJS(firstCountryDetailsBtn);
	}

	public void clickShowOtherCountriesLicenses() throws InterruptedException {
		waitForElement(showOtherCountriesLicensesBtn);
		clickElementJS(showOtherCountriesLicensesBtn);
	}

	public void clickShowReportBtn() throws InterruptedException {
		waitForElement(showReportBtn);
		clickElementJS(showReportBtn);
	}

	public boolean IsCountChanged() {
		if (countAfter.equals(countBefore))
			return false;
		else
			return true;
	}

	public SubstitutableCountriesPage_TC_0068(WebDriver driver) {
		super(driver);
	}

	private String getFirstCountryName() {
		return getElementText(firstCountryNamelbl);
	}

	private String getFirstCountryProfessionsCount() {
		return getElementText(firstCountryProfessionsCountlbl);
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}
