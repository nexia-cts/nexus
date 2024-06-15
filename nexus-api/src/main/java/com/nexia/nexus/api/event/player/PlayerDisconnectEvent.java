package com.nexia.nexus.api.event.player;

import com.nexia.nexus.api.event.EventBackend;
import com.nexia.nexus.api.world.entity.player.Player;
import net.kyori.adventure.text.Component;

public class PlayerDisconnectEvent extends PlayerEvent {
    public static final EventBackend<PlayerDisconnectEvent> BACKEND = EventBackend.create(PlayerDisconnectEvent.class);

    private final Component disconnectReason;
    private Component leaveMessage;

    public PlayerDisconnectEvent(Player player, Component disconnectReason, Component leaveMessage) {
        super(player);
        this.disconnectReason = disconnectReason;
        this.leaveMessage = leaveMessage;
    }

    public Component getDisconnectReason() {
        return disconnectReason;
    }

    public Component getLeaveMessage() {
        return leaveMessage;
    }

    public void setLeaveMessage(Component leaveMessage) {
        this.leaveMessage = leaveMessage;
    }
}
