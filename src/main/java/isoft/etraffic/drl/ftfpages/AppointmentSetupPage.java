package isoft.etraffic.drl.ftfpages;

import java.awt.AWTException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import isoft.etraffic.wrapper.SeleniumWraper;

public class AppointmentSetupPage extends SeleniumWraper {

	// Search by Plate
	By appointmentsetupsearchScreenBtn = By.id("appointment_setup_search-btn-id");
	By vehicleClassLst = By.id("vchId:vchIdSelectOneMenu");
	By searchBtn = By.partialLinkText("بحث");
	By editBtn = By.xpath("//*[@title='تعديل']");
	By saveBtn = By.partialLinkText("حفظ");
	By settingsDetailsScreenBtn = By.id("appointment_setup_details-btn-id");
	By centersLst = By.id("centersDialogField:centersDialogFieldSelectOneMenu");
	By startTimeTxt = By.id("startTimeHourId:startTimeHourIdInputTextCalender");

	public void openScreen() throws InterruptedException, AWTException {
		waitForElement(appointmentsetupsearchScreenBtn);
		clickElementJS(appointmentsetupsearchScreenBtn);
	}

	public void searchByVehicleClass(String vehicleClass) throws InterruptedException
	{
		waitForElement(vehicleClassLst);
		selectFromListByVisibleText(vehicleClassLst, vehicleClass);
		
		clickElementJS(searchBtn);
		Thread.sleep(1000);
	}
	
	public void selectTestType(String testType) throws InterruptedException 
	{
//		waitForElement(By.xpath("//*[@title='"+testType+"']"));
		tryClickElement(By.xpath("//*[@title='"+testType+"']"));
	}
	
	public void clickEditBtn() throws InterruptedException 
	{
		waitForElement(editBtn);
		clickElementJS(editBtn);
	}
	
	public void clickSaveBtn() throws InterruptedException {
		waitForElement(saveBtn);
		clickElementJS(saveBtn);
	}
	
	public void gotoSettingsDetails() throws InterruptedException {
		waitForElement(settingsDetailsScreenBtn);
		clickElementJS(settingsDetailsScreenBtn);
	}
	
	public void searchByCenter(String center) throws InterruptedException
	{
		waitForElement(centersLst);
		selectFromListByVisibleText(centersLst, center);
		
		clickElementJS(searchBtn);
		Thread.sleep(1000);
	}
	
	public void modifyStartTime(String time) throws InterruptedException
	{
		waitForElement(startTimeTxt);
		clickElementJS(startTimeTxt);
		writeToElement(startTimeTxt, time);
		Thread.sleep(500);
		clickElementJS(saveBtn);
	}
	
	public AppointmentSetupPage(WebDriver driver) {
		super(driver);
	}
	
	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

}