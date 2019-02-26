package isoft.etraffic.ctapages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.qameta.allure.Step;
import isoft.etraffic.wrapper.SeleniumWraper;
import isoft.etraffic_CI.enums.MemberTypes;

public class AddPerson extends SeleniumWraper {
 
	WebDriverWait wait = new WebDriverWait(driver, 15, 100);
	private By addMemberbtn = By.xpath("//*[contains(@class,'addMemberLink')]");
	private By ArabicNametxt = By.id("memberArabicNameId");
	private By EnglishNametxt = By.id("memberEnglishNameId"); 
	private By occupationdrp = By.id("select2-memberOccupationId-container");
	private By occupationSelect = By.xpath("/html/body/span/span/span[2]/ul/li[2]"); 
	private By MobileCodeDrp = By.id("select2-memberMobileCodeId-container");
	private By MobileCodeSelect = By.xpath("/html/body/span/span/span[2]/ul/li[2]");
	private By mobileNotxt = By.id("memberMobileNoId");
	private By GenderDrp = By.id("select2-memberGenderId-container");
	private By GenderSelect = By.xpath("/html/body/span/span/span[2]/ul/li[3]");
	private By birthDateIdtxt = By.id("memberBirthDateId");
	private By countryDrp = By.id("select2-memberCountryId-container");
	private By countrySelect = By.xpath("/html/body/span/span/span[2]/ul/li[2]");
	private By emirateIdtxt = By.id("memberEmirateId");
	private By memberTypeDrp = By.id("select2-memberTypeId-container");
	private By membertypeInput = By.xpath("//*[@class='select2-search__field']");
	private By memberTypeManagerSelect = By.xpath("/html/body/span/span/span[2]/ul/li[18]");
	private By memberTypeMemberSelect = By.xpath("/html/body/span/span/span[2]/ul/li[3]");
	private By memberTypeOwnerSelect = By.xpath("/html/body/span/span/span[2]/ul/li[2]");
	private By membersharetxt = By.id("memberCapitalShareId");
	private By emiratesIDattach = By.id("membermEmiratesIdCopyId");
	private By saveMemberbtn = By.id("btnCompanyProfileSubmit");
	MemberTypes membertypes;
	
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
		
		switch (membertypes) {
		case manager:
		
			waitForElement(membertypeInput);
			selectFromFTFList(membertypeInput, "manager");
			/*wait.until(ExpectedConditions.elementToBeClickable(memberTypeManagerSelect));
			clickElement(memberTypeManagerSelect);*/
			break;
		case owner:
			wait.until(ExpectedConditions.elementToBeClickable(memberTypeOwnerSelect));
			clickElement(memberTypeOwnerSelect);
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
		clickElement(memberTypeDrp);
		wait.until(ExpectedConditions.elementToBeClickable(memberTypeMemberSelect));
		clickElement(memberTypeMemberSelect);
		Thread.sleep(2000);
		writeToElement(membersharetxt, memberShare);
		FileInputElement(emiratesIDattach, System.getProperty("user.dir")+"\\attachments\\Lighthouse.jpg");
		clickElement(saveMemberbtn);}

	public AddPerson(WebDriver driver) { 
		super(driver);
		}

	@Override
	public boolean isPageLoaded() {
		return false;}

}
