package com.nexia.nexus.api.event.server;

import com.nexia.nexus.api.NexusServer;
import com.nexia.nexus.api.event.Event;
import com.nexia.nexus.api.event.EventBackend;

public class ServerStopEvent extends Event {
    public static final EventBackend<ServerStopEvent> BACKEND = EventBackend.create(ServerStopEvent.class);

    private final NexusServer server;
    public ServerStopEvent(NexusServer server) {
        this.server = server;
    }

    public NexusServer getServer() {
        return server;
    }
}
