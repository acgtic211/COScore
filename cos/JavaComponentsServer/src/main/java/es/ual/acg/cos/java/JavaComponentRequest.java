package es.ual.acg.cos.java;
import java.io.Serializable;



public class JavaComponentRequest implements Serializable
{

	private static final long serialVersionUID = 1L;

	private String userId;
	private String applicationId;
	private String componentId;
	private String componentInstanceName;

	private String componentName;

	private String applicationHost;
	private int applicationPort;
	
	
	
	public JavaComponentRequest()
	{
		this.userId = null;
		this.applicationId = null;
		this.componentId = null;
		this.componentInstanceName = null;
		
		this.componentName = null;
		
		this.applicationHost = null;
		this.applicationPort = 0;
	}

	
	
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	
	
	
	public void setApplicationId(String applicationId)
	{
		this.applicationId = applicationId;
	}

	
	
	public void setComponentId(String componentId)
	{
		this.componentId = componentId;
	}

	
	
	public void setComponentName(String componentName)
	{
		this.componentName = componentName;
	}
	
	public void setComponentInstanceName(String _componentInstanceName)
	{
		this.componentInstanceName = _componentInstanceName;
	}
	
	
	
	public void setApplicationHost(String applicationHost)
	{
		this.applicationHost = applicationHost;
	}
	
	
	
	public void setApplicationPort(int applicationPort)
	{
		this.applicationPort = applicationPort;
	}

	
	
	public String getUserId()
	{
		return(this.userId);
	}
	
	
	
	public String getApplicationId()
	{
		return(this.applicationId);
	}

	
	
	public String getComponentId()
	{
		return(this.componentId);
	}

	
	
	public String getComponentName()
	{
		return(this.componentName);
	}
	
	public String getComponentInstanceName()
	{
		return(this.componentInstanceName);
	}
	
	
	public String getApplicationHost()
	{
		return(this.applicationHost);
	}
	
	
	
	public int getApplicationPort()
	{
		return(this.applicationPort);
	}
	
}
