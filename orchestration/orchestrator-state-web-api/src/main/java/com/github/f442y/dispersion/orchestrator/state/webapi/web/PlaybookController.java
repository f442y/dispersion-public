package com.github.f442y.dispersion.orchestrator.state.webapi.web;

import com.github.f442y.dispersion.orchestrator.state.webapi.model.PlayTaskViewer;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
public class PlaybookController {
    private static final Logger log = LoggerFactory.getLogger(PlaybookController.class);
    private final PlayTaskViewer playTaskViewer;

    @Inject
    public PlaybookController(PlayTaskViewer playTaskViewer) {
        this.playTaskViewer = playTaskViewer;
    }

    @GetMapping("/playtasks")
    public PlayTaskViewer.PlayTasksView getPlayTasks() {
        var playTaskViews = playTaskViewer.getAllPlayTasks();
        if (playTaskViews.playTaskIds().isEmpty()) {
            log.error("not found");
            throw new ResponseStatusException(NOT_FOUND, "Unable to find resource");
        }
        return playTaskViews;
    }

    @GetMapping("/playtasks/{playTaskName}")
    public PlayTaskViewer.PlayTaskView getPlayTask(@PathVariable String playTaskName) {
        var playTaskView = playTaskViewer.getPlayTask(playTaskName);
        if (playTaskView == null) {
            log.error("not found");
            throw new ResponseStatusException(NOT_FOUND, "Unable to find resource");
        }
        return playTaskView;
    }
}
