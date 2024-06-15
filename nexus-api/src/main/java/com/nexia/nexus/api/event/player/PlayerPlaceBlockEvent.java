package com.nexia.nexus.api.event.player;

import com.nexia.nexus.api.event.Cancellable;
import com.nexia.nexus.api.event.EventBackend;
import com.nexia.nexus.api.world.World;
import com.nexia.nexus.api.world.block.Block;
import com.nexia.nexus.api.world.block.BlockState;
import com.nexia.nexus.api.world.entity.equipment.HandSlot;
import com.nexia.nexus.api.world.entity.player.Player;
import com.nexia.nexus.api.world.item.ItemStack;
import com.nexia.nexus.api.world.util.Location;
import org.jetbrains.annotations.Nullable;

public class PlayerPlaceBlockEvent extends PlayerEvent implements Cancellable {
    public static final EventBackend<PlayerPlaceBlockEvent> BACKEND = EventBackend.create(PlayerPlaceBlockEvent.class);

    private boolean cancelled;

    private final Location location;
    private final @Nullable Block currentBlockState;
    private @Nullable BlockState newBlockState;
    private final ItemStack blockStack;
    private final HandSlot placingHand;

    public PlayerPlaceBlockEvent(Player player, Location location, @Nullable Block currentBlockState, @Nullable BlockState newBlockState, ItemStack blockStack, HandSlot placingHand) {
        super(player);
        this.location = location;
        this.currentBlockState = currentBlockState;
        this.newBlockState = newBlockState;
        this.blockStack = blockStack;
        this.placingHand = placingHand;
    }

    public Location getLocation() {
        return location;
    }

    public World getWorld() {
        return location.getWorld();
    }

    public @Nullable Block getCurrentBlockState() {
        return currentBlockState;
    }

    public @Nullable BlockState getNewBlockState() {
        return newBlockState;
    }

    public void setNewBlockState(@Nullable BlockState newBlockState) {
        this.newBlockState = newBlockState;
    }

    public ItemStack getBlockStack() {
        return blockStack;
    }

    public HandSlot getPlacingHand() {
        return placingHand;
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
