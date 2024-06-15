package com.nexia.nexus.builder.implementation.world.damage;

import com.nexia.nexus.api.world.damage.DamageData;
import com.nexia.nexus.api.world.entity.Entity;
import com.nexia.nexus.builder.implementation.Wrapped;
import com.nexia.nexus.builder.implementation.util.ObjectMappings;
import com.nexia.nexus.builder.implementation.world.entity.WrappedEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;

public class WrappedDamageData extends Wrapped<DamageSource> implements DamageData {
    private Type type;

    public WrappedDamageData(DamageSource wrapped) {
        super(wrapped);
        type = null;
    }

    @Override
    public DamageSource unwrap() {
        return wrapped;
    }

    @Override
    public Type getType() {
        if (type == null) {
            if (ObjectMappings.DAMAGE_TYPES.inverse().containsKey(wrapped)) {
                type = ObjectMappings.DAMAGE_TYPES.inverse().get(wrapped);
            }
            else {
                if (wrapped.isExplosion()) {
                    type = Type.EXPLOSION;
                }
                else if (wrapped instanceof IndirectEntityDamageSource) {
                    type = Type.PROJECTILE;
                }
                else if (wrapped instanceof EntityDamageSource) {
                    type = Type.ENTITY_ATTACK;
                }
                else type = Type.GENERIC;
            }
        }
        return type;
    }

    @Override
    public Entity getDamagingEntity() {
        return Wrapped.wrap(wrapped.getEntity(), WrappedEntity.class);
    }
}
