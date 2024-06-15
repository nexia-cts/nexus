package com.nexia.nexus.api.event.player;

import com.nexia.nexus.api.event.EventBackend;
import com.nexia.nexus.api.world.entity.player.Player;
import net.kyori.adventure.text.Component;

public class PlayerJoinEvent extends PlayerEvent {
    public static final EventBackend<PlayerJoinEvent> BACKEND = EventBackend.create(PlayerJoinEvent.class);

    private Component joinMessage;

    public PlayerJoinEvent(Player player, Component joinMessage) {
        super(player);
        this.joinMessage = joinMessage;
    }

    public Component getJoinMessage() {
        return joinMessage;
    }

    public void setJoinMessage(Component joinMessage) {
        this.joinMessage = joinMessage;
    }
}
