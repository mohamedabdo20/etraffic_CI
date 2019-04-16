package isoft.etraffic.cta.sddipages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class UpdateTradeLicenseForRenew {

	//public WebDriver driver;
	//String OrderNo;
	//String CertificationNo;

	@Test()
	public void updatetradelicenseforrenew(String odernumber , String Certificatenumber , WebDriver driver) throws InterruptedException {


		driver.get("https://tst12c:7793/trfesrv/public_resources/public-access.do");
		driver.findElement(By.xpath("/html/body/form/div[1]/div[7]/div[2]/div[2]/div[2]/div[2]/ul/li[8]/a")).click();
		WebElement elemente = driver.findElement(By.xpath("//input[@type='checkbox']"));
		JavascriptExecutor executorr = (JavascriptExecutor) driver;
		executorr.executeScript("arguments[0].click();", elemente);
		driver.findElement(By.xpath("//button[@id='btnStartProcess']")).click();

		JavascriptExecutor jsx3 = (JavascriptExecutor) driver;
		jsx3.executeScript("window.scrollBy(0,450)", "");
		Thread.sleep(10000);
		driver.findElement(By.id("newPermitAppRefNoId")).sendKeys(odernumber);
		driver.findElement(By.id("newCompanyNOCLetterNumberId")).sendKeys(Certificatenumber);
		driver.findElement(By.id("btnSearchCompanyDetails")).click();

		JavascriptExecutor jsx1 = (JavascriptExecutor) driver;
		jsx1.executeScript("window.scrollBy(0,450)", "");

		driver.findElement(By.id("tradeLicenseExpiryDateId")).sendKeys("20-10-2020");
		
		JavascriptExecutor jsx2 = (JavascriptExecutor) driver;
		jsx2.executeScript("window.scrollBy(0,450)", "");

		WebElement f = driver.findElement(By.xpath("//input[@id='tradeLicenseCopyId']"));
		f.sendKeys("C:\\Users\\Public\\Pictures\\Sample Pictures\\Lighthouse.jpg");
		Thread.sleep(2000);
		driver.findElement(By.id("btnProceed")).click();

	}

}
