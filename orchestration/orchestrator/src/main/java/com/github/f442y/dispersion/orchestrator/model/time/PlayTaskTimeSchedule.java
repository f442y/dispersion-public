package com.github.f442y.dispersion.orchestrator.model.time;

import com.github.f442y.dispersion.orchestrator.model.PlayTask;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "PLAY_TASK_TIME_SCHEDULE")
public class PlayTaskTimeSchedule {

    @Id
    @Column(name = "ID")
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PLAY_TASK_TO_RUN_ID")
    private PlayTask playTaskToRun;

    public PlayTaskTimeSchedule() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PlayTask getPlayTaskToRun() {
        return playTaskToRun;
    }

    public void setPlayTaskToRun(PlayTask playTaskToRun) {
        this.playTaskToRun = playTaskToRun;
    }
}
