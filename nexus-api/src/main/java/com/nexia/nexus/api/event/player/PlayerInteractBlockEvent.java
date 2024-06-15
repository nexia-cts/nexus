package com.nexia.nexus.api.event.player;

import com.nexia.nexus.api.event.Cancellable;
import com.nexia.nexus.api.event.EventBackend;
import com.nexia.nexus.api.world.block.Block;
import com.nexia.nexus.api.world.entity.equipment.HandSlot;
import com.nexia.nexus.api.world.entity.player.Player;
import com.nexia.nexus.api.world.item.ItemStack;
import com.nexia.nexus.api.world.util.Location;

public class PlayerInteractBlockEvent extends PlayerEvent implements Cancellable {
    public static final EventBackend<PlayerInteractBlockEvent> BACKEND = EventBackend.create(PlayerInteractBlockEvent.class);

    private boolean cancelled;

    private final Block block;
    private final Location location;
    private final HandSlot hand;
    private final ItemStack itemStack;

    public PlayerInteractBlockEvent(Player player, Block block, Location location, HandSlot hand, ItemStack itemStack) {
        super(player);
        this.block = block;
        this.location = location;
        this.hand = hand;
        this.itemStack = itemStack;
    }

    public Block getBlock() {
        return block;
    }

    public Location getLocation() {
        return location;
    }

    public HandSlot getHand() {
        return hand;
    }

    public ItemStack getItemStack() {
        return itemStack;
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
