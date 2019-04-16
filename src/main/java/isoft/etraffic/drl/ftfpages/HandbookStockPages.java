package isoft.etraffic.drl.ftfpages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import isoft.etraffic.wrapper.SeleniumWraper;

public class HandbookStockPages extends SeleniumWraper {

	By deleteNoBtn = By.xpath("//*[@class='modal-footer']/button");
	By deleteYesBtn = By.id("deleteButtonAction");
	By statusLst = By.id("statusList:statusListselectOneDomainPanelSelectOneMenu");
	By saveBtn = By.id("j_idt136:saveButtonId");
	By logIframe = By.id("logIframe");
	By closeWindowBtn = By.xpath("//*[@class='close pull-left']");
	By backBtn =  By.id("backBtn");
	By addBtn = By.id("addButton");
	By maintainceIframe = By.id("maintainceIframe");
	String mainWindow;
	String secondWindow;
	
	public void deleteThenCancel(String center) throws InterruptedException {
		mainWindow = switchToSecondWindow();
		driver.manage().window().maximize();
		Thread.sleep(500);
		waitForElement(By.xpath("//*[contains(@selectedctrname,'" + center + "')]"));
		List<WebElement> deleteBtns = driver.findElements(By.xpath("//*[contains(@selectedctrname,'" + center + "')]"));
		clickElementJS(deleteBtns.get(2));
		Thread.sleep(1000);
		clickElement(deleteNoBtn);
	}

	public void showDetails(String center) throws InterruptedException {
		mainWindow = switchToSecondWindow();
		driver.manage().window().maximize();
		Thread.sleep(500);
		waitForElement(By.xpath("//*[contains(@selectedctrname,'" + center + "')]"));
		List<WebElement> deleteBtns = driver.findElements(By.xpath("//*[contains(@selectedctrname,'" + center + "')]"));
		clickElementJS(deleteBtns.get(1));
		Thread.sleep(1000);
		secondWindow = driver.getWindowHandle();
		switchToFrame(logIframe);
		waitForElement(backBtn);
		clickElementJS(backBtn);
		switchToWindow(secondWindow);
	}

	public void edit(String center) throws InterruptedException {
		mainWindow = switchToSecondWindow();
		driver.manage().window().maximize();
		Thread.sleep(500);
		waitForElement(By.xpath("//*[contains(@selectedctrname,'" + center + "')]"));
		List<WebElement> deleteBtns = driver.findElements(By.xpath("//*[contains(@selectedctrname,'" + center + "')]"));
		clickElementJS(deleteBtns.get(0));
		Thread.sleep(1000);
		secondWindow = driver.getWindowHandle();
		switchToFrame(maintainceIframe);
		waitForElement(backBtn);
		String currentValue = getElementAttributeValue(By.id("00"), "value");
		System.out.println("currentValue: " + currentValue + " -- New Value: " + Integer.toString((Integer.parseInt(currentValue)+5)));
		writeToElement(By.id("00"), Integer.toString((Integer.parseInt(currentValue)+5)));
		Thread.sleep(500);
		clickElementJS(By.xpath("//*[@onclick='doSave()']"));
		Thread.sleep(1000);
		switchToWindow(secondWindow);
		waitForElement(addBtn);
	}

	public HandbookStockPages(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

}
