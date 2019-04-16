package isoft.etraffic.cta.sddipages;

import java.sql.SQLException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import isoft.etraffic.wrapper.SeleniumWraper;

public class ModifyTLNOCPage extends SeleniumWraper {
 
	private By modifyTLServiceLink = By.xpath("//a[contains(@href,'serviceCode=802')]");
	private By IagreeBtn = By.id("cbTncVehicleRenewal");
	private By StartProcessbtn = By.id("btnStartProcess");
	private By trafficFileTxt = By.id("trafficFileNoId");
	private By tradeLTxt = By.id("tradeLicenceId");
	private By searchComBtn =  By.id("btnSearchCompanyDetails");
	
	private By editBtn = By.xpath("//img[@title='Edit Organization Name']");
	private By orgNameEngtxt = By.id("orgNameEnId");
	private By orgNameARtxt = By.id("orgNameArId");
	private By compProfileUpdateBtn = By.id("btnCompanyProfileUpdateMember");
	private By confirmbtn = By.id("btnCompanyProfileProceed");
	private By submitFeesBtn = By.id("saveResumeButton");
	
	private By editaddressBtn = By.xpath("//img[@title='Edit Organization Address']");
	private By addressArtxt = By.id("newOrgAddressArId");
	private By updateBtn = By.xpath("btnCompanyProfileUpdateAddress");
	private By cancelLink = By.xpath("//*[@title='Cancel Permit']"); 
	private By deletemember = By.xpath("//*[@class='tabFeeSummary21']//a[2]");
	private By obligationlink1 = By.xpath("//*[contains(@href,'id=10081')]");
	private By obligationlink2 = By.xpath("//*[contains(@href,'id=10082')]");
	private By agreeObligationCheck = By.id("signElecObligationForModifyPermit");
	private By obligationLink = By.xpath("//*[contains(@onclick,'openedObligation')]");

	private By editlegalclass = By.xpath("//*[@title='Edit Organization Legal Class']");
	private By legalclassdrp = By.id("select2-newOrgLegalClassId-container");
	private By legalclassLimitedLiabilityCo = By.xpath("//*[@id='select2-newOrgLegalClassId-results']//li[3]");
	private By legalclassLimitedindividual = By.xpath("//*[@id='select2-newOrgLegalClassId-results']//li[2]");
	private By legalclassLimitedLiabilityoneowner = By.xpath("//*[@id='select2-newOrgLegalClassId-results']//li[4]");
	private By legalclassCommercialCompany = By.xpath("//*[@id='select2-newOrgLegalClassId-results']//li[5]");
	private By updatelegalclass = By.xpath("//*[@onclick=\"updateOrgLegalClass()\"]");
	
	
	
	
	
	public void ModifyTLInitiate(String Traffic_file, String Trade_license) throws InterruptedException {
	
		clickElement(modifyTLServiceLink);
		clickElement(IagreeBtn);
		clickElement(StartProcessbtn);
		writeToElement(trafficFileTxt, Traffic_file);
		writeToElement(tradeLTxt, Trade_license);
		clickElement(searchComBtn);
	
	}
	
	public void changecompanyName(String OrgEngName, String OrgArName) throws InterruptedException {
		clickElement(editBtn);
		writeToElement(orgNameEngtxt, OrgEngName);
		writeToElement(orgNameARtxt, OrgArName);
		WebElement fileInput = driver.findElement(By.id("dedNocCopyId"));
		fileInput.sendKeys("C:\\Users\\Public\\Pictures\\Sample Pictures\\Lighthouse.jpg");
		clickElement(compProfileUpdateBtn);
		clickElement(confirmbtn);
		clickElement(submitFeesBtn);

	}
	
	public void changeaddress(String address) throws InterruptedException
	{
		clickElement(editaddressBtn);
		writeToElement(addressArtxt, address);
		clickElement(updateBtn);
		clickElement(confirmbtn);
	}
	
	public void changelegalclass(String legalclass) throws InterruptedException {
		clickElement(editlegalclass);
		clickElement(legalclassdrp);
		waitForElement(legalclassLimitedindividual);
		switch (legalclass) {
			case "individual":
				clickElement(legalclassLimitedindividual);
				break;
			case "commercial":
				clickElement(legalclassCommercialCompany);
				break;
			case "limitedco":
			clickElement(legalclassLimitedLiabilityCo);
			break;
			case "limitedowner":
				clickElement(legalclassLimitedLiabilityoneowner);
				break;}
	
		clickElement(updatelegalclass);
		

	}

	public void obligation() throws InterruptedException {
	/*	switch (obligation) {
		case "No":
			System.out.println("No Obligation for this activity ");

			break;
		case "Yes":
		
			waitForElement(obligationLink);
			clickElement(obligationLink);
			clickElement(agreeObligationCheck);
		

		default:
			break;
		}*/
		
		int obligationlink = driver.findElements( By.xpath("//a[contains(@onclick,'Obligation')]")).size();
		switch (obligationlink) {
		case 0:
			System.out.println("No Obligation for this activity ");

			break;
		case 1:
		
			waitForElement(obligationLink);
			clickElement(obligationLink);
			clickElement(agreeObligationCheck);
		

		default:
			break;
		}
		
		
	}
	public void submitfees() throws InterruptedException {
		clickElement(confirmbtn);
		clickElement(submitFeesBtn);
		
	}
	
	public String UpdateTraxID() {

		String updatetrsNo;

		RemoteWebElement transactionElm = (RemoteWebElement) 
				driver.findElement(By.xpath("//strong[contains(.,'Approval Requests')]"));
		updatetrsNo = transactionElm.getText().trim().substring(6, 14);

		return updatetrsNo;
	}
	
	public void CancelTLInitiate(String Traffic_file, String Trade_license) throws ClassNotFoundException, SQLException, InterruptedException {

		clickElement(cancelLink);	}
	
	public void deleteMember() throws InterruptedException{

		clickElement(deletemember);	
		clickElement(confirmbtn);
		clickElement(submitFeesBtn);}
	
	public void acceptobligation () throws InterruptedException {
		
		clickElement(obligationlink1);
		clickElement(obligationlink2);
		clickElement(agreeObligationCheck);
		clickElement(confirmbtn);
		clickElement(submitFeesBtn);
		
	}
	
	
	
	public ModifyTLNOCPage(WebDriver driver) {
		super(driver);
		
	}

	@Override
	public boolean isPageLoaded() {

		return false;
	}

}
