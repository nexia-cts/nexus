package com.nexia.nexus.api.event.entity;

import com.nexia.nexus.api.event.Event;
import com.nexia.nexus.api.world.entity.Entity;

public abstract class EntityEvent extends Event {
    private final Entity entity;

    public EntityEvent(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }
}
