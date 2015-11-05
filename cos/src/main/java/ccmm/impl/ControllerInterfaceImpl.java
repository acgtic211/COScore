/**
 */
package ccmm.impl;

import ccmm.CcmmPackage;
import ccmm.ControllerInterface;
import ccmm.ProvidedInterface;
import ccmm.RequiredInterface;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Controller Interface</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link ccmm.impl.ControllerInterfaceImpl#getProvidedInterface <em>Provided Interface</em>}</li>
 *   <li>{@link ccmm.impl.ControllerInterfaceImpl#getRequiredInterface <em>Required Interface</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ControllerInterfaceImpl extends ComponentStructureImpl implements ControllerInterface {
	/**
	 * The cached value of the '{@link #getProvidedInterface() <em>Provided Interface</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProvidedInterface()
	 * @generated
	 * @ordered
	 */
	protected EList<ProvidedInterface> providedInterface;

	/**
	 * The cached value of the '{@link #getRequiredInterface() <em>Required Interface</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRequiredInterface()
	 * @generated
	 * @ordered
	 */
	protected EList<RequiredInterface> requiredInterface;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ControllerInterfaceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CcmmPackage.Literals.CONTROLLER_INTERFACE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ProvidedInterface> getProvidedInterface() {
		if (providedInterface == null) {
			providedInterface = new EObjectContainmentEList<ProvidedInterface>(ProvidedInterface.class, this, CcmmPackage.CONTROLLER_INTERFACE__PROVIDED_INTERFACE);
		}
		return providedInterface;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<RequiredInterface> getRequiredInterface() {
		if (requiredInterface == null) {
			requiredInterface = new EObjectContainmentEList<RequiredInterface>(RequiredInterface.class, this, CcmmPackage.CONTROLLER_INTERFACE__REQUIRED_INTERFACE);
		}
		return requiredInterface;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CcmmPackage.CONTROLLER_INTERFACE__PROVIDED_INTERFACE:
				return ((InternalEList<?>)getProvidedInterface()).basicRemove(otherEnd, msgs);
			case CcmmPackage.CONTROLLER_INTERFACE__REQUIRED_INTERFACE:
				return ((InternalEList<?>)getRequiredInterface()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CcmmPackage.CONTROLLER_INTERFACE__PROVIDED_INTERFACE:
				return getProvidedInterface();
			case CcmmPackage.CONTROLLER_INTERFACE__REQUIRED_INTERFACE:
				return getRequiredInterface();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case CcmmPackage.CONTROLLER_INTERFACE__PROVIDED_INTERFACE:
				getProvidedInterface().clear();
				getProvidedInterface().addAll((Collection<? extends ProvidedInterface>)newValue);
				return;
			case CcmmPackage.CONTROLLER_INTERFACE__REQUIRED_INTERFACE:
				getRequiredInterface().clear();
				getRequiredInterface().addAll((Collection<? extends RequiredInterface>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case CcmmPackage.CONTROLLER_INTERFACE__PROVIDED_INTERFACE:
				getProvidedInterface().clear();
				return;
			case CcmmPackage.CONTROLLER_INTERFACE__REQUIRED_INTERFACE:
				getRequiredInterface().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case CcmmPackage.CONTROLLER_INTERFACE__PROVIDED_INTERFACE:
				return providedInterface != null && !providedInterface.isEmpty();
			case CcmmPackage.CONTROLLER_INTERFACE__REQUIRED_INTERFACE:
				return requiredInterface != null && !requiredInterface.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ControllerInterfaceImpl
