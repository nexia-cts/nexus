package com.nexia.nexus.builder.mixin.world.scores;

import com.nexia.nexus.api.world.scoreboard.ScoreboardObjective;
import com.nexia.nexus.builder.extension.world.scores.ObjectiveExtension;
import com.nexia.nexus.builder.extension.wrap.Wrap;
import com.nexia.nexus.builder.implementation.world.scoreboard.WrappedScoreboardObjective;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Objective.class)
public abstract class ObjectiveMixin implements Wrap<ScoreboardObjective>, ObjectiveExtension {
    @Shadow @Final private Scoreboard scoreboard;
    @Unique private WrappedScoreboardObjective wrapped;

    @Inject(method = "<init>*", at = @At("TAIL"))
    public void injectWrapped(CallbackInfo ci) {
        this.wrapped = new WrappedScoreboardObjective((Objective) (Object) this);
    }

    @Override
    public ScoreboardObjective wrap() {
        return wrapped;
    }

    @Override
    public Scoreboard getScoreboardServer() {
        return this.scoreboard;
    }
}
