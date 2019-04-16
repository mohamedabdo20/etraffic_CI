package isoft.etraffic.vhl.sddipages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import isoft.etraffic.wrapper.SeleniumWraper;

public class SalesTransactionPages extends SeleniumWraper {

	By sourceTypeLst = By.id("sourceType");
	By sellertTrfFileTxt = By.id("sellerTrfFileNo");
	By sellerDocNoTxt = By.id("sellerIdNo");
	By buyerTrfFileTxt = By.id("buyerTrfFileNo");
	By buyerDocNoTxt = By.id("buyerIdNo");
	By verifyInfoBtn = By.id("confirmAndProceedButton");
	By sellerAddressTxt = By.id("sellerAddressId");
	By buyerAddressTxt = By.xpath("//*[@name='buyerAddress']");
	By confirmInfoBtn = By.id("confirmInfo");
	By plateNumberTxt = By.xpath("//*[@name='plateNo']");
	By plateCatLst = By.id("plateCatValueId");
	By plateCodeLst = By.id("plateCodeValueId");
	// By saveBtn = By.id("confirmAndProceedButton");
	By approveBtn = By.id("approveBtn0");

	public void fillSellerAndBuyerInfo(String sourceType, String sellerTrfFileNo, String sellerDocNo,
			String buyerTrfFileNo, String buyerDocNo) throws InterruptedException {

		waitForElement(sourceTypeLst);
		selectFromListByVisibleText(sourceTypeLst, sourceType);

		writeToElement(sellertTrfFileTxt, sellerTrfFileNo);
		writeToElement(sellerDocNoTxt, sellerDocNo);

		writeToElement(buyerTrfFileTxt, buyerTrfFileNo);
		writeToElement(buyerDocNoTxt, buyerDocNo);
		Thread.sleep(1000);
		clickElementJS(verifyInfoBtn);
	}

	public void fillDetailsInfo() throws InterruptedException {

		waitForElement(By.id("confirmInfo"));

		writeToElement(sellerAddressTxt, "sellerAddressId");

		writeToElement(buyerAddressTxt, "buyerAddress");
		clickElementJS(confirmInfoBtn);
	}

	public void fillVehicleDetails(String plateNumber, String plateCode) throws InterruptedException {

		writeToElement(plateNumberTxt, plateNumber);
		selectFromListByVisibleText(plateCatLst, "Private");
		Thread.sleep(500);
		selectFromListByVisibleText(plateCodeLst, plateCode);
	}

	public void clickSaveAndConfirm() throws InterruptedException {
		Thread.sleep(1000);
		clickElementJS(verifyInfoBtn);
		Thread.sleep(2000);
		acceptAlert();
	}

	public void approveSalesTrns() throws InterruptedException {
		waitForElement(approveBtn);
		clickElementJS(approveBtn);

		Thread.sleep(1000);
		clickElementJS(verifyInfoBtn);
	}
	
	public SalesTransactionPages(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}