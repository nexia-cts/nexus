package com.nexia.nexus.api;

import com.nexia.nexus.api.builder.Builder;
import com.nexia.nexus.api.event.server.ServerTickEvent;
import com.nexia.nexus.api.scheduler.TaskScheduler;
import com.nexia.nexus.api.scheduler.TickFunction;
import com.nexia.nexus.api.util.ImplementationUtils;
import com.nexia.nexus.api.world.util.Pair;

public class NexusAPI {
    private static NexusAPI INSTANCE = null;

    private final Builder builder;
    private final ImplementationUtils implementationUtils;
    private final NexusServer server;
    private final TaskScheduler scheduler;
    private final TickFunction tickFunction;

    public NexusAPI(NexusServer server, Builder builder) {
        this.builder = builder;
        this.implementationUtils = builder.createImplementationUtils();
        this.server = server;
        Pair<TaskScheduler, TickFunction> sched = TaskScheduler.create();
        this.scheduler = sched.a();
        this.tickFunction = sched.b();

        ServerTickEvent.BACKEND.register(event -> event.runAfterwards(tickFunction::tick));

        INSTANCE = this;
    }

    public Builder getBuilder() {
        return builder;
    }

    public NexusServer getServer() {
        return server;
    }

    public ImplementationUtils getImplementationUtils() {
        return implementationUtils;
    }

    public static NexusAPI getInstance() {
        return INSTANCE;
    }

    public TaskScheduler getScheduler() {
        return scheduler;
    }
}
