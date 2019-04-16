package isoft.etraffic.drl.ftfpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import isoft.etraffic.wrapper.SeleniumWraper;

public class TransferOfLicensePage  extends SeleniumWraper {


	By damageBtn = By.id("damage");
	By recertifyBtn = By.id("recertifyBtnId");
//	Thread.sleep(3000);
//	driver.findElement(By.id("licenseTab")).click();
//	Thread.sleep(3000);
	
	By convertAutoToManualBtn = By.xpath("//*[contains(@id, 'convertAutoToManualBtnId')]");

	public void proceedTrs() throws InterruptedException {
		//waitForElement(driver.findElements(By.xpath("//*[@class='iframe-container']")).get(3));
		//switchToFrame(driver.findElements(By.xpath("//*[@class='iframe-container']")).get(3));
		Thread.sleep(1000);
	//	driver.switchTo().frame(driver.findElements(By.xpath("//*[@class='iframe-container']")).get(3));
		
		WebElement frame = driver.findElement(By.xpath("//*[@id='dLicense']//*[@class='iframe-container']"));
		driver.switchTo().frame(frame);
		
		waitForElement(convertAutoToManualBtn);
		clickElementJS(convertAutoToManualBtn);
		
		//driver.switchTo().window(driver.getWindowHandle());
		Thread.sleep(5000);
		driver.switchTo().window(driver.getWindowHandle());
		//waitForElement(By.xpath("//*[@id='appURLId']/iframe"));
		//WebElement frame2 = driver.findElement(By.xpath("//*[@id='appURLId']//*[@frameborder='0']"));
		System.out.println("Size: " +  driver.findElements(By.xpath("//*[@id='appURLId']/iframe")).size());
		WebElement frame2 = driver.findElement(By.xpath("//*[@id='appURLId']/iframe"));
		driver.switchTo().frame(frame2);
		System.out.println("Switched");
		Thread.sleep(5000);
		System.out.println("Waited");
		//driver.findElement(By.xpath("//*[@class='card-body card-padding c-white bgm-green']"));
		//driver.findElement(By.xpath("//*[@id='allForm:slotDivDiv']"));
		//waitForElement(By.xpath("//*[@id='allForm:slotDivDiv']/div/div/div"));
		clickElementJS(By.xpath("//*[@title='إحجز']"));
		//clickElementJS(By.xpath("//*[@id='allForm:slotDivDiv']/div/div//[@title='إحجز']"));
		Thread.sleep(1000);
		clickElementJS(By.xpath("//*[@id='modalDialog']//button"));
		Thread.sleep(1000);
		
		switchToWindow(driver.getWindowHandle());
		clickElementJS(recertifyBtn);

	} 
	
	public TransferOfLicensePage(WebDriver driver) {
		super(driver);
	}
	
	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

	

}
