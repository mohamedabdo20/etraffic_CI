package isoft.etraffic.cta.sddipages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import isoft.etraffic.wrapper.SeleniumWraper;


public class CertifyNewLuxuryVehicleModelPage extends SeleniumWraper {
	
	
	
	WebDriverWait wait = new WebDriverWait(driver, 15, 100);
	private By serviceLink = By.xpath("//a[contains(@href,'serviceCode=819')]");
	private By TFtxt = By.id("trafficFileNoId");
	private By TLtxt = By.id("tradeLicenceId");
	private By ComSearchBtn = By.id("btnSearchCompanyDetails");
	private By SelectVHL = By.id("select2-vmkId-container");
	//private By SelectVHLType = By.xpath("/html/body/span/span/span[2]/ul/li[2]");
	private By SelectVHLType;// = By.xpath("//*[@id='select2-vmkId-results']/li[contains(text(),'"+model+"')]");
	private By SelectVHLModel = By.id("select2-vsmId-container");
	private By SelectVHLModelType = By.xpath("/html/body/span/span/span[2]/ul/li[2]");
	private By NoSeat = By.id("noOfSeatsId");
	private By NoDoor = By.id("noOfDoorsId");
	private By Attach = By.id("vhlModelSpecsDocId");
	private By ProcessSevice = By.id("btnProceed");
	private By SaveResumeButton = By.id("saveResumeButton");
	private By ReturnToDashBoardbtn = By.id("returnToDashBoard");

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
		public void filltransactionsdata(String model) throws InterruptedException {
		
			
			clickElement(SelectVHL);
			SelectVHLType = By.xpath("//*[@id='select2-vmkId-results']/li[contains(text(),'"+model+"')]");
			clickElement(SelectVHLType);
			wait.until(ExpectedConditions.elementToBeClickable(SelectVHLModel));
			clickElement(SelectVHLModel);
			wait.until(ExpectedConditions.elementToBeClickable(SelectVHLModelType));
			clickElement(SelectVHLModelType);
			writeToElement(NoSeat, "5");
			writeToElement(NoDoor, "4");
			FileInputElement(Attach, System.getProperty("user.dir")+"//attachments//Lighthouse.jpg");
			clickElement(ProcessSevice);
			clickElement(SaveResumeButton);
		}


	public CertifyNewLuxuryVehicleModelPage(WebDriver driver) {
		super(driver);

	}
	
	
	public void backtomainservices() throws InterruptedException {
		clickElement(ReturnToDashBoardbtn);
	}

	public String TraxID() {

		String trsNo;

		RemoteWebElement transactionElm = (RemoteWebElement) driver
				.findElement(By.xpath("//strong[contains(.,'Transaction number')]"));
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

	
	
	
	
	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
}
