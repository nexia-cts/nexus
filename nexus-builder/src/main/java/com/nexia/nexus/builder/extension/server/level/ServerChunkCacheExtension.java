package com.nexia.nexus.builder.extension.server.level;

public interface ServerChunkCacheExtension {
    void setThread(Thread thread);
    Thread getThread();
}
