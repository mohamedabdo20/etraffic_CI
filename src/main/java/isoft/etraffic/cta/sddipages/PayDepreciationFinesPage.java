package isoft.etraffic.cta.sddipages;

import java.sql.SQLException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.SeleniumWraper;

public class PayDepreciationFinesPage extends SeleniumWraper {

	// private WebDriver driver;
	String TrafficFile;
	String TradeLicense;

	private By PayfinesLink = By.xpath("//a[contains(@href,'serviceCode=815')]");
	private By trafficFileTxt = By.id("trafficFileNoId");
	private By tradeLTxt = By.id("tradeLicenceId");
	private By searchComBtn = By.id("btnSearchCompanyDetails");
	private By proceedBtn = By.id("btnProceed");
	CtaCommonPages GP = new CtaCommonPages(driver);

	public void paydepreciationfines(String Traffic_file, String Trade_license)
			throws InterruptedException, ClassNotFoundException, SQLException {

		clickElement(PayfinesLink);
		GP.applyForService();
		writeToElement(trafficFileTxt, Traffic_file);
		writeToElement(tradeLTxt, Trade_license);
		clickElement(searchComBtn);
		clickElement(proceedBtn);

	}

	public PayDepreciationFinesPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

}
