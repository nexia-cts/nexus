package com.nexia.nexus.builder.mixin.world.level.border;

import com.nexia.nexus.builder.extension.wrap.Wrap;
import com.nexia.nexus.builder.implementation.world.border.WrappedWorldBorder;
import net.minecraft.world.level.border.WorldBorder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldBorder.class)
public abstract class WorldBorderMixin implements Wrap<com.nexia.nexus.api.world.border.WorldBorder> {
    @Unique private WrappedWorldBorder wrapped;

    @Inject(method = "<init>*", at = @At("TAIL"))
    public void injectWrapped(CallbackInfo ci) {
        wrapped = new WrappedWorldBorder((WorldBorder) (Object) this);
    }

    @Override
    public com.nexia.nexus.api.world.border.WorldBorder wrap() {
        return wrapped;
    }
}
