package isoft.etraffic.drl.ftfpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.SeleniumWraper;

public class TrainingPage  extends SeleniumWraper {


	By Traininglink = By.id("trainingDataTab");
	

	public void navigateToTrainingTab() throws InterruptedException {
		waitForElement(Traininglink);
		clickElementJS(Traininglink);

		Thread.sleep(1000);
	} 
	
	public TrainingPage(WebDriver driver) {
		super(driver);
	}
	
	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

}
