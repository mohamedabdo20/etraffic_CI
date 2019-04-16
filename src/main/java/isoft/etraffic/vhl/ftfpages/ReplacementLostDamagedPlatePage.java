package isoft.etraffic.vhl.ftfpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.enums.OldPlateStatus;
import isoft.etraffic.enums.PlateSize;
import isoft.etraffic.enums.ReplacedPlate;
import isoft.etraffic.wrapper.SeleniumWraper;

public class ReplacementLostDamagedPlatePage extends SeleniumWraper {

	public ReplacementLostDamagedPlatePage(WebDriver driver) {
		super(driver);
	}

	By lostBtn = By.xpath("//*[@id='tab1']/div/div/a[1]");
	By damagedBtn = By.xpath("//*[@id='tab1']/div/div/a[2]");
	By frontLongBtn = By.xpath("//*[@id='tab2']/div/div/a[1]");
	By frontShortBtn = By.xpath("//*[@id='tab2']/div/div/a[2]");
	By frontSpecialBtn = By.xpath("//*[@id='tab2']/div/div/a[3]");
	By backLongBtn = By.xpath("//*[@id='tab3']/div/div/a[1]");
	By backShortBtn = By.xpath("//*[@id='tab3']/div/div/a[2]");
	By dubaiLogoCheckbox = By.id("Dubai_Logo");
	By proceedTrsBtn = By.id("proceedTrsId");

	public void proceedTrs(OldPlateStatus oldPlateStatus, ReplacedPlate replacedPlate, PlateSize plateSize,
			boolean dubaiLogo) throws InterruptedException {
		waitForElement(proceedTrsBtn);

		selectOldPlateStatus(oldPlateStatus);

		selectplates(replacedPlate, plateSize);
		
		if (dubaiLogo)
			clickElement(dubaiLogoCheckbox);

		Thread.sleep(1000);
		clickproceedBtn();
	}

	public void clickproceedBtn() throws InterruptedException {
		tryClickElement(proceedTrsBtn);
	}
	
	public void selectOldPlateStatus(OldPlateStatus oldPlateStatus) throws InterruptedException {
		if (oldPlateStatus.equals(OldPlateStatus.Lost))
			clickButton(lostBtn);
		else
			clickButton(damagedBtn);
		
	}

	private void selectplates(ReplacedPlate replacedPlate, PlateSize plateSize) throws InterruptedException {
		if (replacedPlate.equals(ReplacedPlate.Front)) {
			switch (plateSize) {
			case Long:
				clickButton(frontLongBtn);
				break;
			case Short:
				clickButton(frontShortBtn);
				break;
			case Luxury:
				clickButton(frontSpecialBtn);
				break;
			default:
				clickButton(frontLongBtn);
				break;
			}
		} else {
			switch (plateSize) {
			case Long:
				clickButton(backLongBtn);
				break;
			case Short:
				clickButton(backShortBtn);
				break;
			default:
				clickButton(backLongBtn);
				break;
			}
		}
		
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}
