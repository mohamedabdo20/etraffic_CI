package isoft.etraffic.cta.sddipages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import io.qameta.allure.Step;
import isoft.etraffic.wrapper.SeleniumWraper;

public class ReviewTLPage extends SeleniumWraper {
//	private static ReviewTLPage reviewTLPage;
	
	public ReviewTLPage(WebDriver driver) {
		super(driver);
	}
	
	private By ReviewTLLink = By.xpath("//*[contains(@href,'serviceCode=803')]");
	private By Iagree = By.id("cbTncVehicleRenewal");
	private By StartProcessbtn = By.id("btnStartProcess");
	private By TraxIDtxt = By.id("trsId");
	private By AppNotxt = By.id("appRefNoId");
	private By SearchBtn = By.id("btnSearchCompanyDetails");
	private By ProceedBtn = By.id("btnProceed");
	private By saveResumeButton = By.id("saveResumeButton");
	
	@Step("Review trade license request")
	public  void ReviewTL(String TraxID, String AppNo) throws InterruptedException {
		waitForElement(ReviewTLLink);
		clickElement(ReviewTLLink);
		clickElement(Iagree);
		clickElement(StartProcessbtn);
		writeToElement(TraxIDtxt, TraxID);
		writeToElement(AppNotxt, AppNo);
		clickElement(SearchBtn);
		clickElement(ProceedBtn);
 
	}
	
	@Step("Submit Fees for review request")
	public void SubmitFees() throws InterruptedException {
			clickElement(saveResumeButton);
	}

	

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

}
