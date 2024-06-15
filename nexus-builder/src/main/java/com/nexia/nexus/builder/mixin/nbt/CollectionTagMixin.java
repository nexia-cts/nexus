package com.nexia.nexus.builder.mixin.nbt;

import com.nexia.nexus.api.world.nbt.NBTValue;
import com.nexia.nexus.builder.extension.wrap.Wrap;
import com.nexia.nexus.builder.implementation.world.nbt.WrappedNBTValue;
import net.minecraft.nbt.CollectionTag;
import net.minecraft.nbt.ListTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CollectionTag.class)
public abstract class
CollectionTagMixin implements Wrap<NBTValue> {
    private WrappedNBTValue wrapped = null;

    @SuppressWarnings("ConstantConditions")
    @Inject(method = "<init>", at = @At("TAIL"))
    public void injectWrapped(CallbackInfo ci) {
        CollectionTag<?> instance = (CollectionTag<?>) (Object) this;
        if (!(instance instanceof ListTag)) {
            wrapped = new WrappedNBTValue(instance);
        }
    }

    @Override
    public NBTValue wrap() {
        return wrapped;
    }
}
