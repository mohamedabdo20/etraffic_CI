package isoft.etraffic.vhl.sddipages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import isoft.etraffic.wrapper.SeleniumWraper;

public class CommonPageOnline extends SeleniumWraper {

	public CommonPageOnline(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	String fees;

	By smartServicesBtn = By.xpath("//*[@id='content']/div[1]/div[2]/div[1]/div[2]/div/div[1]");
	By trfFileTxtbox = By.id("trafficFileNo");
	By trafficFileSearchBtn = By.id("trafficFileSearchBtn");
	By trafficOwnerNamelbl = By.id("customerDetailsBtn");
	By servicesBtnHolder = By.xpath("//*[@id='searchResultsRepeat']/div/div/div[4]");
	By servicesBtn1 = By.xpath("//*[@id='searchResultsRepeat']/div/div/div[4]/button");
	By servicesBtn2 = By.xpath("//*[@id='searchResultsRepeat']/div/div/div[5]/button");
	// Services Frame
	String availableServisesFrame = "servicesAvailableIframe";
	By availableServicesTxtbox = By.xpath("//*[@id='data-table-services-header']/div/div/div/div/input");
	By serviceLnk = By.xpath("//*[@id='data-table-services']/tbody/tr/td/a");
	By smartServicesHomePageBtn = By.xpath("//*[@id='content']/div[1]/div[2]/div[1]/div[2]/div/div[1]");

	// Search by plate
	By plateNumberBtn = By.id("plateIcon");
	By plateCategoryLst = By.id("plateCategoryId");
	By plateCodeLst = By.xpath("//*[@name='plateCodeId']");
	By goBtn = By.xpath("//*[@type='submit']");

	By plateNumberTxtbox = By.xpath("//*[@name='plateNo']");
	By plateServicesBtn = By.xpath("//*[@id='customerBooklets']/div/button");
	By plateRenewBtn = By.xpath("//*[@id='customerBooklets']/div/input");
	By plateTabBtn = By.id("platesTab");

	// Select Delivery Date
	By dateOfDeliveryRB = By.id("dateOfDelivery_<%=deliveryDay%>");
	By confrimAndProceedBtn = By.partialLinkText("Confirm & Proceed");

	// Business rule Elements
	By selectDeliveryMethodBtn = By.id("btnGoToStep2");
	By brLbl = By.xpath("//*[@class='warning']");

	By moreActionsLst = By.partialLinkText("More Actions");
	By ecertificateBtn = By.id("btnDeliverECertificate");
	By trsId = By.xpath("//*[@class='margb1']//span");//*[@class='vrRow vrFeeSummary']/div/div/div/h3"));
	By proceedToServiceBtn = By.partialLinkText("Proceed to service");
	
	public void loginAsTrustedAgent(String centerName) throws InterruptedException {
		driver.findElement(By.xpath("/html/body/form/table/tbody/tr[6]/td[1]/input")).click();
		driver.findElement(By.xpath("/html/body/form/table/tbody/tr[6]/td[3]/input")).sendKeys("Automated TC");
		driver.findElement(By.xpath("/html/body/form/table/tbody/tr[6]/td[5]/div/span/input")).sendKeys(centerName);
		driver.findElement(By.xpath("//*[@name='Login']")).click();

		waitForElement(By.linkText("I agree"));
		clickElementJS(By.linkText("I agree"));
	}

	public void loginAsCallCenter(String username) throws InterruptedException {
		driver.findElement(By.xpath("/html/body/form/table/tbody/tr[4]/td[1]/input")).click();
		driver.findElement(By.xpath("/html/body/form/table/tbody/tr[4]/td[3]/input")).sendKeys(username);
		driver.findElement(By.xpath("//*[@name='Login']")).click();
	}

	public void loginByTrafficFile(String trafficFileNo) throws InterruptedException {
		driver.findElement(By.xpath("//*[@name='trafficNo']")).sendKeys(trafficFileNo);
		driver.findElement(By.name("Login")).click();
	}

	public void gotoLicensing() throws InterruptedException {
		waitForElement(By.partialLinkText("Go to Licensing"));
		clickElementJS(By.partialLinkText("Go to Licensing"));
	}

	public void gotoLicenseTrustedAgent() throws InterruptedException {
		waitForElement(By.xpath("//*[@class='mylicns']/a"));
		clickElementJS(By.xpath("//*[@class='mylicns']/a"));
	}

	public void gotoMyPlates() throws InterruptedException {
		waitForElement(By.partialLinkText("My Plates"));
		clickElementJS(By.partialLinkText("My Plates"));
	}

	public void gotoMyLicense() throws InterruptedException {
		waitForElement(By.partialLinkText("My License"));
		clickElementJS(By.partialLinkText("My License"));
	}

	public void gotoService(String serviceName) throws InterruptedException {
		waitForElement(By.partialLinkText(serviceName));
		clickElementJS(By.partialLinkText(serviceName));
	}

	public void selectTrustedAgentService(String serviceName) throws InterruptedException {
		waitForElement(By.id("selectServiceId"));
		selectFromListByValue(By.id("selectServiceId"),
				getOptionValue(By.xpath("//*[@id='selectServiceId']/option"), serviceName));
	}

	public void gotoTab(String tabName) throws InterruptedException {
		gotoService(tabName);
	}

	public void gotoDriversTabPA() throws InterruptedException
	{
		waitForElement(By.xpath("//*[@id='tab2']/a"));
		clickElementJS(By.xpath("//*[@id='tab2']/a"));
	}
	
	public void gotoCustomerProfile() throws InterruptedException {
		gotoService("Confirm & View Customer Profile");
	}

	public void searchByPlate(String plateCategory, String plateNumber, String plateCode) throws InterruptedException {
		waitForElement(By.xpath("//*[@name='plateNo']"));
		writeToElement(plateNumberTxtbox, plateNumber);

		selectFromListByVisibleText(plateCategoryLst, plateCategory);
		if (plateCategory.equals("Private") || plateCategory.equals("Motorcycle"))
			selectFromListByVisibleText(plateCodeLst, plateCode);
		else if (plateCategory.contains("Public Transportation")) {
			if (plateCode.contains("1")) {
				hitTabToElement(plateCategoryLst);
				hitArrowDownToElement(plateCodeLst);
				hitArrowDownToElement(plateCodeLst);
			} else
				selectFirstValueOnline(plateCodeLst);
		} else {
			hitTabToElement(plateCategoryLst);
			selectFirstValueOnline(plateCodeLst);
		}

		clickButton(goBtn);
		Thread.sleep(1000);
	}

	public void clickViewVehicleDetails() throws InterruptedException {
		waitForElement(By.partialLinkText("View vehicle details"));
		clickElementJS(By.partialLinkText("View vehicle details"));
	}

	public void setDeliveryDetails() throws InterruptedException {
		waitForElement(By.id("dateOfDelivery_<%=deliveryDay%>"));
		clickElementJS(By.id("dateOfDelivery_<%=deliveryDay%>"));
		driver.findElement(By.xpath("//*[@name='shipmentCompanyName']")).clear();
		clickElementJS(By.xpath("//*[@name='residentialAddressShippmentInfo']"));
		selectFromListByVisibleText(By.xpath("//*[@name='shipmentEmirate']"), "Dubai");
		selectFromListByVisibleText(By.xpath("//*[@name='shipmentArea']"), "Al Barsha");
		writeToElement(By.id("shipmentAddress1"), "123");
		writeToElement(By.xpath("//*[@name='shipmentAddress2']"), "Jumeirah 1 Jumeirah grand Masjid");
		selectFromListByVisibleText(By.xpath("//*[@name='phoneOperatorCode']"), "4");
		writeToElement(By.xpath("//*[@name='phoneNumber']"), "4444444");
		driver.findElement(By.xpath("//*[@name='email']")).clear();
		driver.findElement(By.xpath("//*[@name='email']")).sendKeys("aaa@bbb.com");
		driver.findElement(By.xpath("//*[@name='emailConfirm']")).clear();
		driver.findElement(By.xpath("//*[@name='emailConfirm']")).sendKeys("aaa@bbb.com");
		driver.findElement(By.xpath("//*[@name='newPoBoxNumber']")).clear();
		Thread.sleep(1000);
		clickElementJS(By.id("idConfrimAndProceedButton"));
		Thread.sleep(5000);
	}

	public void gotoMyVehicles() throws InterruptedException {
		waitForElement(By.partialLinkText("Vehicles"));
		clickElementJS(By.partialLinkText("Vehicles"));
	}

	public void searchByTRFFile(String trafficFile) throws InterruptedException {
		writeToElement(trfFileTxtbox, trafficFile);
		hitEnterToElement(trfFileTxtbox);
	}

	public void searchByTRFFileTrustedAgent(String trafficFile) throws InterruptedException {
		waitForElement(By.xpath("//*[@name='trafficFileNo']"));
		writeToElement(By.xpath("//*[@name='trafficFileNo']"), trafficFile);
		hitEnterToElement(By.xpath("//*[@name='trafficFileNo']"));
	}

	public void gotoSelectDelivery() throws InterruptedException {
		waitForElement(By.id("btnGoToStep2"));
		clickElementJS(By.id("btnGoToStep2"));
	}

	public void selectDeliveryDate() throws InterruptedException {
		waitForElement(dateOfDeliveryRB);
		clickElementJS(dateOfDeliveryRB);
		clickElementJS(confrimAndProceedBtn);
	}

	public void selectDeliveryDateCourier(boolean courier) throws InterruptedException {
		if (courier) {
			waitForElement(By.id("btnDeliverToDoor"));
			clickElementJS(By.id("btnDeliverToDoor"));
			waitForElement(By.id("txtDeliveryDateCourier"));
			clickElementJS(By.xpath("//*[@id='dp1']/span/span"));
			Thread.sleep(1000);
			driver.findElements(By.xpath("//*[@class='day']")).get(0).click();

			driver.findElement(By.id("shipmentContactName")).sendKeys("firstName lastName");

			driver.findElement(By.xpath("//span[starts-with(@id, 'select2-shipmentEmirate')]")).click();
			waitForElement(By.xpath("/html/body/span/span/span[1]/input"));
			driver.findElement(By.xpath("/html/body/span/span/span[1]/input")).sendKeys("Dubai");
			driver.findElement(By.xpath("/html/body/span/span/span[1]/input")).sendKeys(Keys.chord(Keys.ENTER));
			driver.findElement(By.id("shipmentCompanyName")).sendKeys("Address Comapny Name");
			selectFromListByVisibleText(By.id("shipmentArea"), "Bur Dubai");
			driver.findElement(By.id("shipmentAddress1")).sendKeys("123456789");
			driver.findElement(By.id("shipmentAddress2")).sendKeys("123456789 Bur Dubai");

			Thread.sleep(1000);
			// driver.findElements(By.xpath("//*[@class='day']")).get(0).click();
			clickElementJS(By.id("btnGoToStep31"));
		} else {
			waitForElement(By.id("btnDeliverFromRTA"));
			clickElementJS(By.id("btnDeliverFromRTA"));
			clickElementJS(By.xpath("//*[@id='dp2']/span/span"));
			Thread.sleep(1000);
			driver.findElements(By.xpath("//*[@class='day']")).get(0).click();
			writeToElement(By.id("mobileNumber"), "0505889595");
			writeToElement(By.id("phoneNumber"), "0505889595");
			writeToElement(By.id("email"), "aaa@bbb.com");
			writeToElement(By.id("emailConfirm"), "aaa@bbb.com");
			clickElementJS(By.id("btnGoToStep32"));
		}
	}

	public void selectDeliveryDateCourierTrustedAgent(boolean courier) throws InterruptedException {
		if (courier) {
			waitForElement(By.xpath("//*[@name='methodOfDelivery']"));
			clickElementJS(By.xpath("//*[@name='methodOfDelivery']"));
			clickElementJS(By.id("dateOfDelivery_<%=deliveryDay%>"));

			writeToElement(By.xpath("//*[@name='shipmentContactName']"), "firstName lastName");
			driver.findElement(By.xpath("//*[@name='shipmentCompanyName']")).clear();
			clickElementJS(By.xpath("//*[@name='residentialAddressShippmentInfo']"));

			selectFromListByVisibleText(By.xpath("//*[@name='shipmentEmirate']"), "Dubai");
			selectFromListByVisibleText(By.xpath("//*[@name='shipmentArea']"), "Al Barsha");
			driver.findElement(By.xpath("//*[@name='shipmentAddress1']")).sendKeys("123456789");
			driver.findElement(By.xpath("//*[@name='shipmentAddress2']")).sendKeys("123456789 Bur Dubai");

			try {clickElementJS(By.xpath("//*[@name='confimCustomerContactInfo']"));}
			catch(Exception e) {}
			Thread.sleep(1000);
			clickElementJS(confrimAndProceedBtn);
			Thread.sleep(1000);

			// try {acceptAlert();}catch(Exception e) {}
		} else {
			waitForElement(By.id("btnDeliverFromRTA"));
			clickElementJS(By.id("btnDeliverFromRTA"));
			clickElementJS(By.xpath("//*[@id='dp2']/span/span"));
			Thread.sleep(1000);
			driver.findElements(By.xpath("//*[@class='day']")).get(0).click();
			writeToElement(By.id("email"), "aaa@bbb.com");
			writeToElement(By.id("emailConfirm"), "aaa@bbb.com");
			clickElementJS(By.id("btnGoToStep32"));
		}
	}

	public void clickConfirmAndProceedToPayment() throws InterruptedException {
		clickByLinkTxt("Confirm & Proceed to Payment");
	}

	public void selectDeliveryDateFromCenterTrustedAgent(boolean selectDate) throws InterruptedException {
		if (selectDate) {
			waitForElement(dateOfDeliveryRB);
			clickElementJS(dateOfDeliveryRB);
		}
		waitForElement(By.xpath("//*[@name='mobileNumber']"));
		selectFromListByVisibleText(By.xpath("//*[@name='mobileOperatorCode']"), "54");
		writeToElement(By.xpath("//*[@name='mobileNumber']"), "5889999");
		writeToElement(By.xpath("//*[@name='email']"), "aaa@bbb.com");
		writeToElement(By.xpath("//*[@name='emailConfirm']"), "aaa@bbb.com");
		writeToElement(By.xpath("//*[@name='newPoBoxNumber']"), "1234");
		try {clickElementJS(By.xpath("//*[@name='confimCustomerContactInfo']"));}
		catch(Exception e) {}
		clickByLinkTxt("Confirm & Proceed to Payment");
	}

	public void setMobileNumber() throws InterruptedException {
		waitForElement(By.xpath("//*[@name='mobileNumber']"));
		writeToElement(By.xpath("//*[@name='mobileNumber']"), "0545889999");
	}

	public void selectDeliveryDateFromCenterTrustedAgent() throws InterruptedException {
		waitForElement(dateOfDeliveryRB);
		clickElementJS(dateOfDeliveryRB);
		selectFromListByVisibleText(By.xpath("//*[@name='mobileOperatorCode']"), "54");
		writeToElement(By.xpath("//*[@name='mobileNumber']"), "5889999");
		writeToElement(By.xpath("//*[@name='email']"), "aaa@bbb.com");
		writeToElement(By.xpath("//*[@name='emailConfirm']"), "aaa@bbb.com");
		clickElementJS(By.xpath("//*[@name='confimCustomerContactInfo']"));
		clickByLinkTxt("Confirm & Proceed to Payment");
	}

	public void setDeliveryAddressDetails() throws InterruptedException {
		driver.findElement(By.id("shipmentContactName")).sendKeys("firstName lastName");

		driver.findElement(By.xpath("//span[starts-with(@id, 'select2-shipmentEmirate')]")).click();
		waitForElement(By.xpath("/html/body/span/span/span[1]/input"));
		driver.findElement(By.xpath("/html/body/span/span/span[1]/input")).sendKeys("Dubai");
		driver.findElement(By.xpath("/html/body/span/span/span[1]/input")).sendKeys(Keys.chord(Keys.ENTER));
		driver.findElement(By.id("shipmentCompanyName")).sendKeys("Address Comapny Name");
		selectFromListByVisibleText(By.id("shipmentArea"), "Bur Dubai");
		driver.findElement(By.id("shipmentAddress1")).sendKeys("123456789");
		driver.findElement(By.id("shipmentAddress2")).sendKeys("123456789 Bur Dubai");

	}

	public boolean isBRShown(String br) throws InterruptedException {
		waitForElement(selectDeliveryMethodBtn);
		if (getElementText(brLbl).contains(br))
			return true;
		else
			return false;
	}

	public void gotoMainService(String service) throws InterruptedException {
		waitForElement(By.partialLinkText("General Services"));
		clickElementJS(By.partialLinkText("General Services"));
		Thread.sleep(1000);
		clickElementJS(By.linkText(service));
		Thread.sleep(1000);
		startTransaction();
	}

	public void gotoPlateService(String service) throws InterruptedException {
		clickButton(By.xpath("/html/body/form/div[2]/table/tbody/tr[2]/td[6]/a/em"));
		Thread.sleep(1000);
		clickElementJS(By.linkText(service));
		Thread.sleep(1000);

		startTransaction();
	}

	public void gotoPlateTourismRetunService(String service) throws InterruptedException {
		clickButton(By.xpath("/html/body/form/div[2]/table/tbody/tr[2]/td[6]/a/em"));
		Thread.sleep(1000);
		clickElementJS(By.linkText(service));
		Thread.sleep(1000);

		waitForElement(By.id("cbTncVehicleRenewal"));
		clickElementJS(By.id("cbTncVehicleRenewal"));
		clickElementJS(By.id("btnStartProcess"));
		Thread.sleep(5000);

		try {
			selectFromListByVisibleText(By.xpath("//*[@name='mobileOperatorCode']"), "50");
			driver.findElement(By.xpath("//*[@name='mobileNumber']")).sendKeys("5050520");
			driver.findElement(By.xpath("//*[@name='email']")).sendKeys("aaa@bbb.com");
			clickElementJS(By.xpath("//*[@class='inptbtn fr']"));
			Thread.sleep(5000);
		} catch (Exception e) {
		}
	}

	public void confirm() throws InterruptedException {
		waitForElement(By.id("btnGoToStep4"));// Confirm button
		clickElementJS(By.id("btnGoToStep4"));
	}

	public boolean checkFreeTransaction() throws InterruptedException {
		waitForElement(By.id("btnGoToStep4"));
		if (getElementText(By.xpath("//*[@class='vrFeeSummary']//div[3]/div/h3"))
				.contains("هذه الخدمة لا يوجد لها رسوم للدفع"))
			return true;
		else
			return false;
	}

	public void payOnline() throws InterruptedException {
		waitForElement(By.id("payButtonId"));
		clickElementJS(By.id("payButtonId"));
		// waitForElement(By.id("epayButtonId"));
		// clickElementJS(By.id("epayButtonId"));
		// clickElementJS(By.id("btnGoToStep4"));

		//// WebDriverWait wait = new WebDriverWait(driver, 15, 100);
		//// WebElement myElement =
		//// wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@name,'chkConfirm')]")));
		//// myElement.click();
		//// driver.findElement(By.xpath("//input[@name='btnPay']")).click();
		waitForElement(By.xpath("//input[contains(@name,'chkConfirm')]"));
		clickElementJS(By.xpath("//input[contains(@name,'chkConfirm')]"));
		Thread.sleep(500);
		clickElementJS(By.xpath("//*[@name='btnPay']"));
		Thread.sleep(3000);
		WebDriverWait waitt = new WebDriverWait(driver, 15, 100);
		WebElement myElementt = waitt.until(ExpectedConditions.elementToBeClickable(By.id("txtCardNo")));
		myElementt.sendKeys("4111111111111111");
		driver.findElement(By.xpath("//select[@ng-model='month']")).click();
		driver.findElement(By.xpath("//option[@label='January']")).click();
		driver.findElement(By.xpath(
				"/html/body/div[1]/div/form/div/div/div[2]/div[2]/div[1]/div[2]/table/tbody/tr[3]/td[3]/table/tbody/tr/td[2]/select"))
				.sendKeys("2020");
		Thread.sleep(1000);// was 5
		driver.findElement(By.id("txtCvvNo")).sendKeys("123");
		driver.findElement(By.id("payButton")).click();
		System.out.println("review finished");
		Thread.sleep(10000);
//		waitForElement(trsId);
//		System.out.println(getElementText(trsId));
		
		
		// Thread.sleep(10000);
		//
		// Actions actions = new Actions(driver);
		// actions.moveToElement(driver.findElement(By.xpath("//*[@class='fancybox-overlay
		// fancybox-overlay-fixed']")));
		// actions.sendKeys(Keys.ESCAPE);
		// actions.build().perform();
		//
		// JavascriptExecutor jsx8 = (JavascriptExecutor) driver;
		// jsx8.executeScript("window.scrollBy(0,550)", "");
		// Thread.sleep(5000);
		// driver.findElement(By.xpath("//*[@id=\"mainDivId\"]/div[4]/div/a[4]")).click();
	}

	public void payCreditCardTrustedAgeny() throws InterruptedException {
		waitForElement(By.id("payCreditButtonId"));
		clickElementJS(By.id("payCreditButtonId"));

		waitForElement(By.id("cboxIframe"));
		switchToFrame("cboxIframe");

		waitForElement(By.xpath("//*[@name='creditCardNumber']"));
		writeToElement(By.xpath("//*[@name='creditCardNumber']"), "4111111111111111");
		selectFromListByVisibleText(By.xpath("//*[@name='creditCardExpMonth']"), "December");
		selectFromListByVisibleText(By.xpath("//*[@name='creditCardExpYear']"), "2024");
		clickByLinkTxt("Make Payment");
	}
	
	public void payCreditCardCallCenter() throws InterruptedException {
		waitForElement(By.id("payButtonId"));
		clickElementJS(By.id("payButtonId"));

		waitForElement(By.id("cboxIframe"));
		switchToFrame("cboxIframe");

		waitForElement(By.xpath("//*[@name='creditCardNumber']"));
		writeToElement(By.xpath("//*[@name='creditCardNumber']"), "4111111111111111");
		selectFromListByVisibleText(By.xpath("//*[@name='creditCardExpMonth']"), "December");
		selectFromListByVisibleText(By.xpath("//*[@name='creditCardExpYear']"), "2024");
		clickByLinkTxt("Make Payment");
		Thread.sleep(1000);
		clickByLinkTxt("Confirm");
	}

	public void payDubaiPay() throws InterruptedException {
		waitForElement(By.xpath("//input[contains(@name,'chkConfirm')]"));
		clickElementJS(By.xpath("//input[contains(@name,'chkConfirm')]"));
		Thread.sleep(500);
		clickElementJS(By.xpath("//*[@name='btnPay']"));
		Thread.sleep(3000);
		WebDriverWait waitt = new WebDriverWait(driver, 15, 100);
		WebElement myElementt = waitt.until(ExpectedConditions.elementToBeClickable(By.id("txtCardNo")));
		myElementt.sendKeys("4111111111111111");
		driver.findElement(By.xpath("//select[@ng-model='month']")).click();
		driver.findElement(By.xpath("//option[@label='January']")).click();
		driver.findElement(By.xpath(
				"/html/body/div[1]/div/form/div/div/div[2]/div[2]/div[1]/div[2]/table/tbody/tr[3]/td[3]/table/tbody/tr/td[2]/select"))
				.sendKeys("2020");
		Thread.sleep(1000);// was 5
		driver.findElement(By.id("txtCvvNo")).sendKeys("123");
		driver.findElement(By.id("payButton")).click();
		System.out.println("Payment Done.");

	}

	public void payCashOnline() throws InterruptedException {
		waitForElement(By.partialLinkText("Cash Payment"));
		clickElementJS(By.partialLinkText("Cash Payment"));

		waitForElement(By.id("cboxIframe"));
		clickElementJS(By.id("cboxIframe"));
		Thread.sleep(1000);
		waitForElement(By.id("cboxIframe"));
		switchToFrame("cboxIframe");

		clickElementJS(By.id("terms"));
		clickElementJS(By.id("paymentButton"));
	}

	public void printTransactionNumber() throws InterruptedException {
		waitForElement(By.xpath("//*[@class='movboxcont']/p[2]/strong"));
		System.out.println(getElementText(By.xpath("//*[@class='movboxcont']/p[2]/strong")));
	}

	public boolean assertPaymentSuccess(String text) throws InterruptedException {
		try {
			driver.findElement(By.xpath("//*[@id='titlePanel']/h1")).getText();
		} catch (Exception e) {
			Thread.sleep(3000);
		}
		System.out.println("Title Text: " + driver.findElement(By.xpath("//*[@id='titlePanel']/h1")).getText());

		if (driver.findElement(By.xpath("//*[@id='titlePanel']/h1")).getText().contains(text + " (" + fees + " درهم)"))
			return true;
		else
			return false;

	}

	// public void searchTrafficFilePlates(String plateNumber) {
	// int platesCount =
	// driver.findElements(By.xpath("//*[@class='number']")).size();
	// List<WebElement> plates =
	// driver.findElements(By.xpath("//*[@class='number']"));
	// // List<WebElement> codes =
	// driver.findElements(By.xpath("//*[@class='code']"));
	// int plateIndex = 0;
	// for (int i = 0; i < platesCount; i++) {
	// if (plates.get(i).getText().equals(plateNumber)) {
	// plateIndex = i;
	// break;
	// }
	// }
	// System.out.println("plateIndex: " + plateIndex);
	//
	// //
	// clickElementJS(driver.findElements(By.xpath("//*[@id='customerBooklets']/div/button")).get(plateIndex));
	//
	// }

	public void startTransaction() throws InterruptedException {
		waitForElement(By.id("terms"));
		clickElementJS(By.id("terms"));
		clickElementJS(By.id("openProAnchor"));
		Thread.sleep(5000);

		try {
			selectFromListByVisibleText(By.xpath("//*[@name='mobileOperatorCode']"), "50");
			driver.findElement(By.xpath("//*[@name='mobileNumber']")).sendKeys("5050520");
			driver.findElement(By.xpath("//*[@name='email']")).sendKeys("aaa@bbb.com");
			clickElementJS(By.xpath("//*[@class='inptbtn fr']"));
			Thread.sleep(5000);
		} catch (Exception e) {
		}
	}

	public void startTransactionNew() throws InterruptedException {
		waitForElement(By.id("cbTncVehicleRenewal"));
		clickElementJS(By.id("cbTncVehicleRenewal"));
		clickElementJS(By.id("btnStartProcess"));
		Thread.sleep(5000);
	}

	public void startTransactionTrustedAgent() throws InterruptedException {
		clickElementJS(By.id("terms"));
		clickElementJS(By.id("openProAnchor"));
		Thread.sleep(5000);
	}

	private String getOptionValue(By optionsLocator, String optionTxt) {
		String optionValue = "";
		List<WebElement> optionsLst = driver.findElements(optionsLocator);
		for (int i = 0; i < optionsLst.size(); i++) {
			if (optionsLst.get(i).getText().equals(optionTxt)) {
				optionValue = optionsLst.get(i).getAttribute("value");
				break;
			}
		}
		return optionValue;
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

	public void selectServiceFromMoreList(String serviceName) throws InterruptedException {
		waitForElement(moreActionsLst);
		clickElementJS(moreActionsLst);

		waitForElement(By.partialLinkText(serviceName));
		clickElementJS(By.partialLinkText(serviceName));
	}

	public void clickConfirmAndProceedtoDeliveryMethod() throws InterruptedException {
		waitForElement(By.linkText("Confirm & Proceed to Delivery Method"));
		clickElementJS(By.linkText("Confirm & Proceed to Delivery Method"));
	}

	public void clickECertificateBtn() throws InterruptedException {
		waitForElement(ecertificateBtn);
		clickElementJS(ecertificateBtn);
	}

	public void clickFullNameChbox() throws InterruptedException {
		waitForElement(By.id("idConfirmCheckBox"));
		clickElementJS(By.id("idConfirmCheckBox"));
	}

	public void clickConfrimAndProceedButton() throws InterruptedException {
		waitForElement(By.id("idConfrimAndProceedButton"));
		clickElementJS(By.id("idConfrimAndProceedButton"));
	}

	public void clickProceedToServiceBtn() throws InterruptedException {
		waitForElement(proceedToServiceBtn);
		clickElementJS(proceedToServiceBtn);
	}
}
