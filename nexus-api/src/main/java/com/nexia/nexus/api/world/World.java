package com.nexia.nexus.api.world;

import com.nexia.nexus.api.NexusServer;
import com.nexia.nexus.api.util.Identifier;
import com.nexia.nexus.api.world.block.Block;
import com.nexia.nexus.api.world.block.BlockEntity;
import com.nexia.nexus.api.world.block.BlockState;
import com.nexia.nexus.api.world.border.WorldBorder;
import com.nexia.nexus.api.world.entity.Entity;
import com.nexia.nexus.api.world.entity.player.GameModeType;
import com.nexia.nexus.api.world.entity.player.Player;
import com.nexia.nexus.api.world.util.BoundingBox;
import com.nexia.nexus.api.world.util.Location;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public interface World {
    Identifier getIdentifier();

    Block getBlockAt(Location location);
    void setBlockAt(Location location, BlockState state);
    default boolean isBlockEntity(Location location) {
        return getBlockEntity(location) != null;
    }
    BlockEntity getBlockEntity(Location location);
    List<Entity> getEntities();
    List<Entity> getEntities(Predicate<Entity> filter);
    List<Player> getPlayers();

    default List<Entity> getEntities(BoundingBox area) {
        return getEntities(area, (entity) -> true);
    }
    List<Entity> getEntities(BoundingBox area, Predicate<Entity> filter);

    boolean spawn(Entity entity);

    Entity getEntity(UUID uuid);
    Player getPlayer(UUID uuid);

    NexusServer getServer();

    boolean isSubWorld();

    default boolean isRaining() {
        return getWeather() == Weather.RAIN;
    }
    default boolean isThundering() {
        return getWeather() == Weather.THUNDER;
    }

    Weather getWeather();
    void setWeather(Weather weather);
    void setWeather(Weather weather, int duration);

    WorldBorder getWorldBorder();

    long getDayTime();
    void setDayTime(long time);
    long getGameTime();
    void setGameTime(long time);

    GameModeType getDefaultGameMode();
    void setDefaultGameMode(GameModeType gameMode);
}
