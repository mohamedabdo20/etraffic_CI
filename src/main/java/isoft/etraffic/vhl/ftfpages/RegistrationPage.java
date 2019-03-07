package isoft.etraffic.vhl.ftfpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import isoft.etraffic.wrapper.SeleniumWraper;

public class RegistrationPage extends SeleniumWraper {

	public RegistrationPage(WebDriver driver) {
		super(driver);
	}

	By sourceEmirateLst = By.id("sourceEmirate-selectized");
	By sourceIdTxt = By.id("vehicleSourceNoId");
	By bookletSourceLst = By.id("bookletSource-selectized");
	By sourceDateTxt = By.id("sourceDate");
	By vehicleClassLst = By.id("vehicleClassId-selectized");
	By vehicleDescriptionLst = By.id("vehicleDescriptionId-selectized");
	By plateCategoryLst = By.id("plateCategoryId-selectized");
	By chassisValidationExceptionBtn = By.id("chassisValidationException");
	By proceedTrsBtn = By.id("proceedTrsId");

	By vehicleManufacturerLst = By.id("vehicleManufacturerId-selectized");
	By vehicleModelLst = By.id("vehicleModelId-selectized");
	By modelYearTxt = By.id("modelYearId-selectized");
	By madeInCountryLst = By.id("madeInCountryId-selectized");
	By engineNoTxt = By.id("engineNoId");
	By vehicleSourceLst = By.id("vehicleSourceId-selectized");
	By fuelSourceLst = By.id("fuelSourceId-selectized");
	By fuelSubTypeLst = By.id("fuelId-selectized");
	By vehicleWeightTxt = By.id("vehicleWeightId");
	By carryWeightsTxt = By.id("carryWeightsId");
	By axesNoTxt = By.id("axesNoId");
	By noOfSeatsTxt = By.id("noOfSeatsId");
	By noOfDoorsTxt = By.id("noOfDoorsId");
	By color1Lst = By.id("color1Id-selectized");
	By certifyLnk = By.id("certifyTrsBtn");

	// Plate Elements
	By openSelectPlateBtn = By.id("openSelectPlateBtn");
	By frontPlateSizeLst = By.id("frontPlateSizeId-selectized");
	By backPlateSizeLst = By.id("backPlateSizeId-selectized");
	By plateSourceLst = By.id("plate-source-selectized");
	By selectPlateBtn = By.id("btnChangePlateSubmit");

	// Chassis Validation
	By chassisValidationBtn = By.id("chassisValidation");
	By manufacturerLst = By.id("selectedManufacturerId");
	By yearLst = By.id("selectedYearValue");
	By confirmManfactorBtn = By.id("confirmManfactorButton");
	By validateChassisBtn = By.id("validateChassisButton");
	By closeBtn = By.xpath("//*[@id='accordion']/p/button");
	
	public void setChassis(String chassis) throws InterruptedException {
		String id = "ch1";
		waitForElement(By.id(id));
		for (int i = 0; i < chassis.length(); i++) {
			id = "ch" + Integer.toString(i + 1);
			String value = Character.toString(chassis.charAt(i));
			try {
				writeToElement(By.id(id), value);
			} catch (Exception e) {
				Thread.sleep(3000);
				writeToElement(By.id(id), value);
			}
		}
	}

	public void selectPlates(String plateSource, String frontPlate, String backPlate) throws InterruptedException {
		clickElement(openSelectPlateBtn);
		Thread.sleep(1000);
		String parentHandle = driver.getWindowHandle();
		switchToFrame("plateSelectionIframeId");

		selectFromFTFList(frontPlateSizeLst, frontPlate);
		selectFromFTFList(backPlateSizeLst, backPlate);
		selectFromFTFList(plateSourceLst, plateSource);
		clickElementJS(selectPlateBtn);

		Thread.sleep(1000);
		driver.switchTo().window(parentHandle);

		clickElement(certifyLnk);
	}

	public void setVehicleSourceDetails(String sourceEmirate, String bookletSource, String sourceId, String sourceDate,
			String vehicleClass, String vehicleDescription, String plateCategory, String chassis)
			throws InterruptedException {
		waitForElement(sourceEmirateLst);
		selectFromFTFList(sourceEmirateLst, sourceEmirate);
		Thread.sleep(1000);
		selectFromFTFList(bookletSourceLst, bookletSource);
		selectFromFTFList(sourceIdTxt, sourceId);
		if (bookletSource.equals("شهادة جمركية")) {
			writeToElement(sourceDateTxt, sourceDate);
			selectFromFTFList(vehicleClassLst, vehicleClass);
			selectFromFTFList(vehicleDescriptionLst, vehicleDescription);
			setChassis(chassis);
		}
		waitForElement(plateCategoryLst);
		selectFromFTFList(plateCategoryLst, plateCategory);
	}

	public void clickProceedBtn() throws InterruptedException
	{
		Thread.sleep(2000);
		clickElementJS(proceedTrsBtn);
		Thread.sleep(1000);
	}
	
	public void clickChassisValidationBtn() throws InterruptedException
	{
		Thread.sleep(2000);
		clickElementJS(chassisValidationBtn);
		Thread.sleep(1000);
	}
	
	public void clickChassisExceptionBtn() throws InterruptedException
	{
		clickElement(chassisValidationExceptionBtn);
	}
	
	public void setVehicleDetails(String chassis, String vehicleManufacturer, String vehicleModel, String madeInCountry,
			String modelYear, String engineNo, String vehicleSource, String fuelSource, String fuelSubType,
			String vehicleWeight, String noOfDoors, String noOfSeats, String color1, String vehicleClass)
			throws InterruptedException {
		waitForElement(madeInCountryLst);

		if (vehicleClass.equals("مقطورة")) {
			selectFromFTFList(madeInCountryLst, madeInCountry);
			selectFromFTFList(vehicleSourceLst, vehicleSource);
			writeToElement(vehicleWeightTxt, vehicleWeight);
			// writeToElement(carryWeightsTxt, noOfSeats);
			// writeToElement(axesNoTxt, noOfDoors);
			Thread.sleep(500);
		} else {
			setChassis(chassis);
			Thread.sleep(1000);
//			selectFromFTFList(vehicleManufacturerLst, vehicleManufacturer);
//			Thread.sleep(500);
//			selectFirstValue(vehicleModelLst);
			selectFromFTFList(madeInCountryLst, madeInCountry);
			selectFromFTFList(modelYearTxt, modelYear);
			writeToElement(engineNoTxt, engineNo);
			selectFromFTFList(vehicleSourceLst, vehicleSource);
			selectFromFTFList(fuelSourceLst, fuelSource);
			Thread.sleep(500);
			selectFromFTFList(fuelSubTypeLst, fuelSubType);

			for (int i = 0; i < 3; i++)
				hitTabToElement(vehicleWeightTxt);
			writeToElement(vehicleWeightTxt, vehicleWeight);
			writeToElement(noOfSeatsTxt, noOfSeats);
			writeToElement(noOfDoorsTxt, noOfDoors);
		}
		selectFromFTFList(color1Lst, color1);
	}

	public void setCertificateVehicleDetails(String vehicleSource) throws InterruptedException {
		waitForElement(engineNoTxt);
		Thread.sleep(1000);
		hitTabToElement(engineNoTxt);
		selectFromFTFList(vehicleSourceLst, vehicleSource);

	}
	
	public void validateChassis(String chassis) throws InterruptedException
	{
		String firstWindow = switchToSecondWindow();
		waitForElement(manufacturerLst);
		selectFromListByVisibleText(manufacturerLst, "جي ام سي");
		selectFromListByVisibleText(yearLst, "2018");
		clickElementJS(confirmManfactorBtn);
		
		waitForElement(By.xpath("//*[@id='chassisInfoBody']/div/table/tbody/tr[2]/td[1]/input"));
		writeToElement(By.xpath("//*[@id='chassisInfoBody']/div/table/tbody/tr[2]/td[1]/input"), chassis.substring(0, 3));
		hitEnterToElement(By.xpath("//*[@id='chassisInfoBody']/div/table/tbody/tr[2]/td[1]/input"));
		hitTabToElement(By.xpath("//*[@id='chassisInfoBody']/div/table/tbody/tr[2]/td[1]/input"));
	
		writeToElement(By.xpath("//*[@id='chassisInfoBody']/div/table/tbody/tr[2]/td[3]/input"), chassis.substring(3, 5));
		hitEnterToElement(By.xpath("//*[@id='chassisInfoBody']/div/table/tbody/tr[2]/td[3]/input"));
		hitTabToElement(By.xpath("//*[@id='chassisInfoBody']/div/table/tbody/tr[2]/td[3]/input"));

		writeToElement(By.xpath("//*[@id='chassisInfoBody']/div/table/tbody/tr[2]/td[5]/input"), chassis.substring(8, 11));
		hitEnterToElement(By.xpath("//*[@id='chassisInfoBody']/div/table/tbody/tr[2]/td[5]/input"));
		hitTabToElement(By.xpath("//*[@id='chassisInfoBody']/div/table/tbody/tr[2]/td[5]/input"));
		
		writeToElement(By.xpath("//*[@id='chassisInfoBody']/div/table/tbody/tr[2]/td[7]/input"), chassis.substring(11, 14));
		hitEnterToElement(By.xpath("//*[@id='chassisInfoBody']/div/table/tbody/tr[2]/td[7]/input"));
		hitTabToElement(By.xpath("//*[@id='chassisInfoBody']/div/table/tbody/tr[2]/td[7]/input"));

		writeToElement(By.xpath("//*[@id='chassisInfoBody']/div/table/tbody/tr[2]/td[9]/input"), chassis.substring(14, chassis.length()));
		hitEnterToElement(By.xpath("//*[@id='chassisInfoBody']/div/table/tbody/tr[2]/td[9]/input"));
		hitTabToElement(By.xpath("//*[@id='chassisInfoBody']/div/table/tbody/tr[2]/td[9]/input"));
		
		clickElementJS(validateChassisBtn);
		
		waitForElement(By.xpath("//*[@id='plateInfoBody']/div/table/tbody/tr/td[2]"));
		System.out.println("Chassis Validated By Manufacturer --> "+ getElementAttributeValue(By.xpath("//*[@id='plateInfoBody']/div/table/tbody/tr/td[2]"), "value"));
	
		clickElementJS(closeBtn);
		
		switchToWindow(firstWindow);
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}