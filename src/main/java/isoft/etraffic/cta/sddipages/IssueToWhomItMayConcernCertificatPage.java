package isoft.etraffic.cta.sddipages;

import java.sql.SQLException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import isoft.etraffic.db.CtaDBQueries;
import isoft.etraffic.wrapper.SeleniumWraper;

public class IssueToWhomItMayConcernCertificatPage extends SeleniumWraper {

	private By ToWhomConcern = By.xpath("//a[contains(@href,'serviceCode=822')]");
	private By trafficFileTxt = By.id("trafficFileNoId");
	private By tradeLTxt = By.id("tradeLicenceId");
	private By searchComBtn = By.id("btnSearchCompanyDetails");
	private By proceedbtn = By.id("btnCompanyProfileProceed");

	public void IssueToWhomItMayConcern() throws InterruptedException, ClassNotFoundException, SQLException {
		clickElement(ToWhomConcern);
		CtaCommonPages GP = new CtaCommonPages(driver);
		GP.applyForService();
		CtaDBQueries getTFTL = new CtaDBQueries();
		getTFTL.getGeneralTFandTL();
		String Traffic_file = getTFTL.TRF;
		String Trade_license = getTFTL.TL;
		writeToElement(trafficFileTxt, Traffic_file);
		writeToElement(tradeLTxt, Trade_license);
		clickElement(searchComBtn);
		clickElement(proceedbtn);

	}

	public IssueToWhomItMayConcernCertificatPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

}
