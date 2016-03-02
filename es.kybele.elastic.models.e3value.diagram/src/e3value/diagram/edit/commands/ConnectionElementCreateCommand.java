package e3value.diagram.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;

import e3value.ConnectionElement;
import e3value.DependencyElement;
import e3value.E3ValueModel;
import e3value.E3valueFactory;
import e3value.diagram.edit.policies.E3valueBaseItemSemanticEditPolicy;

/**
 * @generated
 */
public class ConnectionElementCreateCommand extends EditElementCommand {

	/**
	 * @generated
	 */
	private final EObject source;

	/**
	 * @generated
	 */
	private final EObject target;

	/**
	 * @generated
	 */
	private final E3ValueModel container;

	/**
	 * @generated
	 */
	public ConnectionElementCreateCommand(CreateRelationshipRequest request,
			EObject source, EObject target) {
		super(request.getLabel(), null, request);
		this.source = source;
		this.target = target;
		container = deduceContainer(source, target);
	}

	/**
	 * @generated
	 */
	public boolean canExecute() {
		if (source == null && target == null) {
			return false;
		}
		if (source != null && false == source instanceof DependencyElement) {
			return false;
		}
		if (target != null && false == target instanceof DependencyElement) {
			return false;
		}
		if (getSource() == null) {
			return true; 
		}
		if (getContainer() == null) {
			return false;
		}
		return E3valueBaseItemSemanticEditPolicy.getLinkConstraints()
				.canCreateConnectionElement_4001(getContainer(), getSource(),
						getTarget());
	}

	/**
	 * @generated
	 */
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
			IAdaptable info) throws ExecutionException {
		if (!canExecute()) {
			throw new ExecutionException(
					"Invalid arguments in create link command"); //$NON-NLS-1$
		}

		ConnectionElement newElement = E3valueFactory.eINSTANCE
				.createConnectionElement();
		getContainer().getHasDependenceElements().add(newElement);
		newElement.setSourceDependenceElement(getSource());
		newElement.setTargetDependenceElement(getTarget());
		doConfigure(newElement, monitor, info);
		((CreateElementRequest) getRequest()).setNewElement(newElement);
		return CommandResult.newOKCommandResult(newElement);

	}

	/**
	 * @generated
	 */
	protected void doConfigure(ConnectionElement newElement,
			IProgressMonitor monitor, IAdaptable info)
			throws ExecutionException {
		IElementType elementType = ((CreateElementRequest) getRequest())
				.getElementType();
		ConfigureRequest configureRequest = new ConfigureRequest(
				getEditingDomain(), newElement, elementType);
		configureRequest.setClientContext(((CreateElementRequest) getRequest())
				.getClientContext());
		configureRequest.addParameters(getRequest().getParameters());
		configureRequest.setParameter(CreateRelationshipRequest.SOURCE,
				getSource());
		configureRequest.setParameter(CreateRelationshipRequest.TARGET,
				getTarget());
		ICommand configureCommand = elementType
				.getEditCommand(configureRequest);
		if (configureCommand != null && configureCommand.canExecute()) {
			configureCommand.execute(monitor, info);
		}
	}

	/**
	 * @generated
	 */
	protected void setElementToEdit(EObject element) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @generated
	 */
	protected DependencyElement getSource() {
		return (DependencyElement) source;
	}

	/**
	 * @generated
	 */
	protected DependencyElement getTarget() {
		return (DependencyElement) target;
	}

	/**
	 * @generated
	 */
	public E3ValueModel getContainer() {
		return container;
	}

	/**
	 * Default approach is to traverse ancestors of the source to find instance of container.
	 * Modify with appropriate logic.
	 * @generated
	 */
	private static E3ValueModel deduceContainer(EObject source, EObject target) {
		for (EObject element = source; element != null; element = element
				.eContainer()) {
			if (element instanceof E3ValueModel) {
				return (E3ValueModel) element;
			}
		}
		return null;
	}

}
