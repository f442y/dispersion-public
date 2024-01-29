package com.github.f442y.dispersion.core.statemachine.TestStateMachine.actions.simpleNumAddition.dependencies;

import com.github.f442y.dispersion.core.service.Service;

public class DummyTestServices {
    private DummyTestServices() {/*no-op*/}

    public static class DummyServiceOne implements Service {}

    public static class DummyServiceTwo implements Service {}
}
