package isoft.etraffic.ctapages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.qameta.allure.Step;
import isoft.etraffic.wrapper.SeleniumWraper;

public class UpdateTLpage extends SeleniumWraper {
 
	private By serviceLink = By.xpath("//a[contains(@href,'serviceCode=805')]");
	private By letterNo = By.id("newCompanyNOCLetterNumberId");
	private By appNo = By.id("newPermitAppRefNoId");
	private By searchBtn = By.id("btnSearchCompanyDetails"); 
	private By arNametxt = By.id("tradeNameAId");
	private By enNametxt = By.id("tradeNameEId");
	private By TLtxt = By.id("tradeLicenseNoId");
	private By TLsourcedrp = By.id("select2-licenseSourceId-container");
	private By TLsourceSelect = By.xpath("/html/body/span/span/span[2]/ul/li[2]");
	private By TLIssuedate = By.id("tradeLicenseIssueDateId");
	private By TLexpirydate = By.id("tradeLicenseExpiryDateId");
	private By MobileCodeDrp = By.id("select2-mobileCodeId-container");
	private By MobileCodeSelect = By.xpath("/html/body/span/span/span[2]/ul/li[3]");
	private By mobileNoId = By.id("mobileNoId");
	private By phoneNoId = By.id("phoneNoId");
	private By emailId = By.id("emailId");
	private By postOfficeBoxId = By.id("postOfficeBoxId");
	private By addrestxt = By.xpath("//textarea[@class='form-control']");
	private By TLcopy = By.id("tradeLicenseCopyId");
	private By submitBtn = By.id("btnProceed");

	WebDriverWait wait;
	GeneralPages GP = new GeneralPages(driver);

	@Step("Search for company using certificate number : {0} , and Application number : {1} ")
	public void searchForComp(String certification, String order) throws InterruptedException {

		clickElement(serviceLink);
		GP.applyForService();
		writeToElement(letterNo, certification);
		writeToElement(appNo, order);
		clickElement(searchBtn);
	}

	@Step("Fill company data for new trade license")
	public void fillTLData(String order, String ArName, String enName, String issuedate, String expirydate,
			String mobileNo, String phoneNo, String email, String postOffice, String address)
			throws InterruptedException {

		writeToElement(arNametxt, ArName);
		writeToElement(enNametxt, enName);
		writeToElement(TLtxt, order);
		clickElement(TLsourcedrp);
		//wait.until(ExpectedConditions.elementToBeClickable(TLsourceSelect));
		clickElement(TLsourceSelect);
		writeToElement(TLIssuedate, issuedate);
		writeToElement(TLexpirydate, expirydate);
		clickElement(MobileCodeDrp);
		//wait.until(ExpectedConditions.elementToBeClickable(MobileCodeSelect));
		clickElement(MobileCodeSelect);
		writeToElement(mobileNoId, mobileNo);
		writeToElement(phoneNoId, phoneNo);
		writeToElement(emailId, email);
		writeToElement(postOfficeBoxId, postOffice);
		writeToElement(addrestxt, address);
		Thread.sleep(1000);

		FileInputElement(TLcopy, System.getProperty("user.dir") + "//attachments//Lighthouse.jpg");
	}

	@Step("Submit update transaction ")
	public void submitTRX() throws InterruptedException {
		clickElement(submitBtn);

		Thread.sleep(2000);
	}

	public UpdateTLpage(WebDriver driver) {
		super(driver);

	}

	@Override
	public boolean isPageLoaded() {

		return false;
	}

}
