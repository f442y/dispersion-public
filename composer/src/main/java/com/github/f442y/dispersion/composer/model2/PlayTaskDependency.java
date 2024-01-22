package com.github.f442y.dispersion.composer.model2;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "PLAY_TASK_DEPENDENCY")
public class PlayTaskDependency {
    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PLAY_TASK_TO_RUN_ID")
    private PlayTask<?, ?> playTaskToRun;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PLAY_TASK_SOURCE_ID")
    private PlayTask<?, ?> playTaskSource;

    public PlayTaskDependency() {}

    public PlayTaskDependency(String id, PlayTask<?, ?> playTaskToRun, PlayTask<?, ?> playTaskSource) {
        this.id = id;
        this.playTaskToRun = playTaskToRun;
        this.playTaskSource = playTaskSource;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PlayTask<?, ?> getPlayTaskToRun() {
        return playTaskToRun;
    }

    public void setPlayTaskToRun(PlayTask<?, ?> playTaskToRun) {
        this.playTaskToRun = playTaskToRun;
    }

    public PlayTask<?, ?> getPlayTaskSource() {
        return playTaskSource;
    }

    public void setPlayTaskSource(PlayTask<?, ?> playTaskSource) {
        this.playTaskSource = playTaskSource;
    }
}
