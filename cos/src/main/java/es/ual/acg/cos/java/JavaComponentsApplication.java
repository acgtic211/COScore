package es.ual.acg.cos.java;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;


public class JavaComponentsApplication
{

	private static final String classes_folder = "H:\\eclipse\\workspace\\JavaComponentsApplication\\classes_repository\\";

	private Socket localClientSocket;
	private ObjectOutputStream objectOutputStream;

	private ServerSocket serverSocket;
	private boolean serverStarted;
	private ObjectInputStream objectInputStream;

	private Socket remoteClientSocket;
	
	private List<Object> components;
	private List<String> classes;

	
	
	public JavaComponentsApplication()	{
		
		this.localClientSocket = null;
		this.objectOutputStream = null;

		this.serverSocket = null;
		this.serverStarted = false;
		this.objectInputStream = null;

		this.remoteClientSocket = null;
		
		this.components = new ArrayList<Object>();
		this.classes = new ArrayList<String>();
	}

	
	
	public void start(String _applicationHost, int _applicationPort, String _repositoryHost, int _repositoryPort) {
		
		Object component;
		String className;
		int index;
		
		
		
		component = this.getComponent(_repositoryHost, _repositoryPort, _applicationHost, _applicationPort, "user1", "application1", "HelloWorldComponent", "HWC1");
		this.components.add(component);
		this.classes.add("HelloWorldComponent");

		component = this.getComponent(_repositoryHost, _repositoryPort, _applicationHost, _applicationPort, "user1", "application1", "HelloWorldComponent", "HWC2");
		this.components.add(component);
		this.classes.add("HelloWorldComponent");

		component = this.getComponent(_repositoryHost, _repositoryPort, _applicationHost, _applicationPort, "user1", "application1", "HelloWorldComponent", "HWC3");
		this.components.add(component);
		this.classes.add("HelloWorldComponent");

		
		for(index=0; index<this.components.size(); index++)
		{
			component = this.components.get(index);
			className = this.classes.get(index);

			
			
			if(component != null)
			{
				this.startComponent(component, className);
			}
		}
	}

	
	
	public void startComponent(Object _componentInstance, String _componentId)
	{
		String classFileName;
		String classFilePath;

		URL[] urls;
		URLClassLoader urlClassLoader;
		Class<?> componentClass;

		
		
		classFileName = _componentId + ".jar";
		classFilePath = JavaComponentsApplication.classes_folder + classFileName;
		
		
		
		try
		{
			urls = new URL[]{new URL("file:///" + classFilePath)};
			urlClassLoader = new URLClassLoader(urls);
			componentClass = urlClassLoader.loadClass("component." + _componentId);
	        Method method = componentClass.getMethod("start");
	        

	        
			method.invoke(_componentInstance);
		}
		catch(MalformedURLException exception)
		{
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
		catch(IllegalAccessException exception)
		{
			exception.printStackTrace();
		}
		catch(IllegalArgumentException exception)
		{
			exception.printStackTrace();
		}
		catch(InvocationTargetException exception)
		{
			exception.printStackTrace();
		}
	}



	public JavaComponentResponse getComponent(String _repositoryHost, int _repositoryPort, String _applicationHost, int _applicationPort, String _userId, String _applicationId, String _componentId, String _componentName)
	{
		JavaComponentResponse response;
		
		String classFileName;
		String classFilePath;

		Object component;
		
		
		
		this.startClient(_repositoryHost, _repositoryPort);
		//System.out.println();
		//System.out.println("Local client connected...");

		this.sendRequest(_applicationHost, _applicationPort, _userId, _applicationId, _componentId, _componentName);
//		System.out.println();
//		System.out.println("Application request sent...");

		this.stopClient();
//		System.out.println();
//		System.out.println("Local client disconnected...");
		
		this.startServer(_applicationPort);
//		System.out.println();
//		System.out.println("Server connected...");
		
		response = this.handleRequest();
		
//		response...

		/*
		
		GUARDAR EL COMPONENTE EN EL SERVIDOR		
		
		this.saveComponentPackageFile(response);
		System.out.println();
		System.out.println("Component package saved...");

		
		classFileName = response.getComponentId() + ".jar";
		classFilePath = JavaComponentsApplication.classes_folder + classFileName;

		this.loadComponentPackage(classFilePath);
		 */
		
		//component = this.deserialize(response.getComponentInstance());
//		System.out.println();
//		System.out.println("Component instance got...");
		
		//return(component);
		return response;
	}
	
	
	
	public Object deserialize(byte[] _componentInstance)
	{
	    ByteArrayInputStream byteArrayInputStream;
	    ObjectInputStream objectInputStream;
	    Object component;
	    
	    
	    
	    component = null;
	    
		try
		{
			byteArrayInputStream = new ByteArrayInputStream(_componentInstance);
			objectInputStream = new ObjectInputStream(byteArrayInputStream);
			component = objectInputStream.readObject(); 
		}
		catch(IOException exception)
		{
			exception.printStackTrace();
		}
		catch(ClassNotFoundException exception)
		{
			exception.printStackTrace();
		}
		
		
		
	    return(component);
	}
	
	
	public void stop() {
		
	}

	
	
	public void startClient(String _repositoryHost, int _repositoryPort)
	{
		try
		{
			this.localClientSocket = new Socket(_repositoryHost, _repositoryPort);
		}
		catch(UnknownHostException exception)
		{
			exception.printStackTrace();
		}
		catch(IOException exception)
		{
			exception.printStackTrace();
		}
	}
	
	
	
	private void sendRequest(String _applicationHost, int _applicationPort, String _userId, 
			String _applicationId, String _componentId, String _componentName)
	{
		JavaComponentRequest request;

		try
		{
			this.objectOutputStream = new ObjectOutputStream(this.localClientSocket.getOutputStream());

			request = new JavaComponentRequest();

			request.setApplicationHost(_applicationHost);
			request.setApplicationPort(_applicationPort);
			request.setUserId(_userId);
			request.setApplicationId(_applicationId);
			request.setComponentId(_componentId);
			request.setComponentName(_componentName);

			this.objectOutputStream.writeObject(request);
		}
		catch(UnknownHostException exception)
		{
			exception.printStackTrace();
		}
		catch(IOException exception)
		{
			exception.printStackTrace();
		}
	}
	
	
	
	private void stopClient()
	{
		try
		{
			this.localClientSocket.close();
		}
		catch(IOException exception)
		{
			exception.printStackTrace();
		}		
	}
	
	
	
	private void startServer(int _applicationPort) {
		try
		{
			this.serverSocket = new ServerSocket(_applicationPort);
			this.serverStarted = true;
		}
		catch(IOException exception)
		{
			exception.printStackTrace();
		}
	}

	
	
	private JavaComponentResponse handleRequest() {
		JavaComponentResponse response;
		
		
		
		response = null;
		
		
		
		while(this.serverStarted == true)
		{
			try
			{
				this.remoteClientSocket = this.serverSocket.accept();
//				System.out.println();
//				System.out.println("Remote client connected...");
				
				response = this.receiveResponse();
//				System.out.println();
//				System.out.println("Application response received...");
//				System.out.println("  userId = " + response.getUserId());
//				System.out.println("  applicationId = " + response.getApplicationId());
//				System.out.println("  componentId = " + response.getComponentId());
//				System.out.println("  componentName = " + response.getComponentName());
//				System.out.println("  componentInstanceName = " + response.getComponentInstanceName());
//				System.out.println("  componentInstance = " + response.getComponentInstance().toString());
//				System.out.println("  componentPackageData = " + response.getComponentPackageData().toString());

				this.remoteClientSocket.close();
//				System.out.println();
//				System.out.println("Remote client disconnected...");

				this.stopServer();
//				System.out.println();
//				System.out.println("Server disconnected...");
			}
			catch(IOException exception)
			{
				exception.printStackTrace();
			}
		}
		
		
		
		return(response);
	}
	
	
	
	private JavaComponentResponse receiveResponse()	{
		JavaComponentResponse response;
		
		
		
		response = null;
		
		
		
		try
		{
			this.objectInputStream = new ObjectInputStream(this.remoteClientSocket.getInputStream());

			response = (JavaComponentResponse) this.objectInputStream.readObject();
		}
		catch(IOException exception)
		{
			exception.printStackTrace();
		}
		catch(ClassNotFoundException exception)
		{
			exception.printStackTrace();
		}
		
		
		
		return(response);
	}

	
	
	private void stopServer()
	{
		try
		{
			this.serverSocket.close();
			this.serverStarted = false;
		}
		catch(IOException exception)
		{
			exception.printStackTrace();
		}
	}


	
	public void saveComponentPackageFile(JavaComponentResponse _response)
	{
		String classFileName;
		String classFilePath;
		File file;
		FileOutputStream fileOutputStream;

		
		
		classFileName = _response.getComponentId() + ".jar";
		classFilePath = JavaComponentsApplication.classes_folder + classFileName;
		file = new File(classFilePath);


		
		try
		{
			fileOutputStream = new FileOutputStream(file);

			
			
			fileOutputStream.write(_response.getComponentPackageData());
			fileOutputStream.flush();
			fileOutputStream.close();
		}
		catch(IOException exception)
		{
			exception.printStackTrace();
		}
	}

	
	
	private void loadComponentPackage(String _classFilePath)
	{
		URL url;
		URLClassLoader urlClassLoader;
		Class<?> systemClass;
		Method method;
		
		
		try
		{
			url = new URL("file:///" + _classFilePath);
			urlClassLoader = (URLClassLoader)ClassLoader.getSystemClassLoader();
			systemClass = URLClassLoader.class;
			method = systemClass.getDeclaredMethod("addURL", new Class[]{URL.class});
			
			
			
			method.setAccessible(true);
			method.invoke(urlClassLoader, new Object[]{url});
		}
		catch(MalformedURLException exception)
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
		catch(IllegalAccessException exception)
		{
			exception.printStackTrace();
		}
		catch(IllegalArgumentException exception)
		{
			exception.printStackTrace();
		}
		catch(InvocationTargetException exception)
		{
			exception.printStackTrace();
		}
	}
	
}
