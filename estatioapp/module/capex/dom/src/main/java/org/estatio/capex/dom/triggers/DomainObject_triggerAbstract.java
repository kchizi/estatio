package org.estatio.capex.dom.triggers;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.services.registry.ServiceRegistry2;

import org.estatio.capex.dom.state.State;
import org.estatio.capex.dom.state.StateTransition;
import org.estatio.capex.dom.state.StateTransitionService;
import org.estatio.capex.dom.state.StateTransitionType;
import org.estatio.dom.party.Person;

/**
 * Subclasses should be annotated using: @Mixin(method = "act")
 */
public abstract class DomainObject_triggerAbstract<
        DO,
        ST extends StateTransition<DO, ST, STT, S>,
        STT extends StateTransitionType<DO, ST, STT, S>,
        S extends State<S>
        > {

    protected final DO domainObject;
    protected final Class<ST> stateTransitionClass;
    protected final List<S> fromStates;
    private final STT requiredTransitionTypeIfAny;

    protected DomainObject_triggerAbstract(
            final DO domainObject,
            final Class<ST> stateTransitionClass,
            final List<S> fromStates,
            final STT requiredTransitionTypeIfAny) {
        this.domainObject = domainObject;
        this.stateTransitionClass = stateTransitionClass;
        this.fromStates = fromStates;
        this.requiredTransitionTypeIfAny = requiredTransitionTypeIfAny;
    }

    protected DomainObject_triggerAbstract(
            final DO domainObject,
            final Class<ST> stateTransitionClass,
            final STT requiredTransitionType) {
        this.domainObject = domainObject;
        this.stateTransitionClass = stateTransitionClass;
        this.fromStates = requiredTransitionType.getFromStates();
        this.requiredTransitionTypeIfAny = requiredTransitionType;
    }


    public DO getDomainObject() {
        return domainObject;
    }


    /**
     * Subclasses should call, to request that the state transition occur (or at least, be attempted).
     *
     * <p>
     *     It's possible that the transition may not occur if there
     *     is {@link StateTransitionType#isGuardSatisified(Object, ServiceRegistry2) guard} that is not yet satisfied
     *     for the particular domain object.
     * </p>
     *
     * @return - the {@link StateTransition} most recently completed for the domain object.
     */
    protected final ST trigger(final String comment) {
        return stateTransitionService.trigger(getDomainObject(), stateTransitionClass, requiredTransitionTypeIfAny, comment);
    }

    protected final ST trigger(final Person personToAssignTo, final String comment) {
        return stateTransitionService.trigger(getDomainObject(), stateTransitionClass, requiredTransitionTypeIfAny, personToAssignTo, comment);
    }

    /**
     * Subclasses must call, typically in their <tt>hideAct()</tt> guargs, in order to check whether {@link #trigger(String)}.
     */
    protected final boolean cannotTransition() {
        return !canTransition();
    }

    private boolean canTransition() {
        final S currentState = stateTransitionService.currentStateOf(getDomainObject(), stateTransitionClass);
        return fromStates.contains(currentState);
    }

    @Inject
    protected StateTransitionService stateTransitionService;

    @Inject
    protected ServiceRegistry2 serviceRegistry2;

}
