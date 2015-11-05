package es.ual.acg.cos.controllers;

import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.teneo.PersistenceOptions;
import org.eclipse.emf.teneo.hibernate.HbDataStore;
import org.eclipse.emf.teneo.hibernate.HbHelper;
import org.hibernate.cfg.Environment;
import org.jboss.logging.Logger;
import ccmm.CcmmPackage;
import acmm.AcmmPackage;

@Singleton
@Startup
@Lock(LockType.READ)

public class ManageDB {

	private HbDataStore dataStoreAC;
	private HbDataStore dataStoreCC;
//	private boolean dataStoreOn = true;
	private boolean dataStoreACOn = false;
	private boolean dataStoreCCOn = true;
	
	private static final Logger LOOGER = Logger.getLogger(ManageDB.class);
	
	@PostConstruct
	private void initializateDataStores()
	{
		//if(dataStoreOn)
		  //this.initializeDataStore();
		if(dataStoreCCOn)
		  this.initializeDataStoreCC();
		if(dataStoreACOn)
		  this.initializeDataStoreAC();		
	}
	
	//There aren't error to return
	private void initializeDataStoreAC() {
		
		LOOGER.info("[ManageDB - initializeDataStoreAC] Creating DataStoreAC...");
		
		Properties hibernateProperties = new Properties();

		String dbName = "abstractcomponents";
		
		hibernateProperties.setProperty(Environment.DRIVER, "org.postgresql.Driver");
		hibernateProperties.setProperty(Environment.USER, "postgres");
		hibernateProperties.setProperty(Environment.URL, "jdbc:postgresql://150.214.150.116:5432/" + dbName);
		hibernateProperties.setProperty(Environment.PASS, "root");
		hibernateProperties.setProperty(Environment.DIALECT, org.hibernate.dialect.PostgreSQL81Dialect.class.getName());

		hibernateProperties.setProperty(PersistenceOptions.CASCADE_POLICY_ON_NON_CONTAINMENT, "REFRESH,PERSIST,MERGE");
		hibernateProperties.setProperty(PersistenceOptions.INHERITANCE_MAPPING, "JOINED");		
		
		hibernateProperties.setProperty("hibernate.c3p0.idle_test_period", "1800" );

		final String dataStoreName = "AbstractComponents";
		dataStoreAC = HbHelper.INSTANCE.createRegisterDataStore(dataStoreName);
		dataStoreAC.setDataStoreProperties(hibernateProperties);

		dataStoreAC.setEPackages(new EPackage[] { AcmmPackage.eINSTANCE });

		//try	{
		dataStoreAC.initialize();
			
		//} finally {

		//}
		
		LOOGER.info("[ManageDB] DataStoreAC has been created");
	}
	
	//There aren't error to return
	private void initializeDataStoreCC() {
		
		LOOGER.info("[ManageDB - initializeDataStoreAC] Creating DataStoreCC...");
		
		Properties hibernateProperties = new Properties();

		String dbName = "concretecomponentsjesus33";
		//String dbName = "concretecomponentsjesus";
		
		//String dbName = "concretecomponentsPrueba";
		
		hibernateProperties.setProperty(Environment.DRIVER, "org.postgresql.Driver");
		hibernateProperties.setProperty(Environment.USER, "postgres");
		hibernateProperties.setProperty(Environment.URL, "jdbc:postgresql://150.214.150.116:5432/" + dbName);
		hibernateProperties.setProperty(Environment.PASS, "root");
		hibernateProperties.setProperty(Environment.DIALECT, org.hibernate.dialect.PostgreSQL81Dialect.class.getName());

		hibernateProperties.setProperty(PersistenceOptions.CASCADE_POLICY_ON_NON_CONTAINMENT, "REFRESH,PERSIST,MERGE");
		hibernateProperties.setProperty(PersistenceOptions.INHERITANCE_MAPPING, "JOINED");
		
		// No crear tablas intermedias
		hibernateProperties.setProperty(PersistenceOptions.JOIN_TABLE_FOR_NON_CONTAINED_ASSOCIATIONS,"false");
		
		// Without e_version in the tables
	    hibernateProperties.setProperty("teneo.mapping.always_version","false");
	    
	    // Without e_container in the tables
	    hibernateProperties.setProperty("teneo.mapping.disable_econtainer","true");
	    
	    hibernateProperties.setProperty("hibernate.c3p0.idle_test_period", "1800" );

		final String dataStoreName = "ConcreteComponents";
		dataStoreCC = HbHelper.INSTANCE.createRegisterDataStore(dataStoreName);
		dataStoreCC.setDataStoreProperties(hibernateProperties);

		dataStoreCC.setEPackages(new EPackage[] { CcmmPackage.eINSTANCE });

		//try	{
		dataStoreCC.initialize();

		//} finally {

		//}
		
		LOOGER.info("[ManageDB] DataStoreCC has been created");
	}
	
//	@Lock(LockType.READ)
//	public HbDataStore getDataStore() throws Exception{
//		//initializeDataStore();
//		return dataStore;
//	}
//
//	public void setDataStore(HbDataStore dataStore) {
//		this.dataStore = dataStore;
//	}

	@Lock(LockType.READ)
	public HbDataStore getDataStoreAC() {
		//initializeDataStoreAC();
		return dataStoreAC;
	}

	public void setDataStoreAC(HbDataStore dataStoreAC) {
		this.dataStoreAC = dataStoreAC;
	}
	
	@Lock(LockType.READ)
	public HbDataStore getDataStoreCC() {
		//initializeDataStoreCC();
		return dataStoreCC;
	}

	public void setDataStoreCC(HbDataStore dataStoreCC) {
		this.dataStoreCC = dataStoreCC;
	}

}
