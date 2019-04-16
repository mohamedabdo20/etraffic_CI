package isoft.etraffic.drl.ftfpages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.SeleniumWraper;

public class ChangeLearningPemitStatusPage extends SeleniumWraper {

	public ChangeLearningPemitStatusPage(WebDriver driver) {
		super(driver);
	}

	// Search by Plate
	By frame = By.id("licenseApplicationFrame");
	By statusLst = By.id("status:statusselectOneDomainPanelSelectOneMenu");

	public void changeStatus(String status) throws InterruptedException, AWTException {

		Thread.sleep(3000);
		// switchToFrame("licenseApplicationFrame");
		// waitForElement(statusLst);
		//
		// Thread.sleep(1000);
		// clickElementJS(statusLst);
		// selectFromFTFList(statusLst, status);.

		StringSelection imageSelection = new StringSelection(status);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(imageSelection, null);

		Robot robot = new Robot();

		// Select status
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_TAB);
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_TAB);

		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);

		Thread.sleep(1000);

		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_V);

		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);

		Thread.sleep(1000);

		// click save
//		robot.keyPress(KeyEvent.VK_TAB);
//		robot.keyRelease(KeyEvent.VK_TAB);
//		robot.keyPress(KeyEvent.VK_ENTER);
//		robot.keyRelease(KeyEvent.VK_ENTER);

		// goto reason and fill reason
		for (int i = 0; i < 6; i++) {
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
		}
		Toolkit.getDefaultToolkit().getSystemClipboard()
				.setContents(new StringSelection("سبب التعديل سبب التعديل سبب التعديل سبب التعديل سبب التعديل"), null);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_V);

		// got Save button
		for (int i = 0; i < 3; i++) {
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
		}
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);

		// close Window
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_TAB);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}
