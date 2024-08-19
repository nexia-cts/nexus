package com.nexia.nexus.builder.mixin.world.inventory;

import com.nexia.nexus.api.event.player.PlayerContainerClickEvent;
import com.nexia.nexus.api.world.item.container.menu.Button;
import com.nexia.nexus.api.world.item.container.menu.ContainerMenu;
import com.nexia.nexus.api.world.item.container.menu.SlotClickType;
import com.nexia.nexus.api.world.util.Pair;
import com.nexia.nexus.builder.extension.wrap.Wrap;
import com.nexia.nexus.builder.implementation.Wrapped;
import com.nexia.nexus.builder.implementation.util.ObjectMappings;
import com.nexia.nexus.builder.implementation.world.entity.player.WrappedPlayer;
import com.nexia.nexus.builder.implementation.world.item.WrappedItemStack;
import com.nexia.nexus.builder.implementation.world.item.container.menu.WrappedContainerMenu;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(AbstractContainerMenu.class)
public abstract class AbstractContainerMenuMixin implements Wrap<ContainerMenu> {
    @Shadow @Final public List<Slot> slots;

    @Shadow public abstract MenuType<?> getType();

    @Unique private WrappedContainerMenu wrapped;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void injectWrap(MenuType<?> menuType, int i, CallbackInfo ci) {
        this.wrapped = new WrappedContainerMenu((AbstractContainerMenu) (Object) this, ObjectMappings.getType((AbstractContainerMenu) (Object) this));
    }

    @Override
    public ContainerMenu wrap() {
        return wrapped;
    }

    @Inject(method = "doClick", at = @At(value = "JUMP", ordinal = 0, shift = At.Shift.BEFORE), cancellable = true)
    public void injectPlayerContainerClickEvent(int i, int j, ClickType clickType, Player player, CallbackInfoReturnable<ItemStack> cir) {
        WrappedPlayer apiPlayer = Wrapped.wrap(player, WrappedPlayer.class);
        ContainerMenu menu = Wrapped.wrap(this, WrappedContainerMenu.class);
        Pair<SlotClickType, Button> typeAndButton = getAPITypes(i, j, clickType);
        SlotClickType slotClickType = typeAndButton.a();
        Button button = typeAndButton.b();

        WrappedItemStack cursorStack = player.inventory.getCarried() != null
                && !player.inventory.getCarried().isEmpty()
                ? Wrapped.wrap(player.inventory.getCarried(), WrappedItemStack.class)
                : null;
        WrappedItemStack targetStack = null;
        if (i >= 0 && i < slots.size() && slots.get(i).getItem() != null && !slots.get(i).getItem().isEmpty()) {
            targetStack = Wrapped.wrap(slots.get(i).getItem(), WrappedItemStack.class);
        }
        PlayerContainerClickEvent clickEvent = new PlayerContainerClickEvent(apiPlayer, menu, i, slotClickType, button, targetStack, cursorStack);
        PlayerContainerClickEvent.BACKEND.invoke(clickEvent);

        ServerPlayer serverPlayer = (ServerPlayer) player;
        if (clickEvent.isCancelled()) {
            serverPlayer.refreshContainer((AbstractContainerMenu) (Object) this);
            cir.setReturnValue(targetStack != null ? targetStack.unwrap() : ItemStack.EMPTY);
        }

        PlayerContainerClickEvent.BACKEND.invokeEndFunctions(clickEvent);
    }

    @Unique
    public Pair<SlotClickType, Button> getAPITypes(int slot, int button, ClickType type) {
        SlotClickType slotClickType;
        Button apiButton;
        switch (type) {
            case PICKUP:
            case SWAP:
            case CLONE:
                slotClickType = SlotClickType.CLICK;
                break;
            case QUICK_MOVE:
                slotClickType = SlotClickType.SHIFT_CLICK;
                break;
            case THROW:
                slotClickType = button == 0
                        ? SlotClickType.CLICK
                        : SlotClickType.SHIFT_CLICK;
                break;
            case QUICK_CRAFT:
                slotClickType = switch (button % 4) {
                    case 0 -> SlotClickType.DRAG_START;
                    case 1 -> SlotClickType.DRAG_CONTINUE;
                    case 2 -> SlotClickType.DRAG_STOP;
                    default -> SlotClickType.NONE;
                };
                break;
            case PICKUP_ALL:
                slotClickType = SlotClickType.DOUBLE_CLICK;
                break;
            default:
                slotClickType = SlotClickType.NONE;
        }

        switch (type) {
            case PICKUP:
            case QUICK_MOVE:
            case CLONE:
            case PICKUP_ALL:
                apiButton = switch (button) {
                    case 0 -> Button.LEFT_CLICK;
                    case 1 -> Button.RIGHT_CLICK;
                    case 2 -> Button.MIDDLE_CLICK;
                    default -> Button.NONE;
                };
                break;
            case SWAP:
                apiButton = Button.hotkey(button);
                break;
            case THROW:
                apiButton = Button.DROP;
                break;
            case QUICK_CRAFT:
                apiButton = switch (button / 4) {
                    case 0 -> Button.LEFT_CLICK;
                    case 1 -> Button.RIGHT_CLICK;
                    case 2 -> Button.MIDDLE_CLICK;
                    default -> Button.NONE;
                };
                break;
            default:
                apiButton = Button.NONE;
        }

        return new Pair<>(slotClickType, apiButton);
    }
}
