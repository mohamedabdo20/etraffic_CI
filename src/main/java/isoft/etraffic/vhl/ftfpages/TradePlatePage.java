package isoft.etraffic.vhl.ftfpages;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import isoft.etraffic.enums.OldPlateStatus;
import isoft.etraffic.enums.PlateSize;
import isoft.etraffic.enums.PlateSource;
import isoft.etraffic.wrapper.SeleniumWraper;
	
public class TradePlatePage extends SeleniumWraper {

	public TradePlatePage(WebDriver driver) {
		super(driver);
	}

	// Select Plate Elements
	By openSelectPlateBtn = By.id("openSelectPlateBtnHidden");
	By frontPlateSizeLst = By.id("frontPlateSizeId-selectized");
	By backPlateSizeLst = By.id("backPlateSizeId-selectized");
	By plateSourceLst = By.id("plate-source-selectized");
	By selectPlateBtn = By.id("btnChangePlateSubmit");

	// Insurance Details - Issue Screen
	By insuranceRefTxt = By.id("insuranceRefId");
	By insuranceCompLst = By.id("insuranceOrgId-selectized");
	By insuranceTypeLst = By.id("insuranceTypeId-selectized");
	By insuranceExpirtDateTxt = By.id("insuranceExpiryDateId:insuranceExpiryDateIdInputTextCalender");

	// Insurance Details - Renew Screen
	By RenewInsuranceRefTxt = By.id("insuranceRefNoId");
	By RenewInsuranceCompLst = By.id("insuranceCompanyId-selectized");
	By RenewInsuranceTypeLst = By.id("insuranceTypeId-selectized");
	By RenewInsuranceExpirtDateTxt = By.id("insuranceExpiryDateId");

	// Change Plate Elements
	By returenToRTABtn = By.xpath("//*[@id='centers']/a[1]");
	By reservedBtn = By.xpath("//*[@id='centers']/a[2]");
	By lostBtn = By.xpath("//*[@id='centers']/a[3]");
	By stolenBtn = By.xpath("//*[@id='centers']/a[4]");

	By threeMonthsBtn = By.id("threeMonthTag");
	By yesBtn = By.xpath("//*[@id='yesORno']/div[1]/a[1]");

	By frontLongPlateBtn = By.xpath("//*[@id='withoutDubailogo1']/div/a[1]");
	By frontShortPlateBtn = By.xpath("//*[@id='withoutDubailogo1']/div/a[2]");
	By frontSpecialPlateBtn = By.xpath("//*[@id='withoutDubailogo1']/div/a[3]");

	By backLongPlateBtn = By.xpath("//*[@id='withoutDubailogo2']/div/a[1]");
	By backShortPlateBtn = By.xpath("//*[@id='withoutDubailogo2']/div/a[2]");

	By dailyIssueBtn = By.xpath("//*[@id='plate-source']/div[1]/a[1]");
	By reservedPlateBtn = By.xpath("//*[@id='plate-source']/div[1]/a[2]");
	By nominalIssuePlate = By.xpath("//*[@id='plate-source']/div[1]/a[3]");
	By auctionIssueBtn = By.xpath("//*[@id='plate-source']/div[1]/a[4]");
	By firstNominalPlate = By.xpath("//*[@id='nominalPlates']/a[2]");
	By reservedPlateSearchTxt = By.id("changedِAutoValue");

	By dubaiLogoCheckbox = By.id("Dubai_Logo");
	By changePlateSubmitBtn = By.id("btnChangePlateSubmit");

	By proceedTrsBtn = By.id("proceedTrsId");

	public void issueTradePlate(OldPlateStatus oldPlateStatus, PlateSize frontPlate, PlateSize backPlate,
			PlateSource plateSource, boolean dubaiLogo) throws InterruptedException {

		waitForElement(insuranceRefTxt);
		selectPlateSource("ص");
		fillInsuranceDetails(false);
		
		// selectOldPlateStatus(oldPlateStatus);
		//
		// changePlateNumber(frontPlate, backPlate, plateSource, dubaiLogo);
	}

	public void proceedTrs() throws InterruptedException {
		waitForElement(proceedTrsBtn);
		clickElementJS(proceedTrsBtn);
	}

	public void fillInsuranceDetails(boolean renew) throws InterruptedException {
		waitForElement(proceedTrsBtn);
		if (renew) {
			//waitForElement(RenewInsuranceCompLst);
			selectFromFTFList(RenewInsuranceCompLst, "شركة");
			selectFromFTFList(RenewInsuranceTypeLst, "شامل");
			writeToElement(RenewInsuranceRefTxt, "123456789");

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, 10);
			String sysDate = sdf.format(cal.getTime());
			writeToElement(RenewInsuranceExpirtDateTxt, sysDate);

			clickElementJS(proceedTrsBtn);

		} else {
			writeToElement(insuranceRefTxt, "123456789");
			hitTabToElement(insuranceRefTxt);
			Thread.sleep(1000);
			selectFromFTFList(insuranceCompLst, "شركة");
			selectFromFTFList(insuranceTypeLst, "شامل");
			clickButton(insuranceExpirtDateTxt);
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, 10);
			String sysDate = sdf.format(cal.getTime());
			//writeToElement(RenewInsuranceExpirtDateTxt, sysDate);
			writeToElement(insuranceExpirtDateTxt, sysDate);
			clickElementJS(proceedTrsBtn);
		}
	}

	public void changePlateNumber(PlateSize frontPlate, PlateSize backPlate, PlateSource plateSource,
			boolean dubaiLogo) throws InterruptedException {
		clickElement(openSelectPlateBtn);
		Thread.sleep(1000);
		String parentHandle = driver.getWindowHandle();
		switchToFrame("plateSelectionIframeId");

		selectFrontPlate(frontPlate);
		selectBackPlate(backPlate);
		selectNewPlateSource(plateSource);

		if (dubaiLogo) {
			clickElement(dubaiLogoCheckbox);
		}

		clickElement(changePlateSubmitBtn);
		driver.switchTo().window(parentHandle);
	}

	public void selectOldPlateStatus(OldPlateStatus oldPlateStatus) throws InterruptedException {
		switch (oldPlateStatus) {
		case ReturntoRTA:
			clickElement(returenToRTABtn);
			break;
		case Reserved:
			clickElement(reservedBtn);
			clickButton(threeMonthsBtn);
			break;
		case Lost:
			clickElement(lostBtn);
			clickElement(yesBtn);
			break;
		case Stolen:
			clickElement(stolenBtn);
			clickElement(yesBtn);
			break;
		default:
			clickElement(returenToRTABtn);
			break;
		}
	}

	private void selectBackPlate(PlateSize backPlate) throws InterruptedException {
		switch (backPlate) {
		case Long:
			clickElement(backLongPlateBtn);
			break;
		case Short:
			clickElement(backShortPlateBtn);
			break;
		default:
			clickElement(backLongPlateBtn);
			break;
		}
	}

	private void selectFrontPlate(PlateSize frontPlate) throws InterruptedException {
		waitForElement(frontLongPlateBtn);
		switch (frontPlate) {
		case Long:
			clickElement(frontLongPlateBtn);
			break;
		case Short:
			clickElement(frontShortPlateBtn);
			break;
		case Luxury:
			clickElement(frontSpecialPlateBtn);
			break;
		default:
			clickElement(frontLongPlateBtn);
			break;
		}
	}

	private void selectNewPlateSource(PlateSource plateSource) throws InterruptedException {
		switch (plateSource) {
		case Daily:
			clickElement(dailyIssueBtn);
			break;
		case Reserved:
			clickElement(reservedPlateBtn);
			Thread.sleep(1000);
			clickElement(firstNominalPlate);
			break;
		case Nominal:
			clickElement(nominalIssuePlate);
			Thread.sleep(1000);
			clickElement(firstNominalPlate);
			break;
		case Auction:
			clickElement(auctionIssueBtn);
			break;
		default:
			clickElement(dailyIssueBtn);
			break;
		}
	}

	public void selectPlateSource(String plateSource) throws InterruptedException {
		clickElementJS(openSelectPlateBtn);
		Thread.sleep(1000);
		String parentHandle = driver.getWindowHandle();
		waitForElement(By.id("plateSelectionIframeId"));
		switchToFrame("plateSelectionIframeId");
		
		waitForElement(plateSourceLst);
		tryClickElement(plateSourceLst);
		hitBackspaceToElement(plateSourceLst);
		selectFromFTFList(plateSourceLst, plateSource);
		scrolldown();
		clickElementJS(selectPlateBtn);

		Thread.sleep(1000);
		driver.switchTo().window(parentHandle);
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}
