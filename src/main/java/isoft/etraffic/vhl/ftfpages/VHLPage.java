package isoft.etraffic.vhl.ftfpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.SeleniumWraper;

public class VHLPage extends SeleniumWraper {

	public VHLPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	By pageHeaderLnk = By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr/td[3]/a");
	
	public void gotoScreen(String screenName) throws InterruptedException {
		waitForElement(By.xpath("//*[@data-name='" + screenName + "']"));
		clickElementJS(By.xpath("//*[@data-name='" + screenName + "']"));
		System.out.println("Done-----------------------");
	}

	public boolean isUnderVHL() throws InterruptedException {
		//waitForElement(pageHeaderLnk);
		//Thread.sleep(3000);
		waitForElement(By.id("myIframe"));
		switchToFrame("myIframe");
		waitForElement(pageHeaderLnk);
		System.out.println("getElementText(pageHeaderLnk): " + getElementText(pageHeaderLnk));
		if (getElementText(pageHeaderLnk).contains("أرقام المركبات"))
			return false;
		else
			return true;
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}
