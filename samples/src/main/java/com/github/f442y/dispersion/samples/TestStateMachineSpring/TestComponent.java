package com.github.f442y.dispersion.samples.TestStateMachineSpring;

import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Named
@Singleton
public class TestComponent {
    private int count = 10;

    public String getCountAndIncrement() {
        String message = "Count is: " + count;
        count++;
        return message;
    }
}
