package com.nexia.nexus.builder.mixin._bugfixes;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Explosion.class)
public abstract class ExplosionMixin {
    @Shadow @Final private double x;
    @Shadow @Final private double y;
    @Shadow @Final private double z;

    @Redirect(method = "explode", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    private boolean shieldBlockExplosion(Entity instance, DamageSource damageSource, float f) {
        if (instance instanceof LivingEntity livingEntity) {
            if (f > 0.0f && isDamageSourceBlocked(damageSource, new Vec3(this.x, this.y, this.z), livingEntity)) {
                livingEntity.knockback(0.5f, livingEntity.getX() - this.x, livingEntity.getZ() - this.z);
                livingEntity.level.playSound(null, new BlockPos(livingEntity.position()), SoundEvents.SHIELD_BLOCK, SoundSource.PLAYERS, 1.0F, 0.8F + livingEntity.level.random.nextFloat() * 0.4F);
                livingEntity.hurtCurrentlyUsedShield(Math.min(ShieldItem.getShieldBlockDamageValue(livingEntity.getBlockingItem()), f));
                return true;
            }
        }

        return instance.hurt(damageSource, f);
    }

    @Unique
    private boolean isDamageSourceBlocked(DamageSource damageSource, Vec3 position, LivingEntity livingEntity) {
        if (!damageSource.isBypassArmor() && livingEntity.isBlocking()) {
            Vec3 vec32 = livingEntity.getViewVector(1.0f);
            if (vec32.y > -0.99 && vec32.y < 0.99) {
                vec32 = new Vec3(vec32.x, 0.0, vec32.z).normalize();
                Vec3 vec33 = position.vectorTo(livingEntity.position());
                vec33 = new Vec3(vec33.x, 0.0, vec33.z).normalize();
                return vec33.dot(vec32) * 3.1415927410125732 < -0.8726646304130554;
            }
        }
        return false;
    }
}
