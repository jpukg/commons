package com.samenea.commons.component.utils.test;

import javax.sql.DataSource;

import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.util.fileloader.FullXmlDataFileLoader;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

import test.annotation.DataSets;

import java.util.Map;

public abstract class  AbstractAcceptanceTestExecutionListener implements TestExecutionListener {

	private IDatabaseTester databaseTester;
	private DataSets dataSetAnnotation;
	private DataSets classDataSetAnnotation;
	private String fileAddress;
	
	/**
	 * implement this method to Datasource Bean Name in application context.
	 * @return
	 */
	protected abstract String getDatasourceName();
	/**
	 * implement this method to FullXmlDataFileLoader Bean Name in application context.
	 * @return
	 */
	protected abstract String getXmlDataFileLoaderName();
	
	@Override
	public void beforeTestClass(TestContext testContext) throws Exception {
		
		
	}

	@Override
	public void prepareTestInstance(TestContext testContext) throws Exception {
		
	}

	@Override
	public void beforeTestMethod(TestContext testContext) throws Exception {
		classDataSetAnnotation = testContext.getTestClass().getAnnotation(DataSets.class);
		dataSetAnnotation =testContext.getTestMethod().getAnnotation(DataSets.class);
		if(needsSetup()){
			setupDatabase(testContext, fileAddress);
		}

	}

	@Override
	public void afterTestMethod(TestContext testContext) throws Exception {
		
//		if(needsTearDown()){
//			tearDownDatabase(testContext, fileAddress);
//		}
//		databaseTester.onTearDown();
	}

	@Override
	public void afterTestClass(TestContext testContext) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	private boolean needsSetup() {
		if(dataSetAnnotation!=null){
			if(!dataSetAnnotation.setUpDataSet().equals("")){
				fileAddress= dataSetAnnotation.setUpDataSet();
				return true;
			}
		}
		
		if(classDataSetAnnotation != null){
			if(!classDataSetAnnotation.setUpDataSet().equals("")){
				fileAddress = classDataSetAnnotation.setUpDataSet();
				return true;
			}
		}
		
		return false;
	}
	
	private void setupDatabase(TestContext testContext,String fileAddress) throws Exception{

        databaseTester = new ConfigurableDataSourceDatabaseTester((DataSource) testContext.getApplicationContext().getBean(getDatasourceName()), getConnectionConfigs());

        FullXmlDataFileLoader xmlDataFileLoader = (FullXmlDataFileLoader) testContext.getApplicationContext().getBean(getXmlDataFileLoaderName());
        IDataSet dataSet = xmlDataFileLoader.load(fileAddress);
        databaseTester.setSetUpOperation(DatabaseOperation.TRANSACTION(DatabaseOperation.CLEAN_INSERT));
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
		
		
	}
	
	private void tearDownDatabase(TestContext testContext,String fileAddress) throws Exception{
		setupDatabase(testContext, fileAddress);
		databaseTester.onTearDown();
		
	}

    public Map<String, Object> getConnectionConfigs() {
        return null;
    }
}
