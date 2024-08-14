package com.nexia.nexus.builder.mixin.world.inventory;

import com.nexia.nexus.builder.mixin_interfaces.BlockDependentMenu;
import com.nexia.nexus.builder.mixin_interfaces.LevelAccessOwner;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.MenuType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemCombinerMenu.class)
public abstract class ItemCombinerMenuMixin implements BlockDependentMenu, LevelAccessOwner {
    @Unique private ContainerLevelAccess prevAccess;

    @Unique private Player player;

    @Unique private boolean independent;

    @Unique
    ContainerLevelAccess access;

    @Unique
    @Override
    public void nexus$setContainerLevelAccess(ContainerLevelAccess access) {
        this.access = access;
    }

    @Unique
    @Override
    public ContainerLevelAccess nexus$getContainerLevelAccess() {
        return access;
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void injectCustomContainerLevelAccess(MenuType<?> menuType, int i, Inventory inventory, ContainerLevelAccess containerLevelAccess, CallbackInfo ci) {
        this.independent = false;
        this.prevAccess = null;
        this.player = inventory.player;

        if (this.nexus$getContainerLevelAccess() == ContainerLevelAccess.NULL) {
            this.nexus$setIndependent(true);
        }
    }

    @Inject(method = "stillValid", at = @At("HEAD"), cancellable = true)
    public void enableIndependent(Player player, CallbackInfoReturnable<Boolean> cir) {
        if (this.independent) {
            cir.setReturnValue(true);
        }
    }

    @Override
    public void nexus$setIndependent(boolean independent) {
        this.independent = independent;
        if (independent) {
            this.prevAccess = this.nexus$getContainerLevelAccess();
            this.nexus$setContainerLevelAccess(ContainerLevelAccess.create(player.level, BlockPos.ZERO));
        } else {
            this.nexus$setContainerLevelAccess(prevAccess);
            this.prevAccess = null;
        }
    }

    @Override
    public boolean nexus$isIndependent() {
        return independent;
    }
}
