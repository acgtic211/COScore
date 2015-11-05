/**
 */
package architectural_metamodel;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Concrete Component</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link architectural_metamodel.ConcreteComponent#getCam <em>Cam</em>}</li>
 *   <li>{@link architectural_metamodel.ConcreteComponent#getRuntimeProperty <em>Runtime Property</em>}</li>
 *   <li>{@link architectural_metamodel.ConcreteComponent#getPort <em>Port</em>}</li>
 *   <li>{@link architectural_metamodel.ConcreteComponent#getComponentInstance <em>Component Instance</em>}</li>
 * </ul>
 * </p>
 *
 * @see architectural_metamodel.Architectural_metamodelPackage#getConcreteComponent()
 * @model
 * @generated
 */
public interface ConcreteComponent extends AbstractComponent {
	/**
	 * Returns the value of the '<em><b>Cam</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link architectural_metamodel.ConcreteArchitecturalModel#getConcreteComponent <em>Concrete Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cam</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cam</em>' container reference.
	 * @see #setCam(ConcreteArchitecturalModel)
	 * @see architectural_metamodel.Architectural_metamodelPackage#getConcreteComponent_Cam()
	 * @see architectural_metamodel.ConcreteArchitecturalModel#getConcreteComponent
	 * @model opposite="concreteComponent" required="true" transient="false"
	 * @generated
	 */
	ConcreteArchitecturalModel getCam();

	/**
	 * Sets the value of the '{@link architectural_metamodel.ConcreteComponent#getCam <em>Cam</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cam</em>' container reference.
	 * @see #getCam()
	 * @generated
	 */
	void setCam(ConcreteArchitecturalModel value);

	/**
	 * Returns the value of the '<em><b>Runtime Property</b></em>' containment reference list.
	 * The list contents are of type {@link architectural_metamodel.RuntimeProperty}.
	 * It is bidirectional and its opposite is '{@link architectural_metamodel.RuntimeProperty#getCc <em>Cc</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Runtime Property</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Runtime Property</em>' containment reference list.
	 * @see architectural_metamodel.Architectural_metamodelPackage#getConcreteComponent_RuntimeProperty()
	 * @see architectural_metamodel.RuntimeProperty#getCc
	 * @model opposite="cc" containment="true"
	 * @generated
	 */
	EList<RuntimeProperty> getRuntimeProperty();

	/**
	 * Returns the value of the '<em><b>Port</b></em>' containment reference list.
	 * The list contents are of type {@link architectural_metamodel.Port}.
	 * It is bidirectional and its opposite is '{@link architectural_metamodel.Port#getCc <em>Cc</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Port</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Port</em>' containment reference list.
	 * @see architectural_metamodel.Architectural_metamodelPackage#getConcreteComponent_Port()
	 * @see architectural_metamodel.Port#getCc
	 * @model opposite="cc" containment="true" required="true"
	 * @generated
	 */
	EList<Port> getPort();

	/**
	 * Returns the value of the '<em><b>Component Instance</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component Instance</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Component Instance</em>' attribute.
	 * @see #setComponentInstance(String)
	 * @see architectural_metamodel.Architectural_metamodelPackage#getConcreteComponent_ComponentInstance()
	 * @model default=""
	 * @generated
	 */
	String getComponentInstance();

	/**
	 * Sets the value of the '{@link architectural_metamodel.ConcreteComponent#getComponentInstance <em>Component Instance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Component Instance</em>' attribute.
	 * @see #getComponentInstance()
	 * @generated
	 */
	void setComponentInstance(String value);

} // ConcreteComponent
