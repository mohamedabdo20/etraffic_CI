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

import io.qameta.allure.Step;
import isoft.etraffic.enums.Database;
import isoft.etraffic.enums.PlateCategory;
import isoft.etraffic.enums.VHLTransaction;
import isoft.etraffic.enums.VehicleClass;
import isoft.etraffic.enums.VehicleWeight;

public class DBQueries {

	Connection con;
	String Username, password;
	public static Database database = Database.Test1;
	public static List<String> selectedVehiclesList = new ArrayList<String>();
	public static List<String> selectedVehiclesListSDDI = new ArrayList<String>();
	public static int rownum = 3;
	public static int rownumSDDI = 2;
	public static boolean vehicleSelectedBefore = false;
	public static boolean vehicleSelectedBeforSDDI = false;
	public String TrafficFileNo;
	public String PlateNo;
	public String PlateCode;
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

	public void addTest(String chassis, VehicleClass vehicleClass, String weight)
			throws ClassNotFoundException, SQLException {
		setConnection();

		Statement stmt = con.createStatement();
		stmt.execute("DECLARE\r\n" + "   v_seqvalue              NUMBER;\r\n" + "   v_seqvalue1             NUMBER;\r\n"
				+ "   v_engin                 VARCHAR2 (30);\r\n" + "   v_seq_value             NUMBER;\r\n"
				+ "   v_new_chass_number      VARCHAR2 (30);\r\n" + "   v_policy_no             VARCHAR2 (30);\r\n"
				+ "   v_VSM_ID                NUMBER;\r\n" + "   v_VMK_ID                NUMBER;\r\n"
				+ "   v_VDS_ID                NUMBER;\r\n" + "   V_USR_NAME              VARCHAR2 (20) := 'esam';\r\n"
				+ "   v_chass_number          VARCHAR2 (30) := '" + chassis + "';\r\n"
				+ "   v_test_type             NUMBER := 5;\r\n" + "   v_new_unloaded_weight   NUMBER := " + weight
				+ ";\r\n" + "   v_new_no_of_seats       NUMBER;\r\n" + "   V_VCL_ID                NUMBER;\r\n"
				+ "BEGIN\r\n" + "   SELECT traffic.vht_seq.NEXTVAL INTO v_seqvalue FROM DUAL;\r\n"
				+ "   SELECT traffic.vps_seq.NEXTVAL INTO v_seqvalue1 FROM DUAL;\r\n"
				+ "   SELECT traffic.eir_seq.NEXTVAL INTO v_seq_value FROM DUAL;\r\n"
				+ "   SELECT VMK.ID, VLE.NO_OF_SEATS, VLE.VCL_ID, VLE.VDS_ID, VLE.VSM_ID\r\n"
				+ "     INTO v_VMK_ID, v_new_no_of_seats, V_VCL_ID, V_VDS_ID, V_VSM_ID\r\n"
				+ "     FROM TF_VHL_VEHICLES VLE, TRAFFIC.TF_VHL_VEHICLE_MODEL VSM, TRAFFIC.TF_VHL_MANUFACTURERS VMK\r\n"
				+ "    WHERE VLE.VSM_ID = VSM.ID AND VSM.VMK_ID = VMK.ID AND CHASISS_NO = v_chass_number;\r\n"
				+ "   V_NEW_CHASS_NUMBER := NULL;\r\n" + "   V_ENGIN := NULL;\r\n" + "   V_POLICY_NO := '123456';\r\n"
				+ "   DELETE FROM TRAFFIC.TF_VHL_TRANSACTION_VEHICLES\r\n"
				+ "         WHERE CHASISS_NO = v_chass_number\r\n"
				+ "               AND CREATION_DATE = TRUNC (SYSDATE)\r\n"
				+ "               AND USR_ID = (SELECT ID FROM traffic.SF_INF_USERS WHERE NAME = V_USR_NAME);\r\n"
				+ "   INSERT INTO traffic.tf_stp_vehicle_tests \r\n"
				+ "   (chasiss_no, test_date, ID, emi_code, ctr_id, created_by,\r\n"
				+ "    creation_date, insurance_ref_no, new_vds_id, new_cnt_id,\r\n"
				+ "    current_meter_reading, status_date, test_type, meter_unit,\r\n"
				+ "    new_unloaded_weight, new_no_of_doors, new_wheel_drive,\r\n"
				+ "    new_eng_power, new_fuel_code, status, new_vsm_id,\r\n"
				+ "    need_fees_calculated, new_axes_no, test_result,\r\n"
				+ "    new_vcl_id, new_myr_id, new_chassis_no, new_no_of_seats,\r\n"
				+ "    new_eng_capacity, failed_count, current_meter_reading_km,\r\n"
				+ "    new_cylinders, new_carry_weight, new_engine_no, cis_id, trs_type)\r\n"
				+ "    VALUES (v_chass_number, SYSDATE, v_seqvalue, 'DXB', 1215,'rta4730',\r\n"
				+ "            SYSDATE, '234567', V_VDS_ID, NULL, 4545, SYSDATE, 1, 1, v_new_unloaded_weight,\r\n"
				+ "            NULL, NULL, NULL, NULL, 1, V_VSM_ID, 2, 12, 2, V_VCL_ID, 2017, v_new_chass_number,\r\n"
				+ "            v_new_no_of_seats, NULL, 0, 4545, NULL, 121, v_engin, v_test_type, 1);\r\n"
				+ "   INSERT INTO traffic.tf_stp_vht_pass_services (ID,cis_id, vht_id, update_date, updated_by,\r\n"
				+ "                                                 creation_date, created_by)\r\n"
				+ "        VALUES (v_seqvalue1, v_test_type, v_seqvalue, SYSDATE, 'rta4730', SYSDATE, 'rta4730');\r\n"
				+ "   COMMIT;\r\n" + "END;");

		System.out.println(("Vehicle Test was added successfully."));
		con.close();
	}

	public void addTestUnRegisteredVehicle(String chassis, VehicleClass vehicleClass, String weight)
			throws ClassNotFoundException, SQLException {
		setConnection();

		// String chassis = "21012019T09000000";
		// VehicleClass vehicleClass =VehicleClass.LightVehicle;
		// String weight = "2500";

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

	public void addTestFotTrailer(String chassis) throws ClassNotFoundException, SQLException {
		setConnection();

		Statement stmt = con.createStatement();
		stmt.execute("DECLARE\r\n" + "   v_seqvalue              NUMBER;\r\n" + "   v_seqvalue1             NUMBER;\r\n"
				+ "   v_engin                 VARCHAR2 (30);\r\n" + "   v_seq_value             NUMBER;\r\n"
				+ "   v_new_chass_number      VARCHAR2 (30);\r\n" + "   v_policy_no             VARCHAR2 (30);\r\n"
				+ "   v_VSM_ID                NUMBER;\r\n" + "   v_VMK_ID                NUMBER;\r\n"
				+ "   v_VDS_ID                NUMBER;\r\n" + "   V_USR_NAME              VARCHAR2 (20) := 'esam';\r\n"
				+ "   v_chass_number          VARCHAR2 (30) := '" + chassis + "';\r\n"
				+ "   v_test_type             NUMBER := 5;\r\n" + "   v_new_unloaded_weight   NUMBER := 1571;\r\n"
				+ "   v_new_no_of_seats       NUMBER := 0;\r\n" + "   V_VCL_ID                NUMBER := 11;\r\n"
				+ "BEGIN\r\n" + "   SELECT traffic.vht_seq.NEXTVAL INTO v_seqvalue FROM DUAL;\r\n"
				+ "   SELECT traffic.vps_seq.NEXTVAL INTO v_seqvalue1 FROM DUAL;\r\n"
				+ "   SELECT traffic.eir_seq.NEXTVAL INTO v_seq_value FROM DUAL;\r\n"
				+ "   V_NEW_CHASS_NUMBER := NULL;\r\n" + "   V_ENGIN := NULL;\r\n" + "   V_POLICY_NO := '123456';\r\n"
				+ "   V_VMK_ID := 7;\r\n" + "   DELETE FROM TRAFFIC.TF_VHL_TRANSACTION_VEHICLES\r\n"
				+ "         WHERE     CHASISS_NO = v_chass_number\r\n"
				+ "               AND CREATION_DATE = TRUNC (SYSDATE)\r\n"
				+ "               AND USR_ID = (SELECT ID\r\n"
				+ "                               FROM traffic.SF_INF_USERS\r\n"
				+ "                              WHERE NAME = V_USR_NAME);\r\n" + "   SELECT ID\r\n"
				+ "     INTO V_VSM_ID\r\n" + "     FROM traffic.tf_vhl_vehicle_model\r\n"
				+ "    WHERE vcl_id = v_vcl_id AND VMK_ID = 7 AND status = 2 AND ROWNUM < 2;\r\n" + "   SELECT ID\r\n"
				+ "     INTO V_VDS_ID\r\n" + "     FROM traffic.tf_vhl_vehicle_DESCRIPTIONS\r\n"
				+ "    WHERE vcl_id = v_vcl_id\r\n" + "    AND DESCRIPTION_A LIKE '%كرفان%';\r\n"
				+ "   INSERT INTO traffic.tf_stp_vehicle_tests (chasiss_no,test_date,ID,emi_code,ctr_id,created_by,creation_date,insurance_ref_no,new_vds_id,\r\n"
				+ "                                             new_cnt_id,current_meter_reading,status_date,test_type,meter_unit,new_unloaded_weight,\r\n"
				+ "                                             new_no_of_doors,new_wheel_drive,new_eng_power,new_fuel_code,status,new_vsm_id,\r\n"
				+ "                                             need_fees_calculated,new_axes_no,test_result,new_vcl_id,new_myr_id,new_chassis_no,new_no_of_seats,\r\n"
				+ "                                             new_eng_capacity,failed_count,current_meter_reading_km,new_cylinders,new_carry_weight,new_engine_no,\r\n"
				+ "                                             cis_id,trs_type)\r\n"
				+ "        VALUES (v_chass_number,SYSDATE,v_seqvalue,'DXB',1215,'rta4730',SYSDATE,\r\n"
				+ "                '234567',V_VDS_ID,NULL,4545,SYSDATE,1,1,v_new_unloaded_weight,\r\n"
				+ "                NULL,NULL,NULL,NULL,1,V_VSM_ID,2,12,2,V_VCL_ID,2017,v_new_chass_number,\r\n"
				+ "                v_new_no_of_seats,NULL,0,4545,NULL,121,v_engin,v_test_type,1);\r\n"
				+ "   INSERT INTO traffic.tf_stp_vht_pass_services (ID, cis_id, vht_id, update_date, updated_by, creation_date, created_by)\r\n"
				+ "        VALUES (v_seqvalue1,v_test_type,v_seqvalue,SYSDATE,'rta4730',SYSDATE,'rta4730');\r\n"
				+ "   COMMIT;\r\n" + "END;");

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
				+ "AND F_GET_TRF_NUMBER_2(BKT.TRF_ID) NOT IN(10010022,10018551,10024368, 10029566, 10011937, 10012944, 10010048, 10010030)\r\n"
				// + "AND F_GET_TRF_NUMBER_2(BKT.TRF_ID) NOT LIKE '%100%'\r\n"
				+ "AND F_GET_TRF_NUMBER_2(BKT.TRF_ID) NOT LIKE '%1000%'\r\n" + "AND ROWNUM < 2\r\n"
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

	// sddi
	synchronized public String[] getVehicle(VehicleClass vehicleClass, PlateCategory plateCategory,
			VehicleWeight vehicleWeight) throws ClassNotFoundException, SQLException {
		vehicleSelectedBeforSDDI = false;
		String plateNumber = "";
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
				+ "         AND F_GET_TRF_NUMBER_2 (BKT.TRF_ID) NOT IN (10012032, 10010268 ,10012944, 10011937, 10184041, 10020699, 10024368, 10018551, 10010048, 10010030)\r\n"
				+ "         AND F_GET_TRF_NUMBER_2 (BKT.TRF_ID) NOT LIKE '%1000%'\r\n"
				+ "         AND BKT.WEIGHT BETWEEN " + weightIntervals[0] + " AND " + weightIntervals[1] + "\r\n"
				+ "         AND ROWNUM < " + rownumSDDI);

		int countrow = 0;
		while (rs.next()) {
			countrow++;
			vehilce[0] = rs.getString(1);
			plateNumber = rs.getString(2);
			String[] plate = rs.getString(2).split("\\s+");

			vehilce[1] = plate[0];
			vehilce[2] = plate[2];
			vehilce[3] = plate[1];
			vehilce[4] = rs.getString(3);
			vehilce[5] = rs.getString(5);
			vehilce[6] = rs.getString(4);
			// System.out.println(" TRF_NO = " + vehilce[0] + " --- PLATE_NO = " +
			// vehilce[1] + " --- Plate_Code = "
			// + vehilce[2] + " --- PLATE_Cat = " + vehilce[3] + " Chassis = " + vehilce[4]
			// + " weight = "
			// + vehilce[5]);
		}
		if (countrow == 0) {
			System.out.println(" There is No available vehicles in DB tables");
		}

		// closing DB Connection
		con.close();

		for (int i = 0; i < selectedVehiclesListSDDI.size(); i++) {
			if (selectedVehiclesListSDDI.get(i).equals(plateNumber)) {
				{
					vehicleSelectedBeforSDDI = true;
					break;
				}
			}
		}
		if (vehicleSelectedBeforSDDI) {
			rownumSDDI++;
			con.close();
			return getVehicle(vehicleClass, plateCategory, vehicleWeight);
		} else {
			selectedVehiclesListSDDI.add(plateNumber);
			System.out.println(" TRF_NO = " + vehilce[0] + "  --- PLATE_NO = " + vehilce[1] + "  --- Plate_Code = "
					+ vehilce[2] + " PlateCategory = " + vehilce[3] + " Chassis = " + vehilce[4] + " Weight = "
					+ vehilce[5]);
			System.out.println("-------------------------------");
			return vehilce;
		}
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
				+ "and rownum<7");

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

	synchronized public String[] getExpiredVehicle(VehicleClass vehicleClass, VehicleWeight vehicleWeight,
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
				+ "AND F_GET_TRF_NUMBER_2(BKT.TRF_ID) NOT IN (50000002, 50045269, 10018551, 10004544, 10002719, 10001821, 10004731, 10018319, 10026660, 10019223, 10012693, 10026334, 10012760 , 10007002, 10010900, 10011237, 10024010, 10009488, 11999945, 10028010, 10006427, 10027607, 10018377, 10005826, 10029731, 10002429, 10002215, 10002163, 10002009, 10014439, 10024856, 10023080, 10025747, 10029951, 10001354,10001096, 10012944, 10016102, 50025748, 10026489, 10024368, 10003535)\r\n"
				+ "AND F_GET_TRF_NUMBER_2(BKT.TRF_ID) NOT LIKE '%10000%'\r\n" + "AND BKT.TRF_ID NOT IN "
				+ unwantedTrafficFiles + "\r\n" + "AND ROWNUM < 2");

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

	// FTF
	synchronized public String[] getVehicle(VehicleClass vehicleClass, VehicleWeight vehicleWeight,
			PlateCategory plateCategory, boolean isOrganization, boolean ownedPlate)
			throws ClassNotFoundException, SQLException {
		vehicleSelectedBefore = false;
		String platesTRFIDValue, plateNumber = "";
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
		if (ownedPlate)
			platesTRFIDValue = "TRF_ID is not null AND ";
		else
			platesTRFIDValue = "TRF_ID is null AND ";

		Statement stmt = con.createStatement();

		String[] vehilce = new String[7];
		ResultSet rs = stmt.executeQuery("SELECT F_GET_TRF_NUMBER_2(BKT.TRF_ID)TRF_NO,\r\n"
				+ "       F_DB_GET_PLATE_DETAILS(BKT.PLT_ID) AS PLATE,\r\n" + "       BKT.CHASISS_NO,\r\n"
				+ "       BKT.WEIGHT,\r\n" + "       BKT.EXPIRY_DATE\r\n"
				+ "FROM TF_VHL_BOOKLETS BKT, TF_STP_TRAFFIC_FILES TRF, TF_VHL_VEHICLES VLE, TF_STP_VEHICLE_CLASSES  VCL\r\n"
				+ "WHERE TRF.ID = BKT.TRF_ID\r\n" + "AND BKT.VLE_ID = VLE.ID\r\n" + "AND VLE.VCL_ID = VCL.ID\r\n"
				+ "AND TRF.PRS_ID " + personFlag + "\r\n" + "AND EXPIRY_DATE >= TRUNC(SYSDATE)\r\n" + "AND TRF.ORG_ID "
				+ organizationFlag + "\r\n" + "AND PLT_ID IN (SELECT PLT.ID FROM TF_VHL_PLATES PLT WHERE "
				+ platesTRFIDValue
				+ " PCD_ID in (Select PCD.ID FROM tf_vhl_plate_codes PCD WHERE plc_emi_code = 'DXB'AND PCD.plc_code = "
				+ plateCategoryId + "))\r\n" + "AND VCL.ID = " + vehicleClassId + "\r\n"
				+ "AND TRS_END_DATE IS NULL \r\n" + "AND EXPIRY_DATE IS NOT NULL\r\n" + "AND BKT.WEIGHT BETWEEN "
				+ weight[0] + " AND " + weight[1] + "\r\n" + "AND   BKT.IS_MORTGAGED = 1\r\n"
				+ "AND F_GET_TRF_NUMBER_2(BKT.TRF_ID) NOT IN (10025096, 10019316, 10009023, 10184041, 10024368, 10011937, 10012944, 10014439, 10005567)\r\n"
				+ "AND F_GET_TRF_NUMBER_2(BKT.TRF_ID) NOT LIKE '%10000%'\r\n" + "AND BKT.TRF_ID NOT IN "
				+ unwantedTrafficFiles + "\r\n" + "AND ROWNUM < " + rownum);

		int countrow = 0;
		while (rs.next()) {
			countrow++;
			vehilce[0] = rs.getString(1);
			plateNumber = rs.getString(2);
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

		}
		if (countrow == 0) {
			System.out.println(" There is No available expired vehicles in DB tables.");
			con.close();
			return null;
		}

		// closing DB Connection
		con.close();
		for (int i = 0; i < selectedVehiclesList.size(); i++) {
			if (selectedVehiclesList.get(i).equals(plateNumber)) {
				{
					vehicleSelectedBefore = true;
					break;
				}
			}
		}
		if (vehicleSelectedBefore) {
			rownum++;
			con.close();
			return getVehicle(vehicleClass, vehicleWeight, plateCategory, isOrganization, ownedPlate);
		} else {
			selectedVehiclesList.add(plateNumber);
			System.out.println(" TRF_NO = " + vehilce[0] + "  --- PLATE_NO = " + vehilce[1] + "  --- Plate_Code = "
					+ vehilce[2] + " PlateCategory = " + vehilce[3] + " Chassis = " + vehilce[4] + " Weight = "
					+ vehilce[5]);
			System.out.println("-------------------------------");
			return vehilce;
		}
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
				+ "AND VCL.id = 3\r\n" // + "AND TRS_END_DATE IS NOT NULL \r\n"
				+ "AND F_GET_TRF_NUMBER_2(BKT.TRF_ID) = " + trafficFile + " and rownum<3");

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
				+ "AND F_GET_TRF_NUMBER_2(BKT.TRF_ID) NOT IN (10027607, 10018377, 10005826, 10029731, 10002429, 10002215, 10002163, 10002009, 10014439, 10024856, 10023080, 10025747, 10029951, 10001354,10001096, 10012944, 10016102, 50025748, 10026489, 10024368, 10003535)\r\n"
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

	public void addFines(String trafficFile, String chassis) throws ClassNotFoundException, SQLException {

		setConnection();
		Statement stmt = con.createStatement();

		stmt.execute("\r\n" + 
				"DECLARE\r\n" + 
				"\r\n" + 
				"	V_TRAFFIC_FILE_NO	NUMBER(15) := "+trafficFile+";"+ 
				"\r\n" + 
				"	V_CHASSIS_NO varchar2(30) :=  '"+chassis+"';"+ 
				"    V_RSET_TRF_VIOLATION NUMBER(15) := 2;\r\n" + 
				"   	V_ADDING_BLACK_POINTS NUMBER(15) := 2;\r\n" + 
				"	V_ADDING_PAID_DUBAI_TCK NUMBER := 2;\r\n" + 
				"    V_COUNT_PAID_DXB_TCK NUMBER := 20;\r\n" + 
				"    V_ADDING_NOT_PAID_DUBAI_TCK NUMBER := 2;\r\n" + 
				"    V_COUNT_NOT_PAID_DXB_TCK NUMBER := 2;\r\n" + 
				"    V_ADDING_VEHICLE_CONFISCATION NUMBER := 2;\r\n" + 
				"    V_COUNT_VEHICLE_CONFISCATION	NUMBER := 2;\r\n" + 
				"    V_ADDING_PAID_LIC_DUBAI_TCK NUMBER := 2;\r\n" + 
				"    V_COUNT_PAID_LIC_DXB_TCK NUMBER := 2;\r\n" + 
				"    V_ADDING_NOT_PAID_LIC_DXB_TCK NUMBER := 2;\r\n" + 
				"    V_COUNT_NOT_PAID_LIC_DXB_TCK NUMBER := 2;\r\n" + 
				"    V_ADDING_PAID_AUH_TCK NUMBER := 2;\r\n" + 
				"    V_COUNT_PAID_AUH_TCK NUMBER := 2;\r\n" + 
				"    V_ADDING_NOT_PAID_AUH_TCK NUMBER := 1;\r\n" + 
				"    V_COUNT_NOT_PAID_AUH_TCK NUMBER := 2;\r\n" + 
				"    V_ADDING_LIC_AUH_TCK NUMBER := 1;\r\n" + 
				"    V_COUNT_LIC_AUH_TCK NUMBER := 2;\r\n" + 
				"    V_ADDING_PAID_SLK_TCK NUMBER := 2;\r\n" + 
				"    V_COUNT_PAID_SLK_TCK NUMBER := 2;\r\n" + 
				"    V_ADDING_NOT_PAID_SLK_TCK NUMBER := 1;\r\n" + 
				"    V_COUNT_NOT_PAID_SLK_TCK NUMBER := 2;\r\n" + 
				"    P_DLC_ID	NUMBER(15);\r\n" + 
				"    V_TCK_ID	NUMBER(15);\r\n" + 
				"    P_LICENSE_NO NUMBER(15);\r\n" + 
				"   cursor get_vle_info IS\r\n" + 
				"\r\n" + 
				"      SELECT\r\n" + 
				"	   BKT_ID,\r\n" + 
				"	   TRF_ID,\r\n" + 
				"	   VLE_ID,\r\n" + 
				"	   PLATE_NO,\r\n" + 
				"		plc_code ,\r\n" + 
				"		pcd_id ,\r\n" + 
				"		pcd_desc ,\r\n" + 
				"		plc_desc    ,PLC_EMI_CODE\r\n" + 
				"FROM (SELECT /*+ RULE */\r\n" + 
				"             MAN.ID,\r\n" + 
				"			 MAN.DESCRIPTION_A,\r\n" + 
				"			 F_DB_PRINT_VEHICLE_COLOR(BKT.VLE_ID) COLOR_DESC,\r\n" + 
				"			 VEHICLES.VCL_ID,\r\n" + 
				"			 BKT.ID BKT_ID,\r\n" + 
				"			 BKT.TRF_ID   ,\r\n" + 
				"			 VEHICLES.ID  VLE_ID,\r\n" + 
				"			 PLT.PLATE_STATUS   PLATE_STATUS,\r\n" + 
				"			 PLT.PLATE_NO,\r\n" + 
				"			 plc.code	plc_code,\r\n" + 
				"			 pcd.id		pcd_id,\r\n" + 
				"			 PLC_EMI_CODE,\r\n" + 
				"			 pcd.description_a pcd_desc,\r\n" + 
				"			 plc.description_a plc_desc\r\n" + 
				"\r\n" + 
				"			 FROM    TF_VHL_MANUFACTURERS MAN,\r\n" + 
				"			 TF_VHL_VEHICLE_MODEL MODEL,\r\n" + 
				"			 TF_VHL_BOOKLETS BKT,\r\n" + 
				"			 TF_VHL_PLATES PLT,\r\n" + 
				"			 TF_VHL_VEHICLES VEHICLES,\r\n" + 
				"			 tf_vhl_plate_codes pcd,\r\n" + 
				"			 tf_vhl_plate_categories plc\r\n" + 
				"\r\n" + 
				"	  WHERE MAN.ID=MODEL.VMK_ID  AND\r\n" + 
				"		    MODEL.ID=BKT.VSM_ID AND\r\n" + 
				"		    BKT.PLT_ID=PLT.ID AND\r\n" + 
				"		    VEHICLES.ID=BKT.VLE_ID AND\r\n" + 
				"		    VEHICLES.chasiss_no = v_chassis_no\r\n" + 
				"		    and	plt.pcd_id = pcd.id\r\n" + 
				"		    and	pcd.plc_code = plc.code\r\n" + 
				"		    and	pcd.plc_emi_code = plc.emi_code\r\n" + 
				"		     AND NOT EXISTS (SELECT 1\r\n" + 
				"			                 FROM TF_VHL_BOOKLETS BKT2\r\n" + 
				"			                  WHERE BKT2.TRS_START_DATE > BKT.TRS_START_DATE\r\n" + 
				"			                  AND BKT2.PLT_ID = PLT.ID\r\n" + 
				"			                  AND NVL(BKT2.TRS_START_DATE, SYSDATE) <= SYSDATE\r\n" + 
				"							 )\r\n" + 
				"			AND    SYSDATE BETWEEN BKT.TRS_START_DATE AND\r\n" + 
				"		    decode(plt.pcd_id,11,(BKT.TRS_START_DATE + 14), NVL(BKT.TRS_END_DATE,SYSDATE)\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"	 )\r\n" + 
				"     ORDER BY TRS_START_DATE DESC\r\n" + 
				")\r\n" + 
				"WHERE ROWNUM <=1;\r\n" + 
				"begin\r\n" + 
				"	IF V_RSET_TRF_VIOLATION = 2 THEN\r\n" + 
				"		UPDATE 	TRAFFIC.TF_FFU_VEHICLE_CONFISCATIONS\r\n" + 
				"		SET		BOOKING_STATUS = 5\r\n" + 
				"\r\n" + 
				"	  	WHERE	BOOKING_STATUS IN (1,2,3)\r\n" + 
				"	  	AND		TRF_ID =  (SELECT ID FROM traffic.TF_STP_TRAFFIC_FILES WHERE TRAFFIC_NO =V_TRAFFIC_FILE_NO ) ;\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"	  	UPDATE 	TRAFFIC.TF_FFU_TICKETS\r\n" + 
				"	  	SET		TICKET_STATUS = 5\r\n" + 
				"	    WHERE	TICKET_STATUS IN (4)\r\n" + 
				"	  	AND		TRF_ID =  (SELECT ID FROM traffic.TF_STP_TRAFFIC_FILES WHERE TRAFFIC_NO =V_TRAFFIC_FILE_NO ) ;\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"	  	UPDATE TRAFFIC.TF_FFU_CIRCULAR_TICKETS\r\n" + 
				"	  	SET		CIRCULAR_STATUS = 3\r\n" + 
				"	    WHERE	CIRCULAR_STATUS IN (1)\r\n" + 
				"	  	AND		TRF_ID =  (SELECT ID FROM traffic.TF_STP_TRAFFIC_FILES WHERE TRAFFIC_NO =V_TRAFFIC_FILE_NO ) ;\r\n" + 
				"\r\n" + 
				"	  	UPDATE TRAFFIC.TF_DRL_DRIVING_LICENSES\r\n" + 
				"	  	SET		TOTAL_BLACK_POINTS =0\r\n" + 
				"	    WHERE	TRF_ID =  (SELECT ID FROM traffic.TF_STP_TRAFFIC_FILES WHERE TRAFFIC_NO =V_TRAFFIC_FILE_NO ) ;\r\n" + 
				"\r\n" + 
				"	    update tf_stp_circular_notes\r\n" + 
				"		set	status = 1\r\n" + 
				"		where prs_id = (SELECT prs_ID FROM traffic.TF_STP_TRAFFIC_FILES WHERE TRAFFIC_NO =V_TRAFFIC_FILE_NO )\r\n" + 
				"		and status = 2 ;\r\n" + 
				"\r\n" + 
				"		 update tf_stp_circular_notes\r\n" + 
				"		set	status = 1\r\n" + 
				"		where org_id = (SELECT org_id FROM traffic.TF_STP_TRAFFIC_FILES WHERE TRAFFIC_NO =V_TRAFFIC_FILE_NO )\r\n" + 
				"		and status = 2 ;\r\n" + 
				"\r\n" + 
				"		update tf_stp_circular_notes\r\n" + 
				"		set	status = 1\r\n" + 
				"		where bkt_id in (select id from tf_vhl_booklets where trf_id = V_TRAFFIC_FILE_NO)\r\n" + 
				"		and status = 2 ;\r\n" + 
				"\r\n" + 
				"	END IF;\r\n" + 
				"	IF V_ADDING_PAID_DUBAI_TCK = 2 THEN\r\n" + 
				"		FOR	I IN 1..V_COUNT_PAID_DXB_TCK LOOP\r\n" + 
				"			FOR J IN GET_VLE_INFO LOOP\r\n" + 
				"\r\n" + 
				"				insert into traffic.tf_ffu_tickets (ID ,EMI_CODE,CTR_ID,TICKET_NO,TICKET_DATE,IS_RADAR,IS_LICENCE_NOT_PRESENTED,IS_PAYABLE,PLATE_CATEGORY_DESC,\r\n" + 
				"							TICKET_STATUS_DATE ,LOC_ID,PCD_ID,DLC_ID ,PLATE_SOURCE  ,PENALTY_FINE,OFFENSE_BLACK_PIONTS,IS_BLACK_POINTS_DELETED ,\r\n" + 
				"							TICKET_HELD_STATUS ,IS_VEHICLE_EXPORTED,HAS_EXTRA_ACTIONS   ,PLC_CODE,PLATE_NO,TOTAL_FINE,TICKET_TIME,\r\n" + 
				"							TICKET_TYPE,LICENCE_HELD_STATUS,BOOKLET_HELD_STATUS  ,TICKET_FINE,TICKET_STATUS  ,VEHICLE_SHOULD_BE_BOOKED ,\r\n" + 
				"							CREATED_BY,CREATION_DATE,UPDATED_BY,UPDATE_DATE, BKT_ID,TRF_ID ,PLC_EMI_CODE,PLATE_COLOR_DESC,\r\n" + 
				"							EMI_CODE_PLATE_FROM\r\n" + 
				"\r\n" + 
				"				)\r\n" + 
				"\r\n" + 
				"					values (tck_seq.nextval,'DXB',233,tck_seq.currval, sysdate,2,1,2,J.PLC_DESC,\r\n" + 
				"					        SYSDATE,2,J.PCD_ID,P_DLC_ID,1,0,2,1,\r\n" + 
				"					        1,1,1,J.PLC_CODE,J.PLATE_NO,0,sysdate,\r\n" + 
				"					        2,1,1,0,1,1,\r\n" + 
				"					        'TESTING_SENARIO',SYSDATE,'TESTING_SENARIO',SYSDATE, J.BKT_ID,J.TRF_ID,J.PLC_EMI_CODE ,J.pcd_desc,\r\n" + 
				"					        J.PLC_EMI_CODE\r\n" + 
				"\r\n" + 
				"							);\r\n" + 
				"               	V_TCK_ID := tck_seq.currval;\r\n" + 
				"\r\n" + 
				"               	DBMS_OUTPUT.PUT_LINE('V_TCK_ID : '||V_TCK_ID||' , J.BKT_ID:='||J.BKT_ID||' ,J.TRF_ID :'||J.TRF_ID);\r\n" + 
				"\r\n" + 
				"				insert into traffic.TF_FFU_TICKET_VIOLATIONS (ID,TCK_ID,RGL_ID,FINE_AMOUNT,NO_OF_BLACK_POINTS,VEHICLE_SHOULD_BE_BOOKED,\r\n" + 
				"																NO_OF_REPEAT,CREATED_BY,CREATION_DATE,UPDATED_BY,UPDATE_DATE\r\n" + 
				"																)\r\n" + 
				"				VALUES										(TVL_SEQ.NEXTVAL,tck_seq.currval,3408,500,0,1,1,\r\n" + 
				"															 'TESTING_SENARIO',SYSDATE,'TESTING_SENARIO',SYSDATE)\r\n" + 
				"				;\r\n" + 
				"\r\n" + 
				"				UPDATE     	traffic.tf_ffu_tickets\r\n" + 
				"				set		 	TOTAL_FINE = (select sum(FINE_AMOUNT) from TF_FFU_TICKET_VIOLATIONS where tck_id = V_TCK_ID),\r\n" + 
				"							TICKET_FINE = (select sum(FINE_AMOUNT) from TF_FFU_TICKET_VIOLATIONS where tck_id = V_TCK_ID) ,\r\n" + 
				"							TICKET_STATUS = 4\r\n" + 
				"				where		id=  V_TCK_ID ;\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"			END LOOP;\r\n" + 
				"\r\n" + 
				"		END LOOP;\r\n" + 
				"	END IF;\r\n" + 
				"	IF V_ADDING_NOT_PAID_DUBAI_TCK = 2 THEN\r\n" + 
				"		FOR	I IN 1..V_COUNT_NOT_PAID_DXB_TCK LOOP\r\n" + 
				"			FOR J IN GET_VLE_INFO LOOP\r\n" + 
				"\r\n" + 
				"				insert into traffic.tf_ffu_tickets (ID ,EMI_CODE,CTR_ID,TICKET_NO,TICKET_DATE,IS_RADAR,IS_LICENCE_NOT_PRESENTED,IS_PAYABLE,PLATE_CATEGORY_DESC,\r\n" + 
				"							TICKET_STATUS_DATE ,LOC_ID,PCD_ID,DLC_ID ,PLATE_SOURCE  ,PENALTY_FINE,OFFENSE_BLACK_PIONTS,IS_BLACK_POINTS_DELETED ,\r\n" + 
				"							TICKET_HELD_STATUS ,IS_VEHICLE_EXPORTED,HAS_EXTRA_ACTIONS   ,PLC_CODE,PLATE_NO,TOTAL_FINE,TICKET_TIME,\r\n" + 
				"							TICKET_TYPE,LICENCE_HELD_STATUS,BOOKLET_HELD_STATUS  ,TICKET_FINE,TICKET_STATUS  ,VEHICLE_SHOULD_BE_BOOKED ,\r\n" + 
				"							CREATED_BY,CREATION_DATE,UPDATED_BY,UPDATE_DATE, BKT_ID,TRF_ID ,PLC_EMI_CODE,PLATE_COLOR_DESC,\r\n" + 
				"							EMI_CODE_PLATE_FROM\r\n" + 
				"\r\n" + 
				"				)\r\n" + 
				"\r\n" + 
				"					values (tck_seq.nextval,'DXB',233,tck_seq.currval, sysdate,2,1,1,J.PLC_DESC,\r\n" + 
				"					        SYSDATE,2,J.PCD_ID,P_DLC_ID,1,0,2,1,\r\n" + 
				"					        1,1,1,J.PLC_CODE,J.PLATE_NO,0,sysdate,\r\n" + 
				"					        2,1,1,0,1,1,\r\n" + 
				"					        'TESTING_SENARIO',SYSDATE,'TESTING_SENARIO',SYSDATE, J.BKT_ID,J.TRF_ID,J.PLC_EMI_CODE ,J.pcd_desc,\r\n" + 
				"					        J.PLC_EMI_CODE\r\n" + 
				"\r\n" + 
				"							);\r\n" + 
				"               	V_TCK_ID := tck_seq.currval;\r\n" + 
				"\r\n" + 
				"               	DBMS_OUTPUT.PUT_LINE('V_TCK_ID : '||V_TCK_ID||' , J.BKT_ID:='||J.BKT_ID||' ,J.TRF_ID :'||J.TRF_ID);\r\n" + 
				"\r\n" + 
				"				insert into traffic.TF_FFU_TICKET_VIOLATIONS (ID,TCK_ID,RGL_ID,FINE_AMOUNT,NO_OF_BLACK_POINTS,VEHICLE_SHOULD_BE_BOOKED,\r\n" + 
				"																NO_OF_REPEAT,CREATED_BY,CREATION_DATE,UPDATED_BY,UPDATE_DATE\r\n" + 
				"																)\r\n" + 
				"				VALUES										(TVL_SEQ.NEXTVAL,tck_seq.currval,3374,1000,12,1,0,\r\n" + 
				"															 'TESTING_SENARIO',SYSDATE,'TESTING_SENARIO',SYSDATE)\r\n" + 
				"				;\r\n" + 
				"\r\n" + 
				"				UPDATE     	traffic.tf_ffu_tickets\r\n" + 
				"				set		 	TOTAL_FINE = (select sum(FINE_AMOUNT) from TF_FFU_TICKET_VIOLATIONS where tck_id = V_TCK_ID),\r\n" + 
				"							TICKET_FINE = (select sum(FINE_AMOUNT) from TF_FFU_TICKET_VIOLATIONS where tck_id = V_TCK_ID) ,\r\n" + 
				"							TICKET_STATUS = 4\r\n" + 
				"				where		id=  V_TCK_ID ;\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"			END LOOP;\r\n" + 
				"\r\n" + 
				"		END LOOP;\r\n" + 
				"	END IF;\r\n" + 
				"	IF V_ADDING_VEHICLE_CONFISCATION = 2 THEN\r\n" + 
				"		FOR	I IN 1..V_COUNT_VEHICLE_CONFISCATION LOOP\r\n" + 
				"			FOR J IN GET_VLE_INFO LOOP\r\n" + 
				"\r\n" + 
				"				insert into traffic.tf_ffu_tickets (ID ,EMI_CODE,CTR_ID,TICKET_NO,TICKET_DATE,IS_RADAR,IS_LICENCE_NOT_PRESENTED,IS_PAYABLE,PLATE_CATEGORY_DESC,\r\n" + 
				"							TICKET_STATUS_DATE ,LOC_ID,PCD_ID,DLC_ID ,PLATE_SOURCE  ,PENALTY_FINE,OFFENSE_BLACK_PIONTS,IS_BLACK_POINTS_DELETED ,\r\n" + 
				"							TICKET_HELD_STATUS ,IS_VEHICLE_EXPORTED,HAS_EXTRA_ACTIONS   ,PLC_CODE,PLATE_NO,TOTAL_FINE,TICKET_TIME,\r\n" + 
				"							TICKET_TYPE,LICENCE_HELD_STATUS,BOOKLET_HELD_STATUS  ,TICKET_FINE,TICKET_STATUS  ,VEHICLE_SHOULD_BE_BOOKED ,\r\n" + 
				"							CREATED_BY,CREATION_DATE,UPDATED_BY,UPDATE_DATE, BKT_ID,TRF_ID ,PLC_EMI_CODE,PLATE_COLOR_DESC,\r\n" + 
				"							EMI_CODE_PLATE_FROM\r\n" + 
				"\r\n" + 
				"				)\r\n" + 
				"\r\n" + 
				"					values (tck_seq.nextval,'DXB',233,tck_seq.currval, sysdate,2,1,1,J.PLC_DESC,\r\n" + 
				"					        SYSDATE,2,J.PCD_ID,P_DLC_ID,1,0,2,1,\r\n" + 
				"					        1,1,1,J.PLC_CODE,J.PLATE_NO,0,sysdate,\r\n" + 
				"					        2,1,1,0,1,2,\r\n" + 
				"					        'TESTING_SENARIO',SYSDATE,'TESTING_SENARIO',SYSDATE, J.BKT_ID,J.TRF_ID,J.PLC_EMI_CODE ,J.pcd_desc,\r\n" + 
				"					        J.PLC_EMI_CODE\r\n" + 
				"\r\n" + 
				"							);\r\n" + 
				"               	V_TCK_ID := tck_seq.currval;\r\n" + 
				"\r\n" + 
				"               --	DBMS_OUTPUT.PUT_LINE('V_TCK_ID : '||V_TCK_ID||' , J.BKT_ID:='||J.BKT_ID||' ,J.TRF_ID :'||J.TRF_ID);\r\n" + 
				"\r\n" + 
				"				insert into traffic.TF_FFU_TICKET_VIOLATIONS (ID,TCK_ID,RGL_ID,FINE_AMOUNT,NO_OF_BLACK_POINTS,VEHICLE_SHOULD_BE_BOOKED,\r\n" + 
				"																NO_OF_REPEAT,CREATED_BY,CREATION_DATE,UPDATED_BY,UPDATE_DATE\r\n" + 
				"																)\r\n" + 
				"				VALUES										(TVL_SEQ.NEXTVAL,tck_seq.currval,3374,1000,12,2,0 ,\r\n" + 
				"															 'TESTING_SENARIO',SYSDATE,'TESTING_SENARIO',SYSDATE)\r\n" + 
				"				;\r\n" + 
				"\r\n" + 
				"				UPDATE     	traffic.tf_ffu_tickets\r\n" + 
				"				set		 	TOTAL_FINE = (select sum(FINE_AMOUNT) from TF_FFU_TICKET_VIOLATIONS where tck_id = V_TCK_ID),\r\n" + 
				"							TICKET_FINE = (select sum(FINE_AMOUNT) from TF_FFU_TICKET_VIOLATIONS where tck_id = V_TCK_ID) ,\r\n" + 
				"							TICKET_STATUS = 4,\r\n" + 
				"							OFFENSE_BLACK_PIONTS= 12\r\n" + 
				"				where		id=  V_TCK_ID ;\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"			END LOOP;\r\n" + 
				"\r\n" + 
				"		END LOOP;\r\n" + 
				"	END IF;\r\n" + 
				"    IF V_ADDING_PAID_LIC_DUBAI_TCK = 2 THEN\r\n" + 
				"		FOR	I IN 1..V_COUNT_PAID_LIC_DXB_TCK LOOP\r\n" + 
				"			FOR J IN GET_VLE_INFO LOOP\r\n" + 
				"\r\n" + 
				"				SELECT ID , LICENSE_NO\r\n" + 
				"				INTO P_DLC_ID,P_LICENSE_NO\r\n" + 
				"\r\n" + 
				"				FROM traffic.TF_DRL_DRIVING_LICENSES\r\n" + 
				"				WHERE TRF_ID = (SELECT ID FROM traffic.TF_STP_TRAFFIC_FILES WHERE TRAFFIC_NO =V_TRAFFIC_FILE_NO );\r\n" + 
				"\r\n" + 
				"				insert into traffic.tf_ffu_tickets (ID ,EMI_CODE,CTR_ID,TICKET_NO,TICKET_DATE,IS_RADAR,IS_LICENCE_NOT_PRESENTED,IS_PAYABLE,PLATE_CATEGORY_DESC,\r\n" + 
				"							TICKET_STATUS_DATE ,LOC_ID,PCD_ID,DLC_ID ,PLATE_SOURCE  ,PENALTY_FINE,OFFENSE_BLACK_PIONTS,IS_BLACK_POINTS_DELETED ,\r\n" + 
				"							TICKET_HELD_STATUS ,IS_VEHICLE_EXPORTED,HAS_EXTRA_ACTIONS   ,PLC_CODE,PLATE_NO,TOTAL_FINE,TICKET_TIME,\r\n" + 
				"							TICKET_TYPE,LICENCE_HELD_STATUS,BOOKLET_HELD_STATUS  ,TICKET_FINE,TICKET_STATUS  ,VEHICLE_SHOULD_BE_BOOKED ,\r\n" + 
				"							CREATED_BY,CREATION_DATE,UPDATED_BY,UPDATE_DATE, BKT_ID,TRF_ID ,PLC_EMI_CODE,PLATE_COLOR_DESC,\r\n" + 
				"							EMI_CODE_PLATE_FROM   ,EMI_CODE_LICENSE_FROM,LICENSE_NO\r\n" + 
				"\r\n" + 
				"				)\r\n" + 
				"\r\n" + 
				"					values (tck_seq.nextval,'DXB',233,tck_seq.currval, sysdate,2,1,2,J.PLC_DESC,\r\n" + 
				"					        SYSDATE,2,J.PCD_ID,P_DLC_ID,1,0,2,1,\r\n" + 
				"					        1,1,1,J.PLC_CODE,J.PLATE_NO,0,sysdate,\r\n" + 
				"					        1,1,1,0,1,1,\r\n" + 
				"					        'TESTING_SENARIO',SYSDATE,'TESTING_SENARIO',SYSDATE, J.BKT_ID,J.TRF_ID,J.PLC_EMI_CODE ,J.pcd_desc,\r\n" + 
				"					        J.PLC_EMI_CODE ,'DXB'  ,P_LICENSE_NO\r\n" + 
				"\r\n" + 
				"							);\r\n" + 
				"               	V_TCK_ID := tck_seq.currval;\r\n" + 
				"\r\n" + 
				"               	DBMS_OUTPUT.PUT_LINE('V_TCK_ID : '||V_TCK_ID||' , J.BKT_ID:='||J.BKT_ID||' ,J.TRF_ID :'||J.TRF_ID);\r\n" + 
				"\r\n" + 
				"				insert into traffic.TF_FFU_TICKET_VIOLATIONS (ID,TCK_ID,RGL_ID,FINE_AMOUNT,NO_OF_BLACK_POINTS,VEHICLE_SHOULD_BE_BOOKED,\r\n" + 
				"																NO_OF_REPEAT,CREATED_BY,CREATION_DATE,UPDATED_BY,UPDATE_DATE\r\n" + 
				"																)\r\n" + 
				"				VALUES										(TVL_SEQ.NEXTVAL,tck_seq.currval,3408,500,0,1,1,\r\n" + 
				"															 'TESTING_SENARIO',SYSDATE,'TESTING_SENARIO',SYSDATE)\r\n" + 
				"				;\r\n" + 
				"\r\n" + 
				"				UPDATE     	traffic.tf_ffu_tickets\r\n" + 
				"				set		 	TOTAL_FINE = (select sum(FINE_AMOUNT) from TF_FFU_TICKET_VIOLATIONS where tck_id = V_TCK_ID),\r\n" + 
				"							TICKET_FINE = (select sum(FINE_AMOUNT) from TF_FFU_TICKET_VIOLATIONS where tck_id = V_TCK_ID) ,\r\n" + 
				"							TICKET_STATUS = 4\r\n" + 
				"				where		id=  V_TCK_ID ;\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"			END LOOP;\r\n" + 
				"\r\n" + 
				"		END LOOP;\r\n" + 
				"	END IF;\r\n" + 
				"	IF V_ADDING_NOT_PAID_LIC_DXB_TCK = 2 THEN\r\n" + 
				"		FOR	I IN 1..V_COUNT_NOT_PAID_LIC_DXB_TCK LOOP\r\n" + 
				"			FOR J IN GET_VLE_INFO LOOP\r\n" + 
				"\r\n" + 
				"				SELECT ID , LICENSE_NO\r\n" + 
				"				INTO P_DLC_ID   ,P_LICENSE_NO\r\n" + 
				"               	FROM TRAFFIC.TF_DRL_DRIVING_LICENSES\r\n" + 
				"				WHERE TRF_ID = (SELECT ID FROM TRAFFIC.TF_STP_TRAFFIC_FILES WHERE TRAFFIC_NO =V_TRAFFIC_FILE_NO );\r\n" + 
				"\r\n" + 
				"				insert into traffic.tf_ffu_tickets (ID ,EMI_CODE,CTR_ID,TICKET_NO,TICKET_DATE,IS_RADAR,IS_LICENCE_NOT_PRESENTED,IS_PAYABLE,PLATE_CATEGORY_DESC,\r\n" + 
				"							TICKET_STATUS_DATE ,LOC_ID,PCD_ID,DLC_ID ,PLATE_SOURCE  ,PENALTY_FINE,OFFENSE_BLACK_PIONTS,IS_BLACK_POINTS_DELETED ,\r\n" + 
				"							TICKET_HELD_STATUS ,IS_VEHICLE_EXPORTED,HAS_EXTRA_ACTIONS   ,PLC_CODE,PLATE_NO,TOTAL_FINE,TICKET_TIME,\r\n" + 
				"							TICKET_TYPE,LICENCE_HELD_STATUS,BOOKLET_HELD_STATUS  ,TICKET_FINE,TICKET_STATUS  ,VEHICLE_SHOULD_BE_BOOKED ,\r\n" + 
				"							CREATED_BY,CREATION_DATE,UPDATED_BY,UPDATE_DATE, BKT_ID,TRF_ID ,PLC_EMI_CODE,PLATE_COLOR_DESC,\r\n" + 
				"							EMI_CODE_PLATE_FROM ,EMI_CODE_LICENSE_FROM,LICENSE_NO\r\n" + 
				"\r\n" + 
				"				)\r\n" + 
				"\r\n" + 
				"					values (tck_seq.nextval,'DXB',233,tck_seq.currval, sysdate,2,1,1,J.PLC_DESC,\r\n" + 
				"					        SYSDATE,2,J.PCD_ID,P_DLC_ID,1,0,2,1,\r\n" + 
				"					        1,1,1,J.PLC_CODE,J.PLATE_NO,0,sysdate,\r\n" + 
				"					        1,1,1,0,1,1,\r\n" + 
				"					        'TESTING_SENARIO',SYSDATE,'TESTING_SENARIO',SYSDATE, J.BKT_ID,J.TRF_ID,J.PLC_EMI_CODE ,J.pcd_desc,\r\n" + 
				"					        J.PLC_EMI_CODE  ,'DXB' ,P_LICENSE_NO\r\n" + 
				"\r\n" + 
				"							);\r\n" + 
				"               	V_TCK_ID := tck_seq.currval;\r\n" + 
				"\r\n" + 
				"               	DBMS_OUTPUT.PUT_LINE('V_TCK_ID : '||V_TCK_ID||' , J.BKT_ID:='||J.BKT_ID||' ,J.TRF_ID :'||J.TRF_ID);\r\n" + 
				"\r\n" + 
				"				insert into traffic.TF_FFU_TICKET_VIOLATIONS (ID,TCK_ID,RGL_ID,FINE_AMOUNT,NO_OF_BLACK_POINTS,VEHICLE_SHOULD_BE_BOOKED,\r\n" + 
				"																NO_OF_REPEAT,CREATED_BY,CREATION_DATE,UPDATED_BY,UPDATE_DATE\r\n" + 
				"																)\r\n" + 
				"				VALUES										(TVL_SEQ.NEXTVAL,tck_seq.currval,3374,1000,12,1,0,\r\n" + 
				"															 'TESTING_SENARIO',SYSDATE,'TESTING_SENARIO',SYSDATE)\r\n" + 
				"				;\r\n" + 
				"\r\n" + 
				"				UPDATE     	traffic.tf_ffu_tickets\r\n" + 
				"				set		 	TOTAL_FINE = (select sum(FINE_AMOUNT) from TF_FFU_TICKET_VIOLATIONS where tck_id = V_TCK_ID),\r\n" + 
				"							TICKET_FINE = (select sum(FINE_AMOUNT) from TF_FFU_TICKET_VIOLATIONS where tck_id = V_TCK_ID) ,\r\n" + 
				"							TICKET_STATUS = 4\r\n" + 
				"				where		id=  V_TCK_ID ;\r\n" + 
				"\r\n" + 
				"				UPDATE     	traffic.tf_ffu_tickets\r\n" + 
				"				set		 	 IS_PAYABLE = 1\r\n" + 
				"				where		id=  V_TCK_ID ;\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"			END LOOP;\r\n" + 
				"\r\n" + 
				"		END LOOP;\r\n" + 
				"	END IF;\r\n" + 
				"	IF V_ADDING_PAID_AUH_TCK = 2 THEN\r\n" + 
				"		FOR	I IN 1..V_COUNT_PAID_AUH_TCK LOOP\r\n" + 
				"			FOR J IN GET_VLE_INFO LOOP\r\n" + 
				"\r\n" + 
				"				insert into traffic.tf_ffu_CIRCULAR_tickets\r\n" + 
				"							(ID,EBF_ID,TRF_ID,EMI_CODE,CIRCULAR_DATE,CIRCULAR_TICKET_NO,TICKET_DATE,LICENSE_TYPE,\r\n" + 
				"							 CIRCULAR_FINE,DLC_ID,BKT_ID,TICKET_TIME,IS_PAYABLE,PENALTY_FINE,TOTAL_FINE,\r\n" + 
				"							 CIRCULAR_STATUS,CIRCULAR_STATUS_DATE,CREATED_BY,CREATION_DATE,UPDATED_BY,UPDATE_DATE,\r\n" + 
				"							 PCD_ID,PLATE_NO,IS_BLACK_POINTS_DELETED\r\n" + 
				"\r\n" + 
				"				)\r\n" + 
				"\r\n" + 
				"					values (CTK_SEQ.NEXTVAL,3,J.TRF_ID,'DXB',SYSDATE,CTK_SEQ.CURRVAL,SYSDATE,NULL,\r\n" + 
				"					        500,null,J.BKT_ID,SYSDATE,2,0,500,\r\n" + 
				"					        1,SYSDATE,'uts_user',SYSDATE,'uts_user',SYSDATE,\r\n" + 
				"					        J.PCD_ID,J.PLATE_NO,1\r\n" + 
				"\r\n" + 
				"							);\r\n" + 
				"               	V_TCK_ID := tck_seq.currval;\r\n" + 
				"\r\n" + 
				"               	DBMS_OUTPUT.PUT_LINE('V_TCK_ID : '||V_TCK_ID||' , J.BKT_ID:='||J.BKT_ID||' ,J.TRF_ID :'||J.TRF_ID);\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"			END LOOP;\r\n" + 
				"\r\n" + 
				"		END LOOP;\r\n" + 
				"	END IF;\r\n" + 
				"	IF V_ADDING_NOT_PAID_AUH_TCK = 2 THEN\r\n" + 
				"		FOR	I IN 1..V_COUNT_NOT_PAID_AUH_TCK LOOP\r\n" + 
				"			FOR J IN GET_VLE_INFO LOOP\r\n" + 
				"\r\n" + 
				"				 insert into traffic.tf_ffu_CIRCULAR_tickets\r\n" + 
				"							(ID,EBF_ID,TRF_ID,EMI_CODE,CIRCULAR_DATE,CIRCULAR_TICKET_NO,TICKET_DATE,LICENSE_TYPE,\r\n" + 
				"							 CIRCULAR_FINE,DLC_ID,BKT_ID,TICKET_TIME,IS_PAYABLE,PENALTY_FINE,TOTAL_FINE,\r\n" + 
				"							 CIRCULAR_STATUS,CIRCULAR_STATUS_DATE,CREATED_BY,CREATION_DATE,UPDATED_BY,UPDATE_DATE,\r\n" + 
				"							 PCD_ID,PLATE_NO,IS_BLACK_POINTS_DELETED\r\n" + 
				"\r\n" + 
				"				)\r\n" + 
				"\r\n" + 
				"					values (CTK_SEQ.NEXTVAL,3,J.TRF_ID,'DXB',SYSDATE,CTK_SEQ.CURRVAL,SYSDATE,NULL,\r\n" + 
				"					        500,null,J.BKT_ID,SYSDATE,1,0,1000,\r\n" + 
				"					        1,SYSDATE,'uts_user',SYSDATE,'uts_user',SYSDATE,\r\n" + 
				"					        J.PCD_ID,J.PLATE_NO,1\r\n" + 
				"\r\n" + 
				"							);\r\n" + 
				"               	V_TCK_ID := tck_seq.currval;\r\n" + 
				"\r\n" + 
				"               	DBMS_OUTPUT.PUT_LINE('V_TCK_ID : '||V_TCK_ID||' , J.BKT_ID:='||J.BKT_ID||' ,J.TRF_ID :'||J.TRF_ID);\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"			END LOOP;\r\n" + 
				"\r\n" + 
				"		END LOOP;\r\n" + 
				"	END IF;\r\n" + 
				"	IF V_ADDING_PAID_SLK_TCK = 2 THEN\r\n" + 
				"		FOR	I IN 1..V_COUNT_PAID_SLK_TCK LOOP\r\n" + 
				"			FOR J IN GET_VLE_INFO LOOP\r\n" + 
				"\r\n" + 
				"				insert into traffic.tf_ffu_CIRCULAR_tickets\r\n" + 
				"							(ID,EBF_ID,TRF_ID,EMI_CODE,CIRCULAR_DATE,CIRCULAR_TICKET_NO,TICKET_DATE,LICENSE_TYPE,\r\n" + 
				"							 CIRCULAR_FINE,DLC_ID,BKT_ID,TICKET_TIME,IS_PAYABLE,PENALTY_FINE,TOTAL_FINE,\r\n" + 
				"							 CIRCULAR_STATUS,CIRCULAR_STATUS_DATE,CREATED_BY,CREATION_DATE,UPDATED_BY,UPDATE_DATE,\r\n" + 
				"							 PCD_ID,PLATE_NO,IS_BLACK_POINTS_DELETED\r\n" + 
				"\r\n" + 
				"				)\r\n" + 
				"\r\n" + 
				"					values (CTK_SEQ.NEXTVAL,10,J.TRF_ID,'DXB',SYSDATE,CTK_SEQ.CURRVAL,SYSDATE,NULL,\r\n" + 
				"					        500,null,J.BKT_ID,SYSDATE,2,0,500,\r\n" + 
				"					        1,SYSDATE,'salik',SYSDATE,'salik',SYSDATE,\r\n" + 
				"					        J.PCD_ID,J.PLATE_NO,1\r\n" + 
				"\r\n" + 
				"							);\r\n" + 
				"               	V_TCK_ID := tck_seq.currval;\r\n" + 
				"\r\n" + 
				"               	DBMS_OUTPUT.PUT_LINE('V_TCK_ID : '||V_TCK_ID||' , J.BKT_ID:='||J.BKT_ID||' ,J.TRF_ID :'||J.TRF_ID);\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"			END LOOP;\r\n" + 
				"\r\n" + 
				"		END LOOP;\r\n" + 
				"	END IF;\r\n" + 
				"	IF V_ADDING_NOT_PAID_SLK_TCK = 2 THEN\r\n" + 
				"		FOR	I IN 1..V_COUNT_NOT_PAID_SLK_TCK LOOP\r\n" + 
				"			FOR J IN GET_VLE_INFO LOOP\r\n" + 
				"\r\n" + 
				"				 insert into traffic.tf_ffu_CIRCULAR_tickets\r\n" + 
				"							(ID,EBF_ID,TRF_ID,EMI_CODE,CIRCULAR_DATE,CIRCULAR_TICKET_NO,TICKET_DATE,LICENSE_TYPE,\r\n" + 
				"							 CIRCULAR_FINE,dlc_id,BKT_ID,TICKET_TIME,IS_PAYABLE,PENALTY_FINE,TOTAL_FINE,\r\n" + 
				"							 CIRCULAR_STATUS,CIRCULAR_STATUS_DATE,CREATED_BY,CREATION_DATE,UPDATED_BY,UPDATE_DATE,\r\n" + 
				"							 PCD_ID,PLATE_NO,IS_BLACK_POINTS_DELETED\r\n" + 
				"\r\n" + 
				"				)\r\n" + 
				"\r\n" + 
				"					values (CTK_SEQ.NEXTVAL,10,J.TRF_ID,'DXB',SYSDATE,CTK_SEQ.CURRVAL,SYSDATE,NULL,\r\n" + 
				"					        500,null,J.BKT_ID,SYSDATE,1,0,1000,\r\n" + 
				"					        1,SYSDATE,'salik',SYSDATE,'salik',SYSDATE,\r\n" + 
				"					        J.PCD_ID,J.PLATE_NO,1\r\n" + 
				"\r\n" + 
				"							);\r\n" + 
				"               	V_TCK_ID := tck_seq.currval;\r\n" + 
				"\r\n" + 
				"               	DBMS_OUTPUT.PUT_LINE('V_TCK_ID : '||V_TCK_ID||' , J.BKT_ID:='||J.BKT_ID||' ,J.TRF_ID :'||J.TRF_ID);\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"			END LOOP;\r\n" + 
				"\r\n" + 
				"		END LOOP;\r\n" + 
				"	END IF;\r\n" + 
				"	IF V_ADDING_LIC_AUH_TCK = 2 THEN\r\n" + 
				"		FOR	I IN 1..V_COUNT_LIC_AUH_TCK LOOP\r\n" + 
				"			FOR J IN GET_VLE_INFO LOOP\r\n" + 
				"\r\n" + 
				"				SELECT ID , LICENSE_NO\r\n" + 
				"				INTO P_DLC_ID   ,P_LICENSE_NO\r\n" + 
				"               	FROM TRAFFIC.TF_DRL_DRIVING_LICENSES\r\n" + 
				"				WHERE TRF_ID = (SELECT ID FROM TRAFFIC.TF_STP_TRAFFIC_FILES WHERE TRAFFIC_NO =V_TRAFFIC_FILE_NO );\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"				 insert into traffic.tf_ffu_CIRCULAR_tickets\r\n" + 
				"							(ID,EBF_ID,TRF_ID,EMI_CODE,CIRCULAR_DATE,CIRCULAR_TICKET_NO,TICKET_DATE,LICENSE_TYPE,\r\n" + 
				"							 CIRCULAR_FINE,DLC_ID,BKT_ID,TICKET_TIME,IS_PAYABLE,PENALTY_FINE,TOTAL_FINE,\r\n" + 
				"							 CIRCULAR_STATUS,CIRCULAR_STATUS_DATE,CREATED_BY,CREATION_DATE,UPDATED_BY,UPDATE_DATE,\r\n" + 
				"							 PCD_ID,PLATE_NO,IS_BLACK_POINTS_DELETED  ,LICENSE_NO\r\n" + 
				"\r\n" + 
				"				)\r\n" + 
				"\r\n" + 
				"					values (CTK_SEQ.NEXTVAL,3,J.TRF_ID,'DXB',SYSDATE,CTK_SEQ.CURRVAL,SYSDATE,NULL,\r\n" + 
				"					        500,P_DLC_ID,NULL,SYSDATE,1,0,1000,\r\n" + 
				"					        1,SYSDATE,'uts_user',SYSDATE,'uts_user',SYSDATE,\r\n" + 
				"					        J.PCD_ID,J.PLATE_NO,1,P_LICENSE_NO\r\n" + 
				"							);\r\n" + 
				"               	V_TCK_ID := tck_seq.currval;\r\n" + 
				"\r\n" + 
				"               	DBMS_OUTPUT.PUT_LINE('V_TCK_ID : '||V_TCK_ID||' , J.BKT_ID:='||J.BKT_ID||' ,J.TRF_ID :'||J.TRF_ID);\r\n" + 
				"			END LOOP;\r\n" + 
				"\r\n" + 
				"		END LOOP;\r\n" + 
				"	END IF;\r\n" + 
				"\r\n" + 
				"	IF V_ADDING_BLACK_POINTS = 2 THEN\r\n" + 
				"\r\n" + 
				"		UPDATE TRAFFIC.TF_DRL_DRIVING_LICENSES\r\n" + 
				"	  	SET		TOTAL_BLACK_POINTS = 24\r\n" + 
				"	    WHERE	TRF_ID =  (SELECT ID FROM traffic.TF_STP_TRAFFIC_FILES WHERE TRAFFIC_NO =V_TRAFFIC_FILE_NO ) ;\r\n" + 
				"   END IF;\r\n" + 
				"\r\n" + 
				"   commit;\r\n" + 
				"END;\r\n" + 
				"");

		System.out.println(("Fines were added successfully."));
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
		ResultSet rs = stmt.executeQuery(
				"Select CASE_DESCRIOTION From TF_STP_KEDB_SUPPORT_CASES Where CASE_DESCRIOTION IN (" + eventListString
						+ ")\r\n" + "AND CREATION_DATE >= to_date('" + testDateTime + "' , 'dd/mm/yyyy hh24:mi:ss')");

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

	public boolean checkVLDFeesEvent(VHLTransaction trns, String testDateTime)
			throws ClassNotFoundException, SQLException {

		setConnection();
		String trnsNo = getVLDTrnsNo(trns);
		Statement stmt = con.createStatement();

		ResultSet rs = stmt.executeQuery("SELECT * FROM\r\n"
				+ "(SELECT TRS.ID  trs_id,trs.STATUS_DATE rpt_status_date, Traffic.PKG_VLD_CHECK_EVENTS.F_VALDATE_"+ trns +"(TRS.ID ,trs.svc_code_vhl)check_status\r\n"
				+ "	from 		TF_STP_TRANSACTIONS TRS\r\n"
				+ "	WHERE 		TRS.STATUS	IN (3,6) AND SVC_CODE_VHL IN (" + trnsNo
				+ ") AND TRS.STATUS_DATE >= to_date('" + testDateTime + "' , 'dd/mm/yyyy hh24:mi:ss')) " + "Where check_status <> 'ALL FEES are Corrected'");

		if (rs.next()) {
			System.out.println(" Test cases Failed  *Fees events Found* - Plese check fired Events ");
			con.close();
			return false;
		} else {
			con.close();
			return true;
		}
	}

	private String getVLDTrnsNo(VHLTransaction trns) {
		String trnsNo;
		switch (trns) {
		case REGISTRATION:
			trnsNo = "201";
			break;
		case VLD_CHANGE_OWNERSHIP:
			trnsNo = "203,202";
			break;
		case VEHICLE_RENEWAL:
			trnsNo = "204";
			break;
		case VLD_EXPORT:
			trnsNo = "206";
			break;
		case VLD_TRANSFER:
			trnsNo = "207";
			break;
		case VLD_RENEWAL_WITH_NO:
			trnsNo = "224";
			break;
		case VLD_POSSESSION:
			trnsNo = "205";
			break;
		case VLD_CHANGE_POSS:
			trnsNo = "240";
			break;
		case VLD_CHANGE_INFO:
			trnsNo = "208";
			break;
		case VLD_CHANGE_NO:
			trnsNo = "209";
			break;
		case VLD_TOURISM_CERT:
			trnsNo = "212";
			break;
		case VLD_CLEARANCE_CERT:
			trnsNo = "213";
			break;
		case VLD_OWNERSHIP_CERT:
			trnsNo = "214";
			break;
		case VLD_NO_OWNERSHIP_CRT:
			trnsNo = "215";
			break;
		case VLD_LOSS_EXPORT_CRT:
			trnsNo = "219";
			break;
		case VLD_LOSS_POSS_CRT:
			trnsNo = "220";
			break;
		case VLD_RET_TOURISM_CERT:
			trnsNo = "225";
			break;
		case VLD_LOSS_BOOKLET:
			trnsNo = "211";
			break;
		case VLD_LOSS_PLATE:
			trnsNo = "218";
			break;
		case VLD_RENEW_RES_PLT:
			trnsNo = "210";
			break;
		case VLD_ISSUE_TRA_PLT:
			trnsNo = "221";
			break;
		case VLD_RENEW_TRA_PLT:
			trnsNo = "222";
			break;
		case VLD_CANCEL_TRA_PLT:
			trnsNo = "223";
			break;
		case VLD_IMPORT_VLD:
			trnsNo = "226";
			break;
		case VLD_APPROVE_MORG:
			trnsNo = "248";
			break;
		case VLD_RELEASE_MORG:
			trnsNo = "251";
			break;
		case VLD_SALES_TRS:
			trnsNo = "228";
			break;
		default:
			trnsNo = "";
			break;
		}
		return trnsNo;
	}

	private void setConnection() throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");

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
		case ERDB:
			connection = "jdbc:oracle:thin:@10.11.206.11:1527:erdb";
			Username = "ELKADY_ER"; // "a_nour_stg";//
			password = "ELKADY_ER";//
			break;
		case TRFER:
			connection = "jdbc:oracle:thin:@10.11.206.11:1527:trfer";
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

		ResultSet rs = stmt.executeQuery("SELECT TRF.TRAFFIC_NO,DLC.LICENSE_NO,TO_CHAR(PRS.BIRTH_DATE,'YYYY') BIRTH_YEAR,TO_CHAR(DLC.ISSUE_DATE,'DD-MM-YYYY'),PRS.ID PRS_ID\r\n" + 
				"FROM TF_DRL_DRIVING_LICENSES DLC,TF_STP_TRAFFIC_FILES TRF,TF_STP_PERSONS PRS\r\n" + 
				"WHERE	TRF.ID = DLC.TRF_ID AND		TRF.PRS_ID = PRS.ID AND		DLC.STATUS = 2 AND		DLC.LICENSE_STATUS = 0 AND		DLC.EXPIRY_DATE >= TRUNC(SYSDATE)-500 AND		DLC.EXPIRY_DATE <  TRUNC(SYSDATE)\r\n" + 
				"AND	EXISTS(SELECT 	1 FROM 	TF_STP_OPTICAL_RESULTS OPR WHERE	OPR.PRS_ID = PRS.ID AND		OPR.TEST_DATE >= TRUNC(SYSDATE)-300)\r\n" + 
				"AND	PRS.CID_ID IS NOT NULL AND		PRS.RES_EXPIRY_DATE >= TRUNC(SYSDATE) AND		PRS.CID_EXPIRY_DATE >= TRUNC(SYSDATE) AND		PRS.CTY_CODE_VISA = '03'\r\n" + 
				"AND	NOT EXISTS (SELECT 1 FROM 	TF_FFU_TICKETS TCK WHERE	TCK.TRF_ID = TRF.ID AND		TCK.TICKET_STATUS = 4)AND NOT EXISTS (SELECT 1 FROM 	TF_FFU_CIRCULAR_TICKETS ctk WHERE	ctk.TRF_ID = TRF.ID AND		ctk.CIRCULAR_STATUS = 1)\r\n" + 
				"AND	ROWNUM <=1");

		int countrow = 0;
		while (rs.next()) {
			countrow++;
			person[0] = rs.getString(1);
			person[1] = rs.getString(2);
			person[2] = rs.getString(3);
			person[3] = rs.getString(4);
			person[4] = rs.getString(5);
			System.out.println("TRF_NO = " + person[0] + "  --- License = " + person[1] + "  --- BirthYear = "
					+ person[2] + "  --- IssueDate = " + person[3] +  "  --- Person ID = " + person[4]);
		}
		if (countrow == 0) {
			System.out.println(" No expired licenses found in DB.");
		}

		// closing DB Connection
		con.close();
		return person;
	}
	
	public void updatePersonAge(String PersonID, String Age) throws ClassNotFoundException, SQLException
	{
		setConnection();
		PreparedStatement preparedStmt = con
				.prepareStatement("UPDATE	TF_STP_PERSONS SET     BIRTH_DATE = ADD_MONTHS(TRUNC(SYSDATE),-(?*12)),EID_DATA_SOURCE = 1 WHERE	ID = ?");
		preparedStmt.setString(1, PersonID);
		preparedStmt.setString(2, Age);
		preparedStmt.executeUpdate();
		System.out.println(" Age of person ID  (" + PersonID + ")" + "updated to "+ "(" + Age + ")");
		con.close();
	}
	
	public void updatePersonPhoto(String PersonID) throws ClassNotFoundException, SQLException
	{
		setConnection();
		PreparedStatement preparedStmt = con
				.prepareStatement("UPDATE TF_STP_PERSON_PHOTOS SET PRS_ID =? ,update_date=sysdate WHERE rownum<2 and update_date<trunc(sysdate) and not exists( select 1 from  TF_STP_PERSON_PHOTOS pph1 where pph1.prs_id = ?)");
		preparedStmt.setString(1, PersonID);
		preparedStmt.setString(2, PersonID);
		int result = preparedStmt.executeUpdate();
		System.out.println("No of rows updated : " + result);
		System.out.println(" Photo updated for person  (" + PersonID + ")" );
		con.close();
	}
	public void updateEXCEPTIONS(String TRF_ID) throws ClassNotFoundException, SQLException
	{
		setConnection();
		PreparedStatement preparedStmt = con
				.prepareStatement("UPDATE TF_STP_EXCEPTIONS SET ALLOWED_END_DATE = TRUNC(SYSDATE+1) WHERE TRF_ID= ?");
		preparedStmt.setString(1, TRF_ID);
		int result = preparedStmt.executeUpdate();
		System.out.println("No of rows updated : " + result);
		System.out.println(" Exception updated for traffic file:   (" + TRF_ID + ")" );
		con.close();
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

	public String getTradePlate() throws ClassNotFoundException, SQLException {
		setConnection();
		Statement stmt = con.createStatement();

		String plateNumber = "";

		ResultSet rs = stmt
				.executeQuery("Select PLATE_NO from TRAFFIC.TF_VHL_BOOKLETS bkt, TRAFFIC.TF_VHL_PLATES plt\r\n"
						+ "Where BKT.PLT_ID = plt.id And plt.PCD_ID = 10 AND rownum <2");

		int countrow = 0;
		while (rs.next()) {
			countrow++;
			plateNumber = rs.getString(1);

			System.out.println(" Renew Trade plateNumber =     " + plateNumber);
		}
		if (countrow == 0) {
			System.out.println(" There are no Trade plates in DB.");
		}

		// closing DB Connection
		con.close();
		return plateNumber;
	}

	public String getTradePlateCancel() throws ClassNotFoundException, SQLException {
		setConnection();
		Statement stmt = con.createStatement();

		String plateNumber = "";

		ResultSet rs = stmt
				.executeQuery("Select PLATE_NO from TRAFFIC.TF_VHL_BOOKLETS bkt, TRAFFIC.TF_VHL_PLATES plt\r\n"
						+ "Where BKT.PLT_ID = plt.id And plt.PCD_ID = 10 AND rownum <3");

		int countrow = 0;
		while (rs.next()) {
			countrow++;
			plateNumber = rs.getString(1);

			System.out.println(" Cancel Trade plateNumber =     " + plateNumber);
		}
		if (countrow == 0) {
			System.out.println(" There are no Trade plates in DB.");
		}

		// closing DB Connection
		con.close();
		return plateNumber;
	}

	public String getPossessionCertificateNo(String execludedTrafficFile) throws ClassNotFoundException, SQLException {
		setConnection();
		Statement stmt = con.createStatement();

		String CertificateNo = "";

		ResultSet rs = stmt.executeQuery("Select CERTIFICATE_NO\r\n"
				+ "FROM TF_VHL_BOOKLETS BKT, TF_STP_TRAFFIC_FILES TRF, TF_VHL_VEHICLES vle\r\n"
				+ "WHERE TRF.ID = BKT.TRF_ID\r\n" + "AND bkt.vle_id = vle.id\r\n" + "AND bkt.trs_type = 8 \r\n"
				+ "AND trs_start_date >= (select max(bkt2.trs_start_date) from TF_VHL_BOOKLETS BKT2 where	bkt.vle_id = bkt2.vle_id )\r\n"
				+ "AND trs_start_date >= to_date('1/1/2019','dd/mm/yyyy')\r\n"
				+ "AND F_GET_TRF_NUMBER_2(BKT.TRF_ID) <> '" + execludedTrafficFile + "'\r\n" + "AND ROWNUM < 2\r\n"
				+ "ORDER BY BKT.TRS_START_DATE Desc");

		int countrow = 0;
		while (rs.next()) {
			countrow++;
			CertificateNo = rs.getString(1);

			System.out.println(" Possesion Certificate Number =     " + CertificateNo);
		}
		if (countrow == 0) {
			System.out.println(" There are no Certificates in DB.");
		}

		// closing DB Connection
		con.close();
		return CertificateNo;
	}

	public String[] getExportCertificateData() throws ClassNotFoundException, SQLException {
		setConnection();
		Statement stmt = con.createStatement();

		String[] certificateData = new String[2];

		ResultSet rs = stmt.executeQuery("Select CERTIFICATE_NO, F_GET_TRF_NUMBER_2(BKT.TRF_ID)\r\n"
				+ "FROM TF_VHL_BOOKLETS BKT, TF_STP_TRAFFIC_FILES TRF, TF_VHL_VEHICLES vle\r\n"
				+ "WHERE TRF.ID = BKT.TRF_ID\r\n" + "AND bkt.vle_id = vle.id\r\n" + "AND bkt.trs_type = 9 \r\n"
				+ "AND trs_start_date >= (select max(bkt2.trs_start_date) from TF_VHL_BOOKLETS BKT2 where	bkt.vle_id = bkt2.vle_id )\r\n"
				+ "AND trs_start_date >= to_date('1/1/2019','dd/mm/yyyy')\r\n" + "AND ROWNUM < 2\r\n"
				+ "ORDER BY BKT.TRS_START_DATE Desc");

		int countrow = 0;
		while (rs.next()) {
			countrow++;
			certificateData[0] = rs.getString(1);
			certificateData[1] = rs.getString(2);

			System.out
					.println(" Export CertificateNo = " + certificateData[0] + "   TrafficFile= " + certificateData[1]);
		}
		if (countrow == 0) {
			System.out.println(" There are no Certificates in DB.");
		}

		// closing DB Connection
		con.close();
		return certificateData;
	}

	public String[] getPossessionCertificateData() throws ClassNotFoundException, SQLException {
		setConnection();
		Statement stmt = con.createStatement();

		String[] certificateData = new String[2];

		ResultSet rs = stmt.executeQuery("Select CERTIFICATE_NO, F_GET_TRF_NUMBER_2(BKT.TRF_ID)\r\n"
				+ "FROM TF_VHL_BOOKLETS BKT, TF_STP_TRAFFIC_FILES TRF, TF_VHL_VEHICLES vle\r\n"
				+ "WHERE TRF.ID = BKT.TRF_ID\r\n" + "AND bkt.vle_id = vle.id\r\n" + "AND bkt.trs_type = 8 \r\n"
				+ "AND trs_start_date >= (select max(bkt2.trs_start_date) from TF_VHL_BOOKLETS BKT2 where	bkt.vle_id = bkt2.vle_id )\r\n"
				+ "AND trs_start_date >= to_date('1/1/2019','dd/mm/yyyy')\r\n" + "AND ROWNUM < 2\r\n"
				+ "ORDER BY BKT.TRS_START_DATE Desc");

		int countrow = 0;
		while (rs.next()) {
			countrow++;
			certificateData[0] = rs.getString(1);
			certificateData[1] = rs.getString(2);

			System.out
					.println(" Export CertificateNo = " + certificateData[0] + "   TrafficFile= " + certificateData[1]);
		}
		if (countrow == 0) {
			System.out.println(" There are no Certificates in DB.");
		}

		// closing DB Connection
		con.close();
		return certificateData;
	}

	
	/////// DRL
	public String getTrfFileHasManualLicense() throws ClassNotFoundException, SQLException {
		setConnection();
		Statement stmt = con.createStatement();

		// step4 execute query
		String trafficFile = "";
		ResultSet rs = stmt.executeQuery("SELECT 	TRF.TRAFFIC_NO,\r\n" + 
				"		TO_CHAR(PRS.BIRTH_DATE,'YYYY') BIRTH_YEAR,\r\n" + 
				"		PRS.NAME_A,\r\n" + 
				"		VCL.DESCRIPTION_A VEHICLE_CLASS,\r\n" + 
				"		APP.CTR_ID TEST_CENTER_ID,\r\n" + 
				"		CTR.NAME_A TEST_CENTER_ARABIC_NAME\r\n" + 
				"FROM 	TF_DTT_LICENSE_APPLICATIONS APP,\r\n" + 
				"		TF_STP_PERSONS PRS,\r\n" + 
				"		TF_STP_TRAFFIC_FILES TRF,\r\n" + 
				"		TF_STP_VEHICLE_CLASSES VCL,\r\n" + 
				"		TF_STP_CENTERS CTR\r\n" + 
				"WHERE	APP.PRS_ID = PRS.ID\r\n" + 
				"AND		TRF.PRS_ID = PRS.ID\r\n" + 
				"AND		APP.VCL_ID = VCL.ID\r\n" + 
				"AND		APP.CTR_ID = CTR.ID\r\n" + 
				"AND		APP.STATUS = 1\r\n" + 
				"AND		APP.CTR_ID IN(18)\r\n" + 
				"AND		APP.VCL_ID IN(3)\r\n" + 
				"--AND		TRL.APPOINT_DATE >= TO_DATE('30/05/2015','DD/MM/YYYY')\r\n" + 
				"AND  	NOT EXISTS(SELECT 1\r\n" + 
				"							   FROM   TF_DTT_TRIALS TRL\r\n" + 
				"							   WHERE  TRL.AEX_APP_ID  = APP.ID)\r\n" + 
				"AND		EXISTS(SELECT  1\r\n" + 
				"				FROM 	TF_DTT_LEARNING_PERMITS LEP\r\n" + 
				"				WHERE	LEP.PERMIT_EXPIRY_DATE > tRUNC(SYSDATE)\r\n" + 
				"				AND		LEP.APP_ID = APP.ID )\r\n" + 
				"AND		EXISTS(SELECT 	1\r\n" + 
				"			   FROM 	TF_STP_OPTICAL_RESULTS OPR\r\n" + 
				"			   WHERE	OPR.PRS_ID = PRS.ID\r\n" + 
				"			   AND		OPR.TEST_DATE >= TRUNC(SYSDATE)-300\r\n" + 
				"			   and		OPR.RIGHT_EYE_RESULT = 1\r\n" + 
				"			   And      OPR.LEFT_EYE_RESULT = 1)\r\n" + 
				"AND		ROWNUM <=1\r\n" + 
				"ORDER BY CTR.ID,TRAFFIC_NO");

		rs.next();
		try {
			System.out.println(("getTrfFileHasManualLicense: " + rs.getString(1)));
			trafficFile = rs.getString(1);
			con.close();
		} catch (Exception e) {
			con.close();
			System.out.println("There is no TrfFiles have manual licenses.");
		}
		return trafficFile;
	}
	
	public String getTrfFile_0009() throws ClassNotFoundException, SQLException {
		setConnection();
		Statement stmt = con.createStatement();

		// step4 execute query
		String trafficFile = "";
		ResultSet rs = stmt.executeQuery("SELECT 	TRF.TRAFFIC_NO, TO_CHAR(PRS.BIRTH_DATE,'YYYY') BIRTH_YEAR, PRS.NAME_A, VCL.DESCRIPTION_A VEHICLE_CLASS, APP.CTR_ID TEST_CENTER_ID, CTR.NAME_A TEST_CENTER_ARABIC_NAME\r\n" + 
				"FROM TF_DTT_LICENSE_APPLICATIONS APP, TF_STP_PERSONS PRS, TF_STP_TRAFFIC_FILES TRF, TF_STP_VEHICLE_CLASSES VCL, TF_STP_CENTERS CTR\r\n" + 
				"WHERE APP.PRS_ID = PRS.ID AND TRF.PRS_ID = PRS.ID AND APP.VCL_ID = VCL.ID AND APP.CTR_ID = CTR.ID AND APP.STATUS = 1 AND APP.CTR_ID IN(18) AND APP.VCL_ID IN(3)\r\n" + 
				"AND EXISTS(SELECT 1 FROM TF_DTT_APPLICANT_EXAMS AEX WHERE AEX.APP_ID = APP.ID AND AEX.CLS_ID = 12 AND AEX.LATEST_RESULT = 1 AND AEX.IS_PEARSON = 2)\r\n" + 
				"AND EXISTS(SELECT 1 FROM TF_DTT_APPLICANT_ELIGIBILITIES AEG WHERE AEG.AEX_APP_ID  = APP.ID AND AEG.AEX_CLS_ID = 12 AND AEG.STATUS = 1)\r\n" + 
				"AND EXISTS(SELECT 1 FROM TF_DTT_LEARNING_PERMITS LEP WHERE LEP.PERMIT_EXPIRY_DATE > tRUNC(SYSDATE) AND LEP.APP_ID = APP.ID )\r\n" + 
				"AND EXISTS(SELECT 1 FROM TF_STP_OPTICAL_RESULTS OPR WHERE OPR.PRS_ID = PRS.ID AND OPR.TEST_DATE >= TRUNC(SYSDATE)-300 and OPR.RIGHT_EYE_RESULT = 1 And OPR.LEFT_EYE_RESULT = 1)\r\n" + 
				"AND ROWNUM <=1\r\n" + 
				"ORDER BY CTR.ID,TRAFFIC_NO");

		rs.next();
		try {
			System.out.println(("getTrfFileHasManualLicense: " + rs.getString(1)));
			trafficFile = rs.getString(1);
			con.close();
		} catch (Exception e) {
			con.close();
			System.out.println("There is no TrfFiles have manual licenses.");
		}
		return trafficFile;
	}
	
	public String getTrfFile_0034() throws ClassNotFoundException, SQLException {
		setConnection();
		Statement stmt = con.createStatement();

		// step4 execute query
		String trafficFile = "";
		ResultSet rs = stmt.executeQuery("SELECT 	TRF.TRAFFIC_NO, DLC.LICENSE_NO, TO_CHAR(PRS.BIRTH_DATE,'YYYY') BIRTH_YEAR, PRS.NAME_A\r\n" + 
				"FROM TF_DRL_DRIVING_LICENSES DLC, TF_STP_TRAFFIC_FILES TRF, TF_STP_PERSONS PRS\r\n" + 
				"WHERE TRF.ID = DLC.TRF_ID AND TRF.PRS_ID = PRS.ID AND DLC.STATUS = 2 AND DLC.LICENSE_STATUS = 0 AND DLC.EXPIRY_DATE >= TRUNC(SYSDATE)-100 AND DLC.EXPIRY_DATE >= TRUNC(SYSDATE)\r\n" + 
				"AND PRS.CID_ID IS NOT NULL AND PRS.RES_EXPIRY_DATE >= TRUNC(SYSDATE) AND PRS.CID_EXPIRY_DATE >= TRUNC(SYSDATE) AND PRS.CTY_CODE_VISA = '03' AND ROWNUM <=1");

		rs.next();
		try {
			System.out.println(("getTrfFile_0034: " + rs.getString(1)));
			trafficFile = rs.getString(1);
			con.close();
		} catch (Exception e) {
			con.close();
			System.out.println("There is no TrfFiles getTrfFile_0034.");
		}
		return trafficFile;
	}
	
	public String getTrfFile_0087() throws ClassNotFoundException, SQLException {
		setConnection();
		Statement stmt = con.createStatement();

		// step4 execute query
		String trafficFile = "";
		ResultSet rs = stmt.executeQuery("SELECT 	TRAFFIC_NO, PRS.ID PRS_ID, DLC.LICENSE_NO, DLC.EXPIRY_DATE , DLC.ID DLC_ID\r\n" + 
				"FROM TF_STP_TRAFFIC_FILES TRF, TF_DRL_DRIVING_LICENSES DLC, TF_STP_PERSONS PRS\r\n" + 
				"WHERE TRF.PRS_ID = PRS.ID AND DLC.TRF_ID = TRF.ID \r\n" + 
				"AND PRS.GENDER = 2 AND EXISTS (SELECT  1 FROM TF_DRL_LICENSE_CLASSES LCL WHERE LCL.VCL_ID IN(3) AND LCL.STATUS = 2)\r\n" + 
				"AND DLC.STATUS = 2 AND DLC.LICENSE_STATUS = 0 AND DLC.EXPIRY_DATE > TRUNC(SYSDATE)\r\n" + 
				"AND NOT EXISTS (SELECT 1 FROM TF_FFU_TICKETS TCK WHERE	 TCK.DLC_ID = DLC.ID)\r\n" + 
				"AND NOT EXISTS (SELECT 1 FROM TF_FFU_CIRCULAR_TICKETS CTK WHERE CTK.DLC_ID = DLC.ID)\r\n" + 
				"AND PRS.CID_EXPIRY_DATE > TRUNC(SYSDATE)\r\n" + 
				"AND ROWNUM <=1");

		rs.next();
		try {
			System.out.println(("getTrfFile_0087: " + rs.getString(1)));
			trafficFile = rs.getString(1);
			con.close();
		} catch (Exception e) {
			con.close();
			System.out.println("There is no TrfFiles getTrfFile_0087.");
		}
		return trafficFile;
	}
	
	public String[] getTrfFile_00128() throws ClassNotFoundException, SQLException {
		setConnection();
		Statement stmt = con.createStatement();

		// step4 execute query
		String[] trafficFileInfo = new String[2];
		ResultSet rs = stmt.executeQuery("SELECT TRAFFIC_NO, PRS.ID PRS_ID,  DLC.EXPIRY_DATE , DLC.ID DLC_ID\r\n" + 
				"FROM TF_STP_TRAFFIC_FILES TRF, TF_DRL_DRIVING_LICENSES DLC, TF_STP_PERSONS PRS\r\n" + 
				"WHERE TRF.PRS_ID = PRS.ID AND DLC.TRF_ID = TRF.ID AND PRS.GENDER = 2 AND DLC.STATUS = 2 \r\n" + 
				"AND DLC.LICENSE_STATUS = 9 AND DLC.EXPIRY_DATE > TRUNC(SYSDATE)\r\n" + 
				"AND NOT EXISTS (SELECT 1 FROM TF_FFU_TICKETS TCK WHERE TCK.DLC_ID = DLC.ID)\r\n" + 
				"AND NOT EXISTS (SELECT 1 FROM TF_FFU_CIRCULAR_TICKETS CTK WHERE CTK.DLC_ID = DLC.ID)\r\n" + 
				"and exists (select 1 from tf_stp_held_licenses hls where hls.prs_id=prs.id AND HLS.EMI_CODE <> 'DXB' AND EXPIRY_DATE > sysdate)\r\n" + 
				"AND ROWNUM <=1");

		rs.next();
		try {
			System.out.println(("getTrfFile_00128: " + rs.getString(1)));
			trafficFileInfo[0] = rs.getString(1);
			trafficFileInfo[1] = rs.getString(2);
			con.close();
		} catch (Exception e) {
			con.close();
			System.out.println("There is no TrfFiles getTrfFile_00128.");
		}
		return trafficFileInfo;
	}
	
	public void addEyeTest(String trafficFileNo) throws ClassNotFoundException, SQLException
	{
		setConnection();
		PreparedStatement preparedStmt = con
				.prepareStatement("UPDATE TF_STP_OPTICAL_RESULTS SET PRS_ID = ? WHERE TEST_DATE =TRUNC(SYSDATE)-60");
		preparedStmt.setString(1, trafficFileNo);
		preparedStmt.executeUpdate();
		System.out.println(" Eye Test was added to trafficFile (" + trafficFileNo + ").");
		con.close();
	}
	public void updateTrfFileEnName(String scenarioId, String trafficFileNo) throws ClassNotFoundException, SQLException {

		setConnection();
		PreparedStatement preparedStmt = con
				.prepareStatement("UPDATE TF_STP_PERSONS\r\n" + 
						"SET  NAME_E = 'DL009'\r\n" + 
						"WHERE ID = (SELECT PRS_ID FROM TF_STP_TRAFFIC_FILES WHERE TRAFFIC_NO =? )");
		preparedStmt.setString(1, trafficFileNo);
		preparedStmt.executeUpdate();
		System.out.println("TrafficFile ("+ trafficFileNo +") name was updated to "+ scenarioId);
		con.close();
	}

	public String getTrfFile_0037() throws ClassNotFoundException, SQLException {
		setConnection();
		Statement stmt = con.createStatement();

		// step4 execute query
		String trafficFile = "";
		ResultSet rs = stmt.executeQuery("SELECT TRAFFIC_NO, PRS.ID PRS_ID, DLC.LICENSE_NO, DLC.EXPIRY_DATE, DLC.ID DLC_ID\r\n" + 
				"FROM TF_STP_TRAFFIC_FILES TRF, TF_DRL_DRIVING_LICENSES DLC, TF_STP_PERSONS PRS\r\n" + 
				"WHERE TRF.PRS_ID = PRS.ID AND DLC.TRF_ID = TRF.ID AND PRS.GENDER = 2 \r\n" + 
				"AND EXISTS (SELECT 1 FROM TF_DRL_LICENSE_CLASSES LCL WHERE LCL.VCL_ID in(1) AND LCL.STATUS = 2)\r\n" + 
				"AND DLC.STATUS = 2 AND DLC.LICENSE_STATUS = 0 AND CEIL(MONTHS_BETWEEN(TRUNC(SYSDATE),PRS.BIRTH_DATE)/12) = 19\r\n" + 
				"AND DLC.EXPIRY_DATE > TRUNC(SYSDATE) AND PRS.CNT_ID = 10\r\n" + 
				"AND NOT EXISTS (SELECT 1 FROM TF_FFU_TICKETS TCK WHERE TCK.DLC_ID = DLC.ID)\r\n" + 
				"AND NOT EXISTS (SELECT 1 FROM TF_FFU_CIRCULAR_TICKETS CTK WHERE CTK.DLC_ID = DLC.ID)\r\n" + 
				"AND ROWNUM <=1");

		rs.next();
		try {
			System.out.println(("getTrfFile_0087: " + rs.getString(1)));
			trafficFile = rs.getString(1);
			con.close();
		} catch (Exception e) {
			con.close();
			System.out.println("There is no TrfFiles getTrfFile_0087.");
		}
		return trafficFile;
	}

	public String getTrfFile_0049() throws ClassNotFoundException, SQLException {
		setConnection();
		Statement stmt = con.createStatement();

		// step4 execute query
		String trafficFile = "";
		ResultSet rs = stmt.executeQuery("SELECT 	TRF.TRAFFIC_NO FROM TF_STP_TRAFFIC_FILES TRF, TF_STP_PERSONS PRS\r\n" + 
				"WHERE TRF.PRS_ID = PRS.ID AND PRS.CNT_ID <> 10 AND PRS.UPDATE_DATE >= TRUNC(SYSDATE)-90 AND prs.occ_id = 100871\r\n" + 
				"AND NOT EXISTS (SELECT 1 FROM TF_DTT_LICENSE_APPLICATIONS APP WHERE APP.PRS_ID = PRS.ID)\r\n" + 
				"AND NOT EXISTS (SELECT 1 FROM TF_DRL_DRIVING_LICENSES DLC WHERE DLC.TRF_ID = TRF.ID)\r\n" + 
				"AND NOT EXISTS (SELECT 1 FROM TF_VHL_BOOKLETS BKT WHERE BKT.TRF_ID = TRF.ID)\r\n" + 
				"AND ROWNUM <=1");

		rs.next();
		try {
			System.out.println(("getTrfFile_0049: " + rs.getString(1)));
			trafficFile = rs.getString(1);
			con.close();
		} catch (Exception e) {
			con.close();
			System.out.println("There is no TrfFiles getTrfFile_0049.");
		}
		return trafficFile;
	}
	
	public String getTrfFile_0064() throws ClassNotFoundException, SQLException {
		setConnection();
		Statement stmt = con.createStatement();

		// step4 execute query
		String trafficFile = "";
		ResultSet rs = stmt.executeQuery("SELECT TRF.TRAFFIC_NO\r\n" + 
				"FROM TF_STP_TRAFFIC_FILES TRF,TF_STP_PERSONS PRS,TF_DRL_DRIVING_LICENSES DLC,TF_DRL_LICENSE_CLASSES LIC\r\n" + 
				"WHERE TRF.PRS_ID = PRS.ID AND DLC.TRF_ID = TRF.ID AND DLC.ID = LIC.DLC_ID \r\n" + 
				"AND NOT EXISTS(SELECT 1 FROM TF_FFU_TICKETS TCK WHERE TCK.TRF_ID = TRF.ID AND TCK.TICKET_STATUS = 4)\r\n" + 
				"AND NOT EXISTS(SELECT 1 FROM TF_FFU_CIRCULAR_TICKETS CTK WHERE CTK.TRF_ID = TRF.ID AND CTK.CIRCULAR_STATUS = 1)\r\n" + 
				"AND EXISTS\r\n" + 
				"(SELECT 1 FROM TF_STP_SPECIAL_PERMITS SPM WHERE SPM.TRF_ID = TRF.ID AND SPM.STATUS = 2 \r\n" + 
				"AND SPM.PEC_ID = 23 AND SPM.EXPIRY_DATE < TRUNC(SYSDATE))\r\n" + 
				"AND PRS.CNT_ID <> 10\r\n" + 
				"AND EXISTS\r\n" + 
				"(SELECT 1 FROM TF_STP_OCCUPATIONS OCC, TF_DTT_OCCUPATION_DETAILS OCD\r\n" + 
				"WHERE OCC.ID = OCD.OCC_ID AND ocd.NEEDS_MEDICAL_ASSESSMENT_TEST = 2 AND ocd.NEEDS_COM_PERMIT = 2\r\n" + 
				"AND OCD.SVC_CODE = 132 AND OCC.ID = PRS.OCC_ID AND OCD.VCL_ID = LIC.VCL_ID)\r\n" + 
				"AND lic.vcl_id=5\r\n" + 
				"AND ROWNUM <=1");

		rs.next();
		try {
			System.out.println(("getTrfFile_0064: " + rs.getString(1)));
			trafficFile = rs.getString(1);
			con.close();
		} catch (Exception e) {
			con.close();
			System.out.println("There is no TrfFiles getTrfFile_0064.");
		}
		return trafficFile;
	}

	public String getTrfFile_0038() throws ClassNotFoundException, SQLException {
		setConnection();
		Statement stmt = con.createStatement();

		// step4 execute query
		String trafficFile = "";
		ResultSet rs = stmt.executeQuery("SELECT 	TRAFFIC_NO, PRS.ID PRS_ID, DLC.LICENSE_NO, DLC.EXPIRY_DATE\r\n" + 
				"FROM TF_STP_TRAFFIC_FILES TRF, TF_DRL_DRIVING_LICENSES DLC,TF_STP_PERSONS PRS\r\n" + 
				"WHERE TRF.PRS_ID = PRS.ID AND DLC.TRF_ID = TRF.ID AND PRS.GENDER = 2\r\n" + 
				"AND EXISTS (SELECT  1 FROM TF_DRL_LICENSE_CLASSES LCL WHERE	LCL.VCL_ID in(3,4) AND LCL.STATUS = 2)\r\n" + 
				"AND DLC.EXPIRY_DATE > TRUNC(SYSDATE) AND DLC.STATUS = 2 AND DLC.LICENSE_STATUS = 0 AND PRS.CNT_ID <> 10\r\n" + 
				"AND NOT EXISTS (SELECT 1 FROM TF_FFU_TICKETS TCK WHERE TCK.DLC_ID = DLC.ID)\r\n" + 
				"AND NOT EXISTS (SELECT 1 FROM TF_FFU_CIRCULAR_TICKETS CTK WHERE CTK.DLC_ID = DLC.ID)\r\n" + 
				"AND ROWNUM <=1");

		rs.next();
		try {
			System.out.println(("getTrfFile_0064: " + rs.getString(1)));
			trafficFile = rs.getString(1);
			con.close();
		} catch (Exception e) {
			con.close();
			System.out.println("There is no TrfFiles getTrfFile_0064.");
		}
		return trafficFile;
	}

	public String getDRLExperienceCertificate() throws ClassNotFoundException, SQLException {
		setConnection();
		Statement stmt = con.createStatement();

		// step4 execute query
		String certNo = "";
		ResultSet rs = stmt.executeQuery("SELECT CPL.REF_NO\r\n" + 
				"FROM TF_STP_TRANSACTIONS TRS, TF_STP_TRAFFIC_FILES TRF, TF_STP_PERSONS PRS, TF_STP_RECEIPTS RPT, TF_DRL_CERT_PRINT_LOGS CPL\r\n" + 
				"WHERE TRS.PRS_ID = PRS.ID AND TRF.PRS_ID = PRS.ID AND TRS.ID = RPT.TRS_ID AND CPL.RELATED_TRS_ID = TRS.ID AND TRS.STATUS = 3\r\n" + 
				"AND TRS.STATUS_DATE >= TRUNC(SYSDATE)-30 AND TRS.SVC_CODE_DRL = 105\r\n" + 
				"AND NOT EXISTS (SELECT 1 FROM TF_STP_TRANSACTIONS TRS1 WHERE TRS1.PRS_ID = PRS.ID AND TRS1.STATUS = 3 AND TRS.STATUS_DATE > TRS.STATUS_DATE)\r\n" + 
				"AND ROWNUM <=1\r\n" + 
				"ORDER BY 1");

		rs.next();
		try {
			System.out.println(("DRLExperienceCertificate: " + rs.getString(1)));
			certNo = rs.getString(1);
			con.close();
		} catch (Exception e) {
			con.close();
			System.out.println("There is no DRL ExperienceCertificate in DB tables.");
		}
		return certNo;
	}
	
	public String gettrafficFile_TC_43() throws ClassNotFoundException, SQLException {
		setConnection();
		Statement stmt = con.createStatement();

		// step4 execute query
		String trafficFile = "";
		ResultSet rs = stmt.executeQuery("SELECT 	TRF.TRAFFIC_NO\r\n" + 
				"FROM TF_STP_TRAFFIC_FILES TRF, TF_STP_PERSONS PRS\r\n" + 
				"WHERE TRF.PRS_ID = PRS.ID AND PRS.CNT_ID = 10 AND trf.traffic_file_trs_id is  null\r\n" + 
				"AND PRS.CID_ID IS NOT NULL AND PRS.CID_EXPIRY_DATE > TRUNC(SYSDATE)\r\n" + 
				"AND NOT EXISTS (SELECT 1 FROM TF_DTT_LICENSE_APPLICATIONS APP WHERE APP.PRS_ID = PRS.ID)\r\n" + 
				"AND NOT EXISTS (SELECT 1 FROM TF_DRL_DRIVING_LICENSES DLC WHERE DLC.TRF_ID = TRF.ID)\r\n" + 
				"AND NOT EXISTS (SELECT 1 FROM TF_VHL_BOOKLETS BKT WHERE BKT.TRF_ID = TRF.ID)\r\n" + 
				"AND PRS.UPDATE_DATE >= TRUNC(SYSDATE)-30 AND ROWNUM <=1");

		rs.next();
		try {
			System.out.println(("gettrafficFile_TC_43: " + rs.getString(1)));
			trafficFile = rs.getString(1);
			con.close();
		} catch (Exception e) {
			con.close();
			System.out.println("There is no traffic file to DRL_TC_43.");
		}
		return trafficFile;
	}
	
	public String gettrafficFile_TC_45() throws ClassNotFoundException, SQLException {
		setConnection();
		Statement stmt = con.createStatement();

		// step4 execute query
		String trafficFile = "";
		ResultSet rs = stmt.executeQuery("\"SELECT TRAFFIC_NO, PRS.ID PRS_ID, DLC.LICENSE_NO,DLC.EXPIRY_DATE\\r\\n\" + \r\n" + 
				"	\"FROM TF_STP_TRAFFIC_FILES TRF, TF_DRL_DRIVING_LICENSES DLC, TF_STP_PERSONS PRS\\r\\n\" + \r\n" + 
				"	\"WHERE TRF.PRS_ID = PRS.ID AND DLC.TRF_ID = TRF.ID AND	PRS.GENDER = 2\\r\\n\" + \r\n" + 
				"	\"AND EXISTS (SELECT  1 FROM TF_DRL_LICENSE_CLASSES LCL WHERE LCL.VCL_ID in(3,4) AND LCL.STATUS = 2)\\r\\n\" + \r\n" + 
				"	\"AND	DLC.EXPIRY_DATE > TRUNC(SYSDATE) AND DLC.STATUS = 2 AND	DLC.LICENSE_STATUS = 0 AND PRS.CNT_ID <> 10\\r\\n\" + \r\n" + 
				"	\"AND	NOT EXISTS (SELECT 1 FROM TF_FFU_TICKETS TCK WHERE TCK.DLC_ID = DLC.ID)\\r\\n\" + \r\n" + 
				"	\"AND	NOT EXISTS (SELECT 1 FROM TF_FFU_CIRCULAR_TICKETS CTK WHERE CTK.DLC_ID = DLC.ID)\\r\\n\" + \r\n" + 
				"	\"AND PRS.CID_ID IS NOT NULL AND PRS.EID_ID IS NOT NULL AND NVL(PRS.EID_DATA_SOURCE, 5) = 1\\r\\n\" + \r\n" + 
				"	\"AND PRS.CID_EXPIRY_DATE >= TRUNC(SYSDATE) AND ROWNUM <=1\"");

		rs.next();
		try {
			System.out.println(("gettrafficFile_TC_43: to transfer" + rs.getString(1)));
			trafficFile = rs.getString(1);
			con.close();
		} catch (Exception e) {
			con.close();
			System.out.println("There is no traffic file to DRL_TC_45.");
		}
		return trafficFile;
	}
	
	public String gettrafficFile_TC_51() throws ClassNotFoundException, SQLException {
		setConnection();
		Statement stmt = con.createStatement();

		// step4 execute query
		String trafficFile = "";
		ResultSet rs = stmt.executeQuery("SELECT TRF.TRAFFIC_NO, TO_CHAR(PRS.BIRTH_DATE,'YYYY') BIRTH_YEAR, PRS.NAME_A,\r\n" + 
				"VCL.DESCRIPTION_A VEHICLE_CLASS, APP.CTR_ID TEST_CENTER_ID, CTR.NAME_A TEST_CENTER_ARABIC_NAME\r\n" + 
				"FROM TF_DTT_LICENSE_APPLICATIONS APP, TF_STP_PERSONS PRS, TF_STP_TRAFFIC_FILES TRF, \r\n" + 
				"TF_STP_VEHICLE_CLASSES VCL, TF_STP_CENTERS CTR\r\n" + 
				"WHERE APP.PRS_ID = PRS.ID AND TRF.PRS_ID = PRS.ID AND APP.VCL_ID = VCL.ID\r\n" + 
				"AND APP.CTR_ID = CTR.ID AND APP.STATUS = 1 AND APP.CTR_ID IN(18) AND APP.VCL_ID IN(3)\r\n" + 
				"AND PRS.GENDER = 1 AND not EXISTS(SELECT 1 FROM TF_DTT_TRIALS TRL WHERE TRL.AEX_APP_ID = APP.ID)\r\n" + 
				"AND ROWNUM <=1 ORDER BY CTR.ID,TRAFFIC_NO");

		rs.next();
		try {
			System.out.println(("gettrafficFile_TC_51:  " + rs.getString(1)));
			trafficFile = rs.getString(1);
			con.close();
		} catch (Exception e) {
			con.close();
			System.out.println("There is no traffic file to DRL_TC_51.");
		}
		return trafficFile;
	}
	
	public String gettrafficFile_TC_52() throws ClassNotFoundException, SQLException {
		setConnection();
		Statement stmt = con.createStatement();

		// step4 execute query
		String trafficFile = "";
		ResultSet rs = stmt.executeQuery("SELECT TRF.TRAFFIC_NO, TO_CHAR(PRS.BIRTH_DATE,'YYYY') BIRTH_YEAR, \r\n" + 
				"PRS.NAME_A, VCL.DESCRIPTION_A VEHICLE_CLASS, APP.CTR_ID TEST_CENTER_ID, \r\n" + 
				"CTR.NAME_A TEST_CENTER_ARABIC_NAME\r\n" + 
				"FROM TF_DTT_LICENSE_APPLICATIONS APP, TF_STP_PERSONS PRS, TF_STP_TRAFFIC_FILES TRF, \r\n" + 
				"TF_STP_VEHICLE_CLASSES VCL, TF_STP_CENTERS CTR\r\n" + 
				"WHERE APP.PRS_ID = PRS.ID AND TRF.PRS_ID = PRS.ID AND APP.VCL_ID = VCL.ID\r\n" + 
				"AND APP.CTR_ID = CTR.ID AND APP.STATUS = 1 AND APP.CTR_ID IN(18) \r\n" + 
				"AND APP.VCL_ID IN(3) AND PRS.GENDER = 2\r\n" + 
				"AND EXISTS(SELECT 1 FROM   TF_DTT_LEARNING_PERMITS LEP\r\n" + 
				" WHERE  LEP.APP_ID = APP.ID\r\n" + 
				" AND LEP.PERMIT_EXPIRY_DATE > TRUNC(SYSDATE)-(3*12)\r\n" + 
				" AND LEP.PERMIT_EXPIRY_DATE < TRUNC(SYSDATE))\r\n" + 
				"AND ROWNUM <=1\r\n" + 
				"ORDER BY CTR.ID,TRAFFIC_NO");

		rs.next();
		try {
			System.out.println(("gettrafficFile_TC_52:  " + rs.getString(1)));
			trafficFile = rs.getString(1);
			con.close();
		} catch (Exception e) {
			con.close();
			System.out.println("There is no traffic file to DRL_TC_52.");
		}
		return trafficFile;
	}
	
	public String gettrafficFile_TC_53() throws ClassNotFoundException, SQLException {
		setConnection();
		Statement stmt = con.createStatement();

		// step4 execute query
		String trafficFile = "";
		ResultSet rs = stmt.executeQuery("SELECT TRF.TRAFFIC_NO, TO_CHAR(PRS.BIRTH_DATE,'YYYY') BIRTH_YEAR,PRS.NAME_A,\r\n" + 
				" VCL.DESCRIPTION_A VEHICLE_CLASS, APP.CTR_ID TEST_CENTER_ID, CTR.NAME_A TEST_CENTER_ARABIC_NAME\r\n" + 
				"FROM TF_DTT_LICENSE_APPLICATIONS APP, TF_STP_PERSONS PRS,TF_STP_TRAFFIC_FILES TRF,\r\n" + 
				" TF_STP_VEHICLE_CLASSES VCL, TF_STP_CENTERS CTR\r\n" + 
				"WHERE APP.PRS_ID = PRS.ID AND TRF.PRS_ID = PRS.ID AND APP.VCL_ID = VCL.ID AND APP.CTR_ID = CTR.ID\r\n" + 
				"AND APP.STATUS = 1 AND APP.CTR_ID IN(18) AND APP.VCL_ID IN(3) AND PRS.GENDER = 2\r\n" + 
				"AND EXISTS(SELECT 1 FROM TF_DTT_LEARNING_PERMITS LEP \r\n" + 
				"WHERE LEP.APP_ID = APP.ID AND LEP.PERMIT_EXPIRY_DATE > TRUNC(SYSDATE))\r\n" + 
				"AND ROWNUM <=1 ORDER BY CTR.ID,TRAFFIC_NO");

		rs.next();
		try {
			System.out.println(("gettrafficFile_TC_53:  " + rs.getString(1)));
			trafficFile = rs.getString(1);
			con.close();
		} catch (Exception e) {
			con.close();
			System.out.println("There is no traffic file to DRL_TC_53.");
		}
		return trafficFile;
	}
	
	
	public String gettrafficFile_TC_54() throws ClassNotFoundException, SQLException {
		setConnection();
		Statement stmt = con.createStatement();

		// step4 execute query
		String trafficFile = "";
		ResultSet rs = stmt.executeQuery("SELECT 	TRF.TRAFFIC_NO, TO_CHAR(PRS.BIRTH_DATE,'YYYY') BIRTH_YEAR,\r\n" + 
				" PRS.NAME_A, VCL.DESCRIPTION_A VEHICLE_CLASS, APP.CTR_ID TEST_CENTER_ID, CTR.NAME_A TEST_CENTER_ARABIC_NAME\r\n" + 
				"FROM TF_DTT_LICENSE_APPLICATIONS APP, TF_STP_PERSONS PRS, TF_STP_TRAFFIC_FILES TRF,\r\n" + 
				" TF_STP_VEHICLE_CLASSES VCL, TF_STP_CENTERS CTR\r\n" + 
				"WHERE APP.PRS_ID = PRS.ID AND TRF.PRS_ID = PRS.ID AND APP.VCL_ID = VCL.ID\r\n" + 
				"AND APP.CTR_ID = CTR.ID AND APP.STATUS = 1 AND APP.CTR_ID IN(18) AND APP.VCL_ID IN(3) AND PRS.GENDER = 2\r\n" + 
				"AND EXISTS(SELECT 1 FROM TF_DTT_LEARNING_PERMITS LEP WHERE LEP.APP_ID = APP.ID AND LEP.PERMIT_EXPIRY_DATE > TRUNC(SYSDATE))\r\n" + 
				"AND NOT EXISTS(SELECT 1 FROM TF_DTT_TRIALS TRL WHERE	TRL.AEX_APP_ID = APP.ID)\r\n" + 
				"AND ROWNUM <=1\r\n" + 
				"ORDER BY CTR.ID,TRAFFIC_NO");

		rs.next();
		try {
			System.out.println(("gettrafficFile_TC_54:  " + rs.getString(1)));
			trafficFile = rs.getString(1);
			con.close();
		} catch (Exception e) {
			con.close();
			System.out.println("There is no traffic file to DRL_TC_54.");
		}
		return trafficFile;
	}
	
	public String gettrafficFile_TC_55() throws ClassNotFoundException, SQLException {
		setConnection();
		Statement stmt = con.createStatement();

		// step4 execute query
		String trafficFile = "";
		ResultSet rs = stmt.executeQuery("SELECT 	TRF.TRAFFIC_NO\r\n" + 
				"FROM TF_STP_TRAFFIC_FILES TRF, TF_STP_PERSONS PRS\r\n" + 
				"WHERE TRF.PRS_ID = PRS.ID\r\n" + 
				"AND NOT EXISTS (SELECT 1 FROM TF_DTT_LICENSE_APPLICATIONS APP WHERE APP.PRS_ID = PRS.ID)\r\n" + 
				"AND NOT EXISTS  (SELECT 1 FROM TF_DRL_DRIVING_LICENSES DLC WHERE DLC.TRF_ID = TRF.ID)\r\n" + 
				"AND NOT EXISTS (SELECT 1 FROM TF_VHL_BOOKLETS BKT WHERE BKT.TRF_ID = TRF.ID)\r\n" + 
				"AND PRS.CNT_ID <> 10 AND ROWNUM <=1");

		rs.next();
		try {
			System.out.println(("gettrafficFile_TC_55:  " + rs.getString(1)));
			trafficFile = rs.getString(1);
			con.close();
		} catch (Exception e) {
			con.close();
			System.out.println("There is no traffic file to DRL_TC_55.");
		}
		return trafficFile;
	}

	public String gettrafficFile_TC_56() throws ClassNotFoundException, SQLException {
		setConnection();
		Statement stmt = con.createStatement();

		// step4 execute query
		String trafficFile = "";
		ResultSet rs = stmt.executeQuery("SELECT 	TRF.TRAFFIC_NO, TO_CHAR(PRS.BIRTH_DATE,'YYYY') BIRTH_YEAR, PRS.NAME_A,\r\n" + 
				" VCL.DESCRIPTION_A VEHICLE_CLASS,APP.CTR_ID TEST_CENTER_ID, CTR.NAME_A TEST_CENTER_ARABIC_NAME\r\n" + 
				"FROM TF_DTT_LICENSE_APPLICATIONS APP, TF_STP_PERSONS PRS, TF_STP_TRAFFIC_FILES TRF,\r\n" + 
				" TF_STP_VEHICLE_CLASSES VCL, TF_STP_CENTERS CTR\r\n" + 
				"WHERE APP.PRS_ID = PRS.ID AND TRF.PRS_ID = PRS.ID AND APP.VCL_ID = VCL.ID\r\n" + 
				"AND APP.CTR_ID = CTR.ID AND APP.STATUS = 1 AND APP.CTR_ID IN(18)\r\n" + 
				"AND APP.VCL_ID IN(3) AND PRS.GENDER = 2\r\n" + 
				"AND EXISTS(SELECT 1 FROM TF_DTT_LEARNING_PERMITS LEP WHERE LEP.APP_ID = APP.ID AND LEP.PERMIT_EXPIRY_DATE > TRUNC(SYSDATE))\r\n" + 
				"AND NOT EXISTS(SELECT 1 FROM 	TF_DTT_TRIALS TRL WHERE	TRL.AEX_APP_ID = APP.ID)\r\n" + 
				"AND ROWNUM <=1 ORDER BY CTR.ID,TRAFFIC_NO");

		rs.next();
		try {
			System.out.println(("gettrafficFile_TC_56:  " + rs.getString(1)));
			trafficFile = rs.getString(1);
			con.close();
		} catch (Exception e) {
			con.close();
			System.out.println("There is no traffic file to DRL_TC_56.");
		}
		return trafficFile;
	}
	@Step("Get traffic file number Has BlackList")
	public void getTrafficFileAndPlateNOAndPlateCode() throws ClassNotFoundException, SQLException, InterruptedException {

		setConnection();
		int count = 0;
		
		String TrafficFileNo;
		
		
		Statement SQLStatement = con.createStatement();
		// step4 execute query
		ResultSet rs = SQLStatement.executeQuery(
				"SELECT PLATE_NO,\r\n" + 
				"       PCD.DESCRIPTION_A PLATE_CODE_DESC,\r\n" + 
				"       (SELECT TRF.TRAFFIC_NO\r\n" + 
				"          FROM tf_vhl_preserved_plates prp, tf_stp_traffic_files trf\r\n" + 
				"         WHERE TRF.ID = prp.TRF_ID AND prp.status = 2 AND plt.id = prp.plt_id)\r\n" + 
				"          traffic_file_preserved_owner\r\n" + 
				"  FROM TF_VHL_PLATES PLT,\r\n" + 
				"       TF_SPL_PLATE_TEMPLATES SPT,\r\n" + 
				"       TF_VHL_PLATE_CODES PCD,\r\n" + 
				"       TF_VHL_PLATE_CATEGORIES PLC,\r\n" + 
				"       tf_vhl_preserved_plates prp\r\n" + 
				" WHERE     PLT.SPT_ID = SPT.ID(+)\r\n" + 
				"       AND PCD.ID = PLT.PCD_ID\r\n" + 
				"       AND PLC.CODE = PCD.PLC_CODE\r\n" + 
				"       AND PLC.EMI_CODE = PCD.PLC_EMI_CODE\r\n" + 
				"       AND plt.id = prp.plt_id\r\n" + 
				"       AND PLC.EMI_CODE = 'DXB'\r\n" + 
				"       AND PLC.CODE = 2                                      -- PLATE_CATEGORY\r\n" + 
				"       AND PLT.TRF_ID IS NULL         --    TO CHECK IF THE PLATE IS NOT OWNED\r\n" + 
				"       AND PLATE_STATUS = 7                          --- TO CHECK PLATE_STATUS\r\n" + 
				"       AND PRP.STATUS = 2\r\n" + 
				"       AND prp.expiry_date < SYSDATE\r\n" + 
				"       AND ROWNUM < 20;");

		System.out.println("get Traffic file");
		Thread.sleep(10000);

		while (rs.next() && count == 1) {
			
			PlateNo = rs.getString(1);
			PlateCode = rs.getString(2);
			TrafficFileNo = rs.getString(3);
			System.out.println("Plate No= " + PlateNo);
			System.out.println("Plate Code = " + PlateCode);
			System.out.println("Traffic File = " + TrafficFileNo);
			count++;
		}

		SQLStatement.close();
		// closing DB Connection
		con.close();
		// Thread.sleep(10000);

	}
	
	public List<String> checkEventsLog(String eventListString, String testDateTime)
			throws ClassNotFoundException, SQLException {

		setConnection();

		Statement stmt = con.createStatement();

		List<String> foundEvents = new ArrayList<String>();
		ResultSet rs = stmt.executeQuery(
				"SELECT 	CASE_DESCRIOTION\r\n" + 
				"FROM 	TF_STP_KEDB_SUPPORT_CASES CAS\r\n" + 
				"WHERE 	CAS.CASE_DESCRIOTION LIKE '%"+eventListString+"%'AND	CAS.STATUS = 2\r\n" + 
				"AND		EXISTS (SELECT  1 FROM 	TF_STP_KEDB_SUPPORT_CASE_LOGS LOG WHERE LOG.LOG_CREATION_DATE >= TRUNC("+testDateTime+") "+
				"AND LOG.ID = CAS.ID AND	LOG.THRESHOLD_STATUS = 2)\r\n" + 
				"ORDER BY STATUS,CAS.UPDATE_DATE");

		int countrow = 0;
		while (rs.next()) {
			countrow++;
			foundEvents.add(rs.getString(1));
			System.out.print(" \n Event " + rs.getString(1) + " has been Logged.");
		}
		if (countrow == 0) {
			System.out.println(" There are No Events Logged in TF_STP_KEDB_SUPPORT_CASE_LOGS table.");
		}
		// closing DB Connection
		con.close();

		return foundEvents;
	}
	@Step("Get traffic file number Has BlackList")
	public void getTrafficFileHasBlackList() throws ClassNotFoundException, SQLException, InterruptedException {

		setConnection();
		//String TrafficFileNo ;
		int count = 0 ;  
		Statement SQLStatement = con.createStatement();
		// step4 execute query
		ResultSet rs = SQLStatement.executeQuery(
				"SELECT TRF.TRAFFIC_NO \r\n" + 
				"FROM TF_STP_TRAFFIC_FILES TRF,\r\n" + 
				"TF_SPL_AUCTIONEERS ACN\r\n" + 
				"WHERE TRF.ID = ACN.TRF_ID\r\n" + 
				"AND	ACN.IS_BLACK_LIST = 2");

		System.out.println("get Traffic file");
		Thread.sleep(10000);

		while (rs.next() && count == 1) {
			TrafficFileNo = rs.getString(1);
			System.out.println("Traffic File = " + TrafficFileNo);
			count++;
		}

		SQLStatement.close();
		// closing DB Connection
		con.close();
		// Thread.sleep(10000);

	}
	

	public void updateOpticalBalance(String RTA_BALANCE , String RECEIPTS_BALANCE) throws ClassNotFoundException, SQLException
	{
		setConnection();
		PreparedStatement preparedStmt = con
				.prepareStatement("UPDATE TF_STP_OPTICIANS_BALANCE\r\n" + 
						"SET RTA_BALANCE = ? , RECEIPTS_BALANCE = ?\r\n" + 
						"WHERE OPT_ID = 4;");
		preparedStmt.setString(1, RTA_BALANCE);
		preparedStmt.setString(2, RECEIPTS_BALANCE);
		preparedStmt.executeUpdate();
		System.out.println(" RTA_BALANCE Updated  (" + RTA_BALANCE + ")" + " and RECEIPTS_BALANCE updated "+ "(" + RECEIPTS_BALANCE + ")");
		con.close();
	}
}
