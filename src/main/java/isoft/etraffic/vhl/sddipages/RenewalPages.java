package isoft.etraffic.vhl.sddipages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import isoft.etraffic.enums.*;
import isoft.etraffic.wrapper.SeleniumWraper;

public class RenewalPages extends SeleniumWraper {

	By vehicleRenewalBtn = By.linkText("Vehicle Renewal");
	By vehicleRenewalCb = By.id("cbTncVehicleRenewal");
	By startProcessBtn = By.id("btnStartProcess");

	By txtPlateNumberTxt = By.id("txtPlateNumber");
	By plateCategoryLst = By.xpath("//*[@class='select2 select2-container select2-container--default']");
	By plateCodeLst = By.id("plateCodeIds");
	By lstSearchTxt = By.xpath("/html/body/span/span/span[1]/input");
	By searchPlateBtn = By.id("btnSearchPlateDetails");
	By saveBasketBtn = By.partialLinkText("Save Basket");
	By removeFromBasketBtn = By.partialLinkText("Remove From Basket");
	

	public void gotoVehicleRenewal() throws InterruptedException {
		waitForElement(vehicleRenewalBtn);
		driver.findElement(vehicleRenewalBtn).click();
	}

	public void startRenewalProcess() throws InterruptedException {
		waitForElement(vehicleRenewalCb);
		clickElementJS(vehicleRenewalCb);
		clickElementJS(startProcessBtn);
	}
	
	public void saveBasket() throws InterruptedException {
		waitForElement(saveBasketBtn);
		clickElementJS(saveBasketBtn);
		Thread.sleep(1000);
	}
	
	public boolean removeFromBasketFirstVehicle() throws InterruptedException {
		waitForElement(removeFromBasketBtn);
		String firstVehicleFees = getFirstVehicleFees();
		String totalFees = getTotalFees();
		System.out.println("firstVehicleFees: " + firstVehicleFees);
		System.out.println("totalFees: " + totalFees);
		clickElementJS(removeFromBasketBtn);
		Thread.sleep(500);
		acceptAlert();
		Thread.sleep(1000);
		if(getTotalFees().equals(Integer.toString((Integer.parseInt(totalFees)-Integer.parseInt(firstVehicleFees)))))
			return true;
		else return false;
//		waitForElement(By.xpath("//*[@class='cboxIframe']"));
//		switchToFrame(By.xpath("//*[@class='cboxIframe']"));
//		waitForElement(By.id("//*[@id='successfulTransactions']/strong"));
//		while(true)
//			try{}
//		catch (Exception e) {}
	}
	
	private String getFirstVehicleFees()
	{
		String s = getElementText(By.xpath("//*[@id='allPageContentId']/table[1]/tbody/tr[2]/td[3]")).substring(0,getElementText(By.xpath("//*[@id='allPageContentId']/table[1]/tbody/tr[2]/td[3]")).indexOf(" AED"));
		return s;//getElementText(By.xpath("//*[@id='allPageContentId']/table[1]/tbody/tr[2]/td[3]"));
	}
	
	private String getTotalFees()
	{
		return getElementText(By.xpath("//*[@id='allPageContentId']/table[2]/tbody/tr/td[3]/strong"));
	}
	
	public void addtoBasket() throws InterruptedException {
		waitForElement(By.id("btnCreateBasket"));
		clickElementJS((By.id("btnCreateBasket")));
		waitForElement(By.xpath("//*[@class='cboxIframe']"));
		switchToFrame(By.xpath("//*[@class='cboxIframe']"));
		clickElementJS(By.id("confirmAndProceedButton"));
	}
	
	public void fillVehicleInfo(String plateNumber, String plateCode, String plateCategory)
			throws InterruptedException {
		waitForElement(txtPlateNumberTxt);
		writeToElement(txtPlateNumberTxt, plateNumber);
		// List<WebElement> list =
		// driver.findElements(By.xpath("//*[@class='select2-selection__arrow']"));
		// clickElementJS(list.get(0));
		clickElement(plateCategoryLst);
		Thread.sleep(500);
		writeToElement(lstSearchTxt, plateCategory);
		hitEnterToElement(lstSearchTxt);
		Thread.sleep(500);
		clickElement(plateCodeLst);
		Thread.sleep(500);
		writeToElement(lstSearchTxt, plateCode);
		hitEnterToElement(lstSearchTxt);
		Thread.sleep(5000);
		clickElementJS(searchPlateBtn);
	}

	public void changePlate(boolean arabic, PlateSize frontPlate, PlateSize backPlate) throws InterruptedException {
		waitForElement(By.id("btnChangePlateNumber"));
		clickElementJS(By.id("btnChangePlateNumber"));
		waitForElement(By.id("oldPlateStatusId"));

		Actions a = new Actions(driver);
		a.moveToElement(driver.findElement(By.id("oldPlateStatusId"))).perform();
		Thread.sleep(500);
		if (arabic)
			selectFromListByVisibleText(By.id("oldPlateStatusId"), "تعاد إلى مؤسسة الترخيص");
		else
			{selectFromListByVisibleText(By.id("oldPlateStatusId"), "Reserved");
			selectFromListByVisibleText(By.id("oldPlateReservationPeriodInfoId"), "3 Months");}
		// js.executeScript("window.scrollBy(0,200)", "");
		clickElementJS(By.id("dailyPlateRadioId"));
		// Select Plate Sizes
		
		selectfrontPlateSize(frontPlate);
		selectbackPlateSize(backPlate);

		clickElementJS(By.id("btnSaveTransaction"));
		Thread.sleep(2000);
		waitForElement(By.id("confirm-btn"));
		clickElementJS(By.id("confirm-btn"));

		Thread.sleep(3000);
	}

	private void selectfrontPlateSize(PlateSize frontPlate) throws InterruptedException
	{
		List<WebElement> frontPlates = driver.findElements(By.xpath("//*[@name='plateSizeFront']"));
		switch (frontPlate) {
		case Long:
			clickElementJS(frontPlates.get(0));
			break;
		case Short:
			clickElementJS(frontPlates.get(1));
			break;
		case Luxury:
			clickElementJS(frontPlates.get(2));
			break;
		default:
			clickElementJS(frontPlates.get(0));
			break;
		}
	}
	
	private void selectbackPlateSize(PlateSize backPlate) throws InterruptedException
	{
		List<WebElement> backPlates = driver.findElements(By.xpath("//*[@name='plateSizeback']"));
		switch (backPlate) {
		case Long:
			clickElementJS(backPlates.get(0));
			break;
		case Short:
			clickElementJS(backPlates.get(1));
			break;
		case Luxury:
			clickElementJS(backPlates.get(2));
			break;
		default:
			clickElementJS(backPlates.get(0));
			break;
		}
		Thread.sleep(1000);
	}
	
	public RenewalPages(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}