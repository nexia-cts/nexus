package com.nexia.nexus.builder.extension.world.level;

import com.nexia.nexus.api.event.player.PlayerBreakBlockEvent;

public interface BlockExtension {
    void currentBreakEvent(PlayerBreakBlockEvent playerBreakBlockEvent);
}
