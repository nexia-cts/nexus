package com.nexia.nexus.api.event.entity;

import com.nexia.nexus.api.world.entity.LivingEntity;

public abstract class LivingEntityEvent extends EntityEvent {
    private final LivingEntity entity;

    public LivingEntityEvent(LivingEntity entity) {
        super(entity);
        this.entity = entity;
    }

    public LivingEntity getLivingEntity() {
        return entity;
    }
}
