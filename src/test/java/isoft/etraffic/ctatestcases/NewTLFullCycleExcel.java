package isoft.etraffic.ctatestcases;

import java.io.IOException;
import java.sql.SQLException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import isoft.etraffic.ctapages.AddActivity;
import isoft.etraffic.ctapages.AddPerson;
import isoft.etraffic.ctapages.DeliveryMethod;
import isoft.etraffic.ctapages.GeneralPages;
import isoft.etraffic.ctapages.NewTLNOCPage;
import isoft.etraffic.ctapages.PaymentCreaditCard;
import isoft.etraffic.ctapages.ReviewTLPage;
import isoft.etraffic.ctapages.UpdateTLpage;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.data.ExcelReader;
import isoft.etraffic.db.GetNo;
import isoft.etraffic.db.GetTF;
import isoft.etraffic.db.NewNocApprovals;

@Epic("Master test cases Execution")
@Feature("New trade license full cycle")
public class NewTLFullCycleExcel extends TestBase {

	GeneralPages GP = new GeneralPages(driver);

	@DataProvider(name = "CTA_Activities")
	public Object[][] ActivityData() throws IOException {
		// get data from Excel Reader class
		ExcelReader ER = new ExcelReader();
		ER.TotalNumberOfCols = 2;
		ER.sheetname = "cta_activities";
		return ER.getExcelData();}

	@Description("Make new trade license cycle for each activity")
	@Step("This is new trade license for activity {0}, and with obligation flag {1}")
	@Test(dataProvider = "CTA_Activities")
	public void NewNoc(String Activity, String Obligation)
			throws InterruptedException, ClassNotFoundException, SQLException {
		// Create NOC for New Trade License
		NewTLNOCPage NOCpage = new NewTLNOCPage(driver);
		NOCpage.NewNOCFill("Abdo", "12345679", "22-07-2018");
		AddActivity addactiv = new AddActivity(driver);
		addactiv.addactivity(Activity);
		// Add members
		AddPerson addnewperson = new AddPerson(driver);

		addnewperson.addOwnerOrManager("مالك ", "Owner", "161616116", "01-01-1980", "12365478903215", "owner");
		addnewperson.addMember("شريك ", "Member", "161616116", "01-01-1980", "1236547890321", "20");
		addnewperson.addOwnerOrManager("مدير ", "Manager", "161616116", "01-01-1980", "12365478903214", "manager");
		
		// Submit Transaction
		NOCpage.submitTransaction(Obligation);
		Thread.sleep(5000);
		// GP.closehappinies();
		String TrxID = NOCpage.TraxID();
		String AppNo = NOCpage.AppNo();
		System.out.println("Activity: " + Activity);
		System.out.println("Obligation: " + Obligation);
		System.out.println("Transaction ID: " + TrxID);
		System.out.println("Application No: " + AppNo);

		// Approve EPS
		NewNocApprovals approve = new NewNocApprovals();
		approve.securityapproval(AppNo);
		approve.EPSapproval(TrxID);
		approve.TRXupdateStatus(AppNo, "3");

		Thread.sleep(5000);

		// Review TL
		driver.get("https://tst12c:7793/trfesrv/public_resources/public-access.do");
		ReviewTLPage ReviewObject = new ReviewTLPage(driver);
		ReviewObject.ReviewTL(TrxID, AppNo);

		DeliveryMethod DeliveryObject = new DeliveryMethod(driver);
		DeliveryObject.delivermethod("0501234657", "04065858585", "test.com", "test.com");

		PaymentCreaditCard payment = new PaymentCreaditCard();
		payment.paymentcreaditcard(driver);

		Thread.sleep(50000);

		// get certification No
		GetNo getorderno = new GetNo();
		String certificatenumber = getorderno.getcertificateno(AppNo);

		System.out.println("Certificate No: " + certificatenumber);
		System.out.println("Application No: " + AppNo);

		// Update TL
		driver.get("https://tst12c:7793/trfesrv/public_resources/public-access.do");
		UpdateTLpage UpdateTLobject = new UpdateTLpage(driver);
		UpdateTLobject.searchForComp(certificatenumber, AppNo);
		UpdateTLobject.fillTLData(AppNo, "شركة جديدة", "New Company", "01-01-2018", "01-01-2020", "0506242628",
				"04065958684", "test.com", "15619849", "Company Address");
		UpdateTLobject.submitTRX();

		String UpdateTrxID = NOCpage.UpdateTraxID();
		System.out.println("New Transaction Number: " + UpdateTrxID);
		approve.EPSapproval(UpdateTrxID);
		driver.get("https://tst12c:7793/trfesrv/public_resources/public-access.do");
		ReviewObject.ReviewTL(UpdateTrxID, AppNo);
		ReviewObject.SubmitFees();
		// GP.closehappinies();

		System.out.println("New Trade license created successfuly");
		Thread.sleep(2000);
		GetTF getTFObject = new GetTF();
		getTFObject.getnewTF(AppNo);
		String TF = getTFObject.TRF;
		System.out.println("New Traffic No: " + TF);
		System.out.println("New Trade license No: " + AppNo);}

}
