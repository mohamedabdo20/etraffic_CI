package  isoft.etraffic.spl.sddipages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebElement;

import isoft.etraffic.wrapper.SeleniumWraper;

public class SubscribeToElctronicPage extends SeleniumWraper {
	 
  

	By trafficFileTextSelector = By.name("trafficNo");
    By loginBtnSelector = By.name("Login");
    By vehicleRenewalChkboxLocator = By.id("cbTncVehicleRenewal");
    By confirmBtnLocator = By.id("btnStartProcess");
    By Auctiontype=By.id("auctionType");
    By confirmAndProceedBtnLocator = By.id("ConfirmButtonId");
    By Paymentmethod = By.id("payButtonId");
    By PaymentBtn= By.xpath("//*[@id=\"btnGoToStep4\"]");
    By SubscribeAuction = By.partialLinkText("Subscribe to Auction");
    By DepositType=By.id("depositType");
    By SelectBank=By.id("bankId");
    By ChequeNO=By.id("chequeNo");
    By ChequeDueDate=By.id("chequeDueDate");
    
    
    public void changePlateOwnership() throws InterruptedException {
    	//waitForElement(By.partialLinkText("Subscribe to Auctionp"));
		clickElementJS(By.partialLinkText("Subscribe to Auction"));
		
		
    }
    
    public void SelectActionType() throws InterruptedException  {
    
    	waitForElement(Auctiontype);
    	selectFromListByValue(Auctiontype, "1");
    	waitForElement(DepositType);
    	selectFromListByValue(DepositType, "4");
    	selectFromListByValue(SelectBank, "226");
    	writeToElement(ChequeNO, "12345688");
    	clickElementJS(By.xpath("//*[@id=\"dp2\"]/span/span"));
		Thread.sleep(1000);
		driver.findElements(By.xpath("//*[@class='day']")).get(0).click();
    	
    
	
    }
    
    public void confirmProcess() throws InterruptedException {
		waitForElement(confirmAndProceedBtnLocator);
		tryClickElement(confirmAndProceedBtnLocator);
		//clickElement(confirmAndProceedBtn);
	}

	public String TraxIDForSubscription() {

		String trsNo;

		RemoteWebElement transactionElm = (RemoteWebElement) 
				driver.findElement(By.xpath("//*[@class=\"col-lg-12 col-md-12 col-sm-12 col-xs-12\"]//h3[1]"));
		trsNo = transactionElm.getText().trim().split(":")[1];

		return trsNo;
	}
    
    public SubscribeToElctronicPage(WebDriver driver) {
  		super(driver);
  		// TODO Auto-generated constructor stub
  	}
    
	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

}
