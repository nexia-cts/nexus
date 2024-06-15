package com.nexia.nexus.builder.implementation.world.entity.projectile;

import com.nexia.nexus.api.world.entity.Entity;
import com.nexia.nexus.api.world.entity.projectile.Projectile;
import com.nexia.nexus.builder.implementation.Wrapped;
import com.nexia.nexus.builder.implementation.world.entity.WrappedEntity;

public class WrappedProjectile extends WrappedEntity implements Projectile {
    public WrappedProjectile(net.minecraft.world.entity.projectile.Projectile wrappedProjectile) {
        super(wrappedProjectile);
    }

    @Override
    public net.minecraft.world.entity.projectile.Projectile unwrap() {
        return wrappedProjectile();
    }

    @Override
    public Entity getOwner() {
        return Wrapped.wrap(wrappedProjectile().getOwner(), WrappedEntity.class);
    }

    private net.minecraft.world.entity.projectile.Projectile wrappedProjectile() {
        return (net.minecraft.world.entity.projectile.Projectile) this.wrapped;
    }
}
