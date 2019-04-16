package isoft.etraffic.vhl.ftfpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import isoft.etraffic.enums.*;
import isoft.etraffic.wrapper.SeleniumWraper;

public class ChangeVehicleOwnershipPage extends SeleniumWraper {

	public ChangeVehicleOwnershipPage(WebDriver driver) {
		super(driver);
	}

	// Search by Plate
	By plateCategoryLst = By.id("plateCategoryId:plateCategoryId-selectized");
	By plateCodeLst = By.id("plateCodeId:plateCodeId-selectized");
	By plateNumberTxt = By.id("plateNoId");
	By searchPlateBtn = By.id("searchPlateBtnId");

	// Transaction elements
	By oldPlateStatusLst = By.id("oldPlateStatusId-selectized");
	By createTrsBtn = By.id("createTrsBtnId");

	By yesBtn = By.xpath("//*[@class='confirm']");
	By newPlateCategoryLst = By.id("newPlateCategoryId-selectized");
	
	// Reserved Plate Elements
	By reservationPeriodLst = By.id("reservationPeriodId-selectized");
	By reservedPlateCategory = By.id("newPlateCategoryId-selectized");
	By selectPlateBtn = By.id("openSelectPlateBtn");
	By openSelectPlateBtn = By.id("openSelectPlateBtn");
	By plateSourceLst = By.id("plate-source-selectized");
	By btnChangePlateBtn = By.id("btnChangePlateSubmit");

	public void searchbyPlate(String plateCategory, String plateCode, String plateNumber) throws InterruptedException {
		waitForElement(plateCategoryLst);
		selectFromFTFList(plateCategoryLst, plateCategory);
		Thread.sleep(1000);
		selectFromFTFList(plateCodeLst, plateCode);
		writeToElement(plateNumberTxt, plateNumber);
		clickElementJS(searchPlateBtn);
	}

	public void proceedTrs(OldPlateStatus oldPlateStatus) throws InterruptedException {
		waitForElement(oldPlateStatusLst);
		while (true) {
			try {
				hitEnterToElement(oldPlateStatusLst);
				break;
			} catch (Exception e) {
				Thread.sleep(1000);
			}
		}
		try {clickElementJS(oldPlateStatusLst);hitBackspaceToElement(oldPlateStatusLst);} catch(Exception e) {}
		switch (oldPlateStatus) {
		case ReturntoRTA:
			selectFromFTFList(oldPlateStatusLst, "تعاد إلى مؤسسة الترخيص");
			hitBackspaceToElement(newPlateCategoryLst);
			selectFromFTFList(newPlateCategoryLst, "خصوصي");
			selectPlates("صرف يومي");
			break;
		case Transfered:
			selectFromFTFList(oldPlateStatusLst, "ينقل");
			Thread.sleep(1000);
			break;
		case Reserved:
			selectFromFTFList(oldPlateStatusLst, "يحفظ لدى الإدارة");
			Thread.sleep(1000);
			hitEnterToElement(reservationPeriodLst);
			selectPlates("صرف يومي");
			break;
		case Lost:
			selectFromFTFList(oldPlateStatusLst, "مفقود");
			break;
		case Stolen:
			selectFromFTFList(oldPlateStatusLst, "مسروقة");
			break;
		default:
			selectFromFTFList(oldPlateStatusLst, "تعاد إلى مؤسسة الترخيص");
			break;
		}
		
		clickElementJS(createTrsBtn);
		
//		if (oldPlateStatus.equals(OldPlateStatus.Transfered)) {
//			waitForElement(yesBtn);
//			clickButton(yesBtn);
//		}
	}

	private void selectPlates(String plateSource) throws InterruptedException {
		clickElement(openSelectPlateBtn);
		Thread.sleep(1000);
		String parentHandle = driver.getWindowHandle();
		switchToFrame("plateSelectionIframeId");
		selectFromFTFList(plateSourceLst, plateSource);
		clickElementJS(btnChangePlateBtn);

		Thread.sleep(5000);
		driver.switchTo().window(parentHandle);
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}