package isoft.etraffic.cta.sddipages;

import java.sql.SQLException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import io.qameta.allure.Step;
import isoft.etraffic.wrapper.SeleniumWraper;

public class ReprintDocumentPage extends SeleniumWraper {

	private By reprintDocLink = By.xpath("//a[contains(@href,'serviceCode=807')]");
	String traffic_file;
	String Trade_license;
	CtaCommonPages GP = new CtaCommonPages(driver);
	private By trafficFileTxt = By.id("trafficFileNoId");
	private By tradeLTxt = By.id("tradeLicenceId");
	private By searchComBtn = By.id("btnSearchCompanyDetails");
	private By documentType = By.xpath("//span[@id='select2-documentTypeId-container']");
	private By documentTypeSelect = By.xpath("/html/body/span/span/span[2]/ul/li[11]");
	private By reprintReason = By.xpath("//span[@id='select2-reprintReasonId-container']");
	private By reprintReasonSelect = By.xpath("/html/body/span/span/span[2]/ul/li[2]");
	private By proceedbtn = By.id("btnReprintDocumentProceed");

	@Step("Initiate reprint document service for traffic file : {0} , trade license : {1}")
	public void reprintdocumentInitiate(String Traffic_file, String Trade_license)
			throws InterruptedException, ClassNotFoundException, SQLException {

		clickElement(reprintDocLink);
		GP.applyForService();

		writeToElement(trafficFileTxt, Traffic_file);
		writeToElement(tradeLTxt, Trade_license);
		clickElement(searchComBtn);
		Thread.sleep(1000);

		clickElement(documentType);
		clickElement(documentTypeSelect);
		Thread.sleep(1000);
		clickElement(reprintReason);
		clickElement(reprintReasonSelect);
		Thread.sleep(1000);
		clickElement(proceedbtn);

	}

	public ReprintDocumentPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

}
