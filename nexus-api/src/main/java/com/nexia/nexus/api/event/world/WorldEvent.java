package com.nexia.nexus.api.event.world;

import com.nexia.nexus.api.event.Event;
import com.nexia.nexus.api.world.World;

public abstract class WorldEvent extends Event {
    private final World world;

    public WorldEvent(World world) {
        this.world = world;
    }

    public World getWorld() {
        return world;
    }
}
