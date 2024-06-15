package com.nexia.nexus.builder.mixin.world.inventory;

import com.nexia.nexus.builder.mixin_interfaces.LevelAccessOwner;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CraftingMenu.class)
public abstract class CraftingMenuMixin implements LevelAccessOwner {
    @Mutable
    @Shadow
    @Final
    private ContainerLevelAccess access;

    @Override
    public void setContainerLevelAccess(ContainerLevelAccess access) {
        this.access = access;
    }

    @Override
    public ContainerLevelAccess getContainerLevelAccess() {
        return access;
    }
}
