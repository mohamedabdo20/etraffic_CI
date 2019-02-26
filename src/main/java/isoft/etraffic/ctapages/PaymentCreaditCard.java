package isoft.etraffic.ctapages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import io.qameta.allure.Step;

public class PaymentCreaditCard {
	
	//private WebDriver driver;
	
	@Step("Make Payment by credit card")
	
	public void paymentcreaditcard(WebDriver driver) throws InterruptedException
	{ 
		driver.findElement(By.xpath("//input[@id='payButtonId']")).click();
		driver.findElement(By.xpath("//button[@id='btnGoToStep4']")).click();
		WebDriverWait wait = new WebDriverWait(driver, 15, 100);
		WebElement myElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@name,'chkConfirm')]")));
		myElement.click();
		driver.findElement(By.xpath("//input[@name='btnPay']")).click();
		
		Thread.sleep(3000);
		WebDriverWait waitt = new WebDriverWait(driver, 20, 100);
		WebElement myElementt = waitt.until(ExpectedConditions.elementToBeClickable(By.id("txtCardNo")));
		myElementt.sendKeys("4111111111111111");
		driver.findElement(By.xpath("//select[@ng-model='month']")).click();
		driver.findElement(By.xpath("//option[@label='January']")).click();
		driver.findElement(By.xpath("/html/body/div[1]/div/form/div/div/div[2]/div[2]/div[1]/div[2]/table/tbody/tr[3]/td[3]/table/tbody/tr/td[2]/select")).sendKeys("2020");
		Thread.sleep(5000);
		driver.findElement(By.id("txtCvvNo")).sendKeys("123");
		driver.findElement(By.id("payButton")).click();
		Thread.sleep(10000);

		
	}
	


}
