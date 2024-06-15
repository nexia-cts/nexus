package com.nexia.nexus.builder.mixin.world.level.block.entity;

import com.nexia.nexus.builder.extension.wrap.Wrap;
import com.nexia.nexus.builder.implementation.world.block.WrappedBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntity.class)
public abstract class BlockEntityMixin implements Wrap<com.nexia.nexus.api.world.block.BlockEntity> {
    @Unique private WrappedBlockEntity wrapped;

    @Inject(method = "<init>*", at = @At("TAIL"))
    public void injectWrap(CallbackInfo ci) {
        this.wrapped = new WrappedBlockEntity((BlockEntity) (Object) this);
    }

    @Override
    public com.nexia.nexus.api.world.block.BlockEntity wrap() {
        return wrapped;
    }
}
