package isoft.etraffic.cta.sddipages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import io.qameta.allure.Step;
import isoft.etraffic.wrapper.SeleniumWraper;

public class SendNotificationsPage extends SeleniumWraper{
	



	//private By Selectresult1 = By.id("resultsTable::ch");
	private By Selectresult2 = By.id("resultsTable:0:c32");
	private By ViewResult = By.id("actions1:viewButtonId");
	private By ReviewResult = By.id("octKpiForm:auditApproval");
	private By BacktoSearchpage = By.id("octKpiForm:j_idt154:backButtonId");
	private By TypeContractName = By.xpath("//*[@class=\"select2-search__field\"]");
	private By SelectContractName = By.xpath("//*[contains(@class,\"select2-results__option select2-results__option--highlighted\")]");
	private By SearchNotification = By.id("j_idt196:searchButtonId");
	private By SendNotification = By.id("octKpiForm:auditSending");
	private By SelectNotificationStatus = By.id("l1:l1selectOneDomainPanelSelectOneMenu");
	
	@Step("Send Notification to Company")
	public void sendNotifcationsPage () throws InterruptedException {
		clickElement(Selectresult2);
		clickElement(ViewResult);
		clickElement(ReviewResult);
		clickElement(BacktoSearchpage);
		writeToElement(TypeContractName, "50189495");
		clickElement(SelectContractName);
		selectFromListByValue(SelectNotificationStatus, "2");
		clickElement(SearchNotification);
		clickElement(Selectresult2);
		clickElement(ViewResult);
		clickElement(SendNotification);
		
	}

	
	
	public SendNotificationsPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

}
