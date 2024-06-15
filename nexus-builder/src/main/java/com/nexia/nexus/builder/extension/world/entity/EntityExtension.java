package com.nexia.nexus.builder.extension.world.entity;

public interface EntityExtension {
    int invokeGetPermissionLevel();

    boolean injectChangeMovementStateEvent();
    void setInjectMovementStateEvent(boolean inject);
}
