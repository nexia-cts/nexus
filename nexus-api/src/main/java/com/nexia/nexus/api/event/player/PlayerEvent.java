package com.nexia.nexus.api.event.player;

import com.nexia.nexus.api.event.entity.LivingEntityEvent;
import com.nexia.nexus.api.world.entity.player.Player;

public abstract class PlayerEvent extends LivingEntityEvent {
    private final Player player;

    public PlayerEvent(Player player) {
        super(player);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
