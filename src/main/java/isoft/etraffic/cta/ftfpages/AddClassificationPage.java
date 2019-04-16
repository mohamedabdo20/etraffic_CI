package isoft.etraffic.cta.ftfpages;

import java.awt.AWTException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.SeleniumWraper;

public class AddClassificationPage extends SeleniumWraper {
	
	public AddClassificationPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}


	By ContractSettingPage = By.xpath("//*[contains(@data-name,'إعدادات النظام')]");
	By ClassificationPage = By.xpath("//a[contains(@onclick,'contract_vehcile_model_search.jsf')]");
	By SwitchFrame = By.id("myIframe");
	By AddClassification = By.id("actions1:addButtonId");
	By AddCode = By.id("code:code");
	By DescriptionAR = By.id("arabicDescription:arabicDescription");
	By DescriptionEN = By.id("englishDescription:englishDescription");
	By SelectContractType = By.id("allowedType:allowedTypeselectOneDomainPanelSelectOneMenu");  
	By MinimumVehicles = By.id("minimumVehiclesCount:minimumVehiclesCount");
	By DepreciationFine = By.id("depreciationFine:depreciationFine");
	By DepreciationYears = By.id("depreciationYearsCount:depreciationYearsCount");
	By FineRecurrencePeriod = By.id("fineRecurrencePeriod:fineRecurrencePeriod");
	By SelectdepreciationCalcMethod = By.id("depreciationCalcMethod:depreciationCalcMethodselectOneDomainPanelSelectOneMenu");
	By LimitedVehiclesPercentage = By.id("limitedVehiclesPercentage:limitedVehiclesPercentage");
	By Save = By.xpath("//a[contains(@class,'btn btn-success btn-save btn-sm waves-effect waves-button waves-float OraLink waves-effect waves-button waves-float')]");

	By Search = By.id("j_idt168:searchButtonId");
	By SelectRow = By.id("resultsTable:0:c1");
	By EditRow = By.id("actions1:editButtonId");
	By AddVehicle = By.id("vehcileModelNavBtn");
	By VehicleManfactor = By.id("vehicleManufacturerId");
	By VehicleModel = By.id("vehicleModelId");
	By SeatNO = By.id("noOfSeats:noOfSeats");
	By DoorNO = By.id("noOfDoors:noOfDoors");
	By VehicleStatusActive = By.id("isActive:isActiveselectOneDomainPanelSelectOneMenu");
	By Limted = By.id("limeted:limetedselectOneDomainPanelSelectOneMenu");
	By Notes = By.id("remarks:remarks");
	By SaveVehicle = By.id("j_idt182:saveButtonId");
	
public void addClassificationPage() throws InterruptedException, AWTException {
	clickElement(ContractSettingPage);
	switchToFrame(SwitchFrame);
	Thread.sleep(2000);
	clickElement(ClassificationPage);
	clickElement(AddClassification);
	writeToElement(AddCode,"5241001");
	writeToElement(DescriptionAR,"تصنيف جديد");
	writeToElement(DescriptionEN,"new classification");
	selectFromListByVisibleText(SelectContractType,"عقد سيارات النقل الفاخر");
	writeToElement(MinimumVehicles,"2");
	writeToElement(DepreciationFine,"100");
	writeToElement(DepreciationYears,"2");
	writeToElement(FineRecurrencePeriod,"30");
	selectFromListByVisibleText(SelectdepreciationCalcMethod,"سنة الصنع");
	writeToElement(LimitedVehiclesPercentage,"10");
	Thread.sleep(2000);
	clickElement(Save);
	
}

public void addVehiclesOnClassification() throws InterruptedException, AWTException {

	writeToElement(AddCode,"52525");
	clickElement(Search);
	clickElement(SelectRow);
	clickElement(EditRow);
	clickElement(AddVehicle);
	selectFromListByValue(VehicleManfactor, "10205");
	Thread.sleep(3000);
	selectFromListByValue(VehicleModel, "310000001973");
	writeToElement(SeatNO,"5");
	writeToElement(DoorNO,"4");
	selectFromListByValue(VehicleStatusActive, "2");
	selectFromListByValue(Limted, "1");
	writeToElement(Notes,"Testing");
	clickElement(SaveVehicle);
}

@Override
public boolean isPageLoaded() {
	// TODO Auto-generated method stub
	return false;
}
	
}
