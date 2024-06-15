package com.nexia.nexus.builder.mixin.nbt;

import com.nexia.nexus.api.world.nbt.NBTValue;
import com.nexia.nexus.builder.extension.wrap.Wrap;
import com.nexia.nexus.builder.implementation.world.nbt.WrappedNBTValue;
import net.minecraft.nbt.StringTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StringTag.class)
public abstract class StringTagMixin implements Wrap<NBTValue> {
    private WrappedNBTValue wrapped;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void injectWrapped(String string, CallbackInfo ci) {
        this.wrapped = new WrappedNBTValue((StringTag) (Object) this);
    }

    @Override
    public NBTValue wrap() {
        return wrapped;
    }
}
