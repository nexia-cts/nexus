package com.nexia.nexus.builder.mixin._bugfixes;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
@SuppressWarnings({"UnusedMethod", "UnusedVariable"})
public abstract class PlayerMixin extends LivingEntity {

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    // Fixes MC-193343
    @ModifyExpressionValue(method = "shouldRemoveSoulSpeed", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/player/Abilities;flying:Z"))
    private boolean shouldRemoveSoulSpeedIfSpectator(boolean flying) {
        return flying || this.isSpectator();
    }


    @Inject(method = "disableShield", at = @At("TAIL"))
    private void playShieldBreakSound(float f, CallbackInfoReturnable<Boolean> cir) {
        this.level.playSound(null, new BlockPos(this.position()), SoundEvents.SHIELD_BREAK, SoundSource.PLAYERS, 0.8F, 0.8F + this.level.random.nextFloat() * 0.4F);
    }
}
