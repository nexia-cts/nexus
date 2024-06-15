package com.nexia.nexus.builder.extension.world.entity;

import com.nexia.nexus.api.event.entity.LivingEntityDeathEvent;

public interface LivingEntityExtension {
    boolean willDropItems();

    void setDeathEvent(LivingEntityDeathEvent event);
    LivingEntityDeathEvent getDeathEvent();
}
