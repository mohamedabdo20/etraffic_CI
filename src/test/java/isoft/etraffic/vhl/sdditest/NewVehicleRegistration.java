package isoft.etraffic.vhl.sdditest;

import java.sql.SQLException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import isoft.etraffic.db.DBQueries;
import isoft.etraffic.enums.VehicleClass;
import isoft.etraffic.vhl.sddipages.CommonPageOnline;

public class NewVehicleRegistration {

	// Vehicle chassis should be exempted from FTF
	VehicleClass vehicleClass = VehicleClass.LightVehicle;
	String possessionNumber = "62835173", 
			trafficFileNo = "10184041", 
			plateCategory = "Privacy", 
			chassis = "OCTOBERRELEASE000",
			weight = "2500",
			plate, plateCode;
	WebDriver driver;
	int wait = 0;
	CommonPageOnline commonPage;
	DBQueries dbQuery = new DBQueries();

	@BeforeTest
	public void setup() throws ClassNotFoundException, SQLException, InterruptedException {
		// SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss");
		// Calendar cal = Calendar.getInstance();
		// chassis = sdf.format(cal.getTime()).replace("-", "").replace(":", "")+"00";

		dbQuery.addInsurance(chassis, vehicleClass);
		dbQuery.addTest(chassis, vehicleClass, weight);
		
		startBrowser("chrome");
		commonPage = new CommonPageOnline(driver);
		commonPage.loginAsTrustedAgent("مؤسسة الترخيص الموثوقة");
	}

	@Test
	public void registerNewVehicle() throws Exception {

		JavascriptExecutor jsx1 = (JavascriptExecutor) driver;

		selectFromList(driver.findElement(By.id("selectServiceId")), "New Vehicle Registration");
		waitForElement(By.partialLinkText("Traffic File No"));
		jsx1.executeScript("arguments[0].click();", driver.findElement(By.partialLinkText("Traffic File No")));
		Thread.sleep(2000);
		jsx1.executeScript("arguments[0].click();", driver.findElement(By.xpath("//*[@name='trafficFileNo']")));
		driver.findElement(By.xpath("//*[@name='trafficFileNo']")).clear();
		driver.findElement(By.xpath("//*[@name='trafficFileNo']")).sendKeys(trafficFileNo);
		jsx1.executeScript("arguments[0].click();", driver.findElement(By.id("searchByTrafficFileId")));
		Thread.sleep(2000);
		waitForElement(By.id("idConfirmButton"));
		jsx1.executeScript("arguments[0].click();", driver.findElement(By.id("idConfirmButton")));

		jsx1.executeScript("arguments[0].click();", driver.findElement(By.partialLinkText("Vehicles")));

		jsx1.executeScript("arguments[0].click();", driver.findElement(By.xpath("//*[@id='readMe']/li/div[1]/a")));
		jsx1.executeScript("arguments[0].click();", driver.findElement(By.linkText("New Vehicle Registration")));
		Thread.sleep(1000);

		waitForElement(By.id("terms"));
		jsx1.executeScript("arguments[0].click();", driver.findElement(By.id("terms")));
		jsx1.executeScript("arguments[0].click();", driver.findElement(By.id("openProAnchor")));
		// Thread.sleep(5000);

		waitForElement(By.id("emirateSelect"));
		jsx1.executeScript("window.scrollBy(0,450)", "");// New Vehicle Registration
		selectFromList(driver.findElement(By.id("emirateSelect")), "Dubai");
		Actions actions1 = new Actions(driver);
		actions1.sendKeys(Keys.TAB);
		actions1.build().perform();

		selectFromList(driver.findElement(By.id("sourceTypeSelect")), "Possession");
		driver.findElement(By.id("sourceNumberId")).sendKeys(possessionNumber);
		driver.findElement(By.id("sourceIssueDate_id")).click();

		selectFromList(driver.findElement(By.id("plateSelect")), "Private");
		Thread.sleep(500);
		jsx1.executeScript("arguments[0].click();", driver.findElement(By.id("confirmAndProceedButton")));

		waitForElement(By.id("vhlSourceSelect"));
		Select list6 = new Select(driver.findElement(By.id("vhlSourceSelect")));
		list6.selectByVisibleText("Used");
		System.out.println("chassis: " + chassis);
		for (int i = 1; i <= 17; i++) {
			try {
				driver.findElement(By.id("chassisNo" + i + "")).sendKeys(Character.toString(chassis.charAt(i - 1)));
			} catch (Exception e) {
				Thread.sleep(1000);
				driver.switchTo().alert().accept();
				driver.findElement(By.id("chassisNo" + i + "")).sendKeys(Character.toString(chassis.charAt(i - 1)));
			}
		}
		
		


//		waitForElement(By.id("makerLookupId"));
//		jsx1.executeScript("window.scrollBy(0,550)", ""); // Vehicle Information
//		jsx1.executeScript("arguments[0].click();", driver.findElement(By.id("makerLookupId")));
//
//		Thread.sleep(500);
//		driver.switchTo().frame(0);
//		driver.findElement(By.xpath("//*[@id=\"payopt1\"]/li/table/tbody/tr/td[1]/p/span/em/input")).sendKeys("KIA");
//		driver.findElement(By.id("searchByMakerId")).click();
//		Thread.sleep(1000);
//		driver.findElement(By.xpath("//*[@class='srchrsult']//*[@value='10144']")).click();
//		driver.findElement(By.xpath("//*[@id=\"selectMakerId\"]/div/button/span/em")).click();
//		actions1.sendKeys(Keys.TAB);
//		actions1.build().perform();
//		Thread.sleep(500);
//
//		driver.findElement(By.id("typeDetailsAnchor")).click();
//		// Thread.sleep(2000);
//		waitForElement(By.id("cboxIframe"));
//		WebElement markerframe = driver.findElement(By.id("cboxIframe"));
//		driver.switchTo().frame(markerframe);
//		Thread.sleep(500);
//		driver.findElement(By.xpath("//*[@id=\"allPageContentId\"]/div[1]/table/tbody/tr[2]/td[1]/input")).click();
//		driver.findElement(By.xpath("//*[@id=\"selectCompanyId\"]/div/button")).click();
//
//		actions1.sendKeys(Keys.TAB);
//		actions1.build().perform();
//		Thread.sleep(500);
//
//		driver.findElement(By.xpath("//*[@id='allPageContentId']/div[1]/div[1]/div/div/table/tbody/tr[3]/td[5]/a"))
//				.click();
//		waitForElement(By.id("cboxIframe"));
//		WebElement descriptionFrame = driver.findElement(By.id("cboxIframe"));
//		driver.switchTo().frame(descriptionFrame);
//		driver.findElement(By.xpath("//*[@name='descriptionEn']")).sendKeys(plateCategory);
//		driver.findElement(By.id("searchByCompanyId")).click();
//		Thread.sleep(500);
//		driver.findElement(By.xpath("//*[@class='srchrsult']/tbody/tr[2]/td/input")).click();
//		driver.findElement(By.xpath("//*[@id='selectVehicleDescId']/div/button")).click();
//		actions1.sendKeys(Keys.TAB);
//		actions1.build().perform();
//		Thread.sleep(500);
//
//		driver.findElement(By.xpath("//*[@id='allPageContentId']/div[1]/div[1]/div/div/table/tbody/tr[4]/td[2]/em/a"))
//				.click();
//		Thread.sleep(1000);
//		waitForElement(By.id("cboxIframe"));
//		driver.switchTo().frame("cboxIframe");
//		driver.findElement(By.xpath("//*[@class='srchrsult']//*[@value='21']")).click();
//		driver.findElement(By.xpath("//*[@id='selectCountryId']/div/button")).click();
//		Thread.sleep(1000);
//
//		driver.findElement(By.id("engineNumber")).sendKeys("12345678910");

//		Select list7 = new Select(driver.findElement(By.id("fuelSelect")));
//		list7.selectByVisibleText("Benzene");
//		Thread.sleep(1000);
//		driver.findElement(By.id("unloadedWeight")).sendKeys("3000");
//		driver.findElement(By.id("numOfSeats")).sendKeys("4");
//		driver.findElement(By.id("numOfDoors")).sendKeys("5");
//		Select list8 = new Select(driver.findElement(By.id("color1Select")));
//		list8.selectByVisibleText("White");
//
		Select list9 = new Select(driver.findElement(By.id("numOfSalesSelect")));
		list9.selectByVisibleText("0");
		Select list10 = new Select(driver.findElement(By.id("registrationYearSelect")));
		list10.selectByVisibleText("one Year");
		Select list11 = new Select(driver.findElement(By.id("plateSourceSelect")));
		list11.selectByVisibleText("Daily Distribution");
		Select list12 = new Select(driver.findElement(By.id("frontPlateSizeSelect")));
		list12.selectByVisibleText("Long");
		Select list13 = new Select(driver.findElement(By.id("backPlateSizeSelect")));
		list13.selectByVisibleText("Long");

		

		Thread.sleep(1000);
		driver.findElement(By.id("confirmAndProceedButton")).click();
		Thread.sleep(2000);
		driver.switchTo().alert().dismiss();
		
		waitForElement(By.id("cboxIframe"));
		
		waitForElement(By.id("cboxIframe"));
		driver.switchTo().frame("cboxIframe");
		Thread.sleep(500);
		driver.findElement(By.id("notAgree")).click();
		
		commonPage.selectDeliveryDateCourierTrustedAgent(true);
	}

	private void selectFromList(WebElement element, String value) {
		Actions a = new Actions(driver);
		a.moveToElement(element).perform();

		Select dropDownList = new Select(element);
		dropDownList.selectByVisibleText(value);
	}

	private void waitForElement(By by) throws InterruptedException {
		while (wait < 60) {
			try {
				driver.findElement(by);
				wait = 0;
				return;
			} catch (Exception e) {
				Thread.sleep(1000);
				wait++;
				waitForElement(by);
			}
		}
	}

	private void startBrowser(String browser) {
		switch (browser) {
		case "chrome":
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--new-window");
			options.addArguments("-incognito");
			ChromeDriverService service = ChromeDriverService.createDefaultService();
			driver = new ChromeDriver(service, options);
			// Go to URL
			driver.get("https://tst12c:7793/trfesrv/test_login.jsp");
			driver.manage().window().maximize();
			break;
		case "ie":
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			// Go to URL
			driver.get("https://tst12c:7793/trfesrv/test_login.jsp");
			break;
		default:
			System.out.println("Invalid browser type");
			break;
		}
	}
}
