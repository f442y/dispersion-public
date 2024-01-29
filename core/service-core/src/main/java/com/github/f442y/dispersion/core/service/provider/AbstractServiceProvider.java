package com.github.f442y.dispersion.core.service.provider;

import com.github.f442y.dispersion.core.service.Service;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Autowired(required = false)
    public final void setLocalService(SERVICE localService) {
        this.localService = localService;
    }

//    public SERVICE getRemoteService() {
//        return remoteService;
//    }
}
