package com.nexia.nexus.builder.implementation.world.entity;

import com.nexia.nexus.api.world.entity.LivingEntity;
import com.nexia.nexus.api.world.entity.Mob;
import com.nexia.nexus.builder.implementation.Wrapped;

public class WrappedMob extends WrappedLivingEntity implements Mob {
    public WrappedMob(net.minecraft.world.entity.Mob wrappedMob) {
        super(wrappedMob);
    }

    @Override
    public LivingEntity getTargetEntity() {
        return Wrapped.wrap(wrappedMob().getTarget(), WrappedLivingEntity.class);
    }

    @Override
    public void setTargetEntity(LivingEntity target) {
        wrappedMob().setTarget(((WrappedLivingEntity) target).unwrap());
    }

    @Override
    public net.minecraft.world.entity.Mob unwrap() {
        return this.wrappedMob();
    }

    private net.minecraft.world.entity.Mob wrappedMob() {
        return (net.minecraft.world.entity.Mob) this.wrapped;
    }
}
