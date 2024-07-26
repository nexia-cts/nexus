package com.nexia.nexus.builder.mixin.server.players;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.nexia.nexus.api.event.player.PlayerJoinEvent;
import com.nexia.nexus.api.event.player.PlayerRespawnEvent;
import com.nexia.nexus.api.world.util.Location;
import com.nexia.nexus.builder.extension.server.MinecraftServerExtension;
import com.nexia.nexus.builder.extension.server.SelectiveBorderChangeListener;
import com.nexia.nexus.builder.implementation.Wrapped;
import com.nexia.nexus.builder.implementation.util.ObjectMappings;
import com.nexia.nexus.builder.implementation.world.WrappedWorld;
import com.nexia.nexus.builder.implementation.world.entity.player.WrappedPlayer;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.border.BorderChangeListener;
import net.minecraft.world.level.border.WorldBorder;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(PlayerList.class)
public abstract class PlayerListMixin {
    @Shadow public abstract void broadcastMessage(Component component, ChatType chatType, UUID uUID);

    @Shadow @Final private MinecraftServer server;

    // BEGIN: JOIN EVENT
    @Redirect(method = "placeNewPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/players/PlayerList;broadcastMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/ChatType;Ljava/util/UUID;)V", ordinal = 0))
    public void catchJoinMessage(PlayerList playerList, Component component, ChatType chatType, UUID uUID, @Share("component") LocalRef<Component> joinMessage) {
        joinMessage.set(component);
    }

    @Inject(method = "placeNewPlayer", at = @At("TAIL"))
    public void callPlayerJoinEvent(Connection connection, ServerPlayer serverPlayer, CallbackInfo ci, @Share("component") LocalRef<Component> joinMessage) {
        PlayerJoinEvent joinEvent = new PlayerJoinEvent(Wrapped.wrap(serverPlayer, WrappedPlayer.class),
                ObjectMappings.convertComponent(joinMessage.get()));
        PlayerJoinEvent.BACKEND.invoke(joinEvent);

        if (joinEvent.getJoinMessage() != null) {
            this.broadcastMessage(ObjectMappings.convertComponent(joinEvent.getJoinMessage()), ChatType.SYSTEM, Util.NIL_UUID);
        }

        PlayerJoinEvent.BACKEND.invokeEndFunctions(joinEvent);
    }
    // END: JOIN EVENT

    // BEGIN: PlayerRespawnEvent
    @Inject(method = "respawn", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/server/MinecraftServer;getLevel(Lnet/minecraft/resources/ResourceKey;)Lnet/minecraft/server/level/ServerLevel;", shift = At.Shift.AFTER))
    public void injectRespawnEvent(ServerPlayer serverPlayer, boolean arg1, CallbackInfoReturnable<ServerPlayer> cir, @Local(ordinal = 0) LocalRef<BlockPos> blockPosRef, @Local(ordinal = 0) LocalFloatRef f, @Local(ordinal = 1) LocalBooleanRef bl2, @Local(ordinal = 0) LocalRef<ServerLevel> serverLevel, @Share("respawnEvent") LocalRef<PlayerRespawnEvent> respawnEventRef) {
        BlockPos blockPos = blockPosRef.get();
        Location respawnPoint = blockPos != null ? new Location(blockPos.getX(), blockPos.getY(), blockPos.getZ(), f.get(), 0, Wrapped.wrap(serverLevel.get(), WrappedWorld.class)) : null;
        GameType gameType = serverPlayer.gameMode.getGameModeForPlayer();
        PlayerRespawnEvent respawnEvent = new PlayerRespawnEvent(Wrapped.wrap(serverPlayer, WrappedPlayer.class), respawnPoint, bl2.get(), ObjectMappings.GAME_MODES.inverse().get(gameType));
        PlayerRespawnEvent.BACKEND.invoke(respawnEvent);
        Location respawnLoc = respawnEvent.getSpawnpoint();
        bl2.set(respawnEvent.isSpawnpointForced());
        if (respawnLoc != null) {
            blockPosRef.set(new BlockPos(respawnLoc.getX(), respawnLoc.getY(), respawnLoc.getZ()));
            f.set(respawnLoc.getYaw());
            serverLevel.set(((WrappedWorld) respawnLoc.getWorld()).unwrap());
        }
        respawnEventRef.set(respawnEvent);
    }

    @WrapOperation(method = "respawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/players/PlayerList;updatePlayerGameMode(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/server/level/ServerLevel;)V"))
    public void modifyGameMode(PlayerList instance, ServerPlayer serverPlayer, ServerPlayer serverPlayer2, ServerLevel serverLevel, Operation<Void> original, @Share("respawnEvent") LocalRef<PlayerRespawnEvent> respawnEventRef) {
        if (respawnEventRef.get() != null) {
            serverPlayer.gameMode.setGameModeForPlayer(ObjectMappings.GAME_MODES.get(respawnEventRef.get().getRespawnMode()), serverPlayer2.gameMode.getPreviousGameModeForPlayer());

            serverPlayer.gameMode.updateGameMode(serverLevel.getServer().getWorldData().getGameType());
            return;
        }
        original.call(instance, serverPlayer, serverPlayer2, serverLevel);
    }

    @Inject(method = "respawn", at = @At("TAIL"))
    public void nullifyRespawnEvent(ServerPlayer serverPlayer, boolean bl, CallbackInfoReturnable<ServerPlayer> cir, @Local(ordinal = 1) ServerPlayer serverPlayer2, @Share("respawnEvent") LocalRef<PlayerRespawnEvent> respawnEventRef) {
        if (respawnEventRef.get() != null) {
            serverPlayer2.setGameMode(ObjectMappings.GAME_MODES.get(respawnEventRef.get().getRespawnMode()));
            PlayerRespawnEvent.BACKEND.invokeEndFunctions(respawnEventRef.get());
        }
    }
    // END: PlayerRespawnEvent

    @Redirect(method = "sendLevelInfo", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;overworld()Lnet/minecraft/server/level/ServerLevel;"))
    public ServerLevel modifyServerLevel(MinecraftServer instance, ServerPlayer serverPlayer, ServerLevel serverLevel) {
        return ((MinecraftServerExtension) instance).getOverworldForLevel(serverLevel);
    }

    @Redirect(method = "setLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/border/WorldBorder;addListener(Lnet/minecraft/world/level/border/BorderChangeListener;)V"))
    public void changeBorderChangeListener(WorldBorder border, BorderChangeListener prev, ServerLevel level) {
        border.addListener(new SelectiveBorderChangeListener(((MinecraftServerExtension) this.server).getRelatedLevels(level)));
    }
}
