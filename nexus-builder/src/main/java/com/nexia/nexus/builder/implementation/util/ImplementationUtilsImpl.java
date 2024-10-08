package com.nexia.nexus.builder.implementation.util;

import com.nexia.nexus.api.interfaces.Namespaced;
import com.nexia.nexus.api.util.Identifier;
import com.nexia.nexus.api.util.ImplementationUtils;
import com.nexia.nexus.api.world.block.BlockType;
import com.nexia.nexus.api.world.effect.StatusEffect;
import com.nexia.nexus.api.world.entity.EntityType;
import com.nexia.nexus.api.world.item.Enchantment;
import com.nexia.nexus.api.world.item.ItemStack;
import com.nexia.nexus.api.world.item.ItemType;
import com.nexia.nexus.api.world.item.container.menu.ContainerMenuType;
import com.nexia.nexus.api.world.item.container.menu.MenuHolder;
import com.nexia.nexus.api.world.sound.SoundType;
import com.nexia.nexus.builder.extension.world.effect.MobEffectExtension;
import com.nexia.nexus.builder.extension.world.inventory.MenuTypeExtension;
import com.nexia.nexus.builder.implementation.Wrapped;
import com.nexia.nexus.builder.implementation.world.item.WrappedItemStack;
import com.nexia.nexus.builder.implementation.world.item.container.menu.WrappedMenuHolder;
import net.kyori.adventure.text.Component;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.Locale;

import static com.nexia.nexus.builder.implementation.util.ObjectMappings.*;

@SuppressWarnings("Immutable")
public class ImplementationUtilsImpl implements ImplementationUtils {
    @Override
    public int getMaxLevel(Enchantment enchantment) {
        return ENCHANTMENTS.get(enchantment).getMaxLevel();
    }

    @Override
    public boolean isCurse(Enchantment enchantment) {
        return ENCHANTMENTS.get(enchantment).isCurse();
    }

    @Override
    public boolean canApply(Enchantment enchantment, ItemStack itemStack) {
        net.minecraft.world.item.ItemStack mcStack = ((WrappedItemStack) itemStack).unwrap();
        net.minecraft.world.item.enchantment.Enchantment mcEnchant = ENCHANTMENTS.get(enchantment);
        return mcEnchant.canEnchant(mcStack, true);
    }

    @Override
    public StatusEffect.Type getType(StatusEffect effect) {
        return switch (((MobEffectExtension) EFFECTS.get(effect)).getCategory()) {
            case HARMFUL -> StatusEffect.Type.HARMFUL;
            case BENEFICIAL -> StatusEffect.Type.BENEFICIAL;
            default -> StatusEffect.Type.NEUTRAL;
        };
    }

    @Override
    public boolean isBlockItem(ItemType item) {
        return ITEMS.get(item) instanceof BlockItem;
    }

    @Override
    public BlockType getBlock(ItemType item) {
        Item mcItem = ITEMS.get(item);
        return mcItem instanceof BlockItem ? BLOCKS.inverse().get(((BlockItem) mcItem).getBlock()) : null;
    }

    @Override
    public int getMaxStackSize(ItemType item) {
        return ITEMS.get(item).getMaxStackSize();
    }

    @Override
    public int getMaxDamage(ItemType item) {
        return ITEMS.get(item).getMaxDamage();
    }

    @Override
    public Identifier getIdentifier(Namespaced namespaced) {
        ResourceLocation loc = null;
        if (namespaced instanceof EntityType) {
            loc = Registry.ENTITY_TYPE.getKey(ENTITIES.get((EntityType) namespaced));
        }
        else if (namespaced instanceof StatusEffect) {
            loc = Registry.MOB_EFFECT.getKey(EFFECTS.get((StatusEffect) namespaced));
        }
        else if (namespaced instanceof Enchantment) {
            loc = Registry.ENCHANTMENT.getKey(ENCHANTMENTS.get((Enchantment) namespaced));
        }
        else if (namespaced instanceof ItemType) {
            loc = Registry.ITEM.getKey(ITEMS.get((ItemType) namespaced));
        }
        else if (namespaced instanceof BlockType) {
            loc = Registry.BLOCK.getKey(BLOCKS.get((BlockType) namespaced));
        }
        
        if (loc != null) {
            return new Identifier(loc.getNamespace(), loc.getPath());
        }
        
        throw new IllegalArgumentException("Cannot find identifier for " + namespaced);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Namespaced> T getByIdentifier(final Identifier identifier, Class<T> type) {
        ResourceLocation loc = new ResourceLocation(identifier.getNamespace(), identifier.getId());
        try {
            if (EntityType.class.isAssignableFrom(type) && Registry.ENTITY_TYPE.getOptional(loc).isPresent()) {
                net.minecraft.world.entity.EntityType<?> mcType = Registry.ENTITY_TYPE.get(loc);
                return ENTITIES.inverse().containsKey(mcType)
                        ? (T) ENTITIES.inverse().get(mcType)
                        : (T) new EntityType.Other() {
                    @Override
                    public String getId() {
                        return identifier.pure();
                    }

                    @Override
                    public Identifier getNamespaceId() {
                        return identifier;
                    }
                };
            } else if (StatusEffect.class.isAssignableFrom(type) && Registry.MOB_EFFECT.getOptional(loc).isPresent()) {
                final MobEffect mcType = Registry.MOB_EFFECT.get(loc);
                assert mcType != null;
                return EFFECTS.inverse().containsKey(mcType)
                        ? (T) EFFECTS.inverse().get(mcType)
                        : (T) new StatusEffect.Other() {
                    @Override
                    public Type getType() {
                        return switch (((MobEffectExtension) mcType).getCategory()) {
                            case HARMFUL -> Type.HARMFUL;
                            case BENEFICIAL -> Type.BENEFICIAL;
                            default -> Type.NEUTRAL;
                        };
                    }

                    @Override
                    public Identifier getNamespaceId() {
                        return identifier;
                    }

                    @Override
                    public String getId() {
                        return identifier.pure();
                    }
                };
            } else if (Enchantment.class.isAssignableFrom(type) && Registry.ENCHANTMENT.getOptional(loc).isPresent()) {
                final net.minecraft.world.item.enchantment.Enchantment mcType = Registry.ENCHANTMENT.get(loc);
                assert mcType != null;
                return ENCHANTMENTS.inverse().containsKey(mcType)
                        ? (T) ENCHANTMENTS.inverse().get(Registry.ENCHANTMENT.get(loc))
                        : (T) new Enchantment.Other() {
                    @Override
                    public Identifier getNamespaceId() {
                        return identifier;
                    }

                    @Override
                    public String getId() {
                        return identifier.pure();
                    }

                    @Override
                    public boolean isCurse() {
                        return mcType.isCurse();
                    }

                    @Override
                    public int getMaxLevel() {
                        return mcType.getMaxLevel();
                    }

                    @Override
                    public boolean canBeAppliedTo(ItemStack itemStack) {
                        return mcType.canEnchant(((WrappedItemStack) itemStack).unwrap(), true);
                    }
                };
            } else if (ItemType.class.isAssignableFrom(type) && Registry.ITEM.getOptional(loc).isPresent()) {
                final Item mcType = Registry.ITEM.get(loc);
                return ITEMS.inverse().containsKey(mcType)
                        ? (T) ITEMS.inverse().get(Registry.ITEM.get(loc))
                        : (T) new ItemType.Other() {
                    @Override
                    public Identifier getNamespaceId() {
                        return identifier;
                    }

                    @Override
                    public String getId() {
                        return identifier.pure();
                    }

                    @Override
                    public boolean isBlockItem() {
                        return mcType instanceof BlockItem;
                    }

                    @Override
                    public BlockType getBlock() {
                        return isBlockItem() ? BLOCKS.inverse().get(((BlockItem) mcType).getBlock()) : null;
                    }

                    @Override
                    public int getMaxStackSize() {
                        return mcType.getMaxStackSize();
                    }

                    @Override
                    public int getMaxDamage() {
                        return mcType.getMaxDamage();
                    }
                };
            } else if (BlockType.class.isAssignableFrom(type) && Registry.BLOCK.getOptional(loc).isPresent()) {
                final Block mcType = Registry.BLOCK.get(loc);
                return BLOCKS.inverse().containsKey(mcType)
                        ? (T) BLOCKS.inverse().get(Registry.BLOCK.get(loc))
                        : (T) new BlockType.Other() {
                    @Override
                    public Identifier getNamespaceId() {
                        return identifier;
                    }

                    @Override
                    public String getId() {
                        return identifier.pure();
                    }
                };
            } else if (BlockType.class.isAssignableFrom(type) && Registry.SOUND_EVENT.getOptional(loc).isPresent()) {
                final SoundEvent mcType = Registry.SOUND_EVENT.get(loc);
                if (mcType == null) {
                    return (T) new SoundType.Other() {
                        @Override
                        public Identifier getNamespaceId() {
                            return identifier;
                        }

                        @Override
                        public String getId() {
                            return identifier.pure();
                        }
                    };
                }

                String replacedMCType = mcType.toString().toLowerCase(Locale.ROOT).replace("_", ".");
                return SOUNDS.inverse().containsKey(replacedMCType)
                        ? (T) SOUNDS.inverse().get(replacedMCType)
                        : (T) new SoundType.Other() {
                    @Override
                    public Identifier getNamespaceId() {
                        return identifier;
                    }

                    @Override
                    public String getId() {
                        return identifier.pure();
                    }
                };
            } else {
                throw new IllegalStateException("No " + type.getSimpleName() + " registered with " + identifier);
            }
        } catch (ClassCastException e) {
            throw new IllegalStateException("Incompatible type", e);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Could not find " + identifier + " with type " + type.getSimpleName(), e);
        }
    }

    @Override
    public MenuHolder createMenu(ContainerMenuType type, Component title) {
       return Wrapped.wrap(new SimpleMenuProvider((i, inventory, player) ->
               ((MenuTypeExtension) MENU_TYPES.get(type)).createServer(i, inventory),
               convertComponent(title)), WrappedMenuHolder.class);
    }
}
