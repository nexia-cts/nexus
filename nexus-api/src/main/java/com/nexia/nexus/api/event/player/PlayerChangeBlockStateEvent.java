package com.nexia.nexus.api.event.player;

import com.nexia.nexus.api.event.Cancellable;
import com.nexia.nexus.api.event.EventBackend;
import com.nexia.nexus.api.world.block.Block;
import com.nexia.nexus.api.world.block.BlockState;
import com.nexia.nexus.api.world.entity.player.Player;
import com.nexia.nexus.api.world.util.Location;

public class PlayerChangeBlockStateEvent extends PlayerEvent implements Cancellable {
    public static final EventBackend<PlayerChangeBlockStateEvent> BACKEND = EventBackend.create(PlayerChangeBlockStateEvent.class);

    private boolean cancelled;

    private final Location location;
    private final Block currentBlockState;
    private BlockState newBlockState;
    private final Action action;

    public PlayerChangeBlockStateEvent(Player player, Location location, Block currentBlockState, BlockState newBlockState, Action action) {
        super(player);
        this.location = location;
        this.currentBlockState = currentBlockState;
        this.newBlockState = newBlockState;
        this.action = action;
    }

    public Location getLocation() {
        return location;
    }

    public Block getCurrentBlockState() {
        return currentBlockState;
    }

    public BlockState getNewBlockState() {
        return newBlockState;
    }

    public void setNewBlockState(BlockState newBlockState) {
        this.newBlockState = newBlockState;
    }

    public Action getAction() {
        return action;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    public enum Action {
        OPEN_CLOSE, STRIP_BLOCK, PULL_LEVER, PRESS_BUTTON, TRAMPLE_ON_FARMLAND
    }
}
