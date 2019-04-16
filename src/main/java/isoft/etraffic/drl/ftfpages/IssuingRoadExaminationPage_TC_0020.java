package isoft.etraffic.drl.ftfpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import isoft.etraffic.wrapper.SeleniumWraper;

public class IssuingRoadExaminationPage_TC_0020  extends SeleniumWraper {


	By damageBtn = By.id("damage");
	By proceedTrsBtn = By.id("proceedTrsId");
	By reqTestsFrame = By.id("reqTestsFrame");
	By issueAppBtn = By.xpath("//*[@data-original-title='إصدار موعد فحص']");
	
	

	public void proceedTrs() throws InterruptedException {
		Thread.sleep(1000);
		waitForElement(reqTestsFrame);
		switchToFrame(reqTestsFrame);
		
		waitForElement(issueAppBtn);
		clickElementJS(issueAppBtn);
		
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
		
		
		
		waitForElement(proceedTrsBtn);
		Thread.sleep(1000);
		clickElementJS(proceedTrsBtn);

	} 
	
	public IssuingRoadExaminationPage_TC_0020(WebDriver driver) {
		super(driver);
	}
	
	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}
