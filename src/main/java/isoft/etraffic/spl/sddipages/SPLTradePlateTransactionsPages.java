package  isoft.etraffic.spl.sddipages;

import java.awt.AWTException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import isoft.etraffic.wrapper.SeleniumWraper;

public class SPLTradePlateTransactionsPages extends SeleniumWraper {

	String firstWindow;
	By gotoPaymentNextBtn = By.xpath("//*[@onclick='goToNext();']");
	By nextBtn = By.id("NextButton");
	By gotoContactInfoNextBtn = By.id("nextButton");
	By gotoDeliveryDateNextBtn = By.id("nextButtonId");
	By acceptBtn = By.xpath("//*[@value='Accept']");
	By addBtn = By.id("addButton");
	By firstPackageRdBtn = By.xpath("//*[@type='RADIO']");
	By selectPlatePackageBtn = By.xpath("//*[@onclick='doSelect();']");
	By tradeLicenseNoTxt = By.xpath("//*[@name='tradeLicenseNo']");
	By tradeLicenseAttchmentBtn = By.id("tradeLicenseAttchmentId");
	By tradeLicenseAttchmentBtn2 = By.xpath("//*[@name='tradeLicenseAttchment']");
	By firstAvlDateRdBtn = By.xpath("//*[@name='dateOfDeliveryRadio']");
	By finishBtn = By.xpath("//*[@value='Finish']");

	// Shipment Info
	By shipmentContactNameTxt = By.xpath("//*[@name='shipmentContactName']");
	By shipmentAddressTxt = By.xpath("//*[@name='shipmentAddress']");
	By shipmentphoneNumberTxt = By.xpath("//*[@name='phoneNumber']");
	By shipmentEmirateLst = By.xpath("//*[@name='shipmentEmirate']");
	By shipmentAreaLst = By.xpath("//*[@name='shipmentArea']");
	By shipmentPOBoxNoTxt = By.xpath("//*[@name='shipmentPOBoxNo']");
	//////////////////////////
	// Payment
	By payFeesBtn = By.xpath("//*[@onclick='payFees();']");

	// purchasingType
	By purchasingTypeLst = By.xpath("//*[@name='purchasingType']");
	By searchOnAvlPlateBtn = By.xpath("//*[@onclick='doSearch();']");
	By plateBuyPackageBtn = By.xpath("//*[@value='Buy Package']");

	// Change Ownership
	By plateNumberTxt = By.xpath("//*[@name='plateNumber']");
	By searchPlateBtn = By.id("searchButton");
	By selectActionBtn = By.partialLinkText("Select Action");
	By passwordTxt = By.xpath("//*[@name='password']");
	By validateBtn = By.xpath("//*[@name='validateButton']");
	By acceptBtnChngOwnerBtn = By.id("acceptButtonEnId");
	By newOwnerTrafficFileTxt = By.xpath("//*[@name='newOwnerTrafficFile']");
	By sellingAmountTxt = By.xpath("//*[@name='sellingAmount']");
	By gotoDeliveryInfoBtn = By.id("nextId");
	By gotoPaymentBtn = By.id("idNextButton");

	// Bid
	By bidBtn = By.id("bidButton");

	public void switchtoServiceWindow() throws InterruptedException {
		Thread.sleep(1000);
		firstWindow = switchToSecondWindow();
	}

	public void switchtoParentWindow() throws InterruptedException {
		Thread.sleep(2000);
		driver.close();
		switchToWindow(firstWindow);
	}
	
	public void acceptTermsAndConditions() throws InterruptedException {
		scrollToelement(nextBtn);
		clickButton(nextBtn);
		waitForElement(acceptBtn);
		clickButton(acceptBtn);
	}

	public void selectPurchasingType(String purchasingType) throws InterruptedException {
		waitForElement(purchasingTypeLst);
		selectFromListByVisibleText(purchasingTypeLst, purchasingType);
		clickButton(searchOnAvlPlateBtn);

		waitForElement(plateBuyPackageBtn);
		clickElementJS(plateBuyPackageBtn);

		acceptTermsAndConditions();

		waitForElement(By.xpath("//*[@onclick='doNext();']"));
		clickElementJS(By.xpath("//*[@onclick='doNext();']"));
	}

	public void selectPlate() throws InterruptedException {
		waitForElement(plateNumberTxt);
		// writeToElement(plateNumberTxt, plateNmber);
		//
		// clickElementJS(searchPlateBtn);
		// Thread.sleep(500);
		clickElementJS(selectActionBtn);
		waitForElement(By.partialLinkText("Change Plate Ownership"));
		clickElementJS(By.partialLinkText("Change Plate Ownership"));
	}

	public void setPassword(String password) throws InterruptedException {
		waitForElement(By.id("cboxIframe"));
		switchToFrame("cboxIframe");

		writeToElement(passwordTxt, password);
		clickElementJS(validateBtn);

		waitForElement(acceptBtnChngOwnerBtn);
		clickElementJS(acceptBtnChngOwnerBtn);
	}

	public void setNewOwnerInfo(String newOwnerTrafficFile) throws InterruptedException {
		waitForElement(newOwnerTrafficFileTxt);
		writeToElement(newOwnerTrafficFileTxt, newOwnerTrafficFile);
		hitTabToElement(newOwnerTrafficFileTxt);
		Thread.sleep(500);
		writeToElement(sellingAmountTxt, "15000");
		clickElementJS(gotoDeliveryInfoBtn);

		waitForElement(gotoPaymentBtn);
		clickElementJS(gotoPaymentBtn);
	}

	public void addPlatePackage() throws InterruptedException {

		waitForElement(addBtn);
		clickElementJS(addBtn);
		waitForElement(By.id("cboxIframe"));
		switchToFrame("cboxIframe");
		waitForElement(firstPackageRdBtn);
		clickElementJS(firstPackageRdBtn);
		clickElementJS(selectPlatePackageBtn);
		Thread.sleep(500);
	}

	public void addTradeLicenseInfo(String tradeLicenseNo) throws InterruptedException, AWTException {
		writeToElement(tradeLicenseNoTxt, tradeLicenseNo);
		clickButton(tradeLicenseAttchmentBtn);
		Thread.sleep(2000);
		uploadImage("TrailerReg.png");
		Thread.sleep(500);
		clickElementJS(gotoContactInfoNextBtn);
	}

	public void addTradeLicenseInfo2(String tradeLicenseNo) throws InterruptedException, AWTException {
		writeToElement(tradeLicenseNoTxt, tradeLicenseNo);
		clickButton(tradeLicenseAttchmentBtn2);
		Thread.sleep(2000);
		uploadImage("TrailerReg.png");
		Thread.sleep(500);
		clickElementJS(gotoContactInfoNextBtn);
	}

	public void setShipmentContactDetails() throws InterruptedException {
		waitForElement(shipmentContactNameTxt);
		writeToElement(shipmentContactNameTxt, "firstName lastName");
		writeToElement(shipmentAddressTxt, "shipment Address shipment Address");
		writeToElement(shipmentphoneNumberTxt, "054599999");
		selectFromListByVisibleText(shipmentEmirateLst, "Dubai");
		selectFromListByVisibleText(shipmentAreaLst, "Al Barsha");
		writeToElement(shipmentPOBoxNoTxt, "1234");
		clickElementJS(gotoDeliveryDateNextBtn);

		waitForElement(firstAvlDateRdBtn);
		clickElementJS(firstAvlDateRdBtn);
		clickElementJS(gotoPaymentNextBtn);
	}

	public void clickPayFeesBtn() throws InterruptedException {
		waitForElement(payFeesBtn);
		clickElementJS(payFeesBtn);
	}

	public void clickBidBtn() throws InterruptedException {
		waitForElement(bidBtn); // bid Btn
		clickElementJS(bidBtn);

		Thread.sleep(1000);
		acceptAlert();
	}

	public void bidForFirstUser() throws InterruptedException
	{
		waitForElement(By.xpath("//*[@class='bidButton']"));
		List<WebElement> bidBtnLst;
		for(int i =0; i<2; i++)
		{
			bidBtnLst= driver.findElements(By.xpath("//*[@class='bidButton']"));
			clickElementJS(bidBtnLst.get(i));
			Thread.sleep(2000);
		}
	}
	
	public void bidForSecondUser() throws InterruptedException
	{
		waitForElement(By.xpath("//*[@class='bidButton']"));
		List<WebElement> bidBtnLst;
		for(int i=3; i<5; i++)
		{
			bidBtnLst= driver.findElements(By.xpath("//*[@class='bidButton']"));
			clickElementJS(bidBtnLst.get(i));
			Thread.sleep(2000);
		}
	}


	public void finish() throws InterruptedException {
		waitForElement(finishBtn);
		clickElementJS(finishBtn);
	}

	public String getTotalFees() {
		return getElementText(By.xpath("//*[@id='allPageContentId']/table[2]/tbody/tr/td[3]/strong"));
	}

	public SPLTradePlateTransactionsPages(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean isPageLoaded() {
		return false;
	}
}
