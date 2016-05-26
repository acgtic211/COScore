/*
 * InterModulesData.java -- Estructura auxiliar para la comunicación entre modulos
 * Copyright (C) 2016  Alfredo Valero Rodríguez, Javier Criado Rodríguez and Jesús Vallecillos Ruíz
 *
 * InterModulesData.java is part of COScore Community.
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
 *  Authors: Alfredo Valero Rodríguez  Software Developer 
 *           Javier Criado Rodríguez   Doctor/Researcher/Software Developer
 *           Jesús Vallecillos Ruíz    Pre-doctoral scholarship holders/Researcher/Software Developer
 *    Group: ACG 		               Applied Computing Group
 * Internet: http://acg.ual.es/        
 *   E-mail: acg.tic211@ual.es        
 *   Adress: Edif. Científico Técnico, CITE-III
 *           Universidad de Almería
 *           Almeria, España
 *           04120
*/
package es.ual.acg.cos.types;

import java.util.List;

import es.ual.acg.cos.wookie.WidgetData;
import architectural_metamodel.ConcreteArchitecturalModel;

/**************
 * 
 * Clase para la comunicación entre modulos
 *
 */
public class InterModulesData {

		private String value; //Puede tomar valores variables, depende de la comunicacion concreta, Ej; CAmID en una, UserID en otra,...
		private String message; //Mensajes variables, generalmente del error producido en un modulo.
		private List<ComponentData> model; //Es necesario para la transmision del modelo en casos concretos
		private ConcreteArchitecturalModel cam; //Es necesario para la transmision del modelo obtenido en hibernate
		private WidgetData widget; ////Es necesario para la transmision de los datos de los widgets de wookie
		
		public InterModulesData() {
			this.value = null;
			this.model = null;
			this.cam = null;
			this.widget = null;
			this.message = null;
		}
		
		public InterModulesData(String value, List<ComponentData> model, String message,ConcreteArchitecturalModel cam, WidgetData widget) {
			this.value = value;
			this.model = model;
			this.cam = cam;
			this.widget = widget;
			this.message = message;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public List<ComponentData> getModel() {
			return model;
		}

		public void setModel(List<ComponentData> model) {
			this.model = model;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public ConcreteArchitecturalModel getCam() {
			return cam;
		}

		public void setCam(ConcreteArchitecturalModel cam) {
			this.cam = cam;
		}

		public WidgetData getWidget() {
			return widget;
		}

		public void setWidget(WidgetData widget) {
			this.widget = widget;
		}

}
