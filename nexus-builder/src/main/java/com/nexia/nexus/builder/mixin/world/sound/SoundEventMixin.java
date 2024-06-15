package com.nexia.nexus.builder.mixin.world.sound;

import com.nexia.nexus.builder.implementation.util.ObjectMappings;
import net.minecraft.sounds.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoundEvent.class)
public class SoundEventMixin {
    @Inject(method = "<clinit>", at = @At("TAIL")) @SuppressWarnings("unused")
    private static void loadConversionTable(CallbackInfo ci) {
        ObjectMappings.setUpSoundTypes();
    }
}
