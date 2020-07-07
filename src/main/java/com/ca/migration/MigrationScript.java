package com.ca.migration;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ca.migration.constants.MigrationQueryConstants;
import com.ca.migration.exception.MigrationOracleException;
import com.ca.migration.service.MigrationService;
import com.ca.migration.utility.MigrationReaderUtility;

public class MigrationScript {

	public static void main(String[] args) {

		Logger logger = Logger.getLogger(MigrationScript.class);
		boolean optionFlag = true;
		Scanner kb = new Scanner(System.in);
		int option = 0;
		boolean continueFlag = false;
		/*do {*/
		do {
			System.out.println("\n*************************************************************");
			System.out.println("Select the Migration operation 1 or 2 based on below options:");
			System.out.println(MigrationQueryConstants.INSERT_OPTION+". Insert Complete MDO Tables Data");
			System.out.println(MigrationQueryConstants.DELETE_OPTION+". Delete Complete MDO Tables Data");
			System.out.println(MigrationQueryConstants.PRINT_OPTION+". Print Data of Each Table");
			System.out.println(MigrationQueryConstants.INSERT_ALERTS_ONLY + ". Insert Alerts Related Table");
			System.out.println(MigrationQueryConstants.DELETE_ALERTS_ONLY + ". Delete Alerts Related Table");
			System.out.println(MigrationQueryConstants.EXIT_OPTION+". Exit");

			try {
				option = kb.nextInt();
				if (option == MigrationQueryConstants.EXIT_OPTION) {
					System.out.println("Exit done....");
					System.exit(0);
				} else if (option == 0) {
					System.out.println("Wrong input .Please try once again.");
					optionFlag = false;
				} else {
					if (option == MigrationQueryConstants.INSERT_OPTION) {
						System.out
						.println("You have selected operation 'INSERT'. Please confirm by Y/Yes or N/No.");
						String confirmation = kb.next();
						if ("Y".equalsIgnoreCase(confirmation) || "YES".equalsIgnoreCase(confirmation)) {
							optionFlag = true;
						} else {
							optionFlag = false;
						}
					} else if (option == MigrationQueryConstants.DELETE_OPTION) {
						System.out
						.println("You have selected operation 'DELETE'. Please confirm by  Y/Yes or N/No.");
						String confirmation = kb.next();
						if ("Y".equalsIgnoreCase(confirmation) || "YES".equalsIgnoreCase(confirmation)) {
							optionFlag = true;
						} else {
							optionFlag = false;
						}
					}

					else if (option == MigrationQueryConstants.PRINT_OPTION
							|| option == MigrationQueryConstants.INSERT_ALERTS_ONLY
							|| option == MigrationQueryConstants.DELETE_ALERTS_ONLY) {

					}

					else {
						System.out.println("Wrong input .Please try once again.");
						optionFlag = false;
					}

				}
			}

			catch (InputMismatchException ex) {
				System.out.println("Wrong Input . Please try once again. ");
				kb.next();
				optionFlag = false;
			}

			catch (Exception ex) {
				ex.printStackTrace();
				System.out.println("Wrong Input . Please try once again. ");
				optionFlag = false;
			}

		} while (optionFlag == false);

		try {

			List<String> returnList = null;

			boolean tenantListFlag = false;
			do {

				returnList = MigrationReaderUtility.getTenantList();
				logger.debug("**********************  Tenant(cohortID) List are  *******************\n");

				for (int i = 0; i < returnList.size(); i++) {

					String toPrint = (i + 1) + ": " + returnList.get(i);
					logger.info(toPrint);

				}
				System.out.println(" \n Verify the Tenant(cohortID) List and confirm to continue Y/Yes or N/No \n");
				String confirmationTenatList = kb.next();

				if ("N".equalsIgnoreCase(confirmationTenatList) || "NO".equalsIgnoreCase(confirmationTenatList)) {
					System.out.println(
							"Update the Tenant(cohortID) List in config/tenantList.properties and then press Enter \n");
					kb.nextLine();
					kb.nextLine();
				}

				if ("Y".equalsIgnoreCase(confirmationTenatList) || "YES".equalsIgnoreCase(confirmationTenatList)) {
					tenantListFlag = true;
				}
			}

			while (!tenantListFlag);

			logger.debug("***** Migration Configuration started .It will take sometime...***************");

			ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");
			MigrationService migrationService = context.getBean("migrationService", MigrationService.class);

			logger.debug("********** Migration Configuration completed.. *************************************");
			logger.debug("\n\n");

			/*
			 * Run Insert operation
			 */
			if (option == MigrationQueryConstants.INSERT_OPTION) {

				/*if (!migrationService.tenantMAUProfileValidation()) {

				 * Tenant_MAU_Profile is not present

						return;
					}*/

				returnList.stream().forEach(tenantID -> {
					try {
						LocalDateTime startTime = LocalDateTime.now();
						logger.debug("Migration Started for TenantID :" + tenantID + " StartTime: " + startTime);
						migrationService.fetchAndInsertMDOTables(tenantID);
						LocalDateTime endTime = LocalDateTime.now();
						Duration totalTime = Duration.between(startTime, endTime);
						logger.debug("************ Migration completed  For Tenant\n tenantID : "
								+ tenantID + " EndTime: " + endTime);
						long tTime = totalTime.getSeconds();
						String tTimeToPrint = null;
						if (tTime > 0) {
							tTimeToPrint = " " + tTime;
						} else {
							tTimeToPrint = "less than 0 ";
						}
						logger.debug(
								"Total Time Taken to complete tenantID " + tenantID + ":  " + tTimeToPrint + "Sec \n");

					} catch (Exception ex) {
						logger.error("Migration Failed for TenantID :" + tenantID);
						logger.error(ex.getMessage(), ex);
					}
				});


				logger.debug("Script generation started ..... ");
				try {
					MigrationReaderUtility.generateIntermediateScript();
					MigrationReaderUtility.generateTenantSpecificScripts();
					logger.debug("Script generation completed .Please execute in NextGen PostgresEnvironment and 'COMMIT' manually...... ");
				}
				catch (Exception ex) {
					logger.error("Error while creating scripts :");
					logger.error(ex.getMessage(), ex);
				}

			}
			/*
			 * Run Delete operation
			 */
			else if (option == MigrationQueryConstants.DELETE_OPTION) {
				returnList.stream().forEach(tenantID -> {
					try {
						LocalDateTime startTime = LocalDateTime.now();
						logger.debug("Deletion Started for TenantID :" + tenantID + " StartTime: " + startTime);
						boolean deletionFlag=false;
						do {
							migrationService.deleteMDOTables(tenantID);
							logger.info("VALIDATING DATA DELETION....." + tenantID);
							deletionFlag = migrationService.isCompleteDataDeleted(tenantID);
							logger.info("VALIDATION COMPLETED DELETION....." + tenantID);

							if(!deletionFlag) {
								logger.info("DATA DOESNOT GET DELETED COMPLETELY .RUNNING DELETION COMMAND AGAIN FOR TENANTID " + tenantID);
							}
						}

						while(!deletionFlag);


						LocalDateTime endTime = LocalDateTime.now();
						Duration totalTime = Duration.between(startTime, endTime);
						logger.debug("******************************* Deletion completed  For Tenant: "
								+ tenantID + " EndTime: " + endTime);
						long tTime = totalTime.getSeconds();
						String tTimeToPrint = null;
						if (tTime > 0) {
							tTimeToPrint = " " + tTime;
						} else {
							tTimeToPrint = "less than 0 ";
						}
						logger.debug(
								"Total Time Taken to complete tenantID " + tenantID + ":  " + tTimeToPrint + "Sec \n");

					} catch (Exception ex) {
						logger.error("Deletion Failed for TenantID :" + tenantID);
						logger.error(ex.getMessage(), ex);
					}
				});
			}

			/*
			 * Delete Only Alert specific table
			 */
			else if (option == MigrationQueryConstants.DELETE_ALERTS_ONLY) {
				returnList.stream().forEach(tenantID -> {
					try {
						LocalDateTime startTime = LocalDateTime.now();
						logger.debug("Deleting Alert Specific data Started for TenantID :" + tenantID + " StartTime: " + startTime);
						boolean deletionFlag=false;

						migrationService.deleteAlertSpecificTables(tenantID);

						if(!deletionFlag) {
							logger.info("DATA DOESNOT GET DELETED COMPLETELY .RUNNING DELETION COMMAND AGAIN FOR TENANTID " + tenantID);
						}

						LocalDateTime endTime = LocalDateTime.now();
						Duration totalTime = Duration.between(startTime, endTime);
						logger.debug("******************************* Deletion(Alert specific data)completed  For Tenant: "
								+ tenantID + " EndTime: " + endTime);
						long tTime = totalTime.getSeconds();
						String tTimeToPrint = null;
						if (tTime > 0) {
							tTimeToPrint = " " + tTime;
						} else {
							tTimeToPrint = "less than 0 ";
						}
						logger.debug(
								"Total Time Taken to complete tenantID " + tenantID + ":  " + tTimeToPrint + "Sec \n");

					} catch (Exception ex) {
						logger.error("Deletion(Alert specific data) Failed for TenantID :" + tenantID);
						logger.error(ex.getMessage(), ex);
					}
				});
			}


			else if (option == MigrationQueryConstants.INSERT_ALERTS_ONLY) {


				returnList.stream().forEach(tenantID -> {
					try {
						LocalDateTime startTime = LocalDateTime.now();
						logger.debug("Migration(Alert specific data) Started for TenantID :" + tenantID + " StartTime: " + startTime);
						migrationService.fetchAndInsertAlertSpecificTables(tenantID);
						LocalDateTime endTime = LocalDateTime.now();
						Duration totalTime = Duration.between(startTime, endTime);
						logger.debug("************ Migration (Alerts Only) completed  For Tenant\n tenantID : "
								+ tenantID + " EndTime: " + endTime);
						long tTime = totalTime.getSeconds();
						String tTimeToPrint = null;
						if (tTime > 0) {
							tTimeToPrint = " " + tTime;
						} else {
							tTimeToPrint = "less than 0 ";
						}
						logger.debug(
								"Total Time Taken to complete tenantID " + tenantID + ":  " + tTimeToPrint + "Sec \n");

					} catch (Exception ex) {
						logger.error("Migration(Alert specific data) Failed for TenantID :" + tenantID);
						logger.error(ex.getMessage(), ex);
					}
				});


				logger.debug("Script generation started (Alert specific data)..... ");
				try {
					MigrationReaderUtility.generateIntermediateScript();
					MigrationReaderUtility.generateScriptsFrAlert(returnList);
					logger.debug("Script generation completed (Alert specific data).Please execute in NextGen PostgresEnvironment and 'COMMIT' manually...... ");
				}
				catch (Exception ex) {
					logger.error("Error while creating scripts :");
					logger.error(ex.getMessage(), ex);
				}

			}


			/*
			 * Run Update operation
			 */
			else if (option == MigrationQueryConstants.PRINT_OPTION) {

				System.out.println("SELECT THE DATABASE: ");
				System.out.println("1. ORACLE");
				System.out.println("2. POSTGRES");
				String selectedDatabase = kb.next();
				String database="";
				if("1".equalsIgnoreCase(selectedDatabase)) {
					database= "ORACLE";
				}
				else if("2".equalsIgnoreCase(selectedDatabase)) {
					database= "POSTGRES";
				}

				else {
					System.out.println("WRONG INPUT");
					System.exit(0);
				}

				for(String tenantID:returnList) {
					migrationService.printTenantData(tenantID,database);
				}

				logger.info("***** RETRIEVED TENANT DATA. PLEASE CHECK IN  " +MigrationQueryConstants.SCRIPT_DIR+"/"+MigrationQueryConstants.PRINT_FILE+" *****");
			}

		} catch (MigrationOracleException ex) {
			logger.error("Error While Fetching TenantLists:");
			logger.error(ex.getMessage(), ex);
		}

		catch (Exception ex) {
			logger.error("Error While Configuration......");
			logger.error(ex.getMessage(), ex);
		}

		/*System.out.println("\n Do you want to continue. Y/Yes or N/No \n");
			String continueOption = kb.next();
			if ("Y".equalsIgnoreCase(continueOption) || "YES".equalsIgnoreCase(continueOption)) {
				continueFlag = true;
			} else {
				continueFlag = false;
				System.out.println("Thank You....");
				System.exit(0);
			}

		} while (continueFlag == true);*/

	}



}
