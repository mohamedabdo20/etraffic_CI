package isoft.etraffic.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import isoft.etraffic.enums.Database;
import isoft.etraffic.enums.PlateCategory;
import isoft.etraffic.enums.VehicleClass;
import isoft.etraffic.enums.VehicleWeight;

public class DBQueries {

	Connection con;
	Database database = Database.Test1;
	String Username, password;

	public void addInsurance(String chassis, VehicleClass vehicleClass) throws ClassNotFoundException, SQLException {
		setConnection();
		String vehicleClassId = getVehicleClassId(vehicleClass);
		Statement stmt = con.createStatement();

		stmt.execute("DECLARE\r\n" + "   v_seqvalue           NUMBER;\r\n" + "   v_seqvalue1          NUMBER;\r\n"
				+ "   v_engin              VARCHAR2 (30);\r\n" + "   v_seq_value          NUMBER;\r\n"
				+ "   v_new_chass_number   VARCHAR2 (30);\r\n" + "   v_policy_no          VARCHAR2 (30);\r\n"
				+ "   v_VSM_ID             NUMBER;\r\n" + "   v_VMK_ID        NUMBER;\r\n"
				+ "   v_VDS_ID       NUMBER;\r\n" + "    V_USR_NAME                 varchar2(20):='esam';\r\n"
				+ "    V_CHASS_NUMBER           VARCHAR2 (30) :='" + chassis + "';\r\n"
				+ "      V_INSURANCE_PERIOD      NUMBER := 13; --- IN MONTHS\r\n"
				+ "    V_VCL_ID                 NUMBER        := " + vehicleClassId + " ; \r\n" + "BEGIN\r\n"
				+ "   SELECT traffic.vht_seq.NEXTVAL\r\n" + "     INTO v_seqvalue\r\n" + "     FROM DUAL;\r\n" + "\r\n"
				+ "   SELECT traffic.vps_seq.NEXTVAL\r\n" + "     INTO v_seqvalue1\r\n" + "     FROM DUAL;\r\n" + "\r\n"
				+ "   SELECT traffic.eir_seq.NEXTVAL\r\n" + "     INTO v_seq_value\r\n" + "     FROM DUAL;\r\n" + "\r\n"
				+ "   v_new_chass_number := NULL;\r\n" + "   v_engin := NULL;\r\n" + "   v_policy_no := '123456';\r\n"
				+ "   V_VMK_ID := 10251;\r\n" + "\r\n" + "        DELETE from TRAFFIC.TF_VHL_TRANSACTION_VEHICLES\r\n"
				+ "        where CHASISS_NO =v_chass_number\r\n" + "        AND    CREATION_DATE = TRUNC(SYSDATE)\r\n"
				+ "        AND    USR_ID = (SELECT ID FROM traffic.SF_INF_USERS WHERE NAME = V_USR_NAME);\r\n" + "\r\n"
				+ "        select ID\r\n" + "        into    V_VSM_ID\r\n"
				+ "        from     traffic.tf_vhl_vehicle_model\r\n" + "        where     vcl_id = v_vcl_id\r\n"
				+ "        and        VMK_ID = 10251\r\n" + "        and        status = 2\r\n"
				+ "        AND    ROWNUM< 2;\r\n" + "\r\n" + "        select ID\r\n" + "        into    V_VDS_ID\r\n"
				+ "        from traffic.tf_vhl_vehicle_DESCRIPTIONS\r\n" + "        where vcl_id = v_vcl_id\r\n"
				+ "        --and    status = 2\r\n" + "        AND    ROWNUM< 2;\r\n" + "\r\n"
				+ "   INSERT INTO traffic.tf_vhl_electronic_insurances\r\n"
				+ "               (ID, policy_date, policy_no, policy_expiry_date,\r\n"
				+ "                no_of_seats, chasiss_no, cylinders, insurance_type,\r\n"
				+ "                engine_no, no_of_doors, myr_id, emi_code, plt_id, org_id,\r\n"
				+ "                created_by, creation_date, status, status_date, updated_by,\r\n"
				+ "                update_date, insurance_user, trf_id, remarks, traffic_no,\r\n"
				+ "                owner_name_a, owner_name_e, driver_age,\r\n"
				+ "                driving_license_issue_date, driver_nationality_id,\r\n"
				+ "                driver_occupation_id, vmk_id, vsm_id, vds_id, vcl_id,\r\n"
				+ "                veh_unloaded_weight, veh_eng_power, vehicle_main_color,\r\n"
				+ "                veh_eng_capacity, insurance_amount, bear_amount,\r\n"
				+ "                is_covered_family_employees, is_covered_driver,\r\n"
				+ "                no_passenger_with_driver, mortgaged_by, svc_code,\r\n"
				+ "                vehicle_value, emirates_id, trs_id\r\n" + "               )\r\n"
				+ "        VALUES (v_seq_value, SYSDATE, v_policy_no, ADD_MONTHS (SYSDATE,V_INSURANCE_PERIOD),\r\n"
				+ "                NULL, v_chass_number, NULL, 3,\r\n"
				+ "                NULL, NULL, 2018, 'DXB', NULL, 64271,\r\n"
				+ "                'ehab', SYSDATE, 1, SYSDATE, 'ehab',\r\n"
				+ "                SYSDATE, 'ehab', NULL, NULL, NULL,\r\n" + "                NULL, NULL, NULL,\r\n"
				+ "                NULL, NULL,\r\n" + "                NULL, NULL, NULL, NULL, NULL,\r\n"
				+ "                NULL, NULL, NULL,\r\n" + "                NULL, NULL, NULL,\r\n"
				+ "                NULL, NULL,\r\n" + "                NULL, NULL, 204,\r\n"
				+ "                NULL, NULL, NULL\r\n" + "               );\r\n" + "\r\n" + "      COMMIT;\r\n"
				+ "END;");
		con.close();
		System.out.println("Vehicle Insurance was added successfully");
	}

	public void addInsurance(String chassis, String vehicleClass) throws ClassNotFoundException, SQLException {
		setConnection();
		String vehicleClassId = getVehicleClassId(vehicleClass);
		Statement stmt = con.createStatement();

		stmt.execute("DECLARE\r\n" + "   v_seqvalue           NUMBER;\r\n" + "   v_seqvalue1          NUMBER;\r\n"
				+ "\r\n" + "   v_engin              VARCHAR2 (30);\r\n" + "   v_seq_value          NUMBER;\r\n"
				+ "   v_new_chass_number   VARCHAR2 (30);\r\n" + "   v_policy_no          VARCHAR2 (30);\r\n"
				+ "   v_VSM_ID             NUMBER;\r\n" + "   v_VMK_ID        NUMBER;\r\n"
				+ "   v_VDS_ID       NUMBER;\r\n" + "\r\n" + "   ---------------SECRIPT CRITERIAS\r\n"
				+ "    V_USR_NAME                 varchar2(20):='esam';\r\n"
				+ "    V_CHASS_NUMBER           VARCHAR2 (30) :='" + chassis + "';\r\n"
				+ "      V_INSURANCE_PERIOD      NUMBER := 13; --- IN MONTHS\r\n"
				+ "    V_VCL_ID                 NUMBER        := " + vehicleClassId
				+ " ; --->> to add the vehcile classs     as below classes :\r\n" + "\r\n" + "BEGIN\r\n"
				+ "   SELECT traffic.vht_seq.NEXTVAL\r\n" + "     INTO v_seqvalue\r\n" + "     FROM DUAL;\r\n" + "\r\n"
				+ "   SELECT traffic.vps_seq.NEXTVAL\r\n" + "     INTO v_seqvalue1\r\n" + "     FROM DUAL;\r\n" + "\r\n"
				+ "   SELECT traffic.eir_seq.NEXTVAL\r\n" + "     INTO v_seq_value\r\n" + "     FROM DUAL;\r\n" + "\r\n"
				+ "   v_new_chass_number := NULL;\r\n" + "   v_engin := NULL;\r\n" + "   v_policy_no := '123456';\r\n"
				+ "   --v_test_type NUMBER:=  5;  -- 5 ---->> Registration , 19------>> Classis\r\n"
				+ "   V_VMK_ID := 10251; ---- TOYOTA\r\n" + "\r\n"
				+ "        DELETE from TRAFFIC.TF_VHL_TRANSACTION_VEHICLES\r\n"
				+ "        where CHASISS_NO =v_chass_number\r\n" + "        AND    CREATION_DATE = TRUNC(SYSDATE)\r\n"
				+ "        AND    USR_ID = (SELECT ID FROM traffic.SF_INF_USERS WHERE NAME = V_USR_NAME);\r\n" + "\r\n"
				+ "        select ID\r\n" + "        into    V_VSM_ID\r\n"
				+ "        from     traffic.tf_vhl_vehicle_model\r\n" + "        where     vcl_id = v_vcl_id\r\n"
				+ "        and        VMK_ID = 10251\r\n" + "        and        status = 2\r\n"
				+ "        AND    ROWNUM< 2;\r\n" + "\r\n" + "        select ID\r\n" + "        into    V_VDS_ID\r\n"
				+ "        from traffic.tf_vhl_vehicle_DESCRIPTIONS\r\n" + "        where vcl_id = v_vcl_id\r\n"
				+ "        --and    status = 2\r\n" + "        AND    ROWNUM< 2;\r\n" + "\r\n"
				+ "   INSERT INTO traffic.tf_vhl_electronic_insurances\r\n"
				+ "               (ID, policy_date, policy_no, policy_expiry_date,\r\n"
				+ "                no_of_seats, chasiss_no, cylinders, insurance_type,\r\n"
				+ "                engine_no, no_of_doors, myr_id, emi_code, plt_id, org_id,\r\n"
				+ "                created_by, creation_date, status, status_date, updated_by,\r\n"
				+ "                update_date, insurance_user, trf_id, remarks, traffic_no,\r\n"
				+ "                owner_name_a, owner_name_e, driver_age,\r\n"
				+ "                driving_license_issue_date, driver_nationality_id,\r\n"
				+ "                driver_occupation_id, vmk_id, vsm_id, vds_id, vcl_id,\r\n"
				+ "                veh_unloaded_weight, veh_eng_power, vehicle_main_color,\r\n"
				+ "                veh_eng_capacity, insurance_amount, bear_amount,\r\n"
				+ "                is_covered_family_employees, is_covered_driver,\r\n"
				+ "                no_passenger_with_driver, mortgaged_by, svc_code,\r\n"
				+ "                vehicle_value, emirates_id, trs_id\r\n" + "               )\r\n"
				+ "        VALUES (v_seq_value, SYSDATE, v_policy_no, ADD_MONTHS (SYSDATE,V_INSURANCE_PERIOD),\r\n"
				+ "                NULL, v_chass_number, NULL, 3,\r\n"
				+ "                NULL, NULL, 2018, 'DXB', NULL, 64271,\r\n"
				+ "                'ehab', SYSDATE, 1, SYSDATE, 'ehab',\r\n"
				+ "                SYSDATE, 'ehab', NULL, NULL, NULL,\r\n" + "                NULL, NULL, NULL,\r\n"
				+ "                NULL, NULL,\r\n" + "                NULL, NULL, NULL, NULL, NULL,\r\n"
				+ "                NULL, NULL, NULL,\r\n" + "                NULL, NULL, NULL,\r\n"
				+ "                NULL, NULL,\r\n" + "                NULL, NULL, 204,\r\n"
				+ "                NULL, NULL, NULL\r\n" + "               );\r\n" + "\r\n" + "      COMMIT;\r\n"
				+ "END;");
		con.close();
		System.out.println(" Vehicle Insurance was added successfully");
	}

	// 
	public void addTest(String chassis, VehicleClass vehicleClass, String weight)
			throws ClassNotFoundException, SQLException {
		setConnection();

		// String chassis = "JL6DWM6R3FK004044";
		// VehicleClass vehicleClass =VehicleClass.HeavyVehicle;
		// String weight = "6500";

//		String vehicleClassId = getVehicleClassId(vehicleClass);
//		String noOfSeats = getNoOfSeats(vehicleClass);
//
//		String manufacturerId;
//		if (vehicleClass.equals(VehicleClass.Trailer))
//			manufacturerId = "7"; // Trailer
//		else if (vehicleClass.equals(VehicleClass.EntertainmentMotorcycle))
//			manufacturerId = "10203"; // Trailer
//		else
//			manufacturerId = "10251"; // TOYOTA

		Statement stmt = con.createStatement();
		stmt.execute("DECLARE\r\n" + 
				"   v_seqvalue              NUMBER;\r\n" + 
				"   v_seqvalue1             NUMBER;\r\n" + 
				"   v_engin                 VARCHAR2 (30);\r\n" + 
				"   v_seq_value             NUMBER;\r\n" + 
				"   v_new_chass_number      VARCHAR2 (30);\r\n" + 
				"   v_policy_no             VARCHAR2 (30);\r\n" + 
				"   v_VSM_ID                NUMBER;\r\n" + 
				"   v_VMK_ID                NUMBER;\r\n" + 
				"   v_VDS_ID                NUMBER;\r\n" + 
				"   V_USR_NAME              VARCHAR2 (20) := 'esam';\r\n" + 
				"   v_chass_number          VARCHAR2 (30) := '"+ chassis +"';\r\n" + 
				"   v_test_type             NUMBER := 5;\r\n" + 
				"   v_new_unloaded_weight   NUMBER := "+ weight +";\r\n" + 
				"   v_new_no_of_seats       NUMBER;\r\n" + 
				"   V_VCL_ID                NUMBER;\r\n" + 
				"BEGIN\r\n" + 
				"   SELECT traffic.vht_seq.NEXTVAL INTO v_seqvalue FROM DUAL;\r\n" + 
				"   SELECT traffic.vps_seq.NEXTVAL INTO v_seqvalue1 FROM DUAL;\r\n" + 
				"   SELECT traffic.eir_seq.NEXTVAL INTO v_seq_value FROM DUAL;\r\n" + 
				"   SELECT VMK.ID, VLE.NO_OF_SEATS, VLE.VCL_ID, VLE.VDS_ID, VLE.VSM_ID\r\n" + 
				"     INTO v_VMK_ID, v_new_no_of_seats, V_VCL_ID, V_VDS_ID, V_VSM_ID\r\n" + 
				"     FROM TF_VHL_VEHICLES VLE, TRAFFIC.TF_VHL_VEHICLE_MODEL VSM, TRAFFIC.TF_VHL_MANUFACTURERS VMK\r\n" + 
				"    WHERE VLE.VSM_ID = VSM.ID AND VSM.VMK_ID = VMK.ID AND CHASISS_NO = v_chass_number;\r\n" + 
				"   V_NEW_CHASS_NUMBER := NULL;\r\n" + 
				"   V_ENGIN := NULL;\r\n" + 
				"   V_POLICY_NO := '123456';\r\n" + 
				"   DELETE FROM TRAFFIC.TF_VHL_TRANSACTION_VEHICLES\r\n" + 
				"         WHERE CHASISS_NO = v_chass_number\r\n" + 
				"               AND CREATION_DATE = TRUNC (SYSDATE)\r\n" + 
				"               AND USR_ID = (SELECT ID FROM traffic.SF_INF_USERS WHERE NAME = V_USR_NAME);\r\n" + 
				"   INSERT INTO traffic.tf_stp_vehicle_tests \r\n" + 
				"   (chasiss_no, test_date, ID, emi_code, ctr_id, created_by,\r\n" + 
				"    creation_date, insurance_ref_no, new_vds_id, new_cnt_id,\r\n" + 
				"    current_meter_reading, status_date, test_type, meter_unit,\r\n" + 
				"    new_unloaded_weight, new_no_of_doors, new_wheel_drive,\r\n" + 
				"    new_eng_power, new_fuel_code, status, new_vsm_id,\r\n" + 
				"    need_fees_calculated, new_axes_no, test_result,\r\n" + 
				"    new_vcl_id, new_myr_id, new_chassis_no, new_no_of_seats,\r\n" + 
				"    new_eng_capacity, failed_count, current_meter_reading_km,\r\n" + 
				"    new_cylinders, new_carry_weight, new_engine_no, cis_id, trs_type)\r\n" + 
				"    VALUES (v_chass_number, SYSDATE, v_seqvalue, 'DXB', 1215,'rta4730',\r\n" + 
				"            SYSDATE, '234567', V_VDS_ID, NULL, 4545, SYSDATE, 1, 1, v_new_unloaded_weight,\r\n" + 
				"            NULL, NULL, NULL, NULL, 1, V_VSM_ID, 2, 12, 2, V_VCL_ID, 2017, v_new_chass_number,\r\n" + 
				"            v_new_no_of_seats, NULL, 0, 4545, NULL, 121, v_engin, v_test_type, 1);\r\n" + 
				"   INSERT INTO traffic.tf_stp_vht_pass_services (ID,cis_id, vht_id, update_date, updated_by,\r\n" + 
				"                                                 creation_date, created_by)\r\n" + 
				"        VALUES (v_seqvalue1, v_test_type, v_seqvalue, SYSDATE, 'rta4730', SYSDATE, 'rta4730');\r\n" + 
				"   COMMIT;\r\n" + 
				"END;");
//		"DECLARE\r\n" + "   v_seqvalue           NUMBER;\r\n" + "   v_seqvalue1          NUMBER;\r\n"
//				+ "\r\n" + "   v_engin              VARCHAR2 (30);\r\n" + "   v_seq_value          NUMBER;\r\n"
//				+ "   v_new_chass_number   VARCHAR2 (30);\r\n" + "   v_policy_no          VARCHAR2 (30);\r\n"
//				+ "   v_VSM_ID             NUMBER;\r\n" + "   v_VMK_ID                NUMBER;\r\n"
//				+ "   v_VDS_ID               NUMBER;\r\n" + "\r\n" + "\r\n" + "    ---------------SECRIPT CRITERIAS\r\n"
//				+ "    V_USR_NAME                 varchar2(20):='esam';\r\n"
//				+ "    v_chass_number           VARCHAR2 (30) :='" + chassis + "';\r\n"
//				+ "       v_test_type                 NUMBER:=5 ;  -- 5 ---->> Registration , 19------>> Classis\r\n"
//				+ "\r\n" + "       v_new_unloaded_weight     NUMBER:= " + weight
//				+ "; ---->> in case vehcile class is Light vehcile (1-3000), Heavy Vehcile (3001-10000000);\r\n"
//				+ "       v_new_no_of_seats         Number:= " + noOfSeats
//				+ ";  ------->> in case vehcile class is Havey Bus (27-1000), Light buss (14-26)\r\n" + "\r\n"
//				+ "       V_VCL_ID                 NUMBER:= " + vehicleClassId
//				+ "; --->> to add the vehcile classs     as below classes :\r\n" + "\r\n" + "BEGIN\r\n"
//				+ "   SELECT traffic.vht_seq.NEXTVAL\r\n" + "     INTO v_seqvalue\r\n" + "     FROM DUAL;\r\n" + "\r\n"
//				+ "   SELECT traffic.vps_seq.NEXTVAL\r\n" + "     INTO v_seqvalue1\r\n" + "     FROM DUAL;\r\n" + "\r\n"
//				+ "   SELECT traffic.eir_seq.NEXTVAL\r\n" + "     INTO v_seq_value\r\n" + "     FROM DUAL;\r\n" + "\r\n"
//				+ "   V_NEW_CHASS_NUMBER := NULL;\r\n" + "   V_ENGIN := NULL;\r\n" + "   V_POLICY_NO := '123456';\r\n"
//				+ "   --V_TEST_TYPE NUMBER:=  5;  -- 5 ---->> REGISTRATION , 19------>> CLASSIS\r\n" + "   V_VMK_ID := "
//				+ manufacturerId + ";\r\n" + "        DELETE from TRAFFIC.TF_VHL_TRANSACTION_VEHICLES\r\n"
//				+ "        where CHASISS_NO =v_chass_number\r\n" + "        AND    CREATION_DATE = TRUNC(SYSDATE)\r\n"
//				+ "        AND    USR_ID = (SELECT ID FROM traffic.SF_INF_USERS WHERE NAME = V_USR_NAME);\r\n" + "\r\n"
//				+ "        select ID\r\n" + "        into    V_VSM_ID\r\n"
//				+ "        from     traffic.tf_vhl_vehicle_model\r\n" + "        where     vcl_id = v_vcl_id\r\n"
//				+ "        and        VMK_ID = " + manufacturerId + "\r\n" + "        and        status = 2\r\n"
//				+ "        AND    ROWNUM< 2;\r\n" + "\r\n" + "        select ID\r\n" + "        into    V_VDS_ID\r\n"
//				+ "        from traffic.tf_vhl_vehicle_DESCRIPTIONS\r\n" + "        where vcl_id = v_vcl_id\r\n"
//				+ "        --and    status = 2\r\n" + "        AND    ROWNUM < 2;\r\n" + "\r\n" + "\r\n"
//				+ "   INSERT INTO traffic.tf_stp_vehicle_tests\r\n"
//				+ "               (chasiss_no, test_date, ID, emi_code, ctr_id, created_by,\r\n"
//				+ "                creation_date, insurance_ref_no, new_vds_id, new_cnt_id,\r\n"
//				+ "                current_meter_reading, status_date, test_type, meter_unit,\r\n"
//				+ "                new_unloaded_weight, new_no_of_doors, new_wheel_drive,\r\n"
//				+ "                new_eng_power, new_fuel_code, status, new_vsm_id,\r\n"
//				+ "                need_fees_calculated, new_axes_no, test_result, new_vcl_id,\r\n"
//				+ "                new_myr_id, new_chassis_no, new_no_of_seats,\r\n"
//				+ "                new_eng_capacity, failed_count, current_meter_reading_km,\r\n"
//				+ "                new_cylinders, new_carry_weight, new_engine_no, cis_id,\r\n"
//				+ "                trs_type\r\n" + "               )\r\n"
//				+ "        VALUES (v_chass_number, SYSDATE, v_seqvalue, 'DXB', 1215, 'rta4730',\r\n"
//				+ "                SYSDATE, '234567', V_VDS_ID, null,\r\n" + "                4545, SYSDATE, 1, 1,\r\n"
//				+ "                v_new_unloaded_weight, null, null,\r\n"
//				+ "                null, null, 1, V_VSM_ID,\r\n" + "                2, 12, 2,V_VCL_ID,\r\n"
//				+ "                2017, v_new_chass_number, v_new_no_of_seats,\r\n"
//				+ "                null, 0, 4545,\r\n" + "                null, 121, v_engin, v_test_type,\r\n"
//				+ "                1\r\n" + "               );\r\n" + "\r\n"
//				+ "   INSERT INTO traffic.tf_stp_vht_pass_services\r\n"
//				+ "               (ID, cis_id, vht_id, update_date, updated_by, creation_date,\r\n"
//				+ "                created_by\r\n" + "               )\r\n"
//				+ "        VALUES (v_seqvalue1,v_test_type, v_seqvalue, SYSDATE, 'rta4730', SYSDATE,\r\n"
//				+ "                'rta4730'\r\n" + "               );\r\n" + "\r\n" + "\r\n" + "   COMMIT;\r\n"
//				+ "END;");

		System.out.println(("Vehicle Test was added successfully"));
		con.close();
	}

		public void addTestUnRegisteredVehicle(String chassis, VehicleClass vehicleClass, String weight)
				throws ClassNotFoundException, SQLException {
			setConnection();

//			String chassis = "21012019T09000000";
//			VehicleClass vehicleClass =VehicleClass.LightVehicle;
//			String weight = "2500";

			String vehicleClassId = getVehicleClassId(vehicleClass);
			String noOfSeats = getNoOfSeats(vehicleClass);
	
			String manufacturerId;
			if (vehicleClass.equals(VehicleClass.Trailer))
				manufacturerId = "7"; // Trailer
			else if (vehicleClass.equals(VehicleClass.EntertainmentMotorcycle))
				manufacturerId = "10203"; // Trailer
			else
				manufacturerId = "10251"; // TOYOTA

			Statement stmt = con.createStatement();
			stmt.execute("DECLARE\r\n" + "   v_seqvalue           NUMBER;\r\n" + "   v_seqvalue1          NUMBER;\r\n"
					+ "\r\n" + "   v_engin              VARCHAR2 (30);\r\n" + "   v_seq_value          NUMBER;\r\n"
					+ "   v_new_chass_number   VARCHAR2 (30);\r\n" + "   v_policy_no          VARCHAR2 (30);\r\n"
					+ "   v_VSM_ID             NUMBER;\r\n" + "   v_VMK_ID                NUMBER;\r\n"
					+ "   v_VDS_ID               NUMBER;\r\n" + "\r\n" + "\r\n" + "    ---------------SECRIPT CRITERIAS\r\n"
					+ "    V_USR_NAME                 varchar2(20):='esam';\r\n"
					+ "    v_chass_number           VARCHAR2 (30) :='" + chassis + "';\r\n"
					+ "       v_test_type                 NUMBER:=5 ;  -- 5 ---->> Registration , 19------>> Classis\r\n"
					+ "\r\n" + "       v_new_unloaded_weight     NUMBER:= " + weight
					+ "; ---->> in case vehcile class is Light vehcile (1-3000), Heavy Vehcile (3001-10000000);\r\n"
					+ "       v_new_no_of_seats         Number:= " + noOfSeats
					+ ";  ------->> in case vehcile class is Havey Bus (27-1000), Light buss (14-26)\r\n" + "\r\n"
					+ "       V_VCL_ID                 NUMBER:= " + vehicleClassId
					+ "; --->> to add the vehcile classs     as below classes :\r\n" + "\r\n" + "BEGIN\r\n"
					+ "   SELECT traffic.vht_seq.NEXTVAL\r\n" + "     INTO v_seqvalue\r\n" + "     FROM DUAL;\r\n" + "\r\n"
					+ "   SELECT traffic.vps_seq.NEXTVAL\r\n" + "     INTO v_seqvalue1\r\n" + "     FROM DUAL;\r\n" + "\r\n"
					+ "   SELECT traffic.eir_seq.NEXTVAL\r\n" + "     INTO v_seq_value\r\n" + "     FROM DUAL;\r\n" + "\r\n"
					+ "   V_NEW_CHASS_NUMBER := NULL;\r\n" + "   V_ENGIN := NULL;\r\n" + "   V_POLICY_NO := '123456';\r\n"
					+ "   --V_TEST_TYPE NUMBER:=  5;  -- 5 ---->> REGISTRATION , 19------>> CLASSIS\r\n" + "   V_VMK_ID := "
					+ manufacturerId + ";\r\n" + "        DELETE from TRAFFIC.TF_VHL_TRANSACTION_VEHICLES\r\n"
					+ "        where CHASISS_NO =v_chass_number\r\n" + "        AND    CREATION_DATE = TRUNC(SYSDATE)\r\n"
					+ "        AND    USR_ID = (SELECT ID FROM traffic.SF_INF_USERS WHERE NAME = V_USR_NAME);\r\n" + "\r\n"
					+ "        select ID\r\n" + "        into    V_VSM_ID\r\n"
					+ "        from     traffic.tf_vhl_vehicle_model\r\n" + "        where     vcl_id = v_vcl_id\r\n"
					+ "        and        VMK_ID = " + manufacturerId + "\r\n" + "        and        status = 2\r\n"
					+ "        AND    ROWNUM< 2;\r\n" + "\r\n" + "        select ID\r\n" + "        into    V_VDS_ID\r\n"
					+ "        from traffic.tf_vhl_vehicle_DESCRIPTIONS\r\n" + "        where vcl_id = v_vcl_id\r\n"
					+ "        --and    status = 2\r\n" + "        AND    ROWNUM < 2;\r\n" + "\r\n" + "\r\n"
					+ "   INSERT INTO traffic.tf_stp_vehicle_tests\r\n"
					+ "               (chasiss_no, test_date, ID, emi_code, ctr_id, created_by,\r\n"
					+ "                creation_date, insurance_ref_no, new_vds_id, new_cnt_id,\r\n"
					+ "                current_meter_reading, status_date, test_type, meter_unit,\r\n"
					+ "                new_unloaded_weight, new_no_of_doors, new_wheel_drive,\r\n"
					+ "                new_eng_power, new_fuel_code, status, new_vsm_id,\r\n"
					+ "                need_fees_calculated, new_axes_no, test_result, new_vcl_id,\r\n"
					+ "                new_myr_id, new_chassis_no, new_no_of_seats,\r\n"
					+ "                new_eng_capacity, failed_count, current_meter_reading_km,\r\n"
					+ "                new_cylinders, new_carry_weight, new_engine_no, cis_id,\r\n"
					+ "                trs_type\r\n" + "               )\r\n"
					+ "        VALUES (v_chass_number, SYSDATE, v_seqvalue, 'DXB', 1215, 'rta4730',\r\n"
					+ "                SYSDATE, '234567', V_VDS_ID, null,\r\n" + "                4545, SYSDATE, 1, 1,\r\n"
					+ "                v_new_unloaded_weight, null, null,\r\n"
					+ "                null, null, 1, V_VSM_ID,\r\n" + "                2, 12, 2,V_VCL_ID,\r\n"
					+ "                2017, v_new_chass_number, v_new_no_of_seats,\r\n"
					+ "                null, 0, 4545,\r\n" + "                null, 121, v_engin, v_test_type,\r\n"
					+ "                1\r\n" + "               );\r\n" + "\r\n"
					+ "   INSERT INTO traffic.tf_stp_vht_pass_services\r\n"
					+ "               (ID, cis_id, vht_id, update_date, updated_by, creation_date,\r\n"
					+ "                created_by\r\n" + "               )\r\n"
					+ "        VALUES (v_seqvalue1,v_test_type, v_seqvalue, SYSDATE, 'rta4730', SYSDATE,\r\n"
					+ "                'rta4730'\r\n" + "               );\r\n" + "\r\n" + "\r\n" + "   COMMIT;\r\n"
					+ "END;");

			System.out.println(("Vehicle Test was added successfully"));
			con.close();
		}
		
	public String getUserPassword(String username) throws ClassNotFoundException, SQLException {
		setConnection();
		Statement stmt = con.createStatement();

		// step4 execute query
		String password = "";
		ResultSet rs = stmt.executeQuery(
				"SELECT TRAFFIC.PKG_DB_ISM_APP_ENCRYPTION_OPER.F_DB_DECRYPT(USR.PSWD,'1234567891') PASSW\r\n"
						+ "  FROM TRAFFIC.SF_INF_USERS USR\r\n" + "WHERE 1 = 1\r\n"
						+ "AND UPPER (USR.NAME) LIKE UPPER ('%" + username + "%')");

		rs.next();
		try {
			System.out.println(("Username password: " + rs.getString(1)));
			password = rs.getString(1);
			con.close();
		} catch (Exception e) {
			con.close();
			System.out.println("This username doesn't exists in SF_INF_USERS table.");
		}
		return password;
	}

	public String getTrfPassword(String trafficFile) throws ClassNotFoundException, SQLException {
		setConnection();
		Statement stmt = con.createStatement();

		// step4 execute query
		String password = "";
		ResultSet rs = stmt
				.executeQuery("SELECT PKG_DB_ISM_APP_ENCRYPTION_OPER.F_DB_DECRYPT (a.PASSWORD, '1234567891') PASSW\r\n"
						+ "FROM TF_SPL_PERMIT_PASSWORDS a\r\n"
						+ "WHERE TPM_ID = (SELECT ID FROM TF_SPL_TRADE_PERMIT WHERE Permit_status = 1 AND Expiry_Date > SYSDATE AND TRF_ID = (SELECT id FROM tf_stp_traffic_files trf WHERE trf.traffic_no = "
						+ trafficFile + "))");

		rs.next();
		try {
			System.out.println(("Username password: " + rs.getString(1)));
			password = rs.getString(1);
			con.close();
		} catch (Exception e) {
			con.close();
			System.out.println("This username doesn't have password.");
		}
		return password;
	}

	public String getTrfFileHasTradePermit() throws ClassNotFoundException, SQLException {
		setConnection();
		Statement stmt = con.createStatement();

		// step4 execute query
		String password = "";
		ResultSet rs = stmt.executeQuery("select 		trf.traffic_no from  TF_STP_TRAFFIC_FILES TRF,"
				+ "	TF_STP_PERSONS PRS ,\r\n" + "	tf_stp_countries cnt\r\n" + "WHERE		TRF.PRS_ID = PRS.ID\r\n"
				+ "and			prs.cnt_id = cnt.id\r\n"
				+ "AND 0 < (SELECT COUNT(1) FROM TRAFFIC.TF_SPL_TRADE_PERMIT TPM WHERE TPM.TRF_ID = trf.id AND TPM.PERMIT_STATUS = 2 )\r\n"
				+ "and			rownum < 5");

		int counter = 0;
		while (rs.next()) {
			counter++;
			password = rs.getString(1);
		}
		if (counter == 0)
			System.out.println("No valid traffic files found in DB.");
		con.close();
		return password;
	}

	public String[] getVehicle() throws ClassNotFoundException, SQLException {
		setConnection();
		Statement stmt = con.createStatement();

		String[] vehilce = new String[6];

		ResultSet rs = stmt.executeQuery("SELECT F_GET_TRF_NUMBER_2(BKT.TRF_ID)TRF_NO,\r\n"
				+ "	   F_DB_GET_PLATE_DETAILS(BKT.PLT_ID) AS PLATE,\r\n" + "	   BKT.CHASISS_NO,\r\n"
				+ "	   EXPIRY_DATE,\r\n" + "	   PRS.PASSPORT_NO\r\n"
				+ "FROM TF_VHL_BOOKLETS BKT, TF_STP_TRAFFIC_FILES TRF, TF_VHL_VEHICLES VLE, TF_STP_VEHICLE_CLASSES  VCL, TF_STP_PERSONS PRS\r\n"
				+ "WHERE TRF.ID = BKT.TRF_ID\r\n" + "AND   TRF.PRS_ID = PRS.ID\r\n" + "AND   BKT.VLE_ID = VLE.ID\r\n"
				+ "AND   VLE.VCL_ID = VCL.ID\r\n" + "AND   TRF.PRS_ID IS NOT NULL\r\n"
				+ "AND   EXPIRY_DATE >= TRUNC(SYSDATE)+60\r\n" + "AND   TRF.ORG_ID IS NULL\r\n"
				+ "AND PRS.PASSPORT_NO IS NOT NULL\r\n" + "AND   PRS.ID IS NOT NULL\r\n"
				+ "AND   BKT.IS_MORTGAGED = 1\r\n" + "AND PLT_ID IN (SELECT plt.ID FROM TF_VHL_PLATES plt\r\n"
				+ "               WHERE PCD_ID in (select pcd.ID from tf_vhl_plate_codes pCD where plc_emi_code = 'DXB'AND pcd.plc_code = 2))\r\n"
				+ "AND VCL.ID = 3\r\n" + "AND TRS_END_DATE IS NULL\r\n" + "AND EXPIRY_DATE IS NOT NULL\r\n"
				+ "AND F_GET_TRF_NUMBER_2(BKT.TRF_ID) NOT IN(10018551,10024368, 10029566, 10011937, 10010048, 10010030)\r\n"
				// + "AND F_GET_TRF_NUMBER_2(BKT.TRF_ID) NOT LIKE '%100%'\r\n"
				+ "AND F_GET_TRF_NUMBER_2(BKT.TRF_ID) NOT LIKE '%1000%'\r\n" + "AND ROWNUM <= 20\r\n"
				+ "ORDER BY BKT.TRS_START_DATE");

		int countrow = 0;
		while (rs.next()) {
			countrow++;
			vehilce[0] = rs.getString(1);
			String[] plate = rs.getString(2).split("\\s+");

			vehilce[1] = plate[0];
			vehilce[2] = plate[2];
			vehilce[3] = rs.getString(3);
			vehilce[4] = plate[1];
			vehilce[5] = rs.getString(5);
			System.out.println("TRF_NO = " + vehilce[0] + "  --- PLATE_NO = " + vehilce[1] + "  --- Plate_Code = "
					+ vehilce[2] + " Chassis = " + vehilce[3]);
		}
		if (countrow == 0) {
			System.out.println(" There is No available vehicles in DB tables");
		}

		// closing DB Connection
		con.close();
		return vehilce;
	}

	public String[] getOwnedPlatesToChangeDesign() throws ClassNotFoundException, SQLException {
		setConnection();
		Statement stmt = con.createStatement();

		String[] vehilce = new String[6];

		ResultSet rs = stmt.executeQuery("SELECT traffic_file_vehcile_owner,\r\n" + "       PLATE_number,\r\n"
				+ "       PLATE_CODE_DESC,\r\n" + "       PLATE_CATEGORY_DESC,\r\n" + "       ex_Date\r\n"
				+ "  FROM (  SELECT PLT.ID PLT_ID,\r\n" + "                 (SELECT TRF.TRAFFIC_NO\r\n"
				+ "                    FROM tf_vhl_booklets bkt, tf_stp_traffic_files trf\r\n"
				+ "                   WHERE     TRF.ID = BKT.TRF_ID\r\n"
				+ "                         AND BKT.TRS_END_DATE IS NULL\r\n"
				+ "                         AND BKT.EXPIRY_DATE IS NOT NULL\r\n"
				+ "                         AND plt.id = bkt.plt_id AND TRF.TRAFFIC_NO not in(11600241, 10650804, 10235482, 11724009, 10451089, 12147768))\r\n"
				+ "                    traffic_file_vehcile_owner,\r\n" + "                 (SELECT BKT.expiry_date\r\n"
				+ "                    FROM tf_vhl_booklets bkt, tf_stp_traffic_files trf\r\n"
				+ "                   WHERE     TRF.ID = BKT.TRF_ID\r\n"
				+ "                         AND BKT.TRS_END_DATE IS NULL\r\n"
				+ "                         AND BKT.EXPIRY_DATE IS NOT NULL\r\n"
				+ "                         AND plt.id = bkt.plt_id\r\n"
				+ "                         AND BKT.expiry_date > TRUNC (SYSDATE))\r\n"
				+ "                    ex_Date,\r\n" + "                 PLATE_NO plate_number,\r\n"
				+ "                 PCD.DESCRIPTION_A PLATE_CODE_DESC,\r\n"
				+ "                 PLC.description_A PLATE_CATEGORY_DESC,\r\n"
				+ "                 PKG_DB_MIL_CORE_TOOLS.F_DB_GET_REF_CODE_DESC (\r\n"
				+ "                    'TF_PLATE_STATUS',\r\n" + "                    PLT.PLATE_STATUS)\r\n"
				+ "                    PLATE_STATUS,\r\n" + "                 PLT.*\r\n"
				+ "            FROM TF_VHL_PLATES PLT,\r\n" + "                 TF_SPL_PLATE_TEMPLATES SPT,\r\n"
				+ "                 TF_VHL_PLATE_CODES PCD,\r\n" + "                 TF_VHL_PLATE_CATEGORIES PLC\r\n"
				+ "           WHERE     PLT.SPT_ID = SPT.ID(+)\r\n" + "                 AND PCD.ID = PLT.PCD_ID\r\n"
				+ "                 AND PLC.CODE = PCD.PLC_CODE\r\n"
				+ "                 AND PLC.EMI_CODE = PCD.PLC_EMI_CODE\r\n"
				+ "                 AND PLC.EMI_CODE = 'DXB'\r\n" + "                 AND PLC.CODE = 2\r\n"
				+ "                 AND plt.STILL_OLD = 2\r\n" + "                 AND PLT.TRF_ID IS NOT NULL\r\n"
				+ "                 AND PLATE_STATUS = 4\r\n" + "                 AND ROWNUM < 50\r\n"
				+ "        ORDER BY ex_Date DESC)\r\n"
				+ " WHERE ex_Date IS NOT NULL AND traffic_file_vehcile_owner IS NOT NULL AND Rownum < 2");

		int countrow = 0;
		while (rs.next()) {
			countrow++;
			vehilce[0] = rs.getString(1);
			vehilce[1] = rs.getString(2);
			vehilce[2] = rs.getString(3);
			vehilce[3] = rs.getString(4);
			System.out.println("TRF_NO = " + vehilce[0] + "  --- PLATE_NO = " + vehilce[1] + "  --- Plate_Code = "
					+ vehilce[2] + "  --- PLATE_Category = " + vehilce[3]);
		}
		if (countrow == 0) {
			System.out.println(" There is No available vehicles in DB tables");
		}

		// closing DB Connection
		con.close();
		return vehilce;
	}

	public String[] getExpiredOwnedPlatesToChangeDesign() throws ClassNotFoundException, SQLException {
		setConnection();
		Statement stmt = con.createStatement();

		String[] vehilce = new String[6];

		ResultSet rs = stmt.executeQuery("SELECT traffic_file_vehcile_owner,\r\n" + "       PLATE_number,\r\n"
				+ "       PLATE_CODE_DESC,\r\n" + "       PLATE_CATEGORY_DESC,\r\n" + "       ex_Date\r\n"
				+ "  FROM (  SELECT PLT.ID PLT_ID,\r\n" + "                 (SELECT TRF.TRAFFIC_NO\r\n"
				+ "                    FROM tf_vhl_booklets bkt, tf_stp_traffic_files trf\r\n"
				+ "                   WHERE     TRF.ID = BKT.TRF_ID\r\n"
				+ "                         AND BKT.TRS_END_DATE IS NULL\r\n"
				+ "                         AND BKT.EXPIRY_DATE IS NOT NULL\r\n"
				+ "                         AND plt.id = bkt.plt_id)\r\n"
				+ "                    traffic_file_vehcile_owner,\r\n" + "                 (SELECT BKT.expiry_date\r\n"
				+ "                    FROM tf_vhl_booklets bkt, tf_stp_traffic_files trf\r\n"
				+ "                   WHERE     TRF.ID = BKT.TRF_ID\r\n"
				+ "                         AND BKT.TRS_END_DATE IS NULL\r\n"
				+ "                         AND BKT.EXPIRY_DATE IS NOT NULL\r\n"
				+ "                         AND plt.id = bkt.plt_id\r\n"
				+ "                         AND BKT.expiry_date < TRUNC (SYSDATE))\r\n"
				+ "                    ex_Date,\r\n" + "                 PLATE_NO plate_number,\r\n"
				+ "                 PCD.DESCRIPTION_A PLATE_CODE_DESC,\r\n"
				+ "                 PLC.description_A PLATE_CATEGORY_DESC,\r\n"
				+ "                 PKG_DB_MIL_CORE_TOOLS.F_DB_GET_REF_CODE_DESC (\r\n"
				+ "                    'TF_PLATE_STATUS',\r\n" + "                    PLT.PLATE_STATUS)\r\n"
				+ "                    PLATE_STATUS,\r\n" + "                 PLT.*\r\n"
				+ "            FROM TF_VHL_PLATES PLT,\r\n" + "                 TF_SPL_PLATE_TEMPLATES SPT,\r\n"
				+ "                 TF_VHL_PLATE_CODES PCD,\r\n" + "                 TF_VHL_PLATE_CATEGORIES PLC\r\n"
				+ "           WHERE     PLT.SPT_ID = SPT.ID(+)\r\n" + "                 AND PCD.ID = PLT.PCD_ID\r\n"
				+ "                 AND PLC.CODE = PCD.PLC_CODE\r\n"
				+ "                 AND PLC.EMI_CODE = PCD.PLC_EMI_CODE\r\n"
				+ "                 AND PLC.EMI_CODE = 'DXB'\r\n" + "                 AND PLC.CODE = 2\r\n"
				+ "                 AND plt.STILL_OLD = 2\r\n" + "                 AND PLT.TRF_ID IS NOT NULL\r\n"
				+ "                 AND PLATE_STATUS = 4\r\n" + "                 AND ROWNUM < 50\r\n"
				+ "        ORDER BY ex_Date DESC)\r\n" + " WHERE ex_Date IS NOT NULL AND Rownum < 2");

		int countrow = 0;
		while (rs.next()) {
			countrow++;
			vehilce[0] = rs.getString(1);
			vehilce[1] = rs.getString(2);
			vehilce[2] = rs.getString(3);
			vehilce[3] = rs.getString(4);
			System.out.println("TRF_NO = " + vehilce[0] + "  --- PLATE_NO = " + vehilce[1] + "  --- Plate_Code = "
					+ vehilce[2] + "  --- PLATE_Category = " + vehilce[3]);
		}
		if (countrow == 0) {
			System.out.println(" There is No available vehicles in DB tables");
		}

		// closing DB Connection
		con.close();
		return vehilce;
	}

	public String[] getNonOwnedPlatesToChangeDesign() throws ClassNotFoundException, SQLException {
		setConnection();
		Statement stmt = con.createStatement();

		String[] vehilce = new String[6];

		ResultSet rs = stmt.executeQuery("SELECT traffic_file_vehcile_owner,\r\n" + "       PLATE_number,\r\n"
				+ "       PLATE_CODE_DESC,\r\n" + "       PLATE_CATEGORY_DESC,\r\n" + "       ex_Date\r\n"
				+ "  FROM (  SELECT PLT.ID PLT_ID,\r\n" + "                 (SELECT TRF.TRAFFIC_NO\r\n"
				+ "                    FROM tf_vhl_booklets bkt, tf_stp_traffic_files trf\r\n"
				+ "                   WHERE     TRF.ID = BKT.TRF_ID\r\n"
				+ "                         AND BKT.TRS_END_DATE IS NULL\r\n"
				+ "                         AND BKT.EXPIRY_DATE IS NOT NULL\r\n"
				+ "                         AND plt.id = bkt.plt_id)\r\n"
				+ "                    traffic_file_vehcile_owner,\r\n" + "                 (SELECT BKT.expiry_date\r\n"
				+ "                    FROM tf_vhl_booklets bkt, tf_stp_traffic_files trf\r\n"
				+ "                   WHERE     TRF.ID = BKT.TRF_ID\r\n"
				+ "                         AND BKT.TRS_END_DATE IS NULL\r\n"
				+ "                         AND BKT.EXPIRY_DATE IS NOT NULL\r\n"
				+ "                         AND plt.id = bkt.plt_id\r\n"
				+ "                         AND BKT.expiry_date > TRUNC (SYSDATE))\r\n"
				+ "                    ex_Date,\r\n" + "                 PLATE_NO plate_number,\r\n"
				+ "                 PCD.DESCRIPTION_A PLATE_CODE_DESC,\r\n"
				+ "                 PLC.description_A PLATE_CATEGORY_DESC,\r\n"
				+ "                 PKG_DB_MIL_CORE_TOOLS.F_DB_GET_REF_CODE_DESC (\r\n"
				+ "                    'TF_PLATE_STATUS',\r\n" + "                    PLT.PLATE_STATUS)\r\n"
				+ "                    PLATE_STATUS,\r\n" + "                 PLT.*\r\n"
				+ "            FROM TF_VHL_PLATES PLT,\r\n" + "                 TF_SPL_PLATE_TEMPLATES SPT,\r\n"
				+ "                 TF_VHL_PLATE_CODES PCD,\r\n" + "                 TF_VHL_PLATE_CATEGORIES PLC\r\n"
				+ "           WHERE     PLT.SPT_ID = SPT.ID(+)\r\n" + "                 AND PCD.ID = PLT.PCD_ID\r\n"
				+ "                 AND PLC.CODE = PCD.PLC_CODE\r\n"
				+ "                 AND PLC.EMI_CODE = PCD.PLC_EMI_CODE\r\n"
				+ "                 AND PLC.EMI_CODE = 'DXB'\r\n" + "                 AND PLC.CODE = 2\r\n"
				+ "                 AND plt.STILL_OLD = 2\r\n" + "                 AND PLT.TRF_ID IS NULL\r\n"
				+ "                 AND PLATE_STATUS = 4\r\n" + "                 AND ROWNUM < 50\r\n"
				+ "        ORDER BY ex_Date DESC)\r\n" + " WHERE ex_Date IS NOT NULL AND Rownum < 2");

		int countrow = 0;
		while (rs.next()) {
			countrow++;
			vehilce[0] = rs.getString(1);
			vehilce[1] = rs.getString(2);
			vehilce[2] = rs.getString(3);
			vehilce[3] = rs.getString(4);
			System.out.println("TRF_NO = " + vehilce[0] + "  --- PLATE_NO = " + vehilce[1] + "  --- Plate_Code = "
					+ vehilce[2] + "  --- PLATE_Category = " + vehilce[3]);
		}
		if (countrow == 0) {
			System.out.println(" There is No available vehicles in DB tables");
		}

		// closing DB Connection
		con.close();
		return vehilce;
	}

	public String[] getLatestVehicleAdded(String trafficFile) throws ClassNotFoundException, SQLException {
		setConnection();
		Statement stmt = con.createStatement();

		String[] vehilce = new String[4];

		ResultSet rs = stmt.executeQuery(
				"SELECT * FROM (SELECT F_GET_TRF_NUMBER_2 (BKT.TRF_ID) TRF_NO, F_DB_GET_PLATE_DETAILS (BKT.PLT_ID) AS PLATE FROM TF_VHL_BOOKLETS BKT WHERE BKT.TRF_ID = (SELECT ID FROM TF_STP_TRAFFIC_FILES WHERE TRAFFIC_NO = 50149189) ORDER BY BKT.TRS_START_DATE DESC)\r\n"
						+ "WHERE ROWNUM <= 1");

		int countrow = 0;
		while (rs.next()) {
			countrow++;
			vehilce[0] = rs.getString(1);
			String[] plate = rs.getString(2).split("\\s+");

			vehilce[1] = plate[0];
			vehilce[2] = plate[2];
			vehilce[3] = plate[1];
			System.out.println(" TRF_NO =     " + vehilce[0] + "  --- PLATE_NO =     " + vehilce[1]
					+ "  --- Plate_Code =     " + vehilce[2] + " PlateCategory =     " + vehilce[3]);
		}
		if (countrow == 0) {
			System.out.println(" There is No available vehicles in DB tables");
		}

		// closing DB Connection
		con.close();
		return vehilce;
	}

	public String[] getVehicle(VehicleClass vehicleClass, PlateCategory plateCategory, VehicleWeight vehicleWeight)
			throws ClassNotFoundException, SQLException {
		setConnection();
		Statement stmt = con.createStatement();

		String vehicleClassId = getVehicleClassId(vehicleClass);
		String plateCategoryId = getPlateCategoryId(plateCategory);
		int[] weightIntervals = new int[2];
		weightIntervals = getWeightIntervals(vehicleWeight);
		String[] vehilce = new String[7];
		ResultSet rs = stmt.executeQuery("SELECT F_GET_TRF_NUMBER_2 (BKT.TRF_ID) TRF_NO,\r\n"
				+ "         F_DB_GET_PLATE_DETAILS (BKT.PLT_ID) AS PLATE,\r\n" + "         BKT.CHASISS_NO,\r\n"
				+ "         PRS.PASSPORT_NO,\r\n" + "         BKT.WEIGHT,\r\n" + "         VCL.ID\r\n"
				+ "    FROM TF_VHL_BOOKLETS BKT,\r\n" + "         TF_STP_TRAFFIC_FILES TRF,\r\n"
				+ "         TF_VHL_VEHICLES VLE,\r\n" + "         TF_STP_VEHICLE_CLASSES VCL,\r\n"
				+ "         TF_STP_PERSONS PRS\r\n" + "   WHERE     TRF.ID = BKT.TRF_ID\r\n"
				+ "         AND TRF.PRS_ID = PRS.ID\r\n" + "         AND BKT.VLE_ID = VLE.ID\r\n"
				+ "         AND VLE.VCL_ID = VCL.ID\r\n" + "         AND TRF.PRS_ID IS NOT NULL\r\n"
				+ "         AND EXPIRY_DATE >= TRUNC (SYSDATE)+120\r\n" + "         AND TRF.ORG_ID IS NULL\r\n"
				+ "         AND PRS.PASSPORT_NO IS NOT NULL\r\n" + "         AND PRS.ID IS NOT NULL\r\n"
				+ "         AND BKT.IS_MORTGAGED = 1\r\n" + "         AND PLT_ID IN\r\n"
				+ "                (SELECT plt.ID FROM TF_VHL_PLATES plt WHERE PCD_ID IN(SELECT pcd.ID FROM tf_vhl_plate_codes pCD WHERE plc_emi_code = 'DXB' AND pcd.plc_code = "
				+ plateCategoryId + "))\r\n" + "         AND VCL.ID = " + vehicleClassId + "\r\n"
				+ "         AND TRS_END_DATE IS NULL\r\n" + "         AND EXPIRY_DATE IS NOT NULL\r\n"
				+ "         AND F_GET_TRF_NUMBER_2 (BKT.TRF_ID) NOT IN (10184041, 10020699, 10024368, 10018551, 10010048, 10010030)\r\n"
				+ "         AND F_GET_TRF_NUMBER_2 (BKT.TRF_ID) NOT LIKE '%1000%'\r\n"
				+ "         AND BKT.WEIGHT BETWEEN " + weightIntervals[0] + " AND " + weightIntervals[1] + "\r\n"
				+ "         AND ROWNUM < 13");

		int countrow = 0;
		while (rs.next()) {
			countrow++;
			vehilce[0] = rs.getString(1);
			String[] plate = rs.getString(2).split("\\s+");

			vehilce[1] = plate[0];
			vehilce[2] = plate[2];
			vehilce[3] = plate[1];
			vehilce[4] = rs.getString(3);
			vehilce[5] = rs.getString(5);
			vehilce[6] = rs.getString(4);
			System.out.println(" TRF_NO = " + vehilce[0] + "  --- PLATE_NO = " + vehilce[1] + "  --- Plate_Code = "
					+ vehilce[2] + "  --- PLATE_Cat = " + vehilce[3] + " Chassis =     " + vehilce[4] + " weight = "
					+ vehilce[5]);
		}
		if (countrow == 0) {
			System.out.println(" There is No available vehicles in DB tables");
		}

		// closing DB Connection
		con.close();
		return vehilce;
	}

	public String[] getVehicleOfTRFHasPreservedPlate() throws ClassNotFoundException, SQLException {
		setConnection();
		Statement stmt = con.createStatement();

		String[] vehilce = new String[6];
		ResultSet rs = stmt.executeQuery("SELECT F_GET_TRF_NUMBER_2(BKT.TRF_ID)TRF_NO,\r\n"
				+ "       F_DB_GET_PLATE_DETAILS(BKT.PLT_ID) AS PLATE,\r\n" + "       BKT.CHASISS_NO,\r\n"
				+ "       BKT.WEIGHT\r\n" + "FROM TF_VHL_BOOKLETS BKT\r\n" + "Where TRS_END_DATE IS NULL \r\n"
				+ "AND F_DB_GET_PLATE_DETAILS(BKT.PLT_ID) is not null\r\n"
				+ "AND BKT.TRF_ID in(SELECT prp.TRF_ID As Trf_NO\r\n" + "FROM 	TF_VHL_PRESERVED_PLATES PRP,\r\n"
				+ "		tf_vhl_plates plt\r\n" + "Where    plt.id = prp.plt_id\r\n" + "and 	 prp.status = 2\r\n"
				+ "and      plt.plate_status = 7\r\n" + "and      plt.trf_id is null\r\n"
				+ "and		 EXPIRY_DATE > trunc(sysdate)\r\n" + "and 	 PLT_ID IN (SELECT plt.ID\r\n"
				+ "					FROM TF_VHL_PLATES plt\r\n" + "					WHERE PCD_ID in (select pcd.ID\r\n"
				+ "									from tf_vhl_plate_codes pCD\r\n"
				+ "									where plc_emi_code = 'DXB'AND pcd.plc_code = 2))\r\n"
				+ "and		exists (select 1 from tf_stp_traffic_files trf , tf_stp_persons prs\r\n"
				+ "				where 	trf.prs_id = prs.id\r\n" + "				and		prp.trf_id = trf.id\r\n"
				+ "				and		prs.is_vip = 1\r\n" + "				)\r\n" + "and	rownum < 10)\r\n"
				+ "and rownum<3");

		int countrow = 0;
		while (rs.next()) {
			countrow++;
			vehilce[0] = rs.getString(1);
			String[] plate = rs.getString(2).split("\\s+");

			vehilce[1] = plate[0];
			vehilce[2] = plate[2];
			vehilce[3] = plate[1];
			vehilce[4] = rs.getString(3);
			vehilce[5] = rs.getString(4);
			System.out.println(" TRF_NO =     " + vehilce[0] + "  --- PLATE_NO =     " + vehilce[1]
					+ "  --- Plate_Code =     " + vehilce[2] + "  --- PLATE_Cat =     " + vehilce[3] + " Chassis =     "
					+ vehilce[4] + " weight =     " + vehilce[5]);
		}
		if (countrow == 0) {
			System.out
					.println(" There is No available vehicles for a traffic files have Preserved Plates in DB tables");
		}

		// closing DB Connection
		con.close();
		return vehilce;
	}

	public String getLicenseNumber(String trafficFile) throws ClassNotFoundException, SQLException {
		setConnection();
		Statement stmt = con.createStatement();
		String licenceNumber = "";
		ResultSet rs = stmt.executeQuery(
				"SELECT DRL.LICENSE_NO FROM TF_DRL_DRIVING_LICENSES DRL WHERE TRF_ID = (SELECT TRF.ID FROM TF_STP_TRAFFIC_FILES TRF WHERE TRF.TRAFFIC_NO = "
						+ trafficFile + ")");

		int countrow = 0;
		while (rs.next()) {
			countrow++;
			licenceNumber = rs.getString(1);
			System.out.println("licenceNumber =     " + licenceNumber);
		}
		if (countrow == 0) {
			System.out.println(" This trafficfile (" + trafficFile + ") has no License");
		}

		// closing DB Connection
		con.close();
		return licenceNumber;
	}

	public String[] getElectricVehicleByTrf(String trafficFile, VehicleWeight vehicleWeight)
			throws ClassNotFoundException, SQLException {
		setConnection();
		Statement stmt = con.createStatement();

		int[] weightIntervals = new int[2];
		weightIntervals = getWeightIntervals(vehicleWeight);

		String[] vehilce = new String[6];
		ResultSet rs = stmt.executeQuery("SELECT F_GET_TRF_NUMBER_2(BKT.TRF_ID)TRF_NO,\r\n"
				+ "       F_DB_GET_PLATE_DETAILS(BKT.PLT_ID) AS PLATE,\r\n" + "       BKT.CHASISS_NO,\r\n"
				+ "       WEIGHT\r\n"
				+ "FROM TF_VHL_BOOKLETS BKT, TF_VHL_VEHICLES VHL, CG_REF_CODES CGC, TF_STP_TRAFFIC_FILES TRF\r\n"
				+ "Where BKT.VLE_ID =  VHL.ID \r\n" + "AND VHL.FUEL = CGC.RV_LOW_VALUE\r\n"
				+ "AND BKT.TRF_ID = TRF.ID\r\n" + "AND TRF.TRAFFIC_NO = " + trafficFile + "\r\n"
				+ "AND BKT.WEIGHT BETWEEN " + weightIntervals[0] + " AND " + weightIntervals[1] + "\r\n"
				+ "AND BKT.EXPIRY_DATE > TRUNC(SYSDATE)+90\r\n" + "AND CGC.RV_DOMAIN LIKE 'TF_FUEL'\r\n"
				+ "AND CGC.RV_ABBREVIATION LIKE '%Battery-electric motor%'");

		int countrow = 0;
		while (rs.next()) {
			countrow++;
			vehilce[0] = rs.getString(1);
			String[] plate = rs.getString(2).split("\\s+");

			vehilce[1] = plate[0];
			vehilce[2] = plate[2];
			vehilce[3] = plate[1];
			vehilce[4] = rs.getString(3);
			vehilce[5] = rs.getString(4);
			System.out.println(" TRF_NO =     " + vehilce[0] + "  --- PLATE_NO =     " + vehilce[1]
					+ "  --- Plate_Code =     " + vehilce[2] + "  --- PLATE_Cat =     " + vehilce[3] + " Chassis =     "
					+ vehilce[4] + " weight =     " + vehilce[5]);
		}
		if (countrow == 0) {
			System.out.println(" There is No electric vehicles for trafficFile (" + trafficFile + ")");
		}

		// closing DB Connection
		con.close();
		return vehilce;
	}

	public String[] getExpiredVehicle(VehicleClass vehicleClass, VehicleWeight vehicleWeight,
			PlateCategory plateCategory, boolean isOrganization) throws ClassNotFoundException, SQLException {
		String vehicleClassId = getVehicleClassId(vehicleClass);
		int[] weight = new int[2];
		weight = getWeightIntervals(vehicleWeight);
		String plateCategoryId = getPlateCategoryId(plateCategory);
		setConnection();
		String organizationFlag, unwantedTrafficFiles, personFlag, noOfDays;
		if (isOrganization) {
			organizationFlag = "IS NOT NULL";
			unwantedTrafficFiles = "(10023080, 10003535)";
			noOfDays = "0";
			personFlag = "IS NULL";
		} else {
			organizationFlag = "IS NULL";
			unwantedTrafficFiles = "(Select TRF_ID FROM TF_VHL_BOOKLETS Where EXPIRY_DATE Between TRUNC(SYSDATE) AND "
					+ "(SYSDATE)-90)";
			noOfDays = "90";
			personFlag = "IS NOT NULL";
		}

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 1);
		String newDate = sdf.format(cal.getTime());
		System.out.println("Date after Addition: " + newDate);

		Statement stmt = con.createStatement();

		String[] vehilce = new String[7];
		ResultSet rs = stmt.executeQuery("SELECT F_GET_TRF_NUMBER_2(BKT.TRF_ID)TRF_NO,\r\n"
				+ "       F_DB_GET_PLATE_DETAILS(BKT.PLT_ID) AS PLATE,\r\n" + "       BKT.CHASISS_NO,\r\n"
				+ "       BKT.WEIGHT,\r\n" + "       BKT.EXPIRY_DATE\r\n"
				+ "FROM TF_VHL_BOOKLETS BKT, TF_STP_TRAFFIC_FILES TRF, TF_VHL_VEHICLES VLE, TF_STP_VEHICLE_CLASSES  VCL\r\n"
				+ "WHERE TRF.ID = BKT.TRF_ID\r\n" + "AND BKT.VLE_ID = VLE.ID\r\n" + "AND VLE.VCL_ID = VCL.ID\r\n"
				+ "AND TRF.PRS_ID " + personFlag + "\r\n" + "AND EXPIRY_DATE <= TRUNC(SYSDATE)-" + noOfDays + "\r\n"
				+ "AND TRF.ORG_ID " + organizationFlag + "\r\n"
				+ "AND PLT_ID IN (SELECT PLT.ID FROM TF_VHL_PLATES PLT WHERE PCD_ID in (Select PCD.ID FROM tf_vhl_plate_codes PCD WHERE plc_emi_code = 'DXB'AND PCD.plc_code = "
				+ plateCategoryId + "))\r\n" + "AND VCL.ID = " + vehicleClassId + "\r\n"
				+ "AND TRS_END_DATE IS NULL \r\n" + "AND EXPIRY_DATE IS NOT NULL\r\n" + "AND BKT.WEIGHT BETWEEN "
				+ weight[0] + " AND " + weight[1] + "\r\n"
				+ "AND F_GET_TRF_NUMBER_2(BKT.TRF_ID) NOT IN (10014439, 10024856, 10023080, 10025747, 10029951, 10001354,10001096, 10012944, 10016102, 50025748, 10026489, 10024368, 10003535)\r\n"
				+ "AND F_GET_TRF_NUMBER_2(BKT.TRF_ID) NOT LIKE '%10000%'\r\n" + "AND BKT.TRF_ID NOT IN "
				+ unwantedTrafficFiles + "\r\n" + "AND ROWNUM <= 23");

		int countrow = 0;
		while (rs.next()) {
			countrow++;
			vehilce[0] = rs.getString(1);
			String[] plate = rs.getString(2).split("\\s+");

			vehilce[1] = plate[0];
			if (plateCategoryId.equals("2") || plateCategoryId.equals("1")) {
				vehilce[2] = plate[2];
				vehilce[3] = plate[1];
			}
			// else if(plateCategoryId.equals("1"))
			// { vehilce[2] = plate[2]; vehilce[3] = plate[1];}
			else {
				vehilce[2] = plate[1];
				vehilce[3] = plate[1];
			}
			vehilce[4] = rs.getString(3);
			vehilce[5] = rs.getString(4);
			vehilce[6] = rs.getString(5);
			System.out.println(" TRF_NO = " + vehilce[0] + " --- PLATE_NO = " + vehilce[1] + " --- Plate_Code = "
					+ vehilce[2] + " --- PlateCategory = " + vehilce[3] + " --- Chassis = " + vehilce[4]
					+ " --- Weight = " + vehilce[5]);
		}
		if (countrow == 0) {
			System.out.println(" There is No available expired vehicles in DB tables.");
		}

		// closing DB Connection
		con.close();
		return vehilce;
	}

	public String[] getVehicle(VehicleClass vehicleClass, VehicleWeight vehicleWeight, PlateCategory plateCategory,
			boolean isOrganization) throws ClassNotFoundException, SQLException {
		String vehicleClassId = getVehicleClassId(vehicleClass);
		int[] weight = new int[2];
		weight = getWeightIntervals(vehicleWeight);
		String plateCategoryId = getPlateCategoryId(plateCategory);
		setConnection();
		String organizationFlag, unwantedTrafficFiles, personFlag;
		if (isOrganization) {
			organizationFlag = "IS NOT NULL";
			unwantedTrafficFiles = "(10004649, 10001047, 10010088, 10011962, 50000387, 50015809, 10003535)";
			personFlag = "IS NULL";
		} else {
			organizationFlag = "IS NULL";
			unwantedTrafficFiles = "(Select TRF_ID FROM TF_VHL_BOOKLETS Where EXPIRY_DATE Between TRUNC(SYSDATE) AND TRUNC(SYSDATE)-90)";
			personFlag = "IS NOT NULL";
		}

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 1);
		String newDate = sdf.format(cal.getTime());
		System.out.println("Date after Addition: " + newDate);

		Statement stmt = con.createStatement();

		String[] vehilce = new String[7];
		ResultSet rs = stmt.executeQuery("SELECT F_GET_TRF_NUMBER_2(BKT.TRF_ID)TRF_NO,\r\n"
				+ "       F_DB_GET_PLATE_DETAILS(BKT.PLT_ID) AS PLATE,\r\n" + "       BKT.CHASISS_NO,\r\n"
				+ "       BKT.WEIGHT,\r\n" + "       BKT.EXPIRY_DATE\r\n"
				+ "FROM TF_VHL_BOOKLETS BKT, TF_STP_TRAFFIC_FILES TRF, TF_VHL_VEHICLES VLE, TF_STP_VEHICLE_CLASSES  VCL\r\n"
				+ "WHERE TRF.ID = BKT.TRF_ID\r\n" + "AND BKT.VLE_ID = VLE.ID\r\n" + "AND VLE.VCL_ID = VCL.ID\r\n"
				+ "AND TRF.PRS_ID " + personFlag + "\r\n" + "AND EXPIRY_DATE >= TRUNC(SYSDATE)\r\n" + "AND TRF.ORG_ID "
				+ organizationFlag + "\r\n"
				+ "AND PLT_ID IN (SELECT PLT.ID FROM TF_VHL_PLATES PLT WHERE PCD_ID in (Select PCD.ID FROM tf_vhl_plate_codes PCD WHERE plc_emi_code = 'DXB'AND PCD.plc_code = "
				+ plateCategoryId + "))\r\n" + "AND VCL.ID = " + vehicleClassId + "\r\n"
				+ "AND TRS_END_DATE IS NULL \r\n" + "AND EXPIRY_DATE IS NOT NULL\r\n" + "AND BKT.WEIGHT BETWEEN "
				+ weight[0] + " AND " + weight[1] + "\r\n"
				+ "AND   BKT.IS_MORTGAGED = 1\r\n"
				+ "AND F_GET_TRF_NUMBER_2(BKT.TRF_ID) NOT IN (10026148, 10018551,  10003535)\r\n" //10001821, 10001047, 10013648, 10011962, 10001370, 10001354, 10001151, 10001096,
				+ "AND F_GET_TRF_NUMBER_2(BKT.TRF_ID) NOT LIKE '%10000%'\r\n" + "AND BKT.TRF_ID NOT IN "
				+ unwantedTrafficFiles + "\r\n" + "AND ROWNUM < 9");

		int countrow = 0;
		while (rs.next()) {
			countrow++;
			vehilce[0] = rs.getString(1);
			String[] plate = rs.getString(2).split("\\s+");

			vehilce[1] = plate[0];
			if (plateCategoryId.equals("2") || plateCategoryId.equals("1")) {
				vehilce[2] = plate[2];
				vehilce[3] = plate[1];
			}
			// else if(plateCategoryId.equals("1"))
			// { vehilce[2] = plate[2]; vehilce[3] = plate[1];}
			else {
				vehilce[2] = plate[1];
				vehilce[3] = plate[1];
			}
			vehilce[4] = rs.getString(3);
			vehilce[5] = rs.getString(4);
			vehilce[6] = rs.getString(5);
			System.out.println(" TRF_NO = " + vehilce[0] + "  --- PLATE_NO = " + vehilce[1] + "  --- Plate_Code = "
					+ vehilce[2] + " PlateCategory = " + vehilce[3] + " Chassis = " + vehilce[4] + " Weight = "
					+ vehilce[5]);
		}
		if (countrow == 0) {
			System.out.println(" There is No available expired vehicles in DB tables.");
		}

		// closing DB Connection
		con.close();
		return vehilce;
	}

	public String[][] getExpiredVehiclesByTrafficFile(String trafficFile) throws ClassNotFoundException, SQLException {

		setConnection();
		Statement stmt = con.createStatement();

		String[][] vehilce = new String[2][6];
		ResultSet rs = stmt.executeQuery("SELECT F_GET_TRF_NUMBER_2(BKT.TRF_ID)TRF_NO,\r\n"
				+ "       F_DB_GET_PLATE_DETAILS(BKT.PLT_ID) AS PLATE,\r\n" + "       BKT.CHASISS_NO,\r\n"
				+ "       BKT.WEIGHT\r\n"
				+ "FROM Traffic.TF_VHL_BOOKLETS BKT, Traffic.TF_STP_TRAFFIC_FILES TRF, Traffic.TF_VHL_VEHICLES VLE, Traffic.TF_STP_VEHICLE_CLASSES  VCL\r\n"
				+ "WHERE TRF.ID = BKT.TRF_ID\r\n" + "AND BKT.VLE_ID = VLE.ID\r\n" + "AND VLE.VCL_ID = VCL.ID\r\n"
				+ "AND EXPIRY_DATE <= TRUNC(SYSDATE)\r\n"
				+ "AND PLT_ID IN (SELECT PLT.ID FROM TF_VHL_PLATES PLT WHERE PCD_ID in (Select PCD.ID FROM tf_vhl_plate_codes PCD WHERE plc_emi_code = 'DXB'AND PCD.plc_code = 2))\r\n"
				+ "AND VCL.id = 3\r\n" //+ "AND TRS_END_DATE IS NOT NULL \r\n" 
				+ "AND F_GET_TRF_NUMBER_2(BKT.TRF_ID) = "
				+ trafficFile);

		int countrow = 0;
		while (rs.next() && countrow < 2) {
			vehilce[countrow][0] = rs.getString(1);
			String[] plate = rs.getString(2).split("\\s+");

			vehilce[countrow][1] = plate[0];
			vehilce[countrow][2] = plate[2];
			vehilce[countrow][3] = plate[1];
			vehilce[countrow][4] = rs.getString(3);
			try {
				if (rs.getString(4).isEmpty() || rs.getString(4).equals("0"))
					vehilce[countrow][5] = "3500";
				else
					vehilce[countrow][5] = rs.getString(4);
			} catch (java.lang.NullPointerException e) {
				vehilce[countrow][5] = "3500";
			}

			System.out.println(" TRF_NO = " + vehilce[countrow][0] + "  --- PLATE_NO = " + vehilce[countrow][1]
					+ "  --- Plate_Code = " + vehilce[countrow][2] + " PlateCategory = " + vehilce[countrow][3]
					+ " Chassis = " + vehilce[countrow][4] + " Weight = " + vehilce[countrow][5]);
			countrow++;
		}
		if (countrow == 0) {
			System.out.println(" There is No available expired vehicles in DB tables.");
		}

		// closing DB Connection
		con.close();
		return vehilce;
	}

	public String getTrafficFileHas2ExpiredVehicles() throws ClassNotFoundException, SQLException {

		setConnection();
		Statement stmt = con.createStatement();

		ResultSet rs = stmt.executeQuery("Select TRF_NO, count(CHASISS_NO) from\r\n"
				+ " (SELECT F_GET_TRF_NUMBER_2(BKT.TRF_ID)TRF_NO,\r\n"
				+ "       F_DB_GET_PLATE_DETAILS(BKT.PLT_ID) AS PLATE,\r\n" + "       BKT.CHASISS_NO,\r\n"
				+ "       EXPIRY_DATE,\r\n" + "       BKT.WEIGHT\r\n"
				+ "FROM TRAFFIC.TF_VHL_BOOKLETS BKT, TRAFFIC.TF_STP_TRAFFIC_FILES TRF, TRAFFIC.TF_VHL_VEHICLES VLE, TRAFFIC.TF_STP_VEHICLE_CLASSES  VCL\r\n"
				+ "WHERE TRF.ID = BKT.TRF_ID\r\n" + "AND BKT.VLE_ID = VLE.ID\r\n" + "AND VLE.VCL_ID = VCL.ID\r\n"
				+ "AND TRF.PRS_ID IS NOT NULL\r\n" + "AND EXPIRY_DATE <= TRUNC(SYSDATE)\r\n"
				+ "AND TRF.ORG_ID IS NULL\r\n"
				+ "AND PLT_ID IN (SELECT PLT.ID FROM TF_VHL_PLATES PLT WHERE PCD_ID in (Select PCD.ID FROM tf_vhl_plate_codes PCD WHERE plc_emi_code = 'DXB'AND PCD.plc_code = 2))\r\n"
				+ "AND VCL.id = 3\r\n" + "AND TRS_END_DATE IS NULL \r\n" + "AND EXPIRY_DATE IS NOT NULL\r\n"
				+ "AND F_GET_TRF_NUMBER_2(BKT.TRF_ID) NOT IN (10003535)\r\n"
				+ "AND F_GET_TRF_NUMBER_2(BKT.TRF_ID) NOT LIKE '%1000%'\r\n" + "AND ROWNUM <= 100\r\n"
				+ "order by F_GET_TRF_NUMBER_2(BKT.TRF_ID))\r\n" + "group by TRF_NO\r\n"
				+ "having count(CHASISS_NO) > 1");

		String trafficFile = "";
		int countrow = 0;
		while (rs.next() && countrow < 1) {
			trafficFile = rs.getString(1);
			System.out.println(" trafficFile has more than 1 Expired Vehicles = " + trafficFile);
			countrow++;
		}
		if (countrow == 0) {
			System.out.println(" There is No available expired vehicles in DB tables.");
		}

		con.close();
		return trafficFile;
	}

	public String getTrafficFileHasPreservedPlates() throws ClassNotFoundException, SQLException {

		setConnection();
		Statement stmt = con.createStatement();

		ResultSet rs = stmt.executeQuery("SELECT F_GET_TRF_NUMBER_2(prp.TRF_ID) As Trf_NO,\r\n"
				+ "       F_DB_GET_PLATE_DETAILS(PLT_ID) AS PLATE\r\n" + "FROM 	TF_VHL_PRESERVED_PLATES PRP,\r\n"
				+ "		tf_vhl_plates plt\r\n" + "Where    plt.id = prp.plt_id\r\n" + "and 	 prp.status = 2\r\n"
				+ "and      plt.plate_status = 7\r\n" + "and      plt.trf_id is null\r\n"
				+ "and		 EXPIRY_DATE > trunc(sysdate)\r\n" + "and 	 PLT_ID IN (SELECT plt.ID\r\n"
				+ "					FROM TF_VHL_PLATES plt\r\n" + "					WHERE PCD_ID in (select pcd.ID\r\n"
				+ "									from tf_vhl_plate_codes pCD\r\n"
				+ "									where plc_emi_code = 'DXB'AND pcd.plc_code = 2))\r\n"
				+ "and		exists (select 1 from tf_stp_traffic_files trf , tf_stp_persons prs\r\n"
				+ "				where 	trf.prs_id = prs.id\r\n" + "				and		prp.trf_id = trf.id\r\n"
				+ "				and		prs.is_vip = 1)\r\n" + "and	rownum < 2");

		String trafficFile = "";
		int countrow = 0;
		if (rs.next() && countrow < 2) {
			trafficFile = rs.getString(1);
			System.out.println(" trafficFile has Preserved Plates = " + trafficFile);
			countrow++;
		} else {
			System.out.println(" There is No available trafficFile has Preserved Plates in DB tables.");
		}

		con.close();
		return trafficFile;
	}

	public void removeBlocker(String trafficFile) throws ClassNotFoundException, SQLException {

		setConnection();
		Statement stmt = con.createStatement();

		stmt.execute("\r\n" + "declare\r\n" + "\r\n" + "      v_traffic_No number:= " + trafficFile
				+ ";  --- CHANGE TRAFFIC fILE NO\r\n"
				+ "      V_USR_NAME varchar2(20):='esam'; --- CHAMGE USER_NAME\r\n" + "\r\n" + "\r\n"
				+ "      V_TRF_ID number;\r\n" + "      V_ELS_id	number;\r\n" + "      V_PRS_ID	number;\r\n"
				+ "      V_ORG_ID	number;\r\n" + "\r\n" + "\r\n" + "\r\n" + "\r\n" + "\r\n" + "begin\r\n" + "\r\n"
				+ "select 	id,PRS_ID,ORG_ID\r\n" + "into    V_TRF_ID,V_PRS_ID,V_ORG_ID\r\n"
				+ "from 	traffic.tf_stp_traffic_files\r\n" + "where 	traffic_no = v_traffic_No ;\r\n" + "\r\n"
				+ "\r\n" + "\r\n" + "\r\n" + "\r\n" + "\r\n"
				+ " for i in (select * from TF_STP_SERVICES where sys_code ='03' and  IS_VISIBILE = 2)  loop\r\n"
				+ "		select 	ELS_SEQ.NEXTVAL\r\n" + "		into    V_ELS_id\r\n" + "		from dual;\r\n" + "\r\n"
				+ "\r\n" + "\r\n" + "		insert into traffic.TF_STP_ELECTRONIC_SEGNITURES\r\n"
				+ "		( ID,NO_OF_TRANSACTIONS,SIGN_DATE,BR_TRS012_MER,BR_TRS014_MER,BR_BTR003_MER,BR_TRS017_MER,\r\n"
				+ "		  BR_TRS018_MER,NOT_RESIDENT,BOOKLETHASTICKETS,BR_LCL003_MER,TRF_ID,SVC_CODE,EMI_CODE,\r\n"
				+ "			EXEMPTION,CREATED_BY,CREATION_DATE,UPDATED_BY,UPDATE_DATE,BR_COS007_MER,BR_TRS013_MER,\r\n"
				+ "			BR_TRS046_MER,BR_COS024_MER,SPL_047_013,BR_TRS050_MER,BKTHASSALIKTICKETS,BR_TRS051_MER,\r\n"
				+ "			SPL_051_012,SPL_051_011,REASON,SALIK_OBJECTION_NO,TRS_ID,BR_ETC022_MER,BR_RLC009_MER,\r\n"
				+ "			SPL_047_037,SPL_050_009,SPL_047_039,BR_BKT049_MER,BR_DLC033_MER,BR_APP032_MER,\r\n"
				+ "			BR_BKT050_ATT,LICENSE_COLORBLIND_TEST,BR_APP002_MER,BR_APP033_MER,SKIP_FEES_BR_TRS012_MER,\r\n"
				+ "			SKIP_FEES_BR_COS007_MER,SKIP_FEES_BOOKLETHASTICKETS,SKIP_FEES_BR_TRS050_MER,\r\n"
				+ "			SKIP_FEES_BKTHASSALIKTICKETS ,\r\n"
				+ "			BR_TRS088_MER, BR_TRS032_MER,BR_TRS0128_MER,DELEGATION_MANUAL_ENTRY,STATUS,\r\n"
				+ "			PRO_MANUAL_ENTRY,CAP_ID,BR_APP010_MER\r\n" + "			)\r\n" + "\r\n"
				+ "			values (V_ELS_id,50,trunc(SYSDATE),1,2,1,2,\r\n"
				+ "		  	2,2,1,1,V_TRF_ID,i.code,'DXB',\r\n"
				+ "			NULL,V_USR_NAME,SYSDATE,V_USR_NAME,SYSDATE,1,2,\r\n" + "			2,2,2,2,1,2,\r\n"
				+ "			2,2,'TEST55555555555555555',NULL,NULL,2,2,\r\n" + "			2,2,2,2,1,1,\r\n"
				+ "			2,1,1,1,1,\r\n" + "			1,1,1,\r\n" + "			1,\r\n" + "			2, 2,2,1,2,\r\n"
				+ "			1,null,null);\r\n" + "\r\n"
				+ "	for j in (select * from TRAFFIC.TF_STP_BENEFICIARIES where IS_ISSUING_VIOLATIONS = 2)  loop\r\n"
				+ "\r\n"
				+ "		insert into  TRAFFIC.TF_STP_ELS_BENIFICIARIES (ID,EBF_ID,ELS_ID,BR_TRS012_MER,BR_TRS012_MER_USER,BKT_TICKETS,\r\n"
				+ "				BKT_TICKETS_USER,BR_COS007_MER,BR_COS007_MER_USER,SKIP_FEES_BR_TRS012_MER,\r\n"
				+ "				SKIP_FEES_BR_TRS012_MER_USER,SKIP_FEES_BKT_TICKETS,SKIP_FEES_BKT_TICKETS_USER)\r\n"
				+ "\r\n" + "		values (SEB_SEQ.NEXTVAL,J.ID,V_ELS_id,2,V_USR_NAME,2,\r\n"
				+ "				V_USR_NAME,2,V_USR_NAME,2,\r\n" + "				V_USR_NAME,2,V_USR_NAME);\r\n" + "\r\n"
				+ "\r\n" + "\r\n" + "	end loop;\r\n" + "\r\n" + "\r\n" + "  end loop;\r\n" + "\r\n" + "\r\n"
				+ "  UPDATE 	TRAFFIC.TF_FFU_VEHICLE_CONFISCATIONS\r\n" + "  SET		BOOKING_STATUS = 5\r\n" + "\r\n"
				+ "  WHERE		BOOKING_STATUS IN (1,2)\r\n" + "  AND		TRF_ID = V_TRF_ID ;\r\n" + "\r\n"
				+ "  for i in (select * from TRAFFIC.tf_stp_vehicle_classes where is_vhl = 2 and id not in (1,2,3))   loop\r\n"
				+ "		    for j in (select * from tf_vhl_plate_categories  where emi_code = 'DXB'\r\n"
				+ "								and code not in (12,11,10,9,8,13,20,19,20,21,22,23,24,25,26,27))loop\r\n"
				+ "\r\n"
				+ "			insert into TRAFFIC.TF_VHL_TRAFFIC_AGREEMENTS (ID,PLC_EMI_CODE,VCL_ID,PLC_CODE,REF_NO,REQUESTED_NO,AGREED_NO ,\r\n"
				+ "															REQUEST_DATE,AGREEMENT_DATE,STATUS,STATUS_DATE,PRS_ID,ORG_ID,\r\n"
				+ "															CREATION_DATE ,	UPDATE_DATE,CREATED_BY,UPDATED_BY,VDS_ID,\r\n"
				+ "															EXPIRY_DATE\r\n"
				+ "															)\r\n"
				+ "			values											(TAG_seq.nextval,'DXB',i.id,J.CODE,NULL,100,100,\r\n"
				+ "															NULL,SYSDATE,2,SYSDATE, V_PRS_ID,V_org_ID,\r\n"
				+ "															SYSDATE ,SYSDATE,V_USR_NAME,V_USR_NAME,NULL,\r\n"
				+ "															TO_DATE('20/8/2018','DD/MM/YYYY')\r\n"
				+ "\r\n" + "															)  ;\r\n" + "\r\n"
				+ "	    	end loop;\r\n" + "	  end loop;\r\n" + "\r\n" + "\r\n"
				+ "  IF	V_PRS_ID IS NOT NULL THEN\r\n" + "\r\n" + "  	UPDATE TF_STP_PERSONS PRS\r\n"
				+ "  	SET	PHONE 						=	'042510302',\r\n"
				+ "		EMAIL 						=   'support@isoft.ae',\r\n"
				+ "		PP_EXPIRY_DATE				=   TO_DATE('20/8/2018','DD/MM/YYYY'),\r\n"
				+ "		CTY_CODE_P_O_BOX_IN			=   '03',\r\n"
				+ "		P_O_BOX             		=   '88888',\r\n"
				+ "		MOBILE                      =   '971509558138',\r\n"
				+ "		PASSPORT_NO					=   '752618' ,\r\n"
				+ "		PP_ISSUE_PLACE				=   'ÇáÇÑÏä' ,\r\n"
				+ "		CTY_CODE_VISA				=  	'03',\r\n"
				+ "		CID_ID						=   '784-1980-1728021-9',\r\n"
				+ "		CID_EXPIRY_DATE				=	TO_DATE('20/8/2018','DD/MM/YYYY'),\r\n"
				+ "		CONTACT_DATA_UPDATE_DATE	=   SYSDATE ,\r\n"
				+ "		RESIDENCY_NO				=   '2222225556',\r\n"
				+ "		RES_EXPIRY_DATE             =   TO_DATE('20/8/2018','DD/MM/YYYY')\r\n" + "\r\n"
				+ "	WHERE ID = V_PRS_ID ;\r\n" + "\r\n" + "	update tf_stp_circular_notes\r\n" + "	set	status = 1\r\n"
				+ "	where prs_id = V_PRS_ID\r\n" + "	and status = 2 ;\r\n" + "\r\n"
				+ "	update tf_stp_circular_notes\r\n" + "	set	status = 1\r\n"
				+ "	where bkt_id in (select id from tf_vhl_booklets where trf_id = V_TRF_ID)\r\n"
				+ "	and status = 2 ;\r\n" + "\r\n" + "	ELSE\r\n" + "\r\n"
				+ "		UPDATE TRAFFIC.TF_STP_ORG_REGISTRATION_INFOS\r\n"
				+ "		SET		TL_EXPIRY_DATE=  TO_DATE('20/8/2018','DD/MM/YYYY'),\r\n"
				+ "				P_O_BOX             		=   '88888',\r\n"
				+ "				MOBILE                      =   '971509558138',\r\n"
				+ "	            PHONE 						=	'042510302',\r\n"
				+ "				EMAIL 						=   'support@isoft.ae'\r\n" + "\r\n"
				+ "		WHERE ORG_ID = V_org_ID ;\r\n" + "\r\n" + "\r\n" + "	update tf_stp_circular_notes\r\n"
				+ "	set	status = 1\r\n" + "	where org_id = V_org_ID\r\n" + "	and status = 2 ;\r\n" + "\r\n"
				+ "	update tf_stp_circular_notes\r\n" + "	set	status = 1\r\n"
				+ "	where bkt_id in (select id from tf_vhl_booklets where trf_id = V_TRF_ID)\r\n"
				+ "	and status = 2 ;\r\n" + "\r\n" + "  END IF;\r\n" + "\r\n" + "\r\n" + "\r\n" + "\r\n"
				+ "  commit;\r\n" + "end;");

		System.out.println(("Blockers were removed successfully."));
		con.close();
	}

	public void makeTradePlateExpired(String plateNumber) throws ClassNotFoundException, SQLException {

		setConnection();
		PreparedStatement preparedStmt = con.prepareStatement(
				"UPDATE TRAFFIC.TF_VHL_BOOKLETS SET EXPIRY_DATE = SYSDATE-120 Where PLT_ID = (Select ID From TRAFFIC.TF_VHL_PLATES where PCD_ID = 10 AND PLATE_NO = ?)");
		preparedStmt.setString(1, plateNumber);
		preparedStmt.executeUpdate();
		System.out.println("Trade Plate (" + plateNumber + ") is now expired (Row Updated with Sysdate-120)");
		con.close();
	}

	public void makePermitDateExpired(String trafficFileNo) throws ClassNotFoundException, SQLException {

		setConnection();
		PreparedStatement preparedStmt = con.prepareStatement(
				"update TF_SPL_TRADE_PERMIT  set EXPIRY_DATE = sysdate-1 where PERMIT_STATUS = 2 AND TRF_ID = (select id from tf_stp_traffic_files trf where trf.traffic_no =  ?)");
		preparedStmt.setString(1, trafficFileNo);
		preparedStmt.executeUpdate();
		System.out.println("Permit for (" + trafficFileNo + ") is now expired (Row Updated with Sysdate-1)");
		con.close();
	}

	public void makeLearningPermitExpired(String trafficFile) throws ClassNotFoundException, SQLException {

		setConnection();
		PreparedStatement preparedStmt = con.prepareStatement(
				"update   TF_DTT_LEARNING_PERMITS\r\n" + "set        permit_expiry_date=trunc(sysdate)-1\r\n"
						+ "where app_id=(select id from tf_dtt_license_applications where reg_id=?)");
		preparedStmt.setString(1, trafficFile);
		preparedStmt.executeUpdate();
		System.out.println("Learning Permit for (" + trafficFile + ") is now expired (Row Updated with Sysdate-120)");
		con.close();
	}

	public void makeVehicleExpired(String chassis) throws ClassNotFoundException, SQLException {

		setConnection();
		PreparedStatement preparedStmt = con
				.prepareStatement("Update TF_VHL_BOOKLETS set Expiry_Date = TRUNC(SYSDATE) Where CHASISS_NO = ?");
		preparedStmt.setString(1, chassis);
		preparedStmt.executeUpdate();
		System.out.println(" Expiry date of vehicle with chassis (" + chassis + ") was updated by Sysdate");
		con.close();
	}

	public String[] getExpiredReservedNumber() throws ClassNotFoundException, SQLException {
		setConnection();
		Statement stmt = con.createStatement();

		String[] vehilce = new String[4];

		ResultSet rs = stmt.executeQuery("select\r\n" + "       F_GET_TRF_NUMBER_2(BKT.TRF_ID)TRF_NO,\r\n"
				+ "       PLATE_NO,\r\n" + "       PCD.DESCRIPTION_A PLATE_CODE_DESC,\r\n"
				+ "       PLC.description_A PLATE_CATEGORY_DESC\r\n" + "FROM\r\n" + "       TF_VHL_PLATES PLT,\r\n"
				+ "       TF_SPL_PLATE_TEMPLATES SPT,\r\n" + "       TF_VHL_PLATE_CODES PCD,\r\n"
				+ "       TF_VHL_PLATE_CATEGORIES PLC,\r\n" + "       tf_vhl_preserved_plates prp,\r\n"
				+ "       TF_VHL_BOOKLETS BKT\r\n" + "WHERE\r\n" + "       PLT.SPT_ID = SPT.ID (+)\r\n"
				+ "       AND PLT.ID = BKT.PLT_ID\r\n" + "       AND PCD.ID = PLT.PCD_ID\r\n"
				+ "       AND PLC.CODE = PCD.PLC_CODE\r\n" + "       AND PLC.EMI_CODE = PCD.PLC_EMI_CODE\r\n"
				+ "       and plt.id = prp.plt_id\r\n" + "       AND PLC.EMI_CODE ='DXB'\r\n"
				+ "       AND PLC.CODE = 2 -- PLATE_CATEGORY\r\n"
				+ "       AND PLT.TRF_ID IS NULL --    TO CHECK IF THE PLATE IS NOT OWNED\r\n"
				+ "       AND PLATE_STATUS = 7 --- TO CHECK PLATE_STATUS\r\n" + "       and PRP.STATUS = 2\r\n"
				+ "       and prp.expiry_date <= trunc(sysdate)\r\n"
				+ "       and prp.expiry_date > trunc(sysdate) -1\r\n" + "       and rownum < 2");

		int countrow = 0;
		while (rs.next()) {
			countrow++;
			vehilce[0] = rs.getString(1);
			vehilce[1] = rs.getString(2);
			vehilce[2] = rs.getString(3);
			vehilce[3] = rs.getString(4);
			System.out.println(" TRF_NO =     " + vehilce[0] + "  --- PLATE_NO =     " + vehilce[1]
					+ "  --- Plate_Code =     " + vehilce[2] + " Chassis =     " + vehilce[3]);
		}
		if (countrow == 0) {
			System.out.println(" There are no expired reserved plates ");
		}

		// closing DB Connection
		con.close();
		return vehilce;
	}

	public String[] getVehiclewithMortgageRelease() throws ClassNotFoundException, SQLException {

		setConnection();

		Statement stmt = con.createStatement();

		String[] vehilce = new String[7];
		ResultSet rs = stmt.executeQuery("SELECT F_GET_TRF_NUMBER_2(BKT.TRF_ID)TRF_NO,\r\n"
				+ "	   F_DB_GET_PLATE_DETAILS(BKT.PLT_ID) AS PLATE,\r\n" + "	   BKT.CHASISS_NO,\r\n"
				+ "	   EXPIRY_DATE\r\n"
				+ "FROM TF_VHL_BOOKLETS BKT, TF_STP_TRAFFIC_FILES TRF, TF_VHL_VEHICLES VLE, TF_STP_VEHICLE_CLASSES  VCL, TF_STP_PERSONS PRS\r\n"
				+ "WHERE TRF.ID = BKT.TRF_ID\r\n" + "AND   TRF.PRS_ID = PRS.ID\r\n" + "AND   BKT.VLE_ID = VLE.ID\r\n"
				+ "AND   VLE.VCL_ID = VCL.ID\r\n" + "AND   TRF.PRS_ID IS NOT NULL\r\n"
				+ "AND   EXPIRY_DATE >= TRUNC(SYSDATE)\r\n" + "AND   TRF.ORG_ID IS NULL\r\n"
				+ "AND  PRS.PASSPORT_NO IS NOT NULL\r\n" + "AND   PRS.ID IS NOT NULL\r\n"
				+ "AND   BKT.IS_MORTGAGED = 2\r\n" + "AND PLT_ID IN (SELECT plt.ID FROM TF_VHL_PLATES plt\r\n"
				+ "               WHERE PCD_ID in (select pcd.ID from tf_vhl_plate_codes pCD where plc_emi_code = 'DXB'AND pcd.plc_code = 2))\r\n"
				+ "AND VCL.ID = 3\r\n" + "AND TRS_END_DATE IS NULL\r\n" + "AND EXPIRY_DATE IS NOT NULL\r\n"
				+ "AND F_GET_TRF_NUMBER_2(BKT.TRF_ID) NOT IN(10999265)\r\n"
				+ "AND F_GET_TRF_NUMBER_2(BKT.TRF_ID) NOT LIKE '%100%'\r\n"
				+ "AND BKT.CHASISS_NO in (select chassis_number from TF_VHL_E_MORTGAGE_RELEASE\r\n"
				+ "where status = 3)\r\n" + "AND ROWNUM <1");

		int countrow = 0;
		while (rs.next()) {
			countrow++;
			vehilce[0] = rs.getString(1);
			String[] plate = rs.getString(2).split("\\s+");

			vehilce[1] = plate[0];
			vehilce[2] = plate[2];
			vehilce[3] = plate[1];
			vehilce[4] = rs.getString(3);
			vehilce[5] = rs.getString(4);
			vehilce[6] = rs.getString(5);
			System.out.println(" TRF_NO = " + vehilce[0] + "  --- PLATE_NO = " + vehilce[1] + "  --- Plate_Code = "
					+ vehilce[2] + " PlateCategory = " + vehilce[3] + " Chassis = " + vehilce[4] + " Weight = "
					+ vehilce[5]);
		}
		if (countrow == 0) {
			System.out.println(" There is No available vehicles with mortgage release request in DB tables.");
		}

		// closing DB Connection
		con.close();
		return vehilce;
	}

	public VehicleClass getVehicleClassEnDescription(String vehicleClass) {
		VehicleClass vehicleClassEn;
		switch (vehicleClass) {
		case "مركبة خفيفة":
			vehicleClassEn = VehicleClass.LightVehicle;
			break;
		case "دراجة نارية":
			vehicleClassEn = VehicleClass.MotorCycle;
			break;
		case "دراجة نارية ترفيهية":
			vehicleClassEn = VehicleClass.EntertainmentMotorcycle;
			break;
		case "مركبة ثقيلة":
			vehicleClassEn = VehicleClass.HeavyVehicle;
			break;
		case "مقطورة":
			vehicleClassEn = VehicleClass.Trailer;
			break;
		case "باص خفيف":
			vehicleClassEn = VehicleClass.LightBus;
			break;
		case "باص ثقيل":
			vehicleClassEn = VehicleClass.HeavyBus;
			break;
		case "جهاز ميكانيكى خفيف":
			vehicleClassEn = VehicleClass.LightMechanical;
			break;
		case "جهاز ميكانيكى ثقيل":
			vehicleClassEn = VehicleClass.HeavyMechanical;
			break;
		case "مركبة أصحاب الهمم":
			vehicleClassEn = VehicleClass.HandicapCarriage;
			break;
		default:
			vehicleClassEn = VehicleClass.LightVehicle;
		}
		return vehicleClassEn;
	}

	public String getPlateCategoryEnDesc(String plateCategoryArDesc) {
		String plateCategoryEnDesc;
		switch (plateCategoryArDesc) {
		case "دراجة نارية":
			plateCategoryEnDesc = "Motorcycle";
			break;
		case "تجارية":
			plateCategoryEnDesc = "Trade Plate";
			break;
		case "خصوصي":
			plateCategoryEnDesc = "Private";
			break;
		case "نقل":
			plateCategoryEnDesc = "Public Transportation";
			break;
		case "استيراد":
			plateCategoryEnDesc = "Import";
			break;
		case "تعليم":
			plateCategoryEnDesc = "Learning Vehicle";
			break;
		case "هيئة قنصلية":
			plateCategoryEnDesc = "Consulate Authority";
			break;
		case "شرطة دبي":
			plateCategoryEnDesc = "Dubai Police";
			break;
		case "اجرة":
			plateCategoryEnDesc = "Taxi";
			break;
		case "دراجة":
			plateCategoryEnDesc = "Entertainment Motorcycle";
			break;
		case "مقطورة":
			plateCategoryEnDesc = "Trailer";
			break;
		case "كلاسيك":
			plateCategoryEnDesc = "Classic";
			break;
		case "حكومة":
			plateCategoryEnDesc = "Government Vehicle";
			break;
		default:
			plateCategoryEnDesc = "Private";
		}
		return plateCategoryEnDesc;
	}

	public List<String> checkEvents(String eventListString, String testDateTime)
			throws ClassNotFoundException, SQLException {
		// String eventListString = "'EV_VLD_9', 'EV_VLD_35', 'EV_VLD_36', 'EV_VLD_40',
		// 'EV_VLD_41', 'EV_VLD_45', 'EV_VLD_60', 'EV_VLD_61', 'EV_VLD_62', 'EV_VLD_63',
		// 'EV_VLD_66', 'EV_VLD_68', 'EV_VLD_69', 'EV_VLD_70', 'EV_VLD_76', 'EV_VLD_97',
		// 'EV_VLD_122', 'EV_VLD_123', 'EV_VLD_131', 'EV_VLD_134', 'EV_VLD_135'";

		setConnection();

		Statement stmt = con.createStatement();

		List<String> foundEvents = new ArrayList<String>();
		ResultSet rs = stmt.executeQuery("Select CASE_DESCRIOTION From TF_STP_KEDB_SUPPORT_CASES \r\n"
				+ "Where CASE_DESCRIOTION IN (" + eventListString + ")\r\n" + "AND CREATION_DATE >= to_date('"
				+ testDateTime + "' , 'dd/mm/yyyy hh24:mi:ss')");

		int countrow = 0;
		while (rs.next()) {
			countrow++;
			foundEvents.add(rs.getString(1));
			System.out.print(" \n Event " + rs.getString(1) + " has been Logged.");
		}
		if (countrow == 0) {
			System.out.println(" There are No Events Logged in TF_STP_KEDB_SUPPORT_CASES table.");
		}
		// closing DB Connection
		con.close();

		return foundEvents;
	}

	private void setConnection() throws ClassNotFoundException, SQLException {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
			System.out.println("JDBC class not found");
		}
		// step2 create the connection object
		con = DriverManager.getConnection(getDatabaseConnection(), Username, password);
		System.out.print("connected to DB   --  ");
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
		case ER:
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

	private int[] getWeightIntervals(VehicleWeight vehicleWeight) {
		int[] weight = new int[2];
		switch (vehicleWeight) {
		case LessThan3001:
			weight[0] = 0;
			weight[1] = 3001;
			break;
		case Between3001AND12000:
			weight[0] = 3001;
			weight[1] = 12000;
			break;
		case MoreThan12000:
			weight[0] = 12000;
			weight[1] = 50000;
			break;
		case Any:
			weight[0] = 0;
			weight[1] = 500000;
			break;
		default:
			weight[0] = 0;
			weight[1] = 3001;
			break;
		}
		return weight;
	}

	private String getVehicleClassId(VehicleClass vehicleClass) {
		String vehicleClassId;
		switch (vehicleClass) {
		case MotorCycle:
			vehicleClassId = "1";
			break;
		case HandicapCarriage:
			vehicleClassId = "2";
			break;
		case LightVehicle:
			vehicleClassId = "3";
			break;
		case LightVehicleAutomatic:
			vehicleClassId = "4";
			break;
		case HeavyVehicle:
			vehicleClassId = "5";
			break;
		case LightBus:
			vehicleClassId = "6";
			break;
		case HeavyBus:
			vehicleClassId = "7";
			break;
		case LightMechanical:
			vehicleClassId = "8";
			break;
		case HeavyMechanical:
			vehicleClassId = "9";
			break;
		case EntertainmentMotorcycle:
			vehicleClassId = "10";
			break;
		case Trailer:
			vehicleClassId = "11";
			break;
		default:
			vehicleClassId = "3";
		}
		return vehicleClassId;
	}

	private String getVehicleClassId(String vehicleClass) {
		String vehicleClassId;
		switch (vehicleClass) {
		case "دراجة نارية":
			vehicleClassId = "1";
			break;
		case "مركبة أصحاب الهمم":
			vehicleClassId = "2";
			break;
		case "مركبة خفيفة":
			vehicleClassId = "3";
			break;
		case "مركبة خفيفة اوتوماتيك":
			vehicleClassId = "4";
			break;
		case "مركبة ثقيلة":
			vehicleClassId = "5";
			break;
		case "باص خفيف":
			vehicleClassId = "6";
			break;
		case "باص ثقيل":
			vehicleClassId = "7";
			break;
		case "جهاز ميكانيكي خفيف":
			vehicleClassId = "8";
			break;
		case "جهاز ميكانيكي ثقيل":
			vehicleClassId = "9";
			break;
		case "دراجة نارية ترفيهية":
			vehicleClassId = "10";
			break;
		case "مقطورة":
			vehicleClassId = "11";
			break;
		default:
			vehicleClassId = "3";
		}
		return vehicleClassId;
	}

	public String getNoOfSeats(VehicleClass vehicleClass) {
		String noOfSeats;
		switch (vehicleClass) {
		case MotorCycle:
			noOfSeats = "1";
			break;
		case HandicapCarriage:
			noOfSeats = "2";
			break;
		case LightVehicle:
			noOfSeats = "5";
			break;
		case LightVehicleAutomatic:
			noOfSeats = "5";
			break;
		case HeavyVehicle:
			noOfSeats = "5";
			break;
		case LightBus:
			noOfSeats = "14";
			break;
		case HeavyBus:
			noOfSeats = "27";
			break;
		case LightMechanical:
			noOfSeats = "1";
			break;
		case HeavyMechanical:
			noOfSeats = "2";
			break;
		case EntertainmentMotorcycle:
			noOfSeats = "1";
			break;
		case Trailer:
			noOfSeats = "0";
			break;
		default:
			noOfSeats = "5";
		}
		return noOfSeats;
	}

	private String getPlateCategoryId(PlateCategory plateCategory) {
		String plateCategoryId;
		switch (plateCategory) {
		case Motorcycle:
			plateCategoryId = "1";
			break;
		case Private:
			plateCategoryId = "2";
			break;
		case Taxi:
			plateCategoryId = "3";
			break;
		case PublicTransportation:
			plateCategoryId = "4";
			break;
		case TradePlate:
			plateCategoryId = "5";
			break;
		case Export:
			plateCategoryId = "6";
			break;
		case ConsulateAuthority:
			plateCategoryId = "7";
			break;
		case PoliticalAuthority:
			plateCategoryId = "8";
			break;
		case InternationalOrganization:
			plateCategoryId = "9";
			break;
		case Delegate:
			plateCategoryId = "10";
			break;
		case GovernmentVehicle:
			plateCategoryId = "11";
			break;
		case PrivateTransportation:
			plateCategoryId = "12";
			break;
		case DataMigration:
			plateCategoryId = "13";
			break;
		case EntertainmentMotorcycle:
			plateCategoryId = "14";
			break;
		case Trailer:
			plateCategoryId = "15";
			break;
		case Classical:
			plateCategoryId = "16";
			break;
		case Import:
			plateCategoryId = "17";
			break;
		case LearningVehicle:
			plateCategoryId = "18";
			break;
		case DubaiPolice:
			plateCategoryId = "19";
			break;
		case DubaiFlag:
			plateCategoryId = "20";
			break;
		case EXPO1:
			plateCategoryId = "21";
			break;
		case EXPO2:
			plateCategoryId = "22";
			break;
		default:
			plateCategoryId = "1";
			break;
		}
		return plateCategoryId;
	}

	public VehicleWeight setVehicleWeightEnum(String vehicleWeightRange) {
		if (vehicleWeightRange.contains("300") && vehicleWeightRange.contains("1200"))
			return VehicleWeight.Between3001AND12000;
		else if (vehicleWeightRange.contains("300"))
			return VehicleWeight.LessThan3001;
		else if (vehicleWeightRange.contains("1200"))
			return VehicleWeight.MoreThan12000;
		else
			return VehicleWeight.Any;
	}

	public PlateCategory getPlateCategoryEnum(String plateCategoryValue) {
		PlateCategory plateCategory;
		switch (plateCategoryValue) {
		case "دراجة نارية":
			plateCategory = PlateCategory.Motorcycle;
			break;
		case "تجارية":
			plateCategory = PlateCategory.TradePlate;
			break;
		case "خصوصي":
			plateCategory = PlateCategory.Private;
			break;
		case "نقل":
			plateCategory = PlateCategory.PublicTransportation;
			break;
		case "استيراد":
			plateCategory = PlateCategory.Import;
			break;
		case "تعليم":
			plateCategory = PlateCategory.LearningVehicle;
			break;
		case "هيئة قنصلية":
			plateCategory = PlateCategory.ConsulateAuthority;
			break;
		case "شرطة دبي":
			plateCategory = PlateCategory.DubaiPolice;
			break;
		case "اجرة":
			plateCategory = PlateCategory.Taxi;
			break;
		case "دراجة":
			plateCategory = PlateCategory.EntertainmentMotorcycle;
			break;
		case "مقطورة":
			plateCategory = PlateCategory.Trailer;
			break;
		case "كلاسيك":
			plateCategory = PlateCategory.Classical;
			break;
		case "حكومة":
			plateCategory = PlateCategory.GovernmentVehicle;
			break;
		default:
			plateCategory = PlateCategory.Private;
		}
		return plateCategory;
	}

	public String generateChassisNo() throws ClassNotFoundException, SQLException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String chassis = sdf.format(cal.getTime()).replace("-", "").replace(":", "") + "00";
		addInsurance(chassis, VehicleClass.LightVehicle);
		System.out.println("Generated Chassis: " + chassis);
		return chassis;
	}

	public void updateTransferedTrfFile(String trafficFile) throws ClassNotFoundException, SQLException {

		setConnection();
		PreparedStatement preparedStmt = con.prepareStatement("UPDATE TRAFFIC.TF_STP_HELD_LICENSES\r\n"
				+ "   SET PRS_ID =\r\n" + "          (SELECT PRS_ID FROM TRAFFIC.TF_STP_TRAFFIC_FILES\r\n"
				+ "                         WHERE TRAFFIC_NO = ?) Where ID = (SELECT HLS.ID\r\n"
				+ "FROM    TRAFFIC.TF_STP_HELD_LICENSES HLS,\r\n" + "             TRAFFIC.TF_STP_TRAFFIC_FILES TRF\r\n"
				+ "WHERE  TRF.PRS_ID = HLS.PRS_ID\r\n" + "AND      HLS.EMI_CODE <> 'DXB'\r\n"
				+ "AND      EXPIRY_DATE > sysdate\r\n" + "AND     HLS.ISSUE_PLACE = 'ابو ظبي'\r\n"
				+ "and      rownum < 2)");
		preparedStmt.setString(1, trafficFile);
		preparedStmt.executeUpdate();
		System.out.println("PRS_ID was updated for Traffic File : (" + trafficFile + ")");
		con.close();
	}

	///////////////////////// DRL
	public String gethbtrafficfile() throws ClassNotFoundException, SQLException {

		setConnection();
		String buybooktrafficNO = "";
		Statement stmt = con.createStatement();
		// step4 execute query (Buy handbook) |(Medical test)
		ResultSet rs = stmt.executeQuery("SELECT 	APP.ID,TRF.TRAFFIC_NO,\r\n"
				+ "		TO_CHAR(PRS.BIRTH_DATE,'YYYY') BIRTH_YEAR,\r\n" + "		PRS.NAME_A,\r\n"
				+ "--		PRS.NAME_E,\r\n" + "--		'ROAD_TEST' EXAM_TYPE,\r\n"
				+ "		VCL.DESCRIPTION_A VEHICLE_CLASS,\r\n" + "		APP.CTR_ID TEST_CENTER_ID,\r\n"
				+ "--		CTR.NAME TEST_CENTER_ENGLISH_NAME,\r\n" + "		CTR.NAME_A TEST_CENTER_ARABIC_NAME\r\n"
				+ "FROM 	TF_DTT_LICENSE_APPLICATIONS APP,\r\n" + "		TF_STP_PERSONS PRS,\r\n"
				+ "		TF_STP_TRAFFIC_FILES TRF,\r\n" + "		TF_STP_VEHICLE_CLASSES VCL,\r\n"
				+ "		TF_STP_CENTERS CTR\r\n" + "WHERE	APP.PRS_ID = PRS.ID\r\n" + "AND		TRF.PRS_ID = PRS.ID\r\n"
				+ "AND		APP.VCL_ID = VCL.ID\r\n" + "AND		APP.CTR_ID = CTR.ID\r\n" + "AND		APP.STATUS = 1\r\n"
				+ "AND		APP.CTR_ID IN(15)\r\n" + "AND		APP.VCL_ID IN(3)\r\n"
				+ "AND		NOT EXISTS(SELECT  1\r\n" + "				   FROM    TF_DTT_TRIALS TRL\r\n"
				+ "				   WHERE   TRL.AEX_APP_ID = APP.ID)\r\n" + "AND		ROWNUM <=1\r\n"
				+ "ORDER BY CTR.ID,TRAFFIC_NO" + "\r\n" + "");

		System.out.println(("get the results"));
		while (rs.next()) {
			String buybookTF = rs.getString(2);
			System.out.print(" The result of the database is  " + buybookTF);
			buybooktrafficNO = buybookTF;
		}
		con.close();
		return buybooktrafficNO;
	}

	public String getTrfBithYear(String trafficFile) throws ClassNotFoundException, SQLException {

		setConnection();
		String birthYear = "";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT TO_CHAR (PRS.BIRTH_DATE, 'YYYY') BIRTH_YEAR\r\n"
				+ "FROM TF_STP_PERSONS PRS, TF_STP_TRAFFIC_FILES TRF\r\n"
				+ "WHERE TRF.PRS_ID = PRS.ID AND TRF.TRAFFIC_NO = " + trafficFile + " AND ROWNUM < 2");

		if (rs.next()) {

			System.out.println(" TrafficFile " + trafficFile + " BirthYear is " + rs.getString(1));
			birthYear = rs.getString(1);
		} else
			System.out.print(" TrafficFile " + trafficFile + " has no BirthYear in DB. ");
		con.close();
		return birthYear;
	}

	public String[] getExpiredLicense() throws ClassNotFoundException, SQLException {
		setConnection();
		Statement stmt = con.createStatement();

		String[] person = new String[6];

		ResultSet rs = stmt.executeQuery(
				"SELECT TRF.TRAFFIC_NO, DLC.LICENSE_NO, TO_CHAR (PRS.BIRTH_DATE, 'YYYY') BIRTH_YEAR, TO_CHAR (DLC.ISSUE_DATE, 'MM/DD/YYYY') ISSUE_DATE\r\n"
						+ "FROM TF_DRL_DRIVING_LICENSES DLC,TF_STP_TRAFFIC_FILES TRF,TF_STP_PERSONS PRS\r\n"
						+ "WHERE     TRF.ID = DLC.TRF_ID\r\n" + "AND TRF.PRS_ID = PRS.ID\r\n" + "AND DLC.STATUS = 2\r\n"
						+ "AND DLC.LICENSE_STATUS = 0\r\n" + "AND DLC.EXPIRY_DATE >= TRUNC (SYSDATE) - 1000\r\n"
						+ "AND DLC.EXPIRY_DATE < TRUNC (SYSDATE)\r\n" + "AND PRS.CID_ID IS NOT NULL\r\n"
						+ "AND PRS.RES_EXPIRY_DATE >= TRUNC (SYSDATE)\r\n"
						+ "AND PRS.CID_EXPIRY_DATE >= TRUNC (SYSDATE)\r\n" + "AND PRS.CTY_CODE_VISA = '03'\r\n"
						+ "AND ROWNUM <= 1");

		int countrow = 0;
		while (rs.next()) {
			countrow++;
			person[0] = rs.getString(1);
			person[1] = rs.getString(2);
			person[2] = rs.getString(3);
			person[3] = rs.getString(4);
			System.out.println("TRF_NO = " + person[0] + "  --- License = " + person[1] + "  --- BirthYear = "
					+ person[2] + "  --- IssueDate = " + person[3]);
		}
		if (countrow == 0) {
			System.out.println(" No expired licenses found in DB.");
		}

		// closing DB Connection
		con.close();
		return person;
	}

	public String[] getValidLicense() throws ClassNotFoundException, SQLException {
		setConnection();
		Statement stmt = con.createStatement();

		String[] person = new String[6];

		ResultSet rs = stmt.executeQuery(
				"SELECT TRAFFIC_NO, DLC.LICENSE_NO, TO_CHAR (PRS.BIRTH_DATE, 'YYYY') BIRTH_YEAR, TO_CHAR (DLC.ISSUE_DATE, 'DD-MM-YYYY') ISSUE_DATE\r\n"
						+ "FROM TF_STP_TRAFFIC_FILES TRF,\r\n" + "TF_DRL_DRIVING_LICENSES DLC,\r\n"
						+ "TF_STP_PERSONS PRS\r\n" + "WHERE     TRF.PRS_ID = PRS.ID\r\n" + "AND DLC.TRF_ID = TRF.ID\r\n"
						+ "AND PRS.GENDER = 2\r\n" + "AND EXISTS\r\n"
						+ "(SELECT 1 FROM TF_DRL_LICENSE_CLASSES LCL WHERE LCL.VCL_ID IN (3, 4) AND LCL.STATUS = 2)\r\n"
						+ "AND DLC.EXPIRY_DATE > TRUNC (SYSDATE)+120\r\n" + "AND DLC.STATUS = 2\r\n"
						+ "AND DLC.LICENSE_STATUS = 0\r\n" + "AND PRS.CNT_ID <> 10\r\n"
						+ "AND NOT EXISTS (SELECT 1 FROM TF_FFU_TICKETS TCK WHERE TCK.DLC_ID = DLC.ID) AND NOT EXISTS\r\n"
						+ "(SELECT 1 FROM TF_FFU_CIRCULAR_TICKETS CTK WHERE CTK.DLC_ID = DLC.ID) AND ROWNUM <= 1");

		int countrow = 0;
		while (rs.next()) {
			countrow++;
			person[0] = rs.getString(1);
			person[1] = rs.getString(2);
			person[2] = rs.getString(3);
			person[3] = rs.getString(4);
			System.out.println("TRF_NO = " + person[0] + "  --- License = " + person[1] + "  --- BirthYear = "
					+ person[2] + "  --- IssueDate = " + person[3]);
		}
		if (countrow == 0) {
			System.out.println(" No expired licenses found in DB.");
		}

		// closing DB Connection
		con.close();
		return person;
	}

	public String getDRLTrfToChangeStatus() throws ClassNotFoundException, SQLException {
		setConnection();
		Statement stmt = con.createStatement();

		String trafficFile = "";

		ResultSet rs = stmt.executeQuery(
				"SELECT TRAFFIC_NO, DLC.LICENSE_NO, TO_CHAR (PRS.BIRTH_DATE, 'YYYY') BIRTH_YEAR, TO_CHAR (DLC.ISSUE_DATE, 'DD-MM-YYYY') ISSUE_DATE\r\n"
						+ "FROM TF_STP_TRAFFIC_FILES TRF,\r\n" + "TF_DRL_DRIVING_LICENSES DLC,\r\n"
						+ "TF_STP_PERSONS PRS\r\n" + "WHERE     TRF.PRS_ID = PRS.ID\r\n" + "AND DLC.TRF_ID = TRF.ID\r\n"
						+ "AND PRS.GENDER = 2\r\n" + "AND EXISTS\r\n"
						+ "(SELECT 1 FROM TF_DRL_LICENSE_CLASSES LCL WHERE LCL.VCL_ID IN (3, 4) AND LCL.STATUS = 2)\r\n"
						+ "AND DLC.EXPIRY_DATE > TRUNC (SYSDATE)+120\r\n" + "AND DLC.STATUS = 2\r\n"
						+ "AND DLC.LICENSE_STATUS = 0\r\n" + "AND PRS.CNT_ID <> 10\r\n"
						+ "AND NOT EXISTS (SELECT 1 FROM TF_FFU_TICKETS TCK WHERE TCK.DLC_ID = DLC.ID) AND NOT EXISTS\r\n"
						+ "(SELECT 1 FROM TF_FFU_CIRCULAR_TICKETS CTK WHERE CTK.DLC_ID = DLC.ID) AND ROWNUM <= 1");

		int countrow = 0;
		while (rs.next()) {
			countrow++;
			trafficFile = rs.getString(1);
			System.out.println("TRF_NO = " + trafficFile);
		}
		if (countrow == 0) {
			System.out.println(" No expired licenses found in DB.");
		}

		// closing DB Connection
		con.close();
		return trafficFile;
	}
}
