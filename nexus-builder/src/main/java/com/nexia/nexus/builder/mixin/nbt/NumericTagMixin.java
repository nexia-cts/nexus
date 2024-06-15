package com.nexia.nexus.builder.mixin.nbt;

import com.nexia.nexus.api.world.nbt.NBTValue;
import com.nexia.nexus.builder.extension.wrap.Wrap;
import com.nexia.nexus.builder.implementation.world.nbt.WrappedNBTValue;
import net.minecraft.nbt.NumericTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NumericTag.class)
public abstract class NumericTagMixin implements Wrap<NBTValue> {
    @Unique
    private WrappedNBTValue wrapped;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void injectWrapped(CallbackInfo ci) {
        this.wrapped = new WrappedNBTValue((NumericTag) (Object) this);
    }

    @Override
    public NBTValue wrap() {
        return wrapped;
    }
}
