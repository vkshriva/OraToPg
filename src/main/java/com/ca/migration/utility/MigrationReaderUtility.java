
package com.ca.migration.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ca.migration.constants.MigrationQueryConstants;
import com.ca.migration.exception.MigrationOracleException;

public class MigrationReaderUtility {

	public static List<String> getTenantList() {

		List<String> returnList = null;
		Set<String> returnSet = new LinkedHashSet<>();

		// Logger logger = Logger.getLogger(MigrationReaderUtility.class);

		try (InputStream inputStream = new FileInputStream("config/tenantList.properties");
				BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (null == line || line.isEmpty() || line.matches("\\s") || line.length() == 0
						|| line.trim().startsWith("#")) {
					continue;
				}

				returnSet.add(line);
			}
		} catch (FileNotFoundException e) {
			// logger.error(e.getMessage(),e);
			throw new MigrationOracleException(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// logger.error(e.getMessage(),e);
			throw new MigrationOracleException(e.getMessage());
		}
		returnList = new ArrayList<String>(returnSet);
		return returnList;

	}

	public static Long getEnableDisableProfile(String propertyName) {

		Long returnVal=0l;

		// Logger logger = Logger.getLogger(MigrationReaderUtility.class);

		try (InputStream inputStream = new FileInputStream("config/enbl_disbl_tenant_profile.properties");) {
			Properties prop = new Properties();
			prop.load(inputStream);

			String propertyValue = prop.getProperty(propertyName);


			returnVal = Long.parseLong(propertyValue);


		}  catch (Exception e) {
			// TODO Auto-generated catch block
			// logger.error(e.getMessage(),e);
			throw new MigrationOracleException(e.getMessage());
		}

		return returnVal;

	}



	public static void generateIntermediateScript() {

		// Logger logger = Logger.getLogger(MigrationReaderUtility.class);
		File dir = new File(MigrationQueryConstants.SCRIPT_DIR);
		File readFile = new File(dir, MigrationQueryConstants.MASTER_SCRIPT_FILE);

		File writeFile = new File(dir, MigrationQueryConstants.INTERMEDIATE_SCRIPT_FILE);

		try (FileReader fr = new FileReader(readFile);
				BufferedReader br = new BufferedReader(fr);
				PrintWriter pw = new PrintWriter(writeFile);) {
			String line;
			Queue<String> queue = new LinkedList<>();
			while ((line = br.readLine()) != null) {
				if (null == line || line.isEmpty() || line.matches("\\s") || line.length() == 0) {
					continue;
				}

				String regExp = ".*;$";
				Pattern pattern = Pattern.compile(regExp);
				Matcher matcher = pattern.matcher(line);
				if (matcher.find()) {
					queue.stream().forEach(str -> {
						pw.print(str + " ");
					});
					pw.print(line.trim());
					pw.println();
					pw.flush();
					queue = new LinkedList<>();
				} else {
					queue.add(line.trim());
				}

			}
		} catch (FileNotFoundException e) {
			// logger.error(e.getMessage(),e);
			throw new MigrationOracleException(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// logger.error(e.getMessage(),e);
			throw new MigrationOracleException(e.getMessage());
		}

	}

	public static void generateTenantSpecificScripts() {
		File dir = new File(MigrationQueryConstants.SCRIPT_DIR);
		File writeDir = new File(MigrationQueryConstants.GENERATED_SCRIPT_DIR);
		if(!writeDir.exists()) {
			writeDir.mkdirs();
		}
		File readFile = new File(dir, MigrationQueryConstants.INTERMEDIATE_SCRIPT_FILE);
		File writeFile = null;
		PrintWriter pw = null;

		String regExpForSplit = MigrationQueryConstants.SELECT_AS_TOKENIZER;

		try (FileReader fr = new FileReader(readFile); BufferedReader br = new BufferedReader(fr)) {

			String line;
			List<String> intermediateList=null;
			while ((line = br.readLine()) != null) {
				if (null == line || line.isEmpty() || line.matches("\\s") || line.length() == 0) {
					continue;
				}

				if (line.contains(regExpForSplit)) {
					String arr[] = line.split("=");
					String fileName = arr[1];
					fileName = fileName.replaceAll("'", "");
					fileName = fileName.replaceAll(";", "");
					writeFile = new File(writeDir, fileName + ".sql");
					pw = new PrintWriter(writeFile);
					intermediateList= new LinkedList<>();
					//pw.println(line);
				}

				if (pw != null) {
					if (line.startsWith("select") || line.startsWith("SELECT")) {
						continue;
					}

					else if (line.startsWith("commit") || line.startsWith("COMMIT")) {

						for(String str:intermediateList) {
							pw.println(str);
						}

						pw.flush();
						pw.close();
						pw = null;
						writeFile = null;
						intermediateList=null;

					}

					else if (line.startsWith("ROLLBACK") || line.startsWith("rollback")) {

						pw.println(MigrationQueryConstants.ERROR_MSG_WHILE_INSERTION_FAILS);
						pw.flush();
						pw.close();
						pw = null;
						writeFile = null;
						intermediateList = null;

					}

					else {
						// pw.println(line);
						intermediateList.add(line);
					}

				}

			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			throw new MigrationOracleException(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new MigrationOracleException(e.getMessage());
		}

	}

	public static void generateScriptsFrAlert(List<String> tenantList) {
		File dir = new File(MigrationQueryConstants.SCRIPT_DIR);
		File writeDir = new File(MigrationQueryConstants.GENERATED_SCRIPT_DIR);
		if (!writeDir.exists()) {
			writeDir.mkdirs();
		}
		File readFile = new File(dir, MigrationQueryConstants.INTERMEDIATE_SCRIPT_FILE);
		File writeFile = null;
		PrintWriter pw = null;

		String regExpForSplit = MigrationQueryConstants.SELECT_AS_TOKENIZER;

		try (FileReader fr = new FileReader(readFile); BufferedReader br = new BufferedReader(fr)) {

			String line;

			int index = 0;

			List<String> intermediateList = null;
			while ((line = br.readLine()) != null) {
				if (null == line || line.isEmpty() || line.matches("\\s") || line.length() == 0) {
					continue;
				}

				/*
				 * if (line.contains(regExpForSplit)) { String arr[] = line.split("="); String
				 * fileName = arr[1]; fileName = fileName.replaceAll("'", ""); fileName =
				 * fileName.replaceAll(";", ""); writeFile = new File(writeDir, fileName +
				 * ".sql"); pw = new PrintWriter(writeFile); intermediateList= new
				 * LinkedList<>(); //pw.println(line); }
				 */

				if (line.startsWith("select") || line.startsWith("SELECT")) {

					if (null == intermediateList) {
						String fileName = tenantList.get(index);
						writeFile = new File(writeDir, fileName + ".sql");
						pw = new PrintWriter(writeFile);
						intermediateList = new LinkedList<>();
					}
					continue;
				}

				else if (line.startsWith("commit") || line.startsWith("COMMIT")) {

					for (String str : intermediateList) {
						pw.println(str);
					}

					pw.flush();
					pw.close();
					pw = null;
					writeFile = null;
					intermediateList = null;
					index++;

				}

				else if (line.startsWith("ROLLBACK") || line.startsWith("rollback")) {

					pw.println(MigrationQueryConstants.ERROR_MSG_WHILE_INSERTION_FAILS);
					pw.flush();
					pw.close();
					pw = null;
					writeFile = null;
					intermediateList = null;
					index++;

				}

				else {
					// pw.println(line);
					intermediateList.add(line);
				}

			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			throw new MigrationOracleException(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new MigrationOracleException(e.getMessage());
		}

	}

}
