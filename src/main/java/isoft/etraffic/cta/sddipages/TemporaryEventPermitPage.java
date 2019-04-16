package isoft.etraffic.cta.sddipages;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebElement;

import isoft.etraffic.db.CtaDBQueries;
import isoft.etraffic.wrapper.SeleniumWraper;

public class TemporaryEventPermitPage extends SeleniumWraper {

	private By TemoraryEvent = By.xpath("//a[contains(@href,'serviceCode=827')]");
	private By trafficFileTxt = By.id("trafficFileNoId");
	private By tradeLTxt = By.id("tradeLicenceId");
	private By searchComBtn = By.id("btnSearchCompanyDetails");
	private By PerimtStartDate = By.id("perimtStartDateId");
	private By NoOfWeeks = By.id("pertmitPeriodId");
	private By EventDiscreption = By.id("eventDescriptionId");
	private By TradeLicenseCopy = By.id("tradeLicenseCopyId");
	private By AuthorizedCertificateCopy = By.id("authorizedCertificateCopyId");
	private By MoreDetailsCopy = By.id("moreDetailsCopyId");
	private By ObligationLink = By.id("obligationLinkId");
	private By SignElecObligation = By.id("signElecObligationId");
	private By BTNSearchCompanyDetails = By.id("btnSearchCompanyDetails");
	private By submitFeesBtn = By.id("saveResumeButton");

	public void temporaryEventPermitPage() throws InterruptedException, ClassNotFoundException, SQLException {

		clickElement(TemoraryEvent);
		CtaCommonPages GP = new CtaCommonPages(driver);
		GP.applyForService();
		CtaDBQueries TemporaryEventobject = new CtaDBQueries();
		TemporaryEventobject.getTFandTLForTemporaryEvent();
		String TrafficFile = TemporaryEventobject.TRF;
		String TradeLicense = TemporaryEventobject.TL;
		writeToElement(trafficFileTxt, TrafficFile);
		writeToElement(tradeLTxt, TradeLicense);
		clickElement(searchComBtn);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Calendar cal = Calendar.getInstance();
		String startdate = sdf.format(cal.getTime());
		writeToElement(PerimtStartDate, startdate);
		selectFromListByValue(NoOfWeeks, "1");
		writeToElement(EventDiscreption, "Create New Event");
		FileInputElement(TradeLicenseCopy, System.getProperty("user.dir") + "//attachments//Lighthouse.jpg");
		FileInputElement(AuthorizedCertificateCopy, System.getProperty("user.dir") + "//attachments//Lighthouse.jpg");
		FileInputElement(MoreDetailsCopy, System.getProperty("user.dir") + "//attachments//Lighthouse.jpg");
		clickElement(ObligationLink);
		clickElement(SignElecObligation);
		clickElement(BTNSearchCompanyDetails);
		clickElement(submitFeesBtn);

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
		ordNo = orderNoElm.getText().trim().split(": ")[1];

		return ordNo;
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

	public TemporaryEventPermitPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

}
