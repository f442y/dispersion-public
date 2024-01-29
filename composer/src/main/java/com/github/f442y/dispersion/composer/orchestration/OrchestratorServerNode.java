package com.github.f442y.dispersion.composer.orchestration;

import com.github.f442y.dispersion.core.orchestration.OrchestrationService;
import com.github.f442y.dispersion.core.orchestration.OrchestratorClient;
import com.github.f442y.dispersion.core.orchestration.OrchestratorServer;
import com.github.f442y.dispersion.core.orchestration.PlayBatchInputToken;
import com.github.f442y.dispersion.core.orchestration.PlayBatchOutput;
import com.github.f442y.dispersion.core.orchestration.PlayRunInput;
import com.github.f442y.dispersion.core.orchestration.PlayRunOutput;
import com.github.f442y.dispersion.core.orchestration.PlayRunOutputToken;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * Holds instances of an {@link OrchestrationService} in the system.
 * <br>
 * Local instance is {@link Autowired}
 * <br>
 * todo: Remote instances (if available) are used for distribution
 * <p>
 * knows all the orchestrator clients and what is running on which one.
 * assigned orchestrations by play runners
 * <p>
 * DORMANT, ACTIVE
 * @param <ORCHESTRATION_SERVICE>
 */
public abstract class OrchestratorServerNode<ORCHESTRATION_SERVICE extends OrchestrationService>
        implements OrchestratorServer<ORCHESTRATION_SERVICE> {
//    private Collection<ORCHESTRATION_SERVICE> orchestrationServices;

    private OrchestratorClient<ORCHESTRATION_SERVICE> localServiceClient;
//    private SERVICE remoteService;

    public Optional<OrchestratorClient<ORCHESTRATION_SERVICE>> getLocalServiceClient() {
        return Optional.ofNullable(localServiceClient);
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

    /**
     * Find service from local application if available, used in monolithic application instances where composer and
     * service instances are both in same jar.
     */
    @Autowired(required = false)
    public final void setLocalServiceClient(OrchestratorClient<ORCHESTRATION_SERVICE> localServiceClient) {
        this.localServiceClient = localServiceClient;
    }
}
