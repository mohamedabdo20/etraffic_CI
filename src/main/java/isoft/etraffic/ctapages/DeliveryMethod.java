package isoft.etraffic.ctapages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import io.qameta.allure.Step;
import isoft.etraffic.wrapper.SeleniumWraper;

public class DeliveryMethod extends SeleniumWraper
{

	public DeliveryMethod(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}


	private By mobileNumbertxt = By.id("mobileNumber");
	private By phoneNumbertxt = By.id("phoneNumber");
	private By emailtxt = By.id("email");
	private By emailConfirmtxt = By.id("emailConfirm");
	private By submitbtn = By.id("btnGoToStep35");
	  
	@Step("Fill delivery data for transaction")
	public void delivermethod(String mobileNumber , String phoneNumber , String email , String emailConfirm) throws InterruptedException {
		writeToElement(mobileNumbertxt, mobileNumber);
		writeToElement(phoneNumbertxt, phoneNumber);
		writeToElement(emailtxt, email);
		writeToElement(emailConfirmtxt, emailConfirm);
		clickElement(submitbtn);
		
		/*
		driver.findElement(By.xpath("//input[@id='mobileNumber']")).sendKeys("0501234567");
		driver.findElement(By.xpath("//input[@id='phoneNumber']")).sendKeys("040123456");
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys("kareem@yahoo.com");
		driver.findElement(By.xpath("//input[@id='emailConfirm']")).sendKeys("kareem@yahoo.com");
		driver.findElement(By.xpath("//button[@id='btnGoToStep35']")).submit();
*/
	}


	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	} 

}
