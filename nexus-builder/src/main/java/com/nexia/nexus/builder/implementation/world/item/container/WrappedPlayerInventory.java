package com.nexia.nexus.builder.implementation.world.item.container;

import com.nexia.nexus.api.exception.UnassignableTypeException;
import com.nexia.nexus.api.world.entity.equipment.ArmorSlot;
import com.nexia.nexus.api.world.entity.equipment.EquipmentSlot;
import com.nexia.nexus.api.world.entity.equipment.HandSlot;
import com.nexia.nexus.api.world.item.ItemStack;
import com.nexia.nexus.api.world.item.container.PlayerInventory;
import com.nexia.nexus.builder.implementation.Wrapped;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.nexia.nexus.api.world.entity.equipment.ArmorSlot.*;
import static com.nexia.nexus.builder.implementation.world.item.WrappedItemStack.conv;

public class WrappedPlayerInventory extends Wrapped<Inventory> implements PlayerInventory, WrappedContainer {
    public WrappedPlayerInventory(Inventory wrapped) {
        super(wrapped);
    }

    @Override
    public boolean addItemStack(ItemStack itemStack) {
        return wrapped.add(conv(itemStack));
    }

    @Override
    public boolean hasSpaceFor(ItemStack itemStack) {
        return wrapped.getSlotWithRemainingSpace(conv(itemStack)) != -1;
    }

    @Override
    public void removeItemStack(ItemStack itemStack) {
        wrapped.removeItem(conv(itemStack));
    }

    @Override
    public void setItemStack(EquipmentSlot slot, ItemStack itemStack) {
        setItemStack(transformToId(slot), itemStack);
    }

    @Override
    public ItemStack getItemStack(EquipmentSlot slot) {
        return getItemStack(transformToId(slot));
    }

    @Override
    public @Nullable ItemStack getItemStack(int slot) {
        net.minecraft.world.item.ItemStack itemStack = wrapped.getItem(transformSlotId(slot));
        return conv(itemStack);
    }

    @Override
    public void setItemStack(int slot, @Nullable ItemStack itemStack) {
        wrapped.setItem(transformSlotId(slot), conv(itemStack));
    }

    @Override
    public Map<Integer, ItemStack> getInventoryContents() {
        Map<Integer, ItemStack> map = new HashMap<>();
        for (int i = 0; i < wrapped.items.size(); i++) {
            map.put(i, !wrapped.items.get(i).isEmpty()
                    ? conv(wrapped.getItem(i))
                    : null);
        }

        return map;
    }

    @Override
    public Map<ArmorSlot, ItemStack> getArmorContents() {
        Map<ArmorSlot, ItemStack> map = new HashMap<>();
        for (int i = 0; i < wrapped.armor.size(); i++) {
            map.put(transformToArmorSlot(i + 100), conv(wrapped.armor.get(i)));
        }

        return map;
    }

    @Override
    public ItemStack getOffhand() {
        return conv(wrapped.offhand.get(0));
    }

    @Override
    public @Nullable ItemStack getCursorStack() {
        if (wrapped.getCarried().isEmpty()) return null;
        return conv(wrapped.getCarried());
    }

    @Override
    public void setCursorStack(@Nullable ItemStack stack) {
        net.minecraft.world.item.ItemStack mcStack = conv(stack);
        wrapped.setCarried(mcStack);
        if (wrapped.player instanceof ServerPlayer) {
            ((ServerPlayer) wrapped.player).refreshContainer(wrapped.player.inventoryMenu);
        }
    }

    @Override
    public List<Integer> getAvailableSlots() {
        List<Integer> slots = new ArrayList<>();
        for (int i = 0; i < wrapped.items.size(); i++) {
            slots.add(i);
        }

        return slots;
    }

    @Override
    public Container container() {
        return wrapped;
    }

    private int transformSlotId(int slot) {
        int chosen = slot;
        if (slot >= 100) {
            chosen = slot - 100 + wrapped.items.size();
        }
        else if (slot == -106) {
            chosen = wrapped.items.size() + wrapped.armor.size();
        }

        return chosen;
    }

    private ArmorSlot transformToArmorSlot(int id) {
        return switch (id) {
            case 100 -> FEET;
            case 101 -> LEGS;
            case 102 -> CHEST;
            case 103 -> HEAD;
            default -> throw new UnsupportedOperationException("Not an armor slot");
        };
    }

    private int transformToId(EquipmentSlot slot) {
        if (slot instanceof ArmorSlot armor) {
            return switch (armor) {
                case HEAD -> 103;
                case CHEST -> 102;
                case LEGS -> 101;
                case FEET -> 100;
            };
        } else if (slot instanceof HandSlot hand) {
            return switch (hand) {
                case MAIN_HAND -> wrapped.selected;
                case OFF_HAND -> -106;
            };
        }
        throw new UnassignableTypeException("Invalid equipment slot");
    }
}
