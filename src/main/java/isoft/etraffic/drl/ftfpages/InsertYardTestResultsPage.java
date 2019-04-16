package isoft.etraffic.drl.ftfpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import isoft.etraffic.wrapper.SeleniumWraper;

public class InsertYardTestResultsPage extends SeleniumWraper {

	By trafficFileField = By.xpath("//*[@id='appIdInputLookupPanelDiv']/div/span/span/span/span[2]");
	By trafficFileSearchTxt = By.xpath("//*[@class='select2-search__field']");
	By searchBtn = By.xpath("//*[contains(@id,'searchBtnId')]");
	By saveBtn = By.id("saveButton");

	public void insertResultsFailed(String trafficFile) throws InterruptedException {
		waitForElement(searchBtn);
		Actions a = new Actions(driver);
		a.moveToElement(driver.findElement(trafficFileField)).click().build().perform();
		Thread.sleep(500);
		writeToElement(trafficFileSearchTxt, trafficFile);
		Thread.sleep(500);
		hitEnterToElement(trafficFileSearchTxt);
		Thread.sleep(500);
		clickElementJS(searchBtn);
		
		waitForElement(By.xpath("//*[@class='af_table_data-row trialRow']/td[5]"));
		for(int i=6; i<11; i++)
		{
			clickElementJS(By.xpath("//*[@class='af_table_data-row trialRow']/td["+i+"]/div"));
			clickElementJS(By.xpath("//*[@class='af_table_data-row trialRow']/td["+i+"]/div"));
			clickElementJS(By.xpath("//*[@class='af_table_data-row trialRow']/td["+i+"]/div"));
		}
		Thread.sleep(500);
		clickElementJS(saveBtn);
	}

	public InsertYardTestResultsPage(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}
