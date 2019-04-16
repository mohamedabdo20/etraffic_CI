package isoft.etraffic.cta.sddipages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.SeleniumWraper;

public class ContractsNavigationPage extends SeleniumWraper
{
	private By MainMenu = By.id("menu-trigger");
	private By ContractfromMenu = By.id("listCategoryLinksId2_13");
    private By ReviewContract = By.xpath("//*[contains(@data-name,'متابعة العقود')]");
    
    
    private By ContractNo = By.id("contractNoId");
    private By SelectContractTypye = By.id("allowedTypeId");
    private By SelectStatus = By.id("contractStatusId");
    private By Search = By.id("searchBttnId");
    //private By SelectOneContract = By.xpath("//*[class='x-grid3-row-table']");
    private By SelectOneContract = By.className("x-grid3-col x-grid3-cell x-grid3-td-0 x-grid3-cell-first");
	private By Financial = By.id("financesBtnId");
	private By ReviewContractRequests = By.xpath("//*[contains(@data-name,'متابعة طلبات العقود')]");
    
	public void ContractsMainMenu() throws InterruptedException {
		clickElement(MainMenu);
		Thread.sleep(3000);
		scrollToelement(ContractfromMenu);
		clickElement(ContractfromMenu);
	}
    public void opencontractandfranchisfrommenupage() throws InterruptedException {
    	
    	ContractsMainMenu();
		clickElement(ReviewContract);	}

	
    
 public void openContractRequests() throws InterruptedException {
    	
    	ContractsMainMenu();
		clickElement(ReviewContractRequests);	}
    
    
    public void searchForContract() throws InterruptedException {
    	driver.switchTo().frame(driver.findElement(By.id("myIframe")));
		writeToElement(ContractNo, "123/123");
		selectFromListByValue(SelectContractTypye, "2");
		selectFromListByValue(SelectStatus, "2");
		clickElement(Search);
		clickElement(SelectOneContract);
		clickElement(Financial);
	}
	

	public ContractsNavigationPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
    
	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
public void openclassifactionpage() throws InterruptedException {
	ContractsMainMenu();
	
	
		
 }

}
