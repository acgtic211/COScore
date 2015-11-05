package es.ual.acg.cos.java;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.StringTokenizer;

import javax.activation.DataHandler;

import org.jboss.logging.Logger;

import java.lang.reflect.Field;

import java.util.Collection;



public class JavaComponentsRepository extends URLClassLoader{
	
	public JavaComponentsRepository(URL[] urls) {
		super(urls);
		// TODO Auto-generated constructor stub
	}

	private static final Logger LOGGER = Logger.getLogger(JavaComponentsRepository.class);

	private static final String nodejs_url = "http://192.168.119.183:6969/";

//	private static final String classes_folder = "C:\\eclipse-COScore\\workspace\\JavaComponentsServer\\classes_repository\\";
//	private static final String instances_folder = "C:\\eclipse-COScore\\workspace\\JavaComponentsServer\\instances_repository\\";
	
	// CUANDO SE DESPLIEGUE EN EL SERVIDOR HABRÁ QUE PONER LA DIRECCIÓN ABSOLUTA DONDE SE ENCUENTRAN ESTOS REPOSITORIOS
	private static final String classes_folder = "C:/eclipse-COScore/workspace/JavaComponentsServer/classes_repository/";
	private static final String instances_folder = "C:/eclipse-COScore/workspace/JavaComponentsServer/instances_repository/";
	
//	private static final String classes_folder = (new File("")).getAbsolutePath() + "/classes_repository/";
//	private static final String instances_folder = (new File("")).getAbsolutePath() + "/instances_repository/";


	public JavaComponentResponse handleRequests(String userID, String applicationId, String javaComponentID, String javaComponentName) {
		
		JavaComponentRequest request = new JavaComponentRequest();
		
		request.setUserId(userID);
		request.setApplicationId(applicationId);
		request.setComponentId(javaComponentID);
		request.setComponentName(javaComponentName);
		
		byte[] componentInstance;
		byte[] componentPackageData;
		
		componentPackageData = null;
		
		LOGGER.info("Remote client connected...");

		LOGGER.info("Application request received...");
		LOGGER.info("  applicationHost = " + request.getApplicationHost());
		LOGGER.info("  applicationPort = " + request.getApplicationPort());
		LOGGER.info("  userId = " + request.getUserId());
		LOGGER.info("  applicationId = " + request.getApplicationId());
		
		LOGGER.info("  componentId = " + request.getComponentId());
		LOGGER.info("  componentName = " + request.getComponentName());

		componentInstance = this.getComponentInstance(request);
		LOGGER.info("Component instance got...");

		componentPackageData = this.getComponentPackageData(request.getComponentId());
		LOGGER.info("Component package data got...");

		return this.sendResponse(request, componentInstance, componentPackageData);
		
	}
	
	

	private Object createComponentInstance(String _classFilePath, JavaComponentRequest _request) {

		URLClassLoader urlClassLoader;
		Class<?> componentClass;
		Constructor<?> componentConstructor;
		Object componentInstance;

		componentInstance = null;
		
		try {
			LOGGER.info("FICHEROOO - " + URI.create("file:/" + _classFilePath).toURL().getPath());

			//urlClassLoader = new URLClassLoader(new URL[]{new URL(URI.create("\\" + _classFilePath).toURL().getPath())});
			urlClassLoader = new URLClassLoader(new URL[]{new URL(URI.create("/" + _classFilePath).toURL().getPath())});

			componentClass = urlClassLoader.loadClass(_request.getComponentId() + "." + _request.getComponentId());

			componentConstructor = componentClass.getConstructor(String.class, String.class, String.class, String.class, String.class);

			componentInstance = componentConstructor.newInstance(_request.getComponentName(), _request.getComponentId(), 
									getComponentInstanceName(_request), _request.getUserId(), JavaComponentsRepository.nodejs_url);
			
			urlClassLoader.close();
			
			deleteComponentJava(_classFilePath + _request.getComponentId() + ".jar");
		}
		catch(MalformedURLException exception)
		{
			//LOGGER.error(e.toString());
			exception.printStackTrace();
		}
		catch(ClassNotFoundException exception)
		{
			exception.printStackTrace();
		}
		catch(NoSuchMethodException exception)
		{
			exception.printStackTrace();
		}
		catch(SecurityException exception)
		{
			exception.printStackTrace();
		}
		catch(InstantiationException exception)
		{
			exception.printStackTrace();
		}
		catch(IllegalAccessException exception)
		{
			exception.printStackTrace();
		}
		catch(IllegalArgumentException exception)
		{
			//exception.printStackTrace();
		}
		catch(InvocationTargetException exception)
		{
			exception.printStackTrace();
		}
		catch(IOException exception)
		{
			exception.printStackTrace();
		}		
		
		return(componentInstance);
	}
	
	
	
	private void saveToFile(File _file, Object _component) {
		
		FileOutputStream fileOutputStream;
		ObjectOutputStream objectOutputStream;
		
		try
		{
			
			fileOutputStream = new FileOutputStream(_file);
			objectOutputStream = new ObjectOutputStream(fileOutputStream);
			
			
			
			objectOutputStream.writeObject(_component);
			objectOutputStream.close();
		}
		catch(FileNotFoundException exception)
		{
			exception.printStackTrace();
		}
		catch(IOException exception)
		{
			exception.printStackTrace();
		}
	}
	
	
	private JavaComponentResponse sendResponse(JavaComponentRequest _request, byte[] _componentInstance, byte[] _componentPackageData) {
		
		JavaComponentResponse response = new JavaComponentResponse();

		response.setUserId(_request.getUserId());
		response.setApplicationId(_request.getApplicationId());
		response.setComponentId(_request.getComponentId());
		response.setComponentName(_request.getComponentName());
		response.setComponentInstanceName(getComponentInstanceName(_request));
		response.setComponentInstance(_componentInstance);
		response.setComponentPackageData(_componentPackageData);
		
		return response;
	}


	
	private String getComponentInstanceName(JavaComponentRequest _request) {
		
		String componentInstanceName = _request.getUserId() + "_" + _request.getApplicationId() + "_" + 
												_request.getComponentId() + "_" + _request.getComponentName();
		
		return componentInstanceName;
	}
	
	
	
	private byte[] getComponentInstance(JavaComponentRequest _request) {
		
		String instanceFileName;
		String instanceFilePath;
		File instanceFile;

		String classFileName;
		String classFilePath;

		Object componentObject;
		
		byte[] componentInstance;
		
		
		instanceFileName = _request.getUserId() + "_" + _request.getApplicationId() + "_" + _request.getComponentId() + "_" + _request.getComponentName() + ".obj";
		instanceFilePath = JavaComponentsRepository.instances_folder + instanceFileName;

		instanceFile = new File(instanceFilePath);

		if(instanceFile.exists() == false) {

			classFileName = _request.getComponentId() + ".jar";

			classFilePath = JavaComponentsRepository.classes_folder + classFileName;

			componentObject = this.createComponentInstance(classFilePath, _request);
			
			
			this.saveToFile(instanceFile, componentObject);	
		}else{
			System.out.println("Exist instance");
		}
		componentInstance = this.getDataFromFile(instanceFilePath);		
		
		return(componentInstance);
	}


    
	public byte[] getComponentPackageData(String _componentId) {
		
		String classFileName;
		String classFilePath;

		byte[] componentPackageData;

		
		
		classFileName = _componentId + ".jar";
		classFilePath = JavaComponentsRepository.classes_folder + classFileName;

		componentPackageData = this.getDataFromFile(classFilePath);
		
		
		
		return(componentPackageData);
	}


    
	public byte[] getDataFromFile(String _filePath)	{
		
		File file;
		FileInputStream fileInputStream;
		DataInputStream dataInputStream;
		int size;
		byte[] componentPackageData;
		int read;
		int numRead;

		
		
		file = new File(_filePath);
		componentPackageData = null;

		
		
		try
		{
			if(file.isFile() == true)
			{
//				System.out.println("El paquete existe.");				

				fileInputStream = new FileInputStream(file);
				dataInputStream = new DataInputStream(fileInputStream);
				size = (int) file.length();
				componentPackageData = new byte[size];
				read = 0;
				numRead = 0;
				while(read < componentPackageData.length && (numRead = dataInputStream.read(componentPackageData, read, componentPackageData.length - read)) >= 0)
				{
					read = read + numRead;
				}
				
				dataInputStream.close();
			}
			else
			{
//				System.out.println("El paquete no existe.");				
			}
		}
		catch(FileNotFoundException exception)
		{
			exception.printStackTrace();
		}
		catch(IOException exception)
		{
			exception.printStackTrace();
		}
		
		
		
		return(componentPackageData);
	}
	
	public boolean deleteComponentJava(String componentId){

		File instanceFiles = new File(instances_folder);
		File componentFile = new File(classes_folder + componentId + ".jar");
		
		File[] instances = instanceFiles.listFiles();
		
		for (int i = 0; i < instances.length; i++){
			LOGGER.info(instances[i].getName());
			String instance = instances[i].getName();
			StringTokenizer st = new StringTokenizer(instance, "_");
			st.nextToken();st.nextToken();
			if(st.nextToken().equalsIgnoreCase(componentId))
				instances[i].delete();
		}
		
		if (componentFile.delete()){
			LOGGER.info("Component deleted");
			return true;
		} else {
			LOGGER.info("Component not deleted");
			return false;
		}
	}
	
	public boolean addComponentJava(String componentId, DataHandler jar) {
		
		try {
			InputStream is = jar.getInputStream();

			OutputStream os = new FileOutputStream(new File(classes_folder + componentId + ".jar"));

			byte[] buffer = new byte[10000];
			int bytesRead = 0;
			while ((bytesRead = is.read(buffer)) != -1) {
				os.write(buffer,0,bytesRead);
			}
			
			os.close();
		} catch(IOException ioe) {
			ioe.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	
	public void close() {
	    try {
	        Class clazz = java.net.URLClassLoader.class;
	        Field ucp = clazz.getDeclaredField("ucp");    //acceso a URLClassPath
	        ucp.setAccessible(true);
	        Object sunMiscURLClassPath = ucp.get(this);
	        Field loaders = sunMiscURLClassPath.getClass().getDeclaredField("loaders");    //acceso a ArrayList
	        loaders.setAccessible(true);
	        Object collection = loaders.get(sunMiscURLClassPath);    //colección de JarLoader's
	        for (Object sunMiscURLClassPathJarLoader : ((Collection) collection).toArray()) {
	            try {
	                Field loader = sunMiscURLClassPathJarLoader.getClass().getDeclaredField("jar");    //acceso a JarLoader
	                loader.setAccessible(true);
	                Object jarFile = loader.get(sunMiscURLClassPathJarLoader);    //acceso a JarFile
	                ((FilterInputStream) jarFile).close();    //close()
	            } catch (Throwable t) {
	                // if we got this far, this is probably not a JAR loader so skip it
	            }
	        }
	    } catch (Throwable t) {
	        // probably not a SUN VM
	    }

	}
	
}
