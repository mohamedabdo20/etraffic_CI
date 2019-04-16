package  isoft.etraffic.spl.sddipages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import isoft.etraffic.enums.PlateSize;
import isoft.etraffic.wrapper.SeleniumWraper;

public class ChangePlateDesignPages extends SeleniumWraper {

	By duabiLogoChbox = By.xpath("//*[@name='dubaiLogoActionType']");
	By frontPlateLostChbox = By.xpath("//*[@name='frontPlateLost']");
	By backPlateLostChbox = By.xpath("//*[@name='backPlateLost']");
	
	By vehicleRenewalBtn = By.linkText("Vehicle Renewal");
	By vehicleRenewalCb = By.id("cbTncVehicleRenewal");
	By startProcessBtn = By.id("btnStartProcess");
	
	By selectDeliveryMethodBtn = By.id("btnGoToStep2");

	////////////////////////
	

	By txtPlateNumberTxt = By.id("txtPlateNumber");
	By plateCategoryLst = By.xpath("//*[@class='select2 select2-container select2-container--default']");
	By plateCodeLst = By.id("plateCodeIds");
	By lstSearchTxt = By.xpath("/html/body/span/span/span[1]/input");
	By searchPlateBtn = By.id("btnSearchPlateDetails");
	By saveBasketBtn = By.partialLinkText("Save Basket");
	By removeFromBasketBtn = By.partialLinkText("Remove From Basket");

	public void startProcess() throws InterruptedException {
		waitForElement(vehicleRenewalCb);
		clickElementJS(vehicleRenewalCb);
		clickElementJS(startProcessBtn);
	}
//	public void gotoVehicleRenewal() throws InterruptedException {
//		waitForElement(vehicleRenewalBtn);
//		driver.findElement(vehicleRenewalBtn).click();
//	}

	public void selectLostPlates(boolean frontPlateLost,boolean backPlateLost) throws InterruptedException
	{
		if(frontPlateLost)
			clickElementJS(frontPlateLostChbox);
		if(backPlateLost)
			clickElementJS(backPlateLostChbox);
	}
	
	public boolean isSelectDeliveryBtnHidden()
	{
		if(getElementAttributeValue(selectDeliveryMethodBtn, "style").contains("display: none"))
			return true;
		else return false;
			
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
		if (getTotalFees().equals(Integer.toString((Integer.parseInt(totalFees) - Integer.parseInt(firstVehicleFees)))))
			return true;
		else
			return false;
		
	}

	private String getFirstVehicleFees() {
		String s = getElementText(By.xpath("//*[@id='allPageContentId']/table[1]/tbody/tr[2]/td[3]")).substring(0,
				getElementText(By.xpath("//*[@id='allPageContentId']/table[1]/tbody/tr[2]/td[3]")).indexOf(" AED"));
		return s;
		}

	private String getTotalFees() {
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

	public void changePlate(PlateSize frontPlate, PlateSize backPlate, boolean dubaiLogo) throws InterruptedException {
		waitForElement(duabiLogoChbox);
		
		

//		Actions a = new Actions(driver);
//		a.moveToElement(driver.findElement(By.id("btnAddBrandPlate"))).perform();
//		Thread.sleep(1000);


		selectfrontPlateSize(frontPlate);
		selectbackPlateSize(backPlate);

		if(dubaiLogo)
		{
			clickElementJS(duabiLogoChbox);
			Thread.sleep(2000);
			waitForElement(selectDeliveryMethodBtn);
		}
		Thread.sleep(1000);
	}
	
	public boolean checkExtraFees(String expected)
	{
		String actual = getElementText(By.xpath("//*[@class='row tabFirst'][2]/div[2]/p"));
		System.out.println("Actual Changes fees: "+ actual);
		if(expected.equals(actual))
			return true;
		else return false;
	}
	
	private void selectfrontPlateSize(PlateSize frontPlate) throws InterruptedException {
		List<WebElement> frontPlates = driver.findElements(By.xpath("//*[@name='frontPlateSize']"));
		switch (frontPlate) {
		case Long:
			clickElementJS(frontPlates.get(1));
			break;
		case Short:
			clickElementJS(frontPlates.get(2));
			break;
		case Luxury:
			clickElementJS(frontPlates.get(3));
			break;
		default:
			clickElementJS(frontPlates.get(2));
			break;
		}
	}

	private void selectbackPlateSize(PlateSize backPlate) throws InterruptedException {
		switch (backPlate) {
		case Long:
			clickElementJS(driver.findElement(By.id("backPlateId1")));
			break;
		case Short:
			clickElementJS(driver.findElement(By.id("backPlateId2")));
			break;
		default:
			clickElementJS(driver.findElement(By.id("backPlateId1")));
			break;
		}
		Thread.sleep(1000);
	}

	public ChangePlateDesignPages(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}