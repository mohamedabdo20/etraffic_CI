package isoft.etraffic.cta.sddipages;

import java.sql.SQLException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import io.qameta.allure.Step;
import isoft.etraffic.wrapper.SeleniumWraper;

public class RenewTLPage extends SeleniumWraper{

	//private WebDriver driver;
	String Traffic_file;
	String Trade_license;
    public String talabNO;
    

    
    private By serviceLink = By.xpath("//a[contains(@href,'serviceCode=804')]");
    private By IagreeCheck = By.id("terms");
    private By ApplyForService = By.id("nextButton");
    private By trafficFileTxt = By.id("trafficNoId");
	private By tradeLTxt = By.id("tradeLicenseId");
	private By submitbtn =  By.id("submitBtn");
	private By confirmtodeilverybtn = By.id("next");
	//private By obligationLink = By.id("public_resources/revamp/cml/VehicleRentalActivityTemplate.pdf?id=5");
	private By obligationLink =By.xpath("//*[contains(@href,\"public_resources/revamp/cml/VehicleRentalActivityTemplate.pdf?id=5\")]");
	private By approvecheck= By.id("renewPmtElecObligation");
	private By ConfirmandProceedbtn= By.xpath("//*[@id='idConfrimAndProceedButton']");
	
    
    CtaCommonPages GP = new CtaCommonPages(driver);
    @Step("Initiate renew trade license services for traffic file : {0} , and trade license : {1}")
	
	public void RenewTLInitiate(String Traffic_file, String Trade_license) throws InterruptedException, ClassNotFoundException, SQLException  {


		//Apply on Renew NOC
		clickElement(serviceLink);
		clickElement(IagreeCheck);
		clickElement(ApplyForService);
		writeToElement(trafficFileTxt, Traffic_file);
		writeToElement(tradeLTxt, Trade_license);
		clickElement(submitbtn);
		
		//get traffic file and trade license expired
		
/*

		//Apply on Renew service
		
		driver.findElement(By.xpath("//input[@id='trafficNoId']")).sendKeys(Traffic_file);
		driver.findElement(By.xpath("//input[@id='tradeLicenseId']")).sendKeys(Trade_license);
		driver.findElement(By.xpath("//em[contains(.,'Submit')]")).click();
		JavascriptExecutor jsx4 = (JavascriptExecutor) driver;
		jsx4.executeScript("window.scrollBy(0,450)", "");
		driver.findElement(By.xpath("//em[contains(.,'Confirm & Proceed to Delivery Method ')]")).click();
		Thread.sleep(5000);
		driver.findElement(By.partialLinkText("Confirm & Proceed to Payment")).click();
		Thread.sleep(5000);
		driver.findElement(By.id("payButtonId")).click();

		//Pay For Services
		
		PaymentCreaditCardforRenew payment = new PaymentCreaditCardforRenew();
		payment.paymentcreaditcardforrenew(driver);
		Thread.sleep(30000);
		
		//Get transaction and applicaion NO
		
		driver.findElement(By.xpath("//*[@class='fancybox-overlay fancybox-overlay-fixed']")).sendKeys(Keys.ESCAPE);
		JavascriptExecutor jsx3 = (JavascriptExecutor) driver;
		jsx3.executeScript("window.scrollBy(0,450)", "");
		RemoteWebElement orderNoElm = (RemoteWebElement) driver
				.findElement(By.xpath("//strong[contains(.,'Application Reference Number')]"));
		talabNO = orderNoElm.getText().trim().split(":")[1];
		System.out.println(talabNO);
		
		//get certification No
		
		GetNo getorderno = new GetNo();
		String certificatenumber = getorderno.getcertificateno(talabNO);

		//Update trade license
		
		UpdateTradeLicenseForRenew updateTL = new UpdateTradeLicenseForRenew();
		updateTL.updatetradelicenseforrenew(talabNO, certificatenumber , driver);
*/
	}

    public void viewandacceptObligation() throws InterruptedException {
    	clickElement(obligationLink);
    	clickElement(approvecheck);
    }
    public void confirmtodelivery() throws InterruptedException {
    	waitForElement(confirmtodeilverybtn);
    	clickElement(confirmtodeilverybtn);
    }
    
    public void confirmandproceed() throws InterruptedException {
    	waitForElement(ConfirmandProceedbtn);
    	clickElement(ConfirmandProceedbtn);
    }
    
	public RenewTLPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
	public String TraxIDForRenew() {

		String trsNo;

		RemoteWebElement transactionElm = (RemoteWebElement) 
				driver.findElement(By.xpath("//strong[contains(.,'Request Reference')]"));
		trsNo = transactionElm.getText().trim().split(":")[1];

		return trsNo;
	}

}
