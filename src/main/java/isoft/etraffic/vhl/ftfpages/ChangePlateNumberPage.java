package isoft.etraffic.vhl.ftfpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.enums.*;
import isoft.etraffic.wrapper.SeleniumWraper;

public class ChangePlateNumberPage extends SeleniumWraper {

	public ChangePlateNumberPage(WebDriver driver) {
		super(driver);
	}

	// Change Plate Elements
	By returenToRTABtn = By.xpath("//*[@class='centers old_ways']/a[1]");
	By reservedBtn = By.xpath("//*[@class='centers old_ways']/a[2]/div");
	By lostBtn = By.xpath("//*[@class='centers old_ways']/a[3]");
	By stolenBtn = By.xpath("//*[@class='centers old_ways']/a[4]");
	
	By threeMonthsBtn = By.id("threeMonthTag");
	By yesBtn = By.xpath("//*[@id='yesORno']/div[1]/a[1]");
	
	// Select Plate
	By openSelectPlateBtn = By.id("openSelectPlateBtn");
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

	public void proceedTrs(OldPlateStatus oldPlateStatus, PlateSource plateSource) throws InterruptedException {
		
		waitForElement(reservedBtn);
		changePlateNumber(plateSource); 
		Thread.sleep(1000);
		selectOldPlateStatus(oldPlateStatus);
		clickProceedBtn();
	}
	
	public void clickProceedBtn() throws InterruptedException
	{
		Thread.sleep(3000);
		clickElementJS(proceedTrsBtn);
	}
	
	private void changePlateNumber(PlateSource plateSource) throws InterruptedException
	{
		tryClickElement(openSelectPlateBtn);
		Thread.sleep(1000);
		String parentHandle = driver.getWindowHandle();
		switchToFrame("plateSelectionIframeId");
		
		selectNewPlateSource(plateSource);
		
		clickElement(changePlateSubmitBtn);
		driver.switchTo().window(parentHandle);
	}
	
	public void selectOldPlateStatus(OldPlateStatus oldPlateStatus) throws InterruptedException
	{
		waitForElement(reservedBtn);
		switch (oldPlateStatus) {
		case ReturntoRTA:
			tryClickElement(returenToRTABtn);
			break;
		case Reserved:
			tryClickElement(reservedBtn);
//			Thread.sleep(1000);
//			tryClickElement(threeMonthsBtn);
			break;
		case Lost:
			tryClickElement(lostBtn);
			tryClickElement(yesBtn);
			break;
		case Stolen:
			tryClickElement(stolenBtn);
			tryClickElement(yesBtn);
			break;
		default:
			tryClickElement(returenToRTABtn);
			break;
		}
	}
	
	public void selectBackPlate(PlateSize backPlate) throws InterruptedException
	{
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
	
	public void selectFrontPlate(PlateSize frontPlate) throws InterruptedException
	{
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
	
	private void selectNewPlateSource(PlateSource plateSource) throws InterruptedException
	{
		waitForElement(dailyIssueBtn);
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
	
@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}
