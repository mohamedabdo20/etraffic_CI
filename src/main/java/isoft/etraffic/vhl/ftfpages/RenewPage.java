package isoft.etraffic.vhl.ftfpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.enums.OldPlateStatus;
import isoft.etraffic.enums.PlateSource;
import isoft.etraffic.wrapper.SeleniumWraper;

public class RenewPage extends SeleniumWraper {

	public RenewPage(WebDriver driver) {
		super(driver);
	}

	// Change Plate Elements
	By changePlateNumberBtn = By.id("changePlateNumberButton");
	By changePlateFrame = By.id("changePlateNumberIframeId");
	By frontPlateLst = By.id("frontPlateSizeId");
	By backPlateLst = By.id("backPlateSizeId");
	By oldPlateStatusLst = By.id("oldPlateStatusId");
	By reservationPeriodLst = By.id("reservationPeriodId");
	By platesourceLst = By.id("plate-source");
	By retrievePlateToCustomerLst = By.id("retrievePlateToCustomerId");
	By changePlateSubmitBtn = By.id("btnChangePlateSubmit");
	By vehicleRenewalBtn = By.id("renewalRegistrationBtnId");
	By addDubaiLogoBtn = By.id("removeDubaiPlateButton");
	By addDubaiLogoFrameBtn = By.id("btnRemoveDubaiPlateSubmit");
	By firstPreservedPlateBtn = By.xpath("//*[@id='preservedPlatesId']/div/a");
	
	By createTrsBtn = By.id("createTrsBtnId");
	
	public void proceedTrs() throws InterruptedException {
		waitForElement(changePlateNumberBtn);
		System.out.println("Found");
//		refresh();
		Thread.sleep(1000);
		String firstWindow = switchToSecondWindow();
		driver.close();
		switchToWindow(firstWindow);
//		System.out.println("Afal w rege3");
//		refresh();
//		Thread.sleep(1000);
		//System.out.println("da5el 3l try");
		tryClickElement(vehicleRenewalBtn);
	}
	
	public void changePlateNumber(OldPlateStatus oldPlateStatus, PlateSource  plateSource, boolean returnPlate) throws InterruptedException
	{
		//waitForElement(changePlateNumberBtn);
		String firstWindow = switchToSecondWindow();
		closeCurrentWindow();
		switchToWindow(firstWindow);
		
		waitForElement(changePlateNumberBtn);
		clickElementJS(changePlateNumberBtn);
		String parentHandle = driver.getWindowHandle();
		switchToFrame("changePlateNumberIframeId");
//		waitForElement(frontPlateLst);
//		switch (frontPlate) {
//		case Long:
//			selectFromListByVisibleText(frontPlateLst, "طويل");
//			break;
//		case Short:
//			selectFromListByVisibleText(frontPlateLst, "قصير");
//			break;
//		case Luxury:
//			selectFromListByVisibleText(frontPlateLst, "فخمة");
//			break;
//		default:
//			selectFromListByVisibleText(frontPlateLst, "طويل");
//			break;
//		}
//		hitTabToElement(frontPlateLst);
//		Thread.sleep(1000);
//		waitForElement(backPlateLst);
//		switch (backPlate) {
//		case Long:
//			hitArrowDownToElement(backPlateLst);
//			break;
//		case Short:
//			hitArrowDownToElement(backPlateLst);
//			hitArrowDownToElement(backPlateLst);
//			break;
//		default:
//			selectFromListByVisibleText(frontPlateLst, "طويل");
//			break;
//		}
		Thread.sleep(1000);
		waitForElement(oldPlateStatusLst);
		if(oldPlateStatus.equals(OldPlateStatus.ReturntoRTA))
			selectFromListByVisibleText(oldPlateStatusLst, "تعاد إلى مؤسسة الترخيص");
		if(oldPlateStatus.equals(OldPlateStatus.Reserved)) {
			selectFromListByVisibleText(oldPlateStatusLst, "يحفظ لدى الإدارة");
			selectFromListByVisibleText(reservationPeriodLst, "3 أشهر");
		}
		
		selectPlateSource(plateSource);
		
		if(returnPlate)
			selectFromListByVisibleText(retrievePlateToCustomerLst, "نعم");
		
		driver.switchTo().window(parentHandle);
		clickButton(changePlateSubmitBtn);
		
		Thread.sleep(3000);
	}
	
	public void addDubaioLogo() throws InterruptedException
	{
		waitForElement(addDubaiLogoBtn);
		clickElementJS(addDubaiLogoBtn);
		waitForElement(addDubaiLogoFrameBtn);
		clickElementJS(addDubaiLogoFrameBtn);
		Thread.sleep(1000);
	}
	
	public void clickproceedBtn() throws InterruptedException
	{
		waitForElement(vehicleRenewalBtn);
		clickElementJS(vehicleRenewalBtn);
	}
	
	private void selectPlateSource(PlateSource plateSource) throws InterruptedException
	{
		switch (plateSource) {
		case Daily:
			selectFromListByVisibleText(platesourceLst, "صرف يومي");
			break;
		case Preserved:
			selectFromListByVisibleText(platesourceLst, "لوحة محفوظة");
			waitForElement(firstPreservedPlateBtn);
			clickElementJS(firstPreservedPlateBtn);
			break;
		default:
			selectFromListByVisibleText(platesourceLst, "صرف يومي");
			break;
		}
			
	}
	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}
