package isoft.etraffic.cta.sddipages;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import isoft.etraffic.vhl.sddipages.CommonPageOnline;
import isoft.etraffic.wrapper.SeleniumWraper;

public class MyContractPage extends SeleniumWraper {

	private By myContractLink = By.xpath("//*[@class='bottom-menu']//a[contains(@href,'contract')]");
	private By addvehcilelink = By.xpath("//a[contains(@href,'add-vehicles-to-contract.do')]");
	private By noOfVehiclestxt = By.id("noOfVehiclesId");
	private By vehcileattachtxt = By.id("vehicleSpecficationsAttachmentCopyId");
	private By IconfirmCheck = By.id("dataAccuracyId");
	private By IagreeCheck = By.id("termsAndConditionOfAddReducevehiclesId");
	private By proceedbtn = By.id("btnProceed");

	// private WebDriver driver;

	WebDriverWait wait;
	CtaCommonPages common = new CtaCommonPages(driver);
	CommonPageOnline loginobject = new CommonPageOnline(driver);

	public void NewNOCFill(String requestIssuer, String mobileNo, String issueDate)
			throws InterruptedException, ClassNotFoundException, SQLException {

	}

	public void accessMyContract() throws InterruptedException {
		clickElement(myContractLink);

	}

	public void addvehicletocontract(String noOfVehicles) throws InterruptedException {

		clickElement(addvehcilelink);
		writeToElement(noOfVehiclestxt, noOfVehicles);
		FileInputElement(vehcileattachtxt, System.getProperty("user.dir") + "//attachments//Lighthouse.jpg");
		clickElement(IconfirmCheck);
		clickElement(IagreeCheck);
		clickElement(proceedbtn);

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

	public MyContractPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

}
