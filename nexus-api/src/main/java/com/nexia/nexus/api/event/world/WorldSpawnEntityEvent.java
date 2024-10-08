package com.nexia.nexus.api.event.world;

import com.nexia.nexus.api.event.Cancellable;
import com.nexia.nexus.api.event.EventBackend;
import com.nexia.nexus.api.world.World;
import com.nexia.nexus.api.world.entity.Entity;
import com.nexia.nexus.api.world.util.Location;

public class WorldSpawnEntityEvent extends WorldEvent implements Cancellable {
    public static final EventBackend<WorldSpawnEntityEvent> BACKEND = EventBackend.create(WorldSpawnEntityEvent.class);

    private boolean cancelled;

    private final Entity entity;
    private Location spawnLocation;

    public WorldSpawnEntityEvent(World world, Entity entity, Location spawnLocation) {
        super(world);
        this.entity = entity;
        this.spawnLocation = spawnLocation;
    }

    public Entity getEntity() {
        return entity;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public void setSpawnLocation(Location spawnLocation) {
        this.spawnLocation = spawnLocation;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }
}
