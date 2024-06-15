package com.nexia.nexus.api.event.player;

import com.nexia.nexus.api.event.Cancellable;
import com.nexia.nexus.api.event.EventBackend;
import com.nexia.nexus.api.world.entity.player.Player;
import com.nexia.nexus.api.world.item.ItemStack;

/**
 * Calls when the player switches puts his selected item in the offhand.
 */
public class PlayerSwapHandItemsEvent extends PlayerEvent implements Cancellable {
    public static final EventBackend<PlayerSwapHandItemsEvent> BACKEND = EventBackend.create(PlayerSwapHandItemsEvent.class);

    private final ItemStack oldOffhandItem;
    private final ItemStack newOffhandItem;
    private boolean cancelled;

    public PlayerSwapHandItemsEvent(Player player, ItemStack oldOffhandItem, ItemStack newOffhandItem) {
        super(player);
        this.oldOffhandItem = oldOffhandItem;
        this.newOffhandItem = newOffhandItem;
    }

    /**
     * Returns the old item in the players offhand.
     *
     * @return the old item in the players offhand
     */
    public ItemStack getOldOffhandItem() {
        return oldOffhandItem;
    }

    /**
     * Returns the new item in the players offhand.
     *
     * @return the new item in the players offhand
     */
    public ItemStack getNewOffhandItem() {
        return newOffhandItem;
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