package es.ual.acg.cos.java;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="JavaComponentResponse")
public class JavaComponentResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userId;
	private String applicationId;
	private String componentId;
	private String componentInstanceName;
	
	private String componentName;
	
	private byte[] componentInstance;
	private byte[] componentPackageData;

	
	
	
	public JavaComponentResponse() {
		this.userId = null;
		this.applicationId = null;
		this.componentId = null;
		this.componentInstanceName = null;
		
		this.componentName = null;
		
		this.componentInstance = null;
		this.componentPackageData = null;
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
	
	
	
	public void setComponentInstance(byte[] _componentInstance)
	{
		this.componentInstance = _componentInstance;
	}
	
	public void setComponentInstanceName(String _componentInstanceName)
	{
		this.componentInstanceName = _componentInstanceName;
	}
	
	
	public void setComponentPackageData(byte[] _componentPackageData)
	{
		this.componentPackageData = _componentPackageData;
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

	
	
	public byte[] getComponentInstance()
	{
		return(this.componentInstance);
	}
	
	public String getComponentInstanceName()
	{
		return(this.componentInstanceName);
	}	
	
	
	public byte[] getComponentPackageData()
	{
		return(this.componentPackageData);
	}
	
}
