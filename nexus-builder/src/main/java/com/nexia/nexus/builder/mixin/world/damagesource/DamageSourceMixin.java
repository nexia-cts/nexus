package com.nexia.nexus.builder.mixin.world.damagesource;

import com.nexia.nexus.api.world.damage.DamageData;
import com.nexia.nexus.builder.extension.wrap.Wrap;
import com.nexia.nexus.builder.implementation.util.ObjectMappings;
import com.nexia.nexus.builder.implementation.world.damage.WrappedDamageData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.level.Explosion;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DamageSource.class)
public abstract class DamageSourceMixin implements Wrap<DamageData> {
    @Unique private DamageData wrapped;

    @SuppressWarnings("unused")
    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void loadConversionTable(@SuppressWarnings("unused") CallbackInfo ci) {
        ObjectMappings.setupDamageTypes();
    }

    @Inject(method = "<init>*", at = @At("TAIL"))
    public void injectWrap(CallbackInfo ci) {
        this.wrapped = new WrappedDamageData((DamageSource) (Object) this);
    }

    @Override
    public DamageData wrap() {
        return wrapped;
    }

    /**
     * @author NotInfinityy
     * @reason Fix shields not being able to block explosions due to getting the wrong source position
     */
    @Overwrite
    public static DamageSource explosion(@Nullable Explosion explosion) {
        assert explosion != null;
        return new EntityDamageSource("explosion", explosion.source).setScalesWithDifficulty().setExplosion();
    }
}
