package isoft.etraffic.ctatestcases;

import java.sql.SQLException;

import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import isoft.etraffic.ctapages.GeneralPages;
import isoft.etraffic.ctapages.NewTLNOCPage;
import isoft.etraffic.ctapages.PaymentCreaditCardforRenew;
import isoft.etraffic.ctapages.RenewTLPage;
import isoft.etraffic.ctapages.ReviewTLPage;
import isoft.etraffic.ctapages.UpdateTLpage;
import isoft.etraffic.db.GetNo;
import isoft.etraffic.db.GetTFAndTL;
import isoft.etraffic.db.NewNocApprovals;
import isoft.etraffic.testbase.TestBase;

public class RenewTL extends TestBase {

	@Description("RenewTrade License cycle for each activity")
	@Step("This is Renew trade license for activity {0}, and with obligation flag {1}")
	@Test
	public void renewTLCML() throws InterruptedException, ClassNotFoundException, SQLException {

		GeneralPages GP = new GeneralPages(driver);
		GetTFAndTL getTFForRenew = new GetTFAndTL();
		RenewTLPage renewObject = new RenewTLPage(driver);

		getTFForRenew.getTFandTLForRenew();

		String Traffic_file = getTFForRenew.TRF;
		String Trade_license = getTFForRenew.TL;

		renewObject.RenewTLInitiate(Traffic_file, Trade_license);
		renewObject.viewandacceptObligation();
		renewObject.confirmtodelivery();

		renewObject.confirmandproceed();
		PaymentCreaditCardforRenew payment = new PaymentCreaditCardforRenew(driver);
		payment.paymentcreaditcardforrenew(driver);
		Thread.sleep(30000);
		GP.closehappinies();

		Thread.sleep(3000);
		// get certification No
		NewTLNOCPage NOCpage = new NewTLNOCPage(driver);
		String TrxID = renewObject.TraxIDForRenew();
		String AppNo = NOCpage.AppNo();
		GetNo getorderno = new GetNo();
		String certificatenumber = getorderno.getcertificateno(AppNo);

		System.out.println("Certificate No: " + certificatenumber);
		System.out.println("Transaction ID : " + TrxID);
		System.out.println("Application No : " + AppNo);

		// Update Trade License
		driver.get("https://tst12c:7793/trfesrv/public_resources/public-access.do");
		UpdateTLpage updateTLobject = new UpdateTLpage(driver);
		updateTLobject.searchForComp(certificatenumber, AppNo);
		updateTLobject.updateTLforRenewTradeLicense();
		updateTLobject.submitTRX();

		// Review After Update
		// Approve EPS
		NewNocApprovals approve = new NewNocApprovals();
		approve.EPSapproval(TrxID);
		approve.TRXupdateStatus(AppNo, "3");
		
		String UpdateTrxID = NOCpage.UpdateTraxID();
		System.out.println("New Transaction Number: " + UpdateTrxID);
		approve.EPSapproval(UpdateTrxID);
		driver.get("https://tst12c:7793/trfesrv/public_resources/public-access.do");
		ReviewTLPage ReviewObject = new ReviewTLPage(driver);
		ReviewObject.ReviewTL(UpdateTrxID, AppNo);
		ReviewObject.SubmitFees();

	}

}
