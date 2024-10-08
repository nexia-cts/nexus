package com.nexia.nexus.api.event.player;

import com.nexia.nexus.api.event.EventBackend;
import com.nexia.nexus.api.event.entity.LivingEntityDeathEvent;
import com.nexia.nexus.api.world.damage.DamageData;
import com.nexia.nexus.api.world.entity.player.Player;
import com.nexia.nexus.api.world.scoreboard.ScoreboardTeam;
import net.kyori.adventure.text.Component;

public class PlayerDeathEvent extends LivingEntityDeathEvent {
    public static final EventBackend<PlayerDeathEvent> BACKEND = EventBackend.create(PlayerDeathEvent.class);

    private final Player player;
    private Component deathMessage;
    private ScoreboardTeam.VisibleFor visibleFor;

    public PlayerDeathEvent(Player player, DamageData cause, boolean dropLoot, boolean dropEquipment, boolean dropExperience, Component deathMessage, ScoreboardTeam.VisibleFor visibleFor) {
        super(player, cause, dropLoot, dropEquipment, dropExperience);
        this.player = player;
        this.deathMessage = deathMessage;
        this.visibleFor = visibleFor;
    }

    public Player getPlayer() {
        return player;
    }

    public Component getDeathMessage() {
        return deathMessage;
    }

    public void setDeathMessage(Component deathMessage) {
        this.deathMessage = deathMessage;
    }

    public ScoreboardTeam.VisibleFor getVisibleFor() {
        return visibleFor;
    }

    public void setVisibleFor(ScoreboardTeam.VisibleFor visibleFor) {
        this.visibleFor = visibleFor;
    }
}
