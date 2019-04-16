package isoft.etraffic.vhl.ftfpages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import isoft.etraffic.enums.OldPlateStatus;
import isoft.etraffic.wrapper.SeleniumWraper;

public class RegisteredVehicleSelectionPage extends SeleniumWraper {
	public RegisteredVehicleSelectionPage(WebDriver driver) {
		super(driver);
	}

	By registeredRadioBtn = By.id("registeredRadioId");
	By platesNumbers = By.xpath("//*[@class='number']");

	By plateCategoryLst = By.id("filterPlateCategoryCode-selectized");
	By plateNumberTxt = By.id("filterPlateNumber");
	By plateCodeLst = By.id("filterPlateCodeId-selectized");
	By searchBtn = By.xpath("//*[@class='col-sm-3 info-item']/button[2]");
	By transferToCountryLst = By.id("exportToCountryId");
	By oldPlateStatusLst = By.id("oldPlateStatusId");
	By reservationPeriodLst = By.id("reservationPeriodId");
	By customerReturnPlateLst = By.id("customerReturnPlateOnId");
	By newOwnerTrfTxt = By.id("trafficFileNumber");
	By newOwnerlicenseNo = By.id("licenseNoId");

	By proceedTrsBtn = By.xpath("//*[@id='renewalRegistrationDisabledId']/button");

	public void filterVehicles(String plateCategory, String plateCode, String plateNumber) throws InterruptedException {
		waitForElement(registeredRadioBtn);
		clickElementJS(registeredRadioBtn);
		Thread.sleep(1000);
		selectFromFTFList(plateCategoryLst, plateCategory);
		selectPlatecode(plateCode);
		writeToElement(plateNumberTxt, plateNumber);
		clickElement(searchBtn);
		Thread.sleep(1000);
		driver.findElement(By.id("searchResultVehicles:" + 0 + ":renewRegistrationBtn")).click();
	}

	public void transfer(String country, OldPlateStatus oldPlateStatus) throws InterruptedException {
		waitForElement(transferToCountryLst);
		while (true) {
			try {
				selectFromListByVisibleText(transferToCountryLst, country);
				break;
			} catch (Exception e) {
			}
		}

		switch (oldPlateStatus) {
		case ReturntoRTA:
			selectFromListByVisibleText(oldPlateStatusLst, "تعاد إلى مؤسسة الترخيص");
			break;
		case Transfered:
			selectFromListByVisibleText(oldPlateStatusLst, "ينقل");
			break;
		case Reserved:
			selectFromListByVisibleText(oldPlateStatusLst, "يحفظ لدى الإدارة");
			hitTabToElement(oldPlateStatusLst);
			try {
				selectFromListByVisibleText(reservationPeriodLst, "ثلاثة شهور");
			} catch (Exception e) {
			}
			break;
		case Lost:
			selectFromListByVisibleText(oldPlateStatusLst, "مفقود");
			selectFromListByVisibleText(customerReturnPlateLst, "نعم");
			break;
		case Stolen:
			selectFromListByVisibleText(oldPlateStatusLst, "مسروقة");
			selectFromListByVisibleText(customerReturnPlateLst, "نعم");
			break;
		default:
			selectFromListByVisibleText(oldPlateStatusLst, "تعاد إلى مؤسسة الترخيص");
			break;
		}

		 clickElement(proceedTrsBtn);

	}

	public void transferToNewOwner(String country, OldPlateStatus oldPlateStatus, String newOwnerTrf)
			throws InterruptedException {
//		waitForElement(newOwnerTrfTxt);
////		try {
////			clickElement(newOwnerTrfTxt);
////			Thread.sleep(1000);
////			writeToElement(newOwnerTrfTxt, newOwnerTrf);
////		} catch (Exception e) {
////			Thread.sleep(1000);
////			writeToElement(newOwnerTrfTxt, newOwnerTrf);
////		}
//
//		tryClickElement(newOwnerTrfTxt);
//		writeToElement(newOwnerTrfTxt, newOwnerTrf);
//		hitTabToElement(newOwnerTrfTxt);
//		Thread.sleep(1000);
//		transfer(country, oldPlateStatus);
		
		waitForElement(newOwnerTrfTxt);
		try {
			clickElement(newOwnerTrfTxt);
			Thread.sleep(1000);
			writeToElement(newOwnerTrfTxt, newOwnerTrf);
		} catch (Exception e) {
			Thread.sleep(1000);
			writeToElement(newOwnerTrfTxt, newOwnerTrf);
		}

		hitTabToElement(newOwnerTrfTxt);
		Thread.sleep(1000);
		transfer(country, oldPlateStatus);
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

	private void selectPlatecode(String plateCode) {
		driver.findElement(plateCodeLst).sendKeys(plateCode);
		driver.findElement(plateCodeLst).sendKeys(Keys.chord(Keys.BACK_SPACE));
		driver.findElement(plateCodeLst).sendKeys(Keys.chord(Keys.ARROW_RIGHT));
		driver.findElement(plateCodeLst).sendKeys(Keys.chord(Keys.BACK_SPACE));
		driver.findElement(plateCodeLst).sendKeys(Keys.chord(Keys.RETURN));
	}
}
