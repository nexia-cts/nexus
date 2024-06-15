package com.nexia.nexus.api.event.player;

import com.nexia.nexus.api.event.Cancellable;
import com.nexia.nexus.api.event.EventBackend;
import com.nexia.nexus.api.world.entity.player.Player;
import com.nexia.nexus.api.world.item.container.menu.ContainerMenu;
import org.jetbrains.annotations.Nullable;

public class PlayerOpenContainerEvent extends PlayerEvent implements Cancellable {
    public static final EventBackend<PlayerOpenContainerEvent> BACKEND = EventBackend.create(PlayerOpenContainerEvent.class);

    private boolean cancelled;
    private final @Nullable ContainerMenu newMenu;

    public PlayerOpenContainerEvent(Player player, @Nullable ContainerMenu newMenu) {
        super(player);
        this.newMenu = newMenu;
    }

    public @Nullable ContainerMenu getNewMenu() {
        return newMenu;
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
