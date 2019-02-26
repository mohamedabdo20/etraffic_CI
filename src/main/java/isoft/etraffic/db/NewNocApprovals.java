package isoft.etraffic.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.annotations.Test;

import io.qameta.allure.Step;

public class NewNocApprovals {
	 
	@Step("Approve security requests")
	@Test
	public void securityapproval(String AppNo) throws ClassNotFoundException, SQLException, InterruptedException {
		
		Connection con = dbConn.dbconntion();
		System.out.println("connected successfully");

		Statement stmt = con.createStatement(); 
		// step4 execute query
		int result = stmt.executeUpdate("UPDATE TRAFFIC.TF_CML_SECURITY_PERMISSIONS CSP2 SET STATUS = 2, "
				+ "UPDATE_DATE = SYSDATE, UPDATED_BY = 'AUTOMATION' "
				+ "WHERE CSP2.CAP_id = (SELECT CAP.ID FROM  TRAFFIC.TF_CML_APPLICATIONS CAP WHERE CAP.APP_REF_NO = " + AppNo
				+ ")");

		System.out.println("Security Approvals Updated rows : " + result);
		System.out.println(("Security Approvals Updated"));

		stmt.close();
		con.close();
	}
	
	@Step("Approve EPS")
	@Test
	public void EPSapproval(String TRSID) throws ClassNotFoundException, SQLException, InterruptedException {

	
		Connection con = dbConn.dbconntion();

		Statement epsStmt = con.createStatement();
		// step4 execute query

		int result = epsStmt
				.executeUpdate("UPDATE TRAFFIC.TF_EPS_PROCEDURES EPS SET STATUS = 2, UPDATE_DATE = SYSDATE, "
						+ "UPDATED_BY = 'AUTOMATION' WHERE EPS.TRS_ID =  " + TRSID);

		System.out.println("EPS Updated rows : " + result);
		System.out.println(("EPS Updated"));

		epsStmt.close();
		// closing DB Connection
		con.close();
		Thread.sleep(2000);
	}

	@Step("Update transaction status after approve EPS and security approvals ")
	@Test
	public void TRXupdateStatus(String AppNo ,String status )
			throws ClassNotFoundException, SQLException, InterruptedException {

		
		Connection con = dbConn.dbconntion();

		Statement trxStmt = con.createStatement();
		// step4 execute query

		int result = trxStmt.executeUpdate("UPDATE TRAFFIC.TF_CML_APPLICATIONS CAP SET STATUS = " + status
				+ ", UPDATE_DATE = SYSDATE, " + "UPDATED_BY = 'AUTOMATION' WHERE CAP.APP_REF_NO =  " + AppNo);

		System.out.println("Transaction Status Updated rows : " + result);
		System.out.println(("Transaction Status Updated"));

		trxStmt.close();
		// closing DB Connection
		con.close();
		Thread.sleep(2000); 
	}

}
