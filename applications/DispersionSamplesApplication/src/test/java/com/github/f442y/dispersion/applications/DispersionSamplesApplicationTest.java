package com.github.f442y.dispersion.applications;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class DispersionSamplesApplicationTest {
    @Test
    void contextLoads() {
    }

    @Test
    public void mainTest() {
        assertThrows(IllegalArgumentException.class, () -> DispersionSamplesApplication.main(null));
    }
}