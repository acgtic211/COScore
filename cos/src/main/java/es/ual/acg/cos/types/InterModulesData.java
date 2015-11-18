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
