package isoft.etraffic.ctapages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
	private By homelink = By.id("goToHomeText");
	WebDriverWait wait;

	@Step("Make Payment by credit card")
	public void paymentcreaditcard(WebDriver driver) throws InterruptedException {

		clickElement(paybtn);
		clickElement(continuebtn);
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
		Thread.sleep(10000);
		waitForElement(homelink);
		// clickElement(closeHappiniesbtn);

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
