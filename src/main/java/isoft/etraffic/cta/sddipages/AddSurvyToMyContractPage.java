package isoft.etraffic.cta.sddipages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import io.qameta.allure.Step;
import isoft.etraffic.wrapper.SeleniumWraper;

public class AddSurvyToMyContractPage extends SeleniumWraper {

	private By SelectFace = By.id("vsatisfied");    
	private By AddComment = By.id("feedbackCommentOfSurvey");    
	private By Submit = By.id("submitSurveyBtn");  
	
	
	@Step("Survey on My Dashboard")
	public void addsurvytomycontractpage() throws InterruptedException {
		clickElement(SelectFace);
		writeToElement(AddComment, "Good");
		clickElement(Submit);
	}
	
	

	public AddSurvyToMyContractPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}


	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}
}
