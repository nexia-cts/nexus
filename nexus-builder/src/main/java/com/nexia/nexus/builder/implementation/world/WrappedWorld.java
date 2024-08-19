package com.nexia.nexus.builder.implementation.world;

import com.nexia.nexus.api.NexusServer;
import com.nexia.nexus.api.util.Identifier;
import com.nexia.nexus.api.world.Weather;
import com.nexia.nexus.api.world.World;
import com.nexia.nexus.api.world.block.Block;
import com.nexia.nexus.api.world.block.BlockEntity;
import com.nexia.nexus.api.world.block.BlockState;
import com.nexia.nexus.api.world.border.WorldBorder;
import com.nexia.nexus.api.world.entity.Entity;
import com.nexia.nexus.api.world.entity.player.GameModeType;
import com.nexia.nexus.api.world.entity.player.Player;
import com.nexia.nexus.api.world.util.BoundingBox;
import com.nexia.nexus.api.world.util.Location;
import com.nexia.nexus.builder.exception.WrappingException;
import com.nexia.nexus.builder.implementation.Wrapped;
import com.nexia.nexus.builder.implementation.WrappedNexusServer;
import com.nexia.nexus.builder.implementation.util.ObjectMappings;
import com.nexia.nexus.builder.implementation.world.block.WrappedBlock;
import com.nexia.nexus.builder.implementation.world.block.WrappedBlockEntity;
import com.nexia.nexus.builder.implementation.world.block.WrappedBlockState;
import com.nexia.nexus.builder.implementation.world.border.WrappedWorldBorder;
import com.nexia.nexus.builder.implementation.world.entity.WrappedEntity;
import com.google.common.collect.ImmutableList;
import com.nexia.nexus.builder.implementation.world.entity.player.WrappedPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.DerivedLevelData;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class WrappedWorld extends Wrapped<ServerLevel> implements World {
    public WrappedWorld(ServerLevel wrapped) {
        super(wrapped);
    }

    @Override
    public ServerLevel unwrap() {
        return wrapped;
    }

    @Override
    public Identifier getIdentifier() {
        return new Identifier(wrapped.dimension().location().getNamespace(), wrapped.dimension().location().getPath());
    }

    @Override
    public Block getBlockAt(Location location) {
        return new WrappedBlock(location);
    }

    @Override
    public void setBlockAt(Location location, BlockState state) {
        BlockPos pos = new BlockPos(location.getX(), location.getY(), location.getZ());
        Level level = ((WrappedWorld) location.getWorld()).unwrap();
        level.setBlock(pos, ((WrappedBlockState) state).state(), 11);
    }

    @Override
    public BlockEntity getBlockEntity(Location location) {
        return Wrapped.wrap(wrapped.getBlockEntity(new BlockPos(location.getX(), location.getY(), location.getZ())), WrappedBlockEntity.class);
    }

    @Override
    public List<Entity> getEntities() {
        List<Entity> entities = new ArrayList<>();
        wrapped.getAllEntities().forEach((entity) -> entities.add(Wrapped.wrap(entity, WrappedEntity.class)));
        return entities;
    }

    @Override
    public List<Entity> getEntities(Predicate<Entity> filter) {
        List<Entity> entities = new ArrayList<>();
        for(net.minecraft.world.entity.Entity entity : wrapped.getAllEntities()) {
            Entity wrappedEntity = Wrapped.wrap(entity, WrappedEntity.class);
            if(!filter.test(wrappedEntity)) continue;
            entities.add(wrappedEntity);
        }

        return entities;
    }

    @Override
    public List<Player> getPlayers() {
        List<Player> players = new ArrayList<>();
        wrapped.players().forEach((player) -> players.add(Wrapped.wrap(player, WrappedPlayer.class)));
        return players;
    }


    @Override
    public List<Entity> getEntities(BoundingBox area, Predicate<Entity> filter) {
        return wrapped.getEntities((net.minecraft.world.entity.Entity) null,
                new AABB(area.getMinX(),
                        area.getMinY(),
                        area.getMinZ(),
                        area.getMaxX(),
                        area.getMaxY(),
                        area.getMaxZ()),
                (net.minecraft.world.entity.Entity entity) -> filter.test(Wrapped.wrap(entity, WrappedEntity.class)))
                .stream()
                .map(entity -> Wrapped.wrap(entity, WrappedEntity.class))
                .collect(ImmutableList.toImmutableList());
    }

    @Override
    public boolean spawn(Entity entity) {
        if (!(entity instanceof WrappedEntity)) {
            throw new WrappingException("Entity not a WrappedEntity");
        }
        return wrapped.addFreshEntity(((WrappedEntity) entity).unwrap());
    }

    @Override
    public Entity getEntity(UUID uuid) {
        return Wrapped.wrap(wrapped.getEntity(uuid), WrappedEntity.class);
    }

    @Override
    public Player getPlayer(UUID uuid) {
        return Wrapped.wrap(wrapped.getPlayerByUUID(uuid), WrappedPlayer.class);
    }

    @Override
    public NexusServer getServer() {
        return Wrapped.wrap(wrapped.getServer(), WrappedNexusServer.class);
    }

    @Override
    public boolean isSubWorld() {
        return wrapped.getLevelData() instanceof DerivedLevelData;
    }

    @Override
    public Weather getWeather() {
        if (wrapped.isThundering()) {
            return Weather.THUNDER;
        } else if (wrapped.isRaining()) {
            return Weather.RAIN;
        }
        return Weather.CLEAR;
    }

    @Override
    public void setWeather(Weather weather) {
        this.setWeather(weather, 6000);
    }

    @Override
    public void setWeather(Weather weather, int duration) {
        int clearDur = 0;
        int weatherDur = 0;
        boolean raining = false;
        boolean thundering = false;
        switch (weather) {
            case CLEAR:
                clearDur = duration;
                break;
            case RAIN:
                weatherDur = duration;
                raining = true;
                break;
            case THUNDER:
                weatherDur = duration;
                raining = true;
                thundering = true;
                break;
        }
        wrapped.setWeatherParameters(clearDur, weatherDur, raining, thundering);
    }

    private ServerLevelData serverLevelData() {
        return ((ServerLevelData) wrapped.getLevelData());
    }

    @Override
    public WorldBorder getWorldBorder() {
        return Wrapped.wrap(wrapped.getWorldBorder(), WrappedWorldBorder.class);
    }

    @Override
    public long getDayTime() {
        return serverLevelData().getDayTime();
    }

    @Override
    public void setDayTime(long time) {
        serverLevelData().setDayTime(time);
    }

    @Override
    public long getGameTime() {
        return serverLevelData().getGameTime();
    }

    @Override
    public void setGameTime(long time) {
        serverLevelData().setGameTime(time);
    }

    @Override
    public GameModeType getDefaultGameMode() {
        return ObjectMappings.GAME_MODES.inverse().get(serverLevelData().getGameType());
    }

    @Override
    public void setDefaultGameMode(GameModeType gameMode) {
        serverLevelData().setGameType(ObjectMappings.GAME_MODES.get(gameMode));
    }
}
