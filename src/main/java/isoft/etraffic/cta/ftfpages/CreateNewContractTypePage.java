package isoft.etraffic.cta.ftfpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import isoft.etraffic.wrapper.SeleniumWraper;

public class CreateNewContractTypePage extends SeleniumWraper{

	By ContractSettingPage = By.xpath("//*[contains(@data-name,'إعدادات النظام')]");
	By ContractsType = By.xpath("//a[contains(@href,'/cml/contracts/franchise_preferences_followup.do')]");
	By SwitchFrame = By.id("myIframe");
	By AddNewContractType = By.name("closeBttn");
	By ContarctNameAR = By.name("nameAr");
	By ContarctNameEN = By.xpath("//*[contains(@name,'nameEn')]");
	By AllowedContractType = By.id("allowedTypeId");
	By RegNoOfBooklets = By.id("regNoOfBookletsId");
	By AddNoOfBooklets = By.id("addNoOfBookletsId");
	By RegGracePeriod = By.id("regGracePeriodId");
	By AddGracePeriod = By.id("addGracePeriodId");
	By RepGracePeriod = By.id("repGracePeriodId");
	By ContractPeriod = By.id("contractPeriodId");
	By DriversCalcMethod = By.xpath("//*[contains(@name,'driversCalcMethod')]");
	//By SelectClassifications = By.xpath("//*[contains(@name,'allowedClassifications')]");
	By SaveBttn = By.id("saveBttnId");
	
	
	public void fillData() throws InterruptedException {
		
		clickElement(ContractSettingPage);
		switchToFrame(SwitchFrame);
		clickElement(ContractsType);
		//switchToFrame(SwitchFrame);
		Thread.sleep(5000);
		Actions a = new Actions(driver);
		WebElement addnewtypeElement = driver.findElement(AddNewContractType) ;
		a.moveToElement(addnewtypeElement).perform();
		clickElement(AddNewContractType);
		writeToElement(ContarctNameAR, "نوع عقد اتوميشن");
		writeToElement(ContarctNameEN, "New Automation Contract Type");
		selectFromListByValue(AllowedContractType, "5");
		writeToElement(RegNoOfBooklets, "2");
		writeToElement(AddNoOfBooklets, "2");
		writeToElement(RegGracePeriod, "90");
		writeToElement(AddGracePeriod, "90");
		writeToElement(RepGracePeriod, "90");
		writeToElement(ContractPeriod, "3");
		selectFromListByValue(DriversCalcMethod, "2");
		
		
		Select SelectClassifications = new Select(driver.findElement(By.xpath("\"//*[contains(@name,'allowedClassifications')]")));
	    int selectOptions = SelectClassifications.getOptions().size();
	    SelectClassifications.selectByIndex(selectOptions - 1);
		
		
		//selectFromListByValue(SelectClassifications, "101");
		clickElement(SaveBttn);
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public CreateNewContractTypePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}
