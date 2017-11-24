package dbmi.hms.harvard.edu.testdrivers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

import org.yaml.snakeyaml.error.YAMLException;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

import dbmi.hms.harvard.edu.reporter.Reporter;
import dbmi.hms.harvard.edu.testplans.Testplan;


public class TestDriver {
	public static final String TESTPLANS = "dbmi.hms.harvard.edu.testplans.";
	public static final String REPORTS = "dbmi.hms.harvard.edu.reporter.";
	
	public static void main(String[] args) {
		Testplan testPlan = null;
		Reporter reporter = null;
		try {
			YamlReader reader = new YamlReader(new FileReader("resources/testConfigs/projects.yaml"));
				while(true){
					Map testConfig = (Map) reader.read();

					if(testConfig == null) break;
					
					testPlan = initTestPlan(testConfig.get("type").toString(), testConfig);
					
					reporter = initReporter(testConfig.get("reporter").toString());	

					testPlan.doPlan(reporter);

				}
			
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (YAMLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				//return testplan;
			}
	}
	private static Testplan initTestPlan(String testType, Map map){
		Testplan newInstance = null;
		try {		
			Class<?> resourceInterfaceClass = Class.forName(TESTPLANS + testType);

			newInstance =  (Testplan) resourceInterfaceClass.newInstance();
			newInstance.setTestPlan(map);
		} catch (SecurityException e) {
			System.out.println(e);
			e.printStackTrace();
			return null;
		} catch (InstantiationException e) {
			System.out.println(e);
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			System.out.println(e);
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			System.out.println(e);
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			return null;
		} finally {
		
			return newInstance;
		}
	}
	
	private static Reporter initReporter(String reporterType){
		Reporter newInstance = null;
		
		try {		
			Class<?> resourceInterfaceClass = Class.forName(REPORTS + reporterType);
			newInstance =  (Reporter) resourceInterfaceClass.newInstance();
		} catch (SecurityException e) {
			e.printStackTrace();
			return null;
		} catch (InstantiationException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} finally {
			return newInstance;
		}		
	}
}
