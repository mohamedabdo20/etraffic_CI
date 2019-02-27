package isoft.etraffic.vhl.sddipages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import isoft.etraffic.wrapper.SeleniumWraper;

public class MortgageAddAndReleasePages extends SeleniumWraper {

	By trfFileTxtbox = By.xpath("//*[@name='trafficFileNo']");
	By serviceLst = By.id("selectServiceId");
	Actions actions = new Actions(driver);
	/////////////////////////////////////////////////////////////
	// By oldPlateStatu = By.linkText("Vehicle Renewal");
	By oldPlateStatusLst = By.id("oldPlateStatusId");
	By goId = By.id("goId");
	By confirmAndProceedBtn = By.partialLinkText("Confirm & Proceed to Delivery");
	By notAgreeBtn = By.id("notAgree");

	public void gotoPlaceMortgageRequest() throws InterruptedException {
		waitForElement(serviceLst);
		selectFromListByVisibleText(serviceLst, "Place Mortgage Request");
		Thread.sleep(500);
		clickElementJS(By.xpath("//*[@id='readMe']/div[2]/div[1]/div/div/div/div[2]/a"));
	}

	public void selectCertificateSource(String certificateSource) throws InterruptedException {
		waitForElement(By.id("certificateSourceId"));
		selectFromListByVisibleText(By.id("certificateSourceId"), certificateSource);
	}

	public void selectCertificateType(String certificateType) throws InterruptedException {
		waitForElement(By.id("trsTypeId"));
		selectFromListByVisibleText(By.id("trsTypeId"), certificateType);
	}

	public void setCertificateInfo(String sourceDate) throws InterruptedException {
		writeToElement(By.id("sourceNumberId"), "123456789");
		writeToElement(By.id("sourceIssueDate_id"), sourceDate);
	}

	public void clickAddMortgageBtn() throws InterruptedException {
		waitForElement(By.id("addMortgageId0"));
		clickElementJS(By.id("addMortgageId0"));
	}

	public void setRequiredInfo(String trafficFile, String passport, String currentday) throws InterruptedException {
		waitForElement(By.id("newOwnerTrafficFileNoId"));
		writeToElement(By.id("newOwnerTrafficFileNoId"), trafficFile);

		actions.sendKeys(Keys.TAB).build().perform();
		Thread.sleep(1000);

		setNewOwnerInfo(passport);

		selectFromListByVisibleText(By.id("vhlSourceSelect"), "New (Agent)");

		writeToElement(By.id("mortgageRefNumberId"), "123456789");
		writeToElement(By.id("mortgageDate_id"), currentday);

		selectFromListByVisibleText(By.id("currentOwnerIdTypeStyleId"), "Passport No");
		writeToElement(By.id("currentOwnerIdentificationId"), passport);
		Thread.sleep(1000);
	}

	public void setNewOwnerInfo(String passport) throws InterruptedException {
		selectFromListByVisibleText(By.id("newOwnerIdTypeStyleId"), "Passport No");
		//actions.sendKeys(Keys.TAB).build().perform();
		hitTabToElement(By.id("newOwnerIdTypeStyleId"));
		
//		writeToElement(By.id("newOwnerIdentificationId"), passport);
//		//actions.sendKeys(Keys.TAB).build().perform();
//		hitTabToElement(By.id("newOwnerIdentificationId"));
//		Thread.sleep(1000);
		setNewOwnerPassport(passport);
		
		writeToElement(By.id("newOwnerEmailId"), "aaaa@bbb.com");
	}

	public void setNewOwnerPassport(String passport) throws InterruptedException {
		waitForElement(By.id("newOwnerIdentificationId"));
		writeToElement(By.id("newOwnerIdentificationId"), passport);
		hitTabToElement(By.id("newOwnerIdentificationId"));
		Thread.sleep(1000);
	}
	
	public void clickAprroveAndGotoPayment() throws InterruptedException {
		Thread.sleep(1000);
		waitForElement(By.id("reviewButtonId"));
		clickElementJS(By.id("reviewButtonId"));
	}

	public void searchByChassis(String chassisRegistered) throws InterruptedException {
		int chassisIndex = 0;
		clickElementJS(By.partialLinkText("Chassis Number"));
		Thread.sleep(2000);
		for (int i = 1; i <= 17; i++) {
			driver.findElement(By.id("chassisNo" + i + ""))
					.sendKeys(Character.toString(chassisRegistered.charAt(chassisIndex)));
			chassisIndex++;
		}

		clickElementJS(By.id("chassisNumberSearchId"));
	}

	public void setUnregisteredChassis(String chassisNotRegistered) throws InterruptedException {
		int chassisIndex = 0;
		System.out.println("chassisNotRegistered: " + chassisNotRegistered);
		for (int i = 18; i <= 34; i++) {
			driver.findElement(By.id("chassisNo" + i + ""))
					.sendKeys(Character.toString(chassisNotRegistered.charAt(chassisIndex)));
			chassisIndex++;
		}
		
		scrolldown();
		Thread.sleep(2000);
		clickElementJS(By.xpath("//*[@id='customsVhlSearchId']/span/em"));
	}

	public void setUnregisteredVehicleDetails(String chassisNotRegistered, String date) throws InterruptedException
	{
		int chassisIndex = 0;
		for (int i = 35; i <= 51; i++) {
			driver.findElement(By.id("chassisNo" + i + ""))
					.sendKeys(Character.toString(chassisNotRegistered.charAt(chassisIndex)));
			chassisIndex++;
		}
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id='addVehicleInfoManuallyId']")).click();

		scrolldown();
		driver.findElement(By.id("newOwnerTrafficFileNoId")).sendKeys("12346610");
		actions.sendKeys(Keys.TAB);
		actions.build().perform();
		Thread.sleep(1000);
		
		selectFromListByVisibleText(By.id("newOwnerIdTypeStyleId"), "Passport No");
		actions.sendKeys(Keys.TAB).build().perform();
		writeToElement(By.id("newOwnerIdentificationId"), "GH7166343");
		actions.sendKeys(Keys.TAB).build().perform();
		Thread.sleep(1000);

		scrolldown();
		for (int i = 1; i <= 17; i++) {
			driver.findElement(By.id("chassisNo" + i + ""))
					.sendKeys(Character.toString(chassisNotRegistered.charAt(i - 1)));
		}
		Thread.sleep(2000);
		clickElement(By.id("makerLookupId"));
		Thread.sleep(2000);
		driver.switchTo().frame(0);
		writeToElement(By.xpath("//*[@id=\"payopt1\"]/li/table/tbody/tr/td[1]/p/span/em/input"), "KIA");
		clickElement(By.id("searchByMakerId"));
		Thread.sleep(2000);
		clickElement(By.xpath("//*[@class='srchrsult']//*[@value='10144']"));
		clickElement(By.xpath("//*[@id=\"selectMakerId\"]/div/button/span/em"));

		actions.sendKeys(Keys.TAB).build().perform();
		Thread.sleep(2000);
		clickElement(By.id("typeDetailsAnchor"));
		Thread.sleep(2000);
		switchToFrame(By.className("cboxIframe"));
		Thread.sleep(2000);
		clickElement(By.xpath("//*[@type='RADIO']"));

		clickElement(By.xpath("//*[@id='selectCompanyId']/div/button"));
		Thread.sleep(1000);

		writeToElement(By.id("engineNumberId"), "123456789");
		selectFromListByVisibleText(By.id("manufactureYearId"), "2018");
		selectFromListByVisibleText(By.id("vhlSourceSelect"), "New (Agent)");
		selectFromListByVisibleText(By.id("color1Select"), "White");
		writeToElement(By.id("mortgageRefNumberId"), "123456789");
		writeToElement(By.id("mortgageDate_id"), date);
		Thread.sleep(1000);
	}
	public void addMortgageRelease() throws InterruptedException {
		waitForElement(By.xpath("//*[@name='customerEmailAddress']"));
		writeToElement(By.xpath("//*[@name='customerEmailAddress']"), "aaaa@bbb.com");
		clickElementJS(By.partialLinkText("Release Mortgage"));
		
		waitForElement(By.xpath("//*[@class='redarrow']/li/p/strong"));
	}

	public void gotoTrafficFileTab() throws InterruptedException {
		waitForElement(By.partialLinkText("Traffic File No."));
		clickElementJS(By.partialLinkText("Traffic File No."));
	}

	public void searchByTRFFile(String trafficFile) throws InterruptedException {
		writeToElement(trfFileTxtbox, trafficFile);
		hitEnterToElement(trfFileTxtbox);
	}

	public void clickConfirmViewCustomerProfileBtn() throws InterruptedException {
		waitForElement(By.partialLinkText("Confirm & View Customer Profile"));
		clickElementJS(By.partialLinkText("Confirm & View Customer Profile"));
	}

	public void clickConfirmAndProceedtoPaymentBtn() throws InterruptedException {
		waitForElement(By.partialLinkText("Confirm & Proceed to Payment"));
		clickElementJS(By.partialLinkText("Confirm & Proceed to Payment"));
	}

	public void clickconfirmAndProceedBtn() throws InterruptedException {
		waitForElement(By.id("confirmAndProceedButton"));
		clickElementJS(By.id("confirmAndProceedButton"));
		Thread.sleep(2000);
		driver.switchTo().alert().accept();

		waitForElement(By.xpath("//*[@class='toast-item toast-type-success']/p"));
	}

	public void clickApproveBtn() throws InterruptedException {
		Thread.sleep(1000);
		waitForElement(By.id("approveBtnId"));
		clickElementJS(By.id("approveBtnId"));
		Thread.sleep(1000);
	}

	public void gotoMortgageRequests() throws InterruptedException {
		waitForElement(By.id("mortgageRequestsList"));
		clickElementJS(By.id("mortgageRequestsList"));
		Thread.sleep(500);
		clickElement(By.id("mortgageRequestsList"));
	}

	public void searchMortgageRequestsByChassis(String chassisRegistered) throws InterruptedException {
		writeToElement(By.id("chassisNoId"), chassisRegistered);
		clickElementJS(By.xpath("//*[@class='inptbtngbg']"));
	}

	public void selectNewPlate() throws InterruptedException {
		try {
			selectFromListByVisibleText(By.id("oldPlateStatusId"), "Returned to RTA");
		} catch (Exception e) {
		}

		Thread.sleep(5000);
		driver.findElement(By.id("selectMyVehiclesId")).click(); // Step 2 - New Plate Details

		Thread.sleep(5000);
		switchToFrame(("cboxIframe"));
		selectFromListByVisibleText(By.id("frontPlateSizeId"), "Long");
		selectFromListByVisibleText(By.id("backPlateSizeId"), "Long");
		selectFromListByVisibleText(By.id("plateSourceId"), "Daily Distribution");
		Thread.sleep(2000);
		switchToFrame("cboxIframe");
		clickElementJS(goId);

		Thread.sleep(2000);
		driver.switchTo().alert().dismiss();

		Thread.sleep(5000);
		clickElementJS(confirmAndProceedBtn);
		Thread.sleep(9000);
		switchToFrame("cboxIframe");// add a Dubai brand logo
		Thread.sleep(1000);
		clickButton(notAgreeBtn);

		Thread.sleep(5000);
	}

	public void setDeliveryDetails() throws InterruptedException {
		clickElementJS(By.id("dateOfDelivery_<%=deliveryDay%>"));
		selectFromListByVisibleText(By.xpath("//*[@name='phoneOperatorCode']"), "4");
		driver.findElement(By.xpath("//*[@name='email']")).clear();
		driver.findElement(By.xpath("//*[@name='email']")).sendKeys("aaa@bbb.com");
		driver.findElement(By.xpath("//*[@name='emailConfirm']")).clear();
		driver.findElement(By.xpath("//*[@name='emailConfirm']")).sendKeys("aaa@bbb.com");
		Thread.sleep(6000);
		clickElementJS(By.id("idConfrimAndProceedButton"));
		Thread.sleep(5000);
	}

	public MortgageAddAndReleasePages(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}
