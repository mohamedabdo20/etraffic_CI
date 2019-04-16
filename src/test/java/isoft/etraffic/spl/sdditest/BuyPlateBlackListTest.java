package isoft.etraffic.spl.sdditest;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class BuyPlateBlackListTest {
    /******************************* Classs Variables ************************************/
    WebDriver driver;
    int seconds=0;
    WebDriverWait webDriverWait;
    Actions actions;
    String siteURL = "https://tst12c:7793/trfesrv/test_login.jsp";
    // WebElements By Definition
    By trafficFileTextSelector = By.name("trafficNo");
    By loginBtnSelector = By.name("Login");
   // By licensingSelector = By.("//*[@id=\\\"readMe\\\"]/div[4]/div[1]/div/div/div[1]/a/img");
    //By buyPlateURLSelector = By.xpath("//*[@id=\"readMe\"]/div[4]/div[3]/div[1]/div/ul/li[2]/a");
    By plateInfoURLSelector = By.xpath("//*[@id=\"plateTabId\"]/a");
    By plateCode = By.id("plateCodeId");
    By plateNo1=By.id("txtDigit1");
    By plateNo2=By.id("txtDigit2");
    By plateNo3=By.id("txtDigit3");
    By plateNo4=By.id("txtDigit4");
    By plateNo5=By.id("txtDigit5");
    By searchCriteriaBanLocator = By.id("searchButtonId");
    By plateDetailsBtnLocator = By.className("plate-details");
    By buyPlateBtnLocator = By.id("newButton");
    By vehicleRenewalChkboxLocator = By.id("cbTncVehicleRenewal");
    By confirmBtnLocator = By.id("btnStartProcess");
    By confirmAndProceedBtnLocator = By.id("idConfrimAndProceedButton");
    By choosedateLocator = By.id("txtDeliveryDateCourier");
    By calendarIconSelector = By.xpath("//*[@id='dp1']/span/span");
    By calendarDateValueSelector = By.xpath("//*[@id='dp1']/div/ul/li[1]/div/div[1]/table/tbody/tr[4]/td[3]");
    By contactnameLocator = By.id("shipmentContactName");
    By emirateLocator = By.name("shipmentEmirate");
    By areaLocator = By.id("shipmentArea");
    By adresslocator1 = By.id("shipmentAddress1");
    By adresslocator2 = By.id("shipmentAddress2");
    By companyname = By.id("shipmentCompanyName");
    By phoneNolocator = By.id("phoneNumber");
    By mobileNo= By.id("mobileNumber");
    By email=By.id("email");
    By emailconfirm=By.id("emailConfirm");
    By Pobox=By.id("newPoBoxNumber");
    By Confirmdelivery = By.id("btnGoToStep31");
    By Paymentmethod = By.id("payButtonId");
    By PaymentBtn= By.xpath("//*[@id=\"btnGoToStep4\"]");





    /******************************* Class Methods ***************************************/


    @Test
    public void buyPlate() throws InterruptedException {

        driver = new ChromeDriver();
        driver.navigate().to(siteURL);
        webDriverWait = new WebDriverWait(driver, 10);
        actions = new Actions(driver);


        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(trafficFileTextSelector))
                .sendKeys("10453583");

        clickElementJS(loginBtnSelector);

       waitForElement(By.partialLinkText("Go to Licensing"));
        clickElementJS(By.partialLinkText("Go to Licensing"));
        waitForElement(By.partialLinkText("Buy Plate"));
        clickElementJS(By.partialLinkText("Buy Plate"));

        actions.moveToElement(driver.findElement(plateInfoURLSelector)).build().perform();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(plateInfoURLSelector)).click();

        //Thread.sleep(5000);

//        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(plateCode)).click();
        waitForElement(plateCode);
        Select dropdown = new Select(driver.findElement(plateCode));
        dropdown.selectByValue("95");


        actions.moveToElement(driver.findElement(plateNo1)).build().perform();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(plateNo1)).sendKeys("2");

        actions.moveToElement(driver.findElement(plateNo2)).build().perform();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(plateNo2)).sendKeys("4");

        actions.moveToElement(driver.findElement(plateNo3)).build().perform();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(plateNo3)).sendKeys("4");

        actions.moveToElement(driver.findElement(plateNo4)).build().perform();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(plateNo4)).sendKeys("6");

        actions.moveToElement(driver.findElement(plateNo5)).build().perform();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(plateNo5)).sendKeys("4");



        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(searchCriteriaBanLocator)).click();

        //webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(buyPlateBtnLocator)).click();
        waitForElement(buyPlateBtnLocator);
        clickElementJS(buyPlateBtnLocator);
        
        actions.moveToElement(driver.findElement(vehicleRenewalChkboxLocator)).build().perform();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(vehicleRenewalChkboxLocator)).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(confirmBtnLocator)).click();


        actions.moveToElement(driver.findElement(confirmAndProceedBtnLocator)).build().perform();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(confirmAndProceedBtnLocator)).click();

      clickElementJS(By.xpath("//*[@id='dp1']/span/span"));
        Thread.sleep(1000);
        driver.findElements(By.xpath("//*[@class='day']")).get(0).click();

        actions.moveToElement(driver.findElement(contactnameLocator)).build().perform();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(contactnameLocator)).sendKeys("Ahmed");


        Select emirate = new Select(driver.findElement(emirateLocator));
        emirate.selectByValue("DXB");

        Thread.sleep(5000);

        Select area = new Select(driver.findElement(areaLocator));
        area.selectByValue("37");


        actions.moveToElement(driver.findElement(companyname)).build().perform();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(companyname)).sendKeys("idealsoft");
        actions.moveToElement(driver.findElement(phoneNolocator )).build().perform();


        actions.moveToElement(driver.findElement(adresslocator1)).build().perform();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(adresslocator1)).sendKeys("Reem Center Block 42 in front of");

        actions.moveToElement(driver.findElement(adresslocator2)).build().perform();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(adresslocator2)).sendKeys("King abdulla street 12345");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(phoneNolocator)).sendKeys("087897897");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(mobileNo)).sendKeys("0555657656");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(email)).sendKeys("a@a.com");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(emailconfirm)).sendKeys("a@a.com");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(Pobox)).sendKeys("1234");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(Confirmdelivery)).click();


        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(Paymentmethod)).click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(PaymentBtn)).click();

        WebDriverWait wait = new WebDriverWait(driver, 15, 100);
        WebElement myElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@name,'chkConfirm')]")));
        myElement.click();
        driver.findElement(By.xpath("//input[@name='btnPay']")).click();
        Thread.sleep(3000);
        WebDriverWait waitt = new WebDriverWait(driver, 15, 100);
        WebElement myElementt = waitt.until(ExpectedConditions.elementToBeClickable(By.id("txtCardNo")));
        myElementt.sendKeys("4111111111111111");
        driver.findElement(By.xpath("//select[@ng-model='month']")).click();
        driver.findElement(By.xpath("//option[@label='January']")).click();
        driver.findElement(By.xpath("/html/body/div[1]/div/form/div/div/div[2]/div[2]/div[1]/div[2]/table/tbody/tr[3]/td[3]/table/tbody/tr/td[2]/select")).sendKeys("2020");
        Thread.sleep(5000);
        driver.findElement(By.id("txtCvvNo")).sendKeys("123");
        driver.findElement(By.id("payButton")).click();
        System.out.println("review finished");
        Thread.sleep(10000);
    }
    
    public void waitForElement(By by) throws InterruptedException {
		seconds=0;
		while (seconds < 60) {
			try {
				driver.findElement(by).isDisplayed();
				Exception e = new Exception();
				if(!driver.findElement(by).isEnabled())
					throw e ;
				return;
			} catch (Exception e) {
				Thread.sleep(1000);
				seconds++;
				waitForElement(by);
			}
		}
	}
    
    public void clickElementJS(By locator) throws InterruptedException {
    	//waitForElement(locator);
    		((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(locator));
    	}
}


