package isoft.etraffic.drl.ftfpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import isoft.etraffic.wrapper.SeleniumWraper;

public class DriversCertificatesPage extends SeleniumWraper {

	By driversCertificateBtn = By.id("drlCertificatesTab");
	By doctorCertificateBtn = By.xpath("//*[@class='doctor']");
	By expertCertificateBtn = By.xpath("//*[@class='experience']");

	public void clickDriversCertificateBtn() throws InterruptedException {
		waitForElement(driversCertificateBtn);
		clickElementJS(driversCertificateBtn);
	}
	
	public void clickExpertCertificateBtn() throws InterruptedException {
		waitForElement(expertCertificateBtn);
		clickElementJS(expertCertificateBtn);
	}
	
	public void clickDoctorCertificateBtn() throws InterruptedException {
		waitForElement(doctorCertificateBtn);
		clickElementJS(doctorCertificateBtn);
	}

	public DriversCertificatesPage(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}