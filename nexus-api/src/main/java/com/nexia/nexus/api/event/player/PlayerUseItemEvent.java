package com.nexia.nexus.api.event.player;

import com.nexia.nexus.api.event.Cancellable;
import com.nexia.nexus.api.event.EventBackend;
import com.nexia.nexus.api.world.entity.equipment.HandSlot;
import com.nexia.nexus.api.world.entity.player.Player;
import com.nexia.nexus.api.world.item.ItemStack;

public class PlayerUseItemEvent extends PlayerEvent implements Cancellable {
    public static final EventBackend<PlayerUseItemEvent> BACKEND = EventBackend.create(PlayerUseItemEvent.class);

    private boolean cancelled;
    private final ItemStack itemStack;
    private final HandSlot hand;

    public PlayerUseItemEvent(Player player, ItemStack itemStack, HandSlot hand) {
        super(player);
        this.itemStack = itemStack;
        this.hand = hand;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public HandSlot getHand() {
        return hand;
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
