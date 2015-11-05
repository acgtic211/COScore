/**
 */
package ccmm;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Controller Interface</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link ccmm.ControllerInterface#getProvidedInterface <em>Provided Interface</em>}</li>
 *   <li>{@link ccmm.ControllerInterface#getRequiredInterface <em>Required Interface</em>}</li>
 * </ul>
 * </p>
 *
 * @see ccmm.CcmmPackage#getControllerInterface()
 * @model extendedMetaData="name='ControllerInterface' kind='elementOnly'"
 * @generated
 */
public interface ControllerInterface extends ComponentStructure {
	/**
	 * Returns the value of the '<em><b>Provided Interface</b></em>' containment reference list.
	 * The list contents are of type {@link ccmm.ProvidedInterface}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Provided Interface</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Provided Interface</em>' containment reference list.
	 * @see ccmm.CcmmPackage#getControllerInterface_ProvidedInterface()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<ProvidedInterface> getProvidedInterface();

	/**
	 * Returns the value of the '<em><b>Required Interface</b></em>' containment reference list.
	 * The list contents are of type {@link ccmm.RequiredInterface}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Required Interface</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Required Interface</em>' containment reference list.
	 * @see ccmm.CcmmPackage#getControllerInterface_RequiredInterface()
	 * @model containment="true"
	 * @generated
	 */
	EList<RequiredInterface> getRequiredInterface();

} // ControllerInterface
