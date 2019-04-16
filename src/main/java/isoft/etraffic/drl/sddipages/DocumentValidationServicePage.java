package isoft.etraffic.drl.sddipages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.SeleniumWraper;

public class DocumentValidationServicePage extends SeleniumWraper {

	By certificateTypeLst = By.xpath("//*[@name='certificateType']");
	By referenceNoTxt = By.xpath("//*[@name='referenceNo']");
	By closeLnk = By.id("cboxClose");
	By viewECertificateBtn = By.partialLinkText("View E-Certificate");
	
	public void searchByCertificate(String certificateType, String certificateNumber) throws InterruptedException {
		waitForElement(certificateTypeLst);
		selectFromListByVisibleText(certificateTypeLst, certificateType);
		
		waitForElement(referenceNoTxt);
		writeToElement(referenceNoTxt, certificateNumber);
		hitEnterToElement(referenceNoTxt);
		
		Thread.sleep(500);
		clickElement(By.xpath("//*[@class='inptbtn']"));
		
		waitForElement(viewECertificateBtn);
		clickElementJS(viewECertificateBtn);
		waitForElement(closeLnk);
		Thread.sleep(2000);
		clickElementJS(closeLnk);
	}

	public DocumentValidationServicePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}