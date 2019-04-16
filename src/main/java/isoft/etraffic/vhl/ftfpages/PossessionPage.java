package isoft.etraffic.vhl.ftfpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import isoft.etraffic.wrapper.SeleniumWraper;
import isoft.etraffic.enums.*;

public class PossessionPage extends SeleniumWraper {
	public PossessionPage(WebDriver driver) {
		super(driver);
	}

	By oldPlateStatusLst = By.id("oldPlateStatusId");
	By reservationPeriodLst = By.id("reservationPeriodId");
	By newOwnerTrfFileTxt = By.id("trafficFileNoId");
	By proceedTrsBtn = By.id("proceedTrsId");

	public void proceedTrs(OldPlateStatus oldPlateStatus, boolean changeOwnership, String newOwner)
			throws InterruptedException {

		waitForElement(oldPlateStatusLst);
		try {
			selectFromFTFList(oldPlateStatusLst, "يحفظ لدى الإدارة");
		} catch (Exception e) {
			Thread.sleep(1000);
			proceedTrs(oldPlateStatus, changeOwnership, newOwner);
		}
		switch (oldPlateStatus) {
		case ReturntoRTA:
			selectFromFTFList(oldPlateStatusLst, "تعاد إلى مؤسسة الترخيص");
			break;
		case Reserved:
			selectFromFTFList(oldPlateStatusLst, "يحفظ لدى الإدارة");
			hitTabToElement(oldPlateStatusLst);
			hitArrowDownToElement(reservationPeriodLst);
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

		if (changeOwnership) {
			writeToElement(newOwnerTrfFileTxt, newOwner);
			hitTabToElement(newOwnerTrfFileTxt);
			Thread.sleep(1000);
		}
		clickElementJS(proceedTrsBtn);
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

}
