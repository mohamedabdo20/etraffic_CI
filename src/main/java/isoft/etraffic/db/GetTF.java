package isoft.etraffic.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.annotations.Test;

public class GetTF {

	public String TRF;
	public String Plateno;

	int count = 1;
	Connection con = dbConn.dbconntion();
	//String TL = "60068";

	public void GetTFfornewsub() throws SQLException, InterruptedException {

		Statement SQLStatement = con.createStatement();
		// step4 execute query
		ResultSet rs = SQLStatement.executeQuery("SELECT TRF.TRAFFIC_NO, PLT.PLATE_NO\r\n"
				+ "  FROM TF_VHL_BOOKLETS BKT,\r\n" + "       TF_VHL_VEHICLES VLE,\r\n"
				+ "       TF_STP_TRAFFIC_FILES TRF,\r\n" + "       TF_VHL_PLATES PLT\r\n"
				+ " WHERE     BKT.VLE_ID = VLE.ID\r\n" + "       AND BKT.TRF_ID = TRF.ID\r\n"
				+ "       AND BKT.PLT_ID = PLT.ID\r\n" + "       -- ALL\r\n" + "       AND EXISTS\r\n"
				+ "              (SELECT 1\r\n" + "                 FROM TF_STP_TELEMATIC_DEVICES TLD\r\n"
				+ "                WHERE TLD.VLE_ID = VLE.ID)\r\n" + "       -- Not Expired\r\n"
				+ "       --AND EXISTS (SELECT 1 FROM TF_STP_TELEMATIC_DEVICES TLD WHERE TLD.VLE_ID = VLE.ID AND TLD.SUBSCRIPTION_DUE_DATE >= SYSDATE)\r\n"
				+ "\r\n" + "       -- Expired\r\n" + "       AND EXISTS\r\n" + "              (SELECT 1\r\n"
				+ "                 FROM TF_STP_TELEMATIC_DEVICES TLD\r\n"
				+ "                WHERE     TLD.VLE_ID = VLE.ID\r\n"
				+ "                      AND TLD.SUBSCRIPTION_DUE_DATE < SYSDATE)\r\n"
				+ "                      and rownum = 1");

		System.out.println("getting Traffic file and plate number");

		Thread.sleep(10000);

		while (rs.next() && count == 1) {

			TRF = rs.getString(1);
			Plateno = rs.getString(2);

			System.out.println("Traffic File = " + TRF);
			System.out.println("Plateno = " + Plateno);

			count++;
		}

		// System.out.println("Count of NOCS TO BE IGNORED = " + NOCS_TO_BE_IGNORED);
		SQLStatement.close();
		// closing DB Connection
		con.close();
		// Thread.sleep(10000);

	}

	@Test
	public void getnewTF(String TL) throws SQLException, InterruptedException {

		Statement SQLStatement = con.createStatement();
		// step4 execute query
		String Query = "SELECT TRF.TRAFFIC_NO FROM TF_STP_TRAFFIC_FILES TRF,TF_STP_ORGANIZATIONS ORG,TF_STP_ORG_REGISTRATION_INFOS ORI WHERE    TRF.ORG_ID = ORG.ID AND ORG.ID = ORI.ORG_ID AND ORI.TRADE_LICENSE = '"+TL +"'";
		//System.out.println(Query);
		ResultSet rs = SQLStatement.executeQuery(Query);
		System.out.println("Getting Traffic file for new Trade license");
		Thread.sleep(5000);

		while (rs.next()) {
			TRF = rs.getString(1);

		}

		// Close statement
		SQLStatement.close();
		// Close DB Connection
		con.close();

	}
}
