package isoft.etraffic.drl.sddipages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.SeleniumWraper;

public class IssueRoadAppPage extends SeleniumWraper {

	By frame = By.id("cboxIframe");
	By bookBtn = By.id("bookId");
	By acceptTermsChkBox = By.id("acceptTerms");
	
	public void proceedTrns() throws InterruptedException
	{
		waitForElement(frame);
		String mainWindow = driver.getWindowHandle();
		
		switchToFrame();
		selectDate();
		Thread.sleep(1000);
		clickElementJS(bookBtn);
		Thread.sleep(2000);
		
		switchToWindow(mainWindow);
		waitForElement(acceptTermsChkBox);
		clickElementJS(acceptTermsChkBox);
		clickByLinkTxt("Confirm & Proceed Pay");
	}

	private void switchToFrame() throws InterruptedException {
		waitForElement(By.id("cboxIframe"));
		switchToFrame("cboxIframe");
	}

	private void selectDate() throws InterruptedException {
		int lastindex = 1;
		waitForElement(By.xpath("//*[@name='gcyId']"));
		for (int i = 5; i >= 1; i--) {
			try {
				driver.findElement(By.xpath("//*[@name='gcyId']/option[" + i + "]"));
				lastindex = i;
				break;
			} catch (Exception e) {
				continue;
			}
		}
		String value = getElementText((By.xpath("//*[@name='gcyId']/option[" + lastindex + "]")));
		selectFromListByVisibleText(By.xpath("//*[@name='gcyId']"), value);
	}

	public IssueRoadAppPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

}
