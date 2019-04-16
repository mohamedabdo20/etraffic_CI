package isoft.etraffic.drl.sddipages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.*;

public class EyeTestResultPage extends SeleniumWraper {

	By trafficFileRdBtn = By.id("trafficMethodStyleId");
	By trafficFileTxt = By.xpath("//*[@name='enteredTrafficFileNo']");
	By searchTrfFileBtn = By.id("readTrafficFileId");
	By confirmTrfFileChkBox = By.xpath("//*[@name='confirmedByUser']");
	By opticianNumberTxt = By.xpath("//*[@name='opticianNumber']");
	By rightEyeResultLst = By.xpath("//*[@name='rightEyeResult']");
	By leftEyeResultLst = By.xpath("//*[@name='leftEyeResult']");
	By colorBlindnessLst = By.xpath("//*[@name='colorBlindness']");
	By requestRefLbl = By.xpath("//*[@class='blue']");
	By renewCheck = By.name("renewLicenseCheckBox");
	
	public void searchByTrf(String trafficFile) throws InterruptedException {
		waitForElement(trafficFileRdBtn);
		clickElementJS(trafficFileRdBtn);

		waitForElement(trafficFileTxt);
		writeToElement(trafficFileTxt, trafficFile.substring(1));
		clickElementJS(searchTrfFileBtn);

		waitForElement(confirmTrfFileChkBox);
		clickElementJS(confirmTrfFileChkBox);
	}

	public void addResult() throws InterruptedException {
		waitForElement(opticianNumberTxt);
		writeToElement(opticianNumberTxt, "123456");
		selectFromListByVisibleText(rightEyeResultLst, "6/6");
		selectFromListByVisibleText(leftEyeResultLst, "6/6");
		selectFromListByVisibleText(colorBlindnessLst, "Fit");
		clickElement(renewCheck);
		clickByLinkTxt("Confirm and Proceed");
		waitForElement(requestRefLbl);
		System.out.println("Request No: " + getElementText(requestRefLbl));
	}

	public EyeTestResultPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

}
