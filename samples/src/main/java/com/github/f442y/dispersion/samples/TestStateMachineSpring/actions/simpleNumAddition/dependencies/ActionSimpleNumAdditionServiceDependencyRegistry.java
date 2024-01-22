package com.github.f442y.dispersion.samples.TestStateMachineSpring.actions.simpleNumAddition.dependencies;

import com.github.f442y.dispersion.core.service.Service;
import com.github.f442y.dispersion.core.statemachine.dependency.registry.ServiceDependencyRegistry;
import com.github.f442y.dispersion.samples.TestStateMachineSpring.DummyTestServices.DummyServiceOne;
import com.github.f442y.dispersion.samples.TestStateMachineSpring.DummyTestServices.DummyServiceTwo;

import java.util.Set;

//@Component
public class ActionSimpleNumAdditionServiceDependencyRegistry extends ServiceDependencyRegistry {

    private final DummyServiceOne dummyServiceOne;
    private final DummyServiceTwo dummyServiceTwo;

    public ActionSimpleNumAdditionServiceDependencyRegistry(DummyServiceOne dummyServiceOne,
                                                            DummyServiceTwo dummyServiceTwo
    ) {
        this.dummyServiceOne = dummyServiceOne;
        this.dummyServiceTwo = dummyServiceTwo;
    }

    public DummyServiceOne dummyServiceOne() {
        return this.dummyServiceOne;
    }

    public DummyServiceTwo dummyServiceTwo() {
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
