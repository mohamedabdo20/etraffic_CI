package isoft.etraffic.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GetNo {

	public String certno;

	Connection con = dbConn.dbconntion();

	public String getcertificateno(String ordNo) throws ClassNotFoundException, SQLException, InterruptedException {
		// Connection con = dbConn.dbconntion();
		Statement stmt3 = con.createStatement();
		Thread.sleep(10000);

		ResultSet rs = stmt3
				.executeQuery("select LTR_REF_NO from TF_CML_APPLICATIONS where status = 5 and APP_REF_NO =" + ordNo);

		System.out.println("Query Selected");

		while (rs.next()) {
			certno = rs.getString(1);

		}
		System.out.println("Certificate NO from Get method: " + certno);

		stmt3.close();
		// closing DB Connection
		con.close();

		return certno;
	}

}
