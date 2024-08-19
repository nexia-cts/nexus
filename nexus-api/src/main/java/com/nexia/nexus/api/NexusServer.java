package com.nexia.nexus.api;

import com.nexia.nexus.api.command.CommandSender;
import com.nexia.nexus.api.command.CommandSourceInfo;
import com.nexia.nexus.api.util.Identifier;
import com.nexia.nexus.api.world.World;
import com.nexia.nexus.api.world.entity.player.Player;
import com.nexia.nexus.api.world.scoreboard.Scoreboard;
import com.mojang.brigadier.CommandDispatcher;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface NexusServer extends CommandSender {
    int getMaxPlayerCount();
    Collection<Player> getPlayers();
    Player getPlayer(String name);
    Player getPlayer(UUID uuid);
    Collection<World> getWorlds();
    World getWorld(Identifier identifier);
    Scoreboard getServerScoreboard();
    CommandDispatcher<CommandSourceInfo> getCommandDispatcher();
    void stopServer();

    boolean hasWorld(String name);
    void loadWorld(Path path);
    void loadWorld(Path path, String name);
    CompletableFuture<Void> loadWorldAsync(Path path);
    CompletableFuture<Void> loadWorldAsync(Path path, String name);
    void saveWorld(String name);
    void unloadWorld(String name) throws IOException;
    void unloadWorld(String name, boolean save) throws IOException;
}
