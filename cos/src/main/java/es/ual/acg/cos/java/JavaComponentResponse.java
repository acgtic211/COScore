/*
 * JavaComponentResponse.java -- ...
 * Copyright (C) 2016  Javier Criado Rodríguez and Jesús Vallecillos Ruíz
 *
 * JavaComponentResponse.java is part of COScore Community.
 * 
 * COScore Community is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Foobar.  If not, see <http://www.gnu.org/licenses/gpl.html> or
 * see  <https://github.com/acgtic211/COScore-Community>.  Or write to
 * the Free Software Foundation, I51 Franklin Street, Fifth Floor,
 * Boston, MA 02110-1335, USA.
 *
 *  Authors: Javier Criado Rodríguez   Doctor/Researcher/Software Developer
 *           Jesús Vallecillos Ruíz    Pre-doctoral scholarship holders/Researcher/Software Developer
 *    Group: ACG 		               Applied Computing Group
 * Internet: http://acg.ual.es/        
 *   E-mail: acg.tic211@ual.es        
 *   Adress: Edif. Científico Técnico, CITE-III
 *           Universidad de Almería
 *           Almeria, España
 *           04120
*/
package es.ual.acg.cos.java;

import java.io.Serializable;



public class JavaComponentResponse implements Serializable
{

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
