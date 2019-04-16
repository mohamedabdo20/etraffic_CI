package isoft.etraffic.cta.sddipages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import io.qameta.allure.Step;
import isoft.etraffic.wrapper.SeleniumWraper;

public class DeliveryMethodPage extends SeleniumWraper {


	private By mobileNumbertxt = By.id("mobileNumber");
	private By phoneNumbertxt = By.id("phoneNumber");
	private By emailtxt = By.id("email");
	private By emailConfirmtxt = By.id("emailConfirm");
	private By submitbtn = By.id("btnGoToStep35");
	private By confirm = By.id("saveResumeButton");
	
	@Step("Fill delivery data for transaction")
	public void delivermethod(String mobileNumber, String phoneNumber, String email, String emailConfirm)
			throws InterruptedException {
		writeToElement(mobileNumbertxt, mobileNumber);
		writeToElement(phoneNumbertxt, phoneNumber);
		writeToElement(emailtxt, email);
		writeToElement(emailConfirmtxt, emailConfirm);
		clickElement(submitbtn);
		System.out.println("----------------Delivery method selected successfully ----------------");

	}

	public void delivermethodwithoutpay(String mobileNumber, String phoneNumber, String email, String emailConfirm)
			throws InterruptedException {
		writeToElement(mobileNumbertxt, mobileNumber);
		writeToElement(phoneNumbertxt, phoneNumber);
		writeToElement(emailtxt, email);
		writeToElement(emailConfirmtxt, emailConfirm);
		clickElement(submitbtn);
		clickElement(confirm);
		
		System.out.println("----------------Delivery method selected successfully ----------------");

	}

	public DeliveryMethodPage(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean isPageLoaded() {
		return false;
	}

}
