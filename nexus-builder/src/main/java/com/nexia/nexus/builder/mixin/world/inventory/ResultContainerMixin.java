package com.nexia.nexus.builder.mixin.world.inventory;

import com.nexia.nexus.api.world.item.container.Container;
import com.nexia.nexus.builder.extension.wrap.Wrap;
import com.nexia.nexus.builder.implementation.world.item.container.WrappedGenericContainer;
import net.minecraft.world.inventory.ResultContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ResultContainer.class)
public abstract class ResultContainerMixin implements Wrap<Container> {
    WrappedGenericContainer wrapped;

    @Inject(method = "<init>*", at = @At("TAIL"))
    public void injectWrapped(CallbackInfo ci) {
        this.wrapped = new WrappedGenericContainer((ResultContainer) (Object) this);
    }

    @Override
    public Container wrap() {
        return wrapped;
    }
}
