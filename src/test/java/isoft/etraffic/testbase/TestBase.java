package isoft.etraffic.testbase;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class TestBase {

	protected WebDriver driver = null;
	public static final String testDataExcelFileName = System.getProperty("user.dir") + "/Exceldata/testdata.xlsx";

	public TestBase() {
		super();
	}

	@BeforeMethod
	@Parameters({ "url", "browser", "lang" })
	public void setup(@Optional("https://tst12c:7793/trfesrv/public_resources/public-access.do") String url,
			@Optional("CHROME") String browser, @Optional("en") String lang) {

		switch (browser) {
		case "CHROME":
			System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--new-window");
			options.addArguments("-incognito");
			driver = new ChromeDriver(options);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			driver.get(url);
			break;

		case "FIREFOX":
			System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver.exe");
			driver = new FirefoxDriver();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			driver.get(url);
			break;
		case "IE":
			System.setProperty("webdriver.ie.driver", "./drivers/IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			driver.get("https://tst12c:7791/traffic/faces/jsf/auth/login.jsf");
			driver.get("javascript:document.getElementById('overridelink').click();");
			break;
		}
	}

	@AfterMethod
	public void tearDown(ITestResult result) throws IOException, InterruptedException {
		String test = result.getName();
		String datenow = new SimpleDateFormat("dd-M-yyyy-hh-mm").format(new Date());
		Thread.sleep(2000);
		if (result.isSuccess())
			isoft.etraffic.utils.ScreenCapture.getScreenShot(driver,
					"./screenshots/Passed/" + test + "_" + datenow + ".png");
		else
			isoft.etraffic.utils.ScreenCapture.getScreenShot(driver,
					"./screenshots/Failed/" + test + "_" + datenow + ".png");
		driver.quit();
	}

}