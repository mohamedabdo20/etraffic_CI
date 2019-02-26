package isoft.etraffic.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.annotations.Test;

public class GetOrderNo {
	String trsNo;
	String ordNo;
	String certno;
	
	@Test(priority = 2, enabled = true)

	public void gettheresulCer() throws ClassNotFoundException, SQLException, InterruptedException {

		System.out.print("connect to database");

		// step1 load the driver class

		Class.forName("oracle.jdbc.driver.OracleDriver");
		// step2 create the connection object
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@10.11.206.11:1527:trftest1", "a_nour_stg",
				"123456");

		// step3 create the statement object
		System.out.println("connected successfully");

		Statement stmt = con.createStatement();
		// step4 execute query
		stmt.executeUpdate("UPDATE TF_CML_SECURITY_PERMISSIONS CSP2 SET STATUS = 2, "
				+ "UPDATE_DATE = SYSDATE, UPDATED_BY = 'AUTOMATION' "
				+ "WHERE CSP2.CAP_id = (SELECT CAP.ID FROM  TF_CML_APPLICATIONS CAP WHERE CAP.APP_REF_NO = " + ordNo
				+ ")");

		System.out.println(("first quiry updated"));

		stmt.close();
		Statement stmt2 = con.createStatement();
		// step4 execute query

		stmt2.executeUpdate("UPDATE TF_CML_APPLICATIONS CAP SET STATUS = 3, UPDATE_DATE = SYSDATE, "
				+ "UPDATED_BY = 'AUTOMATION' WHERE CAP.APP_REF_NO =  " + ordNo);

		System.out.println(("second quiry updated"));

		stmt2.close();
		// closing DB Connection
		con.close();
		Thread.sleep(10000);
	}

}
