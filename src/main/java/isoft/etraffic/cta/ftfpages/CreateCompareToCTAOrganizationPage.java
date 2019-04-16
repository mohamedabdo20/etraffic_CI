package isoft.etraffic.cta.ftfpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.SeleniumWraper;

public class CreateCompareToCTAOrganizationPage extends SeleniumWraper {

	public CreateCompareToCTAOrganizationPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	By MainMenu = By.id("menu-trigger");
	By ContractfromMenu = By.id("listCategoryLinksId2_13");
	By ReviewContract = By.xpath("//*[contains(@data-module,'cml')]");
	By ReviewPermits = By.xpath("//*[contains(@data-name,'متابعة التصاريح')]");
	By SwitchFrame = By.id("myIframe");
	By TrafficNo = By.id("trafficFileNoLookupId");
	By TradeLicense = By.id("tradeLicenseID");
	By SaveBTN = By.xpath("//*[contains(@value,'إبحث')]");
	By SelectOneRow = By.xpath("//*[contains(@class,'x-grid3-col x-grid3-cell x-grid3-td-0 x-grid3-cell-first')]");
	By ShowCompare = By.id("showCompareButton");
	By ActionBtn = By.id("action");
	By ForceCompare = By.id("actions1:forceCompareBtn");
	By SelectPreviuosCompare = By.id("resultsTable:0:c1");

	/*public void selectTradeLicense(String TradeLicensefromExcell) {

		writeToElement(TradeLicense, TradeLicensefromExcell);
	}
*/
	public void createCompareOrganizationPage(String TradeLicensefromExcell) throws InterruptedException {
		clickElement(MainMenu);
		Thread.sleep(3000);
		scrollToelement(ContractfromMenu);
		//clickElement(ContractfromMenu);
		clickElement(ReviewContract);
		clickElement(ReviewPermits);
		// waitForElement(SwitchFrame);
		switchToFrame("myIframe");
		writeToElement(TradeLicense, TradeLicensefromExcell);
		/*
		 * writeToElement(TrafficNo, "50052702"); //hitTabToElement(TrafficNo);
		 * hitEnterToElement(TrafficNo); String SwitchWindow = switchToSecondWindow();
		 * Thread.sleep(3000); driver.close(); switchToWindow(SwitchWindow);
		 * Thread.sleep(3000);
		 */
		clickElement(SaveBTN);
		clickElement(SaveBTN);
		clickElement(SelectOneRow);
		clickElement(ShowCompare);
		clickElement(SelectPreviuosCompare);
		clickElement(ActionBtn);
		Thread.sleep(3000);
		clickElement(ForceCompare);
		Thread.sleep(10000);
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}
