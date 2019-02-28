package isoft.etraffic.ctapages;

import java.sql.SQLException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.qameta.allure.Step;
import isoft.etraffic.enums.*;
import isoft.etraffic.wrapper.SeleniumWraper;

public class NewTLNOCPage extends SeleniumWraper {
 
	private By NewNOCLink = By.xpath("//a[contains(@href,'serviceCode=801')]");
	private By requestIssuertxt = By.id("requestIssuerId");
	private By mobileCodetxt = By.xpath("//span[@id='select2-mobileCodeId-container']");
	private By mobileCodeSelect = By.xpath("/html/body/span/span/span[2]/ul/li[3]");
	private By mobileNotxt = By.id("mobileNoId");
	private By branchCheck = By.id("branchForExistingOrgId");
	private By trafficFileNotxt = By.id("trafficFileNoId");
	private By tradeLicenceNotxt = By.id("tradeLicenceNoId");
	private By CompanyProfileSearchbtn = By.id("btnNewCompanyProfileSearch");
	private By branchTypetxt = By.id("select2-branchTypeId-container");
	private By branchTypeCounterSelect = By.xpath("/html/body/span/span/span[2]/ul/li[4]");
	private By branchTypeBranchSelect = By.xpath("/html/body/span/span/span[2]/ul/li[3]");
	private By LegalClasstxt = By.id("select2-orgLegalClassId-container");
	private By LegalClassSelect = By.xpath("/html/body/span/span/span[2]/ul/li[2]");
	private By RepClasstxt = By.id("select2-orgRepClassId-container");
	private By RepClassSelect = By.xpath("/html/body/span/span/span[2]/ul/li[3]");
	private By tradeLicenseIssuertxt = By.id("select2-tradeLicenseIssuerId-container");
	private By tradeLicenseIssuerselect = By.xpath("/html/body/span/span/span[2]/ul/li[2]");
	private By issueDatetxt = By.id("issueDateId");
	private By confirmAndProceedbtn = By.id("btnNewCompanyProfileSubmit");
	private By CompanyProfileProceedbtn = By.id("btnCompanyProfileProceed");
	private By saveResumeButton = By.id("saveResumeButton");
	private By obligationLink = By.id("obligationLink");
	private By agreeObligationCheck = By.id("signElecObligationForNewPermit");
	private By returnToDashBoardbtn = By.id("returnToDashBoard");
	// private WebDriver driver;
	Obligation obligation;
	WebDriverWait wait;
	GeneralPages GP = new GeneralPages(driver);

	@Step("Create new NOC for new trade license - Fill NOC form data ")
	public void NewNOCFill(String requestIssuer, String mobileNo, String issueDate)
			throws InterruptedException, ClassNotFoundException, SQLException {
		
		clickElement(NewNOCLink);
		GP.applyForService();
		writeToElement(requestIssuertxt, requestIssuer);
		clickElement(mobileCodetxt);
		clickElement(mobileCodeSelect);
		writeToElement(mobileNotxt, mobileNo);
		clickElement(LegalClasstxt);
		clickElement(LegalClassSelect);
		clickElement(RepClasstxt);
		clickElement(RepClassSelect);
		clickElement(tradeLicenseIssuertxt);
		clickElement(tradeLicenseIssuerselect);
		writeToElement(issueDatetxt, issueDate);
		clickElement(confirmAndProceedbtn);

	}
	public void NewNOCCounterFill(String TF ,String TL ,String requestIssuer, String mobileNo, String issueDate)
			throws InterruptedException, ClassNotFoundException, SQLException {

		clickElement(NewNOCLink);
		GP.applyForService();
		writeToElement(requestIssuertxt, requestIssuer);
		clickElement(mobileCodetxt);
		clickElement(mobileCodeSelect);
		writeToElement(mobileNotxt, mobileNo);
		clickElement(branchCheck);
		writeToElement(trafficFileNotxt, TF);
		writeToElement(tradeLicenceNotxt, TL);
		clickElement(CompanyProfileSearchbtn);
		clickElement(branchTypetxt);
		clickElement(branchTypeCounterSelect);
		clickElement(tradeLicenseIssuertxt);
		clickElement(tradeLicenseIssuerselect);
		writeToElement(issueDatetxt, issueDate);
		clickElement(confirmAndProceedbtn);

	}
	public void NewNOCBranchFill(String TF ,String TL ,String requestIssuer, String mobileNo, String issueDate)
			throws InterruptedException, ClassNotFoundException, SQLException {

		clickElement(NewNOCLink);
		GP.applyForService();
		writeToElement(requestIssuertxt, requestIssuer);
		clickElement(mobileCodetxt);
		clickElement(mobileCodeSelect);
		writeToElement(mobileNotxt, mobileNo);
		clickElement(branchCheck);
		writeToElement(trafficFileNotxt, TF);
		writeToElement(tradeLicenceNotxt, TL);
		clickElement(CompanyProfileSearchbtn);
		clickElement(branchTypetxt);
		clickElement(branchTypeBranchSelect);
		clickElement(tradeLicenseIssuertxt);
		clickElement(tradeLicenseIssuerselect);
		writeToElement(issueDatetxt, issueDate);
		clickElement(confirmAndProceedbtn);

	}
	@Step("Submit new NOC transaction for new trade license")
	public void submitTransaction(String obligation) throws InterruptedException {

		switch (obligation) {
		case "No":
			
			clickElement(CompanyProfileProceedbtn);
			waitForElement(saveResumeButton);
			clickElement(saveResumeButton);

			break;
		case "Yes":
			
			waitForElement(obligationLink);
			clickElement(obligationLink);
			clickElement(agreeObligationCheck);
			clickElement(CompanyProfileProceedbtn);
			waitForElement(saveResumeButton);
			clickElement(saveResumeButton);

		default:
			break;
		}

	}

	public void backtoDashboard() throws InterruptedException {
		
		clickElement(returnToDashBoardbtn);
	}

	public String TraxID() {

		String trsNo;

		RemoteWebElement transactionElm = (RemoteWebElement) 
				driver.findElement(By.xpath("//strong[contains(.,'Transaction number')]"));
		trsNo = transactionElm.getText().trim().split(":")[1];

		return trsNo;
	}

	
	public String UpdateTraxID() {

		String updatetrsNo;

		RemoteWebElement transactionElm = (RemoteWebElement) 
				driver.findElement(By.xpath("//strong[contains(.,'Approval Requests')]"));

		updatetrsNo = transactionElm.getText().trim().substring(6, 15);

		return updatetrsNo;
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

	public NewTLNOCPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

}
