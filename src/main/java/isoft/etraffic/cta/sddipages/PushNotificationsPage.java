package isoft.etraffic.cta.sddipages;

import java.awt.AWTException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import io.qameta.allure.Step;
import isoft.etraffic.wrapper.SeleniumWraper;

public class PushNotificationsPage extends SeleniumWraper {
	
	private By MainMenu = By.id("menu-trigger");
	private By ContractfromMenu = By.id("listCategoryLinksId2_13");
	private By PushNotifications = By.xpath("//*[contains(@data-name,'دفع الإشعارات')]");
	private By AddNotifications = By.id("actions1:addButtonId");
	private By Attach = By.id("attachFile");
	private By SelectContractType = By.xpath("//*[contains(@class,\"select2-selection__placeholder\")]");
	private By TypeContractName = By.xpath("//*[@class=\"select2-search__field\"]");
	private By SelectContractName = By.xpath("//*[contains(@class,\"select2-results__option select2-results__option--highlighted\")]");
	private By SelectNotificationReason = By.id("notifcationReason:notifcationReasonSelectOneMenu");
	private By SelectNotificationType = By.id("notificationType:notificationTypeSelectOneMenu");
	private By SelectLanguage = By.id("lang:langselectOneDomainPanelSelectOneMenu");
	private By NotificationSubject = By.id("subject:subject");
	private By NotificationContent = By.id("contentM:contentM");
	private By Save = By.id("j_idt207:saveButtonId");
	
	
	@Step("Add Notifications to company")
	public void pushNotificationPage () throws InterruptedException, AWTException {
		waitForElement(MainMenu);
		clickElement(MainMenu);
		Thread.sleep(3000);
		scrollToelement(ContractfromMenu);
		clickElement(ContractfromMenu);
		clickElement(PushNotifications);
		waitForElement(AddNotifications);
		clickElement(AddNotifications);
		clickElement(AddNotifications);
		clickElement(Attach);
		uploadFile("Lighthouse.jpg");
		Thread.sleep(2000);
		clickElement(SelectContractType);
		//writeToElement(TypeContractName, "عقد المركبات الفخمة العادية");
		writeToElement(TypeContractName, "50189495");
		clickElement(SelectContractName);
		selectFromListByValue(SelectNotificationReason, "1");
		selectFromListByValue(SelectNotificationType, "1");
		selectFromListByValue(SelectLanguage, "1");
		writeToElement(NotificationSubject, "أضافة اشعارات جديدة");
		writeToElement(NotificationContent, "أضافة اشعارات جديدة");
		clickElement(Save);

		
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

	public PushNotificationsPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

}
