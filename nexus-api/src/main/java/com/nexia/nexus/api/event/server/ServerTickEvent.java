package com.nexia.nexus.api.event.server;

import com.nexia.nexus.api.NexusServer;
import com.nexia.nexus.api.event.Event;
import com.nexia.nexus.api.event.EventBackend;

public class ServerTickEvent extends Event {
    public static final EventBackend<ServerTickEvent> BACKEND = EventBackend.create(ServerTickEvent.class);

    private final NexusServer server;
    private final int tickID;

    public ServerTickEvent(NexusServer server, int tickID) {
        this.server = server;
        this.tickID = tickID;
    }

    public NexusServer getServer() {
        return server;
    }

    public int getTickID() {
        return tickID;
    }
}
