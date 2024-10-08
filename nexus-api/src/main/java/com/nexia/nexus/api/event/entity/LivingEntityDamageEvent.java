package com.nexia.nexus.api.event.entity;

import com.nexia.nexus.api.event.Cancellable;
import com.nexia.nexus.api.event.EventBackend;
import com.nexia.nexus.api.world.damage.DamageData;
import com.nexia.nexus.api.world.entity.LivingEntity;

public class LivingEntityDamageEvent extends LivingEntityEvent implements Cancellable {
    public static final EventBackend<LivingEntityDamageEvent> BACKEND = EventBackend.create(LivingEntityDamageEvent.class);

    private boolean cancelled;

    private DamageData cause;
    private float damage;

    public LivingEntityDamageEvent(LivingEntity entity, DamageData cause, float damage) {
        super(entity);
        this.cause = cause;
        this.damage = damage;

        this.cancelled = false;
    }

    public DamageData getCause() {
        return cause;
    }

    public void setCause(DamageData cause) {
        this.cause = cause;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }
}
