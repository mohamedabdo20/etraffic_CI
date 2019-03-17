package isoft.etraffic.vhl.ftfpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import isoft.etraffic.enums.*;
import isoft.etraffic.wrapper.SeleniumWraper;

public class ExportCertificatePage extends SeleniumWraper {

	public ExportCertificatePage(WebDriver driver) {
		super(driver);
	}

	// Expot
	By countriesLst = By.id("exportToCountryId-selectized");
	By oldPlateStatusLst = By.id("oldPlateStatusId");
	By withPlateCheckbox = By.id("withExportPlatesChkId");
	By licenseNumberTxt = By.id("licenseNumber");
	By licenseSourceLst = By.id("licenseSourceId");
	By reservationPeriodLst = By.id("reservationPeriodId");
	By customerReturnPlateLst = By.id("customerReturnPlateOnId");
	By newOwnerTrfTxt = By.id("trafficFileNumber");
	By newOwnerlicenseNo = By.id("licenseNoId");

	// Search by Plate
	By plateCategoryLst = By.id("plateCategoryId:plateCategoryId-selectized");
	By plateCodeLst = By.id("plateCodeId:plateCodeId-selectized");
	By plateNumberTxt = By.id("plateNoId");
	By searchPlateBtn = By.id("searchPlateBtnId");
	By proceedTrsId = By.id("proceedTrsId");
	// Transaction elements
	// By oldPlateStatusLst = By.id("oldPlateStatusId-selectized");

	// Reserved Plate Elements
	// By reservationPeriodLst = By.id("reservationPeriodId-selectized");
	By reservedPlateCategory = By.id("newPlateCategoryId-selectized");
	By selectPlateBtn = By.id("openSelectPlateBtn");
	By openSelectPlateBtn = By.id("openSelectPlateBtn");
	By plateSourceLst = By.id("plate-source-selectized");
	By btnChangePlateBtn = By.id("btnChangePlateSubmit");

	public void proceedTrs(String country, OldPlateStatus oldPlateStatus, boolean withPlates, String licenseNumber)
			throws InterruptedException {
		waitForElement(countriesLst);
		Thread.sleep(1000);
		try {
			selectFromFTFList(countriesLst, country);
		} catch (Exception e) {
			proceedTrs(country, oldPlateStatus, withPlates, licenseNumber);
		}

		clickElementJS(oldPlateStatusLst);
		hitBackspaceToElement(oldPlateStatusLst);
		switch (oldPlateStatus) {
		case ReturntoRTA:
			selectFromListByVisibleText(oldPlateStatusLst, "تعاد إلى مؤسسة الترخيص");
			break;
		case Transfered:
			selectFromListByVisibleText(oldPlateStatusLst, "ينقل");
			break;
		case Reserved:
			selectFromListByVisibleText(oldPlateStatusLst, "يحفظ لدى الإدارة");
			Thread.sleep(500);
			hitTabToElement(oldPlateStatusLst);
			try {
				if (driver.findElement(reservationPeriodLst).isEnabled())
					selectFromListByVisibleText(reservationPeriodLst, "ثلاثة شهور");
				else {removeElementAttribute(driver.findElement(reservationPeriodLst),"disabled");Thread.sleep(500);}
			} catch (Exception e) {
				Thread.sleep(1000);
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
		Thread.sleep(500);
		if (withPlates) {
			clickElement(withPlateCheckbox);
			writeToElement(licenseNumberTxt, licenseNumber);
			hitTabToElement(licenseNumberTxt);
			selectFromListByVisibleText(licenseSourceLst, "دبي");
			Thread.sleep(1000);
		}
		Thread.sleep(1000);
		clickElementJS(proceedTrsId);
	}

	public void clickProceedBtn() throws InterruptedException {
		Thread.sleep(500);
		Actions a = new Actions(driver);
		a.moveToElement(driver.findElement(By.id("proceedTrsId"))).perform();
		clickElementJS(proceedTrsId);
	}

	public void exportToNewOwner(String country, OldPlateStatus oldPlateStatus, boolean withPlates, String newOwnerTrf,
			String licenseNumber) throws InterruptedException {
		waitForElement(newOwnerTrfTxt);
		try {
			writeToElement(newOwnerTrfTxt, newOwnerTrf);
		} catch (Exception e) {
			Thread.sleep(1000);
			writeToElement(newOwnerTrfTxt, newOwnerTrf);
		}
		hitTabToElement(newOwnerTrfTxt);
		Thread.sleep(1000);

		proceedTrs(country, oldPlateStatus, withPlates, licenseNumber);
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}