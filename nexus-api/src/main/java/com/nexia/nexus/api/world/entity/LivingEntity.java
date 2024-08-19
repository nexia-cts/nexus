package com.nexia.nexus.api.world.entity;

import com.nexia.nexus.api.world.effect.StatusEffect;
import com.nexia.nexus.api.world.effect.StatusEffectInstance;
import com.nexia.nexus.api.world.entity.equipment.EquipmentSlot;
import com.nexia.nexus.api.world.item.ItemStack;

import java.util.List;

public interface LivingEntity extends Entity {
    ItemStack getEquipmentStack(EquipmentSlot slot);
    void setEquipmentStack(EquipmentSlot slot, ItemStack itemStack);

    float getHealth();

    float getMaxHealth();

    void setHealth(float health);

    boolean isDead();

    int getInvulnerabilityTime();

    void setInvulnerabilityTime(int ticks);

    void damage(float amount);

    List<StatusEffectInstance> getActiveEffects();

    void addEffectInstance(StatusEffectInstance effectInstance);

    void removeEffect(StatusEffect statusEffect);

    void clearEffects();

    boolean isSprinting();
    void setSprinting(boolean sprinting);
    boolean isSneaking();
    void setSneaking(boolean sneaking);
    boolean isSwimming();
    void setSwimming(boolean swimming);
}
