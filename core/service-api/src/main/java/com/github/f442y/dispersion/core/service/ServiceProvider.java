package com.github.f442y.dispersion.core.service;

public interface ServiceProvider<SERVICE> {
    SERVICE localService();

    SERVICE remoteService();
}
