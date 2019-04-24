package isoft.etraffic.cta.sddipages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.qameta.allure.Step;
import isoft.etraffic.wrapper.SeleniumWraper;

public class PaymentCreaditCard extends SeleniumWraper {
	//private By closeHappiniesbtn = By.xpath("//*[@class='fancybox-item fancybox-close']");
	private By paybtn = By.xpath("//input[@id='payButtonId']");
	private By continuebtn = By.xpath("//button[@id='btnGoToStep4']");
	private By chkConfirm = By.xpath("//input[contains(@name,'chkConfirm')]");
	private By paybtn2 = By.xpath("//input[@name='btnPay']");
	private By cardnotxt = By.id("txtCardNo");
	private By monthdrp = By.xpath("//select[@ng-model='month']");
	private By yeardrp = By.xpath("//select[@ng-model='selectedYear']");
	private By cvvtxt = By.id("txtCvvNo");
	private By confirmpaymentbtn = By.id("payButton");
	//private By homelink = By.id("goToHomeText");
	private By amounttxt = By.xpath("//*[@class='formBoldRed']");
	WebDriverWait wait;
	String Amount;
	@Step("Make Payment by credit card")
	public void paymentcreaditcard(WebDriver driver) throws InterruptedException {
		waitForElement(paybtn);
		clickElement(paybtn);
		clickElement(continuebtn);
		Thread.sleep(2000);
		RemoteWebElement amountelement = (RemoteWebElement) driver.findElement(By.xpath("//*[@class='formBoldRed']"));
		Amount = amountelement.getText();
		System.out.println(Amount);
		clickElement(chkConfirm);
		clickElement(paybtn2);
		Thread.sleep(3000);
		wait = new WebDriverWait(driver, 15, 100);
		WebElement cardno = wait.until(ExpectedConditions.elementToBeClickable(cardnotxt));
		cardno.sendKeys("4111111111111111");
		selectFromListByValue(monthdrp, "1");
		selectFromListByValue(yeardrp, "number:2023");
		writeToElement(cvvtxt, "123");
		clickElement(confirmpaymentbtn);
		Thread.sleep(30000);
		
		System.out.println("----------------Payment done successfully ----------------");
		// clickElement(closeHappiniesbtn);

	}
	
	
	public String Amount() {

		return Amount;
	}
	

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

	public PaymentCreaditCard(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
}
