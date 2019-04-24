package isoft.etraffic.cta.sddipages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.qameta.allure.Step;
import isoft.etraffic.wrapper.SeleniumWraper;

public class AddPersonModifyPage extends SeleniumWraper {

	WebDriverWait wait = new WebDriverWait(driver, 15, 100);
	private By addMemberbtn = By.xpath("//*[contains(@class,'addLink  addMemberLink')]");
	private By ArabicNametxt = By.id("arabicNameId");
	private By EnglishNametxt = By.id("englishNameId"); 
	private By occupationdrp = By.id("select2-occupationStyleId-container");
	private By occupationInput = By.xpath("//*[@class='select2-search__field']");
	private By occupationSelect = By.xpath("/html/body/span/span/span[2]/ul/li[2]"); 
	private By MobileCodeDrp = By.id("select2-mobileCodeId-container");
	private By MobileCodeSelect = By.xpath("/html/body/span/span/span[2]/ul/li[2]");
	private By mobileNotxt = By.id("mobileNoId");
	private By GenderDrp = By.id("select2-genderId-container");
	private By GenderSelect = By.xpath("/html/body/span/span/span[2]/ul/li[3]");
	private By birthDateIdtxt = By.id("birthDateId");
	private By countryDrp = By.id("select2-countryStyleId-container");
	private By countrySelect = By.xpath("/html/body/span/span/span[2]/ul/li[2]");
	private By emirateIdtxt = By.id("emirateIdStyleId");
	private By memberTypeDrp = By.id("select2-memberTypeId-container");
	private By memberTypeInput = By.xpath("//input[@class='select2-search__field']");
	private By membersharetxt = By.id("capitalShareId");
	private By emiratesIDattach = By.name("emiratesIdCopy");
	private By saveMemberbtn = By.id("btnCompanyProfileSubmit");

	@Step("Add new person")
	public void addOwnerOrManager(String ArabicName, String EnglishName, String MobileNo, String birthDate,
			String emiratesID, String memberType) throws InterruptedException {

		clickElement(addMemberbtn);
		Thread.sleep(2000);
		writeToElement(ArabicNametxt, ArabicName);
		writeToElement(EnglishNametxt, EnglishName);
		clickElement(occupationdrp);
		wait.until(ExpectedConditions.elementToBeClickable(occupationSelect));
		clickElement(occupationSelect);
	/*	clickElement(occupationdrp);
		selectFromListByValue(occupationdrp, "7");*/
		clickElement(MobileCodeDrp);
		wait.until(ExpectedConditions.elementToBeClickable(MobileCodeSelect));
		clickElement(MobileCodeSelect);
		writeToElement(mobileNotxt, MobileNo);
		clickElement(GenderDrp);
		wait.until(ExpectedConditions.elementToBeClickable(GenderSelect));
		clickElement(GenderSelect);
		writeToElement(birthDateIdtxt, birthDate);
		clickElement(countryDrp);
		wait.until(ExpectedConditions.elementToBeClickable(countrySelect));
		clickElement(countrySelect);
		writeToElement(emirateIdtxt, emiratesID);
		clickElement(memberTypeDrp); 
		
		switch (memberType) {
		case "manager":
		
			waitForElement(memberTypeDrp);
			selectFromListByValue(memberTypeDrp, "21");
			
			break;
		case "owner":
			waitForElement(memberTypeDrp);
			selectFromListByValue(memberTypeDrp, "1");
			break;}

		Thread.sleep(2000);
		FileInputElement(emiratesIDattach, System.getProperty("user.dir")+"\\attachments\\Lighthouse.jpg");

		clickElement(saveMemberbtn);}

	@Step("Add New member")
	public void addMember(String ArabicName, String EnglishName, String MobileNo, String birthDate, String emiratesID,
			String memberShare) throws InterruptedException {

		clickElement(addMemberbtn);
		Thread.sleep(2000);
		writeToElement(ArabicNametxt, ArabicName);
		writeToElement(EnglishNametxt, EnglishName);
		clickElement(occupationdrp);
		wait.until(ExpectedConditions.elementToBeClickable(occupationSelect));
		clickElement(occupationSelect);
		clickElement(MobileCodeDrp);
		wait.until(ExpectedConditions.elementToBeClickable(MobileCodeSelect));
		clickElement(MobileCodeSelect);
		
		writeToElement(mobileNotxt, MobileNo);
		clickElement(GenderDrp);
		wait.until(ExpectedConditions.elementToBeClickable(GenderSelect));
		clickElement(GenderSelect);
		writeToElement(birthDateIdtxt, birthDate);
		clickElement(countryDrp);
		wait.until(ExpectedConditions.elementToBeClickable(countrySelect));
		clickElement(countrySelect);
		writeToElement(emirateIdtxt, emiratesID);
		waitForElement(memberTypeDrp);
		clickElement(memberTypeDrp);
		selectFromFTFList(memberTypeInput, "Partner");
		
		writeToElement(membersharetxt, memberShare);
		FileInputElement(emiratesIDattach, System.getProperty("user.dir")+"\\attachments\\Lighthouse.jpg");
		clickElement(saveMemberbtn);}

	public AddPersonModifyPage(WebDriver driver) { 
		super(driver);
		}

	@Override
	public boolean isPageLoaded() {
		return false;}

}
