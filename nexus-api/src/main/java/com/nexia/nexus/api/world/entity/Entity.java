package com.nexia.nexus.api.world.entity;

import com.nexia.nexus.api.builder.Builder;
import com.nexia.nexus.api.command.CommandSender;
import com.nexia.nexus.api.command.CommandSourceInfo;
import com.nexia.nexus.api.exception.UnassignableTypeException;
import com.nexia.nexus.api.world.World;
import com.nexia.nexus.api.world.nbt.NBTObject;
import com.nexia.nexus.api.world.util.Location;
import com.nexia.nexus.api.world.util.Vector3D;
import net.kyori.adventure.nbt.api.BinaryTagHolder;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface Entity extends CommandSender {
    int getEntityId();

    EntityType getEntityType();

    Component getName();

    UUID getUUID();

    @Nullable
    Component getCustomName();

    void setCustomName(Component customName);

    boolean isCustomNameVisible();

    void setCustomNameVisible(boolean customNameVisible);

    boolean isNoGravity();

    void setNoGravitiy(boolean noGravitiy);

    boolean isInvisible();

    void setInvisible(boolean invisible);

    boolean isGlowing();

    void setGlowing(boolean glowing);

    boolean isInvulnerable();

    void setInvulnerable(boolean invulnurable);

    Location getLocation();

    World getWorld();

    void teleport(Location location, boolean ignoreDirection);

    default void teleport(Location location) { teleport(location, false); }

    void kill();

    boolean isOnFire();

    int getRemainingFireTicks();

    void setRemainingFireTicks(int ticks);

    Vector3D getVelocity();

    void setVelocity(Vector3D velocity);

    default void addVelocity(Vector3D velocity) {
        setVelocity(getVelocity().add(velocity));
    }

    List<Entity> getPassengers();

    void removePassenger(Entity entity);

    void addPassenger(Entity entity);

    void startRiding(Entity entity);

    void stopRiding();

    Set<String> getTags();

    boolean addTag(String tag);

    void removeTag(String tag);

    boolean hasTag(String tag);

    @Deprecated BinaryTagHolder getEntityData();

    @Deprecated void setEntityData(BinaryTagHolder tag);

    NBTObject getEntityNBT();

    void setEntityNBT(NBTObject nbt);

    boolean isOnGround();

    CommandSourceInfo createCommandInfo();

    static Entity create(EntityType type, World world) {
        return Builder.getInstance().createEntity(type, world);
    }

    @SuppressWarnings("unchecked")
    static <T extends Entity> T create(EntityType type, World world, Class<T> clazz) {
        try {
            return ((T) create(type, world));
        } catch (ClassCastException e) {
            throw new UnassignableTypeException(clazz.getName() + " can not be assigned to entity type " + type.toString());
        }
    }
}
