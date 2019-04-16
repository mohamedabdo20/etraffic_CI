package isoft.etraffic.cta.ftfpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.SeleniumWraper;

public class AddNewContractPage extends SeleniumWraper 
{
	 
	By contractlink = By.id("action");
	By addnewcontractbtn = By.id("actions1:add");
	By companyTFtxt = By.className("select2-search__field");
	
	By companyTFsearchResult = By.className("select2-results__option select2-results__option--highlighted");
	
	By contractTypetxt = By.xpath("//*[@id='contractTypeLookupBeanIdInputLookupPanelDiv']//*[@class='select2-selection__rendered']");
	
	By contractTypeSearchResult = By.className("select2-results__option select2-results__option--highlighted");
	By viewDatabtn = By.id("octKpiForm:view");
	By contractNotxt = By.id("octKpiForm:organizationcontractNo:organizationcontractNo");
	By regNumberOfVehiclestxt = By.id("octKpiForm:RegNoOfBooklets:RegNoOfBooklets");
	By savebtn = By.id("octKpiForm:j_idt296:saveButtonId");
	
	
	
	public void fillcontractform(String TF , String contractType , String contractNumber , String regNumberOfVehicles) throws InterruptedException 
	{
		clickElementJS(contractlink);
		clickElementJS(addnewcontractbtn);
		writeToElement(companyTFtxt, TF);
		Thread.sleep(2000);
		hitEnterToElement(companyTFtxt);
		clickElementJS(contractTypetxt);
		writeToElement(companyTFtxt, contractType);
		hitEnterToElement(companyTFtxt);
		clickElementJS(viewDatabtn);
		writeToElement(contractNotxt, contractNumber);
		writeToElement(regNumberOfVehiclestxt, regNumberOfVehicles);
		clickElementJS(savebtn);
	}

	public AddNewContractPage(WebDriver driver) {
		super(driver);
		
	}

	@Override
	public boolean isPageLoaded() {
		
		return false;
	}

}
