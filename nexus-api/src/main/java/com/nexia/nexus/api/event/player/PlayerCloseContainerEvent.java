package com.nexia.nexus.api.event.player;

import com.nexia.nexus.api.event.Cancellable;
import com.nexia.nexus.api.event.EventBackend;
import com.nexia.nexus.api.world.entity.player.Player;
import com.nexia.nexus.api.world.item.container.menu.ContainerMenu;

public class PlayerCloseContainerEvent extends PlayerEvent implements Cancellable {
    public static EventBackend<PlayerCloseContainerEvent> BACKEND = EventBackend.create(PlayerCloseContainerEvent.class);

    private boolean cancelled;
    private final ContainerMenu closedContainer;

    public PlayerCloseContainerEvent(Player player, ContainerMenu closedContainer) {
        super(player);
        this.closedContainer = closedContainer;
    }

    public ContainerMenu getClosedContainer() {
        return closedContainer;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }
}
