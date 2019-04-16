package isoft.etraffic.vhl.ftfpages;

import java.awt.AWTException;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import isoft.etraffic.enums.*;
import isoft.etraffic.wrapper.*;

public class CommonPage extends SeleniumWraper {

	public CommonPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	String fees;

	By smartServicesBtn = By.xpath("//*[@data-name='الخدمات الذكيه']/div");// By.xpath("//*[@id='content']/div[1]/div[2]/div[1]/div[2]/div/div[1]");
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
	By vhlBtn = By.partialLinkText("ترخيص المركبات");

	// Search by plate
	By plateNumberBtn = By.id("plateIcon");
	By plateCategoryLst = By.id("selectPlateType-selectized");
	By plateCodeLst = By.id("selectPlateCode-selectized");
	By plateNumberTxtbox = By.id("inputPlateNo");
	By plateServicesBtn = By.xpath("//*[@id='customerBooklets']/div/button");
	By plateRenewBtn = By.xpath("//*[@id='customerBooklets']/div/input");
	By plateTabBtn = By.id("platesTab");
	// Service CommonBtn
	By verifyAllBtn = By.id("verifyAllBtnId");

	// Plate Tab Elements
	By plateReplaceBtn = By.xpath("//*[@id='productsHolderInnerPlates']/div/input[1]");
	By plateCancelBtn = By.xpath("//*[@id='productsHolderInnerPlates']/div/input[2]");
	By plateRenewalBtn2 = By.xpath("//*[@class='col-sm-1 bgm-white card plateBox']/input");

	// Student File Tab
	By studentFileTabBtn = By.id("studentFileTab");
	By prevLicenseTabBtn = By.id("prevLicenseTab");
	By studentFileServicesBtn = By
			.xpath("//*[@class='btn btn-warning dropdown-toggle waves-effect waves-button waves-float']");
	By updatestudentFileBtn = By.xpath("//*[@class='btn btn-success OraLink waves-effect waves-button waves-float']");

	// Applicant Eligibility Tab
	By applicantEligiblityTaBtn = By.id("applicantEligiblityTab");

	// Plate Strategy
	By openSelectPlateBtn = By.id("openSelectPlateBtn");
	By plateSourceLst = By.id("plate-source");
	By selectPlateBtn = By.id("btnChangePlateSubmit");
	By certifyLnk = By.id("certifyTrsBtn");

	// reqTestsTab
	By reqTestsTabBtn = By.id("reqTestsTab");

	By businessRule = By.xpath("//*[@id='mCSB_3_container']/span");

	public void gotoSmartServices() throws InterruptedException {
		Thread.sleep(1000);
		System.out.println("----------- Go to Smart Service ------------");
		try {
			clickElementJS(smartServicesBtn);
		} catch (Exception e) {
			Thread.sleep(2000);
			try {
				if (driver.findElement(smartServicesBtn).isDisplayed()) {
					gotoSmartServices();
					}
			} catch (Exception ex) {}
		}
	}

	public void gotoDashboard() throws InterruptedException {
		waitForElement(By.id("navigateToDashboardId"));
		clickElementJS(By.id("navigateToDashboardId"));
	}

	public void clickMainMenuBtn() throws InterruptedException {
		waitForElement(By.id("menu-trigger"));
		try {
			clickElementJS(By.id("menu-trigger"));
		} catch (Exception e) {
			clickMainMenuBtn();
		}
	}

	public void gotoHomePage() throws InterruptedException {
		System.out.println("----------- Go To Home Page ------------");
		waitForElement(By.partialLinkText("نظام الترخيص و المرور"));
		tryClickElement(By.partialLinkText("نظام الترخيص و المرور"));
		Thread.sleep(1000);
		boolean smartServicesBtnIsShown = false;
		while(!smartServicesBtnIsShown)
		{
			try{Thread.sleep(1000);
				driver.findElement(smartServicesBtn).isDisplayed();
				//System.out.println("try Go To Home w la2i smartservice");
				smartServicesBtnIsShown = true;
				break;
			}
			catch(Exception ex) {//System.out.println("catch msh la2a smartservice");
			tryClickElement(By.partialLinkText("نظام الترخيص و المرور"));}
		}
	}

	public void gotoDriversLicense() throws InterruptedException {
		clickMainMenuBtn();
		while (true) {
			try {
				clickByLinkTxt("ترخيص السائقين");
				break;
			} catch (Exception e) {
				Thread.sleep(1000);
			}
		}
	}

	public void gotoDriversTest() throws InterruptedException {
		// waitForElement(By.partialLinkText("نظام الترخيص و المرور الذكى"));
		// try {
		// clickElementJS(By.partialLinkText("نظام الترخيص و المرور الذكى"));
		// } catch (Exception e) {
		// Thread.sleep(1000);
		// clickElementJS(By.partialLinkText("نظام الترخيص و المرور الذكى"));
		// }
		Thread.sleep(1000);
		clickMainMenuBtn();
		while (true) {
			try {
				clickByLinkTxt("فحص السائقين");
				break;
			} catch (Exception e) {
				Thread.sleep(1000);
			}
		}
	}

	public void gotoMain() throws InterruptedException {
		Thread.sleep(1000);
		clickMainMenuBtn();
		while (true) {
			try {
				clickByLinkTxt("بيانات أساسية");
				break;
			} catch (Exception e) {
				Thread.sleep(1000);
			}
		}
	}

	public void gotoBackOfficeScreen(String screenName) throws InterruptedException {
		waitForElement(By.xpath("//*[@data-name = '" + screenName + "']"));
		scrollToelement(By.xpath("//*[@data-name = '" + screenName + "']"));
		clickElementJS(By.xpath("//*[@data-name = '" + screenName + "']"));

	}

	public void gotoScreen(String screenName) throws InterruptedException {
		waitForElement(By.xpath("//div[contains(@data-name,'" + screenName + "')]"));
		clickElementJS(By.xpath("//div[contains(@data-name,'" + screenName + "')]"));
		System.out.println("Done-----------------------");
	}

	public void gotoVHL() throws InterruptedException {
		waitForElement(By.partialLinkText("نظام الترخيص و المرور الذكى"));
		Thread.sleep(1000);
		while (true) {
			try {
				clickElement(vhlBtn);
				break;
			} catch (Exception e) {
				Thread.sleep(1000);
			}
		}
	}

	public void searchByTRFFile(String trfFile) throws InterruptedException {
		System.out.println("----------- Go to SearchByTRFFile ------------");
		waitForElement(trfFileTxtbox);
		Thread.sleep(500);
		writeToElement(trfFileTxtbox, trfFile);
		hitEnterToElement(trfFileTxtbox);
		waitForElement(trafficOwnerNamelbl);
		Thread.sleep(1000);
	}

	public void searchByPlate(String plateCategory, String plateNumber, String plateCode) throws InterruptedException {
		waitForElement(plateNumberBtn);
		Thread.sleep(1000);
		clickButton(plateNumberBtn);
		waitForElement(plateCategoryLst);
		try {
			selectFromFTFList(plateCategoryLst, plateCategory);
		} catch (Exception e) {
			Thread.sleep(500);
			clickButton(plateNumberBtn);
			waitForElement(plateCategoryLst);
			selectFromFTFList(plateCategoryLst, plateCategory);
		}

		if (plateCategory.contains("خصوص") || plateCategory.endsWith("نارية") || plateCategory.contains("نقل")) {
			Thread.sleep(1000);
			try {
				selectFromFTFList(plateCodeLst, plateCode);
			} catch (Exception e) {
				Thread.sleep(1000);
				selectFromFTFList(plateCodeLst, plateCode);
			}
		}
		writeToElement(plateNumberTxtbox, plateNumber);
		hitEnterToElement(plateNumberTxtbox);
	}

	public void gotoPlateService(String service) throws InterruptedException {
		waitForElement(plateServicesBtn);
		tryClickElement(plateServicesBtn);
		// Thread.sleep(1000);
		// System.out.println(driver.findElement(servicesBtnHolder).getAttribute("class").toString());
		// if
		// (driver.findElement(servicesBtnHolder).getAttribute("class").toString().contains("button-holder"))
		// clickElementJS(servicesBtn1);
		// else
		// clickElementJS(servicesBtn2);
		// Thread.sleep(1000);
		switchToFrame(availableServisesFrame);
		writeToElement(availableServicesTxtbox, service);
		Thread.sleep(1000);
		clickElement(serviceLnk);
		try {
			tryClickElement(verifyAllBtn, 5);
			Thread.sleep(3000);
		} catch (Exception e) {
			System.out.println("No Need For confirmation");
		}
	}

	public void gotoStudentFileService(String service) throws InterruptedException {
		waitForElement(By.id("studentFileFrame"));
		switchToFrame("studentFileFrame");
		waitForElement(studentFileServicesBtn);
		clickElementJS(studentFileServicesBtn);
		waitForElement(By.partialLinkText(service));
		clickElementJS(By.partialLinkText(service));

		// switchToFrame(availableServisesFrame);
		// writeToElement(availableServicesTxtbox, service);
		// Thread.sleep(1000);
		// clickElement(serviceLnk);
		// try {
		// Thread.sleep(5000);
		// clickElement(verifyAllBtn);
		// Thread.sleep(3000);
		// } catch (Exception e) {
		// System.out.println("No Need For confirmation");
		// }
	}

	public void clickUpdateStudentFileBtn() throws InterruptedException {
		waitForElement(By.id("studentFileFrame"));
		switchToFrame("studentFileFrame");
		waitForElement(updatestudentFileBtn);
		clickElementJS(updatestudentFileBtn);
		Thread.sleep(1000);
	}

	public void gotoPlateServiceOld(String service) throws InterruptedException {
		waitForElement(plateServicesBtn);
		clickElementJS(plateServicesBtn);
		switchToFrame(availableServisesFrame);
		writeToElement(availableServicesTxtbox, service);
		Thread.sleep(1000);
		clickElement(serviceLnk);
	}

	public void gotoRenewl() throws InterruptedException {
		waitForElement(plateRenewBtn);
		clickElement(plateRenewBtn);
		try {
			Thread.sleep(5000);
			clickElement(verifyAllBtn);
			Thread.sleep(5000);
		} catch (Exception e) {
			System.out.println("No Need For confirmation");
		}
	}

	public void gotoPlatesTab(String plateNumber) throws InterruptedException {

		Thread.sleep(1000);
		waitForElement(plateTabBtn);
		try {
			clickElement(plateTabBtn);
		} catch (Exception e) {
			Thread.sleep(500);
			// if (!getElementText(By.xpath("//*[@class='number']")).equals(plateNumber)) {
			// tryClickElement(By.linkText("بحث عن اللوحات"));
			// waitForElement(By.id("filterPlateNumber"));
			// writeToElement(By.id("filterPlateNumber"), plateNumber);
			// tryClickElement(By.id("filterPlatesBtn"));
			// Thread.sleep(500);}
		}
	}

	public void gotoStudentFileTab() throws InterruptedException {
		Thread.sleep(1000);
		waitForElement(studentFileTabBtn);
		clickElement(studentFileTabBtn);
		Thread.sleep(1000);
	}

	public void gotoPreviousLicenseTab() throws InterruptedException {
		Thread.sleep(1000);
		waitForElement(prevLicenseTabBtn);
		clickElement(prevLicenseTabBtn);
		Thread.sleep(1000);
	}

	public void gotoapplicantEligiblityTab() throws InterruptedException {
		clickNextArrow();
		waitForElement(applicantEligiblityTaBtn);
		Thread.sleep(500);
		clickElementJS(applicantEligiblityTaBtn);
	}

	public void gotoReqTestsTab() throws InterruptedException {
		waitForElement(reqTestsTabBtn);
		Thread.sleep(500);
		clickElementJS(reqTestsTabBtn);
	}

	public void gotolicenseTab() throws InterruptedException {
		Thread.sleep(1000);
		waitForElement(By.id("licenseTab"));
		clickElementJS(By.id("licenseTab"));

	}

	public void gotoCancelPlate() throws InterruptedException {
		clickElement(plateCancelBtn);
		try {
			Thread.sleep(5000);
			clickElement(verifyAllBtn);
			Thread.sleep(5000);
		} catch (Exception e) {
			System.out.println("No Need For confirmation");
		}
	}

	public void gotoRenewPlate() throws InterruptedException {
		waitForElement(plateReplaceBtn);
		clickElementJS(plateReplaceBtn);
		try {
			Thread.sleep(5000);
			clickElement(verifyAllBtn);
			Thread.sleep(5000);
		} catch (Exception e) {
			System.out.println("No Need For confirmation");
		}
	}

	public void gotoPlateRenewal() throws InterruptedException {
		System.out.println("----------- gotoPlateRenewal ------------ ");
		waitForElement(plateRenewalBtn2);
		clickElementJS(plateRenewalBtn2);
		Thread.sleep(5000);
		try {
			clickElement(verifyAllBtn);
			Thread.sleep(5000);
		} catch (Exception e) {
			System.out.println("No Need For confirmation");
		}
	}

	public void gotoMainService(String service) throws InterruptedException {
		waitForElement(trafficOwnerNamelbl);
		Thread.sleep(1000);
		if (service.equals("تسفير بدل فاقد") || service.contains("ممانعة من نقل ملف")) {
			Thread.sleep(3000);
		}
		if (driver.findElement(servicesBtnHolder).getAttribute("class").toString().contains("button-holder"))
			try {
				clickElementJS(servicesBtn1);
			} catch (Exception e) {
				clickElementJS(servicesBtn2);
			}
		else
			clickElementJS(servicesBtn2);
		Thread.sleep(1000);

		switchToFrame(availableServisesFrame);
		Thread.sleep(1000);
		writeToElement(availableServicesTxtbox, service);
		Thread.sleep(1000);
		clickElement(serviceLnk);
		try {
			tryClickElement(verifyAllBtn, 5);
			Thread.sleep(5000);
		} catch (Exception e) {
			System.out.println("No Need For confirmation");
		}
	}

	public void printApplicationForm() throws InterruptedException, AWTException {
		System.out.println("----------- Print Application Form ------------");
		waitForElement(By.xpath("//*[@id='printApplicationFormId']"));
		clickElementJS(By.xpath("//*[@id='printApplicationFormId']"));
		Thread.sleep(1000);
		String firstWindow = switchToSecondWindow();
		closeCurrentWindow();
		Thread.sleep(1000);
		// uploadFile("UserInfo.pdf");
		switchToWindow(firstWindow);
	}

	public void printAfterPayment(String fileName) throws InterruptedException, AWTException {
		Thread.sleep(2000);
		String firstWindow = switchToSecondWindow();
		Thread.sleep(2000);
		uploadFile(fileName);
		switchToWindow(firstWindow);
	}

	public void printApplicationFormOld() throws InterruptedException {
		waitForElement(By.xpath("//*[@id='printApplicantFormButton']"));
		clickElementJS(By.xpath("//*[@id='printApplicantFormButton']"));
		Thread.sleep(2000);
	}

	public void goToPayment() throws InterruptedException {
		System.out.println("----------- Checkbox the Go to Payment ------------");
		waitForElement(By.id("masterCheckbox"));
		clickElementJS(By.id("masterCheckbox"));
		clickElementJS(By.id("doPaymentId"));
		Thread.sleep(3000);
	}

	public void payFTF() throws InterruptedException {
		System.out.println("----------- Payemnet: FTF ------------");
		waitForElement(By.xpath("//*[@class='dropdown-menu inner selectpicker']/li[2]/a"));
		// System.out.println("Trs ID: " +
		// getElementText(By.xpath("//*[@id='mainInvoice']/div[1]/div[2]")));
		try {
			// System.out.println("Payment (try): "
			// + getElementText(By.xpath("//*[@class='dropdown-menu inner
			// selectpicker']/li[4]/a/span[1]")));
			clickElementJS(By.xpath("//*[@class='dropdown-menu inner selectpicker']/li[4]/a"));
		} catch (Exception e) {
			// System.out.println("Payment (catch): "
			// + getElementText(By.xpath("//*[@class='dropdown-menu inner
			// selectpicker']/li[2]/a/span[1]")));
			clickElementJS(By.xpath("//*[@class='dropdown-menu inner selectpicker']/li[2]/a"));

		}
		Thread.sleep(2000);
		fees = driver.findElement(By.id("transactionTotalcash")).getText();
		System.out.println("Fees : " + fees);
		clickElementJS(By.id("receivedAmount"));
		driver.findElement(By.id("receivedAmount")).sendKeys(fees);
		clickElementJS(By.id("proceedToPaymentId"));
		Thread.sleep(2000);
		clickElementJS(By.xpath("//*[@class='confirm']"));
		Thread.sleep(4000);
	}

	public String getTransactionId() throws InterruptedException {
		// waitForElement(By.xpath("//*[@class='dropdown-menu inner
		// selectpicker']/li[2]/a"));
		waitForElement(By.xpath("//*[@id='mainInvoice']/div[1]/div[2]"));
		tryClickElement(By.xpath("//*[@id='mainInvoice']/div[1]/div[2]"));
		Thread.sleep(1000);
		String elementTxt = getElementText(By.xpath("//*[@id='mainInvoice']/div[1]/div[2]"));
		// System.out.println("Transaction Id = " + elementTxt);
		return elementTxt.substring(elementTxt.indexOf('(') + 1, elementTxt.indexOf(')'));
	}

	public void confirmFreeTransaction() throws InterruptedException {
		waitForElement(By.id("proceedToPaymentId"));
		System.out.println("Trs ID: " + getElementText(By.xpath("//*[@id='mainInvoice']/div[1]/div[2]")));
		clickElementJS(By.id("proceedToPaymentId"));
		Thread.sleep(2000);
	}

	public boolean brIsShown(String br) throws InterruptedException {
		Thread.sleep(2000);
		if (driver.findElement(businessRule).isDisplayed()) {
			if (getElementText(businessRule).contains(br))

				return true;
			else {
				System.out.println("Different BR was shown:");
				System.out.println("Expected : " + br);
				System.out.println("Actual : " + getElementText(businessRule));
				return false;
			}
		} else
			return false;
	}

	public void payFTFOld() throws InterruptedException, AWTException {
		System.out.println("click payment button");
		waitForElement(By.id("transButton"));
		clickElementJS(By.id("transButton"));
		System.out.println("Payment button clicked");
		// java.awt.Robot robot = new java.awt.Robot();
		// robot.keyPress(KeyEvent.VK_ENTER);
		// System.out.println("Enter clicked");
		Thread.sleep(1000);
		// System.out.println("Payment alert waiting id done.");
		// acceptAlert();
		Set<String> handles = driver.getWindowHandles();
		System.out.println("Payment handles: " + handles.size());

		String transactionWinHandle = switchToThirdWindow();

		// java.awt.Robot robot = new java.awt.Robot();
		// robot.keyPress(KeyEvent.VK_ENTER);
		// System.out.println("VK_ENTER");
		// String transactionWinHandle = switchToThirdWindow();
		// driver.findElement(By.xpath("//*class='print-labels red']"));
		waitForElement(By.id("receivedAmountId"));
		writeToElementWithoutClear(By.id("receivedAmountId"), "3290");
		clickElementJS(By.id("payButton"));
		Thread.sleep(4000);
		java.awt.Robot robot = new java.awt.Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		System.out.println("Enter clicked");
		switchToWindow(transactionWinHandle);
		// System.out.println("Trs ID: " +
		// getElementText(By.xpath("//*[@id='mainInvoice']/div[1]/div[2]")));
		// clickElementJS(By.xpath("//*[@class='dropdown-menu inner
		// selectpicker']/li[4]/a"));
		// Thread.sleep(2000);
		// fees = driver.findElement(By.id("transactionTotalcash")).getText();
		// System.out.println("---- Fees ---- : " + fees);
		// clickElementJS(By.id("receivedAmount"));
		// driver.findElement(By.id("receivedAmount")).sendKeys(fees);
		// clickElementJS(By.id("proceedToPaymentId"));
		// Thread.sleep(2000);
		// clickElementJS(By.xpath("//*[@class='confirm']"));
		// Thread.sleep(4000);
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

	public void searchTrafficFilePlates(String plateNumber) {
		int platesCount = driver.findElements(By.xpath("//*[@class='number']")).size();
		List<WebElement> plates = driver.findElements(By.xpath("//*[@class='number']"));
		// List<WebElement> codes = driver.findElements(By.xpath("//*[@class='code']"));
		int plateIndex = 0;
		for (int i = 0; i < platesCount; i++) {
			if (plates.get(i).getText().equals(plateNumber)) {
				plateIndex = i;
				break;
			}
		}
		System.out.println("plateIndex: " + plateIndex);

		// clickElementJS(driver.findElements(By.xpath("//*[@id='customerBooklets']/div/button")).get(plateIndex));

	}

	public void clickNextArrow() throws InterruptedException {
		waitForElement(By.xpath("//*[@class='owl-prev']"));
		Thread.sleep(1000);
		clickElementJS(By.xpath("//*[@class='owl-prev']"));
	}

	public void selectPlateDesign_PStrategy(PlateDesign plateDesign) throws InterruptedException {
		waitForElement(By.linkText("تغيير التصميم"));
		switch (plateDesign) {
		case New:
			clickByLinkTxt("تغيير التصميم");
			break;
		case Current:
			clickByLinkTxt("التصميم الحالي");
			break;
		default:
			clickByLinkTxt("تغيير التصميم");
			break;
		}
	}

	public void selectNewPlateSource_PStrategy(String plateSource) throws InterruptedException {
		System.out.println("----------- Select Plate Source ------------");
		Thread.sleep(1000);
		clickElement(openSelectPlateBtn);
		Thread.sleep(1000);
		String parentHandle = driver.getWindowHandle();
		switchToFrame("plateSelectionIframeId");
		waitForElement(plateSourceLst);
		try {
			selectFromListByVisibleText(plateSourceLst, plateSource);
		} catch (Exception e) {
		}
		clickElementJS(selectPlateBtn);

		Thread.sleep(1000);
		driver.switchTo().window(parentHandle);
	}

	public void clickCertifyBtn() throws InterruptedException {
		Thread.sleep(1000);
		System.out.println("Click Proceed After Plate Source Selection");
		clickElement(certifyLnk);
		Thread.sleep(1000);
	}

	// public void selectNewPlates_PStrategy(boolean logo, PlateSize frontPlate,
	// PlateSize backPlate)
	// {
	// waitForElement(By.linkText("نعم"));
	// if (logo) {
	// clickByLinkTxt("نعم");
	// selectFrontPlate_PStrategy(frontPlate, true);
	// selectBackPlate_PStrategy(backPlate, true);
	// } else {
	// clickByLinkTxt("لا");
	// selectFrontPlate_PStrategy(frontPlate, false);
	// selectBackPlate_PStrategy(backPlate, false);
	// }
	// waitForElement(By.xpath("//*[@class='confirm']"));
	// clickElementJS(By.xpath("//*[@class='confirm']"));
	// }
	public void selectNewPlates_PStrategy(boolean logo, PlateSize frontPlate, PlateSize backPlate)
			throws InterruptedException {
		System.out.println("----------- Select New Plates Design------------");
		addLogoStatus(logo);

		Thread.sleep(2000);
		selectFrontPlate_PStrategy(frontPlate, logo);
		selectBackPlate_PStrategy(backPlate, logo);
		Thread.sleep(1000);
		waitForElement(By.xpath("//*[@class='confirm']"));
		clickElementJS(By.xpath("//*[@class='confirm']"));
	}

	public void selectNewPlates_PStrategy(PlateSize frontPlate, PlateSize backPlate) throws InterruptedException {
		Thread.sleep(2000);
		selectFrontPlate_PStrategy(frontPlate, false);
		selectBackPlate_PStrategy(backPlate, false);
		Thread.sleep(1000);
		waitForElement(By.xpath("//*[@class='confirm']"));
		clickElementJS(By.xpath("//*[@class='confirm']"));
	}

	public void selectNewPlates_PStrategy(PlateSize backPlate) throws InterruptedException {
		Thread.sleep(2000);
		selectBackPlate_PStrategy(backPlate, false);
		Thread.sleep(1000);
		waitForElement(By.xpath("//*[@class='confirm']"));
		clickElementJS(By.xpath("//*[@class='confirm']"));
	}

	private void addLogoStatus(boolean logo) throws InterruptedException {
		waitForElement(By.linkText("نعم"));
		if (logo)
			clickByLinkTxt("نعم");
		else
			clickByLinkTxt("لا");

	}

	public void selectCollectionCenter_PStrategy(String collectionCenter) throws InterruptedException {
		waitForElement(By.xpath("//*[@class='plate-collection-center-title animated bounceInUp']"));
		Thread.sleep(1000);
		tryClickElement(By.partialLinkText(collectionCenter));
	}

	private void selectFrontPlate_PStrategy(PlateSize frontPlate, boolean logo) throws InterruptedException {
		Thread.sleep(1000);
		if (logo) {

			switch (frontPlate) {
			case Long:
				tryClickElement(By.id("plate-button-front-with-logo-1"));
				break;
			case Short:
				tryClickElement(By.id("plate-button-front-with-logo-2"));
				break;
			case Luxury:
				tryClickElement(By.id("plate-button-front-with-logo-3"));
				break;
			default:
				tryClickElement(By.id("plate-button-front-with-logo-1"));
				break;
			}
		} else {

			switch (frontPlate) {
			case Long:
				tryClickElement(By.id("plate-button-front-1"));
				break;
			case Short:
				tryClickElement(By.id("plate-button-front-2"));
				break;
			case Luxury:
				tryClickElement(By.id("plate-button-front-3"));
				break;
			default:
				clickElementJS(By.id("plate-button-front-1"));
				break;
			}
		}
	}

	private void selectBackPlate_PStrategy(PlateSize backPlate, boolean logo) throws InterruptedException {
		Thread.sleep(1000);
		if (logo) {
			switch (backPlate) {
			case Long:
				clickElementJS(By.id("plate-button-rear-with-logo-1"));
				break;
			case Short:
				clickElementJS(By.id("plate-button-rear-with-logo-2"));
				break;
			default:
				clickElementJS(By.id("plate-button-rear-with-logo-1"));
				break;
			}
		} else {
			switch (backPlate) {
			case Long:
				clickElementJS(By.id("plate-button-rear-1"));
				break;
			case Short:
				clickElementJS(By.id("plate-button-rear-2"));
				break;
			default:
				clickElementJS(By.id("plate-button-rear-1"));
				break;
			}
		}
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

	public void clickContinue_PStrategy() throws InterruptedException {
		waitForElement(By.id("createTransactionBtnId"));
		Thread.sleep(3000);
		tryClickElement(By.id("createTransactionBtnId"));
	}

	public void selectDeliveryDate_PStrategy() throws InterruptedException {
		waitForElement(By.xpath("//*[@class='btn btn-default btn-block']"));
		Thread.sleep(1000);
		clickElementJS(By.xpath("//*[@class='btn btn-default btn-block']"));

	}

	public void waitImmediateDeliveryBtn() throws InterruptedException {
		waitForElement(By.linkText("سوف يتم تسليم اللوحات فورا"));
		Thread.sleep(1000);
	}

	public void selectLostPlate_PStrategy(boolean logo, ReplacedPlate replacedPlate, PlateSize frontPlate,
			PlateSize backPlate) throws InterruptedException {
		addLogoStatus(logo);
		waitForElement(By.id("onePlateLost"));
		tryClickElement(By.id("onePlateLost"));

		// if (replacedPlate.equals(ReplacedPlate.Front))
		// selectFrontPlate_PStrategy(plateSize, logo);
		// else
		// selectBackPlate_PStrategy(plateSize, logo);
		selectFrontPlate_PStrategy(frontPlate, logo);
		selectBackPlate_PStrategy(backPlate, logo);
		Thread.sleep(1000);
		waitForElement(By.xpath("//*[@class='confirm']"));
		clickElementJS(By.xpath("//*[@class='confirm']"));
	}

	public void clickNextHeader() throws InterruptedException {
		waitForElement(By.xpath("//*[@class='owl-next']"));
		clickElementJS(By.xpath("//*[@class='owl-next']"));
	}

	public void skipLogoStep() throws InterruptedException {
		waitForElement(By.partialLinkText("إستمرار"));
		clickByLinkTxt("إستمرار");
	}

	public void payFTFCashDRL() throws InterruptedException {
		waitForElement(By.xpath("//*[@class='dropdown-menu inner selectpicker']/li[2]/a"));
		try {
			clickElementJS(By.xpath("//*[@class='dropdown-menu inner selectpicker']/li[3]/a"));
		} catch (Exception e) {
			clickElementJS(By.xpath("//*[@class='dropdown-menu inner selectpicker']/li[3]/a"));

		}
		Thread.sleep(2000);
		fees = driver.findElement(By.id("transactionTotalcash")).getText();
		clickElementJS(By.id("receivedAmount"));
		driver.findElement(By.id("receivedAmount")).sendKeys(fees);
		clickElementJS(By.id("proceedToPaymentId"));
		Thread.sleep(2000);
		clickElementJS(By.xpath("//*[@class='confirm']"));
		Thread.sleep(4000);
	}

	public boolean transactionFeesAssertion(int amount) {
		// System.out.println("fees = " + fees);
		int feesAmount = Integer.parseInt(fees);
		if(feesAmount == amount)
			return true;
			else return false;
	}

	public void closeBRAlert() throws InterruptedException {
		clickElementJS(By.xpath("//*[@class='btn btn-link btn-dismiss c-white']"));
	}

	public boolean isBRFired() throws InterruptedException {
		// Thread.sleep(2000);
		try {
			if (driver.findElement(By.xpath("//*[@class='c-white alert-message']")).isDisplayed() && !getBRText().contains("حدث خطأ أثناء الإستعلام عن المركبه في الجمارك"))
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isBRFiredRegistration() throws InterruptedException {
		Thread.sleep(1000);
		if (isAtVehicleDetailsStep())
			return false;
		else {
			if (isBRFired())
				return true;
			else
				return isBRFiredRegistration();
		}
	}

	public boolean isBRShownRegistration() throws InterruptedException {
		Thread.sleep(1000);
		if (isAtDocumentCheckStep())
			return false;
		else {
			if (isBRFired())
				return true;
			else
				return isBRShownRegistration();
		}
	}

	public boolean isBRShown() throws InterruptedException {
		Thread.sleep(1000);
		if (isAtPlateDesignStep())
			return false;
		else {
			if (isAtDocumentCheckStep())
				return false;
			else {
				if (isAtPaymentStep())
					return false;
				else {
					if (isBRFired())
						return true;
					else
						return isBRShown();
				}
			}
		}
	}

	public boolean isBRRenewalWithChangePlate() throws InterruptedException {
		Thread.sleep(1000);
		if (isAtPlateDesignStep())
			return false;
		else {
			if (isBRFired())
				return true;
			else
				return isBRShown();
		}
	}

	public boolean isAtVehicleDetailsStep() throws InterruptedException {
		try {
			driver.findElement(By.xpath("//*[@class='c-white alert-message']")).isDisplayed();
			return true;
		} catch (Exception printEx) {
			//System.out.println("Exception: IsAtVehicleDetailsStep");
			return false;
		}
	}

	public boolean isAtPlateDesignStep() throws InterruptedException {
		try {
			driver.findElement(By.id("changePlateDesignServiceId")).isDisplayed();
			return true;
		} catch (Exception printEx) {
			//System.out.println("Exception: IsAtPlateDesignStep");
			return false;
		}
	}

	public boolean isAtDocumentCheckStep() throws InterruptedException {
		try {
			driver.findElement(By.id("masterCheckbox")).isDisplayed();
			return true;
		} catch (Exception printEx) {
			//System.out.println("Exception: IsAtDocumentCheckStep");
			return false;
		}
	}

	public boolean isAtPaymentStep() throws InterruptedException {
		try {
			driver.findElement(By.id("transactionTotalcash"));
			return true;
		} catch (Exception printEx) {
			//System.out.println("Exception: IsAtPaymentStep");
			return false;
		}
	}

	public String getBRText() throws InterruptedException {
		// TODO Auto-generated method stub c-white alert-message
		return getElementText(By.xpath("//*[@class='c-white alert-message']"));
	}
}
