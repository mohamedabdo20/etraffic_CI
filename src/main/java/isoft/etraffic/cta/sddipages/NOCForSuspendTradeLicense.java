package isoft.etraffic.cta.sddipages;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.helpers.DateTimeDateFormat;
import org.apache.poi.util.SystemOutLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.qameta.allure.Step;
import isoft.etraffic.wrapper.SeleniumWraper;

public class NOCForSuspendTradeLicense extends SeleniumWraper{

	
	WebDriverWait wait = new WebDriverWait(driver, 15, 100);
	private By serviceLink = By.xpath("//a[contains(@href,'serviceCode=825')]");
	private By TFtxt = By.id("trafficFileNoId");
	private By TLtxt = By.id("tradeLicenceId");
	private By ComSearchBtn = By.id("btnSearchCompanyDetails");
	private By StartSuspendDate = By.id("startSuspendDateId");
	private By SuspendYears = By.id("select2-suspendYearsCountId-container");
	private By SuspendYearsCount = By.xpath("/html/body/span/span/span[2]/ul/li[3]");
	private By AttachTL = By.id("commercialLicenseApplicationsAttachmentCopyId");
	private By AttachLetter = By.id("ltrOfSuspendFromOwnerOrManagerCopyId");
	private By OpenLink = By.xpath("/html/body/form/div[1]/div[2]/div/div/div[11]/div/div/a");                                                                 
	private By ApprovalCheck = By.id("signElecObligationForSuspendPermitId");
	private By Proceed = By.id("btnCompanyProfileProceed");
	private By Submit = By.xpath("/html/body/form/div[2]/div[4]/div[2]/div/div/div[2]/div/a/em");

	CtaCommonPages GP = new CtaCommonPages(driver);
	
	
	
	
	
	@Step("Initiate suspend trade license service")
	public void InitiateService() throws InterruptedException {
		System.out.println();
		clickElement(serviceLink);
		GP.applyForService();
	}

	@Step("Search for company using traffic number {0} , and trade licesnse number {1}")
	public void searchForCompany(String TF, String TL) throws InterruptedException {

		writeToElement(TFtxt, TF);
		writeToElement(TLtxt, TL);
		clickElement(ComSearchBtn);

	}
		
		@Step("Fill transaction data ")
		public void filltransactionsdataforsuspend() throws InterruptedException {

			//clickElement(serviceLink);
			String Startdate = getsysdateplusmonths();
			System.out.println(Startdate);			
			writeToElement(StartSuspendDate, "20/10/2019");
			clickElement(SuspendYears);
			clickElement(SuspendYearsCount);
			FileInputElement(AttachTL, System.getProperty("user.dir")+"//attachments//Lighthouse.jpg");
			FileInputElement(AttachLetter, System.getProperty("user.dir")+"//attachments//Lighthouse.jpg");
			clickElement(OpenLink);
			clickElement(ApprovalCheck);
			clickElement(Proceed);
			clickElement(Submit);
		}

		public String getsysdateplusmonths() {
			 LocalDateTime today = LocalDateTime.now();
			 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/mm/yyyy");
			 System.out.println(today);
			 String oneMonthLater = today.plusMonths(1).format(formatter);
			return oneMonthLater;
		}



		public NOCForSuspendTradeLicense(WebDriver driver) {
			super(driver);
	}
		
		public String TraxID() {

			String trsNo;

			RemoteWebElement transactionElm = (RemoteWebElement) 
					driver.findElement(By.xpath("//strong[contains(.,'Transaction number')]"));
			trsNo = transactionElm.getText().trim().split(":")[1];

			return trsNo;
		}

		public String AppNo() {

			String ordNo;
			RemoteWebElement orderNoElm = (RemoteWebElement) driver
					.findElement(By.xpath("//strong[contains(.,'Application Reference Number')]"));
			ordNo = orderNoElm.getText().trim().split(":")[1];

			return ordNo;
		}


	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
