package isoft.etraffic.drl.sddipages;

import java.awt.AWTException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.SeleniumWraper;

public class MedicalTestPage extends SeleniumWraper {

	By submitBtn = By.id("submitBtnId");
	By mdclAssessBtn=		By.id("idMdclAssessButton");
	By doctorToDoctorRemarksTxt = By.xpath("//*[@name='doctorToDoctorRemarks']");
	By doctorToDriverRemarksTxt = By.xpath("//*[@name='doctorToDriverRemarks']");
	By rightEyeResultLst = By.xpath("//*[@name='rightEyeResult']");
	By leftEyeResultLst = By.xpath("//*[@name='leftEyeResult']");
	By colorBlindnessLst = By.xpath("//*[@name='colorBlindness']");
	
	String issueDate, expiryDate;
	
	public void addTest() throws InterruptedException, AWTException {
		waitForElement(doctorToDoctorRemarksTxt);
		writeToElement(doctorToDoctorRemarksTxt, "Remarks Remarks Remarks Remarks.");
		writeToElement(doctorToDriverRemarksTxt, "Remarks Remarks Remarks Remarks.");
		selectFromListByVisibleText(rightEyeResultLst, "6/6");
		selectFromListByVisibleText(leftEyeResultLst, "6/6");
		selectFromListByVisibleText(colorBlindnessLst, "Fit");
		Thread.sleep(500);
		clickElementJS(submitBtn);
		
	}

	public MedicalTestPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}
