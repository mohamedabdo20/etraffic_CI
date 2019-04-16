package isoft.etraffic.vhl.ftfpages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.SeleniumWraper;

public class LoginFTFPage extends SeleniumWraper {

	public LoginFTFPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	By usernameTxt = By.id("dataForm:userInputText");
	By passwordTxt = By.id("dataForm:passInputSecret");
	By loginLnk = By.id("dataForm:loginLink");

	public void loginFTF(String username, String password, String centerName) throws InterruptedException {
		System.out.println("----------- Login: FTF ------------");
		writeToElement(usernameTxt, username);
		writeToElement(passwordTxt, password);
		clickElementJS(loginLnk);
 
		Thread.sleep(3000);
		if (!centerName.equals("")) {
			tryClickElement(By.partialLinkText(centerName));
			Thread.sleep(2000);
			try {
				driver.findElement(By.xpath("/html/body/div[6]")).sendKeys(Keys.chord(Keys.RETURN));
			} catch (Exception e) {
				clickElement(By.partialLinkText(centerName));
				Thread.sleep(2000);
				driver.findElement(By.xpath("/html/body/div[6]")).sendKeys(Keys.chord(Keys.RETURN));
			}
		}
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

	public void loginFTFWithoutCenterSelection(String username, String password) throws InterruptedException {
		writeToElement(usernameTxt, username);
		writeToElement(passwordTxt, password);
		clickElementJS(loginLnk);

		Thread.sleep(3000);
	}
}
