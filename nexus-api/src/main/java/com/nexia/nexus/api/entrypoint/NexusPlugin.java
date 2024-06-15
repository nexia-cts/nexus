package com.nexia.nexus.api.entrypoint;

import com.nexia.nexus.api.NexusAPI;
import com.nexia.nexus.api.NexusServer;

public interface NexusPlugin {
    void onNexusLoad(NexusAPI api, NexusServer server);
}
