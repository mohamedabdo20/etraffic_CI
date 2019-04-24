package isoft.etraffic.cta.sddipages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import isoft.etraffic.wrapper.SeleniumWraper;

public class ResumeTLPage extends SeleniumWraper {
	
	WebDriverWait wait = new WebDriverWait(driver, 15, 100);
	private By serviceLink = By.xpath("//a[contains(@href,'serviceCode=826')]");
	private By TFtxt = By.id("trafficFileNoId");
	private By TLtxt = By.id("tradeLicenceId");
	private By ComSearchBtn = By.id("btnSearchCompanyDetails");
	private By AttachTL = By.id("nocOfResumeFromDEDAttachmentCopyId");
	private By OpenLink = By.xpath("//a[contains(@href,'public_resources/revamp/cml/ResumePermitForFranchiesActivtiesTemplate.pdf?id=0')]");                                                                 
	private By ApprovalCheck = By.id("signElecObligationForResumePermitId");
	private By Proceed = By.id("btnCompanyProfileProceed");
	private By submitbtn = By.id("saveResumeButton"); 


	CtaCommonPages GP = new CtaCommonPages(driver);

	public void InitiateService() throws InterruptedException {

		clickElement(serviceLink);
		GP.applyForService();
	}

	public void SearchForCompany(String TF, String TL) throws InterruptedException {

		writeToElement(TFtxt, TF);
		writeToElement(TLtxt, TL);
		clickElement(ComSearchBtn);

	}
	
		public void fillResumeData() throws InterruptedException {

			//clickElement(serviceLink);
			FileInputElement(AttachTL, System.getProperty("user.dir")+"//attachments//Lighthouse.jpg");
			clickElement(OpenLink);
			clickElement(ApprovalCheck);
			clickElement(Proceed);
			clickButton(submitbtn);
			
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


		public ResumeTLPage(WebDriver driver) {
			super(driver);
	}


	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
