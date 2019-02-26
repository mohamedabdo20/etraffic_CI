package isoft.etraffic.ctapages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import io.qameta.allure.Step;
import isoft.etraffic.wrapper.SeleniumWraper;

public class AddActivity extends SeleniumWraper {


	public AddActivity(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
  
	private By addActivityBtn = By.xpath("//a[contains(.,'Add Activity')]");
	private By ActivityCodeTxt = By.id("activityCode");
	private By Searchbtn = By.id("btnSearch");
	private By SearchresultTxt = By.xpath("//*[@id='ORGACT001_search_results']//*[@class='row border-bottom box'][1]");

	@Step("Add new activity")
	public void addactivity( String activityno) throws InterruptedException {

		clickElement(addActivityBtn);
		Thread.sleep(2000);
		writeToElement(ActivityCodeTxt, activityno);
		clickElement(Searchbtn);
		Thread.sleep(2000);
		clickElement(SearchresultTxt);
 
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

}
