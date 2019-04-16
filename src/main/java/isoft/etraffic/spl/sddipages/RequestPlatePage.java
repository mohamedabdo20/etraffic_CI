package  isoft.etraffic.spl.sddipages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import isoft.etraffic.wrapper.SeleniumWraper;

public class RequestPlatePage extends SeleniumWraper {


	By trafficFileTextSelector = By.name("trafficNo");
	By loginBtnSelector = By.name("Login");
	By Licenses = By.partialLinkText("Go to Licensing");
	By SpecialPlates = By.id("allocateAuctionId");
	By SpecialPlate = By.partialLinkText("Special Plate Service");
	By MyPlate = By.partialLinkText("Buy Plate");
	By plateInfoURLSelector = By.id("plateTabId");
	By plateCode = By.id("plateCodeId");
	By plateNo1 = By.id("txtDigit1");
	By plateNo2 = By.id("txtDigit2");
	By plateNo3 = By.id("txtDigit3");
	By plateNo4 = By.id("txtDigit4");
	By plateNo5 = By.id("txtDigit5");
	By searchCriteriaBanLocator = By.id("searchButtonId");
	By plateDetailsBtnLocator = By.className("plate-details");
	By buyPlateBtnLocator = By.id("newButton");
	By vehicleRenewalChkboxLocator = By.id("cbTncVehicleRenewal");
	By confirmBtnLocator = By.id("btnStartProcess");
	By confirmAndProceedBtn = By.id("idConfrimAndProceedButton");
	By choosedateLocator = By.id("txtDeliveryDateCourier");
	By calendarIconSelector = By.xpath("//*[@id='dp1']/span/span");
	By calendarDateValueSelector = By.xpath("//*[@id='dp1']/div/ul/li[1]/div/div[1]/table/tbody/tr[4]/td[3]");
	By contactnameLocator = By.id("shipmentContactName");
	By emirateLocator = By.name("shipmentEmirate");
	By areaLocator = By.id("shipmentArea");
	By adresslocator1 = By.id("shipmentAddress1");
	By adresslocator2 = By.id("shipmentAddress2");
	By companyname = By.id("shipmentCompanyName");
	By phoneNolocator = By.id("phoneNumber");
	By mobileNo = By.id("mobileNumber");
	By email = By.id("email");
	By emailconfirm = By.id("emailConfirm");
	By Pobox = By.id("newPoBoxNumber");
	By Confirmdelivery = By.id("btnGoToStep31");
	By Paymentmethod = By.id("payButtonId");
	By PaymentBtn = By.id("btnGoToStep4");
	
	
	public void requestPlatePage() throws InterruptedException {
		

		waitForElement(By.partialLinkText("Special Plate Service"));
		clickElementJS(By.partialLinkText("Special Plate Service"));
		Thread.sleep(5000);
		clickElement(plateInfoURLSelector);
		selectFromListByValue(plateCode, "79");
		writeToElement(plateNo1, "3");
		writeToElement(plateNo2, "4");
		writeToElement(plateNo3, "9");
		writeToElement(plateNo4, "6");
		writeToElement(plateNo5, "4");
		waitForElement(searchCriteriaBanLocator);
		clickElement(searchCriteriaBanLocator);
		waitForElement(buyPlateBtnLocator);
		tryClickElement(buyPlateBtnLocator);
	}
		//Thread.sleep(3000);
		//clickElement(vehicleRenewalChkboxLocator);
		//clickElement(confirmBtnLocator);
		
		public void confirmProcess() throws InterruptedException {
			waitForElement(confirmAndProceedBtn);
			tryClickElement(confirmAndProceedBtn);
		}
		
	

	
	
	
	
	
	
	
	
	
	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

	public RequestPlatePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}


}
