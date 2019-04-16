package isoft.etraffic.drl.sddipages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.SeleniumWraper;

public class BuyHandbookPage extends SeleniumWraper {

	By buyHandbookClickHereLnk = By.id("chooseLink");
	By frame = By.id("cboxIframe");
	By handbookTypeLst = By.xpath("//*[@name='handbookType']");
	By lightVehicleChkbox = By.id("1");
	By UrdoChkbox = By.id("UR_1");
	By saveBtn = By.xpath("//*[@name='saveButton']");
	
	By previousHandbookClickHereLnk = By.id("previouslyLink");
	
	By trafficFileTxt = By.xpath("//*[@name='enteredTrafficFileNo']");
	By confirmTrfFileChkBox = By.xpath("//*[@name='confirmedByUser']");
	By opticianNumberTxt = By.xpath("//*[@name='opticianNumber']");
	By rightEyeResultLst = By.xpath("//*[@name='rightEyeResult']");
	By leftEyeResultLst = By.xpath("//*[@name='leftEyeResult']");
	By colorBlindnessLst = By.xpath("//*[@name='colorBlindness']");
	By requestRefLbl = By.xpath("//*[@class='blue']");

	public void handBook() throws InterruptedException {
		waitForElement(buyHandbookClickHereLnk);
		clickElementJS(buyHandbookClickHereLnk);

		waitForElement(frame);
		switchToFrame(frame);

		waitForElement(handbookTypeLst);
		selectFromListByVisibleText(handbookTypeLst, "Soft Copy");
		
		clickElementJS(lightVehicleChkbox);
		Thread.sleep(500);
		clickElementJS(UrdoChkbox);
		
		clickElementJS(saveBtn);
		
	}

	
	public void addResult() throws InterruptedException {
		waitForElement(opticianNumberTxt);
		writeToElement(opticianNumberTxt, "123456");
		selectFromListByVisibleText(rightEyeResultLst, "6/6");
		selectFromListByVisibleText(leftEyeResultLst, "6/6");
		selectFromListByVisibleText(colorBlindnessLst, "Fit");
		clickByLinkTxt("Confirm and Proceed");
		waitForElement(requestRefLbl);
		System.out.println("Request No: " + getElementText(requestRefLbl));
	}

	public BuyHandbookPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

}