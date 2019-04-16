package  isoft.etraffic.spl.sddipages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.qameta.allure.Step;
import isoft.etraffic.wrapper.SeleniumWraper;

public class LoginPage extends SeleniumWraper {
	private By txtonlineTraficNo = By.name("trafficNo");
	private By radioOnlineBtn = By.xpath("//*[@value='ONLINE']");
	 
	private By radioPublicBtn = By.xpath("//*[@value='PI']");	
	private By txtpublicTraficNo = By.name("publicInquiryTrafficNo");
	
	private By radioCallCenterBtn = By.xpath("//*[@value='CC']");
	private By txtusername = By.name("username");
	
	private By radioTrustedAgentBtn = By.xpath("//*[@value='TA-O']");
	private By txtagentName = By.name("agentName");
	private By drpagentCenter = By.xpath("//*[@class='custom-combobox-input ui-widget ui-widget-content ui-state-default ui-corner-left ui-autocomplete-input']");
	private By btnLogin = By.name("Login");
 
	WebDriverWait webDriverWait;
	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	@Step("Go to login usin Online channel")
	public void doOnlineLogin(String trafficNo) throws InterruptedException {
		driver.get("https://tst12c:7793/trfesrv/test_login.jsp");
		clickElement(radioOnlineBtn);
		writeToElement(txtonlineTraficNo,trafficNo);
		clickElement(btnLogin);
	}

	@Step("Go to login usin Call Center channel")
	public void doCallCenterLogin(String username) throws InterruptedException {
		driver.get("https://tst12c:7793/trfesrv/test_login.jsp");
		clickElement(radioCallCenterBtn);
		writeToElement(txtusername, username);
		clickElement(btnLogin);
	}

	
	@Step("Go to login usin Trusted Agent channel")
	public void doTrustedAgentLogin(String agentName ,String agentcenter) throws InterruptedException {
		driver.get("https://tst12c:7793/trfesrv/test_login.jsp");
		clickElement(radioTrustedAgentBtn);
		writeToElement(txtagentName, agentName);
		writeToElement(drpagentCenter, agentcenter);
		clickElement(btnLogin);
	}

	@Step("Go to login usin Public Inquiry channel")
	public void doPublicInquiryLogin(String PublicTraficNo) throws InterruptedException {
		driver.get("https://tst12c:7793/trfesrv/test_login.jsp");
		clickElement(radioPublicBtn);
		writeToElement(txtpublicTraficNo, PublicTraficNo);
		clickElement(btnLogin);
	}

	@Override
	public boolean isPageLoaded() {
		return false;
	}
}
