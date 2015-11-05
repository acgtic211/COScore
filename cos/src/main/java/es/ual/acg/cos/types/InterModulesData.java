package es.ual.acg.cos.types;

import java.util.List;

/**************
 * 
 * Clase para la comunicación entre modulos
 *
 */
public class InterModulesData {

		private String value; //Puede tomar valores variables, depende de la comunicacion concreta, Ej; CAmID en una, UserID en otra,...
		private List<ComponentData> model; //Es necesario para la transmision del modelo en casos concretos
		private String message; //Mensajes variables, generalmente del error producido en un modulo.
		
		public InterModulesData() {
			this.value = null;
			this.model = null;
			this.message = null;
		}
		
		public InterModulesData(String value, List<ComponentData> model, String message) {
			this.value = value;
			this.model = model;
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

}
