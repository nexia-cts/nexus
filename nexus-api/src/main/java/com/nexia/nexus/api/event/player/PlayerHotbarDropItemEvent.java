package com.nexia.nexus.api.event.player;

import com.nexia.nexus.api.event.Cancellable;
import com.nexia.nexus.api.event.EventBackend;
import com.nexia.nexus.api.world.entity.player.Player;
import com.nexia.nexus.api.world.item.ItemStack;

public class PlayerHotbarDropItemEvent extends PlayerEvent implements Cancellable {
    public static final EventBackend<PlayerHotbarDropItemEvent> BACKEND = EventBackend.create(PlayerHotbarDropItemEvent.class);

    private boolean cancelled;

    private final ItemStack itemStack;
    private final boolean dropEntireStack;
    private final int hotbarSlot;

    public PlayerHotbarDropItemEvent(Player player, ItemStack itemStack, boolean dropEntireStack, int hotbarSlot) {
        super(player);
        this.itemStack = itemStack;
        this.dropEntireStack = dropEntireStack;
        this.hotbarSlot = hotbarSlot;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public boolean isDropEntireStack() {
        return dropEntireStack;
    }

    public int getHotbarSlot() {
        return hotbarSlot;
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
