package es.ual.acg.cos.controllers;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.teneo.PersistenceOptions;
import org.eclipse.emf.teneo.hibernate.HbDataStore;
import org.eclipse.emf.teneo.hibernate.HbHelper;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;
import org.jboss.logging.Logger;
import org.xml.sax.InputSource;

import architectural_metamodel.AbstractArchitecturalModel;
import architectural_metamodel.AbstractDependency;
import architectural_metamodel.Architectural_metamodelFactory;
import architectural_metamodel.Architectural_metamodelPackage;
import architectural_metamodel.Binary;
import architectural_metamodel.ConcreteArchitecturalModel;
import architectural_metamodel.ConcreteComponent;
import architectural_metamodel.ConcreteDependency;
import architectural_metamodel.Connector;
import architectural_metamodel.InputPort;
import architectural_metamodel.Interface;
import architectural_metamodel.Nary;
import architectural_metamodel.OutputPort;
import architectural_metamodel.Port;
import architectural_metamodel.Provided;
import architectural_metamodel.Required;
import architectural_metamodel.Relationship;
import architectural_metamodel.RuntimeProperty;
import architectural_metamodel.impl.RuntimePropertyImpl;

@Singleton
@Startup
@Lock(LockType.READ)
public class ManageArchitectures {
  private HbDataStore dataStore;
  private boolean dataStoreOn = true;

  private static final Logger LOGGER = Logger
      .getLogger(ManageArchitectures.class);

  @PostConstruct
  private void initializateDataStores() {
    if (dataStoreOn)
      this.initializeDataStore();
  }

  private void initializeDataStore() {
    LOGGER.info("[ManageArchitectures] Creating DataStore...");

    Properties hibernateProperties = new Properties();

    String dbName = "architecturalmodelsjesus33";

    hibernateProperties.setProperty(Environment.DRIVER, "org.postgresql.Driver");
    hibernateProperties.setProperty(Environment.USER, "postgres");
    hibernateProperties.setProperty(Environment.URL, "jdbc:postgresql://150.214.150.116:5432/" + dbName);
    hibernateProperties.setProperty(Environment.PASS, "root");
    hibernateProperties.setProperty(Environment.DIALECT, org.hibernate.dialect.PostgreSQL81Dialect.class.getName());

    hibernateProperties.setProperty(PersistenceOptions.INHERITANCE_MAPPING, "JOINED");

    // para insertar componentes
    // hibernateProperties.setProperty(PersistenceOptions.CASCADE_POLICY_ON_CONTAINMENT,
    // "ALL");
    hibernateProperties.setProperty(PersistenceOptions.CASCADE_POLICY_ON_NON_CONTAINMENT, "MERGE,PERSIST,REFRESH,REMOVE");
    // hibernateProperties.setProperty(PersistenceOptions.CASCADE_POLICY_ON_NON_CONTAINMENT,
    // "ALL");

    // No crear tablas intermedias
    hibernateProperties.setProperty(PersistenceOptions.JOIN_TABLE_FOR_NON_CONTAINED_ASSOCIATIONS, "false");

    // Table by class, problems with insertion
    // hibernateProperties.setProperty("teneo.mapping.inheritance","TABLE_PER_CLASS");

    // To resolve the error "deleted object would be re-saved by cascade"
    hibernateProperties.setProperty("teneo.mapping.fetch_containment_eagerly", "true");
    
    // Without e_version in the tables
    hibernateProperties.setProperty("teneo.mapping.always_version", "false");

    // Without e_container in the tables
    hibernateProperties.setProperty("teneo.mapping.disable_econtainer", "true");

    hibernateProperties.setProperty("hibernate.c3p0.idle_test_period", "1800");

    final String dataStoreName = "ArchitecturalModels";
    dataStore = HbHelper.INSTANCE.createRegisterDataStore(dataStoreName);
    dataStore.setDataStoreProperties(hibernateProperties);

    dataStore.setEPackages(new EPackage[] { Architectural_metamodelPackage.eINSTANCE });

    dataStore.initialize();

    LOGGER.info("[ManageDB] DataStore has been created");
  }

  @Lock(LockType.READ)
  public HbDataStore getDataStore() throws Exception {
 
    return dataStore;
  }

  public void setDataStore(HbDataStore dataStore) {
    this.dataStore = dataStore;
  }

  public String exportCAMFromString(String camFileType, String camFileString) {
    // It is necessary for the SOAP message, that the input String has
    // <![CDATA["" at the start and
    // "]]>" at the end

    String result = "";

    ConcreteArchitecturalModel cam = null;
    String camID = null;
    try {
      cam = (ConcreteArchitecturalModel) convertXMIStringToEObject(camFileString);
      camID = cam.getCamID();
      LOGGER.info("Read CAM ID: " + camID);
    } catch (IOException e) {
      LOGGER.error(e.toString());
    }

    if (cam != null) {
      SessionFactory sessionFactory = dataStore.getSessionFactory();

      // Open a new Session
      Session session = sessionFactory.openSession();

      // Check if the CAM ID does exist
      Query query = session.createQuery("FROM ConcreteArchitecturalModel cam WHERE cam.camID = '"+ camID + "'");
      List<?> ccsList = query.list();
      if (ccsList.size() == 0) {
        result = camID + " ID does not exist --> Insert CAM Specification";
        LOGGER.info(result);

        AbstractArchitecturalModel aam = getAbstractArchitecturalModel(cam.getAamID(), session);

        cam.setAam(aam);

        // Start transaction
        session.beginTransaction();

        session.save(cam);

        // Commit the changes to the database.
        session.getTransaction().commit();

      } else {
        result = cam + " ID exist --> CAM Specification is not inserted";
        LOGGER.info(result);
      }

      // Close the session.
      session.close();

    }

    return result;
  }

  private static AbstractArchitecturalModel getAbstractArchitecturalModel(String aamID, Session session) {

    AbstractArchitecturalModel aam = null;

    LOGGER.info("aam.aamID = " + aamID);
    Query query = session.createQuery("FROM AbstractArchitecturalModel aam WHERE aam.aamID='"+ aamID + "'");
    List<?> aamList = query.list();
    if (aamList.size() == 1) {
      LOGGER.info("AAM has been found");
      aam = (AbstractArchitecturalModel) aamList.get(0);
    } else {
      LOGGER.info("AAM has not been found");
    }

    return aam;
  }

  public String exportAAMFromString(String aamFileType, String aamFileString) throws Exception {

    // It is necessary for the SOAP message, that the input String has
    // <![CDATA["" at the start and
    // "]]>" at the end

    String result = "";

    AbstractArchitecturalModel aam = null;
    String aamID = null;
    // try {
    aam = (AbstractArchitecturalModel) convertXMIStringToEObject(aamFileString);
    aamID = aam.getAamID();
    LOGGER.info("Read AAM ID: " + aamID);
    // } catch (IOException e){
    // LOGGER.error(e.toString());
    // }

    if (aam != null) {
      // getDataStoreFromManageDB();

      SessionFactory sessionFactory = dataStore.getSessionFactory();

      // Open a new Session
      Session session = sessionFactory.openSession();

      // Check if the CAM ID does exist
      Query query = session.createQuery("FROM AbstractArchitecturalModel aam WHERE aam.aamID = '"+ aamID + "'");
      List<?> ccsList = query.list();
      if (ccsList.size() == 0) {
        result = aamID + " ID does not exist --> Insert AAM Specification";
        LOGGER.info(result);

        // Start transaction
        session.beginTransaction();

        session.save(aam);

        // Commit the changes to the database.
        session.getTransaction().commit();

      } else {
        result = aam + " ID exist --> AAM Specification is not inserted";
        LOGGER.info(result);
      }

      // Close the session.
      session.close();
    }

    return result;
  }

  public String withdrawCAM(String camID) {

    String result = "";
    SessionFactory sessionFactory = dataStore.getSessionFactory();

    // Open a new Session
    Session session = sessionFactory.openSession();

    // Check if the CC ID does exist
    Query query = session.createQuery("FROM ConcreteArchitecturalModel cam WHERE cam.camID = '"+ camID + "'");
    List<?> camsList = query.list();
    if (camsList.size() == 0) {
      result = camID + " ID does not exist --> Cannot delete CAM Specification";
      LOGGER.info(result);
    } else {
      result = camID + " ID exist --> Deleting CAM Specification...";
      LOGGER.info(result);

      ConcreteArchitecturalModel cam = (ConcreteArchitecturalModel) camsList.get(0);

      // Start transaction
      session.beginTransaction();

      for (ConcreteComponent cc : cam.getConcreteComponent()) {
        for (Port p : cc.getPort())
          session.delete(p);
        session.delete(cc);
      }

      session.delete(cam);

      // Commit the changes to the database.
      session.getTransaction().commit();

      LOGGER.info(" CAM Specification has been deleted");
    }
    // Close the session.
    session.close();

    return result;
  }

  public EObject convertXMIStringToEObject(String xmiString) throws IOException {
    XMIResourceImpl resource = new XMIResourceImpl();
    resource.setEncoding("UTF-8");
    resource.load(new InputSource(new StringReader(xmiString)), null);

    return resource.getContents().get(0);
  }

  public ConcreteArchitecturalModel readModel(String camId) throws Exception {

    ConcreteArchitecturalModel cam = null;
    SessionFactory sessionFactory = dataStore.getSessionFactory();

    // Open a new Session
    Session session = sessionFactory.openSession();

    // Start transaction
    session.beginTransaction();

    Query query = session
        .createQuery("FROM ConcreteArchitecturalModel WHERE camID = '" + camId
            + "'");
    List<?> cams = query.list();

    if (cams.size() > 0) {
      cam = (ConcreteArchitecturalModel) cams.get(0);

      // Initialize CAM
      for (ConcreteComponent cc : cam.getConcreteComponent()) {
    	  for(Interface in : cc.getInterface()){
    		  if (in instanceof Provided) {
    	            Hibernate.initialize(((Provided) in).getDTarget());
    	          } else {
    	            Hibernate.initialize(((Required) in).getDSource());
    	          }
    	  }
    	  Hibernate.initialize(cc.getBSource()); 
    	  Hibernate.initialize(cc.getBTarget()); 
    	  Hibernate.initialize(cc.getRuntimeProperty());
        for (Port p : cc.getPort()) {
          if (p instanceof InputPort) {
            Hibernate.initialize(((InputPort) p).getCTarget());
          } else {
            Hibernate.initialize(((OutputPort) p).getCSource());
          }
        }
      }
      for (Relationship r : cam.getRelationship()) {
        if (r instanceof Binary) {
          for (AbstractDependency ad : ((Binary) r).getDependency()) {
            ConcreteDependency cd = ConcreteDependency.class.cast(ad);
            Hibernate.initialize(cd.getSource());
            Hibernate.initialize(cd.getTarget());
            for (Connector conn : cd.getConnector()) {
              Hibernate.initialize(conn.getSource());
              Hibernate.initialize(conn.getTarget());
            }
          }
        }
      }
    }
    // Close session
    session.close();

    LOGGER.info("[ManageArchitectures - readModel] - cam: " + cam);

    return cam;
  }

  public void saveModel(ConcreteArchitecturalModel cam) throws Exception {

    SessionFactory sessionFactory = dataStore.getSessionFactory();

    // Open a new Session
    Session session = sessionFactory.openSession();

    // Start transaction
    session.beginTransaction();

    session.save(cam);

    session.getTransaction().commit();

    session.close();

    LOGGER.info("[ManageArchitectures - saveModel] - cam: " + cam.getCamID());

  }

  public void addComponent(String camID, ConcreteComponent concreteComponent,
		  					 Port inputPort, List<RuntimeProperty> runtimePropertyList) throws Exception {
	  
    SessionFactory sessionFactory = dataStore.getSessionFactory();
    //String componentAlias = null;

    // Open a new Session
    Session session = sessionFactory.openSession();

    // Start transaction
    session.beginTransaction();

    Query query = session.createQuery("FROM ConcreteArchitecturalModel WHERE camID = '" + camID+ "'");

    List<?> cams = query.list();

    ConcreteArchitecturalModel cam = (ConcreteArchitecturalModel) cams.get(0);

    concreteComponent.getPort().add(inputPort);

    for (int i = 0; i < runtimePropertyList.size(); i++) {

      runtimePropertyList.get(i).setCc(concreteComponent);
      concreteComponent.getRuntimeProperty().add(runtimePropertyList.get(i));

      session.save(runtimePropertyList.get(i));
    }

    cam.getConcreteComponent().add(concreteComponent);

    // Save p
    session.save(inputPort);

    // Save cc
    session.save(concreteComponent);

    // Save cam
    session.save(cam);

    // Commit the changes to the database.
    session.getTransaction().commit();

    // Close the session.
    session.close();

    //return componentAlias;
  }

  public void saveComponentInstance(String componentName, String componentAlias, String componentInstance) throws Exception {
    
	SessionFactory sessionFactory = dataStore.getSessionFactory();

    try {
      // Open a new Session
      Session session = sessionFactory.openSession();

      // Start transaction
      session.beginTransaction();

      Query query = session.createQuery("FROM ConcreteComponent WHERE componentname = '"
              + componentName + "' AND componentalias= '" + componentAlias
              + "'");

      List<?> ccs = query.list();

      // Initialize the component
      ConcreteComponent cc = Architectural_metamodelFactory.eINSTANCE.createConcreteComponent();
      cc = (ConcreteComponent) ccs.get(0);

      cc.setComponentInstance(componentInstance);

      session.save(cc);

      // Commit the changes to the database.
      session.getTransaction().commit();

      // Close the session.
      session.close();

    } catch (Exception e) {
      LOGGER.error(e);
    }
  }

  public void changeComponentRuntimeProperties(String componentInstance, RuntimeProperty runtimeProperty) throws Exception {
    
	SessionFactory sessionFactory = dataStore.getSessionFactory();

    // Open a new Session
    Session session = sessionFactory.openSession();

    // Start transaction
    session.beginTransaction();

    Query query = session
        .createQuery("FROM ConcreteComponent WHERE componentinstance = '"
            + componentInstance + "'");

    List<?> ccs = query.list();

    // Initialize the component
    ConcreteComponent cc = Architectural_metamodelFactory.eINSTANCE.createConcreteComponent();
    cc = (ConcreteComponent) ccs.get(0);

    boolean found = false;
    for (int i = 0; i < cc.getRuntimeProperty().size() && found == false; i++) {
      if (cc.getRuntimeProperty().get(i).getPropertyID().equalsIgnoreCase(runtimeProperty.getPropertyID())) {
        cc.getRuntimeProperty().get(i).setPropertyValue(runtimeProperty.getPropertyValue());
        found = true;
        session.save(cc.getRuntimeProperty().get(i));
      }
    }

    session.save(cc);

    // Commit the changes to the database.
    session.getTransaction().commit();

    // Close the session.
    session.close();
  }

  public void changeComponentProperties(String componentInstance, String componentNewInstance,
		  								String componentNewName, String componentNewAlias) throws Exception {
    
	SessionFactory sessionFactory = dataStore.getSessionFactory();

    // Open a new Session
    Session session = sessionFactory.openSession();

    // Start transaction
    session.beginTransaction();

    Query query = session.createQuery("FROM ConcreteComponent WHERE componentinstance = '"+ componentInstance + "'");

    List<?> ccs = query.list();

    // Initialize the component
    ConcreteComponent cc = Architectural_metamodelFactory.eINSTANCE.createConcreteComponent();
    cc = (ConcreteComponent) ccs.get(0);

    cc.setComponentInstance(componentNewInstance);
    cc.setComponentName(componentNewName);
    cc.setComponentAlias(componentNewAlias);

    session.save(cc);

    // Commit the changes to the database.
    session.getTransaction().commit();

    // Close the session.
    session.close();
  }

  public void changeComponentService(String componentInstance,
		  							 int numberService, List<RuntimeProperty> listServices) throws Exception {
    
	SessionFactory sessionFactory = dataStore.getSessionFactory();

    // Open a new Session
    Session session = sessionFactory.openSession();

    // Start transaction
    session.beginTransaction();

    Query query = session.createQuery("FROM ConcreteComponent WHERE componentinstance = '"+ componentInstance + "'");

    List<?> ccs = query.list();

    // Initialize the component
    ConcreteComponent cc = Architectural_metamodelFactory.eINSTANCE.createConcreteComponent();
    cc = (ConcreteComponent) ccs.get(0);

    // Encontrar numero de servicios
    boolean found = false;
    int nServicios = 0;
    for (int i = 0; i < cc.getRuntimeProperty().size() && found == false; i++) {
      if (cc.getRuntimeProperty().get(i).getPropertyID().equalsIgnoreCase("numero_servicios")) {
        nServicios = Integer.parseInt(cc.getRuntimeProperty().get(i).getPropertyValue());
        found = true;
      }
    }

    // Borrar todos los servicios
    int nServiciosEncontrados = 0;
    for (int i = 0; i < cc.getRuntimeProperty().size()&& nServicios <= nServiciosEncontrados; i++) {
      String property = cc.getRuntimeProperty().get(i).getPropertyID();
      String service = "";

      if (property.length() > 9)
        service = property.substring(0, 9);

      if (service.equalsIgnoreCase("servicio" + nServiciosEncontrados)) {
        cc.getRuntimeProperty().remove(i);

        nServiciosEncontrados++;
      }
    }

    // Añadir los nuevos servicios
    for (int i = 0; i < listServices.size(); i++) {
      cc.getRuntimeProperty().add(listServices.get(i));
      session.save(listServices.get(i));
    }

    session.save(cc);

    // Commit the changes to the database.
    session.getTransaction().commit();

    // Close the session.
    session.close();

    RuntimeProperty rp = new RuntimePropertyImpl();
    rp.setCc(cc);
    rp.setPropertyID("numero_servicios");
    rp.setPropertyValue(numberService + "");
    changeComponentRuntimeProperties(componentInstance, rp);
  }

  public void deleteComponent(String camID, String componentInstance)throws Exception {
    
	// Result is the instance id
    SessionFactory sessionFactory = dataStore.getSessionFactory();

    // Open a new Session
    Session session = sessionFactory.openSession();

    // Start transaction
    session.beginTransaction();

    Query query = session.createQuery("FROM ConcreteArchitecturalModel WHERE camID = '" + camID+ "'");

    List<?> cams = query.list();

    ConcreteArchitecturalModel cam = (ConcreteArchitecturalModel) cams.get(0);
    LOGGER.info("[LRMM] Delete CAM ID: " + cam.getCamID());

    // Search the component to be deleted
    boolean found = false;
    for (int i = 0; i < cam.getConcreteComponent().size() && found == false; i++) {
      LOGGER.info("ITEM(" + i + "): "+ cam.getConcreteComponent().get(i).getComponentName());
      if (cam.getConcreteComponent().get(i).getComponentInstance().equalsIgnoreCase(componentInstance)) {
        LOGGER.info("CC NAME to intro: "+ cam.getConcreteComponent().get(i).getComponentInstance());

        ConcreteComponent cc = cam.getConcreteComponent().get(i);

        // Delete component source
        LOGGER.info("CC NAME --- SIZE bSOURCE: " + cc.getBSource().size());
        while (cc.getBSource().size() > 0) {
          LOGGER.info("        --- POS(" + (0) + ") bSOURCE id: "
              + cc.getBSource().get(0).getRelationshipID() + "   --   "
              + cc.getBSource().size());
          String idRelationship = cc.getBSource().get(0).getRelationshipID();
          cc.getBSource().remove(0);

          for (int k = 0; k < cam.getConcreteComponent().size(); k++) {
            LOGGER.info("        -----&----- ConcreteComponent: "+ cam.getConcreteComponent().get(k).getComponentName());
            for (int p = 0; p < cam.getConcreteComponent().get(k).getBSource().size(); p++)
              if (cam.getConcreteComponent().get(k).getBSource().get(p).getRelationshipID().equalsIgnoreCase(idRelationship))
                cam.getConcreteComponent().get(k).getBSource().remove(p);

            for (int p = 0; p < cam.getConcreteComponent().get(k).getBTarget().size(); p++)
              if (cam.getConcreteComponent().get(k).getBTarget().get(p).getRelationshipID().equalsIgnoreCase(idRelationship))
                cam.getConcreteComponent().get(k).getBTarget().remove(p);
          }

          for (int z = 0; z < cam.getRelationship().size(); z++) {
            if (cam.getRelationship().get(z).getRelationshipID().equalsIgnoreCase(idRelationship)) {
              Binary b = (Binary) cam.getRelationship().get(z);

              // Delete nary relationship
              if (((Binary) cam.getRelationship().get(z)).getNaryRelationship() != null) {
                Nary naryRelationship = ((Binary) cam.getRelationship().get(z)).getNaryRelationship();
                cam.getRelationship().remove((Nary) naryRelationship);
              }

              cam.getRelationship().remove(b);
              z = 0;
            }
          }
        }

        // Delete component target
        LOGGER.info("CC NAME --- SIZE bTARGET: " + cc.getBTarget().size());
        while (cc.getBTarget().size() > 0) {
          LOGGER.info("        --- POS(" + (0) + ") bTARGET id: "+ cc.getBTarget().get(0).getRelationshipID());
          String idRelationship = cc.getBTarget().get(0).getRelationshipID();
          cc.getBTarget().remove(0);

          for (int k = 0; k < cam.getConcreteComponent().size(); k++) {

            for (int p = 0; p < cam.getConcreteComponent().get(k).getBSource().size(); p++)
              if (cam.getConcreteComponent().get(k).getBSource().get(p).getRelationshipID().equalsIgnoreCase(idRelationship)) {
                // Objective connector
                ConcreteDependency cd = (ConcreteDependency) cam.getConcreteComponent().get(k).getBSource().get(p).getDependency().get(0);
                EList<Connector> connectorList = cd.getConnector();

                // Delete connector
                for (int q = 0; q < cam.getConcreteComponent().get(k).getPort().size(); q++) {
                  if ((cam.getConcreteComponent().get(k).getPort().get(q)) instanceof OutputPort) {
                    OutputPort port = (OutputPort) cam.getConcreteComponent().get(k).getPort().get(q);
                    for (int d = 0; d < connectorList.size(); d++) {
                      for (int t = 0; t < port.getCSource().size(); t++)
                        if (connectorList.get(d).getConnectorID().equalsIgnoreCase(port.getCSource().get(t).getConnectorID())) {
                          port.getCSource().remove(t);
                        }
                    }
                  } else {
                    if ((cam.getConcreteComponent().get(k).getPort().get(q)) instanceof InputPort) {
                      InputPort port = (InputPort) cam.getConcreteComponent().get(k).getPort().get(q);
                      for (int d = 0; d < connectorList.size(); d++) {
                        for (int t = 0; t < port.getCTarget().size(); t++)
                          if (connectorList.get(d).getConnectorID().equalsIgnoreCase(port.getCTarget().get(t).getConnectorID())) {
                            port.getCTarget().remove(t);
                          }
                      }
                    }
                  }
                }

                cam.getConcreteComponent().get(k).getBSource().remove(p);
              }

            for (int p = 0; p < cam.getConcreteComponent().get(k).getBTarget().size(); p++)
              if (cam.getConcreteComponent().get(k).getBTarget().get(p).getRelationshipID().equalsIgnoreCase(idRelationship)) {
                // Objective connector
                ConcreteDependency cd = (ConcreteDependency) cam.getConcreteComponent().get(k).getBTarget().get(p).getDependency().get(0);
                EList<Connector> connectorList = cd.getConnector();

                // Delete connector
                for (int q = 0; q < cam.getConcreteComponent().get(k).getPort().size(); q++) {
                  if ((cam.getConcreteComponent().get(k).getPort()) instanceof OutputPort) {
                    OutputPort port = (OutputPort) cam.getConcreteComponent().get(k).getPort().get(q);
                    for (int d = 0; d < connectorList.size(); d++) {
                      for (int t = 0; t < port.getCSource().size(); t++) {
                        if (connectorList.get(d).getConnectorID().equalsIgnoreCase(port.getCSource().get(t).getConnectorID())) {
                          port.getCSource().remove(t);
                        }
                      }
                    }
                  } else {
                    InputPort port = (InputPort) cam.getConcreteComponent().get(k).getPort().get(q);
                    for (int d = 0; d < connectorList.size(); d++) {
                      for (int t = 0; t < port.getCTarget().size(); t++)
                        if (connectorList.get(d).getConnectorID().equalsIgnoreCase(port.getCTarget().get(t).getConnectorID())) {
                          port.getCTarget().remove(t);
                        }
                    }
                  }
                }

                cam.getConcreteComponent().get(k).getBTarget().remove(p);
              }
          }

          for (int z = 0; z < cam.getRelationship().size(); z++) {
            if (cam.getRelationship().get(z).getRelationshipID().equalsIgnoreCase(idRelationship)) {
              Binary b = (Binary) cam.getRelationship().get(z);
              cam.getRelationship().remove(b);
              z = 0;
            }
          }

        }

        LOGGER.info("COMPONENT TO DELETE: "+ cam.getConcreteComponent().get(i).getComponentName());
        cam.getConcreteComponent().remove(i);

        session.save(cam);
        found = true;
      }
    }

    // Commit the changes to the database.
    session.getTransaction().commit();

    // Close the session.
    session.close();

  }

}
