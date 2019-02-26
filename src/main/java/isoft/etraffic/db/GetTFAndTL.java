package isoft.etraffic.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import io.qameta.allure.Step;

public class GetTFAndTL {

	public String TRF;
	public String TL;
	int count = 1;
	Connection con = dbConn.dbconntion();

	@Step("Get traffic file number and trade license number for service")
	public void getGeneralTFandTL() throws SQLException, InterruptedException {

		Statement SQLStatement = con.createStatement();
		// step4 execute query
		ResultSet rs = SQLStatement.executeQuery(
				"SELECT TRF.TRAFFIC_NO , ORI.TRADE_LICENSE FROM TF_STP_TRAFFIC_FILES TRF,TF_STP_ORGANIZATIONS ORG,TF_STP_ORG_REGISTRATION_INFOS ORI WHERE TRF.ORG_ID = ORG.ID AND ORG.ID = ORI.ORG_ID AND NOT EXISTS (SELECT * FROM TF_CML_APPLICATIONS CAP WHERE CAP.TRF_ID = TRF.ID AND TRS_END_DATE IS NULL AND STATUS IN (1,2,3,5))And exists ( select * from TRAFFIC.TF_STP_ORG_CAT_DETAILS det where DET.ORG_ID=org.id and DET.OCT_ID in (select CAT.ID from TRAFFIC.TF_STP_ORG_CATEGORIES cat where CAT.LICENSE_TYPE=2)) order by TRF.ORG_ID ASC ");

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
	public void getTFandTLForModify() throws SQLException, InterruptedException {

		Statement SQLStatement = con.createStatement();
		ResultSet rs = SQLStatement.executeQuery(
				"SELECT TRF.TRAFFIC_NO, ORI.TRADE_LICENSE   FROM TF_STP_TRAFFIC_FILES TRF, TF_STP_ORGANIZATIONS ORG, TF_STP_ORG_REGISTRATION_INFOS ORI\r\n"
						+ "WHERE ORI.TL_EXPIRY_DATE > (SYSDATE) AND TRF.ORG_ID = ORG.ID AND ORG.ID = ORI.ORG_ID\r\n"
						+ "AND (org.data_source <> 2 OR org.data_source IS NULL)\r\n"
						+ "AND NOT EXISTS (SELECT * FROM TF_CML_APPLICATIONS CAP   WHERE CAP.TRF_ID = TRF.ID AND TRS_END_DATE IS NULL AND STATUS IN (1,  2,  3,  5))\r\n"
						+ "AND EXISTS (SELECT * FROM TRAFFIC.TF_STP_ORG_CAT_DETAILS det   WHERE DET.ORG_ID = org.id AND DET.OCT_ID IN ('10068', '10069', '10039')\r\n"
						+ "AND NOT EXISTS  (SELECT * FROM TRAFFIC.TF_STP_ORG_CAT_DETAILS det WHERE DET.ORG_ID = org.id  AND DET.OCT_ID IN ('10064',   '10081',   '10080',   '5',   '25',   '15253',   '15510'))  AND DET.OCT_ID IN   (SELECT CAT.ID  FROM TRAFFIC.TF_STP_ORG_CATEGORIES   cat WHERE CAT.LICENSE_TYPE = 2))ORDER BY TRF.ID DEsc");
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
	public void getTFandTLForModifyaddactivity() throws SQLException, InterruptedException {

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

		Statement SQLStatement = con.createStatement();
		// step4 execute query
		ResultSet rs = SQLStatement.executeQuery(
				"SELECT TRF.TRAFFIC_NO, ORI.TRADE_LICENSE , ORI.TL_EXPIRY_DATE , ORG.ID FROM TF_STP_TRAFFIC_FILES TRF, TF_STP_ORGANIZATIONS ORG, TF_STP_ORG_REGISTRATION_INFOS ORI  WHERE ORI.TL_EXPIRY_DATE < (SYSDATE) AND TRF.ORG_ID = ORG.ID AND ORG.ID = ORI.ORG_ID AND (org.data_source <> 2 OR org.data_source IS NULL) AND NOT EXISTS   (SELECT *  FROM TF_CML_APPLICATIONS CAP WHERE CAP.TRF_ID = TRF.ID   AND TRS_END_DATE IS NULL   AND STATUS IN (1, 2, 3, 5)) AND EXISTS   (SELECT *  FROM TRAFFIC.TF_STP_ORG_CAT_DETAILS det WHERE DET.ORG_ID = org.id  AND DET.OCT_ID IN (SELECT CAT.ID FROM TRAFFIC.TF_STP_ORG_CATEGORIES cat WHERE CAT.LICENSE_TYPE = 2)) AND EXISTS ( SELECT * FROM TF_CML_PERMITS PER   WHERE EXPIRY_DATE < (SYSDATE)   AND PER.ORG_ID = ORG.ID) AND EXISTS (SELECT * FROM TF_CML_TRAINING_APPLICATIONS TRN WHERE TRN.ORG_ID = ORG.ID  AND TRAINING_END_DATE IS NOT NULL \r\n" + 
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

		Statement stmt = con.createStatement();
		// step4 execute query
		ResultSet rs = stmt.executeQuery(
				"SELECT TRF.TRAFFIC_NO , ORI.TRADE_LICENSE FROM TF_STP_TRAFFIC_FILES TRF,TF_STP_ORGANIZATIONS ORG,TF_STP_ORG_REGISTRATION_INFOS ORI WHERE TRF.ORG_ID = ORG.ID AND ORG.ID = ORI.ORG_ID AND NOT EXISTS (SELECT * FROM TF_CML_APPLICATIONS CAP WHERE CAP.TRF_ID = TRF.ID AND TRS_END_DATE IS NULL AND STATUS IN (1,2,3,5))AND EXISTS (SELECT * FROM TF_CML_TRF_DPR_FINES TDF WHERE TDF.TRf_id = TRF.ID and  TRUNC(TDF.CALC_DATE)  = TRUNC(SYSDATE) AND TDF.STATUS = 2 AND TDF.TOTAL_DEP_DAYS > 0)");

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
	public void getTFandTLForIssueDriverL() throws SQLException, InterruptedException {

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

	public void getTFandTLFordealer() throws SQLException, InterruptedException {

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
	public void getTFandTLforsuspend() throws SQLException, InterruptedException {

		Statement SQLStatement = con.createStatement();
		// step4 execute query
		ResultSet rs = SQLStatement.executeQuery(
				"SELECT TRF.TRAFFIC_NO , ORI.TRADE_LICENSE FROM TF_STP_TRAFFIC_FILES TRF,TF_STP_ORGANIZATIONS ORG,TF_STP_ORG_REGISTRATION_INFOS ORI \r\n"
						+ "WHERE TRF.ORG_ID = ORG.ID AND ORG.ID = ORI.ORG_ID \r\n"
						+ "AND NOT EXISTS (SELECT * FROM TF_CML_APPLICATIONS CAP WHERE CAP.TRF_ID = TRF.ID AND TRS_END_DATE IS NULL \r\n"
						+ "AND STATUS IN (1,2,3,5))And exists ( select * from TRAFFIC.TF_STP_ORG_CAT_DETAILS det where DET.ORG_ID=org.id and DET.OCT_ID in \r\n"
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

	public void getTFandTLForaddnewContract () throws SQLException, InterruptedException {

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
}
