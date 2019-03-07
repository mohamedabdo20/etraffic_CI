package isoft.etraffic.vhl.ftfpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import isoft.etraffic.wrapper.SeleniumWraper;

public class ReplacementExportCertifcatePage extends SeleniumWraper {

	public ReplacementExportCertifcatePage(WebDriver driver) {
		super(driver);
	}

	By certificateNoTxt = By.id("certificateNoId");
	By firstChassisCharTxt = By.id("ch1");
	By proceedTrsBtn = By.id("proceedTrsId");
	By confirmBtn = By.id("confirm");
	By popUp = By.xpath("//*[@class='confirm']");
	
	int seconds = 0;

	public void proceedTrs(String certificateNumber) throws InterruptedException {
		waitForElement(certificateNoTxt);
		writeToElement(certificateNoTxt, certificateNumber);
		hitEnterToElement(certificateNoTxt);
		Thread.sleep(1000);
//		while(seconds<10)
//		{
//			System.out.println("Print:" + getElementText(firstChassisCharTxt));
//			if(getElementText(firstChassisCharTxt).equals(""))
//			{
//				Thread.sleep(1000);
//				seconds++;
//			}
//			else break;
//		}
//		System.out.println("tele3-----------------------------------------------------");
		clickButton(confirmBtn);
		Thread.sleep(1000);
		try{
			while(isElementDisplayed(popUp)){Thread.sleep(1000);}
		}
		catch (Exception e) {}
		clickElementJS(proceedTrsBtn);
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}