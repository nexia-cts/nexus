package com.nexia.nexus.builder.mixin.world;

import com.nexia.nexus.api.world.item.container.menu.MenuHolder;
import com.nexia.nexus.builder.extension.wrap.Wrap;
import com.nexia.nexus.builder.implementation.world.item.container.menu.WrappedMenuHolder;
import net.minecraft.world.SimpleMenuProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SimpleMenuProvider.class)
public abstract class SimpleMenuProviderMixin implements Wrap<MenuHolder> {
    WrappedMenuHolder wrapped;

    @Inject(method = "<init>*", at = @At("TAIL"))
    public void injectWrapped(CallbackInfo ci) {
        wrapped = new WrappedMenuHolder((SimpleMenuProvider) (Object) this);
    }

    @Override
    public MenuHolder wrap() {
        return wrapped;
    }
}
