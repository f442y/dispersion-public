package com.github.f442y.dispersion.composer.playbook;

import com.github.f442y.dispersion.composer.model.Play;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.Collection;
import java.util.List;

@Named
public class Playbook {

    private final Collection<Play<?, ?, ?>> plays;

    @Inject
    public Playbook(List<Play<?, ?, ?>> allPlays) {
        this.plays = allPlays;
    }

    public Collection<Play<?, ?, ?>> plays() {
        return plays;
    }
}
