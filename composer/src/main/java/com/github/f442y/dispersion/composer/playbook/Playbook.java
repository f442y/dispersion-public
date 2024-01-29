package com.github.f442y.dispersion.composer.playbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@Lazy
public class Playbook {

    private final Collection<Play<?, ?>> plays;

    @Autowired
    @Lazy
    public Playbook(List<Play<?, ?>> allPlays) {
        this.plays = allPlays;
    }

    public Collection<Play<?, ?>> plays() {
        return plays;
    }
}
