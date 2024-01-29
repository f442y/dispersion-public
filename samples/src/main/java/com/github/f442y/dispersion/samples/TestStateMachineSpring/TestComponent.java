package com.github.f442y.dispersion.samples.TestStateMachineSpring;

import org.springframework.stereotype.Component;

@Component
public class TestComponent {
    private int count = 10;

    public String getCountAndIncrement() {
        String message = "Count is: " + count;
        count++;
        return message;
    }
}
