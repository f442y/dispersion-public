package com.github.f442y.dispersion.core.statemachine.TestStateMachine.actions.simpleNumAddition.dependencies;

import com.github.f442y.dispersion.core.service.Service;
import com.github.f442y.dispersion.core.statemachine.dependency.registry.ServiceDependencyRegistry;

import java.util.Set;

//@Component
public class ActionSimpleNumAdditionServiceDependencyRegistry extends ServiceDependencyRegistry {

    private final DummyTestServices.DummyServiceOne dummyServiceOne;
    private final DummyTestServices.DummyServiceTwo dummyServiceTwo;

    public ActionSimpleNumAdditionServiceDependencyRegistry(DummyTestServices.DummyServiceOne dummyServiceOne,
                                                            DummyTestServices.DummyServiceTwo dummyServiceTwo
    ) {
        this.dummyServiceOne = dummyServiceOne;
        this.dummyServiceTwo = dummyServiceTwo;
    }

    public DummyTestServices.DummyServiceOne dummyServiceOne() {
        return this.dummyServiceOne;
    }

    public DummyTestServices.DummyServiceTwo dummyServiceTwo() {
        return dummyServiceTwo;
    }

    @Override
    public Set<Service> allServiceDependencies() {
        return Set.of(dummyServiceOne, dummyServiceTwo);
    }

    @Override
    public void validateRegistryObject() {
        //check all services are available
    }
}
