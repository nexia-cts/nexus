package com.nexia.nexus.builder.implementation.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.nexia.nexus.api.NexusServer;
import com.nexia.nexus.api.command.CommandSender;
import com.nexia.nexus.api.command.CommandSourceInfo;
import com.nexia.nexus.api.world.entity.Entity;
import com.nexia.nexus.api.world.entity.player.Player;
import com.nexia.nexus.api.world.scoreboard.ScoreboardTeam;
import com.nexia.nexus.api.world.util.Location;
import com.nexia.nexus.builder.implementation.WrappedNexusServer;
import net.kyori.adventure.text.Component;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandSourceInfoImpl implements CommandSourceInfo, SharedSuggestionProvider {
    public static final SimpleCommandExceptionType ERROR_NOT_PLAYER = new SimpleCommandExceptionType(new TranslatableComponent("permissions.requires.player"));
    public static final SimpleCommandExceptionType ERROR_NOT_ENTITY = new SimpleCommandExceptionType(new TranslatableComponent("permissions.requires.entity"));
    
    private final CommandSender sender;
    @Nullable
    private final Entity executingEntity;
    private final Location location;
    private final NexusServer server;

    public CommandSourceInfoImpl(CommandSender sender, @Nullable Entity executingEntity, Location location, NexusServer server) {
        this.sender = sender;
        this.executingEntity = executingEntity;
        this.location = location;
        this.server = server;
    }

    @Override
    public CommandSender getSender() {
        return sender;
    }

    @Override
    public @Nullable Entity getExecutingEntity() {
        return executingEntity;
    }

    @Override
    public Entity getExecutingEntityOrException() throws CommandSyntaxException {
        if (this.executingEntity == null) {
            throw ERROR_NOT_ENTITY.create();
        } else {
            return this.executingEntity;
        }
    }

    @Override
    public Player getPlayerOrException() throws CommandSyntaxException {
        if (!(this.executingEntity instanceof Player)) {
            throw ERROR_NOT_PLAYER.create();
        } else {
            return (Player) this.executingEntity;
        }
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public NexusServer getServer() {
        return server;
    }

    @Override
    public void sendMessage(Component message) {
        sender.sendMessage(message);
    }

    @Override
    public Collection<String> getOnlinePlayerNames() {
        return this.server.getPlayers().stream()
                .map(Player::getRawName)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<String> getAllTeams() {
        return this.server.getServerScoreboard().getAllTeams().stream()
                .map(ScoreboardTeam::getName)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<ResourceLocation> getAvailableSoundEvents() {
        return Registry.SOUND_EVENT.keySet();
    }

    @Override
    public Stream<ResourceLocation> getRecipeNames() {
        return ((WrappedNexusServer) this.server).unwrap().getRecipeManager().getRecipeIds();
    }

    @Override
    public CompletableFuture<Suggestions> customSuggestion(CommandContext<SharedSuggestionProvider> commandContext, SuggestionsBuilder suggestionsBuilder) {
        return null;
    }

    @Override
    public Set<ResourceKey<Level>> levels() {
        return ((WrappedNexusServer) this.server).unwrap().levelKeys();
    }

    @Override
    public RegistryAccess registryAccess() {
        return ((WrappedNexusServer) this.server).unwrap().registryAccess();
    }

    @Override
    public boolean hasPermission(int i) {
        return this.sender.getPermissionLevel() >= i;
    }
}
