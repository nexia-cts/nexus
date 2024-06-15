package com.nexia.nexus.api.event.server;

import com.nexia.nexus.api.NexusServer;
import com.nexia.nexus.api.event.Event;
import com.nexia.nexus.api.event.EventBackend;

public class ServerStartupFinishEvent extends Event {
    public static final EventBackend<ServerStartupFinishEvent> BACKEND = EventBackend.create(ServerStartupFinishEvent.class);

    private final NexusServer server;
    public ServerStartupFinishEvent(NexusServer server) {
        this.server = server;
    }

    public NexusServer getServer() {
        return server;
    }
}
