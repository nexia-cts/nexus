package com.nexia.nexus.builder.mixin._bugfixes;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public class PlayerMixin {

    // Fixes MC-193343
    @ModifyExpressionValue(method = "shouldRemoveSoulSpeed", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/player/Abilities;flying:Z"))
    private boolean shouldRemoveSoulSpeedIfSpectator(boolean flying) {
        return flying || ((Player) (Object) this).isSpectator();
    }
}
