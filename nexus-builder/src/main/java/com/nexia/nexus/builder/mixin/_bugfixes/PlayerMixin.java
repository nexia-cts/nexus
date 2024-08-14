package com.nexia.nexus.builder.mixin._bugfixes;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
@SuppressWarnings("UnusedMethod")
public abstract class PlayerMixin extends LivingEntity {
    @Shadow public abstract ItemCooldowns getCooldowns();

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    // Fixes MC-193343
    @ModifyExpressionValue(method = "shouldRemoveSoulSpeed", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/player/Abilities;flying:Z"))
    private boolean shouldRemoveSoulSpeedIfSpectator(boolean flying) {
        return flying || ((Player) (Object) this).isSpectator();
    }

    /**
     * @author NotCoded
     * @reason Make shield break actually play the sound to other players.
     */
    @Overwrite
    public boolean disableShield(float f) {
        this.getCooldowns().addCooldown(Items.SHIELD, (int)(f * 20.0F));
        this.stopUsingItem();
        this.level.broadcastEntityEvent(this, (byte)30);
        if (this.getLastDamageSource() != null && this.getLastDamageSource().getEntity() instanceof Player attacker) {
            SoundSource soundSource = null;
            for (SoundSource source : SoundSource.values()) {
                soundSource = source;
            }
            this.level.playSound(null, new BlockPos(attacker.position()), SoundEvents.SHIELD_BREAK, soundSource, 2f, 1f);
        }
        return true;
    }}
