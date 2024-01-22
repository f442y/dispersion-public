package com.github.f442y.dispersion.core.service.provider;

import com.github.f442y.dispersion.core.service.Service;
import jakarta.annotation.Nullable;
import jakarta.inject.Inject;

import java.util.Optional;

public abstract class AbstractServiceProvider<SERVICE extends Service> {
    private SERVICE localService;
    private SERVICE remoteService;

    public Optional<SERVICE> getService() {
        if (getLocalService().isPresent()) {
            return getLocalService();
        }
        if (getRemoteService().isPresent()) {
            return getRemoteService();
        }
        return Optional.empty();

    }

    public Optional<SERVICE> getLocalService() {
        return Optional.ofNullable(localService);
    }

    public Optional<SERVICE> getRemoteService() {
        return Optional.ofNullable(remoteService);
    }

    @Inject
    public final void setLocalService(@Nullable SERVICE localService) {
        this.localService = localService;
    }

//    public SERVICE getRemoteService() {
//        return remoteService;
//    }
}
