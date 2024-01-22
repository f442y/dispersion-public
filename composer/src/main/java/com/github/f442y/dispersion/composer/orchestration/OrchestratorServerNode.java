package com.github.f442y.dispersion.composer.orchestration;

import com.github.f442y.dispersion.core.orchestration.OrchestrationService;
import com.github.f442y.dispersion.core.orchestration.OrchestratorClientAPI;
import com.github.f442y.dispersion.core.orchestration.OrchestratorServerAPI;
import com.github.f442y.dispersion.core.orchestration.PlayBatchInputToken;
import com.github.f442y.dispersion.core.orchestration.PlayBatchOutput;
import com.github.f442y.dispersion.core.orchestration.PlayRunInput;
import com.github.f442y.dispersion.core.orchestration.PlayRunOutput;
import com.github.f442y.dispersion.core.orchestration.PlayRunOutputToken;
import jakarta.annotation.Nullable;
import jakarta.inject.Inject;

import java.util.Optional;

/**
 * Holds instances of an {@link OrchestrationService} in the system.
 * <br>
 * Local instance is {@link Inject}ed
 * <br>
 * todo: Remote instances (if available) are used for distribution
 * <p>
 * knows all the orchestrator clients and what is running on which one.
 * assigned orchestrations by play runners
 * <p>
 * DORMANT, ACTIVE
 *
 * @param <ORCHESTRATION_SERVICE>
 */
public abstract class OrchestratorServerNode<ORCHESTRATION_SERVICE extends OrchestrationService>
        implements OrchestratorServerAPI<ORCHESTRATION_SERVICE> {
//    private Collection<ORCHESTRATION_SERVICE> orchestrationServices;

    private OrchestratorClientAPI<ORCHESTRATION_SERVICE> localServiceClient;
//    private SERVICE remoteService;

    public Optional<OrchestratorClientAPI<ORCHESTRATION_SERVICE>> getLocalServiceClient() {
        return Optional.ofNullable(localServiceClient);
    }

    /**
     * Find service from local application if available, used in monolithic application instances where composer and
     * service instances are both in same jar.
     */
    @Inject
    public final void setLocalServiceClient(@Nullable OrchestratorClientAPI<ORCHESTRATION_SERVICE> localServiceClient) {
        this.localServiceClient = localServiceClient;
    }

    @Override
    public final PlayRunOutput distributeToClients(PlayRunInput playRunInput) {
        // spread play run into play batches to distribute to client nodes

        // for now add all to local service client
        if (this.getLocalServiceClient().isPresent()) {
            PlayBatchOutput playBatchOutput =
                    this.getLocalServiceClient().get().addTasksToClientNode(new PlayBatchInputToken());
        }
        return new PlayRunOutputToken();
    }
}
