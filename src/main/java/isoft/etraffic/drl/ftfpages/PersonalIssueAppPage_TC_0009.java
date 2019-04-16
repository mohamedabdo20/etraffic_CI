package isoft.etraffic.drl.ftfpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.SeleniumWraper;

public class PersonalIssueAppPage_TC_0009 extends SeleniumWraper {

	By bookappointmentBtn = By.xpath("//*[@class='btn btn-success btn-book-appointment']");
	By proceedTrsBtn = By.id("proceedTrsId");

	public void proceedTrs() throws InterruptedException {
		waitForElement(bookappointmentBtn);
		clickElementJS(bookappointmentBtn);
		
		waitForElement(proceedTrsBtn);
		Thread.sleep(1000);
		clickElementJS(proceedTrsBtn);

	} 
	
	public PersonalIssueAppPage_TC_0009(WebDriver driver) {
		super(driver);
	}
	
	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}

