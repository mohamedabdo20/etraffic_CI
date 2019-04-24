package isoft.etraffic.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import io.qameta.allure.Step;
import isoft.etraffic.enums.Database;

public class CtaDBQueries {

	public String TRF;
	public String TL;
	public String certno;
	public String Plateno;
	int count = 1;
	Connection con;
	String Username, password;
	public static Database database = Database.Test1;

	private void setConnection() throws ClassNotFoundException, SQLException {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
			System.out.println("JDBC class not found");
		}

		// step2 create the connection object
		try {
			con = DriverManager.getConnection(getDatabaseConnection(), Username, password);
			System.out.println("connected to DB Success");
		} catch (SQLException e) {

			e.printStackTrace();
			System.out.println("Error to connect to 0DB");
		}
		
		
	}

	private String getDatabaseConnection() {
		String connection = "";
		switch (database) {
		case Test1:
			connection = "jdbc:oracle:thin:@10.11.206.11:1527:trftest1";
			Username = "A_ELKADY_STG"; // "a_nour_stg";//
			password = "Arcgis.isoft";//
			break;
		case Test2:
			connection = "jdbc:oracle:thin:@10.11.206.10:1527:trftest2";
			Username = "A_ELKADY_STG"; // "a_nour_stg";//
			password = "Arcgis.isoft";//
			break;
		case Automation:
			connection = "jdbc:oracle:thin:@172.18.125.38:1521:trfstg";
			break;
		case ERDB:
			connection = "jdbc:oracle:thin:@10.11.206.11:1527:erdb";
			Username = "ELKADY_ER"; // "a_nour_stg";//
			password = "ELKADY_ER";//
			break;
		default:
			connection = "jdbc:oracle:thin:@172.18.125.38:1521:trfstg";
			break;
		}
		return connection;
	}

	@Step("Get traffic file number and trade license number for service")
	public void getGeneralTFandTL() throws SQLException, InterruptedException, ClassNotFoundException {
		setConnection();
		Statement SQLStatement = con.createStatement();
		// step4 execute query
		ResultSet rs = SQLStatement.executeQuery(
				"SELECT TRF.TRAFFIC_NO , ORI.TRADE_LICENSE FROM TF_STP_TRAFFIC_FILES TRF,TF_STP_ORGANIZATIONS ORG,TF_STP_ORG_REGISTRATION_INFOS ORI WHERE TRF.ORG_ID = ORG.ID AND ORG.ID = ORI.ORG_ID AND NOT EXISTS (SELECT * FROM TF_CML_APPLICATIONS CAP WHERE CAP.TRF_ID = TRF.ID AND TRS_END_DATE IS NULL AND STATUS IN (1,2,3,5))And exists ( select * from TRAFFIC.TF_STP_ORG_CAT_DETAILS det where DET.ORG_ID=org.id and DET.OCT_ID in (select CAT.ID from TRAFFIC.TF_STP_ORG_CATEGORIES cat where CAT.LICENSE_TYPE=2))  and rownum <9 order by TRF.ORG_ID ASC ");

		System.out.println("getting Traffic file and Trade License");

		Thread.sleep(10000);

		while (rs.next() && count == 1) {

			TRF = rs.getString(1);
			TL = rs.getString(2);
			System.out.println("Traffic File = " + TRF);
			System.out.println("Trade License = " + TL);
			count++;
		}

		// System.out.println("Count of NOCS TO BE IGNORED = " + NOCS_TO_BE_IGNORED);
		SQLStatement.close();
		// closing DB Connection
		con.close();
		// Thread.sleep(10000);

	}

	@Step("Get traffic file number and trade license number for modify service")
	public void getTFandTLForModify(String Cat_Id) throws SQLException, InterruptedException, ClassNotFoundException {
		setConnection();
		Statement SQLStatement = con.createStatement();
		ResultSet rs = SQLStatement.executeQuery(
				"SELECT TRF.TRAFFIC_NO, ORI.TRADE_LICENSE   FROM TF_STP_TRAFFIC_FILES TRF, TF_STP_ORGANIZATIONS ORG, TF_STP_ORG_REGISTRATION_INFOS ORI\r\n" + 
				"WHERE ORI.TL_EXPIRY_DATE > (SYSDATE) AND TRF.ORG_ID = ORG.ID AND ORG.ID = ORI.ORG_ID\r\n" + 
				"AND (org.data_source <> 2 OR org.data_source IS NULL)\r\n" + 
				"AND NOT EXISTS (SELECT * FROM TF_CML_APPLICATIONS CAP   WHERE CAP.TRF_ID = TRF.ID AND TRS_END_DATE IS NULL AND STATUS IN (1,  2,  3,  5))\r\n" + 
				"AND EXISTS (SELECT * FROM TRAFFIC.TF_STP_ORG_CAT_DETAILS det   WHERE DET.ORG_ID = org.id AND DET.OCT_ID ="+ Cat_Id +
				"AND DET.OCT_ID IN   (SELECT CAT.ID  FROM TRAFFIC.TF_STP_ORG_CATEGORIES   cat WHERE CAT.LICENSE_TYPE = 2))ORDER BY TRF.ID DEsc");
		System.out.println("get Traffic file and Trade License");
		Thread.sleep(10000);

		while (rs.next() && count == 1) {
			TRF = rs.getString(1);
			TL = rs.getString(2);
			System.out.println("Traffic File = " + TRF);
			System.out.println("Trade License = " + TL);
			count++;
		}

		// System.out.println("Count of NOCS TO BE IGNORED = " + NOCS_TO_BE_IGNORED);

		SQLStatement.close();
		// closing DB Connection
		con.close();
		// Thread.sleep(10000);

	}

	@Step("Get traffic file number and trade license number for modify service")
	public void getTFandTLForModifyaddactivity() throws SQLException, InterruptedException, ClassNotFoundException {
		setConnection();
		Statement SQLStatement = con.createStatement();
		ResultSet rs = SQLStatement.executeQuery("SELECT TRF.TRAFFIC_NO, ORI.TRADE_LICENSE\r\n"
				+ "  FROM TF_STP_TRAFFIC_FILES TRF,TF_STP_ORGANIZATIONS ORG,TF_STP_ORG_REGISTRATION_INFOS ORI\r\n"
				+ "  WHERE ORI.TL_EXPIRY_DATE > (SYSDATE) AND TRF.ORG_ID = ORG.ID AND ORG.ID = ORI.ORG_ID AND (org.data_source <> 2 OR org.data_source IS NULL) \r\n"
				+ "  AND NOT EXISTS(SELECT * FROM TF_CML_APPLICATIONS CAP WHERE     CAP.TRF_ID = TRF.ID AND TRS_END_DATE IS NULL AND STATUS IN (1,2,3,5))\r\n"
				+ "  AND EXISTS(SELECT * FROM TRAFFIC.TF_STP_ORG_CAT_DETAILS det WHERE DET.ORG_ID = org.id AND DET.OCT_ID = '10081' AND DET.OCT_ID IN(SELECT CAT.ID FROM TRAFFIC.TF_STP_ORG_CATEGORIES cat WHERE CAT.LICENSE_TYPE = 2))\r\n"
				+ "  AND ROWNUM <= 10\r\n" + "  ORDER BY TRF.ID DESC");
		System.out.println("get Traffic file and Trade License");
		Thread.sleep(10000);

		while (rs.next() && count == 1) {
			TRF = rs.getString(1);
			TL = rs.getString(2);
			System.out.println("Traffic File = " + TRF);
			System.out.println("Trade License = " + TL);
			count++;
		}

		SQLStatement.close();
		// closing DB Connection
		con.close();
		// Thread.sleep(10000);

	}

	@Step("Get traffic file number and trade license number for renew service")
	public void getTFandTLForRenew() throws ClassNotFoundException, SQLException, InterruptedException {
		setConnection();
		Statement SQLStatement = con.createStatement();
		// step4 execute query
		ResultSet rs = SQLStatement.executeQuery(
				"SELECT TRF.TRAFFIC_NO, ORI.TRADE_LICENSE , ORI.TL_EXPIRY_DATE , ORG.ID FROM TF_STP_TRAFFIC_FILES TRF, TF_STP_ORGANIZATIONS ORG, TF_STP_ORG_REGISTRATION_INFOS ORI  WHERE ORI.TL_EXPIRY_DATE < (SYSDATE)-90 AND TRF.ORG_ID = ORG.ID AND ORG.ID = ORI.ORG_ID AND (org.data_source <> 2 OR org.data_source IS NULL) AND NOT EXISTS   (SELECT *  FROM TF_CML_APPLICATIONS CAP WHERE CAP.TRF_ID = TRF.ID   AND TRS_END_DATE IS NULL   AND STATUS IN (1, 2, 3, 5)) AND EXISTS   (SELECT *  FROM TRAFFIC.TF_STP_ORG_CAT_DETAILS det WHERE DET.ORG_ID = org.id  AND DET.OCT_ID IN (SELECT CAT.ID FROM TRAFFIC.TF_STP_ORG_CATEGORIES cat WHERE CAT.LICENSE_TYPE = 2)) AND EXISTS ( SELECT * FROM TF_CML_PERMITS PER   WHERE EXPIRY_DATE < (SYSDATE)   AND PER.ORG_ID = ORG.ID) AND EXISTS (SELECT * FROM TF_CML_TRAINING_APPLICATIONS TRN WHERE TRN.ORG_ID = ORG.ID  AND TRAINING_END_DATE IS NOT NULL \r\n" + 
				"AND TO_DATE(TRAINING_END_DATE) < TO_DATE(SYSDATE)) ORDER BY TRF.ID DESC");

		System.out.println("get Traffic file and Trade License");
		Thread.sleep(10000);

		while (rs.next() && count == 1) {
			TRF = rs.getString(1);
			TL = rs.getString(2);
			System.out.println("Traffic File = " + TRF);
			System.out.println("Trade License = " + TL);
			count++;
		}

		// System.out.println("Count of NOCS TO BE IGNORED = " + NOCS_TO_BE_IGNORED);

		SQLStatement.close();
		// closing DB Connection
		con.close();
		// Thread.sleep(10000);

	}

	@Step("Get traffic file number and trade license number for cancel TL service")
	public void getTFandTLNoVhlForCancelTL() throws ClassNotFoundException, SQLException, InterruptedException {
		setConnection();
		Statement SQLStatement = con.createStatement();
		// step4 execute query
		ResultSet rs = SQLStatement.executeQuery(
				"SELECT TRF.TRAFFIC_NO , ORI.TRADE_LICENSE FROM TF_STP_TRAFFIC_FILES TRF,TF_STP_ORGANIZATIONS ORG,TF_STP_ORG_REGISTRATION_INFOS ORI \r\n"
						+ "WHERE TRF.ORG_ID = ORG.ID AND ORG.ID = ORI.ORG_ID and (org.data_source <> 2 or org.data_source is null)\r\n"
						+ "AND NOT EXISTS (SELECT * FROM TF_CML_APPLICATIONS CAP WHERE CAP.TRF_ID = TRF.ID AND TRS_END_DATE IS NULL AND STATUS IN (1,2,3,5))\r\n"
						+ "And exists ( select * from TRAFFIC.TF_STP_ORG_CAT_DETAILS det where DET.ORG_ID=org.id and DET.OCT_ID\r\n"
						+ "in (select CAT.ID from TRAFFIC.TF_STP_ORG_CATEGORIES cat where CAT.LICENSE_TYPE=2))\r\n"
						+ "AND NOT EXISTS (SELECT * FROM TF_VHL_BOOKLETS BKT WHERE BKT.TRF_ID = TRF.ID AND BKT.EXPIRY_DATE IS NOT NULL AND BKT.TRS_END_DATE IS NULL) ORDER BY ORG.ID ASC");

		System.out.println("get Traffic file and Trade License");
		Thread.sleep(10000);

		while (rs.next() && count == 1) {

			TRF = rs.getString(1);
			TL = rs.getString(2);
			System.out.println("Traffic File = " + TRF);
			System.out.println("Trade License = " + TL);
			count++;
		}

		// System.out.println("Count of NOCS TO BE IGNORED = " + NOCS_TO_BE_IGNORED);

		SQLStatement.close();
		// closing DB Connection
		con.close();
		// Thread.sleep(10000);

	}

	@Step("Get traffic file number and trade license number for pay depreciation fines service")
	public void getTFandTLForDepreciationFines() throws ClassNotFoundException, SQLException, InterruptedException {
		setConnection();
		Statement stmt = con.createStatement();
		// step4 execute query
		ResultSet rs = stmt.executeQuery(
				"SELECT TRF.TRAFFIC_NO , ORI.TRADE_LICENSE FROM TF_STP_TRAFFIC_FILES TRF,TF_STP_ORGANIZATIONS ORG,TF_STP_ORG_REGISTRATION_INFOS ORI WHERE TRF.ORG_ID = ORG.ID AND ORG.ID = ORI.ORG_ID AND NOT EXISTS (SELECT * FROM TF_CML_APPLICATIONS CAP WHERE CAP.TRF_ID = TRF.ID AND TRS_END_DATE IS NULL AND STATUS IN (1,2,3,5))AND EXISTS (SELECT * FROM TF_CML_TRF_DPR_FINES TDF WHERE TDF.TRf_id = TRF.ID and  TRUNC(TDF.CALC_DATE)  <= TRUNC(SYSDATE) AND TDF.STATUS = 2 AND TDF.TOTAL_DEP_DAYS > 0 and TDF.TOTAL_DEP_AMOUNT < 49000 )");

		System.out.println("get Traffic file and Trade License");
		Thread.sleep(10000);

		while (rs.next() && count == 1) {
			TRF = rs.getString(1);
			TL = rs.getString(2);
			System.out.println("Traffic File = " + TRF);
			System.out.println("Trade License = " + TL);
			count++;
		}

		// System.out.println("Count of NOCS TO BE IGNORED = " + NOCS_TO_BE_IGNORED);

		stmt.close();
		// closing DB Connection
		con.close();
		// Thread.sleep(10000);

	}

	@Step("Get traffic file number and trade license number for issue driver NOC service")
	public void getTFandTLForIssueDriverL() throws SQLException, InterruptedException, ClassNotFoundException {
		setConnection();
		Statement SQLStatement = con.createStatement();
		ResultSet rs = SQLStatement.executeQuery(
				"SELECT TRF.TRAFFIC_NO, ORI.TRADE_LICENSE FROM TF_STP_TRAFFIC_FILES TRF, TF_STP_ORGANIZATIONS ORG, TF_STP_ORG_REGISTRATION_INFOS ORI WHERE TRF.ORG_ID = ORG.ID AND ORG.ID = ORI.ORG_ID AND NOT EXISTS (SELECT * FROM TF_CML_APPLICATIONS CAP WHERE CAP.TRF_ID = TRF.ID AND TRS_END_DATE IS NULL AND STATUS IN (1, 2, 3, 5)) AND EXISTS (SELECT * FROM TRAFFIC.TF_STP_ORG_CAT_DETAILS det WHERE DET.ORG_ID = org.id AND DET.OCT_ID IN ('10039'))");
		System.out.println("get Traffic file and Trade License");
		Thread.sleep(10000);

		while (rs.next() && count == 1) {
			TRF = rs.getString(1);
			TL = rs.getString(2);
			System.out.println("Traffic File = " + TRF);
			System.out.println("Trade License = " + TL);
			count++;
		}

		SQLStatement.close();
		// closing DB Connection
		con.close();
		// Thread.sleep(10000);

	}

	public void getTFandTLFordealer() throws SQLException, InterruptedException, ClassNotFoundException {
		setConnection();
		Statement SQLStatement = con.createStatement();
		ResultSet rs = SQLStatement.executeQuery(
				"SELECT TRF.TRAFFIC_NO , ORI.TRADE_LICENSE FROM TF_STP_TRAFFIC_FILES TRF,TF_STP_ORGANIZATIONS ORG,TF_STP_ORG_REGISTRATION_INFOS ORI \r\n"
						+ "WHERE  ORI.TL_EXPIRY_DATE < (SYSDATE) and TRF.ORG_ID = ORG.ID AND ORG.is_dealer = 2 AND ORG.ID = ORI.ORG_ID \r\n"
						+ "And exists ( select * from TRAFFIC.TF_STP_ORG_CAT_DETAILS det where DET.ORG_ID=org.id and DET.OCT_ID in \r\n"
						+ "(select CAT.ID from TRAFFIC.TF_STP_ORG_CATEGORIES cat where CAT.LICENSE_TYPE=2 ))ORDER BY TRF.ID DESC");
		Thread.sleep(10000);

		while (rs.next() && count == 1) {
			TRF = rs.getString(1);
			TL = rs.getString(2);
			System.out.println("Traffic File = " + TRF);
			System.out.println("Trade License = " + TL);
			count++;
		}

		SQLStatement.close();
		// closing DB Connection
		con.close();
		// Thread.sleep(10000);

	}

	@Step("Get traffic file number and trade license number for suspend TL service")
	public void getTFandTLforsuspend(String Cat_Id) throws SQLException, InterruptedException, ClassNotFoundException {
		setConnection();
		Statement SQLStatement = con.createStatement();
		// step4 execute query
		ResultSet rs = SQLStatement.executeQuery(
				"SELECT TRF.TRAFFIC_NO , ORI.TRADE_LICENSE FROM TF_STP_TRAFFIC_FILES TRF,TF_STP_ORGANIZATIONS ORG,TF_STP_ORG_REGISTRATION_INFOS ORI \r\n"
						+ "WHERE TRF.ORG_ID = ORG.ID AND ORG.ID = ORI.ORG_ID \r\n"
						+ "AND NOT EXISTS (SELECT * FROM TF_CML_APPLICATIONS CAP WHERE CAP.TRF_ID = TRF.ID AND TRS_END_DATE IS NULL \r\n"
						+ "AND STATUS IN (1,2,3,5))And exists ( select * from TRAFFIC.TF_STP_ORG_CAT_DETAILS det where DET.ORG_ID=org.id and AND DET.OCT_ID ="+ Cat_Id +
						 "AND DET.OCT_ID AND DET.OCT_ID in \r\n"
						+ "(select CAT.ID from TRAFFIC.TF_STP_ORG_CATEGORIES cat where CAT.LICENSE_TYPE=2))\r\n"
						+ "order by TRF.ORG_ID DESC ");

		System.out.println("getting Traffic file and Trade License");

		Thread.sleep(10000);

		while (rs.next() && count == 1) {

			TRF = rs.getString(1);
			TL = rs.getString(2);
			System.out.println("Traffic File = " + TRF);
			System.out.println("Trade License = " + TL);
			count++;
		}

		// System.out.println("Count of NOCS TO BE IGNORED = " + NOCS_TO_BE_IGNORED);
		SQLStatement.close();
		// closing DB Connection
		con.close();
		// Thread.sleep(10000);

	}

	public void getTFandTLForaddnewContract () throws SQLException, InterruptedException, ClassNotFoundException {
		setConnection();
		Statement SQLStatement = con.createStatement();
		// step4 execute query
		ResultSet rs = SQLStatement.executeQuery(
				"  SELECT  TRF.TRAFFIC_NO, ORI.TRADE_LICENSE \r\n" + 
				"    FROM TF_STP_TRAFFIC_FILES TRF,\r\n" + 
				"         TF_STP_ORGANIZATIONS ORG,\r\n" + 
				"         TF_STP_ORG_REGISTRATION_INFOS ORI,\r\n" + 
				"         TF_STP_ORG_CAT_DETAILS ORG_CAT,\r\n" + 
				"         TF_STP_ORG_CATEGORIES CAT\r\n" + 
				"   WHERE TRF.ORG_ID = ORG.ID\r\n" + 
				"         AND ORG.ID = ORI.ORG_ID\r\n" + 
				"         AND ORG_CAT.ORG_ID = ORG.ID\r\n" + 
				"         AND CAT.ID = ORG_CAT.OCT_ID\r\n" + 
				"         AND CAT.CODE  = 325\r\n" + 
				"        AND ORI.ORG_ID NOT IN (SELECT ORG_ID FROM TF_STP_ORG_CONTRACTS )\r\n");

		System.out.println("getting Traffic file and Trade License");

		Thread.sleep(10000);

		while (rs.next() && count == 1) {

			TRF = rs.getString(1);
			TL = rs.getString(2);
			System.out.println("Traffic File = " + TRF);
			System.out.println("Trade License = " + TL);
			count++;
		}

		// System.out.println("Count of NOCS TO BE IGNORED = " + NOCS_TO_BE_IGNORED);
		SQLStatement.close();
		// closing DB Connection
		con.close();
		// Thread.sleep(10000);

		
	}
	@Step("Get traffic file number and trade license number for TemporaryEvent")
	public void getTFandTLForTemporaryEvent() throws ClassNotFoundException, SQLException, InterruptedException {
		setConnection();
		Statement SQLStatement = con.createStatement();
		// step4 execute query
		ResultSet rs = SQLStatement.executeQuery("/* Formatted on 3/7/2019 10:58:13 AM (QP5 v5.287) */\r\n" + 
				"  SELECT TRF.TRAFFIC_NO, ORI.TRADE_LICENSE\r\n" + 
				"    FROM TF_STP_TRAFFIC_FILES TRF,\r\n" + 
				"         TF_STP_ORGANIZATIONS ORG,\r\n" + 
				"         TF_STP_ORG_REGISTRATION_INFOS ORI\r\n" + 
				"   WHERE     ORI.TL_EXPIRY_DATE > (SYSDATE)\r\n" + 
				"         AND TRF.ORG_ID = ORG.ID\r\n" + 
				"         AND ORG.ID = ORI.ORG_ID\r\n" + 
				"         AND (org.data_source <> 2 OR org.data_source IS NULL)\r\n" + 
				"         AND NOT EXISTS\r\n" + 
				"                (SELECT *\r\n" + 
				"                   FROM TF_CML_APPLICATIONS CAP\r\n" + 
				"                  WHERE     CAP.TRF_ID = TRF.ID\r\n" + 
				"                        AND TRS_END_DATE IS NULL\r\n" + 
				"                        AND STATUS IN (1,\r\n" + 
				"                                       2,\r\n" + 
				"                                       3,\r\n" + 
				"                                       5))\r\n" + 
				"         AND EXISTS\r\n" + 
				"                (SELECT *\r\n" + 
				"                   FROM TRAFFIC.TF_STP_ORG_CAT_DETAILS det\r\n" + 
				"                  WHERE     DET.ORG_ID = org.id\r\n" + 
				"                        AND DET.OCT_ID IN ('27', '11602', '15340')\r\n" + 
				"                        AND DET.OCT_ID IN\r\n" + 
				"                               (SELECT CAT.ID\r\n" + 
				"                                  FROM TRAFFIC.TF_STP_ORG_CATEGORIES cat\r\n" + 
				"                                 WHERE CAT.LICENSE_TYPE = 2))\r\n" + 
				"         AND ROWNUM <= 10\r\n" + 
				"ORDER BY TRF.ID ASC");

		System.out.println("get Traffic file and Trade License");
		Thread.sleep(10000);

		while (rs.next() && count == 1) {
			TRF = rs.getString(1);
			TL = rs.getString(2);
			System.out.println("Traffic File = " + TRF);
			System.out.println("Trade License = " + TL);
			count++;
		}

		// System.out.println("Count of NOCS TO BE IGNORED = " + NOCS_TO_BE_IGNORED);

		SQLStatement.close();
		// closing DB Connection
		con.close();
		// Thread.sleep(10000);

	}

	public String getcertificateno(String ordNo) throws ClassNotFoundException, SQLException, InterruptedException {
		// Connection con = dbConn.dbconntion();
		setConnection();
		Statement stmt3 = con.createStatement();
		Thread.sleep(10000);

		ResultSet rs = stmt3
				.executeQuery("select LTR_REF_NO from TF_CML_APPLICATIONS where status = 5 and APP_REF_NO =" + ordNo);
		while (rs.next()) {
			certno = rs.getString(1);}

		
		stmt3.close();
		// closing DB Connection
		if (certno == null) {
			getcertificateno(ordNo);}		
		con.close();

		return certno;
	}
	public void GetTFfornewsub() throws SQLException, InterruptedException, ClassNotFoundException {
	setConnection();
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
	public void getnewTF(String TL) throws SQLException, InterruptedException, ClassNotFoundException {
	setConnection();
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

@Step("Approve security requests")
	public void securityapproval(String AppNo) throws ClassNotFoundException, SQLException, InterruptedException {
	
	setConnection();

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

	public void EPSapproval(String TRSID) throws ClassNotFoundException, SQLException, InterruptedException {

setConnection();

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
	public void TRXupdateStatus(String AppNo ,String status )
		throws ClassNotFoundException, SQLException, InterruptedException {
	setConnection();

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
