/**
 */
package ccmm;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Extra Functional</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link ccmm.ExtraFunctional#getProperty <em>Property</em>}</li>
 *   <li>{@link ccmm.ExtraFunctional#getDependency <em>Dependency</em>}</li>
 * </ul>
 * </p>
 *
 * @see ccmm.CcmmPackage#getExtraFunctional()
 * @model extendedMetaData="name='ExtraFunctional' kind='elementOnly'"
 * @generated
 */
public interface ExtraFunctional extends EObject {
	/**
	 * Returns the value of the '<em><b>Property</b></em>' reference list.
	 * The list contents are of type {@link ccmm.Property}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property</em>' reference list.
	 * @see ccmm.CcmmPackage#getExtraFunctional_Property()
	 * @model required="true"
	 * @generated
	 */
	EList<Property> getProperty();

	/**
	 * Returns the value of the '<em><b>Dependency</b></em>' reference list.
	 * The list contents are of type {@link ccmm.Dependency}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dependency</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dependency</em>' reference list.
	 * @see ccmm.CcmmPackage#getExtraFunctional_Dependency()
	 * @model required="true"
	 * @generated
	 */
	EList<Dependency> getDependency();

} // ExtraFunctional
