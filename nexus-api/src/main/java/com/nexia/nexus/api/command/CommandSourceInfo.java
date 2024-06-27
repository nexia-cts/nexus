package com.nexia.nexus.api.command;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.nexia.nexus.api.NexusServer;
import com.nexia.nexus.api.world.entity.Entity;
import com.nexia.nexus.api.world.entity.player.Player;
import com.nexia.nexus.api.world.util.Location;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;

public interface CommandSourceInfo {
    CommandSender getSender();

    @Nullable Entity getExecutingEntity();

    Entity getExecutingEntityOrException() throws CommandSyntaxException;

    Player getPlayerOrException() throws CommandSyntaxException;

    Location getLocation();

    NexusServer getServer();

    void sendMessage(Component message);

    static Builder builder() {
        return new Builder();
    }

    class Builder {
        private CommandSender source;
        @Nullable private Entity executingEntity;
        private Location location;
        private NexusServer server;

        Builder() {}

        public Builder source(CommandSender source) {
            this.source = source;
            return this;
        }

        public Builder executingEntity(Entity entity) {
            this.executingEntity = entity;
            return this;
        }

        public Builder location(Location location) {
            this.location = location;
            return this;
        }

        public Builder server(NexusServer server) {
            this.server = server;
            return this;
        }

        public CommandSourceInfo build() {
            return com.nexia.nexus.api.builder.Builder.getInstance().createCommandSourceInfo(source, executingEntity, location, server);
        }
    }
}
