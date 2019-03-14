package isoft.etraffic.wrapper;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class SeleniumWraper {

	protected WebDriver driver;
	WebDriverWait wait;
	int seconds;
	By loadingImg = By.xpath("//*[@class='page-loader']");
	
	public SeleniumWraper(WebDriver driver) {
		this.driver = driver;
	}

	public abstract boolean isPageLoaded();

	public boolean isPageLoaded(By locator, String textToSearch) {
		wait = new WebDriverWait(driver, 60, 200);
		try {
			wait.until(ExpectedConditions.textToBe(locator, textToSearch));
		} catch (Exception e) {
			return false;
		}
		return true;

	}
	public void FileInputElement(By locator, String text) {
		driver.findElement(locator).sendKeys(text);
		;}


	public void selectFromList(WebElement element, String value) {
		Actions a = new Actions(driver);
		a.moveToElement(element).perform();

		Select dropDownList = new Select(element);
		dropDownList.selectByVisibleText(value);
	}

	// Wrapper
	public void writeToElement(By locator, String text) throws InterruptedException {
		waitForElement(locator);
		driver.findElement(locator).clear();
		driver.findElement(locator).sendKeys(text);
	}

	public void writeToElementWithoutClear(By locator, String text) throws InterruptedException {
		// waitForElement(locator);
		driver.findElement(locator).sendKeys(text);
	}

	// Wrapper
	public void hitEnterToElement(By locator) {
		// wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		driver.findElement(locator).sendKeys(Keys.chord(Keys.RETURN));

	}

	public void hitTabToElement(By locator) {
		// wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		driver.findElement(locator).sendKeys(Keys.chord(Keys.TAB));

	}

	public void hitArrowDownToElement(By locator) {
		// wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		driver.findElement(locator).sendKeys(Keys.chord(Keys.ARROW_DOWN));

	}

	public void hitBackspaceToElement(By locator) {
		// wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		driver.findElement(locator).sendKeys(Keys.chord(Keys.BACK_SPACE));

	}

	public static void uploadFile(String image) throws AWTException, InterruptedException {

		// Specify the file location with extension
		StringSelection imageSelection = new StringSelection(System.getProperty("user.dir") + "\\Output\\" + image);
		// Copy the image to the clipboard
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(imageSelection, null);

		//elem.click();

		// Create object of Robot class for upload the image
		Robot robot = new Robot();

		// Thread.sleep(5000);

		// Press and Release the Enter button
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);

		// Press Ctrl+V buttons
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);

		// Release Ctrl+V buttons
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_V);

		Thread.sleep(5000);

		// Click Enter button to open the image
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}
	
	public static void uploadImage(String image) throws AWTException, InterruptedException {

		// Specify the file location with extension
		StringSelection imageSelection = new StringSelection(System.getProperty("user.dir") + "\\Images\\" + image);
		// Copy the image to the clipboard
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(imageSelection, null);

		// Create object of Robot class for upload the image
		Robot robot = new Robot();

		// Press and Release the Enter button
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);

		// Press Ctrl+V buttons
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);

		// Release Ctrl+V buttons
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_V);
		Thread.sleep(500);
		// Click Enter button to open the image
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}
	
	// Wrapper


	public void clickElement(By locator) throws InterruptedException {
		driver.findElement(locator).click();
	}

	public void clickButton(By locator) throws InterruptedException {
		waitForElement(locator);
		driver.findElement(locator).click();
	}

	public void clickElementJS(By locator) throws InterruptedException {

		((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(locator));
	}

	public void clickElementJS(WebElement locator) throws InterruptedException {
		// waitForElement(locator);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", locator);
	}

	public void clickByLinkTxt(String lnkTxt) throws InterruptedException
	{
		clickElementJS(By.partialLinkText(lnkTxt));
	}
	public void scrollToelement(By locator) {
		WebElement element = driver.findElement(locator);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true)", element);
	}

	public void scrolldown() {
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,450)", "");
	}

	public void scrollup() {
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-450)", "");
	}

	public void selectFromListByVisibleText(By locator, String vText) {
		Select listObj = new Select(driver.findElement(locator));
		listObj.selectByVisibleText(vText);
	}

	public void selectFromListByValue(By locator, String value) {
		Select listObj = new Select(driver.findElement(locator));
		listObj.selectByValue(value);
	}

	public void selectFromFTFList(By locator, String value) {
		driver.findElement(locator).sendKeys(value);
		driver.findElement(locator).sendKeys(Keys.chord(Keys.RETURN));
	}

	public void selectFirstValue(By locator) {
		// driver.findElement(locator).sendKeys(Keys.chord(Keys.ARROW_DOWN));
		driver.findElement(locator).sendKeys(Keys.chord(Keys.RETURN));
	}

	public void selectFirstValueOnline(By locator) {
		driver.findElement(locator).sendKeys(Keys.chord(Keys.ARROW_DOWN));
	}

	public String getElementText(By locator) {
		System.out.println("Element Text: " + driver.findElement(locator).getText());
		return driver.findElement(locator).getText();
	}

	public String getElementText(WebElement element) {
		return element.getText();
	}
	
	public void switchToFrame(String frame) {
		driver.switchTo().frame(frame);
	}

	public void switchToFrame(By frame) {
		driver.switchTo().frame(driver.findElement(frame));
	}

	public void switchToWindow(String nameOrHandle) {
		driver.switchTo().window(nameOrHandle);
	}

	public void acceptAlert() {
		driver.switchTo().alert().accept();
	}

	public void dismissAlert() {
		driver.switchTo().alert().dismiss();
	}

	public void closeAlert() {
		driver.switchTo().alert();
		driver.close();
	}

	public String switchToSecondWindow() throws InterruptedException {
		//waitForNumberofWindowsToEqual(2);//this method to wait for 2nd window
		
		Thread.sleep(1000);
		Set<String> handles;

		String firstWinHandle, winHandle;
		while (true) {
			try {
				handles = driver.getWindowHandles();
				System.out.println("handles Size: " + handles.size());
				firstWinHandle = driver.getWindowHandle();
				System.out.println("firstWinHandle: " + firstWinHandle);
				handles.remove(firstWinHandle);
				winHandle = (String) handles.iterator().next();
				break;
			} catch (Exception e) {
				Thread.sleep(1000);
			}
		}
		System.out.println("winHandle: " + winHandle);
		if (winHandle != firstWinHandle) {

			// To retrieve the handle of second window, extracting the handle which does not
			// match to first window handle

			String secondWinHandle = winHandle; // Storing handle of second window handle

			// Switch control to new window

			driver.switchTo().window(secondWinHandle);
			try {
				driver.get("javascript:document.getElementById('overridelink').click();");
			} catch (Exception e) {
				Thread.sleep(1000);
			}
		}
		return firstWinHandle;
	}

	public void waitForNumberofWindowsToEqual(int i) throws InterruptedException {
		Thread.sleep(1000);
		Set<String> handles = driver.getWindowHandles();
		if(handles.size() != i)
		{
			Thread.sleep(1000);
			waitForNumberofWindowsToEqual(i);
		}
		
	}

	public void closeCurrentWindow() throws InterruptedException
	{
		//Thread.sleep(1000);
		driver.close();
	}
	
	public String getElementAttributeValue(By locator, String attribute)
	{
		System.out.println("Attribute value: " + driver.findElement(locator).getAttribute(attribute));
		return driver.findElement(locator).getAttribute(attribute);
	}
	
	public String getElementAttributeValue(WebElement element, String attribute)
	{
		System.out.println("Attribute value: " + element.getAttribute(attribute));
		return element.getAttribute(attribute);
	}
	
	public String switchToThirdWindow() throws InterruptedException {
		// waitForNumberofWindowsToEqual(2);//this method is for wait

		Set<String> handles;

		String firstWinHandle, secindWinHandle, winHandle;
		while (true) {
			try {
				handles = driver.getWindowHandles();
				System.out.println("handles Size: " + handles.size());
				firstWinHandle = driver.getWindowHandle();
				System.out.println("firstWinHandle: " + firstWinHandle);
				handles.remove(firstWinHandle);
				secindWinHandle = (String) handles.iterator().next();
				handles.remove(secindWinHandle);
				System.out.println("handles Size = 3 ");
				winHandle = (String) handles.iterator().next();
				System.out.println("handles 3: " + winHandle);
				driver.switchTo().window(winHandle);
				break;
			} catch (Exception e) {
				Thread.sleep(1000);
			}
		}
		// System.out.println("winHandle: " + winHandle);
		// if (winHandle != firstWinHandle) {
		//
		// // To retrieve the handle of second window, extracting the handle which does
		// not
		// // match to first window handle
		//
		// String secondWinHandle = winHandle; // Storing handle of second window handle
		//
		// // Switch control to new window
		//
		// driver.switchTo().window(secondWinHandle);
		// try {
		// driver.get("javascript:document.getElementById('overridelink').click();");
		// } catch (Exception e) {
		// Thread.sleep(1000);}
		// }
		return secindWinHandle;
	}

	public void refresh()
	{
		driver.navigate().refresh();
	}
	public void waitForElement(By by) throws InterruptedException {
		seconds = 0;
		while (seconds < 2) {
			try {
				driver.findElement(by).isDisplayed();
				Exception e = new Exception();
				if (!driver.findElement(by).isEnabled())
					throw e;
				return;
			} catch (Exception e) {
				Thread.sleep(1000);
				seconds++;
			}
		}
	}

	public void tryClickElement(By locator) throws InterruptedException {
		while (true) {
			try {
				clickElementJS(locator);
				break;
			} catch (Exception e) {
				Thread.sleep(1000);
			}
		}
	}

	public void tryClickElement(WebElement webElement) throws InterruptedException {
		while (true) {
			try {
				clickElementJS(webElement);
				break;
			} catch (Exception e) {
				Thread.sleep(1000);
			}
		}
	}
	
	public boolean isElementDisplayed(By by) {
		if (driver.findElement(by).isDisplayed())
			return true;
		else
			return false;
	}
	
	public void waitforLoading() throws InterruptedException
	{
		waitTillLoadingBegins();
		waitTillLoadingFinish();
	}
	
	private void waitTillLoadingBegins() throws InterruptedException
	{
		System.out.println("waitTillLoadingBegins");
		if(getElementAttributeValue(loadingImg, "style").contains("none"))
		{
			Thread.sleep(1000);
			waitTillLoadingBegins();
		}
	}
	
	private void waitTillLoadingFinish() throws InterruptedException
	{
		System.out.println("waitTillLoadingFinish");
		if(getElementAttributeValue(loadingImg, "style").contains("block"))
		{
			Thread.sleep(1000);
			waitTillLoadingFinish();
		}
	}
	public void tryClickElement(By locator, int seconds) throws InterruptedException {
		int sec=0;
		while (sec<seconds) {
			try {
				clickElementJS(locator);
				break;
			} catch (Exception e) {
				Thread.sleep(1000);
				sec++;
			}
		}
	}
}
