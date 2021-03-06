/**
 */
package ccmm;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Concrete Component Specification</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link ccmm.ConcreteComponentSpecification#getComponentName <em>Component Name</em>}</li>
 *   <li>{@link ccmm.ConcreteComponentSpecification#getAbstractComponentID <em>Abstract Component ID</em>}</li>
 *   <li>{@link ccmm.ConcreteComponentSpecification#getComponentDescription <em>Component Description</em>}</li>
 *   <li>{@link ccmm.ConcreteComponentSpecification#getFunctional <em>Functional</em>}</li>
 *   <li>{@link ccmm.ConcreteComponentSpecification#getExtraFunctional <em>Extra Functional</em>}</li>
 *   <li>{@link ccmm.ConcreteComponentSpecification#getComponentAlias <em>Component Alias</em>}</li>
 *   <li>{@link ccmm.ConcreteComponentSpecification#getPackaging <em>Packaging</em>}</li>
 *   <li>{@link ccmm.ConcreteComponentSpecification#getMarketing <em>Marketing</em>}</li>
 * </ul>
 * </p>
 *
 * @see ccmm.CcmmPackage#getConcreteComponentSpecification()
 * @model extendedMetaData="name='ConcreteComponentSpecification' kind='elementOnly'"
 * @generated
 */
public interface ConcreteComponentSpecification extends EObject {
	/**
	 * Returns the value of the '<em><b>Component Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Component Name</em>' attribute.
	 * @see #setComponentName(String)
	 * @see ccmm.CcmmPackage#getConcreteComponentSpecification_ComponentName()
	 * @model id="true" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='componentID'"
	 * @generated
	 */
	String getComponentName();

	/**
	 * Sets the value of the '{@link ccmm.ConcreteComponentSpecification#getComponentName <em>Component Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Component Name</em>' attribute.
	 * @see #getComponentName()
	 * @generated
	 */
	void setComponentName(String value);

	/**
	 * Returns the value of the '<em><b>Abstract Component ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Abstract Component ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Abstract Component ID</em>' attribute.
	 * @see #setAbstractComponentID(String)
	 * @see ccmm.CcmmPackage#getConcreteComponentSpecification_AbstractComponentID()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='abstractComponentID'"
	 * @generated
	 */
	String getAbstractComponentID();

	/**
	 * Sets the value of the '{@link ccmm.ConcreteComponentSpecification#getAbstractComponentID <em>Abstract Component ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Abstract Component ID</em>' attribute.
	 * @see #getAbstractComponentID()
	 * @generated
	 */
	void setAbstractComponentID(String value);

	/**
	 * Returns the value of the '<em><b>Component Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Component Description</em>' attribute.
	 * @see #setComponentDescription(String)
	 * @see ccmm.CcmmPackage#getConcreteComponentSpecification_ComponentDescription()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='componentDescription'"
	 * @generated
	 */
	String getComponentDescription();

	/**
	 * Sets the value of the '{@link ccmm.ConcreteComponentSpecification#getComponentDescription <em>Component Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Component Description</em>' attribute.
	 * @see #getComponentDescription()
	 * @generated
	 */
	void setComponentDescription(String value);

	/**
	 * Returns the value of the '<em><b>Functional</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Functional</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Functional</em>' containment reference.
	 * @see #setFunctional(Functional)
	 * @see ccmm.CcmmPackage#getConcreteComponentSpecification_Functional()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='functional'"
	 * @generated
	 */
	Functional getFunctional();

	/**
	 * Sets the value of the '{@link ccmm.ConcreteComponentSpecification#getFunctional <em>Functional</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Functional</em>' containment reference.
	 * @see #getFunctional()
	 * @generated
	 */
	void setFunctional(Functional value);

	/**
	 * Returns the value of the '<em><b>Extra Functional</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extra Functional</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Extra Functional</em>' containment reference.
	 * @see #setExtraFunctional(ExtraFunctional)
	 * @see ccmm.CcmmPackage#getConcreteComponentSpecification_ExtraFunctional()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='extraFunctional'"
	 * @generated
	 */
	ExtraFunctional getExtraFunctional();

	/**
	 * Sets the value of the '{@link ccmm.ConcreteComponentSpecification#getExtraFunctional <em>Extra Functional</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Extra Functional</em>' containment reference.
	 * @see #getExtraFunctional()
	 * @generated
	 */
	void setExtraFunctional(ExtraFunctional value);

	/**
	 * Returns the value of the '<em><b>Component Alias</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component Alias</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Component Alias</em>' attribute.
	 * @see #setComponentAlias(String)
	 * @see ccmm.CcmmPackage#getConcreteComponentSpecification_ComponentAlias()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 * @generated
	 */
	String getComponentAlias();

	/**
	 * Sets the value of the '{@link ccmm.ConcreteComponentSpecification#getComponentAlias <em>Component Alias</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Component Alias</em>' attribute.
	 * @see #getComponentAlias()
	 * @generated
	 */
	void setComponentAlias(String value);

	/**
	 * Returns the value of the '<em><b>Packaging</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Packaging</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Packaging</em>' containment reference.
	 * @see #setPackaging(Packaging)
	 * @see ccmm.CcmmPackage#getConcreteComponentSpecification_Packaging()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Packaging getPackaging();

	/**
	 * Sets the value of the '{@link ccmm.ConcreteComponentSpecification#getPackaging <em>Packaging</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Packaging</em>' containment reference.
	 * @see #getPackaging()
	 * @generated
	 */
	void setPackaging(Packaging value);

	/**
	 * Returns the value of the '<em><b>Marketing</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Marketing</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Marketing</em>' reference.
	 * @see #setMarketing(Marketing)
	 * @see ccmm.CcmmPackage#getConcreteComponentSpecification_Marketing()
	 * @model required="true"
	 * @generated
	 */
	Marketing getMarketing();

	/**
	 * Sets the value of the '{@link ccmm.ConcreteComponentSpecification#getMarketing <em>Marketing</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Marketing</em>' reference.
	 * @see #getMarketing()
	 * @generated
	 */
	void setMarketing(Marketing value);

} // ConcreteComponentSpecification
