package com.nexia.nexus.builder.mixin.world.scores;

import com.nexia.nexus.builder.extension.wrap.Wrap;
import com.nexia.nexus.builder.implementation.world.scoreboard.WrappedScoreboard;
import net.minecraft.world.scores.Scoreboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Scoreboard.class)
public abstract class ScoreboardMixin implements Wrap<com.nexia.nexus.api.world.scoreboard.Scoreboard> {
    @Unique private WrappedScoreboard wrapped;

    @Inject(method = "<init>*", at = @At("TAIL"))
    public void injectWrapped(CallbackInfo ci) {
        this.wrapped = new WrappedScoreboard((Scoreboard) (Object) this);
    }

    @Override
    public com.nexia.nexus.api.world.scoreboard.Scoreboard wrap() {
        return wrapped;
    }
}
