package com.nexia.nexus.api.world.entity;

public interface Mob extends LivingEntity {
    LivingEntity getTargetEntity();
    void setTargetEntity(LivingEntity target);
}
