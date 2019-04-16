package isoft.etraffic.cta.sddipages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import isoft.etraffic.wrapper.SeleniumWraper;

public class NOCForResumeTradeLicenses extends SeleniumWraper {
	
	WebDriverWait wait = new WebDriverWait(driver, 15, 100);
	private By serviceLink = By.xpath("//a[contains(@href,'serviceCode=826')]");
	private By TFtxt = By.id("trafficFileNoId");
	private By TLtxt = By.id("tradeLicenceId");
	private By ComSearchBtn = By.id("btnSearchCompanyDetails");
	private By AttachTL = By.id("nocOfResumeFromDEDAttachmentCopyId");
	private By OpenLink = By.xpath("/html/body/form/div[1]/div[2]/div/div/div[9]/div/a");                                                                 
	private By ApprovalCheck = By.id("signElecObligationForResumePermitId");
	private By Proceed = By.id("btnCompanyProfileProceed");


	CtaCommonPages GP = new CtaCommonPages(driver);

	public void InitiateService() throws InterruptedException {

		clickElement(serviceLink);
		GP.applyForService();
	}

	public void logintoservice(String TF, String TL) throws InterruptedException {

		writeToElement(TFtxt, TF);
		writeToElement(TLtxt, TL);
		clickElement(ComSearchBtn);

	}
	
		public void filltransactionsdataforsuspend() throws InterruptedException {

			//clickElement(serviceLink);
			FileInputElement(AttachTL, System.getProperty("user.dir")+"//attachments//Lighthouse.jpg");
			clickElement(OpenLink);
			clickElement(ApprovalCheck);
			clickElement(Proceed);
		}



		public NOCForResumeTradeLicenses(WebDriver driver) {
			super(driver);
	}


	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
